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
import es.ull.patrones.view.Observer;

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
        // maxNumberOfFavouritesLabel;
        minItemCountLabel = new JLabel("No. of items (min - max): ");
        // maxItemCountLabel;

        // Search text fields
        minNumberOfFavourites = new JTextField(10);
        maxNumberOfFavourites = new JTextField(10);
        minNumberOfItems = new JTextField(10);
        maxNumberOfItems = new JTextField(10);

        // Search button
        // When pressed, the program will use the Vinted API
        searchButton = new JButton("Search items");
        // An action listener should be defined here
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clic event
                onSearchButtonClick();
            }
        });
        // Add stuff to the panels and main frame
        // Filters panel
        filtersPanel.add(minNoOfFavouritesLabel);
        filtersPanel.add(new JLabel());
        filtersPanel.add(minNumberOfFavourites);
        filtersPanel.add(maxNumberOfFavourites);
        filtersPanel.add(minItemCountLabel);
        filtersPanel.add(minNumberOfItems);
        filtersPanel.add(maxNumberOfItems);
        // Buttons panel
        buttonsPanel.add(searchButton);
        // Main frame
        this.add(logoPanel, BorderLayout.NORTH);
        this.add(filtersPanel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);

        // We assure the main frame is visible
        this.setVisible(true);
    }

    // When the search button gets pressed...
    private void onSearchButtonClick() {
        try {
            // We extract the data from the new JSON
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

            // Check that the input data is correct
            // An exception will be thrown when the min value is greater than max value
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

            // It will be acceptable if the fields of min and max are empty
            // That would make the min be 0, and the max be 1000000000
            int minFavourites = 0;
            int maxFavourites;
            int minItems = 0;
            int maxItems;

            // Min numbers
            if (!(minNumberOfFavourites.getText().isEmpty())) {
                minFavourites = Integer.parseInt(minNumberOfFavourites.getText());
            }

            if (!(minNumberOfItems.getText().isEmpty())) {
                minItems = Integer.parseInt(minNumberOfItems.getText());
            }

            // Max numbers
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

            // We parse the JSON file
            BrandJSONParser parserTest = new BrandJSONParser(jsonData, minFavourites, maxFavourites, minItems, maxItems);
            List<Brand> brands = parserTest.getBrandList();

            /**
             * TODO: Add some way for the user to choose the charts that will be shown in the scoreboard
             * TODO: Use the factories!
             * TODO: Use the Scoreboard class
            */

            // We show the charts
            // This is just a test to check the charts work properly
            SwingUtilities.invokeLater(() -> {
                PieChart visible = new VisibleInListingsPieChart(brands);
                visible.setSize(800, 400);
                visible.setLocationRelativeTo(null);
                visible.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                visible.setVisible(true);
            });

            SwingUtilities.invokeLater(() -> {
                PieChart luxury = new LuxuryPieChart(brands);
                luxury.setSize(800, 400);
                luxury.setLocationRelativeTo(null);
                luxury.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                luxury.setVisible(true);
            });

            SwingUtilities.invokeLater(() -> {
                PieChart autenticity = new AutenticityCheckPieChart(brands);
                autenticity.setSize(800, 400);
                autenticity.setLocationRelativeTo(null);
                autenticity.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                autenticity.setVisible(true);
            });

            SwingUtilities.invokeLater(() -> {
                BarChart noItems = new ItemsBarChart(brands);
                noItems.setSize(800, 400);
                noItems.setLocationRelativeTo(null);
                noItems.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                noItems.setVisible(true);
            });

            SwingUtilities.invokeLater(() -> {
                BarChart noFavourites = new FavouritesBarChart(brands);
                noFavourites.setSize(800, 400);
                noFavourites.setLocationRelativeTo(null);
                noFavourites.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                noFavourites.setVisible(true);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
