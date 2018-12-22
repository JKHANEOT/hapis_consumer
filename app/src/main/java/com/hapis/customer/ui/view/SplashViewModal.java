package com.hapis.customer.ui.view;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.hapis.customer.database.repository.UserProfileRepository;
import com.hapis.customer.ui.models.HapisModel;

public class SplashViewModal extends BaseViewModal<SplashView> {

    private String TAG = SplashViewModal.class.getName();

    private final long SPLASH_TIME_OUT = 3000;

    private UserProfileRepository userProfileRepository;

    public SplashViewModal(LifecycleOwner owner) {
        super(owner);

        userProfileRepository = new UserProfileRepository();
    }

    @Override
    protected void initObservableData() {

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
//                final Integer userStatus = AccessPreferences.get(HapisApplication.getApplication(), ApplicationConstants.IS_USER_LOGGED_IN, ApplicationConstants.USER_NEW);
                MutableLiveData<Integer> appStatus = new MutableLiveData<>();
                userProfileRepository.getAppProfileStatus(appStatus);

                appStatus.observe(mOwner, new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer integer) {
                        mView.loadDesiredActivity(integer);
                    }
                });
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void handleChangedDataModal(HapisModel data) {

    }
}
