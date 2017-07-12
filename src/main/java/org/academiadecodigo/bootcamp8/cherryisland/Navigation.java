package org.academiadecodigo.bootcamp8.cherryisland;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by dgcst on 01/07/2017.
 */

public final class Navigation {

    private final int MIN_WIDTH = 725;
    private final int MIN_HEIGHT = 725;
    private final String ICON = "/interface/cherry.png";
    private final String VIEW_PATH = "/view/";
    private static Navigation instance = null;
    private Stage stage;
    private LinkedList<Scene> scenes = new LinkedList<Scene>();
    private Map<String, Initializable> controllers = new HashMap<>();

    private Navigation() {
    }

    public static synchronized Navigation getInstance() {
        if (instance == null) {
            instance = new Navigation();
        }
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        setIcons();
    }

    private void setIcons() {

        try {
            com.apple.eawt.Application.getApplication().setDockIconImage(new ImageIcon(getClass().getResource(ICON)).getImage());
        } catch (Exception e) {
        }

        stage.getIcons().add(new Image(ICON));
    }

    public void loadScreen(String view) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(VIEW_PATH + view + ".fxml"));
            Parent root = fxmlLoader.load();

            controllers.put(view, fxmlLoader.<Initializable>getController());

            Scene scene = new Scene(root, MIN_WIDTH, MIN_HEIGHT);
            scenes.push(scene);

            setScene(scene);

        } catch (IOException e) {
            System.out.println("Failure to load view " + view + " : " + e.getMessage());
        }
    }

    public void back() {
        if (scenes.size() < 2) {
            return;
        }
        scenes.pop();
        setScene(scenes.peek());
    }

    private void setScene(Scene scene) {
        stage.setScene(scene);
        stage.show();
    }

    public Initializable getController(String view) {
        return controllers.get(view);
    }

    public Scene getScene() {
        return stage.getScene();
    }
}
