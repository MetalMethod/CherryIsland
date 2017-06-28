package sample.model;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

public class Player {

    @FXML
    Rectangle rectangle;

    public void move(KeyCode key) {
        switch(key) {
            case UP:
                System.out.println("up");
                break;
            case DOWN:
                System.out.println("down");
                break;
            case LEFT:
                System.out.println("left");
                break;
            case RIGHT:
                rectangle.setTranslateX(2);
                break;
        }
    }
}
