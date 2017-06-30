package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

/**
 * Created by dgcst on 29/06/17.
 */

public class Player {

    private Rectangle rectangle;
    private Scene scene;
    private ScrollPane pane;
    private int dimensionX;
    private int dimensionY;

    public Player(Scene scene, Controller controller, ScrollPane pane) {
        this.scene = scene;
        this.pane = pane;
        this.rectangle = controller.getRectangle();
        dimensionX = 10;
        dimensionY = 10;
    }

    @FXML
    GridPane grid;

    public void start() {
        scene.setOnKeyPressed(keyboardHandler);
        centerViewOn(GridPane.getColumnIndex(rectangle), GridPane.getRowIndex(rectangle));
    }

    private void centerViewOn(double x, double y) {
        System.out.println("Pane: " + pane + " | " + "Grid: " + grid);
        double viewportWidth    = pane.getViewportBounds().getWidth();
        double maxHscrollPixels = grid.getWidth() - viewportWidth;
        double hscrollPixels    =  (x + 0.5) * grid.getWidth() / dimensionX - viewportWidth / 2;
        pane.setHvalue(hscrollPixels / maxHscrollPixels);

        double viewportHeight   = pane.getViewportBounds().getHeight();
        double maxVscrollPixels = grid.getHeight() - viewportHeight;
        double vscrollPixels    =  (y + 0.5) * grid.getHeight() / dimensionY - viewportHeight / 2;
        pane.setVvalue(vscrollPixels / maxVscrollPixels);
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
                        GridPane.setRowIndex(rectangle, currentRow);
                        /*pane.getChildren().remove(rectangle);
                        pane.add(rectangle, currentColumn, currentRow);*/
                    }
                    break;
                case DOWN:
                    System.out.println("down");
                    if (currentRow < dimensionY - 1) {
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
                    if (currentColumn < dimensionX - 1) {
                        currentColumn++;
                        GridPane.setColumnIndex(rectangle, currentColumn);
                    }
                    break;
            }
            System.out.println(rectangle.toString());

        }
    };
}