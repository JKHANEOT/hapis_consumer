package com.hapis.customer.ui.fragments.search.enterprise.enterprises;

import android.widget.Filter;

import com.hapis.customer.ui.models.enterprise.EnterpriseRequest;

import java.util.ArrayList;

public class EnterpriseSearchCustomFilter extends Filter {

    private EnterpriseSearchAdapter mLocationSearchAdapter;
    private ArrayList<EnterpriseRequest> filterList;

    public EnterpriseSearchCustomFilter(EnterpriseSearchAdapter locationSearchAdapter,
                                        ArrayList<EnterpriseRequest> filterList){
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
            ArrayList<EnterpriseRequest> filteredPlayers = new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getEnterpriseName().toUpperCase().contains(constraint))
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

        mLocationSearchAdapter.actualList = (ArrayList<EnterpriseRequest>) results.values;

        //REFRESH
        mLocationSearchAdapter.notifyDataSetChanged();
    }
}
