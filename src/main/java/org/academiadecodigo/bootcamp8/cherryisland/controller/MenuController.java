package org.academiadecodigo.bootcamp8.cherryisland.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    @FXML
    TextField textFieldNickname;

    @FXML
    Label labelNickname;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerService = (PlayerService) ServiceRegistry.getInstance().getService(PlayerService.class.getSimpleName());
    }

    public void onRegisterButtonClick() {
        String nickname = textFieldNickname.getText();

        if(playerService.playerExists(nickname)) {
            labelNickname.setStyle("-fx-text-fill: red;");
            labelNickname.setText("Nickname already in use, please choose a different one.");
            return;
        }

        playerService.addPlayer(nickname);
        Navigation.getInstance().loadScreen("grid");
    }

    public void onCloseButtonClick() {
        Stage stage = (Stage) Navigation.getInstance().getScene().getWindow();
        stage.close();
    }
}
