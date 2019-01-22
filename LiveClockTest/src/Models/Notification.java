package Models;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Controllers.NotiController;

import java.util.Properties;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Notification { // Pop Up window notification AND send email notification
    private Session session = sessionMail();

    public void sendNotification(String title, String message, boolean isNoti){
        Stage stage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/notifyPopUp.fxml"));
            Parent root1 = fxmlLoader.load();
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.setTitle(title);
            NotiController controller = fxmlLoader.getController();
            controller.setMsg(message);
            controller.stop.setVisible(isNoti);
            controller.stop.setDisable(!isNoti);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (Exception ioe){
            stage.show();
        }
    }

    private Session sessionMail(){

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return  Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("namvu6799@gmail.com", "nam671999");
                    }
                }
                );
    }

    public void sendEmail(String receivers,String subject, String content){
        try {

            Message message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receivers));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkMultiEmail(String emails){
        String[] arr = emails.split(",");
            for (String email: arr) {
                if (!isValidEmailAddress(email)){
                    return false;
                }
            }
        return true;
    }

    private boolean isValidEmailAddress(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}