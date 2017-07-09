package org.academiadecodigo.bootcamp8.cherryisland.service;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.academiadecodigo.bootcamp8.cherryisland.Navigation;
import org.academiadecodigo.bootcamp8.cherryisland.controller.GameController;
import org.academiadecodigo.bootcamp8.cherryisland.gameObjects.GameObject;
import org.academiadecodigo.bootcamp8.cherryisland.gameObjects.GameObjectFactory;
import org.academiadecodigo.bootcamp8.cherryisland.gameObjects.ObjectType;
import org.academiadecodigo.bootcamp8.cherryisland.model.Direction;
import org.academiadecodigo.bootcamp8.cherryisland.model.GridPosition;
import org.academiadecodigo.bootcamp8.cherryisland.model.Player;
import org.academiadecodigo.bootcamp8.cherryisland.sound.Sound;
import org.academiadecodigo.bootcamp8.cherryisland.sound.SoundEnum;
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
    private Label woodUpdate;
    private Sound sound = new Sound(SoundEnum.SOUNDTRACK.getPath());

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
                sound.play(true);

                Navigation.getInstance().loadScreen(U.INITIAL_VIEW);
                GameController gameController = (GameController) Navigation.getInstance().getController(U.INITIAL_VIEW);
                gameController.setGame(this);
                gameController.scrollPaneRequest();
                gameController.setPlayer(player);

                gridPane = gameController.getGridPane();
                pane = gameController.getPane();
                scrollPane = gameController.getScrollPane();
                woodUpdate = gameController.getWoodCounter();

                if (playerNumber.equals("1")) {
                    gameObjectHashMap.put(String.valueOf(U.P2_STARTING_COL) + String.valueOf(U.P2_STARTING_ROW), enemy);
                    enemyImg = new ImageView(ObjectType.ENEMY.getPath());
                    gridPane.add(enemyImg, U.P2_STARTING_COL, U.P2_STARTING_ROW);
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
        Game.hostname=null;
        if(args.length > 0){
            Game.hostname=args[0];
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
                for (int i = 0; i < U.LAKE_COL_SPAN; i++) {
                    for (int j = -1; j < 2; j++) {
                        positionContents[(j * U.GRID_COLS) + U.GRID_COLS * row + col + i] = "lake";
                    }
                }
                break;
        }

    }

    public void removeGameObject(int col, int row) {

        for(Node n:gridPane.getChildren()){
            if(GridPane.getRowIndex(n)==row && GridPane.getColumnIndex(n)==col){
                ((ImageView)n).setImage(null);
            }
        }
        gridPane.getChildren().remove(gameObjectHashMap.get(String.valueOf(col) + String.valueOf(row)));
        //stackoverflow says row and col(cont col and row)
        String key = String.valueOf(col) + String.valueOf(row);
        gameObjectHashMap.remove(key);
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

    public void takeAction() {
        Direction dir = player.getDirection();
        int playerPos = U.GRID_COLS * player.getPosition().getRow() + player.getPosition().getCol();
        int facingPos = 0;
        switch (dir) {
            case UP:
                facingPos = playerPos - U.GRID_COLS;
                break;
            case LEFT:
                facingPos = playerPos - 1;
                break;
            case RIGHT:
                facingPos = playerPos + 1;
                break;
            case DOWN:
                facingPos = playerPos + U.GRID_COLS;
                break;
        }
        int facingCol = facingPos % U.GRID_COLS;
        int facingRow = (facingPos / U.GRID_COLS);
        switch (positionContents[facingPos]) {
            case "lake":
                player.raiseHealth(U.LAKE_HEAL_AMOUNT);
                break;
            case "cherries":
                removeGameObject(facingCol, facingRow);
                player.raiseHealth(U.CHERRY_HEAL_AMOUNT);
                gameSend("cherries remove " + facingCol + " " + facingRow);
                break;
            case "tree":
                if (!player.carryMoreWood()) {
                    break;
                }
                removeGameObject(facingCol, facingRow);
                player.pickWood();
                gameSend("tree remove " + facingCol + " " + facingRow);
                break;
            default:
                if (player.getPosition().getCol() == 14 ||
                        player.getPosition().getCol() == 85 || player.getPosition().getRow() == 14
                        || player.getPosition().getRow() == 85) {
                    boolean hasBuilt = player.buildBoat();
                    if (hasBuilt) {
                        gameSend(playerNumber + " wins");
                    }
                }
                break;

        }

        woodUpdate.setText(String.valueOf(player.getWoodCounter()));
        System.out.println(String.valueOf(player.getWoodCounter()));
        //1-check player direction
        //2- check if there is a lake, cherries, tree or beach in the position player is facing
        //3-take corresponding action if there is something (get health from lake, cut tree to get wood, take cherries, build boat)
        //4-send corresponding message to server:"tree (re)move col row", "cherries (re)move col row", or "player_ wins"
    }

    public void checkPlayerHealth(){
        if(player.getHealth() <=0){
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
