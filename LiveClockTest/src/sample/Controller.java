package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
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
        String endTime = endHrs.getValue() + " " + endAmPm.getValue() + " "
                + endDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.println(endTime);
        try{
            LocalDateTime end = LocalDateTime.parse(endTime, this.formatter);
            if (end.isBefore(startTime)){   //error msg if end date is sooner than start date
                this.errorMessage = this.errorMessage + "Invalid end time input\n" +
                        "It should after start date, duh.\n\n";
            }

        } catch (Exception e){  //error msg if wrong format input or no input
            this.errorMessage = this.errorMessage + "Invalid end time input\n" +
                    "Please check if hours input are correct, include AM/PM.\n\n";
        }
        String guestList = "";  //not have yet currently

        if (this.errorMessage.equals("")){  //print out event created and add created event to arrayList
            list.add(new Event(title,startTime,endTime,owner,location,notiTime,guestList,describe));
            System.out.println("Event no: " + count + "\n"
                                +"Event create time" + currentTime.toString() + "\n"
                                +"Event title: " + list.get(count).getTitle() + "\n"
                                +"Event startTime: " + list.get(count).getStartTime().toString() + "\n"
                                +"Event endTime: " + list.get(count).getEndTime() + "\n"
                                +"Event Owner: " + list.get(count).getOwner() + "\n"
                                +"Event location: " + list.get(count).getLocation() + "\n"
                                +"Event Notify time: " + list.get(count).getNotifyTime().toString() + "\n"
                                +"Event describe: " + list.get(count).getDescription() + "\n");
            count++;
            System.out.println("all good boi");
        } else {
            sendError();
        }

    }

    private void sendError(){   //Placeholder print to console, will change to pop up later
        System.out.printf(errorMessage);
        errorMessage = "";
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
            try {
                for (int i = 0; i < 10; i++) {  //scan all event stored in arrayList
                    if (list.get(i).getNotifyTime().isEqual(currentTime)){
                        //get an event is noti time, yeah
                        System.out.println("Its time boi " + count);
                        list.remove(i);//remove event after job done
                        count++;
                        i--;    //roll back index by 1 to inspect next element.
                    }
                }
            } catch (Exception error){
                //System.out.println("some error idk");
            }
            time.setText(currentTime.format(this.formatter));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}
