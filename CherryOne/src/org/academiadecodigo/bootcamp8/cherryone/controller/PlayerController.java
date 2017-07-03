package org.academiadecodigo.bootcamp8.cherryone.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import org.academiadecodigo.bootcamp8.cherryone.model.Player;
import org.academiadecodigo.bootcamp8.cherryone.service.Game;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerController implements Initializable {


    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    private Player player;

    private Game game;

    @FXML
    void scrollPaneKeyPressed(KeyEvent event) {
        System.out.println("keypressed");

        switch (event.getCode()){

            case UP:
                System.out.println("UP");

                if (player.getPosition().getRow() == 8){
                    return;
                }

                player.setPosition(player.getPosition().getCol(), player.getPosition().getRow() - 1);
                scrollPane.setVvalue(scrollPane.getVvalue() - 30.1);

                System.out.println("COL: " + player.getPosition().getCol());
                System.out.println("ROW: " + player.getPosition().getRow());

                break;

            case DOWN:
                System.out.println("DOWN");

                if (player.getPosition().getRow() == 99){
                    return;
                }

                player.setPosition(player.getPosition().getCol() - 0, player.getPosition().getRow() +1);
                scrollPane.setVvalue(scrollPane.getVvalue() + 30.1);

                System.out.println("COL: " + player.getPosition().getCol());
                System.out.println("ROW: " + player.getPosition().getRow());

                break;

            case LEFT:
                System.out.println("LEFT");

                if (player.getPosition().getCol() == 0){
                    return;
                }

                player.setPosition(player.getPosition().getCol() - 1, player.getPosition().getRow()-0);
                scrollPane.setHvalue(scrollPane.getHvalue() - 30.1);

                System.out.println("COL: " + player.getPosition().getCol());
                System.out.println("ROW: " + player.getPosition().getRow());

                break;

            case RIGHT:
                System.out.println("RIGHT");

                if (player.getPosition().getCol() == 99){
                    return;
                }

                player.setPosition(player.getPosition().getCol() + 1 , player.getPosition().getRow()-0);
                scrollPane.setHvalue(scrollPane.getHvalue() + 30.1);

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

    public void setPlayer(Player player){
        this.player=player;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        scrollPane.setVmax(2501);
        scrollPane.setHmax(2501);
        scrollPane.setPannable(false);

    }

}

