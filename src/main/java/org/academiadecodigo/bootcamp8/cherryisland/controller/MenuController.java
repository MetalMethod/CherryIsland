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
import org.academiadecodigo.bootcamp8.cherryisland.service.ServiceRegistry;

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
    Label labelNickname;

    @FXML
    Label labelNicknameExists;

    @FXML
    Label labelWaiting;

    @FXML
    Label labelNicknameChoice;

    @FXML
    Button readyButton;

    @FXML
    ImageView imageWaiting;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerService = (PlayerService) ServiceRegistry.getInstance().getService(PlayerService.class.getSimpleName());
    }

    public void onRegisterButtonClick() {
        labelNickname.setVisible(true);

        nickname = textFieldNickname.getText();

        if(nickname.equals("") || playerService.playerExists(nickname)) {
            labelNickname.setVisible(false);
            labelNicknameExists.setVisible(true);
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
        labelNickname.setVisible(false);
        labelNicknameExists.setVisible(false);
        textFieldNickname.setVisible(false);
        labelNicknameChoice.setText(nickname);
        labelNicknameChoice.setVisible(true);
        labelWaiting.setVisible(true);
        imageWaiting.setVisible(true);
    }
}
