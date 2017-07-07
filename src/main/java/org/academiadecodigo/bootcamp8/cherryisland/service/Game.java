package org.academiadecodigo.bootcamp8.cherryisland.service;

import javafx.application.Application;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.academiadecodigo.bootcamp8.cherryisland.Navigation;
import org.academiadecodigo.bootcamp8.cherryisland.controller.PlayerController;
import org.academiadecodigo.bootcamp8.cherryisland.gameObjects.GameObject;
import org.academiadecodigo.bootcamp8.cherryisland.gameObjects.GameObjectFactory;
import org.academiadecodigo.bootcamp8.cherryisland.gameObjects.ObjectType;
import org.academiadecodigo.bootcamp8.cherryisland.model.GridPosition;
import org.academiadecodigo.bootcamp8.cherryisland.model.Player;
import org.academiadecodigo.bootcamp8.cherryisland.util.U;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by dgcst on 30/06/17.
 */

public class Game extends Application {

    private Player player;
    private GridPane gridPane;
    private HashMap<String, GameObject> gameObjectHashMap = new HashMap<>();
    private ImageView cherries = new ImageView("PATH TO CHERRIES");
    private ImageView tree = new ImageView("PATH TO TREE");
    private ImageView lake = new ImageView("PATH TO LAKE");

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Socket socket = new Socket("127.0.0.1", 6666);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PlayerController playerController = (PlayerController) Navigation.getInstance().getController(U.INITIAL_VIEW);

            String playerNumber;
            String start;

            playerNumber = reader.readLine();
            start = reader.readLine();

            if (playerNumber.split(" ")[0].equals("1")) {
                player = new Player(U.P1_STARTING_COL, U.P1_STARTING_ROW, playerNumber.split(" ")[1]);
            }

            if (playerNumber.equals("2")) {
                player = new Player(U.P2_STARTING_COL, U.P2_STARTING_ROW, playerNumber.split(" ")[1]);
                playerController.getScrollPane().setTranslateX(-(2500 - 725));
                playerController.getScrollPane().setTranslateY(-(2500 - 725));
            }

            gridPane = playerController.getGridPane();

            if (start.equals("start")) {

                Navigation.getInstance().setStage(primaryStage);
                primaryStage.setTitle("Cherry Island");


                Navigation.getInstance().loadScreen(U.INITIAL_VIEW);
                playerController.setGame(this);
                playerController.scrollPaneRequest();
                playerController.setPlayer1(player);

                GameSend gameSend = new GameSend(socket);
                GameReceive gameReceive = new GameReceive(socket);

                Thread send = new Thread(gameSend);
                Thread recieve = new Thread(gameReceive);

                send.start();
                recieve.start();
            }

            System.out.println("EERRRROOOOORRRRRR");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void addGameObject(ObjectType objectType, int col, int row) {

        switch (objectType) {

            case CHERRIES:
                GameObject cherries = GameObjectFactory.getObject(ObjectType.CHERRIES, new GridPosition(col, row));
                gameObjectHashMap.put(String.valueOf(col) + String.valueOf(row), cherries);
                gridPane.add(this.cherries, col, row);
                break;

            case TREE:
                GameObject tree = GameObjectFactory.getObject(ObjectType.TREE, new GridPosition(col, row));
                gameObjectHashMap.put(String.valueOf(col) + String.valueOf(row), tree);
                gridPane.add(this.tree, col, row);
                break;

            case LAKE:
                GameObject lake = GameObjectFactory.getObject(ObjectType.LAKE, new GridPosition(col, row));
                gameObjectHashMap.put(String.valueOf(col) + String.valueOf(row), lake);
                gridPane.add(this.lake, col, row);
                break;

        }
    }


    public void movePlayer(int col, int row) {
        player.setPosition(col, row);
    }

    public boolean comparePosition(GridPosition g1, GridPosition g2) {

        if (g1.getCol() == g2.getCol() || g1.getRow() == g2.getRow()) {
            return true;
        }

        return false;
    }
}
