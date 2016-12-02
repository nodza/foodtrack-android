package com.syntaxplayground.foodtruckr.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.syntaxplayground.foodtruckr.R;
import com.syntaxplayground.foodtruckr.adapter.FoodtruckAdapter;
import com.syntaxplayground.foodtruckr.model.Foodtruck;
import com.syntaxplayground.foodtruckr.view.ItemDecorator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodtrucksListActivity extends AppCompatActivity {

    // Variables
    private FoodtruckAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodtrucks_list);

        String url = "http://10.0.2.2:3005/api/v1/foodtruck";
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

                System.out.println("My favorite foodtruck is " + foodtruckList.get(1).getName());

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_foodtruck);
                recyclerView.setHasFixedSize(true);
                adapter = new FoodtruckAdapter(foodtruckList);
                recyclerView.setAdapter(adapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.addItemDecoration(new ItemDecorator(0, 0, 0, 10));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("API", "Err" + error.getLocalizedMessage());
            }
        });

        Volley.newRequestQueue(this).add(getTrucks);
    }
}
