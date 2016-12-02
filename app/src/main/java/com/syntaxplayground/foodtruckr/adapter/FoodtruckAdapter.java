package com.syntaxplayground.foodtruckr.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syntaxplayground.foodtruckr.R;
import com.syntaxplayground.foodtruckr.holder.FoodtruckHolder;
import com.syntaxplayground.foodtruckr.model.Foodtruck;

import java.util.ArrayList;

/**
 * Created by nodza on 11/30/16.
 */

public class FoodtruckAdapter extends RecyclerView.Adapter<FoodtruckHolder>{

    private ArrayList<Foodtruck> foodtrucks;

    public FoodtruckAdapter(ArrayList<Foodtruck> foodtrucks) {
        this.foodtrucks = foodtrucks;
    }

    @Override
    public void onBindViewHolder(FoodtruckHolder holder, int position) {

        final Foodtruck foodtruck = foodtrucks.get(position);
        holder.updateUI(foodtruck);

    }

    @Override
    public int getItemCount() {
        return foodtrucks.size();
    }

    @Override
    public FoodtruckHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View foodtruckCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_foodtruck, parent, false);
        return new FoodtruckHolder(foodtruckCard);
    }
}
