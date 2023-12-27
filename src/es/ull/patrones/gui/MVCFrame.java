package es.ull.patrones.gui;

import es.ull.patrones.controller.VintedApiController;
import es.ull.patrones.factory.products.AutenticityCheckPieChart;
import es.ull.patrones.factory.products.LuxuryPieChart;
import es.ull.patrones.factory.products.PieChart;
import es.ull.patrones.factory.products.VisibleInListingsPieChart;
import es.ull.patrones.model.Brand;
import es.ull.patrones.model.BrandJSONParser;
import es.ull.patrones.model.VintedApiModel;
import es.ull.patrones.view.Observer;
import es.ull.patrones.view.PrintObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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
    // Method to manage when the button is pressed
    Observer printObserver;
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

            // Parse JSON file
            // Check that the data is correct
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
            int maxFavourites = 0;
            int minItems = 0;
            int maxItems = 0;

            if (minNumberOfFavourites.getText().isEmpty()) {
                minFavourites = 0;
            } else {
                minFavourites = Integer.parseInt(minNumberOfFavourites.getText());
            }

            if (minNumberOfItems.getText().isEmpty()) {
                minItems = 0;
            } else {
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
                maxFavourites = Integer.parseInt(maxNumberOfItems.getText());
            }
            BrandJSONParser parserTest = new BrandJSONParser(jsonData, minFavourites, maxFavourites, minItems, maxItems);
            List<Brand> brands = parserTest.getBrandList();

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

        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
        VintedApiModel model = new VintedApiModel();
        VintedApiController vintedApiSubject = new VintedApiController(model);
        if(printObserver!=null)
            printObserver.removepreviousFrame();
        printObserver = new PrintObserver();
        // Input fields values
        String keywordValue = keyword.getText();
        int noOfPageValue = (int) noOfpage.getSelectedItem();
        String countryValue = (String) country.getSelectedItem();
        int minPriceValue = Integer.parseInt(minPrice.getText());
        int maxPriceValue = Integer.parseInt(maxPrice.getText());
        int noOfFavouritesValue = (int) noOfFavourites.getSelectedItem();
        if (minPriceValue > maxPriceValue) {
            JOptionPane.showMessageDialog(this,"ERROR DE PRECIO");
        }
        vintedApiSubject.addObserver(printObserver);
        vintedApiSubject.fetchData(noOfPageValue, keywordValue, minPriceValue, maxPriceValue, noOfFavouritesValue);
        // We search the items with the search criteria
        String results = performSearch(keywordValue, noOfPageValue, countryValue, minPriceValue, maxPriceValue, noOfFavouritesValue);
        */
    }

    // Method to perform search with parameters
    private String performSearch(String keyword, int noOfPage, String country, int minPrice, int maxPrice, int noOfFavourites) {
        return String.format("Búsqueda con:\nKeyword: %s\nNo. of Page: %d\nCountry: %s\nMin. Price: %s\nMax. Price: %s\nNo. of Favourites: %d",
                keyword, noOfPage, country, minPrice, maxPrice, noOfFavourites);
    }
}
