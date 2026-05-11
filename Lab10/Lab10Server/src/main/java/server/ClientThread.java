package server;

import player.Player;
import server.GameServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread implements Runnable {
    private final Socket socket;
    private final GameServer server;
    private PrintWriter out;
    private Player playerProfile;

    public ClientThread(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)
        ) {
            this.out = writer;
            String request;

            while ((request = in.readLine()) != null) {
                String trimmedRequest = request.trim();
                System.out.println("Received command: " + trimmedRequest);

                if ("stop".equalsIgnoreCase(trimmedRequest)) {
                    sendMessage("Server stopped");
                    server.stopServer();
                    break;
                } else if (trimmedRequest.startsWith("join ")) {
                    String playerName = trimmedRequest.substring(5).trim();
                    this.playerProfile = new Player(playerName, this);

                    server.getActiveSession().addPlayer(this.playerProfile);
                    sendMessage("Welcome " + playerName + "! You have joined the game.");

                } else if ("start".equalsIgnoreCase(trimmedRequest)) {
                    new Thread(server.getActiveSession()).start();

                } else {
                    if (server.getActiveSession().isRunning()) {
                        server.getActiveSession().submitAnswer(this.playerProfile, trimmedRequest);
                    } else {
                        sendMessage("Server received: " + trimmedRequest + " (Game hasn't started yet)");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Communication error with client: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }


    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    private void closeConnection() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing client socket: " + e.getMessage());
        }
    }
}