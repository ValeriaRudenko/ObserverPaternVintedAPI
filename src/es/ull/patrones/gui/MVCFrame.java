package es.ull.patrones.gui;

import es.ull.patrones.factory.products.barchart.BarChart;
import es.ull.patrones.factory.products.barchart.FavouritesBarChart;
import es.ull.patrones.factory.products.barchart.ItemsBarChart;
import es.ull.patrones.factory.products.piechart.AutenticityCheckPieChart;
import es.ull.patrones.factory.products.piechart.LuxuryPieChart;
import es.ull.patrones.factory.products.piechart.PieChart;
import es.ull.patrones.factory.products.piechart.VisibleInListingsPieChart;
import es.ull.patrones.model.Brand;
import es.ull.patrones.strategy.BrandJSONParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MVCFrame extends JFrame {
    private JPanel filtersPanel;
    private JPanel buttonsPanel;
    private JPanel logoPanel;

    private JLabel minNoOfFavouritesLabel;
    private JLabel minItemCountLabel;

    private JTextField minNumberOfFavourites;
    private JTextField maxNumberOfFavourites;
    private JTextField minNumberOfItems;
    private JTextField maxNumberOfItems;

    private JButton searchButton;

    private JCheckBox visibleInListingsCheckbox;
    private JCheckBox luxuryCheckbox;
    private JCheckBox authenticityCheckbox;
    private JCheckBox itemsBarChartCheckbox;
    private JCheckBox favouritesBarChartCheckbox;

    public MVCFrame() {
        this.setSize(400, 300);
        this.getContentPane().setLayout(new BorderLayout());
        this.setBackground(new Color(190, 245, 255));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Vinted Scorecard");

        ImageIcon vintedIcon = new javax.swing.ImageIcon("images/vinted_icon.png");
        Image vintedIconIcon = vintedIcon.getImage();
        this.setIconImage(vintedIconIcon);

        filtersPanel = new JPanel();
        filtersPanel.setBackground(new Color(190, 245, 255));
        filtersPanel.setLayout(new GridLayout(7, 2));
        buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(190, 245, 255));
        logoPanel = new JPanel();
        logoPanel.setSize(150, 100);
        logoPanel.setLayout(new BorderLayout());
        logoPanel.setBackground(new Color(190, 245, 255));

        ImageIcon logo = new ImageIcon("images/Vinted_Logo.png");
        Image originalIcon = logo.getImage();
        Image escalatedLogo = originalIcon.getScaledInstance(75, 25, Image.SCALE_SMOOTH);
        logo = new ImageIcon(escalatedLogo);
        JLabel vintedLogo = new JLabel(logo);
        logoPanel.add(vintedLogo, BorderLayout.CENTER);

        minNoOfFavouritesLabel = new JLabel("No. of favourites (min - max): ");
        minItemCountLabel = new JLabel("No. of items (min - max): ");

        minNumberOfFavourites = new JTextField(10);
        maxNumberOfFavourites = new JTextField(10);
        minNumberOfItems = new JTextField(10);
        maxNumberOfItems = new JTextField(10);

        searchButton = new JButton("Search items");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSearchButtonClick();
            }
        });

        visibleInListingsCheckbox = new JCheckBox("Visible in Listings");
        luxuryCheckbox = new JCheckBox("Luxury");
        authenticityCheckbox = new JCheckBox("Authenticity Check");
        itemsBarChartCheckbox = new JCheckBox("Items Bar Chart");
        favouritesBarChartCheckbox = new JCheckBox("Favourites Bar Chart");

        filtersPanel.add(minNoOfFavouritesLabel);
        filtersPanel.add(new JLabel());
        filtersPanel.add(minNumberOfFavourites);
        filtersPanel.add(maxNumberOfFavourites);
        filtersPanel.add(minItemCountLabel);
        filtersPanel.add(minNumberOfItems);
        filtersPanel.add(maxNumberOfItems);
        filtersPanel.add(visibleInListingsCheckbox);
        filtersPanel.add(luxuryCheckbox);
        filtersPanel.add(authenticityCheckbox);
        filtersPanel.add(itemsBarChartCheckbox);
        filtersPanel.add(favouritesBarChartCheckbox);

        buttonsPanel.add(searchButton);

        this.add(logoPanel, BorderLayout.NORTH);
        this.add(filtersPanel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private void onSearchButtonClick() {
        try {
            URL url = new URL("https://raw.githubusercontent.com/0AlphaZero0/Vinted-data/main/DATA/brand.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

            reader.close();
            connection.disconnect();

            String jsonData = content.toString();

            if (!(minNumberOfFavourites.getText().isEmpty() && maxNumberOfFavourites.getText().isEmpty())) {
                if (Integer.parseInt(minNumberOfFavourites.getText()) > Integer.parseInt(maxNumberOfFavourites.getText())) {
                    throw new IllegalArgumentException("Incorrect input data");
                }
            }
            if (!(minNumberOfItems.getText().isEmpty() && maxNumberOfItems.getText().isEmpty())) {
                if (Integer.parseInt(minNumberOfItems.getText()) > Integer.parseInt(maxNumberOfItems.getText())) {
                    throw new IllegalArgumentException("Incorrect input data");
                }
            }

            int minFavourites = 0;
            int maxFavourites;
            int minItems = 0;
            int maxItems;

            if (!(minNumberOfFavourites.getText().isEmpty())) {
                minFavourites = Integer.parseInt(minNumberOfFavourites.getText());
            }

            if (!(minNumberOfItems.getText().isEmpty())) {
                minItems = Integer.parseInt(minNumberOfItems.getText());
            }

            if (maxNumberOfFavourites.getText().isEmpty()) {
                maxFavourites = 1000000000;
            } else {
                maxFavourites = Integer.parseInt(maxNumberOfFavourites.getText());
            }

            if (maxNumberOfItems.getText().isEmpty()) {
                maxItems = 1000000000;
            } else {
                maxItems = Integer.parseInt(maxNumberOfItems.getText());
            }

            BrandJSONParser parserTest = new BrandJSONParser(jsonData, minFavourites, maxFavourites, minItems, maxItems);
            List<Brand> brands = parserTest.getBrandList();

            Scoreboard scoreboard = new Scoreboard();

            if (visibleInListingsCheckbox.isSelected()) {
                JPanel visible = new VisibleInListingsPieChart(brands);
                scoreboard.addChart(visible);
            }

            if (luxuryCheckbox.isSelected()) {
                JPanel luxury = new LuxuryPieChart(brands);
                scoreboard.addChart(luxury);
            }

            if (authenticityCheckbox.isSelected()) {
                JPanel authenticity = new AutenticityCheckPieChart(brands);
                scoreboard.addChart(authenticity);
            }

            if (itemsBarChartCheckbox.isSelected()) {
                JPanel noItems = new ItemsBarChart(brands);
                scoreboard.addChart(noItems);
            }

            if (favouritesBarChartCheckbox.isSelected()) {
                JPanel noFavourites = new FavouritesBarChart(brands);
                scoreboard.addChart(noFavourites);
            }

            SwingUtilities.invokeLater(() -> {
                scoreboard.setSize(1200, 800);
                scoreboard.setLocationRelativeTo(null);
                scoreboard.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                scoreboard.setVisible(true);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


