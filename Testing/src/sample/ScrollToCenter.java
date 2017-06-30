package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ScrollToCenter extends Application {

    private ScrollPane scrollPane;
    private GridPane gridPane;
    private int dimensionX;
    private int dimensionY;
    private Scene scene;
    private Pane target;

    @Override
    public void start(Stage primaryStage) {
        scrollPane = new ScrollPane();
        gridPane   = new GridPane();
        target = new Pane();
        scene = new Scene(gridPane);
        dimensionX = 20;
        dimensionY = 20;

        for(int x = 0; x < dimensionX; ++x){
            for(int y = 0; y < dimensionY; ++y){
                Pane temp = new Pane();
                temp.setPrefSize(100, 100);
                /*temp.setOnMouseClicked( e -> {
                    centerViewOn(GridPane.getColumnIndex(temp), GridPane.getRowIndex(temp));
                    temp.setStyle("-fx-background-color: blue");
                });*/
                gridPane.add(temp, x, y);
            }
        }

        target.setStyle("-fx-background-color: blue");
        gridPane.add(target, 0, 0);

        gridPane.setGridLinesVisible(true);
        scrollPane.setContent(gridPane);

        primaryStage.setScene(scene);
        primaryStage.show();

        centerViewOn(GridPane.getColumnIndex(target), GridPane.getRowIndex(target));
        scrollPane.setOnKeyPressed(keyboardHandler);

    }
    EventHandler<KeyEvent> keyboardHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            int currentColumn = GridPane.getColumnIndex(target);
            int currentRow = GridPane.getRowIndex(target);
            switch (event.getCode()) {

                case UP:
                    System.out.println("up");
                    if (currentRow > 0) {
                        currentRow--;
                        GridPane.setRowIndex(target, currentRow);
                    }
                    break;
                case DOWN:
                    System.out.println("down");
                    if (currentRow < 4) {
                        currentRow++;
                        GridPane.setRowIndex(target, currentRow);
                    }
                    break;
                case LEFT:
                    System.out.println("left");
                    if (currentColumn > 0) {
                        currentColumn--;
                        GridPane.setColumnIndex(target, currentColumn);
                    }
                    break;
                case RIGHT:
                    System.out.println("right");
                    if (currentColumn < 4) {
                        currentColumn++;
                        GridPane.setColumnIndex(target, currentColumn);
                    }
                    break;
            }
        }
    };

    private void centerViewOn(double x, double y){
        double viewportWidth    = scrollPane.getViewportBounds().getWidth();
        double maxHscrollPixels = gridPane.getWidth() - viewportWidth;
        double hscrollPixels    =  (x + 0.5) * gridPane.getWidth() / dimensionX - viewportWidth / 2;
        scrollPane.setHvalue(hscrollPixels / maxHscrollPixels);

        double viewportHeight   = scrollPane.getViewportBounds().getHeight();
        double maxVscrollPixels = gridPane.getHeight() - viewportHeight;
        double vscrollPixels    =  (y + 0.5) * gridPane.getHeight() / dimensionY - viewportHeight / 2;
        scrollPane.setVvalue(vscrollPixels / maxVscrollPixels);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
