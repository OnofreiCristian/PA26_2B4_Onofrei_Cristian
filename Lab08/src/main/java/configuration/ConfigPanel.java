package configuration;

import org.example.Main;

import javax.swing.*;
import java.awt.*;

public class ConfigPanel extends JPanel {

    private final Main frame;

    private JLabel rowsLabel;
    private JLabel colsLabel;
    private JSpinner rowsSpinner;
    private JSpinner colsSpinner;
    private JButton drawButton;

    public ConfigPanel(Main frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        setLayout(new FlowLayout());

        rowsLabel = new JLabel("Rows:");
        colsLabel = new JLabel("Columns:");

        rowsSpinner = new JSpinner(new SpinnerNumberModel(10, 2, 100, 1));
        colsSpinner = new JSpinner(new SpinnerNumberModel(10, 2, 100, 1));

        drawButton = new JButton("Draw Cells");

        add(rowsLabel);
        add(rowsSpinner);
        add(colsLabel);
        add(colsSpinner);
        add(drawButton);
    }

    public int getRows() {
        return ((Number) rowsSpinner.getValue()).intValue();
    }

    public int getCols() {
        return ((Number) colsSpinner.getValue()).intValue();
    }

    public int getColumns() {
        return ((Number) colsSpinner.getValue()).intValue();
    }


}
