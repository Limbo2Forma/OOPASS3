package sample;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class ClickedPane extends StackPane {
    public boolean clicked = false;

    public boolean getClicked(){
        return this.clicked;
    }
    public void setClicked(boolean b){
        this.clicked = b;
    }
    public ClickedPane(Node...children) {
        super(children);
        //this.clicked = clicked;
        this.setOnMouseClicked(e -> {
            setClicked(true);
            //this.setStyle("-fx-background-color: blue");
        });

    }
}
