package com.hapis.customer.ui.view;

public interface LoginFragmentView extends BaseView {

    void validateScreenFields(String errorMsg);

    void forgotPasswordClicked();

    void signUpClicked();

    void SigninRequestSuccess(String msg);

    void SigninRequestFailed(String errorMsg);
}
