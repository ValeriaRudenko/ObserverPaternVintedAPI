package es.ull.patrones.gui;

import es.ull.patrones.observer.BrandObserver;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Scoreboard extends JFrame {
    private JPanel chartsPanel;
    private List<BrandObserver> observers = new ArrayList<>();
    private String userOptions;
    public Scoreboard() {
        // Configure the main frame for the scoreboard
        this.setLayout(new BorderLayout());
        this.setTitle("Vinted Scoreboard");

        // Panel to display charts
        chartsPanel = new JPanel();
        chartsPanel.setLayout(new GridLayout(2, 3)); // You can adjust the layout as needed

        this.add(chartsPanel, BorderLayout.CENTER);
    }

    public void setUserOptions(String options) {
        this.userOptions = options;
        notifyObservers();
    }

    // Métodos para observadores
    public void registerObserver(BrandObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (BrandObserver observer : observers) {
            observer.updateOptions(userOptions);
        }
    }

    public void addChart(JComponent chart) {
        chartsPanel.add(chart);
    }
}
