package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MonthController implements Initializable {
    private LocalDate firstSun;
    private LocalDate currentYearMonth;
    @FXML private StackPane month;
    @FXML private StackPane year;
    @FXML private GridPane calendar;
    private int clickCol, clickRow;

    public void initialize(URL url, ResourceBundle rb){
        LocalDate day = LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),1);
        currentYearMonth = LocalDate.now();
        while (!day.getDayOfWeek().toString().equals("SUNDAY")) {
            day = day.minusDays(1);
        }
        firstSun = day;
        for (int i = 0; i < 7; i++){
            for (int j = 0; j < 5; j++){
                populateDay(firstSun.plusDays((7*j)+i),i,j);
            }
        }
        populateMonth(currentYearMonth);
    }
    @FXML
    private void nextMonth(){
        calendar.getChildren().clear();
        month.getChildren().clear();
        year.getChildren().clear();
        currentYearMonth = currentYearMonth.plusMonths(1);
        LocalDate firstSun = LocalDate.of(currentYearMonth.getYear(),currentYearMonth.getMonth(),1);
        while (!firstSun.getDayOfWeek().toString().equals("SUNDAY")) {
            firstSun = firstSun.minusDays(1);
        }
        for (int i = 0; i < 7; i++){
            for (int j = 0; j < 5; j++){
                populateDay(firstSun.plusDays((7*j)+i),i,j);
            }
        }
        populateMonth(currentYearMonth);
    }
    @FXML
    private void prevMonth(){
        calendar.getChildren().clear();
        month.getChildren().clear();
        year.getChildren().clear();
        currentYearMonth = currentYearMonth.minusMonths(1);
        LocalDate firstSun = LocalDate.of(currentYearMonth.getYear(),currentYearMonth.getMonth(),1);
        while (!firstSun.getDayOfWeek().toString().equals("SUNDAY")) {
            firstSun = firstSun.minusDays(1);
        }
        for (int i = 0; i < 7; i++){
            for (int j = 0; j < 5; j++){
                populateDay(firstSun.plusDays((7*j)+i),i,j);
            }
        }
        populateMonth(currentYearMonth);
    }
    private void populateMonth(LocalDate today){
        Text label1 = new Text(""+today.getMonth());
        Text label2 = new Text(""+today.getYear());
        month.getChildren().add(label1);
        year.getChildren().add(label2);
    }
    private void populateDay(LocalDate day, int col, int row){
        Label label1 = new Label(""+day.getDayOfMonth());
        //Node node = calendar.getChildren().get(col*7+row);
        if (day.isEqual(LocalDate.now())){
            label1.setTextFill(Color.RED);
        }
        label1.setFont(Font.font(20));
        label1.setMinWidth(110);
        label1.setMaxWidth(110);
        label1.setAlignment(Pos.CENTER);
        calendar.add(label1,col,row);
    }

    @FXML
    private void mouseClick(MouseEvent e){
        calendar.getChildren().get(clickRow * 7 + clickCol).setStyle("-fx-background-color: null");
        Node source = (Node) e.getSource();
        source.toBack();
        Integer colIndex = (GridPane.getColumnIndex(source) == null) ? 0 : (GridPane.getColumnIndex(source));
        Integer rowIndex = (GridPane.getRowIndex(source) == null) ? 0 : (GridPane.getRowIndex(source));
        System.out.println(colIndex + " " + rowIndex);
        calendar.getChildren().get(colIndex + rowIndex * 7).setStyle("-fx-background-color: rgba(0,0,255,0.62)");
        clickCol = colIndex;
        clickRow = rowIndex;
    }
}
