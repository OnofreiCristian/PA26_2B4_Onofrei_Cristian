package control;

import canvas.Maze;
import org.example.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;

public class ControlPanel extends JPanel {

    private final Main frame;
    private JButton createBtn;
    private JButton resetBtn;
    private JButton exitBtn, validateBtn, exportBtn, saveBtn, loadBtn;

    public ControlPanel(Main frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        setLayout(new FlowLayout());


        createBtn = new JButton("Create");
        resetBtn = new JButton("Reset");
        exitBtn = new JButton("Exit");
        validateBtn = new JButton("Validate");
        exportBtn = new JButton("Export");
        saveBtn = new JButton("Save");
        loadBtn = new JButton("Load");

        add(createBtn);
        add(resetBtn);
        add(exitBtn);
        add(validateBtn);
        add(exportBtn);
        add(saveBtn);
        add(loadBtn);


        exitBtn.addActionListener((ActionEvent e) -> {
            System.exit(0);
        } );

        createBtn.addActionListener((ActionEvent e) -> {
            int rows = frame.getConfigPanel().getRows();
            int cols = frame.getConfigPanel().getCols();

            Maze newMaze = new Maze(rows,cols);
            newMaze.randomlyRemoveWalls();

            frame.setMaze(newMaze);
            frame.repaint();
        });

        resetBtn.addActionListener((ActionEvent e) -> {
            frame.getMaze().resetMaze();
            frame.repaint();
        });

        validateBtn.addActionListener((ActionEvent e) -> {
           boolean traverseable = frame.getMaze().isTraversable();

           if(traverseable){
               JOptionPane.showMessageDialog(frame,"The maze is traversable", "Validation", JOptionPane.INFORMATION_MESSAGE);

           }
           else {
               JOptionPane.showMessageDialog(frame,"No Path!", "Validation", JOptionPane.ERROR_MESSAGE);

           }
        });

        exportBtn.addActionListener((ActionEvent e) -> {
            try {
                JPanel canvas = frame.getCanvasPanel();
                BufferedImage image = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = image.createGraphics();
                canvas.paint(g2d);
                g2d.dispose();

                ImageIO.write(image, "png", new File("maze.png"));
                JOptionPane.showMessageDialog(frame, "Exported successfully to maze.png!");

            }catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "An error occurred while exporting to maze.png!", "Error",  JOptionPane.ERROR_MESSAGE);
            }
        });

        saveBtn.addActionListener((ActionEvent e) -> {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("maze.dat"))) {
                oos.writeObject(frame.getMaze());
                JOptionPane.showMessageDialog(frame, "Maze saved successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error saving maze: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loadBtn.addActionListener((ActionEvent e) -> {
           try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("maze.dat"))) {
               Maze loadMaze = (Maze) ois.readObject();
               frame.setMaze(loadMaze);
               frame.repaint();
               JOptionPane.showMessageDialog(frame, "Maze loaded successfully!");
           } catch (Exception ex) {
               JOptionPane.showMessageDialog(frame, "Error loading maze.dat: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
           }
        });

    }


}
