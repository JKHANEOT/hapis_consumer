package com.hapis.customer.ui.fragments.search.enterprise.doctor;

import android.widget.Filter;

import com.hapis.customer.ui.models.appointments.DoctorDetails;

import java.util.ArrayList;

public class DoctorSearchByEnterpriseCustomFilter extends Filter {

    private DoctorSearchByEnterpriseAdapter mLocationSearchAdapter;
    private ArrayList<DoctorDetails> filterList;

    public DoctorSearchByEnterpriseCustomFilter(DoctorSearchByEnterpriseAdapter locationSearchAdapter,
                                                ArrayList<DoctorDetails> filterList){
        mLocationSearchAdapter = locationSearchAdapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<DoctorDetails> filteredPlayers = new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getFullName().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(filterList.get(i));
                }
            }

            results.count=filteredPlayers.size();
            results.values=filteredPlayers;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }


        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        mLocationSearchAdapter.actualList = (ArrayList<DoctorDetails>) results.values;

        //REFRESH
        mLocationSearchAdapter.notifyDataSetChanged();
    }
}
