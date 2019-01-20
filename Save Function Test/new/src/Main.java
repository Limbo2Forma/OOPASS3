import Controllers.EventController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EventController event = new EventController();
        boolean stop = false;
        String title; LocalDateTime startTime; LocalDateTime endTime; String owner; String location;int notiTime; String guestList;String description;
        String temp;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a dd/MM/yyyy");
        do{
            System.out.println("Insert Title: ");
            title = scanner.nextLine();
            System.out.println("Insert startTime(hh:mm a dd/MM/yyyy): ");
            temp = scanner.nextLine();
            startTime = LocalDateTime.parse(temp, formatter);
            System.out.println("Insert endTime(hh:mm a dd/MM/yyyy): ");
            temp = scanner.nextLine();
            endTime = LocalDateTime.parse(temp, formatter);
            System.out.println("Insert owner: ");
            owner = scanner.nextLine();
            System.out.println("Insert location: ");
            location = scanner.nextLine();
            System.out.println("Insert guest list: ");
            guestList = scanner.nextLine();
            System.out.println("Insert description: ");
            description = scanner.nextLine();
            System.out.println("Insert notiTime: ");
            notiTime = scanner.nextInt();
            scanner.nextLine();

            event.addEvent(title,startTime,endTime,owner,location,notiTime,guestList, description);

            System.out.println("Do you want to stop? (y/n) ");
            if (scanner.nextLine().equals("y")){
                stop = true;
            }
        } while (!stop);


    }
}
