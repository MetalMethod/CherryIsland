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

    Socket socket;
    BufferedReader buffer;
    Game game;
    Sound sound;

    public GameReceive(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
    }

    @Override
    public void run() {
        try {

            while (!socket.isClosed()) {

                buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String msgRecieved;

                while ((msgRecieved = buffer.readLine()) != null) {

                    System.out.println(msgRecieved);
                    final String msg2 = msgRecieved;

                    if (msg2.split(" ")[1].equals("add")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (msg2.split(" ")[0].equals("tree")) {
                                    int col = Integer.parseInt(msg2.split(" ")[2]);
                                    int row = Integer.parseInt(msg2.split(" ")[3]);
                                    int type = Integer.parseInt(msg2.split(" ")[4]);

                                    game.addGameObject(ObjectType.TREE, col, row, type);
                                }

                                if (msg2.split(" ")[0].equals("cherries")) {
                                    int col = Integer.parseInt(msg2.split(" ")[2]);
                                    int row = Integer.parseInt(msg2.split(" ")[3]);
                                    int type = Integer.parseInt(msg2.split(" ")[4]);

                                    game.addGameObject(ObjectType.CHERRIES, col, row, type);
                                }

                                if (msg2.split(" ")[0].equals("lake")) {
                                    int col = Integer.parseInt(msg2.split(" ")[2]);
                                    int row = Integer.parseInt(msg2.split(" ")[3]);
                                    int type = Integer.parseInt(msg2.split(" ")[4]);

                                    game.addGameObject(ObjectType.LAKE, col, row, type);
                                }

                                if (msg2.split(" ")[0].equals("boat")) {
                                    int col = Integer.parseInt(msg2.split(" ")[2]);
                                    int row = Integer.parseInt(msg2.split(" ")[3]);

                                    game.addGameObject(ObjectType.BOAT, col, row);
                                }

                                if (msg2.split(" ")[0].equals("rock")){
                                    int col = Integer.parseInt(msg2.split(" ")[2]);
                                    int row = Integer.parseInt(msg2.split(" ")[3]);

                                    game.addGameObject(ObjectType.ROCK, col, row);
                                }

                                if (msg2.split(" ")[0].equals("rope")){
                                    int col = Integer.parseInt(msg2.split(" ")[2]);
                                    int row = Integer.parseInt(msg2.split(" ")[3]);

                                    game.addGameObject(ObjectType.ROPE, col, row);
                                }
                            }
                        });
                    }

                    if (msg2.split(" ")[1].equals("remove")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                int col = Integer.parseInt(msg2.split(" ")[2]);
                                int row = Integer.parseInt(msg2.split(" ")[3]);

                                game.removeGameObject(col, row);

                            }
                        });

                    }

                    if (msg2.split(" ")[1].equals("move")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (!msg2.split(" ")[0].equals(game.getPlayerNumber())) {
                                    int newcol = Integer.parseInt(msg2.split(" ")[2]);
                                    int newrow = Integer.parseInt(msg2.split(" ")[3]);

                                    game.moveGameObject(game.getEnemy(), newcol, newrow);
                                }
                            }
                        });
                    }

                    if (msg2.split(" ")[1].equals("wins")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                game.getGameSound().stop();

                                if (msg2.split(" ")[0].equals(game.getPlayerNumber())) {
                                    Navigation.getInstance().loadScreen("youwin");

                                    sound = new Sound(SoundEnum.WIN.getPath());
                                    sound.setLoop(2);
                                    sound.play(true);

                                } else {
                                    Navigation.getInstance().loadScreen("youlose");

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
            e.printStackTrace();
        }

    }
}
