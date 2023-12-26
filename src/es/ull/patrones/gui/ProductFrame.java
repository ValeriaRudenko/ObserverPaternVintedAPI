package es.ull.patrones.gui;

import es.ull.patrones.api.Product;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ProductFrame extends JFrame {
    private JPanel panel;

    public ProductFrame(List<Product> productList) {
        initializeFrame();
        displayProducts(productList);
    }

    private void initializeFrame() {
        this.setTitle("Results");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 700);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(190, 245, 255));

        JScrollPane scrollPane = new JScrollPane(panel);
        this.add(scrollPane);
    }

    private void displayProducts(List<Product> productList) {
        panel.removeAll();  // Clear the panel before showing new products

        if (productList.isEmpty()) {
            panel.add(new JLabel("No results were found."));
        } else {
            for (Product product : productList) {
                // Construct labels with details of the product
                JLabel imageLabel = createAsyncImageLabel(product.getImageUrl());
                JLabel titleLabel = createCopyableLabel("Title: " + product.getTitle());
                JLabel urlLabel = createCopyableLabel("URL: " + product.getUrl());
                JLabel favouritesLabel = createCopyableLabel("Favourites: " + product.getFavourites());
                JLabel totalAmountLabel = createCopyableLabel("Price (including taxes): " + product.getTotalAmount() + " " + product.getCurrency());
                JLabel separator = new JLabel("-------------------------");

                // Add labels to the panel
                panel.add(imageLabel);
                panel.add(titleLabel);
                panel.add(urlLabel);
                panel.add(favouritesLabel);
                panel.add(totalAmountLabel);
                panel.add(separator);
            }
        }

        this.revalidate();
        this.repaint();
        this.setVisible(true);
    }

    private JLabel createAsyncImageLabel(String imageUrl) {
        JLabel imageLabel = new JLabel();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            SwingWorker<Void, ImageIcon> worker = new SwingWorker<Void, ImageIcon>() {
                @Override
                protected Void doInBackground() {
                    try {
                        URL url = new URL(imageUrl);
                        BufferedImage image = ImageIO.read(url);

                        if (image != null) {
                            Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                            ImageIcon imageIcon = new ImageIcon(scaledImage);
                            publish(imageIcon);
                        } else {
                            System.err.println("Failed to load image from URL: " + imageUrl);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void process(List<ImageIcon> chunks) {
                    if (!chunks.isEmpty()) {
                        imageLabel.setIcon(chunks.get(chunks.size() - 1));
                    }
                }
            };

            worker.execute();
        }
        return imageLabel;
    }

    private JLabel createCopyableLabel(String text) {
        JLabel label = new JLabel(text);
        label.setOpaque(true);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                StringSelection stringSelection = new StringSelection(label.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                JOptionPane.showMessageDialog(null, "Copied to clipboard!");
            }
        });
        return label;
    }

    public void closeFrame() {
        this.dispose();
    }
}
