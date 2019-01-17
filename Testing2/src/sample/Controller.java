package sample;

import javafx.fxml.FXML;

public class Controller {

    private Main main;

    @FXML
    private void goDay1() throws Exception {
        main.showDay1();
    }

    @FXML
    private void goDay2() throws Exception {
        main.showDay2();
    }

    @FXML
    private void goBack() throws Exception {
        main.showDays();
    }

    @FXML
    private void addNewEvent() throws Exception{
        main.showAddStage();
    }
}