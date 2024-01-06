package es.ull.patrones.gui;

import es.ull.patrones.model.Product;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductChart {

  public static void createAndShowCombinedChart(List<Product> productList) {
    DefaultPieDataset favouritesDataset = new DefaultPieDataset();
    DefaultCategoryDataset priceDataset = new DefaultCategoryDataset();


    for (Product product : productList) {
      favouritesDataset.setValue(product.getTitle(), product.getFavourites());
      priceDataset.addValue(Double.parseDouble(product.getTotalAmount()), "Price", product.getTitle());
    }

    JFreeChart favouritesChart = ChartFactory.createPieChart(
            "Favourites Chart", favouritesDataset, true, true, false);

    JFreeChart priceChart = ChartFactory.createBarChart(
            "Price Chart", "Product", "Price", priceDataset, PlotOrientation.VERTICAL, true, true, false);

    customizePieChart(favouritesChart);

    // Crear el panel de gráfico para las gráficas de pastel
    ChartPanel favouritesChartPanel = new ChartPanel(favouritesChart);
    favouritesChartPanel.setPreferredSize(new Dimension(400, 300));

    // Crear el panel de gráfico para el gráfico de barras
    ChartPanel priceChartPanel = new ChartPanel(priceChart);
    priceChartPanel.setPreferredSize(new Dimension(400, 300));

    // Crear el contenedor principal
    JFrame frame = new JFrame("Combined Chart");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setLayout(new GridLayout(1, 2));

    // Agregar los paneles de gráficos al contenedor principal
    frame.add(favouritesChartPanel);
    frame.add(priceChartPanel);

    // Configurar la apariencia del contenedor principal
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  private static void customizePieChart(JFreeChart chart) {
    PiePlot plot = (PiePlot) chart.getPlot();
    plot.setSectionPaint("Favourites Chart", new Color(255, 0, 0));  // Color personalizado para la sección 'Favourites Chart'
    plot.setSectionPaint("Price Chart", new Color(0, 0, 255));        // Color personalizado para la sección 'Price Chart'
    plot.setExplodePercent("Favourites Chart", 0.10);  // Separación de la sección 'Favourites Chart'

    // Utilizar un generador de etiquetas personalizado para mostrar el número de favoritos
    plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({1})"));
  }

}
