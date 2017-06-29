package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    GridPane grid;

    @FXML
    Rectangle rect;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    public GridPane getGridPane(){
        return grid;
    }

    public Rectangle getRectangle(){
        return rect;
    }
}