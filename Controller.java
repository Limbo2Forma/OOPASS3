import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.YearMonth;

public class Controller {
    FullCalendarView calendarView = new FullCalendarView(YearMonth.now());
    // Get the pane to put the calendar on
    public VBox view(){
        return calendarView.getView();
    }
    @FXML Pane calendarPane;
}
