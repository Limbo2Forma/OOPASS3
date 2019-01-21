package Models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reminder implements Serializable {
    private String title;
    private String time;                // (hh:mm a dd/MM/yyyy)
    private RepeatType repeat;          // can be "None", "Daily", "Weekly", "Monthly", "Yearly", custom

    public String composeSubject(){
        return "Reminder " + title + " at " + time + " " + repeat.getRepeatPeriod();
    }

    public Reminder(String title, String time, RepeatType repeat){          // Create a custom reminder object
        this.title = title;
        this.time = time;
        this.repeat = repeat;
    }

    public Reminder(String title, String time, String typeOfRepeat){        // Create a preset reminder object of daily, weekly, monthly...
        // typeOfRepeat must be either "daily", "weekly","weekday", "monthly", "yearly"
        this.title = title;
        this.time = time;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a dd/MM/yyyy");
        LocalDateTime selectedTime = LocalDateTime.parse(this.time, formatter);
        switch(typeOfRepeat){
            case "daily":
            {
                boolean[] tempDay = {false,false,false,false,false,false,false};
                int[] tempDate = {0,0};
                this.repeat = new RepeatType(1, "daily", "never","",tempDay,tempDate);
            }
            case "weekly":
            {
                boolean[] tempDay = {false,false,false,false,false,false,false};
                int[] tempDate = {0,0};
                this.repeat = new RepeatType(1, "weekly", "never","",tempDay,tempDate);
            }
            case "weekday":
            {
                boolean[] tempDay = {false,true,true,true,true,true,false};
                int[] tempDate = {0,0};
                this.repeat = new RepeatType(1, "weekly", "never","",tempDay,tempDate);
            }
            case "monthly":
            {
                boolean[] tempDay = {false,false,false,false,false,false,false};
                int[] tempDate = {selectedTime.getDayOfMonth(),0};
                this.repeat = new RepeatType(1, "monthly", "never","",tempDay,tempDate);
            }
            case "yearly":
            {
                boolean[] tempDay = {false,false,false,false,false,false,false};
                int[] tempDate = {0,0};
                this.repeat = new RepeatType(1, "yearly", "never","",tempDay,tempDate);
            }
            default:
            {
                System.out.println("Error repeat type string");
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle){
        title = newTitle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String newTime){
        title = newTime;
    }

    public RepeatType getRepeatType() {
        return repeat;
    }
    public void setRepeatType(int repeatFrequency, String repeatPeriod, String endType, String endTime, boolean[] dayOfWeek, int[] dateForMonth){
        this.repeat = new RepeatType(repeatFrequency, repeatPeriod, endType, endTime, dayOfWeek, dateForMonth);;
    }

}

class RepeatType{
    private int repeatFrequency;
    private String repeatPeriod;            // "daily", "weekly", "monthly", "yearly"

    private String endType;                 // "never", "date"
    private String endTime;                 // "dd/mm/yyyy" for endType = "Date"

    private boolean[] dayOfWeek;       // for repeatPeriod = "Weekly", dayOfWeek[0] represent Sunday, [6] is Saturday

    private int[] dateForMonth;
// dateForMonth[1] = 0 means it is monthly reminder based on date, dateForMonth[1] = 0 means it is monthly reminder based on week
// for repeatPeriod = "Monthly", example: dateForMonth[0] = 3 && dateForMonth[1] = 0 means monthly reminder every day 3 of the month
// dateForMonth[1] != 0, for example dateForMonth[1] = 3 and dateForMonth[0] = 6 means reminder for every Saturday of the 3rd week

    RepeatType(int repeatFrequency, String repeatPeriod, String endType, String endTime, boolean[] dayOfWeek, int[] dateForMonth){
        this.repeatFrequency=repeatFrequency;
        this.repeatPeriod=repeatPeriod;
        this.endType=endType;
        this.endTime=endTime;
        this.dayOfWeek=dayOfWeek;
        this.dateForMonth=dateForMonth;
    }

    public int getRepeatFrequency() {
        return repeatFrequency;
    }

    public void setRepeatFrequency(int repeatFrequency) {
        this.repeatFrequency = repeatFrequency;
    }

    public String getRepeatPeriod() {
        return repeatPeriod;
    }

    public void setRepeatPeriod(String repeatPeriod) {
        this.repeatPeriod = repeatPeriod;
    }

    public String getEndType() {
        return endType;
    }

    public void setEndType(String endType) {
        this.endType = endType;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean[] getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(boolean[] dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int[] getDateForMonth() {
        return dateForMonth;
    }

    public void setDateForMonth(int[] dateForMonth) {
        this.dateForMonth = dateForMonth;
    }
}


