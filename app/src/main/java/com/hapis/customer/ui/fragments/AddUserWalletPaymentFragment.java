package com.hapis.customer.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.hapis.customer.R;
import com.hapis.customer.ui.AddUserWalletActivity;
import com.hapis.customer.ui.models.wallet.RechargeRequest;
import com.hapis.customer.ui.utils.EditTextUtils;
import com.hapis.customer.ui.view.BaseView;
import com.hapis.customer.ui.view.UserWalletFragmentView;
import com.hapis.customer.ui.view.UserWalletFragmentViewModal;
import com.hapis.customer.utils.Util;

import java.util.List;

public class AddUserWalletPaymentFragment extends BaseAbstractFragment<UserWalletFragmentViewModal> implements UserWalletFragmentView {

    public static final String TAG = AddUserWalletPaymentFragment.class.getName();

    private AppCompatTextView entered_amount_value_tv;

    private LinearLayout add_wallet_money_bttn;

    private RadioButton debit_card_rb, credit_card_rb;

    private LinearLayout debit_card_ll, credit_card_ll;

    private AppCompatEditText enter_your_debit_card_details_et, expiry_or_validity_date_debit_card_et, enter_cvv_debit_card_et;
    private AppCompatEditText enter_your_credit_card_details_et, expiry_or_validity_date_credit_card_et, enter_cvv_credit_card_et;

    private String enteredCardNumber, enteredCardValidity, enteredCardCvv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_money_to_wallet, container, false);

        entered_amount_value_tv = v.findViewById(R.id.entered_amount_value_tv);

        debit_card_rb = v.findViewById(R.id.debit_card_rb);
        debit_card_rb.setOnCheckedChangeListener(debitCardRbCheckedListener);

        debit_card_ll = v.findViewById(R.id.debit_card_ll);
        enter_your_debit_card_details_et = v.findViewById(R.id.enter_your_debit_card_details_et);
        enter_your_debit_card_details_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(EditTextUtils.isEmpty(enter_your_debit_card_details_et))
                    enteredCardNumber = null;
                else
                    enteredCardNumber = EditTextUtils.getText(enter_your_debit_card_details_et);
            }
        });
        expiry_or_validity_date_debit_card_et = v.findViewById(R.id.expiry_or_validity_date_debit_card_et);
        expiry_or_validity_date_debit_card_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(EditTextUtils.isEmpty(expiry_or_validity_date_debit_card_et))
                    enteredCardValidity = null;
                else
                    enteredCardValidity = EditTextUtils.getText(expiry_or_validity_date_debit_card_et);
            }
        });
        enter_cvv_debit_card_et = v.findViewById(R.id.enter_cvv_debit_card_et);
        enter_cvv_debit_card_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(EditTextUtils.isEmpty(enter_cvv_debit_card_et))
                    enteredCardCvv = null;
                else
                    enteredCardCvv = EditTextUtils.getText(enter_cvv_debit_card_et);
            }
        });

        credit_card_rb = v.findViewById(R.id.credit_card_rb);
        credit_card_rb.setOnCheckedChangeListener(creditCardRbCheckedListener);

        credit_card_ll = v.findViewById(R.id.credit_card_ll);
        enter_your_credit_card_details_et = v.findViewById(R.id.enter_your_credit_card_details_et);
        enter_your_credit_card_details_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(EditTextUtils.isEmpty(enter_your_credit_card_details_et))
                    enteredCardNumber = null;
                else
                    enteredCardNumber = EditTextUtils.getText(enter_your_credit_card_details_et);
            }
        });

        expiry_or_validity_date_credit_card_et = v.findViewById(R.id.expiry_or_validity_date_credit_card_et);
        expiry_or_validity_date_credit_card_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(EditTextUtils.isEmpty(expiry_or_validity_date_credit_card_et))
                    enteredCardValidity = null;
                else {
                    enteredCardValidity = EditTextUtils.getText(expiry_or_validity_date_credit_card_et);
                }
            }
        });

        enter_cvv_credit_card_et = v.findViewById(R.id.enter_cvv_credit_card_et);
        enter_cvv_credit_card_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(EditTextUtils.isEmpty(enter_cvv_credit_card_et))
                    enteredCardCvv = null;
                else
                    enteredCardCvv = EditTextUtils.getText(enter_cvv_credit_card_et);
            }
        });

        add_wallet_money_bttn = v.findViewById(R.id.add_wallet_money_bttn);
        add_wallet_money_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enteredCardNumber == null || enteredCardValidity == null || enteredCardCvv == null)
                    Toast.makeText(getActivity(), getResources().getString(R.string.please_enter_card_details), Toast.LENGTH_SHORT).show();
                else{
                    mViewModal.proceedWithRecharge(((AddUserWalletActivity)getActivity()).getEnteredAmount(), enteredCardNumber, enteredCardValidity, enteredCardCvv);
                }
            }
        });

        return v;
    }

    private CompoundButton.OnCheckedChangeListener debitCardRbCheckedListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            enter_your_debit_card_details_et.setText("");
            expiry_or_validity_date_debit_card_et.setText("");
            enter_cvv_debit_card_et.setText("");
            if(!isChecked){
                add_wallet_money_bttn.setVisibility(View.GONE);
                debit_card_ll.setVisibility(View.GONE);
            }else{
                credit_card_rb.setChecked(false);
                add_wallet_money_bttn.setVisibility(View.VISIBLE);
                debit_card_ll.setVisibility(View.VISIBLE);
            }
        }
    };

    private CompoundButton.OnCheckedChangeListener creditCardRbCheckedListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            enter_your_credit_card_details_et.setText("");
            expiry_or_validity_date_credit_card_et.setText("");
            enter_cvv_credit_card_et.setText("");
            if(!isChecked){
                add_wallet_money_bttn.setVisibility(View.GONE);
                credit_card_ll.setVisibility(View.GONE);
            }else{
                debit_card_rb.setChecked(false);
                add_wallet_money_bttn.setVisibility(View.VISIBLE);
                credit_card_ll.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        entered_amount_value_tv.setText(getResources().getString(R.string.rs_currency)+" "+Util.getFormattedAmount(((AddUserWalletActivity)getActivity()).getEnteredAmount()));
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
    public void updateAvailableBalance(Double balance) {}

    @Override
    public void amountEnteredProceedNext(String enteredAmount) {

    }

    @Override
    public void rechargeWalletAccountDone() {
        getActivity().finish();
    }

    @Override
    public void failedToRecharge() {
        Toast.makeText(getActivity(), getResources().getString(R.string.recharge_failed_please_try_again_later), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updatePastRecharges(List<RechargeRequest> rechargeRequests) {

    }
}
