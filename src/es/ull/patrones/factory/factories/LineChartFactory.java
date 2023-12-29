package es.ull.patrones.factory.factories;

import es.ull.patrones.factory.products.barchart.BarChart;
import es.ull.patrones.factory.products.barchart.FavouritesBarChart;
import es.ull.patrones.factory.products.barchart.ItemsBarChart;
import es.ull.patrones.model.Brand;

import javax.swing.*;
import java.util.List;

public class LineChartFactory implements ChartFactory {
    @Override
    public JPanel createChart(List<Brand> brands, String type) {
        if (type.equals("line")) {
            return null;
        }else if (type.equals("another")) {
            return null;
        }
        return null;
    }
}
