package es.ull.patrones.model;

public class Brand {
    // Attributes
    private String title;
    private String slug;
    private int favouriteCount;
    private int itemCount;
    private boolean isVisibleInListings;
    private boolean requiresAutenticityCheck;
    private boolean isLuxury;

    // Constructor
    public Brand(String title, String slug, int favouriteCount, int itemCount, boolean isVisibleInListings, boolean requiresAutenticityCheck, boolean isLuxury) {
        this.title = title;
        this.slug = slug;
        this.favouriteCount = favouriteCount;
        this.itemCount = itemCount;
        this.isVisibleInListings = isVisibleInListings;
        this.requiresAutenticityCheck = requiresAutenticityCheck;
        this.isLuxury = isLuxury;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getSlug() {
        return slug;
    }

    public int getFavouriteCount() {
        return favouriteCount;
    }

    public int getItemCount() {
        return itemCount;
    }

    public boolean getIsVisibleInListings() {
        return isVisibleInListings;
    }

    public boolean getRequiresAutenticityCheck() {
        return requiresAutenticityCheck;
    }

    public boolean getIsLuxury() {
        return isLuxury;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setFavouriteCount(int favouritesCount) {
        this.favouriteCount = favouritesCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public void setVisibleInListings(boolean visibleInListings) {
        this.isVisibleInListings = visibleInListings;
    }

    public void setRequiresAutenticityCheck(boolean requiresAutenticityCheck) {
        this.requiresAutenticityCheck = requiresAutenticityCheck;
    }

    public void setLuxury(boolean isLuxury) {
        this.isLuxury = isLuxury;
    }
}
