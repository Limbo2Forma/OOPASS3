package Controllers;

import Models.DatePickerFormat;
import Models.Event;
import Models.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainMenu {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a dd/MM/yyyy");
    private String errorMessage = "";
    private Notification notification = new Notification();
    private int number;
    private boolean isAdd;
    private DatePickerFormat dpf = new DatePickerFormat();

    @FXML private TabPane tabPane;

    @FXML private Pane mainPane;
    @FXML private MenuButton menu;
    @FXML private Tab detailEventTab,eventTab,reminderTab,detailReminder;

    @FXML private TableColumn eventCol;
    @FXML private TableColumn ownerCol;
    @FXML private TableColumn timeCol;
    @FXML public TableView<Event> eventTable;
    @FXML private Button addEvent;

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
    @FXML private Button updateAndAdd;
    @FXML private Button deleteEvent;

    @FXML
    public void initialize() throws Exception {
        menu.setText("Weekly");
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/Views/weekCalendar.fxml")));
        initializeEventTable();
        refreshEventTable();
    }

    public void initializeEventTable(){
        this.eventCol.setCellValueFactory(new PropertyValueFactory<Event,String>("title"));
        this.timeCol.setCellValueFactory(new PropertyValueFactory<Event,String>("startTime"));
        this.ownerCol.setCellValueFactory(new PropertyValueFactory<Event,String>("owner"));
        eventTable.setItems(getEvent());
        dpf.setDatePickerFormat(startDate);
        dpf.setDatePickerFormat(endDate);
    }

    private void loadDetailEvent(){
        updateAndAdd.setDisable(false);
        if (isAdd){
            eventTitle.setText("");
            owner.setText("");
            email.setText("");
            locate.setText("");
            description.setText("");
            notiMin.setText("0");
            startHrs.setValue("");
            endHrs.setValue("");
            startAmPm.setValue("");
            endAmPm.setValue("");
            startDate.setValue(null);
            endDate.setValue(null);
        } else {
            Event event = DataLoad.eventList.get(number);
            String[] start = event.getStartTime().split(" ");
            String[] end = event.getStartTime().split(" ");
            eventTitle.setText(event.getTitle());
            owner.setText(event.getOwner());
            email.setText(event.getGuestList());
            locate.setText(event.getLocation());
            description.setText(event.getDescription());
            notiMin.setText(event.getNotiTime() + "");
            startHrs.setValue(start[0]);
            endHrs.setValue(end[0]);
            startAmPm.setValue(start[1]);
            endAmPm.setValue(end[1]);
            startDate.setValue(LocalDate.parse(start[2],DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            endDate.setValue(LocalDate.parse(end[2],DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
    }

    @FXML private void showSetting()throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/setting.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Setting");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void showMonth() throws Exception{
        mainPane.getChildren().clear();
        menu.setText("Monthly");
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/Views/monthCalendar.fxml")));
    }
    @FXML
    private void showWeek() throws Exception{
        mainPane.getChildren().clear();
        menu.setText("Weekly");
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/Views/weekCalendar.fxml")));
    }

    private ObservableList<Event> getEvent(){ return FXCollections.observableArrayList(); }

    public void refreshEventTable(){
        eventTable.getItems().clear();
        for (Event event: DataLoad.eventList) {
            eventTable.getItems().add(event);
        }
    }

    public void deleteEventAll(){
        ObservableList<Event> selectedEvents, allEvents;
        allEvents = eventTable.getItems();
        selectedEvents = eventTable.getSelectionModel().getSelectedItems();

        for (Event event : selectedEvents){
            int i = eventTable.getSelectionModel().getFocusedIndex();
            allEvents.remove(event);
            System.out.println(i);
            System.out.println(DataLoad.eventList.get(i).getTitle());
            DataLoad.eventList.remove(i);
            DataLoad.eventList.trimToSize();
        }
    }

    @FXML
    private void updateEventAll(){
        number = eventTable.getSelectionModel().getFocusedIndex();
        System.out.println(number);
        updateAndAdd.setText("Update");
        detailEventTab.setDisable(false);
        eventTab.setDisable(true);
        reminderTab.setDisable(true);
        tabPane.getSelectionModel().select(1);
        isAdd = false;
        deleteEvent.setText("Delete");
        loadDetailEvent();
    }

    @FXML
    private void addEventAll(){
        updateAndAdd.setText("Add");
        detailEventTab.setDisable(false);
        eventTab.setDisable(true);
        reminderTab.setDisable(true);
        tabPane.getSelectionModel().select(1);
        isAdd = true;
        deleteEvent.setText("Cancel");
        loadDetailEvent();
    }

    @FXML
    private void updateAndAddEvent(){     //register Event into ArrayList
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
            updateAndAdd.setDisable(true);
            if (isAdd){
                DataLoad.eventList.add(new Event(title,startTime,endTime,owner,location,notiTime,guestList,describe));
                notification.sendNotification("Event Updated","Event " + title + " created successfully",false);
            } else {
                DataLoad.eventList.get(number).setTitle(title);
                DataLoad.eventList.get(number).setOwner(owner);
                DataLoad.eventList.get(number).setStartTime(startTime);
                DataLoad.eventList.get(number).setEndTime(endTime);
                DataLoad.eventList.get(number).setLocation(location);
                DataLoad.eventList.get(number).setGuestList(guestList);
                DataLoad.eventList.get(number).setDescription(describe);
                DataLoad.eventList.get(number).setNotiTime(notiTime);
                DataLoad.eventList.get(number).setNotifyTime();
                notification.sendNotification("Event Updated","Event " + title + " update successfully",false);
            }
            detailEventTab.setDisable(true);
            eventTab.setDisable(false);
            reminderTab.setDisable(false);
            tabPane.getSelectionModel().select(0);
            refreshEventTable();
        } else {
            //send pop up notification error message
            System.out.println(errorMessage);
            notification.sendNotification("Error input",errorMessage,false);
            errorMessage = "";
        }
    }

    @FXML
    public void deleteEvent(){
        if (!isAdd){
            DataLoad.eventList.remove(number);
            DataLoad.eventList.trimToSize();
        }
        detailEventTab.setDisable(true);
        eventTab.setDisable(false);
        reminderTab.setDisable(false);
        tabPane.getSelectionModel().select(0);
        refreshEventTable();
    }
}

