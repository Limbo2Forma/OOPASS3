package sample;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Notification {
    private Session session = sessionMail();

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
                });
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
//        sendEmail("s3695424@rmit.edu.vn,s3678436@rmit.edu.vn,s3695336@rmit.edu.vn,s3695275@rmit.edu.vn,s3678505@rmit.edu.vn",
//                "Lời kêu gọi","Tôi nói đồng bào nghe rõ không\n"+"Đừng rep mail này nha");
}