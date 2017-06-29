package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

/**
 * Created by dgcst on 29/06/17.
 */

public class Player {

    private Rectangle rectangle;
    private Scene scene;
    private GridPane pane;

    public Player(Scene scene, Controller controller, GridPane pane) {
        this.scene = scene;
        this.pane = pane;
        this.rectangle = controller.getRectangle();
    }

    public void start() {
        scene.setOnKeyPressed(keyboardHandler);
    }

    EventHandler<KeyEvent> keyboardHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            int currentColumn = GridPane.getColumnIndex(rectangle);
            int currentRow = GridPane.getRowIndex(rectangle);
            switch (event.getCode()) {

                case UP:
                    System.out.println("up");
                    if (currentRow > 0) {
                        currentRow--;
                        //GridPane.setRowIndex(rectangle, currentRow);
                        pane.getChildren().remove(rectangle);
                        pane.add(rectangle, currentColumn, currentRow);
                    }
                    break;
                case DOWN:
                    System.out.println("down");
                    if (currentRow < 4) {
                        currentRow++;
                        GridPane.setRowIndex(rectangle, currentRow);
                    }
                    break;
                case LEFT:
                    System.out.println("left");
                    if (currentColumn > 0) {
                        currentColumn--;
                        GridPane.setColumnIndex(rectangle, currentColumn);
                    }
                    break;
                case RIGHT:
                    System.out.println("right");
                    if (currentColumn < 4) {
                        currentColumn++;
                        GridPane.setColumnIndex(rectangle, currentColumn);
                    }
                    break;
            }
            System.out.println(rectangle.toString());

        }
    };
}