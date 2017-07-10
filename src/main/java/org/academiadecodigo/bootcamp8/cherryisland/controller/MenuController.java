package org.academiadecodigo.bootcamp8.cherryisland.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.academiadecodigo.bootcamp8.cherryisland.Navigation;
import org.academiadecodigo.bootcamp8.cherryisland.service.Game;
import org.academiadecodigo.bootcamp8.cherryisland.service.PlayerService;
import org.academiadecodigo.bootcamp8.cherryisland.sound.Sound;
import org.academiadecodigo.bootcamp8.cherryisland.sound.SoundEnum;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by dgcst on 01/07/2017.
 */

public class MenuController implements Initializable {

    private PlayerService playerService;
    private String nickname;
    private Game game;
    private Sound waiting;
    private Sound menu;

    @FXML
    TextField textFieldNickname;

    @FXML
    Label labelInfo;

    @FXML
    Label labelNickname;

    @FXML
    Button buttonReady;

    @FXML
    ImageView imageViewWaiting;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerService = PlayerService.getInstance();
        waiting = new Sound(SoundEnum.WAITING.getPath());
        menu = new Sound(SoundEnum.MENU.getPath());
        menu.setLoop(10);
        menu.play(true);
    }

    public void onReadyButtonClick() {
        menu.stop();
        waiting.setLoop(10);
        waiting.play(true);

        if (labelNickname.isVisible()) {
            return;
        }

        nickname = textFieldNickname.getText();
        if (nickname.equals("") || playerService.playerExists(nickname)) {
            labelInfo.setStyle("-fx-text-fill: red;");
            labelInfo.setText("Invalid nickname, choose another.");
            return;
        }
        playerService.addPlayer(nickname);
        waitForGame();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                game.connection();
                waiting.stop();
            }
        });
        thread.start();
    }

    public void onCloseButtonClick() {
        Stage stage = (Stage) Navigation.getInstance().getScene().getWindow();
        stage.close();
        System.exit(0);
    }

    private void waitForGame() {
        buttonReady.setStyle("-fx-background-color: green;");
        labelInfo.setStyle("-fx-text-fill: black;");
        labelInfo.setText("Please wait for the game to start...");
        textFieldNickname.setVisible(false);
        labelNickname.setText(nickname);
        labelNickname.setVisible(true);
        imageViewWaiting.setVisible(true);
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
