package com.hapis.customer.ui.view;

import android.arch.lifecycle.LifecycleOwner;

import com.hapis.customer.R;
import com.hapis.customer.ui.models.HapisModel;

public class TakeATourViewModal extends BaseViewModal<TakeATourView> {

    private String TAG = TakeATourViewModal.class.getName();

    public TakeATourViewModal(LifecycleOwner owner) {
        super(owner);
    }

    @Override
    protected void initObservableData() {

    }

    @Override
    protected void handleChangedDataModal(HapisModel data) {

    }

    public void handleClick(int clickedId){
        switch (clickedId){
            case R.id.btn_signin:
            {
                mView.viewSignIn();
                break;
            }
            case R.id.btn_signup:
            {
                mView.viewSignUp();
                break;
            }
        }
    }
}
