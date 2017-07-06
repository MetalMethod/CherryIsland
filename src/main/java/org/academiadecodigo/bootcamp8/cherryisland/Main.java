package org.academiadecodigo.bootcamp8.cherryisland;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.academiadecodigo.bootcamp8.cherryisland.controller.PlayerController;
import org.academiadecodigo.bootcamp8.cherryisland.model.Player;
import org.academiadecodigo.bootcamp8.cherryisland.service.Game;
import org.academiadecodigo.bootcamp8.cherryisland.service.PlayerService;
import org.academiadecodigo.bootcamp8.cherryisland.service.ServiceRegistry;

public class Main extends Application {

    private final int STARTING_COL = 14;
    private final int STARTING_ROW = 14;
    private final String INITIAL_VIEW = "menu";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Navigation navigation = Navigation.getInstance();
        navigation.setStage(primaryStage);
        primaryStage.setTitle("Cherry Island");

        Game game = new Game();
        Player player = new Player(STARTING_COL , STARTING_ROW);


        PlayerService playerService = new PlayerService();

        ServiceRegistry serviceRegistry = ServiceRegistry.getInstance();
        serviceRegistry.addService(playerService.getName(), playerService);

        navigation.loadScreen(INITIAL_VIEW);

        /*((PlayerController) navigation.getController("grid")).setGame(game);
        ((PlayerController) navigation.getController("grid")).scrollPaneRequest();
        ((PlayerController) navigation.getController("grid")).setPlayer1(player);*/



    }

    public static void main(String[] args) {
        launch(args);
    }
}
