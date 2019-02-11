package com.hapis.customer.ui.view;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.hapis.customer.database.repository.UserProfileRepository;
import com.hapis.customer.database.tables.UserProfileTable;
import com.hapis.customer.ui.models.HapisModel;

public class UserProfileFragmentViewModal extends BaseViewModal<UserProfileFragmentView> {

    private String TAG = UserProfileFragmentViewModal.class.getName();

    private UserProfileRepository userProfileRepository;

    public UserProfileFragmentViewModal(LifecycleOwner owner) {
        super(owner);

        userProfileRepository = new UserProfileRepository();
    }

    @Override
    protected void initObservableData() {
    }

    @Override
    protected void handleChangedDataModal(HapisModel data) {

    }

    public void loadUserProfileDetails() {
        MutableLiveData<UserProfileTable> userProfileTableMutableLiveData = new MutableLiveData<>();

        userProfileRepository.loadUserProfileDetails(userProfileTableMutableLiveData);
        userProfileTableMutableLiveData.observe(mOwner, new Observer<UserProfileTable>() {
            @Override
            public void onChanged(@Nullable UserProfileTable userProfileTable) {
                if(userProfileTable != null){
                    mView.loadUserProfileDetails(userProfileTable);
                }
            }
        });
    }
}
