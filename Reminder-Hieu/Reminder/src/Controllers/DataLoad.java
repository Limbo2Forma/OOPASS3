package Controllers;

import Models.Event;
import Models.Reminder.Reminder;

import java.io.*;
import java.util.ArrayList;

public class DataLoad {
    public static ArrayList<Event> eventList = new ArrayList<>();
    public static ArrayList<Reminder> reminderList = new ArrayList<>();

    public static void loadAllEvent(){
        try {
            BufferedReader b = new BufferedReader(new FileReader(new File("src/EventSaveData.txt")));
            String readLine = "";
            System.out.println("Reading file using Buffered Reader");
            while ((readLine = b.readLine()) != null) {
                System.out.println(readLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void loadAlldata(){
        deserializeEvent();
        deserializeReminder();
    }

    public static void saveAllData(){
        serializeEvent();
        serializeReminder();
    }

    public static void serializeEvent(){
        try {
            FileOutputStream fos = new FileOutputStream("EventSaveData.txt");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("EventSaveData.txt"));
            oos.writeObject(eventList);
            oos.close();
            fos.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void deserializeEvent(){
        try {
            FileInputStream fis = new FileInputStream("EventSaveData.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            eventList = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }
        catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }

        //Verify list data
        for (Event event : eventList) {
            System.out.println(event.getTitle());
            System.out.println(event.getOwner());
            System.out.println(event.getStartTime());
            System.out.println(event.getEndTime());
            System.out.println(event.getNotiTime());
            System.out.println(event.getLocation());
            System.out.println(event.getDescription());
            System.out.println(event.getNotifyTime());
            System.out.println("########");
        }
    }


    public static void serializeReminder(){
        try {
            FileOutputStream fos = new FileOutputStream("ReminderSaveData.txt");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ReminderSaveData.txt"));
            oos.writeObject(reminderList);
            oos.close();
            fos.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void deserializeReminder(){
        try {
            FileInputStream fis = new FileInputStream("ReminderSaveData.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            reminderList = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }
        catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }

        //Verify list data
        for (Reminder reminder : reminderList) {
            System.out.println(reminder.getTitle());
            System.out.println(reminder.getTime());
            System.out.println(reminder.getRepeatType());
            System.out.println("########");
        }
    }
}
