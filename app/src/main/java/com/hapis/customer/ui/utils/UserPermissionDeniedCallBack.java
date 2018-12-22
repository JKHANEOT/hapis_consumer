package com.hapis.customer.ui.utils;

public interface UserPermissionDeniedCallBack {

    void takeActionOnOnlyPermissionDenied();
    void takeActionOnCheckedDontAskAndPermissionDenied();
    void noAction();
}
