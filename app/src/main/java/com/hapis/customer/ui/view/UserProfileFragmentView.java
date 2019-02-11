package com.hapis.customer.ui.view;

import com.hapis.customer.database.tables.UserProfileTable;

public interface UserProfileFragmentView extends BaseView {


    void failedToProcess(String errorMsg);

    void updateUserProfile(String msg);

    void loadUserProfileDetails(UserProfileTable userProfileTable);
}
