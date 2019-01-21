package Controllers;

import Models.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;
import Controllers.DataLoad;

public class AllEventsController {
    @FXML private TableColumn eventCol;
    @FXML private TableColumn ownerCol;
    @FXML private TableColumn timeCol;
    @FXML private TableView<Event> eventTable;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.eventCol.setCellValueFactory(new PropertyValueFactory<Event,String>("title"));
        this.timeCol.setCellValueFactory(new PropertyValueFactory<Event,String>("startTime"));
        this.ownerCol.setCellValueFactory(new PropertyValueFactory<Event,String>("owner"));
        eventTable.setItems(getEvent());
    }

    private ObservableList<Event> getEvent(){
        ObservableList<Event> data = FXCollections.observableArrayList();
        return data;
    }
    private void addEvent(){
         int lastEntry = DataLoad.eventList.size();
         Event entry = DataLoad.eventList.get(lastEntry);
         eventTable.getItems().add(entry);
    }
}
