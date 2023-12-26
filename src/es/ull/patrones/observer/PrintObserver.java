package es.ull.patrones.observer;

import es.ull.patrones.api.Product;
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