package com.hapis.customer.ui.fragments.search.enterprise.doctor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.hapis.customer.R;
import com.hapis.customer.ui.models.appointments.DoctorDetails;

import java.util.ArrayList;
import java.util.List;

public class DoctorSearchByEnterpriseAdapter extends RecyclerView.Adapter<DoctorSearchByEnterpriseAdapterViewHolder> implements Filterable {

    private Context c;
    public ArrayList<DoctorDetails> actualList,filterList;
    private DoctorSearchByEnterpriseCustomFilter customFilter;
    private DoctorSelectedCallBack mDoctorSelectedCallBack;

    public DoctorSearchByEnterpriseAdapter(Context ctx, List<DoctorDetails> arrayList, DoctorSelectedCallBack enterpriseSelectedCallBack)
    {
        this.c=ctx;
        actualList = new ArrayList<>();
        actualList.addAll(arrayList);
        if(filterList == null)
            filterList = new ArrayList<>();

        filterList.addAll(actualList);

        mDoctorSelectedCallBack = enterpriseSelectedCallBack;
    }

    @Override
    public DoctorSearchByEnterpriseAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //CONVERT XML TO VIEW ONBJ
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_location_recyclerview_item_row,null);

        //HOLDER
        DoctorSearchByEnterpriseAdapterViewHolder holder=new DoctorSearchByEnterpriseAdapterViewHolder(v);

        return holder;
    }

    //DATA BOUND TO VIEWS
    @Override
    public void onBindViewHolder(DoctorSearchByEnterpriseAdapterViewHolder holder, int position) {

        //BIND DATA
        holder.brandName.setText(actualList.get(position).getFullName());


        //IMPLEMENT CLICK LISTENET
        holder.setItemClickListener(new DoctorSearchByEnterpriseAdapterItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                mDoctorSelectedCallBack.onValueSelected(actualList.get(pos));
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
            customFilter = new DoctorSearchByEnterpriseCustomFilter(this, filterList);
        }

        return customFilter;
    }

    public interface DoctorSelectedCallBack {
        void onValueSelected(DoctorDetails selectedDoctorDetails);
    }
}
