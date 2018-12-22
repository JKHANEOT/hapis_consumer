package com.hapis.customer.ui.view;

import android.arch.lifecycle.LifecycleOwner;

import com.hapis.customer.ui.models.HapisModel;

public class SignUpViewModal extends BaseViewModal<LoginView> {

    private String TAG = SignUpViewModal.class.getName();

    public SignUpViewModal(LifecycleOwner owner) {
        super(owner);
    }

    @Override
    protected void initObservableData() {

    }

    @Override
    protected void handleChangedDataModal(HapisModel data) {

    }
}
