package com.hapis.customer.ui;

import android.os.Bundle;

import com.hapis.customer.R;
import com.hapis.customer.ui.custom.dialogplus.OnClickListener;
import com.hapis.customer.ui.fragments.SignUpRevampFrag;
import com.hapis.customer.ui.utils.AlertUtil;
import com.hapis.customer.ui.utils.DialogIconCodes;
import com.hapis.customer.ui.view.BaseView;
import com.hapis.customer.ui.view.SignUpView;
import com.hapis.customer.ui.view.SignUpViewModal;

public class SigupActivity extends BaseFragmentActivity<SignUpViewModal> implements SignUpView {

    @Override
    protected Class getViewModalClass() {
        return SignUpViewModal.class;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setUpNavigationDrawer(getResources().getString(R.string.sign_up_label), null, true, null);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.login_container, new SignUpRevampFrag(), SignUpRevampFrag.TAG).addToBackStack(null).commit();
    }

    @Override
    public void showError(String errorMsg, OnClickListener onClickListener, String positiveLbl, String negativeLbl, String status) {
        if(onClickListener == null){
            AlertUtil.showAlert(SigupActivity.this, getResources().getString(R.string.login_label), errorMsg, DialogIconCodes.DIALOG_FAILED.getIconCode());
        }else{
            if(positiveLbl != null && positiveLbl.length() > 0 && (negativeLbl == null || (negativeLbl != null && negativeLbl.length() == 0)))
                AlertUtil.showAlert(SigupActivity.this, getResources().getString(R.string.login_label), errorMsg, positiveLbl, onClickListener, status);
            else if(positiveLbl != null && positiveLbl.length() > 0 && negativeLbl != null && negativeLbl.length() > 0)
                AlertUtil.showAlert(SigupActivity.this, getResources().getString(R.string.login_label), errorMsg, positiveLbl, negativeLbl, onClickListener, status);
            else
                AlertUtil.showAlert(SigupActivity.this, getResources().getString(R.string.login_label), errorMsg, DialogIconCodes.DIALOG_FAILED.getIconCode());
        }
    }
}

