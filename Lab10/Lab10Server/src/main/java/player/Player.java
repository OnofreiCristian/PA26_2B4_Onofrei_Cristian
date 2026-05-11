package player;

import server.ClientThread;

public class Player {
    private final String name;
    private final ClientThread clientConnection;
    private int score = 0;
    private long totalResponseTimeMs = 0;

    public Player(String name, ClientThread clientConnection) {
        this.name = name;
        this.clientConnection = clientConnection;
    }

    public void addScore(int points, long responseTime) {
        this.score += points;
        this.totalResponseTimeMs += responseTime;
    }
    public void addPenaltyTime(long timeLimit) {
        this.totalResponseTimeMs += timeLimit;
    }

    public int getScore() { return score; }
    public long getTotalResponseTimeMs() { return totalResponseTimeMs; }
    public String getName() { return name; }
    public ClientThread getClientConnection() { return clientConnection; }
}