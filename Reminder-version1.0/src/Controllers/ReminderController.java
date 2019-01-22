package Controllers;


import Models.Notification;
import Models.Reminder.Reminder;
import Models.Reminder.RepeatType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

public  class ReminderController {
    private String errorMessage = "";
    private Notification notification = new Notification();
    @FXML
    private TextField titleField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox timeMenuButton, ampmComboBox;
    @FXML
    private MenuButton repeatMenuButton;
    @FXML
    private Button createButton;
    @FXML
    private Button cancelButton;

    @FXML
    private MenuItem periodDaily,periodWeekly, periodWeekday, periodMonthly, periodYearly,periodCustom;

    public static RepeatType customReminder;

    @FXML
    private void setRepeatMenuButtonDaily(){
        repeatMenuButton.setText("Daily");
    }
    @FXML
    private void setRepeatMenuButtonWeekly(){
        repeatMenuButton.setText("Weekly");
    }
    @FXML
    private void setRepeatMenuButtonWeekday(){
        repeatMenuButton.setText("Weekday");
    }
    @FXML
    private void setRepeatMenuButtonMonthly(){
        repeatMenuButton.setText("Monthly");
    }
    @FXML
    private void setRepeatMenuButtonYearly(){
        repeatMenuButton.setText("Yearly");
    }
    @FXML
    private void setRepeatMenuButtonCustom() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/ReminderCustomType/ReminderCustomView.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage  = new Stage();
        stage.setTitle("Create Custom Reminder");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void acceptButtonPressed(){

        String timeStr ="";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            timeStr = timeMenuButton.getValue() + " " + (String) ampmComboBox.getValue() + " " + datePicker.getValue().format(formatter);
        } catch (NullPointerException npe){
            errorMessage = errorMessage + "Unspecified Date";
        }
        if(!repeatMenuButton.getText().equals("Custom")){
            if(errorMessage.equals("")) {
                try {
                    System.out.println("save reminder");
                    DataLoad.reminderList.add(new Reminder(titleField.getText(), timeStr, repeatMenuButton.getText()));
                } catch (Exception e) {
                    System.out.println("Error creating a Reminder");
                }
            } else {
                notification.sendNotification("Error creating Reminder", errorMessage, false);
            }
        } else {
            // THIS IS FOR CUSTOM CLASS, IT IS NOT DONE
            if(errorMessage.equals("")) {
                try {
                    System.out.println("save custom reminder");
                    //DataLoad.reminderList.add(new Reminder(titleField.getText(),timeStr,temporaryReminder));
                } catch (Exception e) {
                    System.out.println("Error creating a custom reminder");
                }
            } else {
                notification.sendNotification("Error creating Reminder", errorMessage, false);
            }
        }
    }

    @FXML
    private void cancelButtonPressed(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
