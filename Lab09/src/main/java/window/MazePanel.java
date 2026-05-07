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

        int rows = maze.getHeight();
        int cols = maze.getWidth();

        int cellWidth = getWidth() / cols;
        int cellHeight = getHeight() / rows;

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Cell cell = maze.getCell(x, y);


                if (cell.isWall()) {
                    g.setColor(Color.BLACK);
                } else if (cell == maze.getExitCell() && cell.getCurrentOccupant() == null) {
                    g.setColor(Color.GREEN);
                } else if (cell.getCurrentOccupant() != null) {
                    Entity occupant = cell.getCurrentOccupant();
                    if (occupant instanceof Bunny) {
                        g.setColor(Color.PINK);
                    } else if (occupant instanceof Robot) {
                        g.setColor(Color.RED);
                    }
                } else {
                    g.setColor(Color.WHITE);
                }

                g.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
            }
        }
    }
}