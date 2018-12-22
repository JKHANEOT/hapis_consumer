package com.hapis.customer.ui.adapters;

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

public class BookingTimeSlotAdapter extends RecyclerView.Adapter<BookingTimeSlotAdapter.MyViewHolder> {

    private String TAG = BookingTimeSlotAdapter.class.getName();
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatButton time_slot_btn;

        public MyViewHolder(View view) {
            super(view);
            time_slot_btn = view.findViewById(R.id.time_slot_btn);
        }
    }

    private BookingTimeSlotAdapterCallBack mBookingTimeSlotAdapterCallBack;

    public BookingTimeSlotAdapter(Context context, List<String> bookingTimeSlotList, BookingTimeSlotAdapterCallBack bookingTimeSlotAdapterCallBack) {
        this.mContext = context;
        mBookingTimeSlotList = bookingTimeSlotList;
        mBookingTimeSlotAdapterCallBack = bookingTimeSlotAdapterCallBack;
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
                        mBookingTimeSlotAdapterCallBack.removeBookingTimeSlot(timeSlotToPass);
                        toggleProceedIcon();
                        notifyDataSetChanged();
                    }else {
                        /*if(mBookingTimeSlotAdapterCallBack.getTotalSlotSelected() == BookingClassActivity.maximumSlotsAllowedToSelect){

                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(mContext.getResources().getString(R.string.maximum_of));
                            stringBuilder.append(" "+String.valueOf(BookingClassActivity.maximumSlotsAllowedToSelect)+" ");
                            stringBuilder.append(mContext.getResources().getString(R.string.slots_allowed));

                            mBookingTimeSlotAdapterCallBack.showAlertWithMsg(stringBuilder.toString());
                        }else {*/
                        selectedBookingTimeSlotList.add(timeSlot);
                        mBookingTimeSlotAdapterCallBack.addBookingTimeSlot(timeSlotToPass);
                        toggleProceedIcon();
                        notifyDataSetChanged();
                        /*}*/
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
        if(mBookingTimeSlotAdapterCallBack.getTotalSlotSelected() > 0)
            mBookingTimeSlotAdapterCallBack.toggleProceedIcon(true);
        else
            mBookingTimeSlotAdapterCallBack.toggleProceedIcon(false);
    }

    @Override
    public int getItemCount() {
        return mBookingTimeSlotList != null ? mBookingTimeSlotList.size() : 0;
    }

    public interface BookingTimeSlotAdapterCallBack {
        void addBookingTimeSlot(String timeSlot);
        void removeBookingTimeSlot(String timeSlot);
        void showAlertWithMsg(String msg);
        int getTotalSlotSelected();
        void toggleProceedIcon(boolean toggleView);
    }
}
