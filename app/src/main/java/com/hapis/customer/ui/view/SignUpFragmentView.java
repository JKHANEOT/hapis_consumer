package com.hapis.customer.ui.view;

public interface SignUpFragmentView extends BaseView {

    void validateScreenFields(String errorMsg);

    void signInClicked();

    void SignupRequestFailed(String errorMsg);

    void SignupRequestSuccess(String msg);
}
