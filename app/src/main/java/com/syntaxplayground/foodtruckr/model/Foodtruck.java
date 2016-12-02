package com.syntaxplayground.foodtruckr.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nodza on 11/30/16.
 */

public class Foodtruck implements Parcelable {

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

    // Make parcel
    private Foodtruck(Parcel in) {
        id = in.readString();
        name = in.readString();
        foodType = in.readString();
        avgCost = in.readDouble();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(foodType);
        parcel.writeDouble(avgCost);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Foodtruck> CREATOR = new Parcelable.Creator<Foodtruck>() {
        public Foodtruck createFromParcel(Parcel in) {
            return new Foodtruck(in);
        }

        public Foodtruck[] newArray(int size) {
            return new Foodtruck[size];
        }
    };
}
