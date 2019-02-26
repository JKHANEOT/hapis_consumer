package com.hapis.customer.ui.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.hapis.customer.R;
import com.hapis.customer.ui.callback.BookRideDialogCallBack;

public class BookRideDialogFragment extends DialogFragment {

    public static final String TAG = BookRideDialogFragment.class.getName();

    public BookRideDialogFragment() {
        // Required empty public constructor
    }

    private BookRideDialogCallBack mBookRideDialogCallBack;
    private int rideIcon;
    private LinearLayout close_dialog_ll;
    private AppCompatImageView book_ride_avatar_iv;
    private AppCompatTextView book_ride_tv;

    public static BookRideDialogFragment newInstance(BookRideDialogCallBack mBookRideDialogCallBack, int rideIcon) {
        BookRideDialogFragment f = new BookRideDialogFragment();
        f.setOnCallBackListener(mBookRideDialogCallBack);
        f.setRideIcon(rideIcon);
        f.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
        return f;
    }

    public void setRideIcon(int rideIcon) {
        this.rideIcon = rideIcon;
    }

    public void setOnCallBackListener(BookRideDialogCallBack bookRideDialogCallBack) {
        mBookRideDialogCallBack = bookRideDialogCallBack;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        // Add back button listener
        dialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                // getAction to make sure this doesn't double fire
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
//                    mOnItemClickListenerCouponActivity.onDismissClick(couponModel);
//                    dismiss();
                    return true; // Capture onKey
                }
                return false; // Don't capture
            }
        });

        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.book_ride_after_appointment, container);
    }

    @Override
    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        // Call super onResume after sizing
        super.onResume();
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        close_dialog_ll = v.findViewById(R.id.close_dialog_ll);
        close_dialog_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBookRideDialogCallBack.bookRideWindowClosed();
                dismiss();
            }
        });

        book_ride_avatar_iv = v.findViewById(R.id.book_ride_avatar_iv);
        book_ride_avatar_iv.setImageDrawable(getActivity().getResources().getDrawable(rideIcon));

        book_ride_tv = v.findViewById(R.id.book_ride_tv);
        book_ride_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBookRideDialogCallBack.bookRideDone();
                dismiss();
            }
        });
    }
}
