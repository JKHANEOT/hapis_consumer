package com.hapis.customer.ui.custom;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by JKHAN
 */
public class InstantAutoCompleteTextView extends AppCompatAutoCompleteTextView {

    public InstantAutoCompleteTextView(Context context) {
        super(context);
    }

    public InstantAutoCompleteTextView(Context context, AttributeSet arg1) {
        super(context, arg1);
    }

    public InstantAutoCompleteTextView(Context context, AttributeSet arg1, int arg2) {
        super(context, arg1, arg2);
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    private boolean hasFocus = false;

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        focused = hasFocus;
        if (focused && getAdapter() != null) {
            performFiltering(getText(), 0);
            if (!isPopupShowing())
                showDropDown();
        }
    }

    private void showSoftKeyPad() {
        // SoftKeypad changes
        if (!hasFocus) {
            requestFocus();
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    //   inputMethodManager.showSoftInput(getRootView(),InputMethodManager.SHOW_IMPLICIT);
                   // inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                   inputMethodManager.toggleSoftInputFromWindow(getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
                }
            }, 100);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        showSoftKeyPad();
        if (getAdapter() != null && !isPopupShowing()) {
            showDropDown();
        }
        return false;
    }
}