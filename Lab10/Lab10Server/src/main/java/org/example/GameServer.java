package org.example;

import org.example.ClientThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class GameServer {
    public static final int PORT = 8100;
    private boolean running = true;
    private ServerSocket serverSocket;

    public GameServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Game Server is running and waiting for players on port " + PORT + "...");


            while (running) {

                Socket socket = serverSocket.accept();
                System.out.println("A new client connected!");

                new ClientThread(socket, this).start();
            }
        } catch (SocketException e) {
            System.out.println("Server socket closed. Server is shutting down.");
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
        } finally {
            stopServer();
        }
    }

    public void stopServer() {
        this.running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error while closing the server socket: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new GameServer();
    }
}
