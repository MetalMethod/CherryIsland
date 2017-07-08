package org.academiadecodigo.bootcamp8.cherryisland.service;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by dgcst on 30/06/17.
 */

public class Game extends Application {

    private Player player;
    private GridPane gridPane;
    private HashMap<String, GameObject> gameObjectHashMap;
    private Pane pane;
    private ScrollPane scrollPane;
    private Socket socket;
    private String[] positionContents;
    public static String hostname;
    private String playerNumber;
    private GameObject enemy;
    private ImageView enemyImg;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            if (hostname != null) {
                socket = new Socket(hostname, 6666);
            } else {
                socket = new Socket("127.0.0.1", 6666);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String start;

            gameObjectHashMap = new HashMap<>();
            positionContents = new String[U.GRID_COLS * U.GRID_COLS];
            for (int i = 0; i < positionContents.length; i++) {
                positionContents[i] = "empty";
            }

            playerNumber = reader.readLine();
            start = reader.readLine();

            if (playerNumber.equals("1")) {
                player = new Player(U.P1_STARTING_COL, U.P1_STARTING_ROW);
                enemy = GameObjectFactory.getObject(ObjectType.ENEMY, new GridPosition(U.P2_STARTING_COL, U.P2_STARTING_ROW));
            }

            if (playerNumber.equals("2")) {
                player = new Player(U.P2_STARTING_COL, U.P2_STARTING_ROW);
                enemy = GameObjectFactory.getObject(ObjectType.ENEMY, new GridPosition(U.P1_STARTING_COL, U.P1_STARTING_ROW));
            }


            if (start.equals("start")) {

                Navigation.getInstance().setStage(primaryStage);
                primaryStage.setTitle("Cherry Island");


                Navigation.getInstance().loadScreen(U.INITIAL_VIEW);
                PlayerController playerController = (PlayerController) Navigation.getInstance().getController(U.INITIAL_VIEW);
                playerController.setGame(this);
                playerController.scrollPaneRequest();
                playerController.setPlayer1(player);

                gridPane = playerController.getGridPane();
                pane = playerController.getPane();
                scrollPane = playerController.getScrollPane();

                if (playerNumber.equals("1")) {
                    gameObjectHashMap.put(String.valueOf(U.P2_STARTING_COL) + String.valueOf(U.P2_STARTING_ROW), enemy);
                    enemyImg = new ImageView(ObjectType.ENEMY.getPath());
                    gridPane.add(enemyImg, U.P1_STARTING_COL, U.P1_STARTING_ROW);
                    positionContents[U.GRID_COLS * U.P2_STARTING_ROW + U.P2_STARTING_COL] = "enemy";
                }

                if (playerNumber.equals("2")) {
                    scrollPane.setVvalue(2500 - 725);
                    scrollPane.setHvalue(2500 - 725);
                    gameObjectHashMap.put(String.valueOf(U.P1_STARTING_COL) + String.valueOf(U.P1_STARTING_ROW), enemy);
                    enemyImg = new ImageView(ObjectType.ENEMY.getPath());
                    gridPane.add(enemyImg, U.P1_STARTING_COL, U.P1_STARTING_ROW);
                    positionContents[U.GRID_COLS * U.P1_STARTING_ROW + U.P1_STARTING_COL] = "enemy";
                }

                GameReceive gameReceive = new GameReceive(socket, this);

                Thread receive = new Thread(gameReceive);

                receive.start();

                gameSend("start");
            }

            System.out.println("EERRRROOOOORRRRRR");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Game.hostname = null;
        if (args.length > 0) {
            Game.hostname = args[0];
        }
        launch(args);
    }

    public void addGameObject(ObjectType objectType, int col, int row) {

        switch (objectType) {

            case CHERRIES:
                GameObject cherries = GameObjectFactory.getObject(ObjectType.CHERRIES, new GridPosition(col, row));
                gameObjectHashMap.put(String.valueOf(col) + String.valueOf(row), cherries);
                gridPane.add(new ImageView("/gameobjects/cherrytree1.png"), col, row);
                positionContents[U.GRID_COLS * row + col] = "cherries";
                break;

            case TREE:
                GameObject tree = GameObjectFactory.getObject(ObjectType.TREE, new GridPosition(col, row));
                gameObjectHashMap.put(String.valueOf(col) + String.valueOf(row), tree);
                gridPane.add(new ImageView("/gameobjects/tree1.png"), col, row);
                positionContents[U.GRID_COLS * row + col] = "tree";
                break;

            case LAKE:
                GameObject lake = GameObjectFactory.getObject(ObjectType.LAKE, new GridPosition(col, row));
                gameObjectHashMap.put(String.valueOf(col) + String.valueOf(row), lake);
                gridPane.add(new ImageView("/gameobjects/lake1.png"), col, row);
                for (int i = 0; i < U.LAKECOLSPAN; i++) {
                    for (int j = -1; j < 2; j++) {
                        positionContents[(j * U.GRID_COLS) + U.GRID_COLS * row + col + i] = "lake";
                    }
                }
                break;

        }
    }

    public void removeGameObject(int col, int row) {

        for (Node n : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(n) == col && GridPane.getRowIndex(n) == row) {
                gridPane.getChildren().remove(gridPane.getChildren().indexOf(n));
            }
        }//stackoverflow says row and col(cont col and row)
        String key = String.valueOf(col) + String.valueOf(row);
        gameObjectHashMap.remove(key);
        System.out.println("hbouhbi");
        positionContents[U.GRID_COLS * row + col] = "empty";
    }

    public synchronized void moveGameObject(GameObject gameObject, int col, int row) {
        int currentcol = gameObject.getGridPosition().getCol();
        int currentrow = gameObject.getGridPosition().getRow();



        System.out.println(col + " " + row + " " + currentcol + " " + currentrow);

        gridPane.getChildren().remove(enemyImg);
        gridPane.add(enemyImg, col, row);

        enemy.getGridPosition().setCol(col);
        enemy.getGridPosition().setRow(row);
        //stackoverflow says row and col(cont col and row)
        String key = String.valueOf(currentcol) + String.valueOf(currentrow);
        String keynew = String.valueOf(col) + String.valueOf(row);
        gameObjectHashMap.remove(key);
        gameObjectHashMap.put(keynew, gameObject);
        positionContents[U.GRID_COLS * currentrow + currentcol] = "empty";
        positionContents[U.GRID_COLS * row + col] = gameObject.getObjectType().getName();
    }

    public void gameSend(String msgToSend) {
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(msgToSend);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String[] getPositionContents() {
        return positionContents;
    }

    public String getPlayerNumber() {
        return playerNumber;
    }

    public GameObject getEnemy() {
        return enemy;
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
