package com.hapis.customer.ui.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hapis.customer.R;
import com.hapis.customer.ui.models.NavDrawerItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by Javeed on 03-10-2018.
 */
public class NavMenuDrawerAdapter extends RecyclerView.Adapter<NavMenuDrawerAdapter.MyViewHolder> {

    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    private Drawable bgDrawable;

    public NavMenuDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_item_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        NavDrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());
//        Drawable myDrawable = context.getResources().getDrawable(R.drawable.ic_change_password/*current.getIcon()*/);
//        holder.icon.setImageDrawable(myDrawable);
        holder.icon.setImageResource(current.getIcon());
        holder.itemLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setShowNotify(position);
                notifyDataSetChanged();
            }
        });
        /*if(current.isShowNotify()){
            bgDrawable = context.getResources().getDrawable(R.drawable.nav_item_clicked_bg);
        } else {
            bgDrawable = context.getResources().getDrawable(R.drawable.nav_item_unclicked_bg);
        }
        holder.icon.setBackground(bgDrawable);*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        AppCompatImageView icon;
        LinearLayout itemLay;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            icon = itemView.findViewById(R.id.item_icon);
            itemLay = itemView.findViewById(R.id.item_lay);
        }
    }

    private void setShowNotify(int position) {
        for (int i = 0; i < data.size(); i++) {
            if (i == position) {
                data.get(i).setShowNotify(true);
            } else {
                data.get(i).setShowNotify(false);
            }
            notifyDataSetChanged();
        }
    }
}
