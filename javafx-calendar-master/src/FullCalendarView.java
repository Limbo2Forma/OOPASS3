import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class FullCalendarView {

    private ArrayList<Day> allCalendarDays = new ArrayList<>(35);
    private VBox view;
    private Text calendarTitle = new Text();
    private YearMonth currentYearMonth;
    public LocalDate chooseDate = LocalDate.now();

    //Create a calendar view
    //@param yearMonth year month to create the calendar of

    // Create calendarTitle and buttons to change current month
    private HBox title(){
        Button previousMonth = new Button("<<");
        previousMonth.setOnAction(e -> previousMonth());
        Button nextMonth = new Button(">>");
        nextMonth.setOnAction(e -> nextMonth());
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);
        HBox titleBar = new HBox(previousMonth,region1,calendarTitle,region2,nextMonth);

        return titleBar;
    }

    // Days of the week labels
    private GridPane dayLabel(){
        Text[] dayNames = new Text[]{ new Text("Sun"), new Text("Mon"), new Text("Tue"),
                new Text("Wed"), new Text("Thu"), new Text("Fri"),
                new Text("Sat") };
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(400);
        byte col = 0;
        for (Text txt : dayNames) {
            StackPane day = new StackPane();
            day.setPrefSize(200, 40);
            Font f = txt.getFont();
            txt.setFont(new Font(f.getName(), 30));
            day.getChildren().add(txt);
            dayLabels.add(day, col++, 0);
        }
        return dayLabels;
    }

    //create calendar view
    public FullCalendarView(YearMonth yearMonth) {
        currentYearMonth = yearMonth;
        // Create the calendar grid pane
        GridPane calendar = new GridPane();
        calendar.setPrefSize(600, 400);
        calendar.setGridLinesVisible(true);
        // Create rows and columns with anchor panes for the calendar
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                Day day = new Day();
                day.setPrefSize(150,150);
                calendar.add(day,j,i);
                allCalendarDays.add(day);
            }
        }
        populateCalendar(yearMonth);

        GridPane daylabels = dayLabel();
        HBox titleBar = title();
        // Create the calendar view
        view = new VBox(titleBar, daylabels, calendar);
    }

        //Set the days of the calendar to correspond to the appropriate date
        //@param yearMonth year and month of month to render

    private void populateCalendar(YearMonth yearMonth) {
        // Get the date we want to start with on the calendar
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        // Dial back the day until it is SUNDAY (unless the month starts on a sunday)
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY") ) {
            calendarDate = calendarDate.minusDays(1);
        }
        // Populate the calendar with day numbers
        for (Day day : allCalendarDays) {
            if (day.getChildren().size() != 0) {
                day.getChildren().remove(0);
            }

            Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            Font f = txt.getFont();
            txt.setFont(new Font(f.getName(), 30));
            day.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY, Insets.EMPTY)));
            if (calendarDate.isEqual(LocalDate.now())){
                txt.setFill(Color.rgb(60,90,255));
            }
            if (day.getClicked()){
                day.setBackground(new Background(new BackgroundFill(Color.rgb(60,90,255),
                        CornerRadii.EMPTY, Insets.EMPTY)));
                txt.setFill(Color.WHITE);
            }
            day.setClicked(false);

            day.setDate(calendarDate);
            day.getChildren().add(txt);
            day.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            calendarDate = calendarDate.plusDays(1);
        }
        // Change the title of the calendar
        this.calendarTitle.setText(yearMonth.getMonth().toString() + " " + yearMonth.getYear());
    }


    // Move the month back by one. Repopulate the calendar with the correct dates.
    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        populateCalendar(currentYearMonth);
    }


    // Move the month forward by one. Repopulate the calendar with the correct dates.
    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        populateCalendar(currentYearMonth);
    }

    public VBox getView() {
        return view;
    }

    public void sendSignal(LocalDate date){
        //send
        date.toString();
    }

    public ArrayList<Day> getAllCalendarDays() {
        return allCalendarDays;
    }

    public void setAllCalendarDays(ArrayList<Day> allCalendarDays) {
        this.allCalendarDays = allCalendarDays;
    }

    private class Day extends StackPane {

        // Date associated with this pane
        private LocalDate date;
        private boolean clicked = false;

        //Create a anchor pane node. Date is not assigned in the constructor.
        //@param children children of the anchor pane

        public void setClicked(boolean b){
            this.clicked = b;
        }
        public boolean getClicked(){
            return this.clicked;
        }

        public Day(Node... children) {
            super(children);
            // Add action handler for mouse clicked
            this.setOnMouseClicked(e -> {
                setClicked(true);
                populateCalendar(currentYearMonth);
                sendSignal(this.date);
                chooseDate = this.date;
            });
        }

        public LocalDate getDate() {
            return this.date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }
    }
}
