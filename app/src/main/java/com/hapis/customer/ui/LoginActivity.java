package com.hapis.customer.ui;

import android.os.Bundle;

import com.hapis.customer.R;
import com.hapis.customer.ui.custom.dialogplus.OnClickListener;
import com.hapis.customer.ui.fragments.LoginFrag;
import com.hapis.customer.ui.utils.AlertUtil;
import com.hapis.customer.ui.utils.DialogIconCodes;
import com.hapis.customer.ui.view.BaseView;
import com.hapis.customer.ui.view.LoginView;
import com.hapis.customer.ui.view.LoginViewModal;

public class LoginActivity extends BaseFragmentActivity<LoginViewModal> implements LoginView {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.login_container, new LoginFrag(), LoginFrag.TAG).commit();
    }

    @Override
    protected Class getViewModalClass() {
        return LoginViewModal.class;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    public void showError(String errorMsg, OnClickListener onClickListener, String positiveLbl, String negativeLbl, String status) {
        if(onClickListener == null){
            AlertUtil.showAlert(LoginActivity.this, getResources().getString(R.string.login_label), errorMsg, DialogIconCodes.DIALOG_FAILED.getIconCode());
        }else{
            if(positiveLbl != null && positiveLbl.length() > 0 && (negativeLbl == null || (negativeLbl != null && negativeLbl.length() == 0)))
                AlertUtil.showAlert(LoginActivity.this, getResources().getString(R.string.login_label), errorMsg, positiveLbl, onClickListener, status);
            else if(positiveLbl != null && positiveLbl.length() > 0 && negativeLbl != null && negativeLbl.length() > 0)
                AlertUtil.showAlert(LoginActivity.this, getResources().getString(R.string.login_label), errorMsg, positiveLbl, negativeLbl, onClickListener, status);
            else
                AlertUtil.showAlert(LoginActivity.this, getResources().getString(R.string.login_label), errorMsg, DialogIconCodes.DIALOG_FAILED.getIconCode());
        }
    }
}

