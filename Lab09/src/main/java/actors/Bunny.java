package actors;

import maze_logic.Cell;
import maze_logic.Maze;
import java.util.List;
import java.util.Random;

public class Bunny extends Entity {
    private final Random random = new Random();

    public Bunny(String name, Maze maze) {
        super(name, maze);
        this.speedDelay = 1000;
    }

    @Override
    public void run() {

        while (!maze.isGameOver()) {
            List<Cell> possibleMoves = maze.getAdjacentValidCells(currentCell);

            if (!possibleMoves.isEmpty()) {
                Cell nextCell = possibleMoves.get(random.nextInt(possibleMoves.size()));

                if (maze.placeEntity(this, nextCell)) {
                    if (nextCell == maze.getExitCell()) {
                        System.out.println("🎉 " + name + " found the exit! Bunny wins!");
                        maze.setGameOver(true);
                        break;
                    }
                }
            }

            try {
                while (isPaused && !maze.isGameOver()) {
                    Thread.sleep(100);
                }
                Thread.sleep(speedDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(name + " was interrupted.");
                break;
            }
        }
    }
}