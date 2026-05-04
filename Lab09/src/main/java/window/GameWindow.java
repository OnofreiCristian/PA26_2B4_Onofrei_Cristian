package window;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import maze_logic.Maze;

public class GameWindow extends JFrame {

    // We keep a reference to the panel so we can easily tell it to redraw
    private final MazePanel mazePanel;

    public GameWindow(Maze maze) {
        setTitle("Bunny vs Robots - Maze Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        mazePanel = new MazePanel(maze);
        add(mazePanel);

        setVisible(true);
    }

    public void refreshVisuals() {
        mazePanel.repaint();
    }
}