package com.syntaxplayground.foodtruckr.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.syntaxplayground.foodtruckr.R;
import com.syntaxplayground.foodtruckr.constants.Constants;
import com.syntaxplayground.foodtruckr.model.Foodtruck;

public class FoodtruckDetailActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Foodtruck foodtruck;
    private TextView foodtruckName;
    private TextView foodType;
    private TextView avgCost;
    private Button addReviewBtn;
    private Button viewReviewsBtn;
    private Button modifyFoodtruckBtn;
    SharedPreferences prefs;

    public static final String EXTRA_ITEM_FOODTRUCK = "FOODTRUCK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);

        foodtruckName = (TextView) findViewById(R.id.detail_truck_name);
        foodType = (TextView) findViewById(R.id.detail_food_type);
        avgCost = (TextView) findViewById(R.id.detail_food_cost);

        addReviewBtn = (Button) findViewById(R.id.add_review_btn);
        viewReviewsBtn = (Button) findViewById(R.id.view_reviews_btn);
        modifyFoodtruckBtn = (Button) findViewById(R.id.modify_foodtruck_btn);


        foodtruck = getIntent().getParcelableExtra(FoodtrucksListActivity.EXTRA_ITEM_FOODTRUCK);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        updateUI();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        viewReviewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFoodtruckReviewsActivity(foodtruck);
            }
        });

        addReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAddReview();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng foodtruckLocation = new LatLng(foodtruck.getLatitude(), foodtruck.getLongitude());
        mMap.addMarker(new MarkerOptions().position(foodtruckLocation).title(foodtruck.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(foodtruckLocation, 14));
        setupMap();
    }

    private void setupMap() {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setTrafficEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    private void updateUI() {
        foodtruckName.setText(foodtruck.getName());
        foodType.setText(foodtruck.getFoodType());
        avgCost.setText("$" + Double.toString(foodtruck.getAvgCost()));
    }

    public void loadFoodtruckReviewsActivity(Foodtruck truck) {
        Intent intent = new Intent(FoodtruckDetailActivity.this, FoodtruckReviewsActivity.class);
        intent.putExtra(FoodtruckDetailActivity.EXTRA_ITEM_FOODTRUCK, truck);
        startActivity(intent);
    }

    public void loadAddReview() {
        if (prefs.getBoolean(Constants.IS_LOGGED_IN, false)) {
            Intent intent = new Intent(FoodtruckDetailActivity.this, AddReviewActivity.class);
            intent.putExtra(FoodtruckDetailActivity.EXTRA_ITEM_FOODTRUCK, foodtruck);
            startActivity(intent);
        } else {
            Intent intent = new Intent(FoodtruckDetailActivity.this, LoginActivity.class);
            Toast.makeText(getBaseContext(), "Please login to leave a review", Toast.LENGTH_SHORT).show();
            startActivity(intent);

        }
    }
}
