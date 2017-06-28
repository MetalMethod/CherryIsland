package sample.controller;

import javafx.fxml.Initializable;
import sample.model.Player;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerController implements Initializable {

    private Player player;

    @Override
    public void initialize(URL lo1cation, ResourceBundle resources) {}

    public void setPlayer(Player player) {
        this.player = player;
    }
}
