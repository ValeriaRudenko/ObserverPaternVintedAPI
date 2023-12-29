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
    // Panels
    private JPanel filtersPanel; // Panel to place the filter fields
    private JPanel buttonsPanel; // Panel to place the buttons
    private JPanel logoPanel;

    // Labels
    private JLabel minNoOfFavouritesLabel;
    private JLabel maxNumberOfFavouritesLabel;
    private JLabel minItemCountLabel;
    private JLabel maxItemCountLabel;
    // private JLabel isVisibleInListings;
    // private JLabel requiresAutenticityCheck;
    // private JLabel isLuxuryCheck;
    private JLabel vintedLogo;

    // Input fields
    private JTextField minNumberOfFavourites;
    private JTextField maxNumberOfFavourites;
    private JTextField minNumberOfItems;
    private JTextField maxNumberOfItems;

    // Buttons
    private JButton searchButton;

    // Checkboxes
    private JCheckBox visibleInListingsCheckbox;
    private JCheckBox luxuryCheckbox;
    private JCheckBox authenticityCheckbox;
    private JCheckBox itemsBarChartCheckbox;
    private JCheckBox favouritesBarChartCheckbox;

    // Constructor method
    public MVCFrame() {
        // Configuration of the main frame
        this.setSize(400, 300);
        this.getContentPane().setLayout(new BorderLayout());
        this.setBackground(new Color(190, 245, 255));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Vinted Scorecard");

        // Icon of the main frame
        ImageIcon vintedIcon = new javax.swing.ImageIcon("images/vinted_icon.png");
        Image vintedIconIcon = vintedIcon.getImage();
        this.setIconImage(vintedIconIcon);

        // Configure panel stuff
        filtersPanel = new JPanel();
        filtersPanel.setBackground(new Color(190, 245, 255));
        filtersPanel.setLayout(new GridLayout(7, 2));
        buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(190, 245, 255));
        logoPanel = new JPanel();
        logoPanel.setSize(150, 100);
        logoPanel.setLayout(new BorderLayout());
        logoPanel.setBackground(new Color(190, 245, 255));

        // Add logo to the label that will be on the top of the window
        ImageIcon logo = new ImageIcon("images/Vinted_Logo.png");
        Image originalIcon = logo.getImage();
        Image escalatedLogo = originalIcon.getScaledInstance(75, 25, Image.SCALE_SMOOTH);
        logo = new ImageIcon(escalatedLogo);
        vintedLogo = new JLabel(logo);
        logoPanel.add(vintedLogo, BorderLayout.CENTER);

        // Search labels
        minNoOfFavouritesLabel = new JLabel("No. of favourites (min - max): ");
        minItemCountLabel = new JLabel("No. of items (min - max): ");

        // Search text fields
        minNumberOfFavourites = new JTextField(10);
        maxNumberOfFavourites = new JTextField(10);
        minNumberOfItems = new JTextField(10);
        maxNumberOfItems = new JTextField(10);

        // Search button
        searchButton = new JButton("Search items");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSearchButtonClick();
            }
        });

        // Checkboxes
        visibleInListingsCheckbox = new JCheckBox("Visible in Listings");
        luxuryCheckbox = new JCheckBox("Luxury");
        authenticityCheckbox = new JCheckBox("Authenticity Check");
        itemsBarChartCheckbox = new JCheckBox("Items Bar Chart");
        favouritesBarChartCheckbox = new JCheckBox("Favourites Bar Chart");

        // Add components to the panels and main frame
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

            /**
             * TODO: Add some way for the user to choose the charts that will be shown in the scoreboard
             * TODO: Use the factories!
             * TODO: Use the Scoreboard class
            */

            // We show the charts
            // This is just a test to check the charts work properly
            if (visibleInListingsCheckbox.isSelected()) {
                SwingUtilities.invokeLater(() -> {
                    PieChart visible = new VisibleInListingsPieChart(brands);
                    visible.setSize(800, 400);
                    visible.setLocationRelativeTo(null);
                    visible.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    visible.setVisible(true);
                });
            }

            if (luxuryCheckbox.isSelected()) {
                SwingUtilities.invokeLater(() -> {
                    PieChart luxury = new LuxuryPieChart(brands);
                    luxury.setSize(800, 400);
                    luxury.setLocationRelativeTo(null);
                    luxury.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    luxury.setVisible(true);
                });
            }

            if (authenticityCheckbox.isSelected()) {
                SwingUtilities.invokeLater(() -> {
                    PieChart authenticity = new AutenticityCheckPieChart(brands);
                    authenticity.setSize(800, 400);
                    authenticity.setLocationRelativeTo(null);
                    authenticity.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    authenticity.setVisible(true);
                });
            }

            if (itemsBarChartCheckbox.isSelected()) {
                SwingUtilities.invokeLater(() -> {
                    BarChart noItems = new ItemsBarChart(brands);
                    noItems.setSize(800, 400);
                    noItems.setLocationRelativeTo(null);
                    noItems.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    noItems.setVisible(true);
                });
            }

            if (favouritesBarChartCheckbox.isSelected()) {
                SwingUtilities.invokeLater(() -> {
                    BarChart noFavourites = new FavouritesBarChart(brands);
                    noFavourites.setSize(800, 400);
                    noFavourites.setLocationRelativeTo(null);
                    noFavourites.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    noFavourites.setVisible(true);
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MVCFrame());
    }
}
