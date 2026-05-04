package window;

import actors.Bunny;
import actors.Entity;
import actors.Robot;
import maze_logic.Cell;
import maze_logic.Maze;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

public class MazePanel extends JPanel {
    private final Maze maze;

    public MazePanel(Maze maze) {
        this.maze = maze;
        // Set a default background color
        setBackground(Color.DARK_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (maze == null) return;

        // Fetch dimensions from the maze
        int rows = maze.getHeight();
        int cols = maze.getWidth();

        // Calculate dynamic cell sizes so the grid scales if the window is resized
        int cellWidth = getWidth() / cols;
        int cellHeight = getHeight() / rows;

        // Iterate through the grid and draw each cell
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Cell cell = maze.getCell(x, y);

                // 1. Determine the color based on the cell's state
                if (cell.isWall()) {
                    g.setColor(Color.BLACK); // Walls are Black
                } else if (cell == maze.getExitCell() && cell.getCurrentOccupant() == null) {
                    g.setColor(Color.GREEN); // The Exit is Green
                } else if (cell.getCurrentOccupant() != null) {
                    Entity occupant = cell.getCurrentOccupant();
                    if (occupant instanceof Bunny) {
                        g.setColor(Color.PINK); // The Bunny is Pink
                    } else if (occupant instanceof Robot) {
                        g.setColor(Color.RED); // Robots are Red
                    }
                } else {
                    g.setColor(Color.WHITE); // Empty floors are White
                }

                // 2. Draw the filled block
                g.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);

                // 3. Draw a subtle grid line border around the cell
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
            }
        }
    }
}