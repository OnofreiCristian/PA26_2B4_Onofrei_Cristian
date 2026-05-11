package server;

import org.example.model.Player;
import org.example.repository.PlayerRepository;

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
                    PlayerRepository playerRepo = new PlayerRepository();
                    Player dbPlayer = playerRepo.findByName(playerName);

                    if (dbPlayer == null) {
                        dbPlayer = new Player(playerName);
                        playerRepo.save(dbPlayer);
                        sendMessage("Welcome, new player " + playerName + "!");
                    } else {
                        dbPlayer.setCurrentScore(0); // Reset live score for the new game
                        sendMessage("Welcome back, " + playerName + "!");
                    }

                    this.playerProfile = dbPlayer;
                    server.getActiveSession().addPlayer(this.playerProfile, this);

                } else if ("start".equalsIgnoreCase(trimmedRequest)) {
                    if (!server.getActiveSession().isRunning()) {
                        new Thread(server.getActiveSession()).start();
                    } else {
                        sendMessage("The game is already running!");
                    }

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