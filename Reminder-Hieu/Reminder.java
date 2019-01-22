package Models.Reminder;

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
        // typeOfRepeat must be either "Daily", "Weekly","Weekday", "Monthly", "Yearly"
        this.title = title;
        this.time = time;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a dd/MM/yyyy");
        LocalDateTime selectedTime = LocalDateTime.parse(this.time, formatter);
        switch(typeOfRepeat){
            case "Daily":
            {
                boolean[] tempDay = {false,false,false,false,false,false,false};
                int[] tempDate = {0,0};
                this.repeat = new RepeatType(1, "Daily", "never","",0,tempDay,tempDate);
                break;
            }
            case "Weekly":
            {
                boolean[] tempDay = {false,false,false,false,false,false,false};
                int[] tempDate = {0,0};
                this.repeat = new RepeatType(1, "Weekly", "never","",0,tempDay,tempDate);
                break;
            }
            case "Weekday":
            {
                boolean[] tempDay = {false,true,true,true,true,true,false};
                int[] tempDate = {0,0};
                this.repeat = new RepeatType(1, "Weekly", "never","",0,tempDay,tempDate);
                break;
            }
            case "Monthly":
            {
                boolean[] tempDay = {false,false,false,false,false,false,false};
                int[] tempDate = {selectedTime.getDayOfMonth(),0};
                this.repeat = new RepeatType(1, "Monthly", "never","",0,tempDay,tempDate);
                break;
            }
            case "Yearly":
            {
                boolean[] tempDay = {false,false,false,false,false,false,false};
                int[] tempDate = {0,0};
                this.repeat = new RepeatType(1, "Yearly", "never","",0,tempDay,tempDate);
                break;
            }
            default:
            {
                System.out.println("Error repeat type string");
                break;
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
    public void setRepeatType(int repeatFrequency, String repeatPeriod, String endType, String endTime, boolean[] dayOfWeek, int[] dateForMonth,int afterTimes){
        this.repeat = new RepeatType(repeatFrequency, repeatPeriod, endType, endTime,afterTimes, dayOfWeek, dateForMonth);;
    }

}


