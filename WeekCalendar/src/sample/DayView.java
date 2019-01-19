package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DayView implements Initializable {
    private LocalDate currentSun, currentMon, currentTue, currentWed, currentThu, currentFri, currentSat;
    @FXML    private StackPane month;
    @FXML    private StackPane year;
    @FXML    private GridPane weekDay;
    @FXML    private GridPane gridDays;
    private int clickedCol, clickedRow;

    public void initialize(URL url, ResourceBundle rb) {
        LocalDate day = LocalDate.now();
        while (!day.getDayOfWeek().toString().equals("SUNDAY")) {
            day = day.minusDays(1);
        }
        currentSun = day;
        for (int i = 0; i < 7; i++) {
            populateDay(currentSun.plusDays(i), i);
        }
        populateMonth(currentSun);
    }

    //Edit time string
    private String editString(String str){
        String str2 = "";
        StringBuilder sb = new StringBuilder(str);
        sb.insert(2," ");
        sb.toString();
        for (int i = sb.length()-1; i >= 0; i-- ){
            str2 = str2 + sb.charAt(i);
        }
        return str2;
    }

    //Highlight when mouse clicked
    @FXML
    private void mouseClick(MouseEvent e) {
        gridDays.getChildren().get(clickedCol * 24 + clickedRow).setStyle("-fx-background-color: null");
        Node source = (Node) e.getSource();
        Integer colIndex = (gridDays.getColumnIndex(source) == null) ? 0 : (GridPane.getColumnIndex(source));
        Integer rowIndex = (gridDays.getRowIndex(source) == null) ? 0 : (GridPane.getRowIndex(source));
        System.out.println(colIndex + " " + rowIndex);
        gridDays.getChildren().get(colIndex * 24 + rowIndex).setStyle("-fx-background-color: blue");
        String str = gridDays.getChildren().get(rowIndex).getId();
        int i = colIndex - 1;
        System.out.println(currentSun.plusDays(i));
        System.out.println(editString(str));
        clickedRow = rowIndex;
        clickedCol = colIndex;
    }

    //Populating days
    private void populateDay(LocalDate day, int j){
        Text label = new Text("" + day.getDayOfMonth());
        if (day.isEqual(LocalDate.now())){
            label.setFill(Color.RED);
        }
        StackPane pane = new StackPane();
        pane.getChildren().add(label);
        weekDay.add(pane,j,0);
    }
    //Populating month
    private void populateMonth(LocalDate thisSun){
        Text label1 = new Text(""+thisSun.getMonth());
        Text label2 = new Text(""+thisSun.getYear());
        month.getChildren().add(label1);
        year.getChildren().add(label2);
    }

    //Go to next week
    @FXML
    private void nextWeek(){
        gridDays.getChildren().get(clickedCol * 24 + clickedRow).setStyle("-fx-background-color: null");
        weekDay.getChildren().clear();
        month.getChildren().clear();
        currentSun = currentSun.plusWeeks(1);
        for (int i = 0; i < 7; i++) {
            populateDay(currentSun.plusDays(i),i);
        }
        populateMonth(currentSun);
    }
    //Return previous week
    @FXML
    private void lastWeek(){
        gridDays.getChildren().get(clickedCol * 24 + clickedRow).setStyle("-fx-background-color: null");
        weekDay.getChildren().clear();
        month.getChildren().clear();
        currentSun = currentSun.minusWeeks(1);
        for (int i = 0; i < 7; i++) {
            populateDay(currentSun.plusDays(i),i);
        }
        populateMonth(currentSun);
    }
}