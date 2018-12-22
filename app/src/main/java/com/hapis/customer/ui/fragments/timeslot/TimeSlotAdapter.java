package com.hapis.customer.ui.fragments.timeslot;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hapis.customer.R;

import java.util.ArrayList;
import java.util.List;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.MyViewHolder> {

    private String TAG = TimeSlotAdapter.class.getName();
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatButton time_slot_btn;

        public MyViewHolder(View view) {
            super(view);
            time_slot_btn = view.findViewById(R.id.time_slot_btn);
        }
    }

    private TimeSlotAdapterCallBack mTimeSlotAdapterCallBack;

    public TimeSlotAdapter(Context context, List<String> bookingTimeSlotList, TimeSlotAdapterCallBack timeSlotAdapterCallBack) {
        this.mContext = context;
        mBookingTimeSlotList = bookingTimeSlotList;
        mTimeSlotAdapterCallBack = timeSlotAdapterCallBack;
    }

    private List<String> mBookingTimeSlotList;

    public void updateProducts(List<String> bookingTimeSlotList){
        mBookingTimeSlotList = bookingTimeSlotList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_time_slot_row, parent, false);

        return new MyViewHolder(itemView);
    }

    private List<Integer> selectedBookingTimeSlotPosList;

    public void setSelectedBookingTimeSlotList(List<String> selectedBookingTimeSlotList) {
        this.selectedBookingTimeSlotList = selectedBookingTimeSlotList;
    }

    private List<String> selectedBookingTimeSlotList;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final int pos = position;

        if (mBookingTimeSlotList != null && mBookingTimeSlotList.size() > 0) {
            final String timeSlot = new String(mBookingTimeSlotList.get(pos));
            final String timeSlotToPass = new String(mBookingTimeSlotList.get(pos));
            holder.time_slot_btn.setText(timeSlot);
            if(selectedBookingTimeSlotList == null)
                selectedBookingTimeSlotList = new ArrayList<>();

            holder.time_slot_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedBookingTimeSlotList.contains(timeSlot)) {
                        selectedBookingTimeSlotList.remove(timeSlot);
                        mTimeSlotAdapterCallBack.removeBookingTimeSlot(timeSlotToPass);
                        toggleProceedIcon();
                        notifyDataSetChanged();
                    }else {
                        if(mTimeSlotAdapterCallBack.getTotalSlotSelected() == 1){

                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(mContext.getResources().getString(R.string.maximum_of));
                            stringBuilder.append(" "+String.valueOf(1)+" ");
                            stringBuilder.append(mContext.getResources().getString(R.string.slots_allowed));

                            mTimeSlotAdapterCallBack.showAlertWithMsg(stringBuilder.toString());
                        }else {
                            selectedBookingTimeSlotList.add(timeSlot);
                            mTimeSlotAdapterCallBack.addBookingTimeSlot(timeSlotToPass, pos);
                            toggleProceedIcon();
                            notifyDataSetChanged();
                        }
                    }
                }
            });

            if(selectedBookingTimeSlotList.contains(timeSlot)){
                holder.time_slot_btn.setTextColor(mContext.getResources().getColor(R.color.booking_time_slot_selected_text));
                holder.time_slot_btn.setTextAppearance(mContext, R.style.semi_bold_12sp);
                holder.time_slot_btn.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rounded_button_booking_time_slot_selected));
            }else{
                holder.time_slot_btn.setTextColor(mContext.getResources().getColor(R.color.booking_time_slot_unselected_text));
                holder.time_slot_btn.setTextAppearance(mContext, R.style.regular_12sp);
                holder.time_slot_btn.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rounded_button_booking_time_slot_unselected));
            }
        }
    }

    private void toggleProceedIcon(){
        if(mTimeSlotAdapterCallBack.getTotalSlotSelected() > 0)
            mTimeSlotAdapterCallBack.toggleProceedIcon(true);
        else
            mTimeSlotAdapterCallBack.toggleProceedIcon(false);
    }

    @Override
    public int getItemCount() {
        return mBookingTimeSlotList != null ? mBookingTimeSlotList.size() : 0;
    }

    public interface TimeSlotAdapterCallBack {
        void addBookingTimeSlot(String timeSlot, int index);
        void removeBookingTimeSlot(String timeSlot);
        void showAlertWithMsg(String msg);
        int getTotalSlotSelected();
        void toggleProceedIcon(boolean toggleView);
    }
}
