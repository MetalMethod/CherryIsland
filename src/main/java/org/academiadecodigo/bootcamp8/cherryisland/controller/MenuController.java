package org.academiadecodigo.bootcamp8.cherryisland.controller;

import javafx.application.Platform;
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

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;

/**
 * Created by dgcst on 01/07/2017.
 */

public class MenuController implements Initializable {

    private PlayerService playerService;
    private String nickname;
    private Game game;

    @FXML
    ImageView imageViewSplash;

    @FXML
    TextField textFieldNickname;

    @FXML
    Label labelInfo;

    @FXML
    Label labelNickname;

    @FXML
    Button readyButton;

    @FXML
    ImageView imageViewWaiting;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerService = PlayerService.getInstance();
    }

    public void onReadyButtonClick() {
        if (labelNickname.isVisible()) {
            return;
        }
        nickname = textFieldNickname.getText();
        if (nickname.equals("") || playerService.playerExists(nickname)) {
            labelInfo.setStyle("-fx-text-fill: red;");
            labelInfo.setText("Ye can nah choose that name!");
            return;
        }
        playerService.addPlayer(nickname);
        waitForGame();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                game.connection();
            }
        });
        thread.run();
        System.out.println("done");
        //game.connection();
    }

    public void onCloseButtonClick() {
        Stage stage = (Stage) Navigation.getInstance().getScene().getWindow();
        stage.close();
    }

    private void waitForGame() {
        readyButton.setStyle("-fx-background-color: green;");
        labelInfo.setStyle("-fx-text-fill: black;");
        labelInfo.setText("Wait fer th' game t' start...");
        textFieldNickname.setVisible(false);
        labelNickname.setText(nickname);
        labelNickname.setVisible(true);
        imageViewWaiting.setVisible(true);
    }

    public ImageView getImageViewSplash() {
        return imageViewSplash;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
