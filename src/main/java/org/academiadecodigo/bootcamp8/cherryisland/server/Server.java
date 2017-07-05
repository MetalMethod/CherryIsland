package org.academiadecodigo.bootcamp8.cherryisland.server;

/**
 * Created by codecadet on 04/07/17.
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javafx.scene.shape.Rectangle;

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

            while (connectionCount<2) {

                // Block waiting for client connections
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client accepted: " + clientSocket);



                try {

                    // Create a new Server Worker
                    connectionCount++;
                    String name = "Client-" + connectionCount;
                    ServerWorker worker = new ServerWorker(name, clientSocket);
                    workers.add(worker);

                    // Serve the client connection with a new Thread
                    Thread thread = new Thread(worker);
                    thread.setName(name);
                    thread.start();

                } catch (IOException ex) {
                    System.out.println("Error receiving client connection: " + ex.getMessage());
                }


            }

        } catch (IOException e) {
            System.out.println("Unable to start server on port " + port);
        }

    }

    /**
     * Broadcasts the grid to all server connected clients
     *
     *
     */
    private void sendAll(int[] rectCoordinates, ServerWorker player) {

        // Acquire lock for safe iteration
        synchronized (workers) {

            Iterator<ServerWorker> it = workers.iterator();
            while (it.hasNext()) {
                ServerWorker serverWorker= it.next();
                if(!serverWorker.equals(player)){
                    serverWorker.send(rectCoordinates);}
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
        final private ObjectInputStream in;
        final private ObjectOutputStream out;

        /**
         * @param name         the name of the thread handling this client connection
         * @param clientSocket the client socket connection
         * @throws IOException upon failure to open socket input and output streams
         */
        private ServerWorker(String name, Socket clientSocket) throws IOException {

            this.name = name;
            this.clientSocket = clientSocket;

            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());

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

                int[] rectCoordinates;
                while (!clientSocket.isClosed()) {

                    // Blocks waiting for client messages
                    rectCoordinates = (int[])in.readObject();

                    if (rectCoordinates == null) {

                        System.out.println("Client " + name + " closed, exiting...");

                        in.close();
                        clientSocket.close();
                        continue;

                    } else {
                        // Broadcast message to all other clients
                        sendAll(rectCoordinates, this);

                    }

                }

                workers.remove(this);

            } catch (Exception ex) {
                System.out.println("Receiving error on " + name + " : " + ex.getMessage());
            }

        }

        /**
         * Send the updated grid to the client served by this thread
         *
         *
         */
        private void send(int[] rectCoordinates) {

            try {

                out.writeObject(rectCoordinates);
                out.flush();

            } catch (IOException ex) {
                System.out.println("Error sending message to Client " + name + " : " + ex.getMessage());
            }
        }

    }

}