package org.academiadecodigo.bootcamp8.cherryisland.service;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import org.academiadecodigo.bootcamp8.cherryisland.gameObjects.ObjectType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by joelalmeida on 06/07/17.
 */
public class GameReceive implements Runnable {

    Socket socket;
    BufferedReader buffer;
    Game game;

    public GameReceive(Socket socket, Game game) {
        this.socket = socket;
        this.game=game;
    }

    @Override
    public void run() {
        try {

            while (!socket.isClosed()) {

                buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String msgRecieved;

                while ((msgRecieved = buffer.readLine()) != null) {

                    System.out.println(msgRecieved);
                    final String msg2=msgRecieved;

                    if (msg2.split(" ")[1].equals("add")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (msg2.split(" ")[0].equals("tree")) {
                                    int col = Integer.parseInt(msg2.split(" ")[2]);
                                    int row = Integer.parseInt(msg2.split(" ")[3]);

                                    game.addGameObject(ObjectType.TREE, col+14, row+14);
                                }

                                if (msg2.split(" ")[0].equals("cherries")) {
                                    int col = Integer.parseInt(msg2.split(" ")[2]);
                                    int row = Integer.parseInt(msg2.split(" ")[3]);

                                    game.addGameObject(ObjectType.CHERRIES, col+14, row+14);
                                }

                                if (msg2.split(" ")[0].equals("lake")) {
                                    int col = Integer.parseInt(msg2.split(" ")[2]);
                                    int row = Integer.parseInt(msg2.split(" ")[3]);

                                    game.addGameObject(ObjectType.LAKE, col+14, row+14);
                                }
                            }
                        });
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
