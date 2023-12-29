package es.ull.patrones.factory.products.piechart;

import es.ull.patrones.model.Brand;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.util.List;

public class LuxuryPieChart extends PieChart {
    public LuxuryPieChart(List<Brand> brands) {
        super("Pie Chart Is Luxury");
        // Crear un conjunto de datos para el pie chart
        DefaultPieDataset dataset = createDataset(brands);

        // Crear el gráfico de tarta
        JFreeChart chart = ChartFactory.createPieChart(
                "Number of luxury brands",
                dataset,
                true, // Mostrar leyenda
                true,
                false
        );

        // Crear un panel de gráfico
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(370, 370));
        add(chartPanel);
    }

    private DefaultPieDataset createDataset(List<Brand> brands) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        // Contar el número de marcas con el atributo a true y a false
        int trueCount = 0;
        int falseCount = 0;

        for (Brand brand : brands) {
            if (brand.getIsLuxury()) {
                trueCount++;
            } else {
                falseCount++;
            }
        }

        // Agregar datos al conjunto de datos
        dataset.setValue("Is luxury", trueCount);
        dataset.setValue("Is not luxury", falseCount);

        return dataset;
    }
    @Override
    public void display() {

    }

}
