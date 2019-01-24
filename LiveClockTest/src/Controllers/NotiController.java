package Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class NotiController {
    @FXML private Label errorMsg;

    @FXML private Button closeMsg;
    @FXML public Button stop;

    public void setMsg(String s){
        errorMsg.setText(s);
    }

    @FXML
    void initialize(){
        stop.setVisible(false);
    }

    public void closeMessage(){
        Stage stage = (Stage) closeMsg.getScene().getWindow();
        stage.close();
    }

    public void stopProgram(){
        DataLoad.saveAllData();
        Platform.exit();
    }
}
