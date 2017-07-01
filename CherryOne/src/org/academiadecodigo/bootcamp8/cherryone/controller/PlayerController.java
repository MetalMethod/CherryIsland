package org.academiadecodigo.bootcamp8.cherryone.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import org.academiadecodigo.bootcamp8.cherryone.service.Game;

import java.lang.reflect.Method;

public class PlayerController {

    private Game game;

    @FXML
    ScrollPane scrollPane;

    @FXML
    GridPane gridPane;

    @FXML
    Rectangle rectangle;

    // more FXML stuff

    EventHandler<KeyEvent> keyboardHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            final int maxCols = 10;
            final int maxRows = 10;

            int currentColumn = GridPane.getColumnIndex(rectangle);
            int currentRow = GridPane.getRowIndex(rectangle);

            switch (event.getCode()) {

                case UP:
                    System.out.println("up");
                    if (currentRow > 0) {
                        currentRow--;
                        GridPane.setRowIndex(rectangle, currentRow);
                        /*pane.getChildren().remove(rectangle);
                        pane.add(rectangle, currentColumn, currentRow);*/
                    }
                    break;
                case DOWN:
                    System.out.println("down");
                    if (currentRow < maxRows - 1) {
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
                    if (currentColumn < maxCols - 1) {
                        currentColumn++;
                        GridPane.setColumnIndex(rectangle, currentColumn);
                    }
                    break;
            }
            System.out.println(rectangle.toString());

        }
    };

    public void setGame(Game game) {
        this.game = game;
    }
}
