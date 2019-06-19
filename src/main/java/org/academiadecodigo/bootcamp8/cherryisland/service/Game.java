package org.academiadecodigo.bootcamp8.cherryisland.service;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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
    private ScrollPane scrollPane;
    private Socket socket;
    private Navigation navigation;
    private String[] positionContents;
    public static String hostname;
    public static int port;
    private String playerNumber;
    private GameObject enemy;
    private ImageView enemyImg;
    private Label woodUpdate;
    private Sound gameSound;
    private Sound lake;
    private Sound cherries;
    private Sound tree;
    private Label ropeUpdate;
    private Label boatWoodUpdate;
    private Label boatRopeUpdate;

    @Override
    public void start(Stage primaryStage) throws Exception {
        gameSound = new Sound(SoundEnum.SOUNDTRACK.getPath());
        lake = new Sound(SoundEnum.DRINK.getPath());
        cherries = new Sound(SoundEnum.PICKING.getPath());
        tree = new Sound(SoundEnum.WOOD.getPath());

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
            if (hostname == null) {
                hostname = "127.0.0.1";
            }
            if (port == -1) {
                port = 6666;
            }
            socket = new Socket(hostname, port);

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
                player = new Player(Utils.P1_STARTING_COL, Utils.P1_STARTING_ROW);
                enemy = GameObjectFactory.getObject(ObjectType.ENEMY, new GridPosition(Utils.P2_STARTING_COL, Utils.P2_STARTING_ROW));
            }

            if (playerNumber.equals("2")) {
                player = new Player(Utils.P2_STARTING_COL, Utils.P2_STARTING_ROW);
                enemy = GameObjectFactory.getObject(ObjectType.ENEMY, new GridPosition(Utils.P1_STARTING_COL, Utils.P1_STARTING_ROW));
            }


            if (start.equals("start")) {
                gameSound.play(true);

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        synchronized (this) {
                            navigation.loadScreen(Utils.GAME_VIEW);

                            GameController gameController = (GameController) navigation.getController(Utils.GAME_VIEW);
                            gameController.setGame(getGame());
                            gameController.scrollPaneRequest();
                            gameController.setPlayer(player);

                            gridPane = gameController.getGridPane();
                            scrollPane = gameController.getScrollPane();
                            woodUpdate = gameController.getWoodCounter();
                            ropeUpdate = gameController.getRopeCounter();
                            boatRopeUpdate = gameController.getBoatRopeCounter();
                            boatWoodUpdate = gameController.getBoatWoodCounter();
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
                    enemyImg = new ImageView(ObjectType.ENEMY.getPath().get(0));
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            gridPane.add(enemyImg, Utils.P2_STARTING_COL, Utils.P2_STARTING_ROW);
                        }
                    });
                    positionContents[Utils.GRID_COLS * Utils.P2_STARTING_ROW + Utils.P2_STARTING_COL] = "enemy";
                }

                if (playerNumber.equals("2")) {
                    scrollPane.setVvalue(Utils.GRID_SIZE - Utils.VIEWPORT_SIZE);
                    scrollPane.setHvalue(Utils.GRID_SIZE - Utils.VIEWPORT_SIZE);
                    gameObjectHashMap.put(String.valueOf(Utils.P1_STARTING_COL) + String.valueOf(Utils.P1_STARTING_ROW), enemy);
                    enemyImg = new ImageView(ObjectType.ENEMY.getPath().get(0));
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {

        try {
            socket.close();
            System.exit(1);
        } catch (Exception ex) {
            System.out.println("Cherry Island is sad to see you go!");
        }
    }

    public void addGameObject(ObjectType objectType, int col, int row) {

        switch (objectType) {
            case BOAT:
                GameObject boat = GameObjectFactory.getObject(ObjectType.BOAT, new GridPosition(col, row));
                gameObjectHashMap.put(String.valueOf(col) + String.valueOf(row), boat);
                gridPane.add(new ImageView(ObjectType.BOAT.getPath().get(0)), col, row);
                for (int i = 0; i < Utils.BOAT_COL_SPAN; i++) {
                    for (int j = -1; j < 2; j++) {
                        positionContents[(j * Utils.GRID_COLS) + Utils.GRID_COLS * row + col + i] = ObjectType.BOAT.getName();
                    }
                }
                break;

            case ROCK:
                GameObject rock = GameObjectFactory.getObject(ObjectType.ROCK, new GridPosition(col, row));
                gameObjectHashMap.put(String.valueOf(col) + String.valueOf(row), rock);
                gridPane.add(new ImageView(ObjectType.ROCK.getPath().get(0)), col, row);
                positionContents[Utils.GRID_COLS * row + col] = ObjectType.ROCK.getName();
                break;

            case ROPE:
                GameObject rope = GameObjectFactory.getObject(ObjectType.ROPE, new GridPosition(col, row));
                gameObjectHashMap.put(String.valueOf(col) + String.valueOf(row), rope);
                gridPane.add(new ImageView(ObjectType.ROPE.getPath().get(0)), col, row);
                positionContents[Utils.GRID_COLS * row + col] = ObjectType.ROPE.getName();
                break;
        }
    }

    public void addGameObject(ObjectType objectType, int col, int row, int type) {

        switch (objectType) {
            case CHERRIES:
                GameObject cherries = GameObjectFactory.getObject(ObjectType.CHERRIES, new GridPosition(col, row));
                gameObjectHashMap.put(String.valueOf(col) + String.valueOf(row), cherries);
                gridPane.add(new ImageView(ObjectType.CHERRIES.getPath().get(type)), col, row);
                positionContents[Utils.GRID_COLS * row + col] = ObjectType.CHERRIES.getName();
                break;

            case TREE:
                GameObject tree = GameObjectFactory.getObject(ObjectType.TREE, new GridPosition(col, row));
                gameObjectHashMap.put(String.valueOf(col) + String.valueOf(row), tree);
                gridPane.add(new ImageView(ObjectType.TREE.getPath().get(type)), col, row);
                positionContents[Utils.GRID_COLS * row + col] = ObjectType.TREE.getName();
                break;

            case LAKE:
                GameObject lake = GameObjectFactory.getObject(ObjectType.LAKE, new GridPosition(col, row));
                gameObjectHashMap.put(String.valueOf(col) + String.valueOf(row), lake);
                gridPane.add(new ImageView(ObjectType.LAKE.getPath().get(type)), col, row);
                for (int i = 0; i < Utils.LAKE_COL_SPAN; i++) {
                    for (int j = -1; j < 2; j++) {
                        positionContents[(j * Utils.GRID_COLS) + Utils.GRID_COLS * row + col + i] = ObjectType.LAKE.getName();
                    }
                }
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
        String key = String.valueOf(col) + String.valueOf(row);
        gameObjectHashMap.remove(key);
        positionContents[Utils.GRID_COLS * row + col] = "empty";
    }

    public synchronized void moveGameObject(GameObject gameObject, int col, int row) {
        int currentCol = gameObject.getGridPosition().getCol();
        int currentRow = gameObject.getGridPosition().getRow();

        gridPane.getChildren().remove(enemyImg);
        gridPane.add(enemyImg, col, row);

        enemy.getGridPosition().setCol(col);
        enemy.getGridPosition().setRow(row);

        String key = String.valueOf(currentCol) + String.valueOf(currentRow);
        String keyNew = String.valueOf(col) + String.valueOf(row);
        gameObjectHashMap.remove(key);
        gameObjectHashMap.put(keyNew, gameObject);
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
                if (player.getHealth() < Utils.PLAYER_INIT_HEALTH / 2) {
                    player.raiseHealth(Utils.LAKE_HEAL_AMOUNT);
                    lake.play(true);
                }
                break;

            case "cherries":
                cherries.play(true);
                player.raiseHealth(Utils.CHERRY_HEAL_AMOUNT);
                gameSend("cherries remove " + facingCol + " " + facingRow);
                break;

            case "tree":
                tree.play(true);
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
        }
        woodUpdate.setText(String.valueOf(player.getWoodCounter()));
        ropeUpdate.setText(String.valueOf(player.getRopeCount()));
        boatWoodUpdate.setText(String.valueOf(player.getWoodInBoat()));
        boatRopeUpdate.setText(String.valueOf(player.getRopeInBoat()));
    }

    public void checkPlayerHealth() {
        if (player.getHealth() <= 0) {
            gameSound.stop();

            Navigation.getInstance().loadScreen("lose");

            Sound lose = new Sound(SoundEnum.LOSE.getPath());
            lose.setLoop(2);
            lose.play(true);
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

    public Sound getGameSound() {
        return gameSound;
    }

    private Game getGame() {
        return this;
    }

    public static void main(String[] args) {
        Game.hostname = null;
        Game.port = -1;
        if (args.length > 0) {
            Game.hostname = args[0];
        }
        if (args.length > 1) {
            Game.port = Integer.parseInt(args[1]);
        }
        launch(args);
    }
}
