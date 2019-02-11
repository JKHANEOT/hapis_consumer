package com.hapis.customer.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hapis.customer.R;
import com.hapis.customer.ui.AddUserWalletActivity;
import com.hapis.customer.ui.adapters.UserWalletRechargesRecyclerViewAdapter;
import com.hapis.customer.ui.custom.recyclerviewanimations.RecyclerviewClickListeners;
import com.hapis.customer.ui.custom.recyclerviewanimations.animators.SlideInUpAnimator;
import com.hapis.customer.ui.models.wallet.RechargeRequest;
import com.hapis.customer.ui.view.BaseView;
import com.hapis.customer.ui.view.UserWalletFragmentView;
import com.hapis.customer.ui.view.UserWalletFragmentViewModal;
import com.hapis.customer.utils.Util;

import java.util.List;

public class UserWalletFragment extends BaseAbstractFragment<UserWalletFragmentViewModal> implements UserWalletFragmentView {

    public static final String TAG = UserWalletFragment.class.getName();

    private AppCompatTextView wallet_account_balance_value_tv;
    private AppCompatButton add_wallet_money_bttn;
    private LinearLayout past_recharges_ll;
    private RecyclerView past_recharges_rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_wallet, container, false);

        wallet_account_balance_value_tv = v.findViewById(R.id.wallet_account_balance_value_tv);
        add_wallet_money_bttn = v.findViewById(R.id.add_wallet_money_bttn);
        add_wallet_money_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddUserWalletActivity.class);
                getActivity().startActivity(intent);
            }
        });

        past_recharges_ll = v.findViewById(R.id.past_recharges_ll);
        past_recharges_rv = v.findViewById(R.id.past_recharges_rv);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        past_recharges_rv.setLayoutManager(mLayoutManager);
        past_recharges_rv.setHasFixedSize(true);

        past_recharges_rv.setItemAnimator(new SlideInUpAnimator());
        past_recharges_rv.addItemDecoration(new RecyclerviewClickListeners.VerticalSpaceItemDecoration(10));

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModal.getAvailableWalletBalance();
        wallet_account_balance_value_tv.setText(getResources().getString(R.string.rs_currency)+" "+Util.getFormattedAmount(0.0));
        mViewModal.getPastRecharges();
    }

    @Override
    protected Class getViewModalClass() {
        return UserWalletFragmentViewModal.class;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    public void updateAvailableBalance(Double balance) {
        if(balance != null && balance.doubleValue() > 0){
            wallet_account_balance_value_tv.setText(getResources().getString(R.string.rs_currency)+" "+Util.getFormattedAmount(balance.doubleValue()));
        }
    }

    @Override
    public void amountEnteredProceedNext(String enteredAmount) {

    }

    @Override
    public void rechargeWalletAccountDone() {

    }

    @Override
    public void failedToRecharge() {

    }

    @Override
    public void updatePastRecharges(List<RechargeRequest> rechargeRequests) {
        if(rechargeRequests != null && rechargeRequests.size() > 0){
            past_recharges_ll.setVisibility(View.VISIBLE);
            UserWalletRechargesRecyclerViewAdapter mAdapter = new UserWalletRechargesRecyclerViewAdapter(rechargeRequests);
            past_recharges_rv.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }else{
            past_recharges_ll.setVisibility(View.GONE);
        }
    }
}
