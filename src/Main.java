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
  public static String stream(URL url) {
    try (InputStream input = url.openStream()) {
      InputStreamReader isr = new InputStreamReader(input);
      BufferedReader reader = new BufferedReader(isr);
      StringBuilder json = new StringBuilder();
      int c;
      while ((c = reader.read()) != -1) {
        json.append((char) c);
      }
      return json.toString();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) throws MalformedURLException {
    App app = new App();
    app.run();

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

      // Now you can use jsonData for parsing
      BrandJSONParser parserTest = new BrandJSONParser(jsonData);
      List<Brand> brands = parserTest.getBrandList();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}


