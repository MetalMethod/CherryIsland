package org.academiadecodigo.bootcamp8.cherryisland.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.academiadecodigo.bootcamp8.cherryisland.Navigation;
import org.academiadecodigo.bootcamp8.cherryisland.service.PlayerService;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by dgcst on 01/07/2017.
 */

public class MenuController implements Initializable {

    private PlayerService playerService;
    private String nickname;

    @FXML
    TextField textFieldNickname;

    @FXML
    Label labelInfo;

    @FXML
    Label labelNickname;

    @FXML
    Button readyButton;

    @FXML
    ImageView imageWaiting;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerService = PlayerService.getInstance();
    }

    public void onRegisterButtonClick() {
        if (labelNickname.isVisible()) {
            return;
        }
        nickname = textFieldNickname.getText();
        if (nickname.equals("") || playerService.playerExists(nickname)) {
            labelInfo.setStyle("-fx-text-fill: red;");
            labelInfo.setText("Nickname invalid or already in use, please choose a different one.");
            return;
        }
        waitForGame();

        playerService.addPlayer(nickname);
    }

    public void onCloseButtonClick() {
        Stage stage = (Stage) Navigation.getInstance().getScene().getWindow();
        stage.close();
    }

    private void waitForGame() {
        readyButton.setStyle("-fx-background-color: green;");
        labelInfo.setStyle("-fx-text-fill: black;");
        labelInfo.setText("Please wait for the game to start...");
        textFieldNickname.setVisible(false);
        labelNickname.setText(nickname);
        labelNickname.setVisible(true);
        imageWaiting.setVisible(true);
    }
}
