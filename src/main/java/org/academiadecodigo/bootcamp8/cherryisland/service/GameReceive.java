package org.academiadecodigo.bootcamp8.cherryisland.service;

import org.academiadecodigo.bootcamp8.cherryisland.gameObjects.ObjectType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by joelalmeida on 06/07/17.
 */
public class GameReceive extends Game implements Runnable {

    Socket socket;
    BufferedReader buffer;

    public GameReceive(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {

            while (true) {

                buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String msgRecieved;

                while ((msgRecieved = buffer.readLine()) != null) {

                    if (msgRecieved.split(" ")[1].equals("add")) {


                        if (msgRecieved.split(" ")[0].equals("tree")) {
                            int col = Integer.parseInt(msgRecieved.split(" ")[2]);
                            int row = Integer.parseInt(msgRecieved.split(" ")[3]);

                            addGameObject(ObjectType.TREE, col, row);
                        }

                        if (msgRecieved.split(" ")[0].equals("cherries")) {
                            int col = Integer.parseInt(msgRecieved.split(" ")[2]);
                            int row = Integer.parseInt(msgRecieved.split(" ")[3]);

                            addGameObject(ObjectType.CHERRIES, col, row);
                        }

                        if (msgRecieved.split(" ")[0].equals("lake")) {
                            int col = Integer.parseInt(msgRecieved.split(" ")[2]);
                            int row = Integer.parseInt(msgRecieved.split(" ")[3]);

                            addGameObject(ObjectType.LAKE, col, row);
                        }

                    }


                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
