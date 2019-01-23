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
            if (DataLoad.user == null) {
                DataLoad.user = new User(name.getText(),email,pass.getText());
                System.out.println(DataLoad.user.name + " " + DataLoad.user.mail + " " + DataLoad.user.pass);
            } else {
                DataLoad.user.setName(name.getText());
                DataLoad.user.setMail(email);
                DataLoad.user.setPass(pass.getText());
            }
        } else {
            noti.sendNotification("Invalid user input","Invalid email",false);
        }
    }
}
