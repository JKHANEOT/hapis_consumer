package com.hapis.customer.ui;

import android.os.Bundle;

import com.hapis.customer.R;
import com.hapis.customer.ui.custom.dialogplus.OnClickListener;
import com.hapis.customer.ui.fragments.UserWalletFragment;
import com.hapis.customer.ui.utils.AlertUtil;
import com.hapis.customer.ui.utils.DialogIconCodes;
import com.hapis.customer.ui.view.BaseView;
import com.hapis.customer.ui.view.BookAppointmentViewModal;
import com.hapis.customer.ui.view.UserWalletActivityView;
import com.hapis.customer.ui.view.UserWalletActivityViewModal;

public class UserWalletActivity extends BaseFragmentActivity<UserWalletActivityViewModal> implements UserWalletActivityView {

    @Override
    protected Class getViewModalClass() {
        return BookAppointmentViewModal.class;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wallet);

        setUpNavigationDrawer(getResources().getString(R.string.wallet_balance), null, true, null);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new UserWalletFragment(), UserWalletFragment.TAG).addToBackStack(null).commit();
    }

    @Override
    public void showError(String errorMsg, OnClickListener onClickListener, String positiveLbl, String negativeLbl, String status) {
        if(onClickListener == null){
            AlertUtil.showAlert(UserWalletActivity.this, getResources().getString(R.string.wallet_account), errorMsg, DialogIconCodes.DIALOG_FAILED.getIconCode());
        }else{
            if(positiveLbl != null && positiveLbl.length() > 0 && (negativeLbl == null || (negativeLbl != null && negativeLbl.length() == 0)))
                AlertUtil.showAlert(UserWalletActivity.this, getResources().getString(R.string.wallet_account), errorMsg, positiveLbl, onClickListener, status);
            else if(positiveLbl != null && positiveLbl.length() > 0 && negativeLbl != null && negativeLbl.length() > 0)
                AlertUtil.showAlert(UserWalletActivity.this, getResources().getString(R.string.wallet_account), errorMsg, positiveLbl, negativeLbl, onClickListener, status);
            else
                AlertUtil.showAlert(UserWalletActivity.this, getResources().getString(R.string.wallet_account), errorMsg, DialogIconCodes.DIALOG_FAILED.getIconCode());
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }
}

