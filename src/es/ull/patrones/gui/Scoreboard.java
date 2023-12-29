package es.ull.patrones.gui;

import javax.swing.*;
import java.awt.*;

public class Scoreboard extends JFrame {
    private JPanel chartsPanel;

    public Scoreboard() {
        // Configure the main frame for the scoreboard
        this.setLayout(new BorderLayout());
        this.setTitle("Vinted Scoreboard");

        // Panel to display charts
        chartsPanel = new JPanel();
        chartsPanel.setLayout(new GridLayout(2, 3)); // You can adjust the layout as needed

        this.add(chartsPanel, BorderLayout.CENTER);
    }

    public void addChart(JComponent chart) {
        chartsPanel.add(chart);
    }
}
