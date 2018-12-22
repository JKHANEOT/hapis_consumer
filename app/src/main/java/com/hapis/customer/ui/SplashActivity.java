package com.hapis.customer.ui;

import android.content.Intent;
import android.os.Bundle;

import com.hapis.customer.R;
import com.hapis.customer.logger.LOG;
import com.hapis.customer.ui.utils.AccessPreferences;
import com.hapis.customer.ui.utils.ApplicationConstants;
import com.hapis.customer.ui.view.BaseView;
import com.hapis.customer.ui.view.SplashView;
import com.hapis.customer.ui.view.SplashViewModal;

public class SplashActivity extends BaseFragmentActivity<SplashViewModal> implements SplashView {

    private static final String TAG = SplashActivity.class.getName();

    @Override
    protected Class getViewModalClass() {
        return SplashViewModal.class;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void loadDesiredActivity(int userStatus) {

        if (userStatus == ApplicationConstants.USER_NEW) {
            LOG.d(TAG, "User Status : ApplicationConstants.USER_NEW");
            Intent i = new Intent(SplashActivity.this, TakeATourActivity.class);
            startActivity(i);
            finish();
            return;
        } else if (userStatus == ApplicationConstants.USER_LOGGED_IN) {
            LOG.d(TAG, "User Status : ApplicationConstants.USER_LOGGED_IN");

            String loggedInUserGuid = AccessPreferences.get(SplashActivity.this, ApplicationConstants.LOGGED_IN_USER_GUID, "");
            LOG.d(TAG, "loggedInUserGuid : " + loggedInUserGuid);
            LOG.d(TAG, "User Status : ApplicationConstants.USER_LOGGED_OUT");
            Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            try {
                finishAffinity();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (userStatus == ApplicationConstants.USER_LOGGED_OUT || userStatus == ApplicationConstants.USER_SIGNED_UP) {
//                    reachOutLoginScreen();
            LOG.d(TAG, "User Status : ApplicationConstants.USER_LOGGED_OUT");
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            try {
                finishAffinity();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Intent intent = new Intent(SplashActivity.this, SigupActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            try {
                finishAffinity();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
