package org.academiadecodigo.bootcamp8.cherryisland.service;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.academiadecodigo.bootcamp8.cherryisland.Navigation;
import org.academiadecodigo.bootcamp8.cherryisland.controller.GameController;
import org.academiadecodigo.bootcamp8.cherryisland.controller.MenuController;
import org.academiadecodigo.bootcamp8.cherryisland.gameObjects.GameObject;
import org.academiadecodigo.bootcamp8.cherryisland.gameObjects.GameObjectFactory;
import org.academiadecodigo.bootcamp8.cherryisland.gameObjects.ObjectType;
import org.academiadecodigo.bootcamp8.cherryisland.model.Direction;
import org.academiadecodigo.bootcamp8.cherryisland.model.GridPosition;
import org.academiadecodigo.bootcamp8.cherryisland.model.Player;
import org.academiadecodigo.bootcamp8.cherryisland.util.Utils;
import org.academiadecodigo.bootcamp8.cherryisland.sound.Sound;
import org.academiadecodigo.bootcamp8.cherryisland.sound.SoundEnum;
import org.academiadecodigo.bootcamp8.cherryisland.sound.Sound;

import java.io.*;
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
    private Navigation navigation;
    private String[] positionContents;
    public static String hostname;
    private String playerNumber;
    private GameObject enemy;
    private ImageView enemyImg;
    private Label woodUpdate;
    private Label ropeUpdate;
    private Sound sound = new Sound(SoundEnum.SOUNDTRACK.getPath());

    @Override
    public void start(Stage primaryStage) throws Exception {
        navigation = Navigation.getInstance();
        primaryStage.setTitle("Cherry Island");
        primaryStage.setResizable(false);
        navigation.setStage(primaryStage);

        navigation.loadScreen(Utils.INITIAL_VIEW);

        MenuController menuController = (MenuController) navigation.getController(Utils.INITIAL_VIEW);
        menuController.setGame(this);
    }

    public void connection() {
        try {
            if (hostname != null) {
                socket = new Socket(hostname, 6666);
            } else {
                socket = new Socket("127.0.0.1", 6666);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String start;

            gameObjectHashMap = new HashMap<>();
            positionContents = new String[Utils.GRID_COLS * Utils.GRID_COLS];
            for (int i = 0; i < positionContents.length; i++) {
                positionContents[i] = "empty";
            }

            playerNumber = reader.readLine();
            start = reader.readLine();

            if (playerNumber.equals("1")) {
                System.out.println("T3");
                player = new Player(Utils.P1_STARTING_COL, Utils.P1_STARTING_ROW);
                enemy = GameObjectFactory.getObject(ObjectType.ENEMY, new GridPosition(Utils.P2_STARTING_COL, Utils.P2_STARTING_ROW));
            }

            if (playerNumber.equals("2")) {
                System.out.println("T4");
                player = new Player(Utils.P2_STARTING_COL, Utils.P2_STARTING_ROW);
                enemy = GameObjectFactory.getObject(ObjectType.ENEMY, new GridPosition(Utils.P1_STARTING_COL, Utils.P1_STARTING_ROW));
            }


            if (start.equals("start")) {

                final Game game = this; //TODO improve
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        synchronized (this) {
                            navigation.loadScreen(Utils.GAME_VIEW);
                            sound.play(true);

                            GameController gameController = (GameController) navigation.getController(Utils.GAME_VIEW);
                            gameController.setGame(game);
                            gameController.scrollPaneRequest();
                            gameController.setPlayer(player);

                            gridPane = gameController.getGridPane();
                            scrollPane = gameController.getScrollPane();
                            woodUpdate = gameController.getWoodCounter();
                            ropeUpdate = gameController.getRopeCounter();
                            notifyAll();
                        }
                    }
                };

                Platform.runLater(runnable);

                synchronized (runnable) {
                    while (gridPane == null) {
                        try {
                            runnable.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (playerNumber.equals("1")) {
                    gameObjectHashMap.put(String.valueOf(Utils.P2_STARTING_COL) + String.valueOf(Utils.P2_STARTING_ROW), enemy);
                    enemyImg = new ImageView(ObjectType.ENEMY.getPath());
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            gridPane.add(enemyImg, Utils.P2_STARTING_COL, Utils.P2_STARTING_ROW);
                        }
                    });
                    positionContents[Utils.GRID_COLS * Utils.P2_STARTING_ROW + Utils.P2_STARTING_COL] = "enemy";
                }

                if (playerNumber.equals("2")) {
                    scrollPane.setVvalue(2500 - 725);
                    scrollPane.setHvalue(2500 - 725);
                    gameObjectHashMap.put(String.valueOf(Utils.P1_STARTING_COL) + String.valueOf(Utils.P1_STARTING_ROW), enemy);
                    enemyImg = new ImageView(ObjectType.ENEMY.getPath());
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            gridPane.add(enemyImg, Utils.P1_STARTING_COL, Utils.P1_STARTING_ROW);
                        }
                    });
                    positionContents[Utils.GRID_COLS * Utils.P1_STARTING_ROW + Utils.P1_STARTING_COL] = "enemy";
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
                gridPane.add(new ImageView(ObjectType.CHERRIES.getPath()), col, row);
                positionContents[Utils.GRID_COLS * row + col] = ObjectType.CHERRIES.getName();
                break;

            case TREE:
                GameObject tree = GameObjectFactory.getObject(ObjectType.TREE, new GridPosition(col, row));
                gameObjectHashMap.put(String.valueOf(col) + String.valueOf(row), tree);
                gridPane.add(new ImageView(ObjectType.TREE.getPath()), col, row);
                positionContents[Utils.GRID_COLS * row + col] = ObjectType.TREE.getName();
                break;

            case LAKE:
                GameObject lake = GameObjectFactory.getObject(ObjectType.LAKE, new GridPosition(col, row));
                gameObjectHashMap.put(String.valueOf(col) + String.valueOf(row), lake);
                gridPane.add(new ImageView(ObjectType.LAKE.getPath()), col, row);
                for (int i = 0; i < Utils.LAKE_COL_SPAN; i++) {
                    for (int j = -1; j < 2; j++) {
                        positionContents[(j * Utils.GRID_COLS) + Utils.GRID_COLS * row + col + i] = ObjectType.LAKE.getName();
                    }
                }
                break;

            case BOAT:
                GameObject boat = GameObjectFactory.getObject(ObjectType.BOAT, new GridPosition(col, row));
                gameObjectHashMap.put(String.valueOf(col) + String.valueOf(row), boat);
                gridPane.add(new ImageView(ObjectType.BOAT.getPath()), col, row);
                for (int i = 0; i < Utils.BOAT_COLSPAN; i++) {
                    for (int j = -1; j < 2; j++) {
                        positionContents[(j * Utils.GRID_COLS) + Utils.GRID_COLS * row + col + i] = ObjectType.BOAT.getName();
                    }
                }
                break;

            case ROCK:
                GameObject rock = GameObjectFactory.getObject(ObjectType.ROCK, new GridPosition(col, row));
                gameObjectHashMap.put(String.valueOf(col) + String.valueOf(row), rock);
                gridPane.add(new ImageView(ObjectType.ROCK.getPath()), col, row);
                positionContents[Utils.GRID_COLS * row + col] = ObjectType.ROCK.getName();
                break;

            case ROPE:
                GameObject rope = GameObjectFactory.getObject(ObjectType.ROPE, new GridPosition(col, row));
                gameObjectHashMap.put(String.valueOf(col) + String.valueOf(row), rope);
                gridPane.add(new ImageView(ObjectType.ROPE.getPath()), col, row);
                positionContents[Utils.GRID_COLS * row + col] = ObjectType.ROPE.getName();
                break;


        }

    }

    public void removeGameObject(int col, int row) {

        for (Node node : gridPane.getChildren()) {
            if ((GridPane.getRowIndex(node) == row) && (GridPane.getColumnIndex(node) == col)) {
                ((ImageView) node).setImage(null);
            }
        }
        gridPane.getChildren().remove(gameObjectHashMap.get(String.valueOf(col) + String.valueOf(row)));
        //stackoverflow says row and col(cont col and row)
        String key = String.valueOf(col) + String.valueOf(row);
        gameObjectHashMap.remove(key);
        positionContents[Utils.GRID_COLS * row + col] = "empty";
    }

    public synchronized void moveGameObject(GameObject gameObject, int col, int row) {
        int currentCol = gameObject.getGridPosition().getCol();
        int currentRow = gameObject.getGridPosition().getRow();


        System.out.println(col + " " + row + " " + currentCol + " " + currentRow);

        gridPane.getChildren().remove(enemyImg);
        gridPane.add(enemyImg, col, row);

        enemy.getGridPosition().setCol(col);
        enemy.getGridPosition().setRow(row);
        //stackoverflow says row and col(cont col and row)
        String key = String.valueOf(currentCol) + String.valueOf(currentRow);
        String keynew = String.valueOf(col) + String.valueOf(row);
        gameObjectHashMap.remove(key);
        gameObjectHashMap.put(keynew, gameObject);
        positionContents[Utils.GRID_COLS * currentRow + currentCol] = "empty";
        positionContents[Utils.GRID_COLS * row + col] = gameObject.getObjectType().getName();
    }

    public void gameSend(String msgToSend) {
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(msgToSend);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void takeAction() {
        Direction dir = player.getDirection();
        int playerPos = Utils.GRID_COLS * player.getPosition().getRow() + player.getPosition().getCol();
        int facingPos = 0;
        switch (dir) {
            case UP:
                facingPos = playerPos - Utils.GRID_COLS;
                break;
            case LEFT:
                facingPos = playerPos - 1;
                break;
            case RIGHT:
                facingPos = playerPos + 1;
                break;
            case DOWN:
                facingPos = playerPos + Utils.GRID_COLS;
                break;
        }
        int facingCol = facingPos % Utils.GRID_COLS;
        int facingRow = (facingPos / Utils.GRID_COLS);
        switch (positionContents[facingPos]) {
            case "lake":
                player.raiseHealth(Utils.LAKE_HEAL_AMOUNT);
                break;
            case "cherries":
                player.raiseHealth(Utils.CHERRY_HEAL_AMOUNT);
                gameSend("cherries remove " + facingCol + " " + facingRow);
                break;
            case "tree":
                if (!player.carryMoreWood()) {
                    break;
                }
                player.pickWood();
                gameSend("tree remove " + facingCol + " " + facingRow);
                break;
            case "boat":
                player.depositWood();
                player.depositRope();
                if (player.buildBoat()) {
                    gameSend(playerNumber + " wins");
                }
                break;

            case "rope":
                if (!player.carryMoreRope()) {
                    break;
                }
                player.pickRope();
                gameSend("rope remove " + facingCol + " " + facingRow);
            default:
                /*
                if (player.getPosition().getCol() == 14 ||
                        player.getPosition().getCol() == 85 || player.getPosition().getRow() == 14
                        || player.getPosition().getRow() == 85) {
                    boolean hasBuilt = player.buildBoat();
                    if (hasBuilt) {
                        gameSend(playerNumber + " wins");
                    }
                }
                */
                break;

        }

        woodUpdate.setText(String.valueOf(player.getWoodCounter()));
        ropeUpdate.setText(String.valueOf(player.getRopeCount()));
        System.out.println(String.valueOf(player.getWoodCounter()));
        //1-check player direction
        //2- check if there is a lake, cherries, tree or beach in the position player is facing
        //3-take corresponding action if there is something (get health from lake, cut tree to get wood, take cherries, build boat)
        //4-send corresponding message to server:"tree (re)move col row", "cherries (re)move col row", or "player_ wins"
    }

    public void checkPlayerHealth() {
        if (player.getHealth() <= 0) {
            Navigation.getInstance().loadScreen("youlose");
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
