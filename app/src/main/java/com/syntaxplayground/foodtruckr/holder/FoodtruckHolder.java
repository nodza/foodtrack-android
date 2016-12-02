package com.syntaxplayground.foodtruckr.holder;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.syntaxplayground.foodtruckr.R;
import com.syntaxplayground.foodtruckr.model.Foodtruck;

/**
 * Created by nodza on 11/30/16.
 */

public class FoodtruckHolder extends RecyclerView.ViewHolder {

    private TextView truckName;
    private TextView foodType;
    private TextView avgCost;

    public FoodtruckHolder(View itemView) {
        super(itemView);

        this.truckName = (TextView) itemView.findViewById(R.id.foodtruck_name);
        this.foodType = (TextView) itemView.findViewById(R.id.foodtruck_type);
        this.avgCost = (TextView) itemView.findViewById(R.id.foodtruck_cost);
    }

    public void updateUI(Foodtruck foodtruck) {
        truckName.setText(foodtruck.getName());
        foodType.setText(foodtruck.getFoodType());
        avgCost.setText("$" + foodtruck.getAvgCost());
    }


}
