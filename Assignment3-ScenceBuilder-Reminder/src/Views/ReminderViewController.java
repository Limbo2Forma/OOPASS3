package Views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class ReminderViewController {
    @FXML Pane weekPane, monthlyPane;
    @FXML Pane pane;
    @FXML TextField numRepeat;
    @FXML
    private void loadViewWeek(){
        try {
            pane.getChildren().remove(monthlyPane);
            pane.getChildren().remove(weekPane);
            weekPane = FXMLLoader.load(getClass().getResource("reminderViewWeekly.fxml"));
            pane.getChildren().add(weekPane);
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("exeception error");
        }
    }
    @FXML
    private void loadViewMonth(){
        try {
            pane.getChildren().remove(weekPane);
            pane.getChildren().remove(monthlyPane);
            monthlyPane = FXMLLoader.load(getClass().getResource("reminderViewMonthly.fxml"));
            pane.getChildren().add(monthlyPane);
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("exeception error");
        }
    }

    private EventHandler<KeyEvent> keyListener = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            int val = 0;
            try {
                val = Integer.parseInt(numRepeat.getText());
            } catch (NumberFormatException ne){
                System.out.println("Invalid Num input");
            }
            if(event.getCode() == KeyCode.UP ) {
                numRepeat.setText(""+(val+1));
            }
            if(event.getCode() == KeyCode.DOWN && val > 0) {
                numRepeat.setText(""+(val-1));
            }
        }
    };
    @FXML
    private void init(){
        numRepeat.setOnKeyPressed(keyListener);
    }
}
