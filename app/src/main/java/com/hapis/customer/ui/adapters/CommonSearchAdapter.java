package com.hapis.customer.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.hapis.customer.R;
import com.hapis.customer.ui.callback.CommonSearchCallBack;

import java.util.ArrayList;
import java.util.List;

public class CommonSearchAdapter extends RecyclerView.Adapter<LocationSearchAdapterViewHolder> implements Filterable {

    private Context c;
    public ArrayList<String> actualList,filterList;
    private CommonSearchCustomFilter customFilter;
    private CommonSelectedCallBack mCmmonSelectedCallBack;

    public CommonSearchAdapter(Context ctx, List<String> arrayList, CommonSelectedCallBack commonSelectedCallBack)
    {
        this.c=ctx;
        actualList = new ArrayList<>();
        actualList.addAll(arrayList);
        if(filterList == null)
            filterList = new ArrayList<>();

        filterList.addAll(actualList);

        mCmmonSelectedCallBack = commonSelectedCallBack;
    }

    @Override
    public LocationSearchAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //CONVERT XML TO VIEW ONBJ
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_location_recyclerview_item_row,null);

        //HOLDER
        LocationSearchAdapterViewHolder holder=new LocationSearchAdapterViewHolder(v);

        return holder;
    }

    //DATA BOUND TO VIEWS
    @Override
    public void onBindViewHolder(LocationSearchAdapterViewHolder holder, int position) {

        //BIND DATA
        holder.brandName.setText(actualList.get(position));


        //IMPLEMENT CLICK LISTENET
        holder.setItemClickListener(new LocationSearchAdapterItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                mCmmonSelectedCallBack.onValueSelected(actualList.get(pos));
            }
        });

    }

    //GET TOTAL NUM OF PLAYERS
    @Override
    public int getItemCount() {
        return actualList.size();
    }

    //RETURN FILTER OBJ
    @Override
    public Filter getFilter() {
        if(customFilter==null)
        {
            customFilter = new CommonSearchCustomFilter(this, filterList);
        }

        return customFilter;
    }

    public interface CommonSelectedCallBack {
        void onValueSelected(String selectedValue);
    }
}
