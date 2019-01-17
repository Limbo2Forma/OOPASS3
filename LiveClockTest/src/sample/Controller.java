package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Controller {
    Notification noti = new Notification();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a dd/MM/yyyy");
    private String errorMessage = "";

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
    private TextField addEmail;

    @FXML
    private ComboBox guestEmail;

    @FXML
    private Button addGuest;
    @FXML
    private Button removeGuest;
    @FXML
    private Button addEvent;

    @FXML
    public void setEvent(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String start = startHrs.getValue() + " " + startAmPm.getValue() + " " + LocalDateTime.now().format(formatter);
        LocalDateTime startTime = null;
        try {
            startTime = LocalDateTime.parse(start, this.formatter);
        } catch (Exception e){
            this.errorMessage = this.errorMessage + "Invalid start time input\n" +
                    "Please check if hours input are correct, include AM/PM.\n\n";
        }

        String title = eventTitle.getText();
        if (title.equals("")){
            this.errorMessage = this.errorMessage + "Missing Event Title\n\n";
        }

        String owner = this.owner.getText();
        if (title.equals("")){
            this.errorMessage = this.errorMessage + "Missing Owner\n\n";
        }

        String location = locate.getText();
        if (location.equals("")){
            location = "None";
        }

        String describe = description.getText();
        if (describe.equals("")){
            describe = "None";
        }

        int notiTime = 0;
        try {
            notiTime = Integer.parseInt(notiMin.getText());
        } catch (NumberFormatException e){
            this.errorMessage = this.errorMessage + "Invalid notification minute format.\n\n";
        }

        String endTime = endHrs.getValue() + " " + endAmPm.getValue() + " "
                + endDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.println(endTime);
        try{
            LocalDateTime end = LocalDateTime.parse(endTime, this.formatter);
        } catch (Exception e){
            this.errorMessage = this.errorMessage + "Invalid start time input\n" +
                    "Please check if hours input are correct, include AM/PM.\n\n";
        }
        String guestList = "";

        if (this.errorMessage.equals("")){
            Event event = new Event(title,startTime,endTime,owner,location,notiTime,guestList,describe);
            System.out.println("all good");
        } else {
            sendError();
        }

    }
    @FXML
    private void removeEmail(){

    }

    private void sendError(){
        //send pop up
        System.out.printf(errorMessage);
        errorMessage = "";
    }

    @FXML
    public void initialize() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalDateTime currentTime = LocalDateTime.now();

            time.setText(currentTime.format(this.formatter));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}
