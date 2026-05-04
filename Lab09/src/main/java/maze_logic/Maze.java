package maze_logic;


import actors.Bunny;
import actors.Entity;
import actors.Robot;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Maze {
    private final int width;
    private final int height;
    private final Cell[][] grid;
    private Cell exitCell;
    private final Random random = new Random();
    private volatile boolean isGameOver = false;

    public Maze(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Cell[height][width];
        initializeGrid();
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Cell getCell(int x, int y) { return grid[y][x]; }

    private void initializeGrid() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                boolean isWall = (y == 0 || y == height - 1 || x == 0 || x == width - 1);
                grid[y][x] = new Cell(x, y, isWall);
            }
        }
        exitCell = grid[height - 1][width / 2];
        exitCell.setWall(false);
    }

    public Cell getRandomEmptyCell() {
        Cell cell;
        do {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            cell = grid[y][x];
        } while (cell.isWall() || cell.getCurrentOccupant() != null);
        return cell;
    }

    public synchronized boolean placeEntity(Entity entity, Cell targetCell) {
        if (!targetCell.isWall() && targetCell.getCurrentOccupant() == null) {
            targetCell.setCurrentOccupant(entity);
            if (entity.getCurrentCell() != null) {
                entity.getCurrentCell().setCurrentOccupant(null);
            }
            entity.setCurrentCell(targetCell);
            return true;
        }
        return false;
    }

    public Cell getExitCell() { return exitCell; }

    public boolean isGameOver() { return isGameOver; }
    public void setGameOver(boolean gameOver) { isGameOver = gameOver; }

    public List<Cell> getAdjacentValidCells(Cell currentCell) {
        List<Cell> validCells = new ArrayList<>();
        int x = currentCell.getX();
        int y = currentCell.getY();

        if (!grid[y - 1][x].isWall()) validCells.add(grid[y - 1][x]);
        if (!grid[y + 1][x].isWall()) validCells.add(grid[y + 1][x]);
        if (!grid[y][x - 1].isWall()) validCells.add(grid[y][x - 1]);
        if (!grid[y][x + 1].isWall()) validCells.add(grid[y][x + 1]);

        return validCells;
    }


    public synchronized void displayBoard() {
        System.out.print("\033[H");
        System.out.flush();

        System.out.println("=== CURRENT MAZE STATE ===");
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = grid[y][x];

                if (cell.isWall()) {
                    System.out.print("# "); // Wall
                } else if (cell == exitCell && cell.getCurrentOccupant() == null) {
                    System.out.print("E "); // Exit
                } else if (cell.getCurrentOccupant() != null) {
                    Entity occupant = cell.getCurrentOccupant();
                    if (occupant instanceof Bunny) {
                        System.out.print("B "); // Bunny
                    } else if (occupant instanceof Robot) {
                        System.out.print("R "); // Robot
                    }
                } else {
                    System.out.print(". "); // Empty space
                }
            }
            System.out.println(); // Next row
        }
        System.out.println("==========================");
        System.out.println("Enter command (e.g., 'stop all', 'fast bunny'): ");
    }
}