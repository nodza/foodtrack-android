package com.syntaxplayground.foodtruckr.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by nodza on 12/1/16.
 */

public class ItemDecorator extends RecyclerView.ItemDecoration {

    private final int left;
    private final int top;
    private final int rigt;
    private final int bottom;

    public ItemDecorator(int left, int top, int rigt, int bottom) {
        this.left = left;
        this.top = top;
        this.rigt = rigt;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.set(left, rigt, top, bottom);
    }
}
