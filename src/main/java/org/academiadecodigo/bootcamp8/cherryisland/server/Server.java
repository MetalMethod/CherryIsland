package org.academiadecodigo.bootcamp8.cherryisland.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.academiadecodigo.bootcamp8.cherryisland.util.Utils;

import java.io.*;

/**
 * Created by codecadet on 04/07/17.
 */

public class Server {

    public final static int DEFAULT_PORT = 6666;
    private List<ServerWorker> workers;

    public static void main(String[] args) {

        int port = DEFAULT_PORT;

        try {
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            }
            Server server = new Server();
            server.start(port);

        } catch (NumberFormatException ex) {
            System.out.println("Usage: java Server [port_number]");
            System.exit(1);
        }

    }

    public void start(int port) {

        workers = Collections.synchronizedList(new ArrayList<ServerWorker>());
        System.out.println("DEBUG: Server instance is : " + this);

        int connectionCount = 0;

        try {
            System.out.println("Binding to port " + port + ", please wait  ...");
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started: " + serverSocket);

            ArrayList<PrintWriter> printWriters = new ArrayList<>();
            ArrayList<BufferedReader> bufferedReaders=new ArrayList<>();

            while (connectionCount < 2) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client accepted: " + clientSocket);
                try {
                    printWriters.add(new PrintWriter(clientSocket.getOutputStream(), true));
                    bufferedReaders.add(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));

                    connectionCount++;
                    String name = "Client-" + connectionCount;
                    ServerWorker worker = new ServerWorker(name, clientSocket);
                    workers.add(worker);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            String[] positionContents = new String[Utils.GRID_COLS * Utils.GRID_COLS];
            for(int i = 0; i < positionContents.length; i ++){
                positionContents[i] = "empty";
            }

            ArrayList<String> gameObjectInit = new ArrayList<>();

            int targetRow = (int)((Math.random()) * (Utils.GREEN_COLS - Utils.LAKE_ROW_SPAN - 1)) + Utils.BEACH_WIDTH + Utils.P1_STARTING_ROW;
            int targetCol = (int)((Math.random()) * (Utils.GREEN_COLS - Utils.LAKE_COL_SPAN - 1))+ Utils.BEACH_WIDTH + Utils.P1_STARTING_COL;
            gameObjectInit.add("lake " + "add " + targetCol + " " + targetRow + " " + (int) Math.floor(Math.random() * 3));
            for(int i = 0; i < Utils.LAKE_COL_SPAN; i++){
                for(int j = -1; j < 2; j++){
                    positionContents[(j * Utils.GRID_COLS)+ Utils.GRID_COLS * targetRow+targetCol + i] = "lake";
                }
            }

            //TODO perceber isto
            targetRow=(int)((Math.random())*(Utils.GREEN_COLS+2*Utils.BEACH_WIDTH-1-Utils.BOAT_ROW_SPAN)) +Utils.P1_STARTING_ROW;
            targetCol=(int)((Math.random())*(Utils.GREEN_COLS+2*Utils.BEACH_WIDTH-1-Utils.BOAT_COL_SPAN)) +Utils.P1_STARTING_COL;
            int maxRow=Utils.P1_STARTING_ROW + Utils.GREEN_COLS+2*Utils.BEACH_WIDTH-Utils.BOAT_ROW_SPAN -2;
            int maxCol=Utils.P1_STARTING_COL + Utils.GREEN_COLS+2*Utils.BEACH_WIDTH-Utils.BOAT_COL_SPAN -2;
            while(targetRow != Utils.P1_STARTING_ROW && targetRow !=maxRow && targetCol != Utils.P1_STARTING_COL && targetCol !=maxCol){
                targetRow=(int)((Math.random())*(Utils.GREEN_COLS+2*Utils.BEACH_WIDTH-1-Utils.BOAT_ROW_SPAN)) +Utils.P1_STARTING_ROW;
                targetCol=(int)((Math.random())*(Utils.GREEN_COLS+2*Utils.BEACH_WIDTH-1-Utils.BOAT_COL_SPAN)) +Utils.P1_STARTING_COL;
            }
            gameObjectInit.add("boat " + "add " + targetCol + " " + targetRow);
            for(int i = 0; i < Utils.BOAT_COL_SPAN; i++){
                for(int j = -1; j < 2; j++){
                    positionContents[(j * Utils.GRID_COLS) + Utils.GRID_COLS * targetRow + targetCol + i] = "boat";
                }
            }

            for(int i = 0; i < Utils.NUMBER_OF_TREES; i++){
                targetRow = getRandomRow();
                targetCol = getRandomCol();
                while(!positionContents[Utils.GRID_COLS * targetRow + targetCol].equals("empty") ){
                    targetRow = getRandomRow();
                    targetCol = getRandomCol();
                }
                gameObjectInit.add("tree " + "add " + targetCol + " " + targetRow + " " + (int) Math.floor(Math.random() * 3));
                positionContents[Utils.GRID_COLS * targetRow + targetCol] = "tree";
            }

            for(int i = 0; i < Utils.NUMBER_OF_CHERRIES; i++){
                targetRow = getRandomRow();
                targetCol = getRandomCol();
                while(!positionContents[Utils.GRID_COLS * targetRow + targetCol].equals("empty") ){
                    targetRow = getRandomRow();
                    targetCol = getRandomCol();
                }
                gameObjectInit.add("cherries " + "add " + targetCol + " " + targetRow + " " + (int) Math.floor(Math.random() * 3));
                positionContents[Utils.GRID_COLS * targetRow + targetCol] = "cherries";
            }

            for(int i = 0; i < Utils.NUMBER_OF_ROCKS; i++){
                targetRow = getRandomRow();
                targetCol = getRandomCol();
                while(!positionContents[Utils.GRID_COLS*targetRow+targetCol].equals("empty") ){
                    targetRow = getRandomRow();
                    targetCol = getRandomCol();
                }
                gameObjectInit.add("rock " + "add " + targetCol + " " + targetRow);
                positionContents[Utils.GRID_COLS * targetRow + targetCol] = "rock";
            }

            for(int i = 0; i < Utils.NUMBER_OF_ROPES; i++){
                targetRow = getRandomRow();
                targetCol = getRandomCol();
                while(!positionContents[Utils.GRID_COLS * targetRow + targetCol].equals("empty") ){
                    targetRow = getRandomRow();
                    targetCol = getRandomCol();
                }
                gameObjectInit.add("rope " + "add " + targetCol + " " + targetRow);
                positionContents[Utils.GRID_COLS * targetRow + targetCol] = "rope";
            }

            for (int i = 1; i < printWriters.size() + 1; i++) {
                printWriters.get(i - 1).println(i);
                printWriters.get(i - 1).println("start");
                String str = bufferedReaders.get(i - 1).readLine();
                if(str.equals("start")) {
                    for (String s : gameObjectInit) {
                        printWriters.get(i - 1).println(s);
                    }
                }
            }

            for (ServerWorker serverWorker : workers) {
                Thread thread = new Thread(serverWorker);
                thread.setName(serverWorker.getName());
                thread.start();
            }

        } catch (IOException e) {
            System.out.println("Unable to start server on port " + port);
        }
    }

    private void sendAll(String s, ServerWorker serverWorker) {

        synchronized (workers) {
            Iterator<ServerWorker> it = workers.iterator();
            while (it.hasNext()) {
                it.next().send(s);
            }
        }
    }

    private int getRandomCol() {
        return (int)((Math.random()) * Utils.GREEN_COLS) + Utils.BEACH_WIDTH + Utils.P1_STARTING_COL;
    }

    private int getRandomRow() {
        return (int)((Math.random()) * Utils.GREEN_COLS) + Utils.BEACH_WIDTH + Utils.P1_STARTING_ROW;
    }

    private class ServerWorker implements Runnable {

        final private String name;
        final private Socket clientSocket;
        final private BufferedReader in;
        final private PrintWriter out;

        private ServerWorker(String name, Socket clientSocket) throws IOException {
            this.name = name;
            this.clientSocket = clientSocket;
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(),true);
        }

        public String getName() {
            return name;
        }

        @Override
        public void run() {
            System.out.println("Thread " + name + " started");
            try {
                String message;

                while (!clientSocket.isClosed()) {
                    message = in.readLine();

                    if (message == null) {
                        in.close();
                        clientSocket.close();
                        continue;
                    } else {
                        sendAll(message, this);
                    }
                }
                workers.remove(this);
            } catch (Exception ex) {
                System.out.println("Receiving error on " + name + " : " + ex.getMessage());
            }
        }

        private void send(String s) {
            out.println(s);
        }
    }

}