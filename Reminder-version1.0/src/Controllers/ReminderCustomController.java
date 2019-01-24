package Controllers;

import Models.Notification;
import Models.Reminder.Reminder;
import Models.Reminder.RepeatType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReminderCustomController {
    @FXML
    Pane weekPane, monthlyPane, yearPane;
    @FXML
    Pane pane, sub_pane, reminderPane;
    @FXML
    MenuButton menu, sub_menu, date, week;
    @FXML
    Button setButton, cancelButton;
    @FXML
    Spinner spinner, iterationNum, DateOfMonthSpinner;
    @FXML DatePicker datePick, datePickYear;
    LocalDate dateYear;
    @FXML
    Label times;
    @FXML
    ToggleButton tbMon, tbTue, tbWed, tbThu, tbFri, tbSat, tbSun;
    @FXML
    RadioButton MonthTypeOnDateRB, MonthTypeOnDayOfWeekRB;
    @FXML
    Label onLabel, onEveryLabel;
    @FXML
    TextField titleFieldCustom;
    @FXML
    ComboBox timeMenuButtonCustom, ampmComboBoxCustom;
    private static boolean[] dateOfWeekBool = new boolean[7];
    private int[] dateOfMonthInt = new int[2];
    private String checkCase = "Choose";

    @FXML
    private void loadViewDay() {
        try {
            menu.setText("Day(s)");
            pane.getChildren().remove(monthlyPane);
            pane.getChildren().remove(weekPane);
            pane.getChildren().remove(yearPane);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("exeception error 1");
        }
    }

    @FXML
    private void loadViewWeek() {
        try {
            for (int i = 0; i < 7; i++) {
                dateOfWeekBool[i] = false;
            }
            pane.getChildren().remove(monthlyPane);
            pane.getChildren().remove(weekPane);
            pane.getChildren().remove(yearPane);
            weekPane = FXMLLoader.load(getClass().getResource("/Views/ReminderCustomType/reminderViewWeekly.fxml"));
            pane.getChildren().add(weekPane);
            menu.setText("Week(s)");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("exeception error 1");
        }
    }

    @FXML
    private void loadViewMonth() {
        try {
            menu.setText("Month(s)");
            pane.getChildren().remove(weekPane);
            pane.getChildren().remove(monthlyPane);
            pane.getChildren().remove(yearPane);
            monthlyPane = FXMLLoader.load(getClass().getResource("/Views/ReminderCustomType/reminderViewMonthly.fxml"));
            pane.getChildren().add(monthlyPane);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("exeception error 2");
        }
    }

    @FXML
    private void loadViewYear() {
        try {
            menu.setText("Year(s)");
            pane.getChildren().remove(monthlyPane);
            pane.getChildren().remove(weekPane);
            pane.getChildren().remove(yearPane);
            datePickYear = FXMLLoader.load(getClass().getResource("/Views/ReminderCustomType/reminderViewYearly.fxml"));
            pane.getChildren().add(datePickYear);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("exeception error 1");
        }
    }

    //########SET BOOLEAN VALUES FOR TOGGLE BUTTONS##############
    @FXML
    private void setSun() {
        dateOfWeekBool[0] = !dateOfWeekBool[0];
        //if(dateOfWeekBool[0]) System.out.println("Sun is " +dateOfWeekBool[0]);
        //else System.out.println("Sun is " +dateOfWeekBool[0]);
    }

    @FXML
    private void setMon() {
        dateOfWeekBool[1] = !dateOfWeekBool[1];
        if (dateOfWeekBool[1]) System.out.println("Mon is " + dateOfWeekBool[1]);
        else System.out.println("Sun is " + dateOfWeekBool[1]);
    }

    @FXML
    private void setTue() {
        dateOfWeekBool[2] = !dateOfWeekBool[2];
        if (dateOfWeekBool[2]) System.out.println("Tue is " + dateOfWeekBool[2]);
        else System.out.println("Sun is " + dateOfWeekBool[2]);
    }

    @FXML
    private void setWed() {
        dateOfWeekBool[3] = !dateOfWeekBool[3];
        if (dateOfWeekBool[3]) System.out.println("Wed is " + dateOfWeekBool[3]);
        else System.out.println("Sun is " + dateOfWeekBool[3]);
    }

    @FXML
    private void setThu() {
        dateOfWeekBool[4] = !dateOfWeekBool[4];
        if (dateOfWeekBool[4]) System.out.println("Thu is " + dateOfWeekBool[4]);
        else System.out.println("Sun is " + dateOfWeekBool[4]);
    }

    @FXML
    private void setFri() {
        dateOfWeekBool[5] = !dateOfWeekBool[5];
        if (dateOfWeekBool[5]) System.out.println("Fri is " + dateOfWeekBool[5]);
        else System.out.println("Sun is " + dateOfWeekBool[5]);
    }

    @FXML
    private void setSat() {
        dateOfWeekBool[6] = !dateOfWeekBool[6];
        if (dateOfWeekBool[6]) System.out.println("Sat is " + dateOfWeekBool[6]);
        else System.out.println("Sun is " + dateOfWeekBool[6]);
    }

    //########FUNCTION CHOOSE A DAY IN A WEEK##############
    @FXML
    private void displayMon() {
        date.setText("Monday");
        dateOfMonthInt[0] = 1;
    }

    @FXML
    private void displayTue() {
        date.setText("Tuesday");
        dateOfMonthInt[0] = 2;
    }

    @FXML
    private void displayWed() {
        date.setText("Wednesday");
        dateOfMonthInt[0] = 3;
    }

    @FXML
    private void displayThur() {
        date.setText("Thursday");
        dateOfMonthInt[0] = 4;
    }

    @FXML
    private void displayFri() {
        date.setText("Friday");
        dateOfMonthInt[0] = 5;
    }

    @FXML
    private void displaySat() {
        date.setText("Saturday");
        dateOfMonthInt[0] = 6;
    }

    @FXML
    private void displaySun() {
        date.setText("Sunday");
        dateOfMonthInt[0] = 0;
    }

    //########FUNCTION CHOOSE A WEEK IN A MONTH##############
    @FXML
    private void displayWeek1() {
        week.setText("Week1");
        dateOfMonthInt[1] = 1;
    }

    @FXML
    private void displayWeek2() {
        week.setText("Week2");
        dateOfMonthInt[1] = 2;
    }

    @FXML
    private void displayWeek3() {
        week.setText("Week3");
        dateOfMonthInt[1] = 3;
    }

    @FXML
    private void displayWeek4() {
        week.setText("Week4");
        dateOfMonthInt[1] = 4;
    }

    @FXML
    private void displayWeek5() {
        week.setText("Week5");
        dateOfMonthInt[1] = 5;
    }

    //##########MENU ITEMS LIST FOR SELECTING WHEN THE REMINDER IS END###############
    @FXML
    private void loadEndButton_Never() {
        try {
            checkCase = "Never";
            datePick.setDisable(true);
            datePick.setVisible(false);
            iterationNum.setDisable(true);
            iterationNum.setVisible(false);
            times.setVisible(false);
            sub_menu.setText("Never");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception error 4");
        }
    }

    @FXML
    private void loadEndButton_OnDate() {
        try {
            checkCase = "On date";
            datePick.setDisable(false);
            datePick.setVisible(true);
            iterationNum.setDisable(true);
            iterationNum.setVisible(false);
            times.setVisible(false);
            sub_menu.setText("On Date");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception error 3");
        }
    }

    @FXML
    private void loadEndButton_After() {
        try {
            checkCase = "After";
            datePick.setDisable(true);
            datePick.setVisible(false);
            times.setVisible(true);
            iterationNum.setVisible(true);
            iterationNum.setDisable(false);
            sub_menu.setText("After");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception error 4");
        }
    }

    //##########MONTH RADIO BUTTON FUNCTIONS  NEW NEW NEW NEW NEW NEW NEW NEW NEW NEW NEW NEW#########################################
    @FXML
    private void switchToMonthOnDate() {
        MonthTypeOnDayOfWeekRB.setSelected(false);
        onEveryLabel.setTextFill(Color.GRAY);
        onLabel.setTextFill(Color.BLACK);
        date.setVisible(false);
        week.setVisible(false);
        MonthTypeOnDateRB.setSelected(true);
        DateOfMonthSpinner.setVisible(true);
    }

    @FXML
    private void switchToMonthOnDayOfWeek() {
        onLabel.setTextFill(Color.GRAY);
        onEveryLabel.setTextFill(Color.BLACK);
        MonthTypeOnDateRB.setSelected(false);
        DateOfMonthSpinner.setVisible(false);
        date.setVisible(true);
        week.setVisible(true);
    }

    @FXML
    private void createCustomRepeatType() {
        String dateStr;
        String errorMessage = "";
        String timeStrCustom = "";
        try {
            timeStrCustom = timeMenuButtonCustom.getValue() + " " + (String) ampmComboBoxCustom.getValue();
            if(menu.getText().equals("Year(s)")){
                dateYear = datePickYear.getValue();
                dateStr = dateYear.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                timeStrCustom = timeStrCustom + " " + dateStr;
            }
            if (checkCase.equals("On date")) {
                try {
                    System.out.println("Save on date case");
                    dateStr = datePick.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    ReminderController.customReminder = new RepeatType(Integer.valueOf(spinner.getValue().toString()),
                            menu.getText(), sub_menu.getText(), dateStr, dateOfWeekBool, dateOfMonthInt);
                    System.out.println(ReminderController.customReminder.getRepeatPeriod());
                    System.out.println(ReminderController.customReminder.getEndType());
                    System.out.println(ReminderController.customReminder.getEndTime());
                    for (int i = 0; i < 7; i++) {
                        System.out.println("haha: " + ReminderController.customReminder.getDayOfWeek(0));
                        if (ReminderController.customReminder.getDayOfWeek(i)) {
                            System.out.println("customReminder dayOfWeek " + i + "is true");
                        } else System.out.println("customReminder dayOfWeek " + i + "is false");
                        if (dateOfWeekBool[i]) {
                            System.out.println("boolean array [" + i + "] is true");
                        } else System.out.println("boolean array [" + i + "] is false");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setButton();
            } else if (checkCase.equals("After")) {
                try {
                    System.out.println("Save After case");
                    System.out.println("Iteration Num:" + iterationNum.getValue().toString());
                    ReminderController.customReminder = new RepeatType(Integer.valueOf(spinner.getValue().toString()),
                            menu.getText(), sub_menu.getText(), Integer.valueOf(iterationNum.getValue().toString()),
                            dateOfWeekBool, dateOfMonthInt);
                    System.out.println(ReminderController.customReminder.getRepeatPeriod());
                    System.out.println(ReminderController.customReminder.getEndType());
                    System.out.println(ReminderController.customReminder.getAfterTimes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setButton();
            } else if (checkCase.equals("Never")) {
                try {
                    System.out.println("Save Never case");
                    ReminderController.customReminder = new RepeatType(Integer.valueOf(spinner.getValue().toString()),
                            menu.getText(), sub_menu.getText(), dateOfWeekBool, dateOfMonthInt);
                    System.out.println(ReminderController.customReminder.getRepeatPeriod());
                    System.out.println(ReminderController.customReminder.getEndType());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setButton();
            } else if (checkCase.equals("Choose")) {
                Notification notification = new Notification();
                notification.sendNotification("Warning", "Choose a specific end type before setting the custom reminder", false);
            }
        } catch (NullPointerException npe) {
            errorMessage = errorMessage + "Unspecified Date or Time";
        }
        try {
            DataLoad.reminderList.add(new Reminder(titleFieldCustom.getText(), timeStrCustom, ReminderController.customReminder));
            System.out.println(DataLoad.reminderList.get(0).getTitle());
            System.out.println(DataLoad.reminderList.get(0).getTime());
            System.out.println(DataLoad.reminderList.get(0).getRepeatType().getRepeatPeriod());
        } catch (NullPointerException npe){
            System.out.println("Error creating a Reminder");
        }
    }

    //##########FUNCTIONS FOR buttons "Set" and "Cancel"################
    @FXML
    private void setButton(){
        Stage stage = (Stage) setButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void cancelButton(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
