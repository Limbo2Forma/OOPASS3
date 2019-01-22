package Controllers;

import Models.Reminder.RepeatType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

public class ReminderCustomController {
    @FXML Pane weekPane, monthlyPane, iterationPane, datePane;
    @FXML Pane pane, sub_pane, reminderPane;
    @FXML MenuButton menu, sub_menu, date, week;
    @FXML Button okButton, cancelButton;
    @FXML Spinner spinner;
    @FXML DatePicker datePick;
    @FXML TextField iterationNum;
    @FXML ToggleButton tbMon, tbTue, tbWed, tbThu, tbFri, tbSat, tbSun;

    private boolean[] dateOfWeekBool= new boolean[7];
    private int[] dateOfMonthInt = new int[2];

    @FXML
    private void loadViewDay(){
        try {
            menu.setText("Daily");
            pane.getChildren().remove(monthlyPane);
            pane.getChildren().remove(weekPane);
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("exeception error 1");
        }
    }

    @FXML
    private void loadViewWeek(){
        try {
            pane.getChildren().remove(monthlyPane);
            pane.getChildren().remove(weekPane);
            weekPane = FXMLLoader.load(getClass().getResource("/Views/ReminderCustomType/reminderViewWeekly.fxml"));
            pane.getChildren().add(weekPane);
            menu.setText("Weekly");
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("exeception error 1");
        }
    }

    @FXML
    private void setSun(){
        dateOfWeekBool[0] = !dateOfWeekBool[0];
    }

    @FXML
    private void setMon(){
        dateOfWeekBool[1] = !dateOfWeekBool[1];
    }

    @FXML
    private void setTue(){
        dateOfWeekBool[2] = !dateOfWeekBool[2];
    }
    @FXML
    private void setWed(){
        dateOfWeekBool[3] = !dateOfWeekBool[3];
    }
    @FXML
    private void setThu(){
        dateOfWeekBool[4] = !dateOfWeekBool[4];
    }
    @FXML
    private void setFri(){
        dateOfWeekBool[5] = !dateOfWeekBool[5];
    }
    @FXML
    private void setSat(){
        dateOfWeekBool[6] = !dateOfWeekBool[6];
    }

    @FXML
    private void loadViewMonth(){
        try {
            menu.setText("Monthly");
            pane.getChildren().remove(weekPane);
            pane.getChildren().remove(monthlyPane);
            monthlyPane = FXMLLoader.load(getClass().getResource("/Views/ReminderCustomType/reminderViewMonthly.fxml"));
            pane.getChildren().add(monthlyPane);
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("exeception error 2");
        }
    }
    @FXML
    private void loadViewYear(){
        try {
            menu.setText("Yearly");
            pane.getChildren().remove(monthlyPane);
            pane.getChildren().remove(weekPane);
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("exeception error 1");
        }
    }
    @FXML
    private void displayMon(){
        date.setText("Monday");
        dateOfMonthInt[0] = 1;
    }
    @FXML
    private void displayTue(){
        date.setText("Tuesday");
        dateOfMonthInt[0] = 2;
    }
    @FXML
    private void displayWed(){
        date.setText("Wednesday");
        dateOfMonthInt[0] = 3;
    }
    @FXML
    private void displayThur(){
        date.setText("Thursday");
        dateOfMonthInt[0] = 4;
    }
    @FXML
    private void displayFri(){
        date.setText("Friday");
        dateOfMonthInt[0] = 5;
    }
    @FXML
    private void displaySat(){
        date.setText("Saturday");
        dateOfMonthInt[0] = 6;
    }
    @FXML
    private void displaySun(){
        date.setText("Sunday");
        dateOfMonthInt[0] = 0;
    }

    @FXML
    private void displayWeek1(){
        week.setText("Week1");
        dateOfMonthInt[1] = 1;
    }
    @FXML
    private void displayWeek2(){
        week.setText("Week2");
        dateOfMonthInt[1] = 2;
    }
    @FXML
    private void displayWeek3(){
        week.setText("Week3");
        dateOfMonthInt[1] = 3;
    }
    @FXML
    private void displayWeek4(){
        week.setText("Week4");
        dateOfMonthInt[1] = 4;
    }
    @FXML
    private void displayWeek5(){
        week.setText("Week5");
        dateOfMonthInt[1] = 5;
    }

    @FXML
    private void loadEndButton_Never() {
        try {
            sub_menu.setText("Never");
            sub_pane.getChildren().remove(datePane);
            sub_pane.getChildren().remove(iterationPane);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception error 4");
        }
    }

    @FXML
    private void loadEndButton_OnDate(){
        try{
            sub_menu.setText("On Date:");
            sub_pane.getChildren().remove(iterationPane);
            datePane = FXMLLoader.load(getClass().getResource("/Views/ReminderCustomType/reminderViewDateCalendar.fxml"));
            sub_pane.getChildren().add(datePane);
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("Exception error 3");
        }
    }

    @FXML
    private void loadEndButton_After(){
        try {
            sub_menu.setText("After");
            sub_pane.getChildren().remove(datePane);
            iterationPane = FXMLLoader.load(getClass().getResource("/Views/ReminderCustomType/reminderViewNumOfIterations.fxml"));
            sub_pane.getChildren().add(iterationPane);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception error 4");
        }
    }

    @FXML
    private void createCustomRepeatType() {
        try {
        System.out.println("key is pressed");
        String dateStr ="";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            dateStr = datePick.getValue().format(formatter);
            ReminderController.customReminder=  new RepeatType(Integer.valueOf(spinner.getValue().toString()),menu.getText(),sub_menu.getText(),dateStr,Integer.valueOf(iterationNum.getText()),dateOfWeekBool,dateOfMonthInt);

        } catch(Exception e){
            e.printStackTrace();
        }
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void exit(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
