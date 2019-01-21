package Controllers;

import Models.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Choice {
    @FXML private Pane mainPane;
    @FXML private MenuButton menu;

    @FXML private TableColumn eventCol;
    @FXML private TableColumn ownerCol;
    @FXML private TableColumn timeCol;
    @FXML private TableView<Event> eventTable;

    @FXML
    public void initialize() throws Exception {
        menu.setText("Weekly");
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/Views/weekCalendar.fxml")));
        this.eventCol.setCellValueFactory(new PropertyValueFactory<Event,String>("title"));
        this.timeCol.setCellValueFactory(new PropertyValueFactory<Event,String>("startTime"));
        this.ownerCol.setCellValueFactory(new PropertyValueFactory<Event,String>("owner"));

        eventTable.setItems(getEvent());
        for(Event event:DataLoad.eventList){
            eventTable.getItems().add(event);
        }
    }

    public ObservableList<Event> getEvent(){
        ObservableList<Event> data = FXCollections.observableArrayList();
        return data;
    }

    private void addEvent(){
        int lastEntry = DataLoad.eventList.size();
        Event entry = DataLoad.eventList.get(lastEntry);
        eventTable.getItems().add(entry);
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

    @FXML private void showMonth() throws Exception{
        mainPane.getChildren().clear();
        menu.setText("Monthly");
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/Views/monthCalendar.fxml")));
    }
    @FXML private void showWeek() throws Exception{
        mainPane.getChildren().clear();
        menu.setText("Weekly");
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/Views/weekCalendar.fxml")));
    }
    @FXML
    private void showAddEvent(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/event.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add new event");
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (Exception ioe){
            System.out.println("ioe error");
        }
    }

}
