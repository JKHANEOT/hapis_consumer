package com.hapis.customer.ui.view;

import android.arch.lifecycle.LifecycleOwner;

import com.hapis.customer.ui.models.HapisModel;

public class LoadWebPageViewModal extends BaseViewModal<LoginView> {

    private String TAG = LoadWebPageViewModal.class.getName();

    public LoadWebPageViewModal(LifecycleOwner owner) {
        super(owner);
    }

    @Override
    protected void initObservableData() {

    }

    @Override
    protected void handleChangedDataModal(HapisModel data) {

    }
}
