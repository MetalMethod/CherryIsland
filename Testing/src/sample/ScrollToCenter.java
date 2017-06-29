package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ScrollToCenter extends Application {

    private ScrollPane scrollPane;
    private GridPane gridPane;
    private double dimensionX;
    private double dimensionY;

    @Override
    public void start(Stage primaryStage) {
        scrollPane = new ScrollPane();
        gridPane   = new GridPane();
        dimensionX = 20;
        dimensionY = 20;

        for(int x = 0; x < dimensionX; ++x){
            for(int y = 0; y < dimensionY; ++y){
                Pane temp = new Pane();
                temp.setPrefSize(100, 100);
                temp.setOnMouseClicked( e -> {
                    centerViewOn(GridPane.getColumnIndex(temp), GridPane.getRowIndex(temp));
                    temp.setStyle("-fx-background-color: blue");
                });
                gridPane.add(temp, x, y);
            }
        }

        gridPane.setGridLinesVisible(true);
        scrollPane.setContent(gridPane);

        primaryStage.setScene(new Scene(scrollPane));
        primaryStage.show();
    }

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
