package com.hapis.customer.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hapis.customer.R;
import com.hapis.customer.ui.AddUserWalletActivity;
import com.hapis.customer.ui.custom.materialedittext.MaterialEditText;
import com.hapis.customer.ui.models.wallet.RechargeRequest;
import com.hapis.customer.ui.utils.EditTextUtils;
import com.hapis.customer.ui.view.BaseView;
import com.hapis.customer.ui.view.UserWalletFragmentView;
import com.hapis.customer.ui.view.UserWalletFragmentViewModal;
import com.hapis.customer.utils.Util;

import java.util.List;

public class AddUserWalletFragment extends BaseAbstractFragment<UserWalletFragmentViewModal> implements UserWalletFragmentView {

    public static final String TAG = AddUserWalletFragment.class.getName();

    private AppCompatTextView available_balance_value_tv;
    private LinearLayout add_wallet_money_bttn;
    private MaterialEditText enter_amount_edittext;
    private AppCompatTextView amount_one_thousand_tv, amount_one_thousand_five_hundred_tv, amount_two_thousand_tv, amount_two_thousand_five_hundred_tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_user_wallet, container, false);

        enter_amount_edittext = v.findViewById(R.id.enter_amount_edittext);

        amount_one_thousand_tv = v.findViewById(R.id.amount_one_thousand_tv);
        amount_one_thousand_tv.setText("+ "+getResources().getString(R.string.rs_currency)+" "+Util.getFormattedAmount(1000));
        amount_one_thousand_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enter_amount_edittext.setText(Util.getFormattedAmount(1000));
            }
        });

        amount_one_thousand_five_hundred_tv = v.findViewById(R.id.amount_one_thousand_five_hundred_tv);
        amount_one_thousand_five_hundred_tv.setText("+ "+getResources().getString(R.string.rs_currency)+" "+Util.getFormattedAmount(1500));
        amount_one_thousand_five_hundred_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enter_amount_edittext.setText(Util.getFormattedAmount(1500));
            }
        });

        amount_two_thousand_tv = v.findViewById(R.id.amount_two_thousand_tv);
        amount_two_thousand_tv.setText("+ "+getResources().getString(R.string.rs_currency)+" "+Util.getFormattedAmount(2000));
        amount_two_thousand_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enter_amount_edittext.setText(Util.getFormattedAmount(2000));
            }
        });

        amount_two_thousand_five_hundred_tv = v.findViewById(R.id.amount_two_thousand_five_hundred_tv);
        amount_two_thousand_five_hundred_tv.setText("+ "+getResources().getString(R.string.rs_currency)+" "+Util.getFormattedAmount(2500));
        amount_two_thousand_five_hundred_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enter_amount_edittext.setText(Util.getFormattedAmount(2500));
            }
        });

        available_balance_value_tv = v.findViewById(R.id.available_balance_value_tv);
        available_balance_value_tv.setText(getResources().getString(R.string.rs_currency)+" "+Util.getFormattedAmount(0.0));

        add_wallet_money_bttn = v.findViewById(R.id.add_wallet_money_bttn);
        add_wallet_money_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EditTextUtils.isEmpty(enter_amount_edittext))
                    Toast.makeText(getActivity(), getResources().getString(R.string.please_enter_amount), Toast.LENGTH_SHORT).show();
                else{
                    mViewModal.amountEnteredProceedNext(EditTextUtils.getText(enter_amount_edittext));
                }
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        mViewModal.getAvailableWalletBalance();
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
            available_balance_value_tv.setText(getResources().getString(R.string.rs_currency)+" "+Util.getFormattedAmount(balance.doubleValue()));
        }
    }

    @Override
    public void amountEnteredProceedNext(String enteredAmount) {
        ((AddUserWalletActivity)getActivity()).amountEnteredProceedNext(enteredAmount);
    }

    @Override
    public void rechargeWalletAccountDone() {

    }

    @Override
    public void failedToRecharge() {

    }

    @Override
    public void updatePastRecharges(List<RechargeRequest> rechargeRequests) {

    }
}
