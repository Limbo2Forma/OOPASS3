import Controllers.DataLoad;

import Models.Event;
import Models.Notification;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDateTime;

public class Main extends Application {
    private Notification notification = new Notification();

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/weekView.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Week Calendar");
        primaryStage.show();

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            checkEvent();
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void checkEvent() {
        LocalDateTime currentTime = LocalDateTime.now().withSecond(0).withNano(0);
        if (!DataLoad.eventList.isEmpty()){
            for (Event event: DataLoad.eventList){
                if (event.getNotifyTime().isEqual(currentTime)){
                    String recipient = event.getGuestList();
                    if (!event.isSentStatus()) {
                        String title = "Notify event " + event.getTitle();
                        String message = event.composeSubject() + "\n\n"+ event.composeMessage();
                        notification.sendNotification(title,message);
                        if (!recipient.equals("None")) {
                            notification.sendEmail(recipient, event.composeSubject(), event.composeMessage());
                        }
                        event.setSentStatus(true);
                    }
                }
            }
        }
    }
}