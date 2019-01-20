package Controllers;

import Models.Event;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DataLoad {
    public static ArrayList<Event> eventList = new ArrayList<>();

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

    public static void serializeEvent(){
        PrintWriter pw = new PrintWriter("EventSaveData.txt");
        pw.close();
        try
        {
            FileOutputStream fos = new FileOutputStream("EventSaveData.txt");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("EventSaveData.txt"));
            oos.writeObject(eventList);
            oos.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
    public static void deserializeEvent(){
        try
        {
            FileInputStream fis = new FileInputStream("EventSaveData.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            eventList = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
            return;
        }
        catch (ClassNotFoundException c)
        {
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
