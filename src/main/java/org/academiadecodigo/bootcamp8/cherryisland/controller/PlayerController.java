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

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
    private Rectangle enemy;

    private Socket socket;
    private ObjectOutputStream sockOut;

    @FXML
    void scrollPaneKeyPressed(KeyEvent event) {
        System.out.println("-------------");

        switch (event.getCode()) {

            case UP:
                System.out.println("UP");

                if (player1.getPosition().getRow() == 0) {
                    return;
                }

                player1.setPosition(player1.getPosition().getCol(), player1.getPosition().getRow() - 1);
                scrollPane.setVvalue(scrollPane.getVvalue() - 29.15);

                System.out.println("COL: " + player1.getPosition().getCol());
                System.out.println("ROW: " + player1.getPosition().getRow());

                break;

            case DOWN:
                System.out.println("DOWN");

                if (player1.getPosition().getRow() == 80) {
                    return;
                }

                player1.setPosition(player1.getPosition().getCol(), player1.getPosition().getRow() + 1);
                scrollPane.setVvalue(scrollPane.getVvalue() + 29.15);

                System.out.println("COL: " + player1.getPosition().getCol());
                System.out.println("ROW: " + player1.getPosition().getRow());

                break;

            case LEFT:
                System.out.println("LEFT");

                if (player1.getPosition().getCol() == 0) {
                    return;
                }

                player1.setPosition(player1.getPosition().getCol() - 1, player1.getPosition().getRow());
                scrollPane.setHvalue(scrollPane.getHvalue() - 33.12);

                System.out.println("COL: " + player1.getPosition().getCol());
                System.out.println("ROW: " + player1.getPosition().getRow());

                break;

            case RIGHT:
                System.out.println("RIGHT");

                if (player1.getPosition().getCol() == 90) {
                    return;
                }

                player1.setPosition(player1.getPosition().getCol() + 1, player1.getPosition().getRow());
                scrollPane.setHvalue(scrollPane.getHvalue() + 33.12);

                System.out.println("COL: " + player1.getPosition().getCol());
                System.out.println("ROW: " + player1.getPosition().getRow());

                break;
        }
        int[] currpos = {player1.getPosition().getRow(), player1.getPosition().getCol()};
        try {
            sockOut.writeObject(currpos);
            sockOut.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void setGame(Game game) {
        this.game = game;
    }

    public void scrollPaneRequest() {
        scrollPane.requestFocus();
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        scrollPane.setVmax(2501);
        scrollPane.setHmax(2501);
        scrollPane.setPannable(false);

    }


    public void setEnemyPos(int row, int col) {
        enemy.setX( col + 8);//magic number is horizontal padding
        enemy.setY( row + 8);//magic number is vertical padding
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
        try {
            sockOut = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

