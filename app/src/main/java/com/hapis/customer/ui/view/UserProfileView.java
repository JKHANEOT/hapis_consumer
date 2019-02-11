package com.hapis.customer.ui.view;

import com.hapis.customer.ui.custom.dialogplus.OnClickListener;

public interface UserProfileView extends BaseView {

    void showError(String errorMsg, OnClickListener onClickListener, String positiveLbl, String negativeLbl, String status);
}
