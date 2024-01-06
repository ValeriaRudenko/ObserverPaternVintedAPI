package es.ull.patrones.gui;

import es.ull.patrones.factory.factories.*;
import es.ull.patrones.model.Brand;
import es.ull.patrones.observer.BrandObserver;
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
import java.util.Map;

public class MVCFrame extends JFrame implements BrandObserver {
    final private JPanel filtersPanel;
    final private JPanel buttonsPanel;
    final private JPanel logoPanel;

    final private JLabel minNoOfFavouritesLabel;
    final private JLabel minItemCountLabel;

    final private JTextField minNumberOfFavourites;
    final private JTextField maxNumberOfFavourites;
    final private JTextField minNumberOfItems;
    final private JTextField maxNumberOfItems;

    final private JButton searchButton;

    final private JCheckBox visibleInListingsCheckbox;
    final private JCheckBox luxuryCheckbox;
    final private JCheckBox authenticityCheckbox;
    final private JCheckBox itemsBarChartCheckbox;
    final private JCheckBox favouritesBarChartCheckbox;

    private JTextArea messageArea;

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
        messageArea = new JTextArea();
        messageArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

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
        JPanel messagePanel = new JPanel(new BorderLayout());


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
        messagePanel.add(new JLabel("Messages: "), BorderLayout.NORTH);
        messagePanel.add(scrollPane, BorderLayout.CENTER);

        buttonsPanel.add(searchButton);

        this.add(logoPanel, BorderLayout.NORTH);
        this.add(filtersPanel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.add(messagePanel, BorderLayout.EAST);


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
            //System.out.println("Nuevo Scoreboard Creado con Opciones: " + getUserOptions());
            scoreboard.registerObserver(this);
            scoreboard.setUserOptions(getUserOptions());

            // Create a map for checkboxes and their corresponding types
            Map<JCheckBox, String> checkboxTypeMap = Map.of(
                    visibleInListingsCheckbox, "visibleInListings",
                    luxuryCheckbox, "luxury",
                    authenticityCheckbox, "authenticity",
                    itemsBarChartCheckbox, "items",
                    favouritesBarChartCheckbox, "favourites"
            );

            // Iterate through the checkboxes and add charts
            for (Map.Entry<JCheckBox, String> entry : checkboxTypeMap.entrySet()) {
                JCheckBox checkbox = entry.getKey();
                String type = entry.getValue();

                if (checkbox.isSelected()) {
                    addChart(scoreboard, brands, type);
                }
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

    private void addChart(Scoreboard scoreboard, List<Brand> brands, String type) {
        ChartFactory factory;
        switch (type) {
            case "visibleInListings" -> factory = new PieChartFactory();
            case "luxury", "authenticity" -> factory = new PieChartFactory();
            case "items", "favourites" -> factory = new BarChartFactory();
            default -> throw new IllegalArgumentException("Invalid chart type: " + type);
        }

        JPanel chart = factory.createChart(brands, type);
        scoreboard.addChart(chart);
    }

    private String getUserOptions() {

        String options = "Favourites: " +
                minNumberOfFavourites.getText() +
                " - " +
                maxNumberOfFavourites.getText() +
                ", Items: " +
                minNumberOfItems.getText() +
                " - " +
                maxNumberOfItems.getText() +
                ", Visible: " +
                visibleInListingsCheckbox.isSelected() +
                ", Luxury: " +
                luxuryCheckbox.isSelected() +
                ", Authenticity: " +
                authenticityCheckbox.isSelected() +
                ", Items Chart: " +
                itemsBarChartCheckbox.isSelected() +
                ", Favourites Chart: " +
                favouritesBarChartCheckbox.isSelected();

        return options;
    }

    @Override
    public void updateOptions(String userOptions) {
        messageArea.append("User's options: " + userOptions + "\n");
        messageArea.setCaretPosition(messageArea.getDocument().getLength());
    }

}
