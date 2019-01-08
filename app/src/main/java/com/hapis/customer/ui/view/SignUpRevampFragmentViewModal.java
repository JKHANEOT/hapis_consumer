package com.hapis.customer.ui.view;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.hapis.customer.HapisApplication;
import com.hapis.customer.R;
import com.hapis.customer.database.repository.UserProfileRepository;
import com.hapis.customer.ui.models.HapisModel;
import com.hapis.customer.ui.models.ResponseStatus;
import com.hapis.customer.ui.models.UserModel;
import com.hapis.customer.ui.models.UserModelResponse;
import com.hapis.customer.ui.utils.EditTextUtils;

public class SignUpRevampFragmentViewModal extends BaseViewModal<SignUpFragmentView> {

    private String TAG = SignUpRevampFragmentViewModal.class.getName();

    private UserProfileRepository userProfileRepository;

    public SignUpRevampFragmentViewModal(LifecycleOwner owner) {
        super(owner);

        userProfileRepository = new UserProfileRepository();
    }

    @Override
    protected void initObservableData() {

    }

    @Override
    protected void handleChangedDataModal(HapisModel data) {

    }

    public void validateRegistrationDetails(String first_name, String last_name, String mobile_number, String password) {
        String msg = null;

        if(EditTextUtils.isEmpty(first_name)){
            msg = HapisApplication.getApplication().getResources().getString(R.string.please_enter_first_name);
        }else if(EditTextUtils.isEmpty(last_name)){
            msg = HapisApplication.getApplication().getResources().getString(R.string.please_enter_last_name);
        }else if(EditTextUtils.isEmpty(mobile_number)){
            msg = HapisApplication.getApplication().getResources().getString(R.string.please_enter_mobile_number);
        }else if(EditTextUtils.isEmpty(password)) {
            msg = HapisApplication.getApplication().getResources().getString(R.string.please_enter_password);
        }

        mView.validateScreenFields(msg);
    }

    MutableLiveData<UserModelResponse> mutableLiveData = new MutableLiveData<UserModelResponse>();

    public void doSignup(String firstName, String lastName, String mobileNumber, String password, String externalReference) {

        UserModel userModel = new UserModel();

//        userModel.setAgentCode("NotDefined");
        userModel.setFirstName(firstName);
        userModel.setLastName(lastName);
        userModel.setMobileNumber(mobileNumber);
        userModel.setPassword(password);
        userModel.setExternalReference(externalReference);

        userModel.setCustomerType(HapisApplication.getApplication().getResources().getInteger(R.integer.application_type));

        userProfileRepository.doSignup(mutableLiveData, userModel);
        mutableLiveData.observe(mOwner, new Observer<UserModelResponse>() {
            @Override
            public void onChanged(@Nullable UserModelResponse userModelResponse) {
                if(userModelResponse != null){
                    if(userModelResponse.getStatus() != null && userModelResponse.getStatus().getStatusCode() != null && userModelResponse.getStatus().getStatusCode().intValue() == ResponseStatus.SUCCESS){
                        mView.SignupRequestSuccess(HapisApplication.getApplication().getResources().getString(R.string.signup_successful));
                    }else{
                        mView.SignupRequestFailed(((userModelResponse.getStatus().getErrorMessages() != null && userModelResponse.getStatus().getErrorMessages().size() > 0) ? userModelResponse.getStatus().getErrorMessages().get(0).getMessageDescription() : HapisApplication.getApplication().getResources().getString(R.string.unable_to_process_request)));
                    }
                }else{
                    mView.SignupRequestFailed(HapisApplication.getApplication().getResources().getString(R.string.unable_to_process_request));
                }
            }
        });
    }
}
