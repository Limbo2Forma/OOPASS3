package Controllers;
import Models.Event;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EventController {
    private ArrayList<Event> eventArrayList = new ArrayList<Event>();

    public void saveEventList(){
        String temp;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a dd/MM/yyyy");
        try (
            BufferedWriter savefile = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream("EventSaveData.txt"), "utf-8"))) {
            for (int i = 0; i< eventArrayList.size();i++) {
                savefile.write("#########");
                savefile.newLine();
                savefile.write(eventArrayList.get(i).getTitle());
                savefile.newLine();
                temp = eventArrayList.get(i).getStartTime().format(formatter);
                savefile.write(temp);
                savefile.newLine();
                temp = eventArrayList.get(i).getEndTime().format(formatter);
                savefile.write(temp);
                savefile.newLine();
                savefile.write(eventArrayList.get(i).getOwner());
                savefile.newLine();
                savefile.write(eventArrayList.get(i).getLocation());
                savefile.newLine();
                savefile.write(eventArrayList.get(i).getGuestList());
                savefile.newLine();
                savefile.write(eventArrayList.get(i).getDescription());
                savefile.newLine();
                savefile.write(Integer.toString(eventArrayList.get(i).getNotiTime()));
            }
        }
        catch (IOException e){
            System.out.println("IO Exception found");
        }
    }

    public void addEvent(String title,LocalDateTime startTime, LocalDateTime endTime, String owner, String location,int notiTime, String guestList, String description){
            Event newEvent = new Event(title,startTime,endTime,owner,location,notiTime,guestList, description);
            eventArrayList.add(newEvent);
            saveEventList();
    }
}
