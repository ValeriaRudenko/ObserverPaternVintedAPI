import es.ull.patrones.app.App;
import es.ull.patrones.model.Brand;
import es.ull.patrones.model.BrandJSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    App app = new App();
    app.run();

  }
}


