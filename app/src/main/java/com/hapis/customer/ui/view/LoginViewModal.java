package com.hapis.customer.ui.view;

import android.arch.lifecycle.LifecycleOwner;

import com.hapis.customer.ui.models.HapisModel;

public class LoginViewModal extends BaseViewModal<LoginView> {

    private String TAG = LoginViewModal.class.getName();

    public LoginViewModal(LifecycleOwner owner) {
        super(owner);
    }

    @Override
    protected void initObservableData() {

    }

    @Override
    protected void handleChangedDataModal(HapisModel data) {

    }
}
