package org.academiadecodigo.bootcamp8.cherryisland.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import org.academiadecodigo.bootcamp8.cherryisland.model.Player;
import org.academiadecodigo.bootcamp8.cherryisland.service.Game;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    private Player player1;

    private Game game;

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
                scrollPane.setVvalue(scrollPane.getVvalue() - 25);

                System.out.println("COL: " + player1.getPosition().getCol());
                System.out.println("ROW: " + player1.getPosition().getRow());

                break;

            case DOWN:
                System.out.println("DOWN");

                if (player1.getPosition().getRow() == 85){
                    return;
                }

                player1.setPosition(player1.getPosition().getCol(), player1.getPosition().getRow() +1);
                scrollPane.setVvalue(scrollPane.getVvalue() + 25);

                System.out.println("COL: " + player1.getPosition().getCol());
                System.out.println("ROW: " + player1.getPosition().getRow());

                break;

            case LEFT:
                System.out.println("LEFT");

                if (player1.getPosition().getCol() == 14){
                    return;
                }

                player1.setPosition(player1.getPosition().getCol() - 1, player1.getPosition().getRow());
                scrollPane.setHvalue(scrollPane.getHvalue() - 25);

                System.out.println("COL: " + player1.getPosition().getCol());
                System.out.println("ROW: " + player1.getPosition().getRow());

                break;

            case RIGHT:
                System.out.println("RIGHT");

                if (player1.getPosition().getCol() == 85){
                    return;
                }

                player1.setPosition(player1.getPosition().getCol() + 1 , player1.getPosition().getRow());
                scrollPane.setHvalue(scrollPane.getHvalue() + 25);

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

    public void setPlayer1(Player player1){
        this.player1 = player1;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        scrollPane.setVmax(2500-725);
        scrollPane.setHmax(2500-725);
        scrollPane.setPannable(false);

    }
}

