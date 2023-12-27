package es.ull.patrones.factory.products;

import javax.swing.*;

public abstract class PieChart extends JFrame {
    public PieChart(String s) {
        super(s);
    }

    public abstract void display();
}
