package com.syntaxplayground.foodtruckr.model;

/**
 * Created by nodza on 11/30/16.
 */

public class Foodtruck {

    private String id = "";
    private String name = "";
    private String foodType = "";
    private Double avgCost = 0.0;
    private Double latitude = 0.0;
    private Double longitude = 0.0;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFoodType() {
        return foodType;
    }

    public Double getAvgCost() {
        return avgCost;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Foodtruck(Double longitude, String id, String name, String foodType, Double avgCost, Double latitude) {
        this.longitude = longitude;
        this.id = id;
        this.name = name;
        this.foodType = foodType;
        this.avgCost = avgCost;
        this.latitude = latitude;
    }
}
