package org.example.logic;

import jakarta.persistence.EntityManager;
import org.example.model.Game;
import org.example.model.Player;
import org.example.model.Question;
import org.example.model.Result;
import org.example.util.PersistenceManager;
import server.ClientThread;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameSession implements Runnable {
    private final Map<Player, ClientThread> activePlayers = new HashMap<>();
    private final Map<Player, Long> responseTimes = new HashMap<>();

    private final List<Question> questions;
    private final long TIME_LIMIT_MS = 10000;
    private boolean running = false;
    private long currentQuestionStartTime;
    private Question currentQuestion;

    public GameSession(List<Question> questions) {
        this.questions = questions;
    }

    public void addPlayer(Player player, ClientThread connection) {
        activePlayers.put(player, connection);
        responseTimes.put(player, 0L);
        player.setCurrentScore(0);
    }

    @Override
    public void run() {
        this.running = true;
        broadcast("--- THE GAME IS STARTING ---");

        for (int i = 0; i < questions.size(); i++) {
            currentQuestion = questions.get(i);
            broadcast("\nQuestion " + (i + 1) + ": " + currentQuestion.getText());
            currentQuestionStartTime = System.currentTimeMillis();

            try {
                Thread.sleep(TIME_LIMIT_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            broadcast("Time is up!");
            currentQuestion = null;
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        this.running = false;
        calculateAndBroadcastWinner();
    }

    private void broadcast(String message) {
        for (ClientThread client : activePlayers.values()) {
            client.sendMessage(message);
        }
    }

    private void calculateAndBroadcastWinner() {
        if (activePlayers.isEmpty()) return;

        Player winner = null;

        for (Player p : activePlayers.keySet()) {
            if (winner == null) {
                winner = p;
                continue;
            }

            if (p.getCurrentScore() > winner.getCurrentScore()) {
                winner = p;
            } else if (p.getCurrentScore() == winner.getCurrentScore()) {
                if (responseTimes.get(p) < responseTimes.get(winner)) {
                    winner = p;
                }
            }
        }

        broadcast("The game is over! The winner is " + winner.getName() + " with " + winner.getCurrentScore() + " points!");

        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            Game newGame = new Game();
            em.persist(newGame);

            for (Player p : activePlayers.keySet()) {
                Result playerResult = new Result(
                        p,
                        newGame,
                        p.getCurrentScore(),
                        responseTimes.get(p)
                );
                em.persist(playerResult);
            }

            em.getTransaction().commit();
            System.out.println("Match results successfully saved to the database.");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Failed to save match results: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public boolean isRunning() {
        return running;
    }

    public synchronized void submitAnswer(Player player, String answer) {
        if (currentQuestion == null) return;

        long timeTaken = System.currentTimeMillis() - currentQuestionStartTime;
        ClientThread client = activePlayers.get(player);

        if (currentQuestion.isCorrect(answer)) {
            player.setCurrentScore(player.getCurrentScore() + 1);
            responseTimes.put(player, responseTimes.get(player) + timeTaken);
            client.sendMessage("Correct! Time: " + timeTaken + "ms");
        } else {
            responseTimes.put(player, responseTimes.get(player) + TIME_LIMIT_MS);
            client.sendMessage("Incorrect!");
        }
    }
}