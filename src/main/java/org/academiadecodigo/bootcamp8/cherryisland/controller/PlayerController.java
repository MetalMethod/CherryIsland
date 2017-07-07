package org.academiadecodigo.bootcamp8.cherryisland.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import org.academiadecodigo.bootcamp8.cherryisland.model.Player;
import org.academiadecodigo.bootcamp8.cherryisland.service.Game;
import org.academiadecodigo.bootcamp8.cherryisland.util.U;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    public Pane getPane() {
        return pane;
    }

    @FXML
    private Pane pane;

    private Player player1;

    private Game game;

    //private Rectangle enemy;

    @FXML
    void scrollPaneKeyPressed(KeyEvent event) {
        System.out.println("-------------");

        switch (event.getCode()){

            case UP:
                System.out.println("UP");

                if (player1.getPosition().getRow() == 14){
                    return;
                }

                player1.setPosition(player1.getPosition().getCol(), player1.getPosition().getRow() - 1);
                scrollPane.setVvalue(scrollPane.getVvalue() - U.COL_ROW_SIZE);

                System.out.println("COL: " + player1.getPosition().getCol());
                System.out.println("ROW: " + player1.getPosition().getRow());

                break;

            case DOWN:
                System.out.println("DOWN");

                if (player1.getPosition().getRow() == 85){
                    return;
                }

                player1.setPosition(player1.getPosition().getCol(), player1.getPosition().getRow() +1);
                scrollPane.setVvalue(scrollPane.getVvalue() + U.COL_ROW_SIZE);

                System.out.println("COL: " + player1.getPosition().getCol());
                System.out.println("ROW: " + player1.getPosition().getRow());

                break;

            case LEFT:
                System.out.println("LEFT");

                if (player1.getPosition().getCol() == 14){
                    return;
                }

                player1.setPosition(player1.getPosition().getCol() - 1, player1.getPosition().getRow());
                scrollPane.setHvalue(scrollPane.getHvalue() - U.COL_ROW_SIZE);

                System.out.println("COL: " + player1.getPosition().getCol());
                System.out.println("ROW: " + player1.getPosition().getRow());

                break;

            case RIGHT:
                System.out.println("RIGHT");

                if (player1.getPosition().getCol() == 85){
                    return;
                }

                player1.setPosition(player1.getPosition().getCol() + 1 , player1.getPosition().getRow());
                scrollPane.setHvalue(scrollPane.getHvalue() + U.COL_ROW_SIZE);

                System.out.println("COL: " + player1.getPosition().getCol());
                System.out.println("ROW: " + player1.getPosition().getRow());

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

    public void setPlayer1(Player player1){
        this.player1 = player1;
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

