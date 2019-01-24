package Models.Reminder;

import java.io.Serializable;

public class RepeatType implements Serializable {
    private int repeatFrequency;
    private String repeatPeriod;            // "daily", "weekly", "monthly", "yearly"

    private String endType;                 // "Never", "On Date", "After"
    private String endTime;                 // "dd/mm/yyyy" for endType = "Date"

    private boolean[] dayOfWeek;       // for repeatPeriod = "Weekly", dayOfWeek[0] represent Sunday, [6] is Saturday

    private int[] dateForMonth;
    private int afterTimes;

// dateForMonth[1] = 0 means it is monthly reminder based on date, dateForMonth[1] = 0 means it is monthly reminder based on week
// for repeatPeriod = "Monthly", example: dateForMonth[0] = 3 && dateForMonth[1] = 0 means monthly reminder every day 3 of the month
// dateForMonth[1] != 0, for example dateForMonth[1] = 3 and dateForMonth[0] = 6 means reminder for every Saturday of the 3rd week
    public RepeatType (String repeatPeriod){
        this.repeatFrequency = 1;
        this.repeatPeriod = repeatPeriod;
        if(repeatPeriod.equals("Weekday")){
            this.dayOfWeek = new boolean[7];
            for (int i = 1; i<6;i++){
                this.dayOfWeek[i] = true;
            }
        }
    }

    public RepeatType(int repeatFrequency, String repeatPeriod, String endType, int afterTimes, boolean[] dayOfWeek, int[] dateForMonth){
        this.repeatFrequency=repeatFrequency;
        this.repeatPeriod=repeatPeriod;
        this.endType=endType;
        this.dayOfWeek = new boolean[7];
        this.dayOfWeek=dayOfWeek;

        this.dateForMonth=dateForMonth;
        this.afterTimes=afterTimes;
    }

    public RepeatType(int repeatFrequency, String repeatPeriod, String endType, String endTime, boolean[] dayOfWeek, int[] dateForMonth){
        this.repeatFrequency=repeatFrequency;
        this.repeatPeriod=repeatPeriod;
        this.endType=endType;
        this.endTime=endTime;
        this.dayOfWeek = new boolean[7];
        this.dayOfWeek=dayOfWeek;
        this.dateForMonth=dateForMonth;
    }

    public RepeatType(int repeatFrequency, String repeatPeriod, String endType, boolean[] dayOfWeek, int[] dateForMonth){
        this.repeatFrequency=repeatFrequency;
        this.repeatPeriod=repeatPeriod;
        this.endType=endType;
        this.dayOfWeek = new boolean[7];
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

    public boolean getDayOfWeek(int index) {
        return dayOfWeek[index];
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

    public int getAfterTimes() {
        return afterTimes;
    }

    public void setAfterTimes(int afterTimes) {
        this.afterTimes = afterTimes;
    }
}

