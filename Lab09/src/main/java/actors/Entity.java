package actors;

import maze_logic.Cell;
import maze_logic.Maze;

public abstract class Entity implements Runnable {
    protected String name;
    protected Cell currentCell;
    protected Maze maze;

    // --- Phase 4: NEW Control variables ---
    // We use protected so Bunny and Robot can access them directly
    protected volatile int speedDelay = 300;
    protected volatile boolean isPaused = false;

    public Entity(String name, Maze maze) {
        this.name = name;
        this.maze = maze;
    }
    public Cell getCurrentCell() { return currentCell; }
    public void setCurrentCell(Cell currentCell) { this.currentCell = currentCell; }
    public String getName() { return name; }

    public void setSpeedDelay(int delay) { this.speedDelay = delay; }
    public void setPaused(boolean paused) { this.isPaused = paused; }
    public boolean isPaused() { return isPaused; }

    @Override
    public abstract void run();
}