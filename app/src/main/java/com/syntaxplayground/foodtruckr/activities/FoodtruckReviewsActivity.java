package com.syntaxplayground.foodtruckr.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.syntaxplayground.foodtruckr.R;
import com.syntaxplayground.foodtruckr.data.DataService;
import com.syntaxplayground.foodtruckr.model.Foodtruck;
import com.syntaxplayground.foodtruckr.model.FoodtruckReview;

import java.util.ArrayList;

public class FoodtruckReviewsActivity extends AppCompatActivity {

    private Foodtruck foodtruck;
    private ArrayList<FoodtruckReview> foodtruckReviews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodtruck_reviews);

        foodtruck = getIntent().getParcelableExtra(FoodtrucksListActivity.EXTRA_ITEM_FOODTRUCK);
        System.out.println(foodtruck.getName());

        ReviewInterface listener = new ReviewInterface() {
            @Override
            public void success(Boolean success) {
                System.out.println(foodtruckReviews);
            }
        };

        foodtruckReviews = DataService.getInstance().downloadAllReviews(this, foodtruck, listener);

    }

    public interface ReviewInterface {
        void success(Boolean success);
    }
}
