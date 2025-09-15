package com.rhix.mycoffee;

import com.google.gson.annotations.SerializedName;

public class CoffeeBean {
    @SerializedName("coffee_name")
    private String name;

    @SerializedName("coffee_description")
    private String description;

    @SerializedName("coffee_image_url")
    private String imageUrl;

    @SerializedName("coffee_origin")
    private String origin;

    @SerializedName("coffee_roast")
    private String roast;

    public CoffeeBean(String name, String description, String imageUrl, String origin, String roast) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.origin = origin;
        this.roast = roast;
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public String getOrigin() { return origin; }
    public String getRoast() { return roast; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setOrigin(String origin) { this.origin = origin; }
    public void setRoast(String roast) { this.roast = roast; }
}
