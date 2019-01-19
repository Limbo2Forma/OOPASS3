import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Add Event pop up");
        Button btn = new Button();
        btn.setText("Add Event");
        btn.setOnAction(e ->
                showAddEvent()
        );

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
    public void showAddEvent(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/event.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add Event");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception ioe){
            System.out.println("ioe error");
        }
    }
}