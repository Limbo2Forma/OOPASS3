package Models;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event implements Serializable {

    private String title;
    private String startTime;
    private String endTime;
    private int notiTime;
    private String notifyTime;
    private String owner;
    private String location;
    private String guestList;
    private String description;
    private boolean sentStatus = false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
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

    public String getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(String notifyTime) {
        this.notifyTime = notifyTime;
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

    public boolean isSentStatus() {
        return sentStatus;
    }

    public void setSentStatus(boolean sentStatus) {
        this.sentStatus = sentStatus;
    }

    public Event(String title, String startTime, String endTime, String owner,
                 String location, int notiTime, String guestList, String description ){
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.owner = owner;
        this.location = location;
        this.guestList = guestList;
        this.description = description;
        this.notiTime = notiTime;

        this.notifyTime = LocalDateTime.parse(startTime,DateTimeFormatter.ofPattern("hh:mm a dd/MM/yyyy")).
                minusMinutes(notiTime).format(DateTimeFormatter.ofPattern("hh:mm a dd/MM/yyyy"));
    }
    public String composeSubject(){
        return "Event " + title + " start at " + startTime + " end at " + endTime;
    }
    public String composeMessage(){

        return "Event at " + location + "\n\nCreate by " + owner + "\n\n" + description;
    }

}