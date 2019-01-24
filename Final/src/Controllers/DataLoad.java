package Controllers;

import Models.Event;
import Models.User;

import java.io.*;
import java.util.ArrayList;

public class DataLoad {
    public static ArrayList<Event> eventList = new ArrayList<>();
    public static User user;

    public static void loadAlldata(){
        deserializeEvent();
        deserializeUser();
    }

    public static void saveAllData(){
        serializeEvent();
        serializeUser();
    }

    public static void serializeUser(){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("UserSaveData.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(user);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    public static void deserializeUser(){
        try {
            FileInputStream fileIn = new FileInputStream("UserSaveData.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            user = (User) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("User class not found");
            c.printStackTrace();
        }
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
}
