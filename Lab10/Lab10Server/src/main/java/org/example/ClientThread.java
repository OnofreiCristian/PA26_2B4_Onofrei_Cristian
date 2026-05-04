package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;
    private GameServer server;

    public ClientThread(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try (

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String request;

            while ((request = in.readLine()) != null) {
                String trimmedRequest = request.trim();
                System.out.println("Received command: " + trimmedRequest);

                if ("stop".equalsIgnoreCase(trimmedRequest)) {
                    out.println("Server stopped");
                    System.out.println("Stop command received. Initiating server shutdown...");
                    server.stopServer();
                    break;
                } else {
                    out.println("Server received the request " + trimmedRequest);
                }
            }
        } catch (IOException e) {
            System.err.println("Communication error with client: " + e.getMessage());
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}