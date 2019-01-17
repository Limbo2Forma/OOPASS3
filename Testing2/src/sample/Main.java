package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage stage;
    private static BorderPane layout;
    @Override
    public void start(Stage stage) throws Exception{
        this.stage = stage;
        stage.setTitle("Testing");
        showSample();
        showDays();

    }

    private void showSample() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("sample.fxml"));
        layout = loader.load();
        stage.setScene(new Scene(layout,800,600));
        stage.show();
    }

    public static void showDays() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("Days.fxml"));
        BorderPane days = loader.load();
        layout.setCenter(days);
    }

    public static void showDay1() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("day1.fxml"));
        BorderPane day1 = loader.load();
        layout.setCenter(day1);
    }

    public static void showDay2() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("day2.fxml"));
        BorderPane day2 = loader.load();
        layout.setCenter(day2);
    }

    public static void showAddStage()throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("event.fxml"));
        BorderPane addEvent = loader.load();

        Stage eventStage = new Stage();
        eventStage.setTitle("Add new event");
        eventStage.initModality(Modality.NONE);
        eventStage.initOwner(stage);
        eventStage.setScene(new Scene(addEvent));
        eventStage.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
