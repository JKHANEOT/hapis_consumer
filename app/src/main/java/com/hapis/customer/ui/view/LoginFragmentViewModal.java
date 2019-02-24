package com.hapis.customer.ui.view;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.hapis.customer.HapisApplication;
import com.hapis.customer.R;
import com.hapis.customer.database.repository.UserProfileRepository;
import com.hapis.customer.ui.models.CustomerResponse;
import com.hapis.customer.ui.models.HapisModel;
import com.hapis.customer.ui.models.ResponseStatus;
import com.hapis.customer.ui.models.UserModel;
import com.hapis.customer.ui.models.UserModelResponse;
import com.hapis.customer.ui.utils.AccessPreferences;
import com.hapis.customer.ui.utils.ApplicationConstants;
import com.hapis.customer.ui.utils.EditTextUtils;

public class LoginFragmentViewModal extends BaseViewModal<LoginFragmentView> {

    private String TAG = LoginFragmentViewModal.class.getName();

    private UserProfileRepository userProfileRepository;

    public LoginFragmentViewModal(LifecycleOwner owner) {
        super(owner);

        userProfileRepository = new UserProfileRepository();
    }

    @Override
    protected void initObservableData() {

    }

    @Override
    protected void handleChangedDataModal(HapisModel data) {

    }

    public void validateLoginDetails(String inputEmailIdOrMobileNumber, String password) {
        String msg = null;

        if (inputEmailIdOrMobileNumber.length() == 0) {
            msg = HapisApplication.getApplication().getResources().getString(R.string.login_label);
        } else if (inputEmailIdOrMobileNumber.length() > 0 && EditTextUtils.isNumeric(inputEmailIdOrMobileNumber) &&
                !(inputEmailIdOrMobileNumber.contains("@") || inputEmailIdOrMobileNumber.contains(".")) &&
                inputEmailIdOrMobileNumber.length() > HapisApplication.getApplication().getResources().getInteger(R.integer.MOBILE_NO_LENGTH)) {
            msg = HapisApplication.getApplication().getResources().getString(R.string.mobile_number_should_not_more_than_10);
        } else if (inputEmailIdOrMobileNumber.length() > 0 && EditTextUtils.isNumeric(inputEmailIdOrMobileNumber) &&
                !(inputEmailIdOrMobileNumber.contains("@") || inputEmailIdOrMobileNumber.contains("."))
                && inputEmailIdOrMobileNumber.startsWith("0")) {
            msg = HapisApplication.getApplication().getResources().getString(R.string.invalid_mobile_number_starts_with_zero);
        }else if (password.length() == 0) {
            msg = HapisApplication.getApplication().getResources().getString(R.string.please_enter_password);
        } else if (password.length() < 4) {
            msg = HapisApplication.getApplication().getResources().getString(R.string.password_should_be_of_length);
        }

        mView.validateScreenFields(msg);
    }

    MutableLiveData<UserModelResponse> mutableLiveData = new MutableLiveData<UserModelResponse>();

    public void doLogin(String userName, String password) {

        userProfileRepository.new LoginAsyncTask(mutableLiveData, userName, password).execute();
        mutableLiveData.observe(mOwner, new Observer<UserModelResponse>() {
            @Override
            public void onChanged(@Nullable UserModelResponse userModelResponse) {
                if(userModelResponse != null){
                    if(userModelResponse.getStatus() != null && userModelResponse.getStatus().getStatusCode() != null && userModelResponse.getStatus().getStatusCode().intValue() == ResponseStatus.SUCCESS){

                        MutableLiveData<CustomerResponse> customerResponseMutableLiveData = new MutableLiveData<>();
                        userProfileRepository.getUserDetailsByCustomerCode(customerResponseMutableLiveData, AccessPreferences.get(HapisApplication.getApplication(), ApplicationConstants.LOGGED_IN_USER_GUID, null));
                        customerResponseMutableLiveData.observe(mOwner, new Observer<CustomerResponse>() {
                            @Override
                            public void onChanged(@Nullable CustomerResponse customerResponse) {
                                mView.SigninRequestSuccess(HapisApplication.getApplication().getResources().getString(R.string.sign_in_successful));
                            }
                        });
                    }else{
                        mView.SigninRequestFailed(((userModelResponse.getStatus().getErrorMessages() != null && userModelResponse.getStatus().getErrorMessages().size() > 0) ? userModelResponse.getStatus().getErrorMessages().get(0).getMessageDescription() : HapisApplication.getApplication().getResources().getString(R.string.unable_to_process_request)));
                    }
                }else{
                    mView.SigninRequestFailed(HapisApplication.getApplication().getResources().getString(R.string.unable_to_process_request));
                }
            }
        });
    }
}
