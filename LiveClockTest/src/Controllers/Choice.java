package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Choice {
    @FXML private Pane mainPane;
    @FXML private MenuButton menu;

    @FXML
    public void initialize() throws Exception {
        menu.setText("Weekly");
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/Views/weekCalendar.fxml")));
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
