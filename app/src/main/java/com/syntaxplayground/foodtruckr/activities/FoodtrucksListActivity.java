package com.syntaxplayground.foodtruckr.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.IntProperty;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.syntaxplayground.foodtruckr.R;
import com.syntaxplayground.foodtruckr.adapter.FoodtruckAdapter;
import com.syntaxplayground.foodtruckr.constants.Constants;
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
    private static FoodtrucksListActivity foodtrucksListActivity;
    private FloatingActionButton addFoodtruckBtn;
    public static final String EXTRA_ITEM_FOODTRUCK = "FOODTRUCK";
    SharedPreferences prefs;

    public static FoodtrucksListActivity getFoodtrucksListActivity() {
        return foodtrucksListActivity;
    }

    public static void setFoodtrucksListActivity(FoodtrucksListActivity foodtrucksListActivity) {
        FoodtrucksListActivity.foodtrucksListActivity = foodtrucksListActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodtrucks_list);

        foodtrucksListActivity.setFoodtrucksListActivity(this);

        addFoodtruckBtn = (FloatingActionButton) findViewById(R.id.addFoodtruckFAB);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        addFoodtruckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAddFoodtruck();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        TrucksDownloaded listener = new TrucksDownloaded() {
            @Override
            public void success(Boolean success) {
                if (success) {
                    setUpRecycler();
                }
            }
        };

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

    public void loadFoodtruckDetailActivity(Foodtruck foodtruck) {
        Intent intent = new Intent(FoodtrucksListActivity.this, FoodtruckDetailActivity.class);
        intent.putExtra(FoodtrucksListActivity.EXTRA_ITEM_FOODTRUCK, foodtruck);
        startActivity(intent);
    }

    public void loadAddFoodtruck() {
        if (prefs.getBoolean(Constants.IS_LOGGED_IN, false)) {
            Intent intent = new Intent(FoodtrucksListActivity.this, AddFoodtruckActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(FoodtrucksListActivity.this, LoginActivity.class);
            Toast.makeText(getBaseContext(), "Please log in to add a food truck", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }
}
