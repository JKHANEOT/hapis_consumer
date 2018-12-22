package com.hapis.customer.ui.view;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.hapis.customer.HapisApplication;
import com.hapis.customer.R;
import com.hapis.customer.database.repository.UserProfileRepository;
import com.hapis.customer.ui.models.AddressModel;
import com.hapis.customer.ui.models.HapisModel;
import com.hapis.customer.ui.models.ResponseStatus;
import com.hapis.customer.ui.models.UserModel;
import com.hapis.customer.ui.models.UserModelResponse;
import com.hapis.customer.ui.utils.ApplicationConstants;
import com.hapis.customer.ui.utils.EditTextUtils;

public class SignUpFragmentViewModal extends BaseViewModal<SignUpFragmentView> {

    private String TAG = SignUpFragmentViewModal.class.getName();

    private UserProfileRepository userProfileRepository;

    public SignUpFragmentViewModal(LifecycleOwner owner) {
        super(owner);

        userProfileRepository = new UserProfileRepository();
    }

    @Override
    protected void initObservableData() {

    }

    @Override
    protected void handleChangedDataModal(HapisModel data) {

    }

    public void validateRegistrationDetails(String first_name, String last_name, int radioButtonId, String selectedMaritalStatus,
                                            String selectedNationality, String selectedReligion, String mobile_number, String emailId,
                                            String dob, String password, String aadhaar_number, AddressModel addressModel) {
        String msg = null;

        if(EditTextUtils.isEmpty(first_name)){
            msg = HapisApplication.getApplication().getResources().getString(R.string.please_enter_first_name);
        }else if(EditTextUtils.isEmpty(last_name)){
            msg = HapisApplication.getApplication().getResources().getString(R.string.please_enter_last_name);
        }else if(radioButtonId == -1){
            msg = HapisApplication.getApplication().getResources().getString(R.string.please_choose_gender);
        }else if(selectedMaritalStatus == null){
            msg = HapisApplication.getApplication().getResources().getString(R.string.please_choose_marital_status);
        }else if(selectedNationality == null){
            msg = HapisApplication.getApplication().getResources().getString(R.string.please_choose_nationality);
        }else if(selectedReligion == null){
            msg = HapisApplication.getApplication().getResources().getString(R.string.please_choose_religion);
        }else if(EditTextUtils.isEmpty(mobile_number)){
            msg = HapisApplication.getApplication().getResources().getString(R.string.please_enter_mobile_number);
        }else if(EditTextUtils.isEmpty(emailId)){
            msg = HapisApplication.getApplication().getResources().getString(R.string.please_enter_email_id);
        }else if(!EditTextUtils.isValidEmail(emailId)){
            msg = HapisApplication.getApplication().getResources().getString(R.string.please_enter_valid_email_id);
        }else if(EditTextUtils.isEmpty(dob)){
            msg = HapisApplication.getApplication().getResources().getString(R.string.please_select_date_of_birth);
        }else if(EditTextUtils.isEmpty(password)){
            msg = HapisApplication.getApplication().getResources().getString(R.string.please_enter_password);
        }else if(EditTextUtils.isEmpty(aadhaar_number)){
            msg = HapisApplication.getApplication().getResources().getString(R.string.please_enter_aadhaar_number);
        }else if(addressModel == null){
            msg = HapisApplication.getApplication().getResources().getString(R.string.please_add_address);
        }

        mView.validateScreenFields(msg);
    }

    MutableLiveData<UserModelResponse> mutableLiveData = new MutableLiveData<UserModelResponse>();

    public void doSignup(String selectedPrefix, String firstName, String middleName, String lastName, String gender, String aadharCardNumber, String selectedMaritalStatus, String selectedNationality, String selectedReligion,
                         String mobileNumber, String emailAddress, String dateOfBirth, String password, String externalReference, AddressModel visibleCurrentLocation) {

        UserModel userModel = new UserModel();

        userModel.setAgentCode("NotDefined");
        userModel.setNamePrefix(selectedPrefix);
        userModel.setFirstName(firstName);
        userModel.setMiddleName(middleName);
        userModel.setLastName(lastName);
        userModel.setGenderCode(gender);
        userModel.setAadhaarNumber(aadharCardNumber);
        userModel.setMaritalStatus(selectedMaritalStatus);
        userModel.setNationality(selectedNationality);
        userModel.setReligionCode(selectedReligion);
        userModel.setMobileNumber(mobileNumber);
        userModel.setEmailAddress(emailAddress);
        userModel.setDateOfBirth(dateOfBirth);
        userModel.setPassword(password);
        userModel.setExternalReference(externalReference);

        userModel.setAddress(visibleCurrentLocation);

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
