package es.ull.patrones.factory.factories;

import es.ull.patrones.factory.products.barchart.BarChart;
import es.ull.patrones.factory.products.barchart.FavouritesBarChart;
import es.ull.patrones.factory.products.barchart.ItemsBarChart;
import es.ull.patrones.model.Brand;

import javax.swing.*;
import java.util.List;

public class BarChartFactory implements ChartFactory{

    @Override
    public JPanel createChart(List<Brand> brands, String type) {
        if (type.equals("favourites")) {
            return new FavouritesBarChart(brands);
        }else if (type.equals("items")) {
            return new ItemsBarChart(brands);
        }
        return null;
    }
}
