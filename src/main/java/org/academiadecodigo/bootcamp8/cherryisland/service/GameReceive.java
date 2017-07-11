package org.academiadecodigo.bootcamp8.cherryisland.service;

import javafx.application.Platform;
import org.academiadecodigo.bootcamp8.cherryisland.Navigation;
import org.academiadecodigo.bootcamp8.cherryisland.gameObjects.ObjectType;
import org.academiadecodigo.bootcamp8.cherryisland.sound.Sound;
import org.academiadecodigo.bootcamp8.cherryisland.sound.SoundEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by joelalmeida on 06/07/17.
 */

public class GameReceive implements Runnable {

    private Socket socket;
    private BufferedReader buffer;
    private Game game;
    private Sound sound;

    public GameReceive(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String receivedMessage;

                while ((receivedMessage = buffer.readLine()) != null) {
                    final String message = receivedMessage;

                    if (message.split(" ")[1].equals("add")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (message.split(" ")[0].equals("tree")) {
                                    int col = Integer.parseInt(message.split(" ")[2]);
                                    int row = Integer.parseInt(message.split(" ")[3]);
                                    int type = Integer.parseInt(message.split(" ")[4]);
                                    game.addGameObject(ObjectType.TREE, col, row, type);
                                }

                                if (message.split(" ")[0].equals("cherries")) {
                                    int col = Integer.parseInt(message.split(" ")[2]);
                                    int row = Integer.parseInt(message.split(" ")[3]);
                                    int type = Integer.parseInt(message.split(" ")[4]);
                                    game.addGameObject(ObjectType.CHERRIES, col, row, type);
                                }

                                if (message.split(" ")[0].equals("lake")) {
                                    int col = Integer.parseInt(message.split(" ")[2]);
                                    int row = Integer.parseInt(message.split(" ")[3]);
                                    int type = Integer.parseInt(message.split(" ")[4]);
                                    game.addGameObject(ObjectType.LAKE, col, row, type);
                                }

                                if (message.split(" ")[0].equals("boat")) {
                                    int col = Integer.parseInt(message.split(" ")[2]);
                                    int row = Integer.parseInt(message.split(" ")[3]);
                                    game.addGameObject(ObjectType.BOAT, col, row);
                                }

                                if (message.split(" ")[0].equals("rock")){
                                    int col = Integer.parseInt(message.split(" ")[2]);
                                    int row = Integer.parseInt(message.split(" ")[3]);
                                    game.addGameObject(ObjectType.ROCK, col, row);
                                }

                                if (message.split(" ")[0].equals("rope")){
                                    int col = Integer.parseInt(message.split(" ")[2]);
                                    int row = Integer.parseInt(message.split(" ")[3]);
                                    game.addGameObject(ObjectType.ROPE, col, row);
                                }
                            }
                        });
                    }

                    if (message.split(" ")[1].equals("remove")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                int col = Integer.parseInt(message.split(" ")[2]);
                                int row = Integer.parseInt(message.split(" ")[3]);
                                game.removeGameObject(col, row);
                            }
                        });
                    }

                    if (message.split(" ")[1].equals("move")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (!message.split(" ")[0].equals(game.getPlayerNumber())) {
                                    int col = Integer.parseInt(message.split(" ")[2]);
                                    int row = Integer.parseInt(message.split(" ")[3]);
                                    game.moveGameObject(game.getEnemy(), col, row);
                                }
                            }
                        });
                    }

                    if (message.split(" ")[1].equals("wins")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                game.getGameSound().stop();

                                if (message.split(" ")[0].equals(game.getPlayerNumber())) {
                                    Navigation.getInstance().loadScreen("win");
                                    sound = new Sound(SoundEnum.WIN.getPath());
                                    sound.setLoop(2);
                                    sound.play(true);
                                } else {
                                    Navigation.getInstance().loadScreen("lose");
                                    sound = new Sound(SoundEnum.LOSE.getPath());
                                    sound.setLoop(2);
                                    sound.play(true);
                                }
                            }
                        });
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Socket connection is now closed.");
        }

    }
}
