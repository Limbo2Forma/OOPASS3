package Views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class ReminderViewController {
    @FXML Pane weekPane, monthlyPane, datePane, iterationPane;
    @FXML Pane pane, sub_pane, reminderPane;
    @FXML MenuButton menu, sub_menu, date, week;

    @FXML
    private void loadViewWeek(){
        try {
            menu.setText("Weekly");
            pane.getChildren().remove(monthlyPane);
            pane.getChildren().remove(weekPane);
            weekPane = FXMLLoader.load(getClass().getResource("reminderViewWeekly.fxml"));
            pane.getChildren().add(weekPane);
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("exeception error 1");
        }
    }
    @FXML
    private void loadViewMonth(){
        try {
            menu.setText("Monthly");
            pane.getChildren().remove(weekPane);
            pane.getChildren().remove(monthlyPane);
            monthlyPane = FXMLLoader.load(getClass().getResource("reminderViewMonthly.fxml"));
            pane.getChildren().add(monthlyPane);
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("exeception error 2");
        }
    }

    @FXML
    private void displayMon(){
        date.setText("Monday");
    }
    @FXML
    private void displayTue(){
        date.setText("Tuesday");
    }
    @FXML
    private void displayWed(){
        date.setText("Wednesday");
    }
    @FXML
    private void displayThur(){
        date.setText("Thursday");
    }
    @FXML
    private void displayFri(){
        date.setText("Friday");
    }
    @FXML
    private void displaySat(){
        date.setText("Saturday");
    }
    @FXML
    private void displaySun(){
        date.setText("Sunday");
    }

    @FXML
    private void displayWeek1(){
        week.setText("Week1");
    }
    @FXML
    private void displayWeek2(){
        week.setText("Week2");
    }
    @FXML
    private void displayWeek3(){
        week.setText("Week3");
    }
    @FXML
    private void displayWeek4(){
        week.setText("Week4");
    }
    @FXML
    private void displayWeek5(){
        week.setText("Week5");
    }

    @FXML
    private void loadEndButton_Never() {
        try {
            sub_menu.setText("Never");
            sub_pane.getChildren().remove(datePane);
            sub_pane.getChildren().remove(iterationPane);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception error 4");
        }
    }

    @FXML
    private void loadEndButton_OnDate(){
        try{
            sub_menu.setText("On Date:");
            sub_pane.getChildren().remove(iterationPane);
            datePane = FXMLLoader.load(getClass().getResource("reminderViewDateCalendar.fxml"));
            sub_pane.getChildren().add(datePane);
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("Exception error 3");
        }
    }

    @FXML
    private void loadEndButton_After(){
        try {
            sub_menu.setText("After");
            sub_pane.getChildren().remove(datePane);
            iterationPane = FXMLLoader.load(getClass().getResource("reminderViewNumOfIterations.fxml"));
            sub_pane.getChildren().add(iterationPane);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception error 4");
        }
    }

}
