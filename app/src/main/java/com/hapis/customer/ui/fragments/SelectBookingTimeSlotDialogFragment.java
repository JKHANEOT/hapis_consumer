package com.hapis.customer.ui.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hapis.customer.R;
import com.hapis.customer.ui.BaseFragmentActivity;
import com.hapis.customer.ui.adapters.BookingTimeSlotAdapter;
import com.hapis.customer.ui.adapters.GridSpacingItemDecoration;
import com.hapis.customer.ui.callback.SelectDateAndTimeSlotCallBack;

import java.util.ArrayList;
import java.util.List;

public class SelectBookingTimeSlotDialogFragment extends DialogFragment {

    public static final String TAG = SelectBookingTimeSlotDialogFragment.class.getName();

    private AppCompatTextView time_slot_not_available_msg_tv;
    private RecyclerView time_slot_grid_view;
    private LinearLayout close_dialog_ll;
    private GridLayoutManager mGridLayoutManager;
    private BookingTimeSlotAdapter rcAdapter;
    private List<String> mTimeSlotsFromServer;
    private AppCompatButton select_timings_bttn;

    public SelectBookingTimeSlotDialogFragment() {
        // Required empty public constructor
    }

    private SelectDateAndTimeSlotCallBack mSelectDateAndTimeSlotCallBack;

    private BaseFragmentActivity baseFragmentActivity;

    public static SelectBookingTimeSlotDialogFragment newInstance(SelectDateAndTimeSlotCallBack selectDateAndTimeSlotCallBack, List<String> timeSlotsFromServer, List<String> selectedBookingTimeSlotList) {
        SelectBookingTimeSlotDialogFragment f = new SelectBookingTimeSlotDialogFragment();
        f.setOnCallBackListener(selectDateAndTimeSlotCallBack);
        f.setTimeSlotsFromServer(timeSlotsFromServer);
        f.setSelectedBookingTimeSlotList(selectedBookingTimeSlotList);
        f.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
        return f;
    }

    public void setTimeSlotsFromServer(List<String> mTimeSlotsFromServer) {
        this.mTimeSlotsFromServer = mTimeSlotsFromServer;
    }

    public void setOnCallBackListener(SelectDateAndTimeSlotCallBack selectDateAndTimeSlotCallBack) {
        mSelectDateAndTimeSlotCallBack = selectDateAndTimeSlotCallBack;
        if (mSelectDateAndTimeSlotCallBack != null && mSelectDateAndTimeSlotCallBack instanceof BaseFragmentActivity)
            baseFragmentActivity = (BaseFragmentActivity) mSelectDateAndTimeSlotCallBack;
    }

    private int maxSlotsAllowed = 3;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_booking_slot, container);
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

    private AppCompatTextView selected_timings_tv;

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        close_dialog_ll = v.findViewById(R.id.close_dialog_ll);
        close_dialog_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        select_timings_bttn = v.findViewById(R.id.select_timings_bttn);
        select_timings_bttn.setOnClickListener(select_timings_bttn_OnClickListener);

        selected_timings_tv = v.findViewById(R.id.selected_timings_tv);

        time_slot_not_available_msg_tv = v.findViewById(R.id.time_slot_not_available_msg);
        time_slot_grid_view = v.findViewById(R.id.time_slot_grid_view);

        mGridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        time_slot_grid_view.setHasFixedSize(true);
        time_slot_grid_view.setLayoutManager(mGridLayoutManager);

        int resId = R.anim.carousel_layout_animation;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);
        time_slot_grid_view.setLayoutAnimation(animation);
        time_slot_grid_view.setItemAnimator(new DefaultItemAnimator());
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.select_booking_time_slot_grid_spacing);
        time_slot_grid_view.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true, 0));
        rcAdapter = new BookingTimeSlotAdapter(getActivity(), mTimeSlotsFromServer, bookingTimeSlotAdapterCallBack);
        time_slot_grid_view.setAdapter(rcAdapter);

        if(selectedBookingTimeSlotList != null && selectedBookingTimeSlotList.size() > 0) {
            loadTimeSlotUi();
        }
        updateScreenTitle();
    }

    View.OnClickListener select_timings_bttn_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(selectedBookingTimeSlotList == null ||
                    (selectedBookingTimeSlotList != null &&
                            selectedBookingTimeSlotList.size() == 0)){
//                AlertUtil.showAlert(mContext, mContext.getResources().getString(R.string.select_date), mContext.getResources().getString(R.string.please_select_a_date_to_continue), DialogIconCodes.DIALOG_NOT_AVAILABLE.getIconCode());
                Toast.makeText(baseFragmentActivity, baseFragmentActivity.getResources().getString(R.string.please_select_a_time_slot_to_continue), Toast.LENGTH_SHORT).show();
            }else{
                mSelectDateAndTimeSlotCallBack.updateSelectedTime(selectedBookingTimeSlotList);
                dismiss();
            }
        }
    };

    private List<String> selectedBookingTimeSlotList;

    public void setSelectedBookingTimeSlotList(List<String> selectedBookingTimeSlotList) {

        this.selectedBookingTimeSlotList = new ArrayList<>();

        if(selectedBookingTimeSlotList != null){
            this.selectedBookingTimeSlotList.addAll(selectedBookingTimeSlotList);
        }else
            this.selectedBookingTimeSlotList = new ArrayList<>();

        setTotalSelectedTimeSlots(this.selectedBookingTimeSlotList.size());
    }

    private void loadTimeSlotUi(){
        rcAdapter.setSelectedBookingTimeSlotList(selectedBookingTimeSlotList);
        rcAdapter.notifyDataSetChanged();
    }

    BookingTimeSlotAdapter.BookingTimeSlotAdapterCallBack bookingTimeSlotAdapterCallBack = new BookingTimeSlotAdapter.BookingTimeSlotAdapterCallBack() {
        @Override
        public void addBookingTimeSlot(final String timeSlot) {

            if(selectedBookingTimeSlotList == null)
                selectedBookingTimeSlotList = new ArrayList<>();

            if(!selectedBookingTimeSlotList.contains(timeSlot)) {
                selectedBookingTimeSlotList.add(timeSlot);
                totalSelectedTimeSlots++;
            }else
                totalSelectedTimeSlots++;

            updateScreenTitle();
        }

        @Override
        public void removeBookingTimeSlot(final String timeSlot) {

            if(selectedBookingTimeSlotList.contains(timeSlot)) {
                selectedBookingTimeSlotList.remove(timeSlot);
                totalSelectedTimeSlots--;
            }else
                totalSelectedTimeSlots--;

            updateScreenTitle();
        }

        @Override
        public void showAlertWithMsg(String msg) {
            if(msg != null && msg.length() > 0){
//                AlertUtil.showAlert(getActivity(), getResources().getString(R.string.select_time_slot), msg, DialogIconCodes.DIALOG_FAILED.getIconCode());
                Toast.makeText(baseFragmentActivity, msg, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public int getTotalSlotSelected() {
            return totalSelectedTimeSlots;
        }

        @Override
        public void toggleProceedIcon(boolean toggleView) {
//            ((BookingClassActivity)getActivity()).toggleProceedIcon(toggleView);
        }
    };

    private void updateScreenTitle(){

        if(selectedBookingTimeSlotList == null)
            selectedBookingTimeSlotList = new ArrayList<>();

        String title = getResources().getString(R.string.select_slots);
        title+=String.valueOf(selectedBookingTimeSlotList.size()+"/"+maxSlotsAllowed)+")";
        selected_timings_tv.setText(title);
    }

    public void setTotalSelectedTimeSlots(int totalSelectedTimeSlots) {
        this.totalSelectedTimeSlots = totalSelectedTimeSlots;
    }

    private int totalSelectedTimeSlots;
}
