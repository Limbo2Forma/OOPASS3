package Controllers;

import Models.Notification;
import Models.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class SettingController {
    @FXML TextField name;
    @FXML TextField mail;
    @FXML TextField pass;

    @FXML
    private void takeUser(){
        Notification noti = new Notification();
        String email = mail.getText();
        if (noti.checkMultiEmail(email)) {
            User user = new User(name.getText(), mail.getText(), pass.getText());
            System.out.println(user.name + " " + user.mail + " " + user.pass);
        } else {
            noti.sendNotification("Invalid user input","Invalid email",false);
        }
    }
}
