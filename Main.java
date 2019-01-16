import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Main {
    private static Session sessionMail(){

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("namvu6799@gmail.com", "nam671999");
                    }
                });
        return  session;
    }

    private static void sendEmail(String receiver,String subject, String content){
        try {

            Message message = new MimeMessage(sessionMail());
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receiver));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public static  void main(String[] args) {
        sendEmail("nam671999hai@gmail.com","Test mail 2","Test mail sent again");
        sendEmail("s3695302@rmit.edu.vn","Test mail 3","Test mail sent again");
    }
}