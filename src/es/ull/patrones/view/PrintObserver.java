package es.ull.patrones.view;

import es.ull.patrones.model.Product;
import es.ull.patrones.gui.ProductFrame;
import javax.swing.*;
import java.util.List;

public class PrintObserver implements Observer {
  ProductFrame productFrame;
  @Override
  public void update(List<Product> productList) {
    SwingUtilities.invokeLater(() -> {
      productFrame = new ProductFrame(productList);
    });
  }
  public void removepreviousFrame(){
    if (productFrame != null) {
      productFrame.dispose();
    }
  }
}