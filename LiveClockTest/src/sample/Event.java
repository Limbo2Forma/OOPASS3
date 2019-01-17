package sample;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event {

    private String title;
    private LocalDateTime startTime;
    private String endTime;
    private int notiTime;
    private LocalDateTime notifyTime;
    private String owner;
    private String location;
    private String guestList;
    private String description;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a dd/MM/yyyy");

    public String getGuestList() {
        return guestList;
    }
    public LocalDateTime getNotifyTime() {
        return notifyTime;
    }

    Event(String title, LocalDateTime startTime, String endTime, String owner,
          String location, int notiTime, String guestList, String description ){
            this.title = title;
            this.startTime = startTime;
            this.endTime = endTime;
            this.owner = owner;
            this.location = location;
            this.guestList = guestList;
            this.description = description;
            this.notiTime = notiTime;

            this.notifyTime = startTime.minusMinutes(notiTime);
    }
    private String composeSubject(){
        return title + " start at " + startTime.format(formatter) + " end at " + endTime;
    }
    private String composeMessage(){
        return "Event at " + location + " create by " + owner + "\n\n" + description;
    }

}
