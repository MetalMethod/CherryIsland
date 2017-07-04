package org.academiadecodigo.bootcamp8.cherryisland;

import javafx.application.Application;
import javafx.stage.Stage;
import org.academiadecodigo.bootcamp8.cherryisland.controller.PlayerController;
import org.academiadecodigo.bootcamp8.cherryisland.model.Player;
import org.academiadecodigo.bootcamp8.cherryisland.service.Game;

public class Main extends Application {

    private final int STARTING_COL = 8;
    private final int STARTING_ROW = 8;
    private final String INITIAL_VIEW = "grid";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Navigation navigation = Navigation.getInstance();
        navigation.setStage(primaryStage);
        primaryStage.setTitle("Cherry Island");

        Game game = new Game();
        Player player = new Player(STARTING_COL , STARTING_ROW);

        navigation.loadScreen(INITIAL_VIEW);
        ((PlayerController) navigation.getController(INITIAL_VIEW)).setGame(game);
        ((PlayerController) navigation.getController(INITIAL_VIEW)).scrollPaneRequest();
        ((PlayerController) navigation.getController(INITIAL_VIEW)).setPlayer1(player);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
