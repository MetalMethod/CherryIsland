package org.academiadecodigo.bootcamp8.cherryisland.service;

import javafx.application.Platform;
import org.academiadecodigo.bootcamp8.cherryisland.gameObjects.ObjectType;
import org.academiadecodigo.bootcamp8.cherryisland.util.U;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by joelalmeida on 06/07/17.
 */
public class GameReceive implements Runnable {

    private Socket socket;
    private Game game;

    public GameReceive(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
    }

    @Override
    public void run() {
        try {

            while (!socket.isClosed()) {
                BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String msgReceived;

                while ((msgReceived = buffer.readLine()) != null) {
                    System.out.println(msgReceived);
                    final String msg2 = msgReceived;
                    if (msg2.split(" ")[1].equals("add")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (msg2.split(" ")[0].equals("tree")) {
                                    int col = Integer.parseInt(msg2.split(" ")[2]);
                                    int row = Integer.parseInt(msg2.split(" ")[3]);

                                    game.addGameObject(ObjectType.TREE, col, row);
                                }

                                if (msg2.split(" ")[0].equals("cherries")) {
                                    int col = Integer.parseInt(msg2.split(" ")[2]);
                                    int row = Integer.parseInt(msg2.split(" ")[3]);

                                    game.addGameObject(ObjectType.CHERRIES, col, row);
                                }

                                if (msg2.split(" ")[0].equals("lake")) {
                                    int col = Integer.parseInt(msg2.split(" ")[2]);
                                    int row = Integer.parseInt(msg2.split(" ")[3]);

                                    game.addGameObject(ObjectType.LAKE, col, row);
                                }
                            }
                        });
                    }

                    if (msg2.split(" ")[1].equals("remove")){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                int col = Integer.parseInt(msg2.split(" ")[2]);
                                int row = Integer.parseInt(msg2.split(" ")[3]);

                                game.removeGameObject(col, row);

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
