import java.time.LocalDate;
import java.time.YearMonth;

public class Controller {
    FullCalendarView calendarView = new FullCalendarView(YearMonth.now());
    // Get the pane to put the calendar on
    public void receiverSignal(String str){
        //receive
        System.out.printf(str);
    }
}
