package es.ull.patrones.app;

import es.ull.patrones.gui.MainFrame;
import es.ull.patrones.gui.MVCFrame;

public class App {
  public App() {}

  public void run() {
    MainFrame mainFrame = new MainFrame();
    MVCFrame mvcFrame = new MVCFrame();
  }
}
