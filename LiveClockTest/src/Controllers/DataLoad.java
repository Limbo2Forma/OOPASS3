package Controllers;

import Models.Event;
import Models.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DataLoad {
    public static ArrayList<Event> eventList = new ArrayList<>();
    public static User user;

    public static void loadAllData(){
        deserializeEvent();
        deserializeUser();
    }

    public static void saveAllData(){
        serializeEvent();
        serializeUser();
    }

    public static void serializeUser(){
        try {
            PrintWriter pw = new PrintWriter("UserSaveData.dat");
            pw.close();
            FileOutputStream fos = new FileOutputStream("UserSaveData.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
            oos.close();
            fos.close();
        } catch (IOException i) { }
    }
    public static void deserializeUser(){
        try {
            FileInputStream fis = new FileInputStream("UserSaveData.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            user = (User) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException i) {
            try {
                Files.createFile(Paths.get("UserSaveData.dat"));
            } catch (IOException io){ }
        } catch (ClassNotFoundException c) {
            System.out.println("User class not found");
        }
    }

    public static void serializeEvent(){
        try {
            PrintWriter pw = new PrintWriter("EventSaveData.dat");
            pw.close();
            FileOutputStream fos = new FileOutputStream("EventSaveData.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(eventList);
            oos.close();
            fos.close();
        }
        catch (IOException ioe) {
        }
    }

    public static void deserializeEvent(){
        try {
            FileInputStream fis = new FileInputStream("EventSaveData.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            eventList = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
        }
        catch (IOException ioe) {
            try {
                Files.createFile(Paths.get("EventSaveData.dat"));
            } catch (IOException io){ }
            return;
        }
        catch (ClassNotFoundException c) {
            System.out.println("Class not found");
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
