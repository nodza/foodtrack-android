package com.syntaxplayground.foodtruckr.model;

/**
 * Created by nodza on 12/2/16.
 */

public class FoodtruckReview {

    private String id = "";
    private String title = "";
    private String text = "";

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public FoodtruckReview(String id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }
}
