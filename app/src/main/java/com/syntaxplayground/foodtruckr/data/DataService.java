package com.syntaxplayground.foodtruckr.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.syntaxplayground.foodtruckr.activities.AddReviewActivity;
import com.syntaxplayground.foodtruckr.activities.FoodtruckReviewsActivity;
import com.syntaxplayground.foodtruckr.activities.FoodtrucksListActivity;
import com.syntaxplayground.foodtruckr.constants.Constants;
import com.syntaxplayground.foodtruckr.model.Foodtruck;
import com.syntaxplayground.foodtruckr.model.FoodtruckReview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    // Request all foodtrucks reviews
    public ArrayList<FoodtruckReview> downloadAllReviews(Context context, Foodtruck foodtruck, final FoodtruckReviewsActivity.ReviewInterface listener) {

        String url = Constants.GET_FOODTRUCK_REVIEWS + foodtruck.getId();
        final ArrayList<FoodtruckReview> foodtruckReviewList = new ArrayList<>();

        final JsonArrayRequest getReviews = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println(response.toString());

                try {
                    JSONArray foodtruckReviews = response;
                    for (int x = 0; x < foodtruckReviews.length(); x++) {
                        JSONObject foodtruckReview = foodtruckReviews.getJSONObject(x);

                        String id = foodtruckReview.getString("_id");
                        String title = foodtruckReview.getString("title");
                        String text = foodtruckReview.getString("text");

                        FoodtruckReview newFoodtruckReview = new FoodtruckReview(id, title, text);
                        foodtruckReviewList.add(newFoodtruckReview);

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

        Volley.newRequestQueue(context).add(getReviews);
        return foodtruckReviewList;
    }

    // Add review POST
    public void addReview(String title, String text, Foodtruck foodtruck, Context context, final AddReviewActivity.AddReviewInterface listener, String authToken) {

        try {

            String url = Constants.ADD_REVIEW + foodtruck.getId();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("title", title);
            jsonBody.put("text", text);
            jsonBody.put("foodtruck", foodtruck.getId());

            final String mRequestBody = jsonBody.toString();
            final String bearer = "Bearer " + authToken;

            final JsonObjectRequest reviewRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String message = response.getString("message");
                        Log.i("JSON Message", message);

                    } catch (JSONException e) {
                        Log.v("JSON Message", "EXC: " + e.getLocalizedMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding", mRequestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    if (response.statusCode == 200) {
                        listener.success(true);
                    }
                    return super.parseNetworkResponse(response);
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", bearer);

                    return headers;
                }
            };

            Volley.newRequestQueue(context).add(reviewRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
