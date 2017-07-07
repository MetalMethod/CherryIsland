package org.academiadecodigo.bootcamp8.cherryisland.service;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by joelalmeida on 06/07/17.
 */
public class GameSend extends Game implements Runnable {

    private Socket socket;
    private PrintWriter buffer;

    public GameSend(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {

        while (socket.isConnected()){


        }

    }
}
