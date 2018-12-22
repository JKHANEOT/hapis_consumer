package com.hapis.customer.ui.adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hapis.customer.R;
/**
 * Created by Javeed on 3/16/2018.
 */

public class LocationSearchAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public AppCompatTextView brandName;

    private LocationSearchAdapterItemClickListener registrationSelectBrandItemClickListener;

    public LocationSearchAdapterViewHolder(View itemView) {
        super(itemView);

        brandName = itemView.findViewById(R.id.location_name_tv);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.registrationSelectBrandItemClickListener.onItemClick(v,getLayoutPosition());

    }

    public void setItemClickListener(LocationSearchAdapterItemClickListener ic)
    {
        this.registrationSelectBrandItemClickListener=ic;
    }
}
