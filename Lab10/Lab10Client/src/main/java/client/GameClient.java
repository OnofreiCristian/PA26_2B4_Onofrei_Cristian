package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GameClient {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";
        int PORT = 8100;

        try (
                Socket socket = new Socket(serverAddress, PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner scanner = new Scanner(System.in)
        ) {

            System.out.println("Connected to the Game Server at " + serverAddress + ":" + PORT);

            Thread listenerThread = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println("\n[Server]: " + serverMessage);
                        System.out.print("Enter command/answer: ");
                    }
                } catch (IOException e) {
                    System.out.println("\nDisconnected from the server.");
                }
            });
            listenerThread.start();
            while (true) {
                String command = scanner.nextLine().trim();

                if ("exit".equalsIgnoreCase(command)) {
                    System.out.println("Exiting the client application...");
                    out.println("exit"); // Let the server know we are leaving
                    break;
                }

                out.println(command);

                if ("stop".equalsIgnoreCase(command)) {
                    System.out.println("Server stop command sent. Exiting client.");
                    break;
                }
            }
            System.exit(0);

        } catch (UnknownHostException e) {
            System.err.println("Server not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage() + ". Is the server running?");
        }
    }
}