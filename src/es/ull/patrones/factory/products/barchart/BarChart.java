package es.ull.patrones.factory.products.barchart;

import es.ull.patrones.model.Brand;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public abstract class BarChart extends JPanel {

    public BarChart(String s) {

    }

    void customizeRenderer(JFreeChart chart, List<Brand> brands) {
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        // Configurar colores distintos para cada barra
        for (int i = 0; i < brands.size(); i++) {
            renderer.setSeriesPaint(i, getRandomColor());
        }
    }

    private Color getRandomColor() {
        // Generar un color aleatorio
        return new Color(
                (int) (Math.random() * 256),
                (int) (Math.random() * 256),
                (int) (Math.random() * 256)
        );
    }
}
