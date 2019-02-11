package com.hapis.customer.ui.adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hapis.customer.R;
import com.hapis.customer.ui.models.wallet.RechargeRequest;
import com.hapis.customer.utils.DateUtil;
import com.hapis.customer.utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Javeed on 2/27/2018.
 */

public class UserWalletRechargesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

//    https://krtkush.github.io/2016/07/08/android-recyclerview-grouping-data.html

    private List<RechargeRequest> mConsolidatedList = new ArrayList<>();

    public UserWalletRechargesRecyclerViewAdapter(List<RechargeRequest> consolidatedList) {
        mConsolidatedList.addAll(consolidatedList);
    }

    @Override
    public int getItemCount() {
        return mConsolidatedList != null ? mConsolidatedList.size() : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new GeneralViewHolder(inflater.inflate(R.layout.recharge_adapter_row, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        final GeneralViewHolder generalViewHolder = (GeneralViewHolder)viewHolder;
        final RechargeRequest rechargeRequest = mConsolidatedList.get(position);

        if(rechargeRequest != null){
            if(rechargeRequest.getRechargeAmount() != null && rechargeRequest.getRechargeAmount().doubleValue() > 0){
                generalViewHolder.recharge_amount_val_tv.setText(generalViewHolder.recharge_amount_val_tv.getResources().getString(R.string.rs_currency)+" "+Util.getFormattedAmount(rechargeRequest.getRechargeAmount().doubleValue()));
            }else{
                generalViewHolder.recharge_amount_val_tv.setText(generalViewHolder.recharge_amount_val_tv.getResources().getString(R.string.rs_currency)+" "+Util.getFormattedAmount(0.0));
            }

            if(rechargeRequest.getCreationDate() != null){
                generalViewHolder.recharge_date_val_tv.setVisibility(View.VISIBLE);
                generalViewHolder.recharge_date_val_tv.setText(DateUtil.convertDateToDateStr(rechargeRequest.getCreationDate(), DateUtil.DATE_FORMAT_dd_MM_yyyy_SEP_HIPHEN));
            }else{
                generalViewHolder.recharge_date_val_tv.setVisibility(View.GONE);
            }
        }
    }

    // View holder for general row item
    class GeneralViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView recharge_amount_val_tv, recharge_date_val_tv;

        public GeneralViewHolder(View v) {
            super(v);

            recharge_amount_val_tv = v.findViewById(R.id.recharge_amount_val_tv);
            recharge_date_val_tv = v.findViewById(R.id.recharge_date_val_tv);
        }
    }
}
