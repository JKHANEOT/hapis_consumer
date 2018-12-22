package com.hapis.customer.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hapis.customer.HapisApplication;
import com.hapis.customer.R;
import com.hapis.customer.ui.utils.DeviceScreenResolutionUtil;

/**
 * Created by Javeed on 03/10/2018.
 */

public class DividerItemDecorationMenuMore extends RecyclerView.ItemDecoration {

    private Drawable mDivider;

    public DividerItemDecorationMenuMore(Context context) {
        mDivider = ContextCompat.getDrawable(context, R.drawable.line_divider_menu_more);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        int left = parent.getPaddingLeft();
//        int right = parent.getWidth() - parent.getPaddingRight();
        final int right = parent.getWidth();
        final int leftWithMargin = DeviceScreenResolutionUtil.getValueInPx(90, HapisApplication.getApplication());

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount-1; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(leftWithMargin, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
