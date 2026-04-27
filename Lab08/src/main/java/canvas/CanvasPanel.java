package canvas;

import org.example.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CanvasPanel extends JPanel {

    private final Main frame;

    public CanvasPanel(Main frame) {
        this.frame = frame;
        setBackground(Color.white);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Maze maze = frame.getMaze();
                if(maze == null) return;

                int cols = maze.getCols();
                int rows = maze.getRows();
                int cellSize = Math.min(getWidth() / cols, getHeight() / rows);
                int xOffset = (getWidth() - (cols * cellSize)) / 2;
                int yOffset = (getHeight() - (rows * cellSize)) / 2;

                int col = (e.getX() - xOffset) / cellSize;
                int row = (e.getY() - yOffset) / cellSize;

                if(row >= 0 && row < rows && col  >= 0 && col < cols) {
                    int cellX = xOffset + (col * cellSize);
                    int cellY = yOffset + (row * cellSize);

                    int distLeft = e.getX() - cellX;
                    int distRight = cellSize - distLeft;
                    int distTop = e.getY() - cellY;
                    int distBottom = cellSize - distTop;

                    int minDist = Math.min(Math.min(distLeft, distRight), Math.min(distTop, distBottom));

                    if(minDist == distTop) maze.toggleWall(row,col,"TOP");
                    else if(minDist == distRight) maze.toggleWall(row,col,"RIGHT");
                    else if(minDist == distBottom) maze.toggleWall(row,col,"BOTTOM");
                    else if(minDist == distLeft) maze.toggleWall(row,col,"LEFT");

                    repaint();
                }
            }
        });
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        Maze maze = frame.getMaze();

        if(maze == null) {
            return;
        }

        int rows = maze.getRows();
        int cols = maze.getCols();

        int cellWidth = getWidth() / cols;
        int cellHeight = getHeight() / rows;
        int cellSize = Math.min(cellWidth, cellHeight);

        int xOffset = (getWidth() - (cols * cellSize)) / 2;
        int yOffset = (getHeight() - (rows * cellSize)) / 2;

        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                int x = xOffset + col * cellSize;
                int y = yOffset + row * cellSize;

                g2d.setColor(getBackground());
                g2d.fillRect(x, y, cellSize, cellSize);

                g2d.setColor(Color.black);
                g2d.setStroke(new BasicStroke(2));

                Cell cell = maze.getCell(row, col);

                if(cell.hasTopWall()) {
                    g2d.drawLine(x, y, x + cellSize, y);
                }
                if(cell.hasBottomWall()) {
                    g2d.drawLine(x,y+cellSize,x+cellSize,y+cellSize);
                }

                if(cell.hasLeftWall()) {
                    g2d.drawLine(x,y,x,y+cellSize);
                }

                if(cell.hasRightWall()) {
                    g2d.drawLine(x+cellSize,y,x+cellSize,y+cellSize);
                }

            }
        }
    }


}
