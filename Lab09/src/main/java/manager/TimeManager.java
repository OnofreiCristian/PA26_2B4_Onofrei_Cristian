package manager;

import maze_logic.Maze;
import window.GameWindow;

public class TimeManager implements Runnable {
    private final Maze maze;
    private final long timeLimitMillis;
    private final long startTime;
    private final GameWindow window;


    public TimeManager(Maze maze, long timeLimitSeconds, GameWindow window) {
        this.maze = maze;
        this.timeLimitMillis = timeLimitSeconds * 1000;
        this.startTime = System.currentTimeMillis();
        this.window = window;
    }

    @Override
    public void run() {
        while (!maze.isGameOver()) {
            long elapsedTime = System.currentTimeMillis() - startTime;

            if (maze.isGameOver()) break;


            window.refreshVisuals();

            System.out.println("⏳ Running Time: " + (elapsedTime / 1000) + "s / " + (timeLimitMillis / 1000) + "s");

            if (elapsedTime >= timeLimitMillis) {
                System.out.println("⏰ Time limit exceeded! The game is forced to stop.");
                maze.setGameOver(true);
                break;
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}