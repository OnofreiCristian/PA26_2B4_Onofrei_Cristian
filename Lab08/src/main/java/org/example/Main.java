package org.example;

import canvas.CanvasPanel;
import canvas.Maze;
import configuration.ConfigPanel;
import control.ControlPanel;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private ConfigPanel configPanel;
    private CanvasPanel canvasPanel;
    private ControlPanel controlPanel;
    private Maze maze;

    public Main() {
        super("Maze Generator");
        init();
    }

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLayout(new BorderLayout());

        configPanel = new ConfigPanel(this);
        add(configPanel, BorderLayout.NORTH);

        canvasPanel = new CanvasPanel(this);
        add(canvasPanel, BorderLayout.CENTER);

        controlPanel = new ControlPanel(this);
        add(controlPanel, BorderLayout.SOUTH);

        this.maze = new Maze(10,10);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public Maze getMaze() {
        return maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    public CanvasPanel getCanvasPanel() { return canvasPanel; }

    public ConfigPanel getConfigPanel() {return configPanel;}

    static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
        }
}
