package com.hapis.customer.ui.adapters;

import android.widget.Filter;

import java.util.ArrayList;

public class CommonSearchCustomFilter extends Filter {

    private CommonSearchAdapter mLocationSearchAdapter;
    private ArrayList<String> filterList;

    public CommonSearchCustomFilter(CommonSearchAdapter locationSearchAdapter,
                                    ArrayList<String> filterList){
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
            ArrayList<String> filteredPlayers = new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).toUpperCase().contains(constraint))
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

        mLocationSearchAdapter.actualList = (ArrayList<String>) results.values;

        //REFRESH
        mLocationSearchAdapter.notifyDataSetChanged();
    }
}
