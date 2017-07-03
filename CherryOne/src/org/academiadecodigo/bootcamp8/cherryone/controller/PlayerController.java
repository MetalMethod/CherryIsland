package org.academiadecodigo.bootcamp8.cherryone.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import org.academiadecodigo.bootcamp8.cherryone.service.Game;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerController implements Initializable {


    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private Rectangle player;

    private Game game;

    @FXML
    void scrollPaneKeyPressed(KeyEvent event) {
        System.out.println("keypressed");

        switch (event.getCode()){

            case UP:
                System.out.println("UP");
                System.out.println(scrollPane.getVvalue());

                scrollPane.setVvalue(scrollPane.getVvalue() - 30.1);

                System.out.println(scrollPane.getVvalue());
                break;

            case DOWN:
                System.out.println("DOWN");
                System.out.println(scrollPane.getVvalue());

                scrollPane.setVvalue(scrollPane.getVvalue() + 30.1);

                System.out.println(scrollPane.getVvalue());
                break;

            case LEFT:
                System.out.println("LEFT");
                System.out.println(scrollPane.getHvalue());

                scrollPane.setHvalue(scrollPane.getHvalue() - 30.1);

                System.out.println(scrollPane.getHvalue());
                break;

            case RIGHT:
                System.out.println("RIGHT");
                System.out.println(scrollPane.getHvalue());

                scrollPane.setHvalue(scrollPane.getHvalue() + 30.1);

                System.out.println(scrollPane.getHvalue());
                break;



        }

    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void scrollPaneRequest(){
        scrollPane.requestFocus();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        scrollPane.setVmax(2501);
        scrollPane.setHmax(2501);
        scrollPane.setPannable(false);

    }
}

