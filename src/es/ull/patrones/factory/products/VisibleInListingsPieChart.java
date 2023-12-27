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

import java.util.List;

public class VisibleInListingsPieChart extends PieChart {
    public VisibleInListingsPieChart(List<Brand> brands) {


    }
    @Override
    public void display() {

    }
}
