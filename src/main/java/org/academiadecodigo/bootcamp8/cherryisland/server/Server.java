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

import org.academiadecodigo.bootcamp8.cherryisland.util.U;

import java.io.*;

/**
 * Multi-threaded tcp chat server that responds to client commands
 */
public class Server {

    /**
     * Default port to run the server on
     */
    public final static int DEFAULT_PORT = 6666;

    /**
     * Graphical field
     */


    /**
     * Synchronized List of worker threads, locked by itself
     */
    private List<ServerWorker> workers = Collections.synchronizedList(new ArrayList<ServerWorker>());


    /**
     * Bootstraps the chat server
     *
     * @param args Optional port number as command line argument
     */
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

    /**
     * Starts the server on a specified port
     *
     * @param port the tcp port to bind to
     */
    public void start(int port) {

        System.out.println("DEBUG: Server instance is : " + this);

        int connectionCount = 0;

        try {

            // Bind to local port
            System.out.println("Binding to port " + port + ", please wait  ...");
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started: " + serverSocket);

            ArrayList<PrintWriter> printWriters = new ArrayList<>();
            ArrayList<BufferedReader> bufferedReaders = new ArrayList<>();

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
            String[] positionContents = new String[U.GRID_COLS * U.GRID_COLS];
            for (int i = 0; i < positionContents.length; i++){
                positionContents[i] = "empty";
            }
            int numberOfTrees = 250;
            int numberOfCherries = 50;
            //int numberOfLakes=3;
            ArrayList<String> gameObjectInit=new ArrayList<>();


            //set lake location
            int targetRow = (int)((Math.random()) * (U.GREEN_COLS - U.LAKEROWSPAN - 1)) + U.BEACH_WIDTH + U.P1_STARTING_ROW;
            int targetCol = (int)((Math.random()) * (U.GREEN_COLS - U.LAKEROWSPAN - 1)) + U.BEACH_WIDTH + U.P1_STARTING_COL;
            gameObjectInit.add("lake " + "add " + targetCol + " " + targetRow);
            for (int i = 0; i < U.LAKECOLSPAN; i++){
                for(int j = -1; j < 2; j++){
                    positionContents[(j * U.GRID_COLS) + U.GRID_COLS * targetRow + targetCol + i] = "lake";
                }
            }


            //set tree locations
            for (int i = 0; i < numberOfTrees; i++){
                targetRow = (int)((Math.random()) * U.GREEN_COLS) + U.BEACH_WIDTH + U.P1_STARTING_ROW;
                targetCol = (int)((Math.random()) * U.GREEN_COLS) + U.BEACH_WIDTH + U.P1_STARTING_COL;
                while (!positionContents[U.GRID_COLS * targetRow+targetCol].equals("empty") ){
                    targetRow = (int)((Math.random()) * U.GREEN_COLS) + U.BEACH_WIDTH + U.P1_STARTING_ROW;
                    targetCol = (int)((Math.random()) * U.GREEN_COLS) + U.BEACH_WIDTH + U.P1_STARTING_COL;
                }
                gameObjectInit.add("tree " + "add " + targetCol + " " + targetRow);
                positionContents[U.GRID_COLS * targetRow + targetCol] = "tree";
            }

            //set cherry locations
            for (int i = 0; i < numberOfCherries; i++){
                targetRow = (int)((Math.random()) * U.GREEN_COLS) + U.BEACH_WIDTH + U.P1_STARTING_ROW;
                targetCol = (int)((Math.random()) * U.GREEN_COLS) + U.BEACH_WIDTH + U.P1_STARTING_COL;
                while (!positionContents[U.GRID_COLS * targetRow+targetCol].equals("empty") ){
                    targetRow = (int)((Math.random()) * U.GREEN_COLS) + U.BEACH_WIDTH + U.P1_STARTING_ROW;
                    targetCol = (int)((Math.random()) * U.GREEN_COLS) + U.BEACH_WIDTH + U.P1_STARTING_COL;
                }
                gameObjectInit.add("cherries " + "add " + targetCol + " " + targetRow);
                positionContents[U.GRID_COLS * targetRow + targetCol] = "cherries";
            }

            //tell all players to start game and send them information about global game variables (tree positions, etc)
            for (int i = 1; i < printWriters.size(); i++){
                printWriters.get(i - 1).println(i);
                printWriters.get(i - 1).println("start");
                //send strings with global game variables
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
            // Serve the client connection with a new Thread
            for(String s : gameObjectInit){
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