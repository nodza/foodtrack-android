package com.syntaxplayground.foodtruckr.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.syntaxplayground.foodtruckr.R;
import com.syntaxplayground.foodtruckr.adapter.FoodtruckAdapter;
import com.syntaxplayground.foodtruckr.adapter.FoodtruckReviewAdapter;
import com.syntaxplayground.foodtruckr.data.DataService;
import com.syntaxplayground.foodtruckr.model.Foodtruck;
import com.syntaxplayground.foodtruckr.model.FoodtruckReview;
import com.syntaxplayground.foodtruckr.view.ItemDecorator;

import java.util.ArrayList;

public class FoodtruckReviewsActivity extends AppCompatActivity {

    private Foodtruck foodtruck;
    private ArrayList<FoodtruckReview> foodtruckReviews = new ArrayList<>();
    private FoodtruckReviewAdapter foodtruckReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodtruck_reviews);

        foodtruck = getIntent().getParcelableExtra(FoodtrucksListActivity.EXTRA_ITEM_FOODTRUCK);
        System.out.println(foodtruck.getName());

        ReviewInterface listener = new ReviewInterface() {
            @Override
            public void success(Boolean success) {
                if (success) {
                    setupRecycler();
                    if (foodtruckReviews.size() == 0) {
                        Toast.makeText(getBaseContext(), "No reviews for this food truck", Toast.LENGTH_LONG).show();
                    }
                }
            }
        };

        foodtruckReviews = DataService.getInstance().downloadAllReviews(this, foodtruck, listener);

    }

    public interface ReviewInterface {
        void success(Boolean success);
    }

    private void setupRecycler() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_reviews);
        recyclerView.setHasFixedSize(true);
        foodtruckReviewAdapter = new FoodtruckReviewAdapter(foodtruckReviews);
        recyclerView.setAdapter(foodtruckReviewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new ItemDecorator(0, 0, 0, 10));
    }
}
