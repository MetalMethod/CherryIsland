package org.academiadecodigo.bootcamp8.cherryisland.server;

/**
 * Created by codecadet on 04/07/17.
 */

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
 * Multi-threaded tcp chat server that responds to client commands
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

            // Bind to local port
            System.out.println("Binding to port " + port + ", please wait  ...");
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started: " + serverSocket);

            ArrayList<PrintWriter> printWriters = new ArrayList<>();
            ArrayList<BufferedReader> bufferedReaders=new ArrayList<>();

            while (connectionCount < 2) {

                // Block waiting for client connections
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client accepted: " + clientSocket);


                try {
                    printWriters.add(new PrintWriter(clientSocket.getOutputStream(), true));
                    bufferedReaders.add(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));


                    // Create a new Server Worker
                    connectionCount++;
                    String name = "Client-" + connectionCount;
                    ServerWorker worker = new ServerWorker(name, clientSocket);
                    workers.add(worker);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }


            //initialize global game variables
            String[] positionContents = new String[Utils.GRID_COLS * Utils.GRID_COLS];
            for(int i = 0; i < positionContents.length;i ++){
                positionContents[i] = "empty";
            }
            //int numberOfLakes=3;
            ArrayList<String> gameObjectInit = new ArrayList<>();


            //set lake location
            int targetRow=(int)((Math.random())*(Utils.GREEN_COLS -1- Utils.LAKE_ROW_SPAN))+ Utils.BEACH_WIDTH+ Utils.P1_STARTING_ROW;
            int targetCol=(int)((Math.random())*(Utils.GREEN_COLS -1- Utils.LAKE_COL_SPAN))+ Utils.BEACH_WIDTH+ Utils.P1_STARTING_COL;
            gameObjectInit.add("lake " + "add " + targetCol + " " + targetRow + " " + (int) Math.floor(Math.random() * 3));
            for(int i = 0; i< Utils.LAKE_COL_SPAN; i++){
                for(int j=-1;j<2;j++){
                    positionContents[(j* Utils.GRID_COLS)+ Utils.GRID_COLS*targetRow+targetCol+i]="lake";
                }
            }

            //set boat location
            targetRow=(int)((Math.random())*(Utils.GREEN_COLS+2*Utils.BEACH_WIDTH-1-Utils.BOAT_ROWSPAN)) +Utils.P1_STARTING_ROW;
            targetCol=(int)((Math.random())*(Utils.GREEN_COLS+2*Utils.BEACH_WIDTH-1-Utils.BOAT_COLSPAN)) +Utils.P1_STARTING_COL;
            int maxRow=Utils.P1_STARTING_ROW + Utils.GREEN_COLS+2*Utils.BEACH_WIDTH-Utils.BOAT_ROWSPAN-2;
            int maxCol=Utils.P1_STARTING_COL + Utils.GREEN_COLS+2*Utils.BEACH_WIDTH-Utils.BOAT_COLSPAN-2;
            while(targetRow != Utils.P1_STARTING_ROW && targetRow !=maxRow && targetCol != Utils.P1_STARTING_COL && targetCol !=maxCol){
                targetRow=(int)((Math.random())*(Utils.GREEN_COLS+2*Utils.BEACH_WIDTH-1-Utils.BOAT_ROWSPAN)) +Utils.P1_STARTING_ROW;
                targetCol=(int)((Math.random())*(Utils.GREEN_COLS+2*Utils.BEACH_WIDTH-1-Utils.BOAT_COLSPAN)) +Utils.P1_STARTING_COL;
            }
            gameObjectInit.add("boat "+"add "+targetCol+" "+targetRow);
            for(int i = 0; i<Utils.BOAT_COLSPAN; i++){
                for(int j=-1;j<2;j++){
                    positionContents[(j*Utils.GRID_COLS)+Utils.GRID_COLS*targetRow+targetCol+i]="boat";
                }
            }
            System.out.println("boat col: "+targetCol+" boat row: "+targetRow);


            //set tree locations
            for(int i = 0; i< Utils.NUMBER_OF_TREES; i++){
                targetRow=(int)((Math.random())* Utils.GREEN_COLS)+ Utils.BEACH_WIDTH+ Utils.P1_STARTING_ROW;
                targetCol=(int)((Math.random())* Utils.GREEN_COLS)+ Utils.BEACH_WIDTH+ Utils.P1_STARTING_COL;
                while(!positionContents[Utils.GRID_COLS*targetRow+targetCol].equals("empty") ){
                    targetRow=(int)((Math.random())* Utils.GREEN_COLS)+ Utils.BEACH_WIDTH+ Utils.P1_STARTING_ROW;
                    targetCol=(int)((Math.random())* Utils.GREEN_COLS)+ Utils.BEACH_WIDTH+ Utils.P1_STARTING_COL;
                }
                gameObjectInit.add("tree "+"add "+targetCol+" "+targetRow + " " + (int) Math.floor(Math.random() * 3));
                positionContents[Utils.GRID_COLS*targetRow+targetCol]="tree";
            }


            //set cherry locations
            for(int i = 0; i < Utils.NUMBER_OF_CHERRIES; i++){
                targetRow=(int)((Math.random())* Utils.GREEN_COLS)+ Utils.BEACH_WIDTH+ Utils.P1_STARTING_ROW;
                targetCol=(int)((Math.random())* Utils.GREEN_COLS)+ Utils.BEACH_WIDTH+ Utils.P1_STARTING_COL;
                while(!positionContents[Utils.GRID_COLS*targetRow+targetCol].equals("empty") ){
                    targetRow=(int)((Math.random())* Utils.GREEN_COLS)+ Utils.BEACH_WIDTH+ Utils.P1_STARTING_ROW;
                    targetCol=(int)((Math.random())* Utils.GREEN_COLS)+ Utils.BEACH_WIDTH+ Utils.P1_STARTING_COL;
                }
                gameObjectInit.add("cherries "+"add "+targetCol+" "+targetRow  + " " + (int) Math.floor(Math.random() * 3));
                positionContents[Utils.GRID_COLS*targetRow+targetCol]="cherries";
            }

            //set rocks locations
            for(int i = 0; i < Utils.NUMBER_OF_ROCKS; i++){
                targetRow=(int)((Math.random())* Utils.GREEN_COLS)+ Utils.BEACH_WIDTH+ Utils.P1_STARTING_ROW;
                targetCol=(int)((Math.random())* Utils.GREEN_COLS)+ Utils.BEACH_WIDTH+ Utils.P1_STARTING_COL;
                while(!positionContents[Utils.GRID_COLS*targetRow+targetCol].equals("empty") ){
                    targetRow=(int)((Math.random())* Utils.GREEN_COLS)+ Utils.BEACH_WIDTH+ Utils.P1_STARTING_ROW;
                    targetCol=(int)((Math.random())* Utils.GREEN_COLS)+ Utils.BEACH_WIDTH+ Utils.P1_STARTING_COL;
                }
                gameObjectInit.add("rock "+"add "+targetCol+" "+targetRow);
                positionContents[Utils.GRID_COLS*targetRow+targetCol]="rock";
            }

            //set ropes location
            for(int i = 0; i < Utils.NUMBER_OF_ROPES; i++){
                targetRow=(int)((Math.random())* Utils.GREEN_COLS)+ Utils.BEACH_WIDTH+ Utils.P1_STARTING_ROW;
                targetCol=(int)((Math.random())* Utils.GREEN_COLS)+ Utils.BEACH_WIDTH+ Utils.P1_STARTING_COL;
                while(!positionContents[Utils.GRID_COLS*targetRow+targetCol].equals("empty") ){
                    targetRow=(int)((Math.random())* Utils.GREEN_COLS)+ Utils.BEACH_WIDTH+ Utils.P1_STARTING_ROW;
                    targetCol=(int)((Math.random())* Utils.GREEN_COLS)+ Utils.BEACH_WIDTH+ Utils.P1_STARTING_COL;
                }
                gameObjectInit.add("rope "+"add "+targetCol+" "+targetRow);
                positionContents[Utils.GRID_COLS*targetRow+targetCol]="rope";
            }

            //tell all players to start game and send them information about global game variables (tree positions, etc)
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

            //launch threads to handle communication with each client
            for (ServerWorker serverWorker : workers) {
                Thread thread = new Thread(serverWorker);
                thread.setName(serverWorker.getName());
                thread.start();
            }

            // Souts for testing
            for(String s:gameObjectInit){
                System.out.println(s);
            }

        } catch (IOException e) {
            System.out.println("Unable to start server on port " + port);
        }
    }



    /**
     * Broadcasts the grid to all server connected clients
     */
    private void sendAll(String s, ServerWorker serverWorker) {

        // Acquire lock for safe iteration
        synchronized (workers) {

            Iterator<ServerWorker> it = workers.iterator();
            while (it.hasNext()) {
                /*
                ServerWorker serverWorker1=it.next();
                if(!serverWorker1.equals(serverWorker)){
                    serverWorker1.send(s);
                }
                */
                it.next().send(s);
            }
        }
    }


    /**
     * Handles client connections
     */
    private class ServerWorker implements Runnable {

        // Immutable state, no need to lock
        final private String name;
        final private Socket clientSocket;
        final private BufferedReader in;
        final private PrintWriter out;

        /**
         * @param name         the name of the thread handling this client connection
         * @param clientSocket the client socket connection
         * @throws IOException upon failure to open socket input and output streams
         */
        private ServerWorker(String name, Socket clientSocket) throws IOException {

            this.name = name;
            this.clientSocket = clientSocket;

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(),true);

        }

        public String getName() {
            return name;
        }

        /**
         * @see Thread#run()
         */
        @Override
        public void run() {

            System.out.println("Thread " + name + " started");

            try {

                String message;
                while (!clientSocket.isClosed()) {

                    // Blocks waiting for client messages
                    message = in.readLine();

                    if (message == null) {

                        System.out.println("Client " + name + " closed, exiting...");

                        in.close();
                        clientSocket.close();
                        continue;

                    } else {
                        // Broadcast message to all other clients
                        sendAll(message, this);
                    }
                }
                workers.remove(this);
            } catch (Exception ex) {
                System.out.println("Receiving error on " + name + " : " + ex.getMessage());
            }
        }

        /**
         * Send the updated grid to the client served by this thread
         */
        private void send(String s) {

            out.println(s);

        }
    }

}