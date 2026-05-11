package game;

import player.Player;
import questions.Question;

import java.util.List;
import java.util.ArrayList;

public class GameSession implements Runnable {
    private final List<Player> players = new ArrayList<>();
    private final List<Question> questions;
    private final long TIME_LIMIT_MS = 10000;
    private boolean running = false;
    private long currentQuestionStartTime;
    private Question currentQuestion;

    public GameSession(List<Question> questions) {
        this.questions = questions;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    @Override
    public void run() {
        this.running = true;
        broadcast("\n--- THE GAME IS STARTING ---");

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
            currentQuestion = null; // Stop accepting answers for this question
        }

        this.running = false;
        calculateAndBroadcastWinner(); // The method we built previously
    }

    private void broadcast(String message) {
        for (Player p : players) {
            p.getClientConnection().sendMessage(message);
        }
    }

    private void calculateAndBroadcastWinner() {
        Player winner = players.get(0);
        for (Player p : players) {
            if (p.getScore() > winner.getScore()) {
                winner = p;
            } else if (p.getScore() == winner.getScore()) {
                if (p.getTotalResponseTimeMs() < winner.getTotalResponseTimeMs()) {
                    winner = p; // Tie breaker!
                }
            }
        }
        broadcast("The game is over! The winner is " + winner.getName() + " with " + winner.getScore() + " points!");
    }

    public boolean isRunning() {
        return running;
    }

    public synchronized void submitAnswer(Player player, String answer) {
        if (currentQuestion == null) return; // Ignore answers if no question is active

        long timeTaken = System.currentTimeMillis() - currentQuestionStartTime;

        if (currentQuestion.isCorrect(answer)) {
            player.addScore(1, timeTaken);
            player.getClientConnection().sendMessage("Correct! Time: " + timeTaken + "ms");
        } else {
            player.addPenaltyTime(TIME_LIMIT_MS);
            player.getClientConnection().sendMessage("Incorrect!");
        }
    }
}