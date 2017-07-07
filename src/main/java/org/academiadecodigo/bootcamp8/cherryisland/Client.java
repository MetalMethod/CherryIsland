package org.academiadecodigo.bootcamp8.cherryisland;

/**
 * Created by codecadet on 05/07/17.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.academiadecodigo.bootcamp8.cherryisland.controller.PlayerController;
import org.academiadecodigo.bootcamp8.cherryisland.model.Player;
import org.academiadecodigo.bootcamp8.cherryisland.service.Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

import java.io.*;
import java.net.UnknownHostException;

/**
 * Multi-threaded tcp client
 */
public class Client extends Application {

    public final static String DEFAULT_NAME = "CLIENT";

    // The client socket


    /**
     * Bootstraps the client
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }


    // Starts handling messages
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Creates a new thread to handle incoming server grid
        Thread thread = new Thread(new MapRunnable(primaryStage));
        thread.start();
    }

    // Runnable to handle incoming messages from the server
    private class MapRunnable implements Runnable {

        private Socket socket;
        private Stage stage;
        private final int STARTING_COL = 0;
        private final int STARTING_ROW = 0;
        private final String INITIAL_VIEW = "/view/grid";

        private MapRunnable(Stage stage) {
            try {
                socket = new Socket("0.0.0.0", 6666);

                Navigation navigation = Navigation.getInstance();
                navigation.setStage(stage);
                stage.setTitle("Cherry Island");

                Game game = new Game();
                Player player = new Player(STARTING_COL, STARTING_ROW);

                navigation.loadScreen(INITIAL_VIEW);
                ((PlayerController) navigation.getController(INITIAL_VIEW)).setGame(game);
                ((PlayerController) navigation.getController(INITIAL_VIEW)).scrollPaneRequest();
                ((PlayerController) navigation.getController(INITIAL_VIEW)).setPlayer1(player);
                //((PlayerController) navigation.getController(INITIAL_VIEW)).setSocket(socket);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        /**
         * @see Thread#run()
         */
        @Override
        public void run() {

            try {

                int[] enemypos = {0,0};
                String input;
                String[] splitinput;
                // Block waiting for incoming messages from server
                BufferedReader sockIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (!socket.isClosed()) {
                    try {
                        //receive updated data from server and update game
                        input=sockIn.readLine();
                                if(input != null) {
                            splitinput=input.split(" ");
                                    if (splitinput.length == 2 ) {
                                        enemypos[0] = Integer.parseInt(splitinput[0]);
                                        enemypos[1] = Integer.parseInt(splitinput[1]);
                                        //((PlayerController) Navigation.getInstance().getController(INITIAL_VIEW)).setEnemyPos(enemypos[0], enemypos[1]);
                                    }
                                }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    if (enemypos == null) {

                        try {

                            System.out.println("Connection closed, exiting...");
                            sockIn.close();
                            socket.close();

                        } catch (IOException ex) {
                            System.out.println("Error closing connection: " + ex.getMessage());
                        }

                    }

                }
            } catch (SocketException ex) {
                // Socket closed by other thread, no need for special handling
            } catch (IOException ex) {
                System.out.println("Error reading from server: " + ex.getMessage());
            }

            // Server closed, but main thread blocked in console readline
            System.exit(0);

        }
    }
}