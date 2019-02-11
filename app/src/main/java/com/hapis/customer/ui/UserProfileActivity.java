package com.hapis.customer.ui;

import android.os.Bundle;

import com.hapis.customer.R;
import com.hapis.customer.database.tables.UserProfileTable;
import com.hapis.customer.ui.custom.dialogplus.OnClickListener;
import com.hapis.customer.ui.fragments.LoginFrag;
import com.hapis.customer.ui.fragments.UserProfileFragment;
import com.hapis.customer.ui.utils.AlertUtil;
import com.hapis.customer.ui.utils.DialogIconCodes;
import com.hapis.customer.ui.view.BaseView;
import com.hapis.customer.ui.view.UserProfileView;
import com.hapis.customer.ui.view.UserProfileViewModal;

public class UserProfileActivity extends BaseFragmentActivity<UserProfileViewModal> implements UserProfileView {

    private UserProfileTable userProfile;

    public UserProfileTable getAppointmentRequest() {
        return userProfile;
    }

    @Override
    protected Class getViewModalClass() {
        return UserProfileViewModal.class;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        setUpNavigationDrawer(/*appointmentRequest == null ? getResources().getString(R.string.book_appointment) : */getResources().getString(R.string.my_profile), null, true, null);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new UserProfileFragment(), UserProfileFragment.TAG).addToBackStack(null).commit();
    }

    @Override
    public void showError(String errorMsg, OnClickListener onClickListener, String positiveLbl, String negativeLbl, String status) {
        if(onClickListener == null){
            AlertUtil.showAlert(UserProfileActivity.this, getResources().getString(R.string.my_profile), errorMsg, DialogIconCodes.DIALOG_FAILED.getIconCode());
        }else{
            if(positiveLbl != null && positiveLbl.length() > 0 && (negativeLbl == null || (negativeLbl != null && negativeLbl.length() == 0)))
                AlertUtil.showAlert(UserProfileActivity.this, getResources().getString(R.string.my_profile), errorMsg, positiveLbl, onClickListener, status);
            else if(positiveLbl != null && positiveLbl.length() > 0 && negativeLbl != null && negativeLbl.length() > 0)
                AlertUtil.showAlert(UserProfileActivity.this, getResources().getString(R.string.my_profile), errorMsg, positiveLbl, negativeLbl, onClickListener, status);
            else
                AlertUtil.showAlert(UserProfileActivity.this, getResources().getString(R.string.my_profile), errorMsg, DialogIconCodes.DIALOG_FAILED.getIconCode());
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

