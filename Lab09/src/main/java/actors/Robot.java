package actors;

import maze_logic.Cell;
import maze_logic.Maze;
import memory.SharedMemory;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Robot extends Entity {
    private final SharedMemory sharedMemory;
    private final Random random = new Random();

    private final Set<Cell> visitedCells = new HashSet<>();

    public Robot(String name, Maze maze, SharedMemory sharedMemory) {
        super(name, maze);
        this.sharedMemory = sharedMemory;
        this.speedDelay = 1000;
    }

    @Override
    public void run() {

        while (!maze.isGameOver()) {
            List<Cell> possibleMoves = maze.getAdjacentValidCells(currentCell);

            if (!possibleMoves.isEmpty()) {
                Cell nextCell = null;

                for (Cell move : possibleMoves) {
                    if (move.getCurrentOccupant() instanceof Bunny) {
                        nextCell = move;
                        //System.out.println("🚨 " + name + " caught the bunny at " + nextCell + "! Robots win!");
                        synchronized (sharedMemory) {
                            sharedMemory.addInformation("BUNNY_STATUS", "Caught by " + name);
                        }
                        maze.setGameOver(true);
                        break;
                    }
                }
                if (nextCell == null && !maze.isGameOver()) {
                    List<Cell> unvisited = possibleMoves.stream()
                            .filter(c -> !visitedCells.contains(c))
                            .collect(Collectors.toList());

                    if (!unvisited.isEmpty()) {
                        nextCell = unvisited.get(random.nextInt(unvisited.size()));
                    } else {
                        nextCell = possibleMoves.get(random.nextInt(possibleMoves.size()));
                    }
                }

                if (nextCell != null && !maze.isGameOver()) {
                    if (maze.placeEntity(this, nextCell)) {
                        visitedCells.add(nextCell);
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