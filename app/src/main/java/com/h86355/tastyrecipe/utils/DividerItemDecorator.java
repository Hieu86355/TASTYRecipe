package com.h86355.tastyrecipe.utils;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DividerItemDecorator extends RecyclerView.ItemDecoration {
    private Drawable dividerDrawable;

    public DividerItemDecorator(Drawable dividerDrawable) {
        this.dividerDrawable = dividerDrawable;
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int dividerLeft = parent.getPaddingLeft() + 50;
        int dividerRight = parent.getWidth() - parent.getPaddingRight() - 50;

        int childCount = parent.getChildCount();
        for (int i = 0; i <= childCount - 2; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int dividerTop = child.getBottom() + params.bottomMargin;
            int dividerBottom = dividerTop + dividerDrawable.getIntrinsicHeight();

            dividerDrawable.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom);
            dividerDrawable.draw(c);
        }
    }
}
