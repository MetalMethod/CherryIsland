package org.academiadecodigo.bootcamp8.cherryone;

import javafx.application.Application;
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

        navigation.loadScreen(INITIAL_VIEW);
        ((PlayerController)navigation.getController(INITIAL_VIEW)).setGame(game);
        ((PlayerController) navigation.getController(INITIAL_VIEW)).scrollPaneRequest();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
