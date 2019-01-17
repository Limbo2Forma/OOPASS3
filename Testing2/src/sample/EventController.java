package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class EventController implements Initializable{
    @FXML private TextField range;
    @FXML private DatePicker date;
    @FXML private TextField name;
    @FXML private TextField venue;
    @FXML private TextField time;

    @FXML private TableView<Event> tableView;
    @FXML private TableColumn nameCol;
    @FXML private TableColumn dateCol;
    @FXML private TableColumn venueCol;
    @FXML private TableColumn timeCol;


    @FXML
    private void dayAhead(){
        Calendar now = Calendar.getInstance();
        int today = now.get(Calendar.DATE);
        int eventDay = (date.getValue().getDayOfMonth());
        int days = eventDay - today;
        this.range.setText(Integer.toString(days) + " days from the event");

    }

    //ObservableList<Event> data = FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.nameCol.setCellValueFactory(new PropertyValueFactory<Event,String>("name"));
        this.venueCol.setCellValueFactory(new PropertyValueFactory<Event,String>("venue"));
        this.timeCol.setCellValueFactory(new PropertyValueFactory<Event,String>("time"));
        this.dateCol.setCellValueFactory(new PropertyValueFactory<Event,String>("date"));

        tableView.setItems(getEvent());

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public ObservableList<Event> getEvent(){
        ObservableList<Event> data = FXCollections.observableArrayList();
        return data;
    }
    public void addEvent(){
        Event entry = new Event(name.getText(),venue.getText(),time.getText(),date.getValue().toString());
        tableView.getItems().add(entry);
        //System.out.println(entry.eName + entry.eTime + entry.eVenue + entry.eDate);
    }

    public void deleteEvent(){
        ObservableList<Event> selectedEvents, allEvents;
        allEvents = tableView.getItems();
        selectedEvents = tableView.getSelectionModel().getSelectedItems();
        for (Event event : selectedEvents){
            allEvents.remove(event);
        }
    }

    public void printData(){
        ObservableList<Event> allEvents = tableView.getItems();
        for (Event event : allEvents){
            System.out.println(event.getName());
        }
    }



}
