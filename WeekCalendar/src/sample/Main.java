package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class Main extends Application {

    private static Stage stage;
    private static Pane layout;

    @Override
    public void start(Stage stage) throws Exception{
        this.stage = stage;
        stage.setTitle("Week Calendar");
        showLayout();
    }

    private void showLayout() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("layout.fxml"));
        layout = loader.load();
        stage.setScene(new Scene(layout,800,600));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
