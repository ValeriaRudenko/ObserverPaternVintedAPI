package es.ull.patrones.gui;

import es.ull.patrones.controller.VintedApiController;
import es.ull.patrones.model.VintedApiModel;
import es.ull.patrones.view.Observer;
import es.ull.patrones.view.PrintObserver;

import javax.swing.*;
import java.awt.*;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

public class MainFrame extends JFrame {
    // Panels
    private JPanel filtersPanel; // Panel to place the filter fields
    private JPanel buttonsPanel; // Panel to place the buttons
    private JPanel logoPanel;

    // Labels
    private JLabel noOfFavouritesLabel;
    private JLabel keywordLabel;
    private JLabel countryLabel;
    private JLabel noOfpageLabel;
    private JLabel minPriceLabel;
    private JLabel maxPriceLabel;
    private JLabel message;
    private JLabel vintedLogo;

    // Input fields
    private JTextField keyword;
    private JComboBox<String> country;
    private JComboBox<Integer> noOfpage;
    private JTextField minPrice;
    private JTextField maxPrice;
    private JComboBox<Integer> noOfFavourites;

    // Buttons
    private JButton searchButton;

    // Constructor method
    public MainFrame() {
        // Configuration of the main frame
        this.setSize(400, 300);
        this.getContentPane().setLayout(new BorderLayout());
        this.setBackground(new Color(190, 245, 255));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("DAP - Practise 7 - Observer Pattern");

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
        message = new JLabel("Please enter the search criteria:");
        keywordLabel = new JLabel("Keyword");
        noOfpageLabel = new JLabel("No. of page");
        countryLabel = new JLabel("Country");
        minPriceLabel = new JLabel("Min. price (€)");
        maxPriceLabel = new JLabel("Max. price (€)");
        noOfFavouritesLabel = new JLabel("Min. no. of favourites");

        // Combo boxes values
        Integer[] pages = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
                26, 27, 28, 29, 30, 31, 32}; // You can only select 1-32 page
        String[] countryOptions = {"Spain", "Belgium", "France", "Luxembourg", "Netherlands", "Lithuania", "Germany",
                "Austria", "Italy", "United Kingdom", "Portugal", "United States", "Czech Republic", "Slovakia",
                "Poland", "Sweden", "Romania", "Hungary", "Denmark"};
        Integer[] favourites = {0, 5, 10, 15, 20, 25, 30, 40, 50, 100};

        // Search text fields
        keyword = new JTextField(20);
        noOfpage = new JComboBox<>(pages);
        country = new JComboBox<>(countryOptions);
        minPrice = new JTextField(20);
        maxPrice = new JTextField(20);
        noOfFavourites = new JComboBox<Integer>(favourites);

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
        filtersPanel.add(message);
        filtersPanel.add(new JLabel());
        filtersPanel.add(keywordLabel);
        filtersPanel.add(keyword);
        filtersPanel.add(noOfpageLabel);
        filtersPanel.add(noOfpage);
        filtersPanel.add(countryLabel);
        filtersPanel.add(country);
        filtersPanel.add(minPriceLabel);
        filtersPanel.add(minPrice);
        filtersPanel.add(maxPriceLabel);
        filtersPanel.add(maxPrice);
        filtersPanel.add(noOfFavouritesLabel);
        filtersPanel.add(noOfFavourites);
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
    }

    // Method to perform search with parameters
    private String performSearch(String keyword, int noOfPage, String country, int minPrice, int maxPrice, int noOfFavourites) {
        return String.format("Búsqueda con:\nKeyword: %s\nNo. of Page: %d\nCountry: %s\nMin. Price: %s\nMax. Price: %s\nNo. of Favourites: %d",
                keyword, noOfPage, country, minPrice, maxPrice, noOfFavourites);
    }
}
