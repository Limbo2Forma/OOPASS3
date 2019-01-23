package Controllers;

import Models.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.omg.CORBA.CODESET_INCOMPATIBLE;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MonthController implements Initializable {
    private ArrayList<dayPane> allCalendarDays = new ArrayList<>(35);
    private LocalDate currentYearMonth;
    public LocalDate clicked;
    @FXML private Label yearMonth;
    @FXML private GridPane calendar;
    private int clickCol, clickRow;

    public class dayPane extends Pane {
        private LocalDate date;
        private boolean clicked;
        public dayPane(Node... children) {
            super(children);
            this.setOnMouseClicked(e -> setHighLight());
        }
        private void setHighLight(){
            this.clicked = true;
            populateMonth();
        }
        public void setDate(LocalDate date){
            this.date = date;
        }
        public boolean getClicked(){ return this.clicked; }
        public void setClicked(boolean bool){ this.clicked = bool; }
    }

    public void initialize(URL url, ResourceBundle rb){
        LocalDate today = LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),1);
        currentYearMonth = LocalDate.now();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                dayPane day = new dayPane();
                day.setPrefSize(156,103);
                calendar.add(day,j,i);
                allCalendarDays.add(day);
            }
        }
        findFirstSunday(today);
    }
    @FXML
    private void nextMonth(){
        currentYearMonth = currentYearMonth.plusMonths(1);
        populateMonth();
    }
    @FXML
    private void prevMonth(){
        currentYearMonth = currentYearMonth.minusMonths(1);
        populateMonth();
    }
    private void findFirstSunday(LocalDate firstSun){
        while (!firstSun.getDayOfWeek().toString().equals("SUNDAY")) {
            firstSun = firstSun.minusDays(1);
        }
        int i = 0;
        for (dayPane date: allCalendarDays) {
            while (date.getChildren().size() != 0) {
                date.getChildren().remove(0);
            }
            date.setStyle("-fx-background-color: #ffffff");
            date.setStyle("-fx-border-color: #000000");
            date.setStyle("-fx-border-width: 1px");
            LocalDate setDay = firstSun.plusDays(i);
            Label label = dayLabel(setDay);
            Label event = eventLabel(setDay);
            date.setDate(setDay);
            if (date.getClicked()){
                label.setTextFill(Color.WHITE);
                event.setTextFill(Color.WHITE);
                date.setStyle("-fx-background-color: #6699ff");
                date.setClicked(false);
            }
            date.getChildren().addAll(label,event);
            i++;
        }
        yearMonth.setText(currentYearMonth.getMonth() + " " + currentYearMonth.getYear());
    }
    private Label dayLabel(LocalDate date){
        Label day = new Label("" + date.getDayOfMonth());
        day.setLayoutX(5.0);
        day.setLayoutY(5.0);
        day.setFont(Font.font(null, FontWeight.BOLD,15));
        if (date.isEqual(LocalDate.now())){
            day.setTextFill(Color.RED);
        }
        return day;
    }
    private Label eventLabel(LocalDate date){
        String names = "";
        for (Event event: DataLoad.eventList) {
            if (date.isEqual(LocalDate.parse(event.getStartTime(), DateTimeFormatter.ofPattern("hh:mm a dd/MM/yyyy")))){
                names = names + event.getTitle() + "\n";
            }
        }
        Label event = new Label("" + names);
        event.setLayoutX(5.0);
        event.setLayoutY(22.0);
        return event;
    }

    private void populateMonth(){
        LocalDate firstSun = LocalDate.of(currentYearMonth.getYear(),currentYearMonth.getMonth(),1);
        findFirstSunday(firstSun);
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
