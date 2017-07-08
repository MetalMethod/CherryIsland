package org.academiadecodigo.bootcamp8.cherryisland.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.academiadecodigo.bootcamp8.cherryisland.model.Direction;
import org.academiadecodigo.bootcamp8.cherryisland.model.Player;
import org.academiadecodigo.bootcamp8.cherryisland.service.Game;
import org.academiadecodigo.bootcamp8.cherryisland.util.U;
import org.academiadecodigo.bootcamp8.cherryisland.service.PlayerService;
import org.academiadecodigo.bootcamp8.cherryisland.service.ServiceRegistry;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerController implements Initializable {

    private PlayerService playerService;
    private Player player1;
    private Game game;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    public Pane getPane() {
        return pane;
    }

    @FXML
    private Pane pane;

    //private Rectangle enemy;

    @FXML
    void scrollPaneKeyPressed(KeyEvent event) {
        System.out.println("-------------");

        switch (event.getCode()){

            case UP:
                player1.setDirection(Direction.UP);
                System.out.println("UP");

                if (player1.getPosition().getRow() == 14 ||
                        !game.getPositionContents()[U.GRID_COLS*(player1.getPosition().getRow()-1)
                                +player1.getPosition().getCol()].equals("empty")){
                    return;
                }

                player1.setPosition(player1.getPosition().getCol(), player1.getPosition().getRow() - 1);
                scrollPane.setVvalue(scrollPane.getVvalue() - U.COL_ROW_SIZE);

                System.out.println("COL: " + player1.getPosition().getCol());
                System.out.println("ROW: " + player1.getPosition().getRow());

                game.gameSend(game.getPlayerNumber()+" move "+
                        player1.getPosition().getCol()+" "+player1.getPosition().getRow());
                player1.loseHealth();
                break;

            case DOWN:
                player1.setDirection(Direction.DOWN);
                System.out.println("DOWN");

                if (player1.getPosition().getRow() == 85 ||
                        !game.getPositionContents()[U.GRID_COLS*(player1.getPosition().getRow()+1)
                                +player1.getPosition().getCol()].equals("empty")){
                    return;
                }

                player1.setPosition(player1.getPosition().getCol(), player1.getPosition().getRow() +1);
                scrollPane.setVvalue(scrollPane.getVvalue() + U.COL_ROW_SIZE);

                System.out.println("COL: " + player1.getPosition().getCol());
                System.out.println("ROW: " + player1.getPosition().getRow());

                game.gameSend(game.getPlayerNumber()+" move "+
                        player1.getPosition().getCol()+" "+player1.getPosition().getRow());
                player1.loseHealth();
                break;

            case LEFT:
                player1.setDirection(Direction.LEFT);
                System.out.println("LEFT");

                if (player1.getPosition().getCol() == 14 ||
                        !game.getPositionContents()[U.GRID_COLS*(player1.getPosition().getRow())
                                +player1.getPosition().getCol()-1].equals("empty")){
                    return;
                }

                player1.setPosition(player1.getPosition().getCol() - 1, player1.getPosition().getRow());
                scrollPane.setHvalue(scrollPane.getHvalue() - U.COL_ROW_SIZE);

                System.out.println("COL: " + player1.getPosition().getCol());
                System.out.println("ROW: " + player1.getPosition().getRow());

                game.gameSend(game.getPlayerNumber()+" move "+
                        player1.getPosition().getCol()+" "+player1.getPosition().getRow());
                player1.loseHealth();
                break;

            case RIGHT:
                player1.setDirection(Direction.RIGHT);
                System.out.println("RIGHT");

                if (player1.getPosition().getCol() == 85 ||
                        !game.getPositionContents()[U.GRID_COLS*(player1.getPosition().getRow())
                                +player1.getPosition().getCol()+1].equals("empty")){
                    return;
                }

                player1.setPosition(player1.getPosition().getCol() + 1 , player1.getPosition().getRow());
                scrollPane.setHvalue(scrollPane.getHvalue() + U.COL_ROW_SIZE);

                System.out.println("COL: " + player1.getPosition().getCol());
                System.out.println("ROW: " + player1.getPosition().getRow());

                game.gameSend(game.getPlayerNumber()+" move "+
                        player1.getPosition().getCol()+" "+player1.getPosition().getRow());
                player1.loseHealth();
                break;

            case Z:
                System.out.println("Trying to take action");

                takeAction();

                break;
        }
    }

    private void takeAction(){
        //1-check player direction
        //2- check if there is a lake, cherries, tree or beach in the position player is facing
        //3-take corresponding action if there is something (get health from lake, cut tree to get wood, take cherries, build boat)
        //4-send corresponding message to server:"tree (re)move col row", "cherries (re)move col row", or "player_ wins"
    }


    public void setGame(Game game) {
        this.game = game;
    }

    public void scrollPaneRequest(){
        scrollPane.requestFocus();
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setPlayer1(Player player1){
        this.player1 = player1;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerService = (PlayerService) ServiceRegistry.getInstance().getService(PlayerService.class.getSimpleName());

        scrollPane.setVmax(2500-725);
        scrollPane.setHmax(2500-725);
        scrollPane.setPannable(false);

    }

    /* Minimap
    public void setEnemyPos(int row, int col) {
        enemy.setX( col + 8);//magic number is horizontal padding
        enemy.setY( row + 8);//magic number is vertical padding
    }
    */

}

