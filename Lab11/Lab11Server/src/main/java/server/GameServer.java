package server;

import org.example.logic.GameSession;
import org.example.model.Question;
import org.example.repository.QuestionRepository;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameServer {
    public static final int PORT = 8100;
    private boolean running = true;
    private ServerSocket serverSocket;

    private final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    private GameSession activeSession;

    public GameServer() {
        QuestionRepository questionRepo = new QuestionRepository();

        List<Question> dbQuestions = questionRepo.findAll();
        if (dbQuestions.isEmpty()) {
            System.out.println("Database is empty. Inserting default questions...");
            questionRepo.save(new Question("What is the capital of Romania?", "Bucharest"));
            questionRepo.save(new Question("What does HTML stand for?", "HyperText Markup Language"));
            questionRepo.save(new Question("What is 5 + 7?", "12"));
            dbQuestions = questionRepo.findAll();
        }
        this.activeSession = new GameSession(dbQuestions);

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Game Server is running and waiting for players on port " + PORT + "...");

            while (running) {
                Socket socket = serverSocket.accept();
                System.out.println("A new client connected from " + socket.getInetAddress());
                threadPool.execute(new ClientThread(socket, this));
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

        shutdownAndAwaitTermination(threadPool);
    }

    private void shutdownAndAwaitTermination(ExecutorService pool) {
        System.out.println("Initiating graceful shutdown of thread pool...");
        pool.shutdown();
        try {
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                System.out.println("Forcing shutdown after timeout...");
                pool.shutdownNow();

                if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Thread pool did not terminate properly.");
                }
            }
        } catch (InterruptedException ie) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("Server fully stopped.");
    }

    public GameSession getActiveSession() {
        return activeSession;
    }

    public static void main(String[] args) {
        new GameServer();
    }
}