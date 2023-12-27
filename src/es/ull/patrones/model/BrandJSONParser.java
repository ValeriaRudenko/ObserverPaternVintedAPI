package es.ull.patrones.model;

import es.ull.patrones.strategy.JSONParserStrategy;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BrandJSONParser implements JSONParserStrategy {
    private List<Brand> brandList;

    public BrandJSONParser(String data, Object... parameters) {
        parse(data, parameters);
    }

    public void parse(String data, Object... parameters) {
        // Check if the parameters provided are correct
        if (parameters.length < 4 || !(parameters[0] instanceof Integer) || !(parameters[1] instanceof Integer) || !(parameters[2] instanceof Integer)|| !(parameters[3] instanceof Integer)) {
            throw new IllegalArgumentException("Invalid parameters");
        }
        int minNumberOfFavourites = (int) parameters[0];
        int maxNumberOfFavourites = (int) parameters[1];
        int minNumberOfItems = (int) parameters[2];
        int maxNumberOfItems = (int) parameters[3];

        JSONArray brandsArray = new JSONArray(data);
        brandList = new ArrayList<>();
        // Assuming each item in the array represents a brand
        for (int i = 0; i < brandsArray.length(); i++) {
            JSONObject brandJson = brandsArray.getJSONObject(i);
            int favourites = brandJson.getInt("favourite_count");
            int items = brandJson.getInt("item_count");

            // Check if the conditions of favourites and items are fulfilled
            if (favourites >= minNumberOfFavourites && favourites <= maxNumberOfFavourites) {
                if (items >= minNumberOfItems && items <= maxNumberOfItems) {
                    // Extract product details from the JSON and create corresponding brands
                    String title = brandJson.getString("title");
                    String slug = brandJson.getString("slug");
                    int favouriteCount = brandJson.getInt("favourite_count");
                    int itemCount = brandJson.getInt("item_count");
                    boolean isVisibleInListings = brandJson.getBoolean("is_visible_in_listings");
                    boolean requiresAutenticityCheck = brandJson.getBoolean("requires_authenticity_check");
                    boolean isLuxury = brandJson.getBoolean("is_luxury");
                    Brand brand = new Brand(title, slug, favouriteCount, itemCount, isVisibleInListings, requiresAutenticityCheck, isLuxury);
                    brandList.add(brand);
                    System.out.println(brand.getTitle());
                }
            }
        }
    }

    public List<Brand> getBrandList() {
        return brandList;
    }
}
