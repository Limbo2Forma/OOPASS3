package sample;



public class Event {
    private String name, venue, time, date;
    //String eDate;
    public Event(String name, String venue, String time, String date){
        this.name = name;
        this.venue = venue;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getVenue() {
        return venue;
    }
    public void setVenue(String venue) {
        this.venue = venue;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}