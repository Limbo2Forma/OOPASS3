import Controllers.DataLoad;

import Models.Event;
import Models.Notification;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main extends Application {
    private Notification notification = new Notification();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a dd/MM/yyyy");
    private static Stage primaryStage = new Stage();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/weekView.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Week Calendar");
        Platform.setImplicitExit(false);
        DataLoad.loadAlldata();
        primaryStage.show();

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            checkEvent();
        }),
                new KeyFrame(Duration.seconds(20))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        primaryStage.setOnCloseRequest((WindowEvent event1) -> {
            //DataLoad.serializeEvent();
        });
    }

    private void checkEvent() {
        LocalDateTime currentTime = LocalDateTime.now().withSecond(0).withNano(0);
        if (!DataLoad.eventList.isEmpty()){
            for (Event event: DataLoad.eventList){
                if (LocalDateTime.parse(event.getNotifyTime(), this.formatter).isEqual(currentTime)){
                    String recipient = event.getGuestList();
                    if (!event.isSentStatus()) {
                        String title = "Notify event " + event.getTitle();
                        String message = event.composeSubject() + "\n\n"+ event.composeMessage();
                        if (!recipient.equals("None")) {
                            notification.sendEmail(recipient, event.composeSubject(), event.composeMessage());
                        }
                        notification.sendNotification(title,message,true);
                        event.setSentStatus(true);
                    }
                }
            }
        }
    }
}