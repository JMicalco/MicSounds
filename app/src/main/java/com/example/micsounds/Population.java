package com.example.micsounds;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Population implements Serializable {

    // Model Class
    String name;
    int price;
    String imageUrl;
    float rating;
    int amount;
    // Constructors
    public Population() {

    }

    public Population(String name, int price, String imageUrl, float rating) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.amount = 1;
    }
    public Population(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.amount = 1;
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public void setRating(float rating) { this.rating = rating;}

    public float getRating() {
        return this.rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("price", price);
        result.put("image", imageUrl);
        result.put("rating", rating);
        result.put("amount", amount);

        return result;
    }

}
