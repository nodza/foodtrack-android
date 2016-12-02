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
import com.syntaxplayground.foodtruckr.data.DataService;
import com.syntaxplayground.foodtruckr.model.Foodtruck;
import com.syntaxplayground.foodtruckr.view.ItemDecorator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodtrucksListActivity extends AppCompatActivity {

    // Variables
    private FoodtruckAdapter adapter;
    private ArrayList<Foodtruck> foodtrucks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodtrucks_list);

        TrucksDownloaded listener = new TrucksDownloaded() {
            @Override
            public void success(Boolean success) {
                if (success) {
                    setUpRecycler();
                }
            }
        };

        setUpRecycler();
        foodtrucks = DataService.getInstance().downloadAllFoodtrucks(this, listener);
    }

    private void setUpRecycler() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_foodtruck);
        recyclerView.setHasFixedSize(true);
        adapter = new FoodtruckAdapter(foodtrucks);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new ItemDecorator(0, 0, 0, 10));
    }

    public interface TrucksDownloaded {
        void success(Boolean success);
    }
}
