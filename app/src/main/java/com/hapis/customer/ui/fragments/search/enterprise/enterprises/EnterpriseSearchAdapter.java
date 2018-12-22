package com.hapis.customer.ui.fragments.search.enterprise.enterprises;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.hapis.customer.R;
import com.hapis.customer.ui.models.enterprise.EnterpriseRequest;

import java.util.ArrayList;
import java.util.List;

public class EnterpriseSearchAdapter extends RecyclerView.Adapter<EnterpriseSearchAdapterViewHolder> implements Filterable {

    private Context c;
    public ArrayList<EnterpriseRequest> actualList,filterList;
    private EnterpriseSearchCustomFilter customFilter;
    private EnterpriseSelectedCallBack mEnterpriseSelectedCallBack;

    public EnterpriseSearchAdapter(Context ctx, List<EnterpriseRequest> arrayList, EnterpriseSelectedCallBack enterpriseSelectedCallBack)
    {
        this.c=ctx;
        actualList = new ArrayList<>();
        actualList.addAll(arrayList);
        if(filterList == null)
            filterList = new ArrayList<>();

        filterList.addAll(actualList);

        mEnterpriseSelectedCallBack = enterpriseSelectedCallBack;
    }

    @Override
    public EnterpriseSearchAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //CONVERT XML TO VIEW ONBJ
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_location_recyclerview_item_row,null);

        //HOLDER
        EnterpriseSearchAdapterViewHolder holder=new EnterpriseSearchAdapterViewHolder(v);

        return holder;
    }

    //DATA BOUND TO VIEWS
    @Override
    public void onBindViewHolder(EnterpriseSearchAdapterViewHolder holder, int position) {

        //BIND DATA
        holder.brandName.setText(actualList.get(position).getEnterpriseName());


        //IMPLEMENT CLICK LISTENET
        holder.setItemClickListener(new EnterpriseSearchAdapterItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                mEnterpriseSelectedCallBack.onValueSelected(actualList.get(pos));
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
            customFilter = new EnterpriseSearchCustomFilter(this, filterList);
        }

        return customFilter;
    }

    public interface EnterpriseSelectedCallBack {
        void onValueSelected(EnterpriseRequest selectedEnterpriseRequest);
    }
}
