package es.ull.patrones.observer;

import es.ull.patrones.api.Product;
import java.util.List;

public interface Observer {
  void update(List<Product> productList);

    void removepreviousFrame();
}
