package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Controller {
    Notification noti = new Notification();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a dd/MM/yyyy");
    private String errorMessage = "";
    private int count = 0;
    private Main popUp;
    private ArrayList<Event> list = new ArrayList<Event>();

    @FXML
    private Label time;

    @FXML
    private ComboBox startHrs;
    @FXML
    private ComboBox startAmPm;

    @FXML
    private ComboBox endHrs;
    @FXML
    private ComboBox endAmPm;
    @FXML
    private DatePicker endDate;

    @FXML
    private TextArea description;
    @FXML
    private TextArea locate;

    @FXML
    private TextField eventTitle;
    @FXML
    private TextField owner;
    @FXML
    private TextField notiMin;

    @FXML
    private TextArea email;

    @FXML
    public void setEvent(){     //register Event into ArrayList
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime currentTime = LocalDateTime.now().withSecond(0).withNano(0);
        //get event start time, default date is today (as we choose the date to start event)
        String start = startHrs.getValue() + " " + startAmPm.getValue() + " " + currentTime.format(formatter);
        LocalDateTime startTime = null;
        try {
             startTime = LocalDateTime.parse(start, this.formatter);
        } catch (Exception e){      //error msg if wrong format input or no input
            this.errorMessage = this.errorMessage + "Invalid start time input\n" +
                    "Please check if hours input are correct, include AM/PM.\n\n";
        }
        //get event title
        String title = eventTitle.getText();
        if (title.equals("")){      //error msg if blank
            this.errorMessage = this.errorMessage + "Missing Event Title\n\n";
        }
        //get event owner
        String owner = this.owner.getText();
        if (title.equals("")){      //error msg if blank
            this.errorMessage = this.errorMessage + "Missing Owner\n\n";
        }
        //get event location, if user let blank, fill as none
        String location = locate.getText();
        if (location.equals("")){
            location = "None";
        }
        //get event description, if user let blank, fill as none
        String describe = description.getText();
        if (describe.equals("")){
            describe = "None";
        }
        //get notification time before event
        int notiTime = 0;
        try {
            notiTime = Integer.parseInt(notiMin.getText());
        } catch (NumberFormatException e){
            this.errorMessage = this.errorMessage + "Invalid notification minute format.\n\n";
        }
        //get end event time
        String endTime = "";
        System.out.println(endTime);
        try{
            endTime = endHrs.getValue() + " " + endAmPm.getValue() + " "
                    + endDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDateTime end = LocalDateTime.parse(endTime, this.formatter);
            if (end.isBefore(startTime)){   //error msg if end date is sooner than start date
                this.errorMessage = this.errorMessage + "Invalid end time input\n" +
                        "It should after start date, duh.\n\n";
            }

        } catch (Exception e){  //error msg if wrong format input or no input
            this.errorMessage = this.errorMessage + "Invalid end time input\n" +
                    "Please check if hours input are correct, include AM/PM.\n\n";
        }
        String guestList = email.getText();  //not have yet currently, add owner mail into the list also
        if (!noti.checkMultiEmail(guestList) && !guestList.equals("")){
            this.errorMessage = this.errorMessage + "Invalid email address.\n\n";
        }

        if (this.errorMessage.equals("")){  //print out event created and add created event to arrayList
            addToSort(new Event(title,startTime,endTime,owner,location,notiTime,guestList,describe));
        } else {
            sendError();
        }
    }

    private void addToSort(Event event){
        if (list.isEmpty()){
            list.add(event);
        } else {
            int i = list.size();
            LocalDateTime eventTime = event.getNotifyTime();
            while (i > 0 && list.get(i - 1).getNotifyTime().isAfter(eventTime)){
                i--;
            }
            list.add(i,event);
        }
    }

    @FXML
    private void sendError(){   //Placeholder print to console, will change to pop up later;
        System.out.println(errorMessage);
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("popup.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Error Input");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception exception){

        }
        errorMessage = "";
    }

    @FXML
    private void closePopUp(){
        popUp.popUp.hide();
    }

    private void setDatePickerFormat(){     //set date format
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        endDate.setConverter(converter);
        endDate.setPromptText("dd/MM/yyyy");
    }

    @FXML
    public void initialize() {
        setDatePickerFormat();
        //use timeline to loop after 1 second
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalDateTime currentTime = LocalDateTime.now().withSecond(0).withNano(0);
            if (!list.isEmpty()){
                int length = list.size();
                for (int i = 0; i < length; i++){
                    Event event = list.get(i);
                    String recipient = event.getGuestList();
                    if (!recipient.equals("None")) {
                        noti.sendEmail(recipient, event.composeSubject(), event.composeMessage());
                    }
                }
                list.trimToSize();
            }
            time.setText(currentTime.format(this.formatter));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}
