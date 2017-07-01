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
    private final String INITIAL_VIEW = "grid";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Navigation navigation = Navigation.getInstance();
        navigation.setStage(primaryStage);
        primaryStage.setTitle("Cherry Island");

        Game game = new Game(STARTING_COL, STARTING_ROW);
        ((PlayerController)navigation.getController(INITIAL_VIEW)).setGame(game);

        navigation.loadScreen(INITIAL_VIEW);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
