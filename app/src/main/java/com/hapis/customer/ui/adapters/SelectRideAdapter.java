package com.hapis.customer.ui.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hapis.customer.R;

/**
 * Created by Javeed
 */
public class SelectRideAdapter extends RecyclerView.Adapter<SelectRideAdapter.MyViewHolder> {

    private Context mContext;
    private String[] serviceList;
    private int[] iconsIds;
    private int selectedPosition;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout lay;
        public AppCompatTextView title;
        AppCompatImageView icon;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.text);
            icon = view.findViewById(R.id.icon);
            lay = view.findViewById(R.id.item_lay);
        }
    }

    public void setSelectedPosition(int pos) {
        selectedPosition = pos;
    }

    public SelectRideAdapter(Context context, String[] serviceList, int[] iconsIds) {
        this.serviceList = serviceList;
        this.mContext = context;
        this.iconsIds = iconsIds;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ride_type_item_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final String title = serviceList[position];
        final int iconId = iconsIds[position];

        if (selectedPosition == position) {
            holder.icon.setSelected(true);
            holder.title.setTextColor(mContext.getResources().getColor(R.color.app_color));
//            holder.lay.setBackgroundColor(mContext.getResources().getColor(R.color.app_dark_color));
        } else {
            holder.icon.setSelected(true);
            holder.title.setTextColor(mContext.getResources().getColor(R.color.white));
//            holder.lay.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }
        holder.title.setText(title);
        holder.icon.setImageDrawable(mContext.getResources().getDrawable(iconId));
    }

    @Override
    public int getItemCount() {
        return serviceList.length;
    }
}