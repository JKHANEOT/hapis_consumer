package com.hapis.customer.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.hapis.customer.R;
import com.hapis.customer.ui.custom.dialogplus.OnClickListener;
import com.hapis.customer.ui.fragments.AddUserWalletFragment;
import com.hapis.customer.ui.fragments.AddUserWalletPaymentFragment;
import com.hapis.customer.ui.utils.AlertUtil;
import com.hapis.customer.ui.utils.DialogIconCodes;
import com.hapis.customer.ui.view.BaseView;
import com.hapis.customer.ui.view.BookAppointmentViewModal;
import com.hapis.customer.ui.view.UserWalletActivityView;
import com.hapis.customer.ui.view.UserWalletActivityViewModal;

public class AddUserWalletActivity extends BaseFragmentActivity<UserWalletActivityViewModal> implements UserWalletActivityView {

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
        setContentView(R.layout.activity_add_user_wallet);

        setUpNavigationDrawer(getResources().getString(R.string.add_money), null, true, null);
        addFragmentOnTop(new AddUserWalletFragment());
    }

    @Override
    public void showError(String errorMsg, OnClickListener onClickListener, String positiveLbl, String negativeLbl, String status) {
        if(onClickListener == null){
            AlertUtil.showAlert(AddUserWalletActivity.this, getResources().getString(R.string.add_money), errorMsg, DialogIconCodes.DIALOG_FAILED.getIconCode());
        }else{
            if(positiveLbl != null && positiveLbl.length() > 0 && (negativeLbl == null || (negativeLbl != null && negativeLbl.length() == 0)))
                AlertUtil.showAlert(AddUserWalletActivity.this, getResources().getString(R.string.add_money), errorMsg, positiveLbl, onClickListener, status);
            else if(positiveLbl != null && positiveLbl.length() > 0 && negativeLbl != null && negativeLbl.length() > 0)
                AlertUtil.showAlert(AddUserWalletActivity.this, getResources().getString(R.string.add_money), errorMsg, positiveLbl, negativeLbl, onClickListener, status);
            else
                AlertUtil.showAlert(AddUserWalletActivity.this, getResources().getString(R.string.add_money), errorMsg, DialogIconCodes.DIALOG_FAILED.getIconCode());
        }
    }

    /**
     * Add a fragment on top of the current tab
     */
    public void addFragmentOnTop(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    public String getEnteredAmount() {
        return enteredAmount;
    }

    private String enteredAmount;

    public void amountEnteredProceedNext(String enteredAmount){
        this.enteredAmount = enteredAmount;
        addFragmentOnTop(new AddUserWalletPaymentFragment());
    }
}

