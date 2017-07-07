package org.academiadecodigo.bootcamp8.cherryisland;

import javafx.application.Application;
import javafx.stage.Stage;
import org.academiadecodigo.bootcamp8.cherryisland.controller.PlayerController;
import org.academiadecodigo.bootcamp8.cherryisland.gameObjects.ObjectType;
import org.academiadecodigo.bootcamp8.cherryisland.model.Player;
import org.academiadecodigo.bootcamp8.cherryisland.service.Game;
import org.academiadecodigo.bootcamp8.cherryisland.util.U;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        System.out.println(ObjectType.CHERRIES);}

    public static void main(String[] args) {
        launch(args);
    }
}
