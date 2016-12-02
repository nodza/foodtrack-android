package com.syntaxplayground.foodtruckr.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.syntaxplayground.foodtruckr.activities.FoodtrucksListActivity;
import com.syntaxplayground.foodtruckr.constants.Constants;
import com.syntaxplayground.foodtruckr.model.Foodtruck;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nodza on 12/1/16.
 */

public class DataService {

    private static DataService instance = new DataService();

    public static DataService getInstance() {
        return instance;
    }

    private DataService() {

    }

    // Request all foodtrucks
    public ArrayList<Foodtruck> downloadAllFoodtrucks(Context context, final FoodtrucksListActivity.TrucksDownloaded listener) {

        String url = Constants.GET_FOODTRUCKS_URL;
        final ArrayList<Foodtruck> foodtruckList = new ArrayList<>();

        final JsonArrayRequest getTrucks = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println(response.toString());

                try {
                    JSONArray foodtrucks = response;
                    for (int x = 0; x < foodtrucks.length(); x++) {
                        JSONObject foodtruck = foodtrucks.getJSONObject(x);

                        String id = foodtruck.getString("_id");
                        String name = foodtruck.getString("name");
                        String foodType = foodtruck.getString("foodType");
                        Double avgCost = foodtruck.getDouble("avgCost");

                        JSONObject geometry = foodtruck.getJSONObject("geometry");
                        JSONObject coordinates = geometry.getJSONObject("coordinates");

                        Double latitude = coordinates.getDouble("lat");
                        Double longitude = coordinates.getDouble("lng");

                        Foodtruck newFoodtruck = new Foodtruck(longitude, id, name, foodType, avgCost, latitude);
                        foodtruckList.add(newFoodtruck);

                    }
                } catch (JSONException e) {
                    Log.v("JSON", "EXC" + e.getLocalizedMessage());
                }

                listener.success(true);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("API", "Err" + error.getLocalizedMessage());
            }
        });

        Volley.newRequestQueue(context).add(getTrucks);
        return foodtruckList;
    }
}
