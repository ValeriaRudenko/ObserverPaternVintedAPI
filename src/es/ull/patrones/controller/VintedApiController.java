package es.ull.patrones.controller;

import es.ull.patrones.model.VintedApiModel;
import es.ull.patrones.view.Observer;

public class VintedApiController {
  private VintedApiModel model;

  public VintedApiController(VintedApiModel model) {
    this.model = model;
  }

  public void addObserver(Observer observer) {
    model.addObserver(observer);
  }

  public void fetchData(int page, String object, int priceMin, int priceMax, int favourites) {
    model.fetchData(page, object, priceMin, priceMax, favourites);
  }
}
