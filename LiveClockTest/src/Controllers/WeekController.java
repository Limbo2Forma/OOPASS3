package Controllers;

import Models.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class WeekController {
    public ArrayList<TimePane> allTimes = new ArrayList<>(168);
    private LocalDate currentSun;
    @FXML public GridPane weekDay;
    @FXML public GridPane gridDays;
    @FXML private Label monthYear;
    public String[] timeValue = {"12 AM","1 AM","2 AM","3 AM","4 AM","5 AM","6 AM","7 AM","8 AM","9 AM",
            "10 AM","11 AM","12 PM","1 PM","2 PM","3 PM","4 PM","5 PM","6 PM","7 PM","8 PM","9 PM", "10 PM","11 PM"};

    public class TimePane extends Pane {
        private LocalDateTime date;
        private boolean clicked;
        public TimePane(Node... children) {
            super(children);
            this.setOnMouseClicked(e -> setHighLight());
        }
        private void setHighLight(){
            this.clicked = true;
            refresh();
        }
        public void setDate(LocalDateTime date){
            this.date = date;
        }
        public boolean getClicked(){ return this.clicked; }
        public void setClicked(boolean bool){ this.clicked = bool; }
    }

    @FXML
    public void initialize() {
        LocalDate day = LocalDate.now();
        while (!day.getDayOfWeek().toString().equals("SUNDAY")) {
            day = day.minusDays(1);
        }
        for (int i = 1; i < 8; i++) {
            for (int j = 0; j < 24; j++) {
                TimePane time = new TimePane();
                time.setPrefSize(100,80);
                time.setId((i - 1) + "o" + j);
                gridDays.add(time,i,j);
                allTimes.add(time);
            }
        }
        for (TimePane time: allTimes){
            System.out.println(time.getId());
        }
        currentSun = day;
        for (int i = 0; i < 7; i++) {
            populateDay(currentSun.plusDays(i), i);
        }
        populateMonth(currentSun);
        refresh();
    }
    //Edit time string
    private String editString(String str){
        String str2 = "";
        StringBuilder sb = new StringBuilder(str);
        sb.insert(2," ");
        for (int i = sb.length()-1; i >= 0; i-- ){
            str2 = str2 + sb.charAt(i);
        }
        return str2;
    }
    public void setColor(int col, int row){
        gridDays.getChildren().get(col * 24 + row).setStyle("-fx-background-color: blue");
        System.out.println("colored");
    }
    //Highlight when mouse clicked
    public void refresh() {
        for (TimePane time: allTimes) {
            while (time.getChildren().size() != 0) {
                time.getChildren().remove(0);
            }
            time.setStyle("-fx-background-color: #ffffff");
            time.setStyle("-fx-border-color: #000000");
            time.setStyle("-fx-border-width: 1px");
            String[] str = time.getId().split("o");
            String times = editString(gridDays.getChildren().get(Integer.parseInt(str[1])).getId())+ " "
                    +weekDay.getChildren().get(Integer.parseInt(str[0])).getId();
            LocalDateTime date = LocalDateTime.parse(times,DateTimeFormatter.ofPattern("hh a dd/MM/yyyy"));
            String names = "";
            for (Event event: DataLoad.eventList) {
                LocalDateTime eventTime = LocalDateTime.parse(event.getStartTime(), DateTimeFormatter.ofPattern("hh:mm a dd/MM/yyyy"));
                if (date.isEqual(eventTime.withMinute(0))){
                    names = names + event.getTitle() + "\n";
                }
            }
            Label label = new Label(names);
            label.setLayoutX(3.0);
            if (time.getClicked()){
                label.setTextFill(Color.WHITE);
                time.setStyle("-fx-background-color: #6699ff");
                time.setClicked(false);
            }
            time.getChildren().add(label);
        }
    }

    private Label dayLabel(String str){
        Label label = new Label(str);
        label.setMinWidth(97.0);
        label.setMaxWidth(97.0);
        label.setMinHeight(47.0);
        label.setMaxHeight(47.0);
        label.setAlignment(Pos.CENTER);
        return label;
    }

    //Populating days
    private void populateDay(LocalDate day, int j){
        Label label = dayLabel("" + day.getDayOfMonth());
        label.setId(day.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        if (day.isEqual(LocalDate.now())){
            label.setTextFill(Color.RED);
        }
        weekDay.add(label,j,0);
    }

    //Populating month
    private void populateMonth(LocalDate thisSun){
        weekDay.getChildren().clear();
        String setMonthYear = thisSun.getMonth() + " " + thisSun.getYear();
        monthYear.setText(setMonthYear);
        for (int i = 0; i < 7; i++) {
            populateDay(thisSun.plusDays(i),i);
            System.out.println(thisSun.plusDays(i).getDayOfMonth());
        }
    }

    //Go to next week
    @FXML
    private void nextWeek(){
        currentSun = currentSun.plusWeeks(1);
        populateMonth(currentSun);
    }
    //Return previous week
    @FXML
    private void lastWeek(){
        currentSun = currentSun.minusWeeks(1);
        populateMonth(currentSun);
    }
}
