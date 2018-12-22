package com.hapis.customer.ui.view;

import android.arch.lifecycle.LifecycleOwner;

import com.hapis.customer.ui.models.HapisModel;

public class DashboardViewModal extends BaseViewModal<LoginView> {

    private String TAG = DashboardViewModal.class.getName();

    public DashboardViewModal(LifecycleOwner owner) {
        super(owner);
    }

    @Override
    protected void initObservableData() {

    }

    @Override
    protected void handleChangedDataModal(HapisModel data) {

    }
}
