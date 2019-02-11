package com.hapis.customer.ui.view;

import android.arch.lifecycle.LifecycleOwner;

import com.hapis.customer.ui.models.HapisModel;

public class UserProfileViewModal extends BaseViewModal<UserProfileView> {

    private String TAG = UserProfileViewModal.class.getName();

    public UserProfileViewModal(LifecycleOwner owner) {
        super(owner);
    }

    @Override
    protected void initObservableData() {

    }

    @Override
    protected void handleChangedDataModal(HapisModel data) {

    }
}
