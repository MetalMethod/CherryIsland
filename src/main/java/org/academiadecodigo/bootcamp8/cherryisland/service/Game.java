package org.academiadecodigo.bootcamp8.cherryisland.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.application.Application;
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
    private boolean[] positionsOccupied;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Socket socket = new Socket("127.0.0.1", 6666);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String playerNumber;
            String start;

            gameObjectHashMap = new HashMap<>();
            positionsOccupied=new boolean[U.GRID_COLS*U.GRID_COLS];
            for(Boolean b:positionsOccupied){b=false;}

            playerNumber = reader.readLine();
            start = reader.readLine();

            if (playerNumber.equals("1")) {
                player = new Player(U.P1_STARTING_COL, U.P1_STARTING_ROW);
            }

            if (playerNumber.equals("2")) {
                player = new Player(U.P2_STARTING_COL, U.P2_STARTING_ROW);
            }



            if (start.equals("start") ) {

                Navigation.getInstance().setStage(primaryStage);
                primaryStage.setTitle("Cherry Island");


                Navigation.getInstance().loadScreen(U.INITIAL_VIEW);
                PlayerController playerController = (PlayerController) Navigation.getInstance().getController(U.INITIAL_VIEW);
                playerController.setGame(this);
                playerController.scrollPaneRequest();
                playerController.setPlayer1(player);

                gridPane = playerController.getGridPane();
                pane=playerController.getPane();
                scrollPane=playerController.getScrollPane();

                if(playerNumber.equals("2")) {
                  scrollPane.setVvalue(2500-725);
                    scrollPane.setHvalue(2500-725);
                }

                GameSend gameSend = new GameSend(socket);
                GameReceive gameReceive = new GameReceive(socket, this);

                Thread send = new Thread(gameSend);
                Thread receive = new Thread(gameReceive);

                send.start();
                receive.start();

                PrintWriter printWriter= new PrintWriter(socket.getOutputStream(),true);
                printWriter.println("start");
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
                //gameObjectHashMap.put(String.valueOf(col) + String.valueOf(row), cherries);
                gridPane.add(new ImageView("/gameobjects/cherrytree1.png"), col, row);
                break;

            case TREE:
                GameObject tree = GameObjectFactory.getObject(ObjectType.TREE, new GridPosition(col, row));
                //gameObjectHashMap.put(String.valueOf(col) + String.valueOf(row), tree);
                gridPane.add(new ImageView("/gameobjects/tree1.png"), col, row);
                break;

            case LAKE:
                GameObject lake = GameObjectFactory.getObject(ObjectType.LAKE, new GridPosition(col, row));
                //gameObjectHashMap.put(String.valueOf(col) + String.valueOf(row), lake);
                gridPane.add(new ImageView("/gameobjects/lake1.png"), col, row);
                for(int i=0;i<U.LAKECOLSPAN;i++){
                    for(int j=-1;j<2;j++){
                        positionsOccupied[(j*U.GRID_COLS)+U.GRID_COLS*row+col+i]=true;
                    }
                }
                break;

        }
        positionsOccupied[U.GRID_COLS*row+col]=true;
    }

    public boolean[] getPositionsOccupied(){return positionsOccupied;}


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
