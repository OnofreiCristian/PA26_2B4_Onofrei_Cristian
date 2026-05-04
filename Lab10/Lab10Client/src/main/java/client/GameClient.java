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
            System.out.println("Type your commands. Type 'exit' to quit the client.");
            System.out.println("Type 'stop' to shut down the server remotely.");
            System.out.println("---------------------------------------------------------");

            while (true) {
                System.out.print("Enter command: ");
                String command = scanner.nextLine().trim();

                if ("exit".equalsIgnoreCase(command)) {
                    System.out.println("Exiting the client application...");
                    break;
                }

                out.println(command);

                String response = in.readLine();
                if (response == null) {
                    System.out.println("Connection to server was lost.");
                    break;
                }

                System.out.println("Server says: " + response);
                if ("stop".equalsIgnoreCase(command)) {
                    System.out.println("Server has been stopped. Exiting client.");
                    break;
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Server not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage() + ". Is the server running?");
        }
    }
}