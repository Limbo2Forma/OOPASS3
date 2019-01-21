package Controllers;

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
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventController {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a dd/MM/yyyy");
    private String errorMessage = "";
    private Notification notification = new Notification();

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

    //set DatePicker to unable to pick previous day than today
    private Callback<DatePicker, DateCell> getDayCellFactory() {
        return new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now())){
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
    }

    @FXML
    public void setEvent(){     //register Event into ArrayList
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
        if (guestList.equals("")){
            guestList = "None";
        }

        if (this.errorMessage.equals("")){  //print out event created and add created event to arrayList
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

    //set DatePicker formatting to dd/MM/yyyy
    private void setDatePickerFormat(DatePicker dp){
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
        dp.setConverter(converter);
        dp.setPromptText("dd/MM/yyyy");

        Callback<DatePicker, DateCell> dayCellFactory = this.getDayCellFactory();
        dp.setDayCellFactory(dayCellFactory);
    }

    @FXML
    public void initialize() {
        setDatePickerFormat(startDate);
        setDatePickerFormat(endDate);
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
