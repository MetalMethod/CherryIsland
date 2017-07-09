package org.academiadecodigo.bootcamp8.cherryisland.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.academiadecodigo.bootcamp8.cherryisland.model.Player;
import org.academiadecodigo.bootcamp8.cherryisland.service.Game;
import org.academiadecodigo.bootcamp8.cherryisland.util.U;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerController implements Initializable {

    private Player player;
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
                System.out.println("UP");

                if (player.getPosition().getRow() == 14 ||
                        !game.getPositionContents()[U.GRID_COLS*(player.getPosition().getRow()-1)
                                + player.getPosition().getCol()].equals("empty")){
                    return;
                }

                player.setPosition(player.getPosition().getCol(), player.getPosition().getRow() - 1);
                scrollPane.setVvalue(scrollPane.getVvalue() - U.COL_ROW_SIZE);

                System.out.println("COL: " + player.getPosition().getCol());
                System.out.println("ROW: " + player.getPosition().getRow());

                break;

            case DOWN:
                System.out.println("DOWN");

                if (player.getPosition().getRow() == 85 ||
                        !game.getPositionContents()[U.GRID_COLS*(player.getPosition().getRow()+1)
                                + player.getPosition().getCol()].equals("empty")){
                    return;
                }

                player.setPosition(player.getPosition().getCol(), player.getPosition().getRow() +1);
                scrollPane.setVvalue(scrollPane.getVvalue() + U.COL_ROW_SIZE);

                System.out.println("COL: " + player.getPosition().getCol());
                System.out.println("ROW: " + player.getPosition().getRow());

                break;

            case LEFT:
                System.out.println("LEFT");

                if (player.getPosition().getCol() == 14 ||
                        !game.getPositionContents()[U.GRID_COLS*(player.getPosition().getRow())
                                + player.getPosition().getCol()-1].equals("empty")){
                    return;
                }

                player.setPosition(player.getPosition().getCol() - 1, player.getPosition().getRow());
                scrollPane.setHvalue(scrollPane.getHvalue() - U.COL_ROW_SIZE);

                System.out.println("COL: " + player.getPosition().getCol());
                System.out.println("ROW: " + player.getPosition().getRow());

                break;

            case RIGHT:
                System.out.println("RIGHT");

                if (player.getPosition().getCol() == 85 ||
                        !game.getPositionContents()[U.GRID_COLS*(player.getPosition().getRow())
                                + player.getPosition().getCol()+1].equals("empty")){
                    return;
                }

                player.setPosition(player.getPosition().getCol() + 1 , player.getPosition().getRow());
                scrollPane.setHvalue(scrollPane.getHvalue() + U.COL_ROW_SIZE);

                System.out.println("COL: " + player.getPosition().getCol());
                System.out.println("ROW: " + player.getPosition().getRow());

                break;
        }
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

    public void setPlayer(Player player){
        this.player = player;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

