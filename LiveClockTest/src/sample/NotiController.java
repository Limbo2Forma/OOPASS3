package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class NotiController {
    @FXML private Label errorMsg;

    @FXML private Button closeMsg;

    public void setMsg(String s){
        errorMsg.setText(s);
    }

    @FXML
    void initialize(){}
    public void closeMessage(){
        Stage stage = (Stage) closeMsg.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
