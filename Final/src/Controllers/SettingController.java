package Controllers;

import Models.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class SettingController {
    @FXML TextField name;
    @FXML TextField mail;
    @FXML TextField pass;
    @FXML Text passReq;
    @FXML Rectangle rec;

    @FXML
    private void takeUser(){
        User user = new User(name.getText(),mail.getText(),pass.getText());
        System.out.println(user.name + " " + user.mail + " " + user.pass);
    }

    @FXML
    private void showRequirement(){
        passReq.setOpacity(1);
        rec.setOpacity(1);
    }
}
