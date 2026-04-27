package canvas;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

@Getter
@Setter
public class Maze implements Serializable { // Added Serializable

    private final int rows;
    private final int cols;
    private final Cell[][] grid;

    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Cell[rows][cols];
        initializeGrid();
    }

    private void initializeGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
    }

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    public void randomlyRemoveWalls() {
        Random rand = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (rand.nextBoolean()) toggleWall(i, j, "TOP");
                if (rand.nextBoolean()) toggleWall(i, j, "LEFT");
                if (rand.nextBoolean()) toggleWall(i, j, "RIGHT");
                if (rand.nextBoolean()) toggleWall(i, j, "BOTTOM");
            }
        }
    }

    public void resetMaze() {
        initializeGrid();
    }

    public void toggleWall(int r, int c, String direction) {
        Cell cell = grid[r][c];
        switch (direction) {
            case "TOP":
                cell.setTopWall(!cell.hasTopWall());
                if (r > 0) grid[r - 1][c].setBottomWall(cell.hasTopWall());
                break;
            case "BOTTOM":
                cell.setBottomWall(!cell.hasBottomWall());
                if (r < rows - 1) grid[r + 1][c].setTopWall(cell.hasBottomWall());
                break;
            case "LEFT":
                cell.setLeftWall(!cell.hasLeftWall());
                if (c > 0) grid[r][c - 1].setRightWall(cell.hasLeftWall());
                break;
            case "RIGHT":
                cell.setRightWall(!cell.hasRightWall());
                if (c < cols - 1) grid[r][c + 1].setLeftWall(cell.hasRightWall());
                break;
        }
    }

    public boolean isTraversable() {
        boolean[][] visited = new boolean[rows][cols];
        Queue<Cell> queue = new LinkedList<>();

        queue.add(grid[0][0]);
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            Cell curr = queue.poll();
            int r = curr.getRow();
            int c = curr.getCol();

            if (r == rows - 1 && c == cols - 1) return true;

            if (!curr.hasTopWall() && r > 0 && !visited[r - 1][c]) {
                visited[r - 1][c] = true; queue.add(grid[r - 1][c]);
            }
            if (!curr.hasBottomWall() && r < rows - 1 && !visited[r + 1][c]) {
                visited[r + 1][c] = true; queue.add(grid[r + 1][c]);
            }
            if (!curr.hasLeftWall() && c > 0 && !visited[r][c - 1]) {
                visited[r][c - 1] = true; queue.add(grid[r][c - 1]);
            }
            if (!curr.hasRightWall() && c < cols - 1 && !visited[r][c + 1]) {
                visited[r][c + 1] = true; queue.add(grid[r][c + 1]);
            }
        }
        return false;
    }
}