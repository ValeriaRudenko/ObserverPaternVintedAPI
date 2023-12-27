package es.ull.patrones.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BrandJSONParser {
    private List<Brand> brandList;

    public BrandJSONParser(String data, int noOfFavouritesValue) {
        doParsing(data, noOfFavouritesValue);
    }

    public void doParsing(String data, int noOfFavouritesValue) {
        JSONArray brandsArray = new JSONArray(data);
        brandList = new ArrayList<>();
        // Assuming each item in the array represents a brand
        for (int i = 0; i < brandsArray.length(); i++) {
            JSONObject brandJson = brandsArray.getJSONObject(i);
            System.out.println(brandJson);
            int favourites = brandJson.getInt("favourites");

            // Extract product details from the JSON and create corresponding objects
            if (favourites >= noOfFavouritesValue) {
                String title = brandJson.getString("title");
                String slug = brandJson.getString("slug");
                int favouriteCount = brandJson.getInt("favourite_count");
                int itemCount = brandJson.getInt("item_count");
                boolean isVisibleInListings = brandJson.getBoolean("is_visible_in_listings");
                boolean requiresAutenticityCheck = brandJson.getBoolean("requires_authenticity_check");
                boolean isLuxury = brandJson.getBoolean("is_luxury");
                Brand brand = new Brand(title, slug, favouriteCount, itemCount, isVisibleInListings, requiresAutenticityCheck, isLuxury);
                brandList.add(brand);
            }
        }
    }

    public List<Brand> getBrandList() {
        return brandList;
    }
}
