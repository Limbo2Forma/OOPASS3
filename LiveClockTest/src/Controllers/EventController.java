package Controllers;

import Models.DatePickerFormat;
import Models.Event;
import Models.Notification;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventController {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a dd/MM/yyyy");
    private String errorMessage = "";
    private Notification notification = new Notification();
    private DatePickerFormat dpf = new DatePickerFormat();

    @FXML private Label time;

    @FXML private ComboBox startHrs;
    @FXML private ComboBox startAmPm;
    @FXML private DatePicker startDate;

    @FXML private ComboBox endHrs;
    @FXML private ComboBox endAmPm;
    @FXML private DatePicker endDate;

    @FXML private TextArea description;
    @FXML private TextArea locate;

    @FXML private TextField eventTitle;
    @FXML private TextField owner;
    @FXML private TextField notiMin;

    @FXML private TextArea email;
    @FXML private Button addEvent;
    @FXML private Button closeEvent;

    @FXML
    public void setEvent(){     //register Event into ArrayList
        //get event title
        String title = eventTitle.getText();
        if (title.equals("")){      //error msg if blank
            this.errorMessage = this.errorMessage + "Missing Event Title\n\n";
        }
        //get event owner
        String owner = this.owner.getText();
        if (owner.equals("")){      //error msg if blank
            owner = DataLoad.user.name;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //get event start time, default date is today (as we choose the date to start event)
        String startTime = "";
        LocalDateTime start = null;
        try {
            startTime = startHrs.getValue() + " " + startAmPm.getValue()
                    + " " + startDate.getValue().format(formatter);
            start = LocalDateTime.parse(startTime, this.formatter);
            if (start.isBefore(LocalDateTime.now().withNano(0).withSecond(0))){
                //error msg if start date is sooner than today
                this.errorMessage = this.errorMessage + "Invalid start time input\n" +
                        "It should start after right now, duh.\n\n";
            }
        } catch (Exception e){      //error msg if wrong format input or no input
            this.errorMessage = this.errorMessage + "Invalid start time input\n" +
                    "Please check if hours input are correct, include AM/PM.\n\n";
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
        String endTime = "";
        try{
            endTime = endHrs.getValue() + " " + endAmPm.getValue() + " "
                    + endDate.getValue().format(formatter);
            LocalDateTime end = LocalDateTime.parse(endTime, this.formatter);
            if (end.isBefore(start)){   //error msg if end date is sooner than start date
                this.errorMessage = this.errorMessage + "Invalid end time input\n" +
                        "It should after start date, duh.\n\n";
            }

        } catch (Exception e){  //error msg if wrong format input or no input
            this.errorMessage = this.errorMessage + "Invalid end time input\n" +
                    "Please check if hours input are correct, include AM/PM.\n\n";
        }
        String guestList = email.getText();  //not have yet currently, add owner mail into the list also
        if (!notification.checkMultiEmail(guestList) && !guestList.equals("")){
            this.errorMessage = this.errorMessage + "Invalid email address(es).\n\n";
        }

        if (this.errorMessage.equals("")){  //print out event created and add created event to arrayList
            addEvent.setDisable(true);
            DataLoad.eventList.add(new Event(title,startTime,endTime,owner,location,notiTime,guestList,describe));
            Stage stage = (Stage) addEvent.getScene().getWindow();
            notification.sendNotification("Event Created","Event " + title + " created successfully",false);
            stage.close();
        } else {
            //send pop up notification error message
            System.out.println(errorMessage);
            notification.sendNotification("Error input",errorMessage,false);
            errorMessage = "";
        }
    }

    @FXML
    public void closeAddEvent(){
        Stage stage = (Stage) closeEvent.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        dpf.setDatePickerFormat(startDate);
        dpf.setDatePickerFormat(endDate);
        addEvent.setDisable(false);
        //use timeline to loop after 1 second
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            time.setText(LocalDateTime.now().withSecond(0).withNano(0).format(this.formatter));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}
