package com.syntaxplayground.foodtruckr.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.syntaxplayground.foodtruckr.R;
import com.syntaxplayground.foodtruckr.model.FoodtruckReview;

/**
 * Created by nodza on 12/5/16.
 */

public class FoodtruckReviewHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private TextView text;

    public FoodtruckReviewHolder(View itemView) {
        super(itemView);

        this.title = (TextView) itemView.findViewById(R.id.review_title);
        this.text = (TextView) itemView.findViewById(R.id.review_text);
    }

    public void updateUI(FoodtruckReview foodtruckReview) {

        title.setText(foodtruckReview.getTitle());
        text.setText(foodtruckReview.getText());

    }


}
