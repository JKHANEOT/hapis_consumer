package com.hapis.customer.ui.view;

import com.hapis.customer.database.tables.UserProfileTable;
import com.hapis.customer.ui.models.AddressModel;

public interface UserProfileFragmentView extends BaseView {

    void validateScreenFields(String errorMsg);

    void failedToProcess(String errorMsg);

    void updateUserProfile(String msg);

    void loadUserProfileDetails(UserProfileTable userProfileTable);

    void loadUserProfilAddressDetails(AddressModel addressModel);
}
