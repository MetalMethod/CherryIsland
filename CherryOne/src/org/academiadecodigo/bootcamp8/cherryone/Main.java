package org.academiadecodigo.bootcamp8.cherryone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.academiadecodigo.bootcamp8.cherryone.controller.PlayerController;
import org.academiadecodigo.bootcamp8.cherryone.service.Game;

public class Main extends Application {

    private final int STARTING_COL = 10;
    private final int STARTING_ROW = 10;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/grid.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Cherry Island");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        Game game = new Game(STARTING_COL, STARTING_ROW);
        ((PlayerController)fxmlLoader.getController()).setGame(game);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
