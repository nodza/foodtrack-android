package com.syntaxplayground.foodtruckr.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.syntaxplayground.foodtruckr.R;
import com.syntaxplayground.foodtruckr.constants.Constants;
import com.syntaxplayground.foodtruckr.data.DataService;

public class AddFoodtruckActivity extends AppCompatActivity {

    private EditText foodtruckName;
    private EditText foodType;
    private EditText avgCost;
    private EditText latitude;
    private EditText longitude;

    private Button addBtn;
    private Button cancelBtn;
    String authToken;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_foodtruck);

        foodtruckName = (EditText) findViewById(R.id.new_truck_name);
        foodType = (EditText) findViewById(R.id.new_truck_food_type);
        avgCost = (EditText) findViewById(R.id.new_avg_cost);
        latitude = (EditText) findViewById(R.id.new_latitude);
        longitude = (EditText) findViewById(R.id.new_long);

        addBtn = (Button) findViewById(R.id.add_truck);
        cancelBtn = (Button) findViewById(R.id.cancel_truck);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        authToken = prefs.getString(Constants.AUTH_TOKEN, "Token does not exist");

        final AddFoodtruckInterface listener = new AddFoodtruckInterface() {
            @Override
            public void success(Boolean success) {
                finish();
            }
        };

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = foodtruckName.getText().toString();
                final String type = foodType.getText().toString();
                final Double cost = Double.parseDouble(avgCost.getText().toString());
                final Double lat = Double.parseDouble(latitude.getText().toString());
                final Double lng = Double.parseDouble(longitude.getText().toString());

                DataService.getInstance().addFoodtruck(name, type, cost, lat, lng, getBaseContext(), listener, authToken);

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public interface AddFoodtruckInterface {
        void success(Boolean success);
    }
}
