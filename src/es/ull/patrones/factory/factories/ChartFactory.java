package es.ull.patrones.factory.factories;

import es.ull.patrones.factory.products.barchart.BarChart;
import es.ull.patrones.model.Brand;

import javax.swing.*;
import java.util.List;

public interface ChartFactory {
    public JPanel createChart(List<Brand> brands, String type);
}
