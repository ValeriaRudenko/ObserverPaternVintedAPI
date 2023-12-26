package es.ull.patrones.view;

import es.ull.patrones.model.Product;
import java.util.List;

public interface Observer {
  void update(List<Product> productList);

    void removepreviousFrame();
}
