package Models;

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
    private boolean sentStatus = false;

    public boolean isSentStatus() {
        return sentStatus;
    }

    public void setSentStatus(boolean sentStatus) {
        this.sentStatus = sentStatus;
    }

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a dd/MM/yyyy");

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getNotiTime() {
        return notiTime;
    }

    public void setNotiTime(int notiTime) {
        this.notiTime = notiTime;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGuestList() {
        return guestList;
    }

    public void setGuestList(String guestList) {
        this.guestList = guestList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getNotifyTime() {
        return notifyTime;
    }

    public Event(String title, LocalDateTime startTime, String endTime, String owner,
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
    public String composeSubject(){
        return "Event " + title + " start at " + startTime.format(formatter) + " end at " + endTime;
    }
    public String composeMessage(){

        return "Event at " + location + "\n\nCreate by " + owner + "\n\n" + description;
    }

}
