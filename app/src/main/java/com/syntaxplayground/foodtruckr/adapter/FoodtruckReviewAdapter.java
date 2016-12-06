package com.syntaxplayground.foodtruckr.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syntaxplayground.foodtruckr.R;
import com.syntaxplayground.foodtruckr.holder.FoodtruckReviewHolder;
import com.syntaxplayground.foodtruckr.model.FoodtruckReview;

import java.util.ArrayList;

/**
 * Created by nodza on 12/5/16.
 */

public class FoodtruckReviewAdapter extends RecyclerView.Adapter<FoodtruckReviewHolder> {

    private ArrayList<FoodtruckReview> foodtruckReviews;

    public FoodtruckReviewAdapter(ArrayList<FoodtruckReview> foodtruckReviews) {
        this.foodtruckReviews = foodtruckReviews;
    }


    @Override
    public FoodtruckReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View foodtruckReviewCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_review, parent, false);
        return new FoodtruckReviewHolder(foodtruckReviewCard);
    }

    @Override
    public void onBindViewHolder(FoodtruckReviewHolder holder, int position) {

        final FoodtruckReview foodtruckReview = foodtruckReviews.get(position);
        holder.updateUI(foodtruckReview);

    }

    @Override
    public int getItemCount() {
        return foodtruckReviews.size();
    }
}
