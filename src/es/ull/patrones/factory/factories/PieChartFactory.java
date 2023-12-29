package es.ull.patrones.factory.factories;

import es.ull.patrones.factory.products.barchart.BarChart;
import es.ull.patrones.factory.products.barchart.FavouritesBarChart;
import es.ull.patrones.factory.products.barchart.ItemsBarChart;
import es.ull.patrones.factory.products.piechart.AutenticityCheckPieChart;
import es.ull.patrones.factory.products.piechart.LuxuryPieChart;
import es.ull.patrones.factory.products.piechart.PieChart;
import es.ull.patrones.factory.products.piechart.VisibleInListingsPieChart;
import es.ull.patrones.model.Brand;

import javax.swing.*;
import java.util.List;

public class PieChartFactory implements ChartFactory {
    @Override
    public JPanel createChart(List<Brand> brands, String type) {
        return switch (type) {
            case "authenticity" -> new AutenticityCheckPieChart(brands);
            case "luxury" -> new LuxuryPieChart(brands);
            case "visibleInListings" -> new VisibleInListingsPieChart(brands);
            default -> null;
        };
    }
}
