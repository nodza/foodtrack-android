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
import com.syntaxplayground.foodtruckr.model.Foodtruck;

public class AddReviewActivity extends AppCompatActivity {

    private EditText reviewTitle;
    private EditText reviewText;
    private Button addReviewBtn;
    private Button cancelBtn;
    private Foodtruck truck;
    private String authToken;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        reviewTitle = (EditText) findViewById(R.id.review_title);
        reviewText = (EditText) findViewById(R.id.review_text);
        addReviewBtn = (Button) findViewById(R.id.add_review_btn);
        cancelBtn = (Button) findViewById(R.id.cancel_review_btn);
        truck = getIntent().getParcelableExtra(FoodtruckDetailActivity.EXTRA_ITEM_FOODTRUCK);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        authToken = prefs.getString(Constants.AUTH_TOKEN, "Token does not exist");

        final AddReviewInterface listener = new AddReviewInterface() {
            @Override
            public void success(Boolean success) {
                finish();
            }
        };

        addReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DataService
                final String title = reviewTitle.getText().toString();
                final String text = reviewText.getText().toString();

                DataService.getInstance().addReview(title, text, truck, getBaseContext(), listener, authToken);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public interface AddReviewInterface {
        void success(Boolean success);
    }
}
