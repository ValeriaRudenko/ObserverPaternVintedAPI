package es.ull.patrones.factory.products;

import javax.swing.JPanel;

import es.ull.patrones.model.Brand;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;
import java.util.List;

public class VisibleInListingsPieChart extends PieChart {
    public VisibleInListingsPieChart(List<Brand> brands) {
        super("Pie Chart Visible In Listings");
        // Crear un conjunto de datos para el pie chart
        DefaultPieDataset dataset = createDataset(brands);

        // Crear el gráfico de tarta
        JFreeChart chart = ChartFactory.createPieChart(
                "Number of brands visible in listings",
                dataset,
                true, // Mostrar leyenda
                true,
                false
        );

        // Crear un panel de gráfico
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(370, 370));
        setContentPane(chartPanel);
    }

    private DefaultPieDataset createDataset(List<Brand> brands) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        // Contar el número de marcas con el atributo a true y a false
        int trueCount = 0;
        int falseCount = 0;

        for (Brand brand : brands) {
            if (brand.getIsVisibleInListings()) {
                trueCount++;
            } else {
                falseCount++;
            }
        }

        // Agregar datos al conjunto de datos
        dataset.setValue("Is visible in listings", trueCount);
        dataset.setValue("Is not visible in listings", falseCount);

        return dataset;
    }
    @Override
    public void display() {

    }
}
