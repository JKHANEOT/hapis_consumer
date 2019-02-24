package com.hapis.customer.ui.view;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.hapis.customer.HapisApplication;
import com.hapis.customer.R;
import com.hapis.customer.database.repository.UserProfileRepository;
import com.hapis.customer.database.tables.UserProfileTable;
import com.hapis.customer.ui.models.AddressModel;
import com.hapis.customer.ui.models.CustomerRequest;
import com.hapis.customer.ui.models.CustomerResponse;
import com.hapis.customer.ui.models.HapisModel;
import com.hapis.customer.ui.models.ResponseStatus;
import com.hapis.customer.ui.utils.AccessPreferences;
import com.hapis.customer.ui.utils.ApplicationConstants;
import com.hapis.customer.ui.utils.EditTextUtils;

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

    public void validateRegistrationDetails(String first_name, String last_name, int radioButtonId, String selectedMaritalStatus,
                                            String selectedNationality, String selectedReligion, String mobile_number, String emailId,
                                            String dob, String aadhaar_number, AddressModel addressModel) {
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
        }else if(EditTextUtils.isEmpty(aadhaar_number)){
            msg = HapisApplication.getApplication().getResources().getString(R.string.please_enter_aadhaar_number);
        }else if(addressModel == null){
            msg = HapisApplication.getApplication().getResources().getString(R.string.please_add_address);
        }

        mView.validateScreenFields(msg);
    }

    public void updateUserProfile(String selectedPrefix, String firstName, String middleName, String lastName, String gender, String aadharCardNumber, String selectedMaritalStatus, String selectedNationality, String selectedReligion,
                                  String mobileNumber, String emailAddress, String dateOfBirth, AddressModel visibleCurrentLocation) {

        CustomerRequest userModel = new CustomerRequest();
        userModel.setUserCode(AccessPreferences.get(HapisApplication.getApplication(), ApplicationConstants.LOGGED_IN_USER_GUID, null));

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

        userModel.setAddress(visibleCurrentLocation);

        userModel.setCustomerType(HapisApplication.getApplication().getResources().getInteger(R.integer.application_type));

        MutableLiveData<CustomerResponse> responseMutableLiveData = new MutableLiveData<>();

        userProfileRepository. new UpdateProfileAsyncTask(responseMutableLiveData, userModel).execute();
        responseMutableLiveData.observe(mOwner, new Observer<CustomerResponse>() {
            @Override
            public void onChanged(@Nullable CustomerResponse customerResponse) {

                if(customerResponse != null && customerResponse.getStatus() != null && customerResponse.getStatus().getStatusCode() != null && customerResponse.getStatus().getStatusCode().intValue() == ResponseStatus.SUCCESS)
                    mView.updateUserProfile(HapisApplication.getApplication().getResources().getString(R.string.update_profile_successful));
                else
                    mView.failedToProcess(HapisApplication.getApplication().getResources().getString(R.string.update_profile_failed));
            }
        });
    }

    public void loadUserProfileDetails() {

        MutableLiveData<CustomerResponse> mutableLiveData = new MutableLiveData<>();

        userProfileRepository.getUserDetailsByCustomerCode(mutableLiveData, AccessPreferences.get(HapisApplication.getApplication(), ApplicationConstants.LOGGED_IN_USER_GUID, null));

        mutableLiveData.observe(mOwner, new Observer<CustomerResponse>() {
            @Override
            public void onChanged(@Nullable CustomerResponse customerResponse) {
                MutableLiveData<UserProfileTable> userProfileTableMutableLiveData = new MutableLiveData<>();

                userProfileRepository.loadUserProfileDetails(userProfileTableMutableLiveData);
                userProfileTableMutableLiveData.observe(mOwner, new Observer<UserProfileTable>() {
                    @Override
                    public void onChanged(@Nullable UserProfileTable userProfileTable) {
                        if(userProfileTable != null && userProfileTable.getUniqueId() != null){
                            mView.loadUserProfileDetails(userProfileTable);
                        }
                    }
                });
            }
        });
    }

    public void loadUserAddress(String uniqueId) {
        MutableLiveData<AddressModel> addressTableMutableLiveData = new MutableLiveData<>();

        userProfileRepository. new GetUserAddressByUniqueId(addressTableMutableLiveData, uniqueId).execute();

        addressTableMutableLiveData.observe(mOwner, new Observer<AddressModel>() {
            @Override
            public void onChanged(@Nullable AddressModel addressModel) {
                if(addressModel != null)
                    mView.loadUserProfilAddressDetails(addressModel);
            }
        });
    }
}
