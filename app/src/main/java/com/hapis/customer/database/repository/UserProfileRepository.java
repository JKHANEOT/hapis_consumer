package com.hapis.customer.database.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.hapis.customer.HapisApplication;
import com.hapis.customer.R;
import com.hapis.customer.database.HapisDatabase;
import com.hapis.customer.database.daos.AddressDao;
import com.hapis.customer.database.daos.ApplicationProfileDao;
import com.hapis.customer.database.daos.UserProfileDao;
import com.hapis.customer.database.tables.AddressTable;
import com.hapis.customer.database.tables.ApplicationProfileTable;
import com.hapis.customer.database.tables.UserProfileTable;
import com.hapis.customer.exception.HapisException;
import com.hapis.customer.logger.LOG;
import com.hapis.customer.networking.RestCall;
import com.hapis.customer.networking.json.JSONAdaptor;
import com.hapis.customer.networking.util.RestConstants;
import com.hapis.customer.ui.models.AddressModel;
import com.hapis.customer.ui.models.CustomerRequest;
import com.hapis.customer.ui.models.CustomerResponse;
import com.hapis.customer.ui.models.ErrorMessage;
import com.hapis.customer.ui.models.ResponseStatus;
import com.hapis.customer.ui.models.UserModel;
import com.hapis.customer.ui.models.UserModelResponse;
import com.hapis.customer.ui.utils.AccessPreferences;
import com.hapis.customer.ui.utils.ApplicationConstants;
import com.hapis.customer.utils.DateUtil;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

public class UserProfileRepository {

    private final String TAG = UserProfileRepository.class.getName();

    private UserProfileDao userProfileDao;
    private ApplicationProfileDao applicationProfileDao;
    private AddressDao addressDao;

    public UserProfileRepository(){
        HapisDatabase database = HapisDatabase.getInstance(HapisApplication.getApplication());
        userProfileDao = database.getUserProfileDao();
        applicationProfileDao = database.getApplicationProfileDao();
        addressDao = database.getAddressDao();
    }

    public void insert(UserProfileTable userProfile){
        new InsertUserProfileAsyncTask(userProfileDao).execute(userProfile);
    }

    public void update(UserProfileTable userProfile){
        new UpdateUserProfileAsyncTask(userProfileDao).execute(userProfile);
    }

    public void delete(UserProfileTable userProfile){
        new DeleteUserProfileAsyncTask(userProfileDao).execute(userProfile);
    }

    public void deleteAllUserProfiles(){
        new DeleteAllUserProfileAsyncTask(userProfileDao).execute();
    }

    public void getAppProfileStatus(MutableLiveData<Integer> appStatus) {
        new GetAppProfileStatusAsyncTask(appStatus, applicationProfileDao).execute();
    }

    private static class GetAppProfileStatusAsyncTask extends AsyncTask<Void, Void, Integer> {

        private ApplicationProfileDao applicationProfileDao;
        private MutableLiveData<Integer> integerMutableLiveData;

        private GetAppProfileStatusAsyncTask(MutableLiveData<Integer> appStatus, ApplicationProfileDao userProfileDao){
            this.applicationProfileDao = userProfileDao;
            integerMutableLiveData = appStatus;
        }

        @Override
        protected Integer doInBackground(Void... notes) {

            Integer appStatus = applicationProfileDao.getAppProfileStatus();

            return appStatus;
        }

        @Override
        protected void onPostExecute(Integer appStatus) {
            super.onPostExecute(appStatus);

            if(appStatus == null || appStatus == 0){
                integerMutableLiveData.postValue(new Integer(ApplicationConstants.USER_NEW));
            }else{
                integerMutableLiveData.postValue(new Integer(appStatus));
            }
        }
    }

    public UserProfileTable getUserProfileByMobileNumber(String mobileNumber, String password) {
        return userProfileDao.getUserProfileByMobileNumber(mobileNumber, password);
    }

    public LiveData<UserProfileTable> getUserProfileByEmail(String email, String password) {
        return userProfileDao.getUserProfileByEmail(email, password);
    }

    private static class InsertUserProfileAsyncTask extends AsyncTask<UserProfileTable, Void, Void> {

        private UserProfileDao userProfileDao;

        private InsertUserProfileAsyncTask(UserProfileDao userProfileDao){
            this.userProfileDao = userProfileDao;
        }

        @Override
        protected Void doInBackground(UserProfileTable... notes) {

            userProfileDao.insert(notes[0]);

            return null;
        }
    }

    private static class UpdateUserProfileAsyncTask extends AsyncTask<UserProfileTable, Void, Void> {

        private UserProfileDao userProfileDao;

        private UpdateUserProfileAsyncTask(UserProfileDao userProfileDao){
            this.userProfileDao = userProfileDao;
        }

        @Override
        protected Void doInBackground(UserProfileTable... notes) {

            userProfileDao.update(notes[0]);

            return null;
        }
    }

    private static class DeleteUserProfileAsyncTask extends AsyncTask<UserProfileTable, Void, Void> {

        private UserProfileDao userProfileDao;

        private DeleteUserProfileAsyncTask(UserProfileDao userProfileDao){
            this.userProfileDao = userProfileDao;
        }

        @Override
        protected Void doInBackground(UserProfileTable... notes) {

            userProfileDao.delete(notes[0]);

            return null;
        }
    }

    private static class DeleteAllUserProfileAsyncTask extends AsyncTask<Void, Void, Void> {

        private UserProfileDao userProfileDao;

        private DeleteAllUserProfileAsyncTask(UserProfileDao userProfileDao){
            this.userProfileDao = userProfileDao;
        }

        @Override
        protected Void doInBackground(Void... notes) {

            userProfileDao.deleteAllUserProfile();

            return null;
        }
    }

    public class LoginAsyncTask extends AsyncTask<Void, Void, UserProfileTable> {

        private MutableLiveData<UserModelResponse> mutableLiveData;
        private String userName;
        private String password;

        public LoginAsyncTask(final MutableLiveData<UserModelResponse> mutableLiveData, final String userName, final String password){
            this.mutableLiveData = mutableLiveData;
            this.userName = userName;
            this.password = password;
        }

        @Override
        protected UserProfileTable doInBackground(Void... voids) {

            UserProfileTable userProfileTable = userProfileDao.getUserProfileByMobileNumber(userName, password);

            /*if(userProfileTable == null) {
                userProfileTable = new UserProfileTable();

                userProfileTable.setUniqueId("CT000014");
                userProfileTable.setState(901);
            }*/

            return userProfileTable;
        }

        @Override
        protected void onPostExecute(UserProfileTable userProfileTable) {
            super.onPostExecute(userProfileTable);

            doLogin(mutableLiveData, userName, password, userProfileTable);
        }
    }

    public class UpdateProfileAsyncTask extends AsyncTask<Void, Void, UserProfileTable> {

        private MutableLiveData<CustomerResponse> mutableLiveData;
        private CustomerRequest customerRequest;

        public UpdateProfileAsyncTask(final MutableLiveData<CustomerResponse> mutableLiveData, CustomerRequest customerRequest){
            this.mutableLiveData = mutableLiveData;
            this.customerRequest = customerRequest;
        }

        @Override
        protected UserProfileTable doInBackground(Void... voids) {

            UserProfileTable userProfileTable = userProfileDao.getUserProfileByUniqueId(AccessPreferences.get(HapisApplication.getApplication(), ApplicationConstants.LOGGED_IN_USER_GUID, null));

            if(userProfileTable == null) {
                ResponseStatus responseStatus = new ResponseStatus();
                responseStatus.setStatusCode(ResponseStatus.FAILED);

                CustomerResponse userModelResponse = new CustomerResponse();
                userModelResponse.setStatus(responseStatus);

                mutableLiveData.postValue(userModelResponse);
            }else{
                customerRequest.setPassword(userProfileTable.getPassword());
            }

            return userProfileTable;
        }

        @Override
        protected void onPostExecute(UserProfileTable userProfileTable) {
            super.onPostExecute(userProfileTable);

            updateUserDetails(mutableLiveData, customerRequest);
        }
    }

    public void doLogin(final MutableLiveData<UserModelResponse> mutableLiveData, final String userName, final String password, final UserProfileTable userProfileTable) {

        UserModel userModel = new UserModel();
        userModel.setMobileNo(userName);
        userModel.setPassword(password);

        userModel.setCustomerType(HapisApplication.getApplication().getResources().getInteger(R.integer.application_type));

        RestCall restCall = new RestCall();
        restCall.setOnRestCallListener(new RestCall.RestCallListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                ResponseStatus responseStatus = new ResponseStatus();
                responseStatus.setStatusCode(ResponseStatus.FAILED);

                UserModelResponse userModelResponse = new UserModelResponse();
                userModelResponse.setStatus(responseStatus);

                mutableLiveData.postValue(userModelResponse);
            }

            @Override
            public void onResponse(RestCall.Result result, String response, List<ErrorMessage> errorMessages, String msg) {
                if (result == RestCall.Result.FAILED || result == RestCall.Result.EXCEPTION) {
                    ResponseStatus responseStatus = new ResponseStatus();
                    responseStatus.setStatusCode(ResponseStatus.FAILED);

                    UserModelResponse userModelResponse = new UserModelResponse();
                    userModelResponse.setStatus(responseStatus);

                    mutableLiveData.postValue(userModelResponse);
                } else {
                    UserModelResponse userModelResponse = null;
                    try {
                        userModelResponse = JSONAdaptor.fromJSON(response, UserModelResponse.class);
                        if(userModelResponse != null && userModelResponse.getCustomerCode() != null && userModelResponse.getCustomerCode().length() > 0){
                            ApplicationProfileTable applicationProfileTable = null;
                            List<ApplicationProfileTable> applicationProfileTables = applicationProfileDao.getAllApplicationProfileWithout();
                            if(applicationProfileTables != null && applicationProfileTables.size() > 0){
                                applicationProfileTable = applicationProfileTables.get(0);
                                applicationProfileTable.setAppStatus(ApplicationConstants.USER_LOGGED_IN);
                                applicationProfileDao.update(applicationProfileTable);
                            }else{
                                applicationProfileTable = new ApplicationProfileTable();
                                applicationProfileTable.setAppStatus(ApplicationConstants.USER_LOGGED_IN);
                                applicationProfileDao.insert(applicationProfileTable);
                            }

                            AccessPreferences.put(HapisApplication.getApplication(), ApplicationConstants.LOGGED_IN_USER_GUID, userModelResponse.getCustomerCode());

                            if(userProfileTable != null && userProfileTable.getUniqueId() != null && userProfileTable.getUniqueId().equals(userModelResponse.getCustomerCode())){
                                userProfileTable.setLastLoginDate(new Date().getTime());
                                if(userProfileDao.getNumberOfRows(userProfileTable.getUniqueId()) > 0)
                                    userProfileDao.update(userProfileTable);
                            }else{
                                UserProfileTable userProfileTable = new UserProfileTable();
                                userProfileTable.setUniqueId(userModelResponse.getCustomerCode());
                                userProfileTable.setLastLoginDate(new Date().getTime());
                                userProfileDao.insert(userProfileTable);
                            }

                            ResponseStatus responseStatus = new ResponseStatus();
                            responseStatus.setStatusCode(ResponseStatus.SUCCESS);
                            userModelResponse.setStatus(responseStatus);

                            mutableLiveData.postValue(userModelResponse);
                        }else{
                            ResponseStatus responseStatus = new ResponseStatus();
                            responseStatus.setStatusCode(ResponseStatus.FAILED);

                            userModelResponse = new UserModelResponse();
                            userModelResponse.setStatus(responseStatus);

                            mutableLiveData.postValue(userModelResponse);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ResponseStatus responseStatus = new ResponseStatus();
                        responseStatus.setStatusCode(ResponseStatus.FAILED);

                        userModelResponse = new UserModelResponse();
                        userModelResponse.setStatus(responseStatus);

                        mutableLiveData.postValue(userModelResponse);
                    }
                }
            }
        });
        try {
            restCall.post(null, false, "Loading items",
                    HapisApplication.getApplication().getBackendUrl()+"9000"  + RestConstants.LOGIN_URL,
                    JSONAdaptor.toJSON(userModel));
        } catch (IOException e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            UserModelResponse userModelResponse = new UserModelResponse();
            userModelResponse.setStatus(responseStatus);

            mutableLiveData.postValue(userModelResponse);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            UserModelResponse userModelResponse = new UserModelResponse();
            userModelResponse.setStatus(responseStatus);

            mutableLiveData.postValue(userModelResponse);
        }
    }

    public void doSignup(final MutableLiveData<UserModelResponse> mutableLiveData, final UserModel userModel) {

        RestCall restCall = new RestCall();
        restCall.setOnRestCallListener(new RestCall.RestCallListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                ResponseStatus responseStatus = new ResponseStatus();
                responseStatus.setStatusCode(ResponseStatus.FAILED);

                UserModelResponse userModelResponse = new UserModelResponse();
                userModelResponse.setStatus(responseStatus);

                mutableLiveData.postValue(userModelResponse);
            }

            @Override
            public void onResponse(RestCall.Result result, final String response, List<ErrorMessage> errorMessages, String msg) {
                try {
                    if (result == RestCall.Result.FAILED || result == RestCall.Result.EXCEPTION) {
                        UserModelResponse userModelResponse = JSONAdaptor.fromJSON(response, UserModelResponse.class);
                        if(userModelResponse != null && userModelResponse.getMessage() != null){
                            mutableLiveData.postValue(userModelResponse);
                        }else{
                            ResponseStatus responseStatus = new ResponseStatus();
                            responseStatus.setStatusCode(ResponseStatus.FAILED);

                            userModelResponse = new UserModelResponse();
                            userModelResponse.setStatus(responseStatus);

                            mutableLiveData.postValue(userModelResponse);
                        }
                    } else {
                        LOG.d(TAG, "" + response);

                        if(response != null && response.length() > 0){
                            UserModelResponse userModelResponse = JSONAdaptor.fromJSON(response, UserModelResponse.class);
                            if(userModelResponse != null && userModelResponse.getMessage() != null){
                                UserModel signedUpResponse = userModelResponse.getMessage();
                                if(signedUpResponse != null){

                                    ApplicationProfileTable applicationProfileTable = new ApplicationProfileTable();
                                    applicationProfileTable.setAppStatus(ApplicationConstants.USER_SIGNED_UP);

                                    applicationProfileDao.insert(applicationProfileTable);

                                    UserProfileTable userProfileTable = new UserProfileTable();

                                    if(signedUpResponse.getCustomerCode() != null)
                                        userProfileTable.setUniqueId(signedUpResponse.getCustomerCode());
                                    if(signedUpResponse.getNamePrefix() != null)
                                        userProfileTable.setTitle(userModel.getNamePrefix());
                                    if(signedUpResponse.getFirstName() != null)
                                        userProfileTable.setFirstName(userModel.getFirstName());
                                    if(signedUpResponse.getMiddleName() != null)
                                        userProfileTable.setMiddleName(userModel.getMiddleName());
                                    if(signedUpResponse.getLastName() != null)
                                        userProfileTable.setLastName(userModel.getLastName());
                                    if(signedUpResponse.getGenderCode() != null)
                                        userProfileTable.setGender(userModel.getGenderCode());
                                    if(signedUpResponse.getAadhaarNumber() != null)
                                        userProfileTable.setAadhaarNumber(userModel.getAadhaarNumber());
                                    if(signedUpResponse.getMaritalStatus() != null)
                                        userProfileTable.setMaritalStatus(userModel.getMaritalStatus());
                                    if(signedUpResponse.getNationality() != null)
                                        userProfileTable.setNationality(userModel.getNationality());
                                    if(signedUpResponse.getReligionCode() != null)
                                        userProfileTable.setReligion(userModel.getReligionCode());
                                    if(signedUpResponse.getMobileNumber() != null)
                                        userProfileTable.setMobileNumber(userModel.getMobileNumber());
                                    if(signedUpResponse.getEmailAddress() != null)
                                        userProfileTable.setEmail(userModel.getEmailAddress());
                                    if(signedUpResponse.getPassword() != null)
                                        userProfileTable.setPassword(userModel.getPassword());
                                    if(signedUpResponse.getDateOfBirth() != null)
                                        userProfileTable.setDateOfBirth(DateUtil.getDateTimeInMillis(userModel.getDateOfBirth(), DateUtil.DATE_FORMAT_yyyy_MM_dd_T_HH_mm_ss_SSS_Z));

                                    userProfileTable.setProfileUpdatedDate(new Date().getTime());

                                    if(signedUpResponse.getAgentCode() != null)
                                        userProfileTable.setAgentCode(signedUpResponse.getAgentCode());
                                    if(signedUpResponse.getCustomerType() != null)
                                        userProfileTable.setCustomerType(signedUpResponse.getCustomerType());
                                    if(signedUpResponse.getState() != null)
                                        userProfileTable.setState(signedUpResponse.getState());

                                    userProfileDao.insert(userProfileTable);

                                    if(userModel.getAddress() != null){
                                        AddressTable addressTable = new AddressTable();

                                        addressTable.setFk_uniqueId(signedUpResponse.getCustomerCode());

                                        addressTable.setAddress(userModel.getAddress().getAddressLine1());
                                        addressTable.setLandmark(userModel.getAddress().getAddressLine2());
                                        addressTable.setCity(userModel.getAddress().getCity());
                                        addressTable.setState(userModel.getAddress().getStateCode());
                                        addressTable.setCountry(userModel.getAddress().getCountry());
                                        addressTable.setPinCode(userModel.getAddress().getPinCode());
                                        addressTable.setAddressType(userModel.getAddress().getAddressType());

                                        addressDao.insert(addressTable);
                                    }
                                }

                                mutableLiveData.postValue(userModelResponse);
                            }else{
                                ResponseStatus responseStatus = new ResponseStatus();
                                responseStatus.setStatusCode(ResponseStatus.FAILED);

                                userModelResponse = new UserModelResponse();
                                userModelResponse.setStatus(responseStatus);

                                mutableLiveData.postValue(userModelResponse);
                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ResponseStatus responseStatus = new ResponseStatus();
                    responseStatus.setStatusCode(ResponseStatus.FAILED);

                    UserModelResponse userModelResponse = new UserModelResponse();
                    userModelResponse.setStatus(responseStatus);

                    mutableLiveData.postValue(userModelResponse);
                }
            }
        });
        try {
            restCall.post(HapisApplication.getApplication(), false, "Loading items",
                    HapisApplication.getApplication().getBackendUrl()+"9000" + RestConstants.REGISTER_USER_REQUEST_URL,
                    JSONAdaptor.toJSON(userModel));
        } catch (IOException e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            UserModelResponse userModelResponse = new UserModelResponse();
            userModelResponse.setStatus(responseStatus);

            mutableLiveData.postValue(userModelResponse);
        } catch (HapisException e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            UserModelResponse userModelResponse = new UserModelResponse();
            userModelResponse.setStatus(responseStatus);

            mutableLiveData.postValue(userModelResponse);
        }catch (Exception e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            UserModelResponse userModelResponse = new UserModelResponse();
            userModelResponse.setStatus(responseStatus);

            mutableLiveData.postValue(userModelResponse);
        }
    }

    public class LogoutAsyncTask extends AsyncTask<Void, Void, Boolean> {

        private MutableLiveData<Boolean> mutableLiveData;

        public LogoutAsyncTask(final MutableLiveData<Boolean> mutableLiveData){
            this.mutableLiveData = mutableLiveData;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            Boolean aBoolean = new Boolean(false);

            ApplicationProfileTable applicationProfileTable = null;
            List<ApplicationProfileTable> applicationProfileTables = applicationProfileDao.getAllApplicationProfileWithout();
            if(applicationProfileTables != null && applicationProfileTables.size() > 0){
                applicationProfileTable = applicationProfileTables.get(0);
                applicationProfileTable.setAppStatus(ApplicationConstants.USER_LOGGED_OUT);
                applicationProfileDao.update(applicationProfileTable);

                AccessPreferences.put(HapisApplication.getApplication(), ApplicationConstants.LOGGED_IN_USER_GUID, "");
                aBoolean = new Boolean(true);
                HapisDatabase database = HapisDatabase.getInstance(HapisApplication.getApplication());
                database.clearAllTables();
            }

            return aBoolean;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            mutableLiveData.postValue(aBoolean);
        }
    }

    public void loadUserProfileDetails(MutableLiveData<UserProfileTable> userProfileTableMutableLiveData) {
        new GetUserProfileAsyncTask(userProfileTableMutableLiveData, userProfileDao).execute();
    }

    private static class GetUserProfileAsyncTask extends AsyncTask<Void, Void, UserProfileTable> {

        private MutableLiveData<UserProfileTable> mUserProfileMutableLiveData;
        private UserProfileDao mUserProfileDao;

        private GetUserProfileAsyncTask(MutableLiveData<UserProfileTable> userProfileMutableLiveData, UserProfileDao userProfileDao){
            mUserProfileDao = userProfileDao;
            mUserProfileMutableLiveData = userProfileMutableLiveData;
        }

        @Override
        protected UserProfileTable doInBackground(Void... notes) {

            UserProfileTable userProfileTable = mUserProfileDao.getUserProfileByUniqueId(AccessPreferences.get(HapisApplication.getApplication(), ApplicationConstants.LOGGED_IN_USER_GUID, null));

            return userProfileTable;
        }

        @Override
        protected void onPostExecute(UserProfileTable userProfileTable) {
            super.onPostExecute(userProfileTable);

            if(userProfileTable != null){
                mUserProfileMutableLiveData.postValue(userProfileTable);
            }
        }
    }

    public void getUserDetailsByCustomerCode(final MutableLiveData<CustomerResponse> mutableLiveData, String customerCode) {

        RestCall restCall = new RestCall();
        restCall.setOnRestCallListener(new RestCall.RestCallListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                ResponseStatus responseStatus = new ResponseStatus();
                responseStatus.setStatusCode(ResponseStatus.FAILED);

                CustomerResponse userModelResponse = new CustomerResponse();
                userModelResponse.setStatus(responseStatus);

                mutableLiveData.postValue(userModelResponse);
            }

            @Override
            public void onResponse(RestCall.Result result, String response, List<ErrorMessage> errorMessages, String msg) {
                if (result == RestCall.Result.FAILED || result == RestCall.Result.EXCEPTION) {
                    ResponseStatus responseStatus = new ResponseStatus();
                    responseStatus.setStatusCode(ResponseStatus.FAILED);

                    CustomerResponse userModelResponse = new CustomerResponse();
                    userModelResponse.setStatus(responseStatus);

                    mutableLiveData.postValue(userModelResponse);
                } else {
                    try {
                        if(response != null && response.length() > 0) {
                            CustomerResponse customResponseModel = JSONAdaptor.fromJSON(response, CustomerResponse.class);
                            if (customResponseModel != null) {

                                storeOrUpdateUserDetails(customResponseModel.getMessage());

                                mutableLiveData.postValue(customResponseModel);
                            }else{
                                ResponseStatus responseStatus = new ResponseStatus();
                                responseStatus.setStatusCode(ResponseStatus.FAILED);

                                CustomerResponse userModelResponse = new CustomerResponse();
                                userModelResponse.setStatus(responseStatus);

                                mutableLiveData.postValue(userModelResponse);
                            }
                        }else{
                            ResponseStatus responseStatus = new ResponseStatus();
                            responseStatus.setStatusCode(ResponseStatus.FAILED);

                            CustomerResponse userModelResponse = new CustomerResponse();
                            userModelResponse.setStatus(responseStatus);

                            mutableLiveData.postValue(userModelResponse);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        ResponseStatus responseStatus = new ResponseStatus();
                        responseStatus.setStatusCode(ResponseStatus.FAILED);

                        CustomerResponse userModelResponse = new CustomerResponse();
                        userModelResponse.setStatus(responseStatus);

                        mutableLiveData.postValue(userModelResponse);
                    }
                }
            }
        });
        try {
            restCall.get(null, false, "Loading items",
                    HapisApplication.getApplication().getBackendUrl()+"9000"  + RestConstants.GET_CUSTOMER_URL+customerCode);
        } catch (IOException e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            CustomerResponse userModelResponse = new CustomerResponse();
            userModelResponse.setStatus(responseStatus);

            mutableLiveData.postValue(userModelResponse);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            CustomerResponse userModelResponse = new CustomerResponse();
            userModelResponse.setStatus(responseStatus);

            mutableLiveData.postValue(userModelResponse);
        }

    }

    private void storeOrUpdateUserDetails(CustomerRequest customerRequest) {
        UserProfileTable userProfileTable = new UserProfileTable();

        if(customerRequest.getUserCode() != null)
            userProfileTable.setUniqueId(customerRequest.getUserCode());
        if(customerRequest.getCustomerCode() != null)
            userProfileTable.setUniqueId(customerRequest.getCustomerCode());
        if(customerRequest.getNamePrefix() != null)
            userProfileTable.setTitle(customerRequest.getNamePrefix());
        if(customerRequest.getFirstName() != null)
            userProfileTable.setFirstName(customerRequest.getFirstName());
        if(customerRequest.getMiddleName() != null)
            userProfileTable.setMiddleName(customerRequest.getMiddleName());
        if(customerRequest.getLastName() != null)
            userProfileTable.setLastName(customerRequest.getLastName());
        if(customerRequest.getGenderCode() != null)
            userProfileTable.setGender(customerRequest.getGenderCode());
        if(customerRequest.getAadhaarNumber() != null)
            userProfileTable.setAadhaarNumber(customerRequest.getAadhaarNumber());
        if(customerRequest.getMaritalStatus() != null)
            userProfileTable.setMaritalStatus(customerRequest.getMaritalStatus());
        if(customerRequest.getNationality() != null)
            userProfileTable.setNationality(customerRequest.getNationality());
        if(customerRequest.getReligionCode() != null)
            userProfileTable.setReligion(customerRequest.getReligionCode());
        if(customerRequest.getMobileNumber() != null)
            userProfileTable.setMobileNumber(customerRequest.getMobileNumber());
        if(customerRequest.getEmailAddress() != null)
            userProfileTable.setEmail(customerRequest.getEmailAddress());
        if(customerRequest.getPassword() != null)
            userProfileTable.setPassword(customerRequest.getPassword());
        try {
            if (customerRequest.getDateOfBirth() != null)
                userProfileTable.setDateOfBirth(Long.parseLong(customerRequest.getDateOfBirth())/*DateUtil.getDateTimeInMillis(customerRequest.getDateOfBirth(), DateUtil.DATE_FORMAT_yyyy_MM_dd_T_HH_mm_ss_SSS_Z)*/);
        }catch (Exception e){
            e.printStackTrace();
        }

        userProfileTable.setProfileUpdatedDate(new Date().getTime());

        if(customerRequest.getAgentCode() != null)
            userProfileTable.setAgentCode(customerRequest.getAgentCode());
        if(customerRequest.getCustomerType() != null)
            userProfileTable.setCustomerType(customerRequest.getCustomerType());
        if(customerRequest.getState() != null)
            userProfileTable.setState(customerRequest.getState());

        userProfileDao.update(userProfileTable);

        if(customerRequest.getAddress() != null){
            AddressTable addressTable = new AddressTable();

            String fk_uniqueId = null;

            if(customerRequest.getUserCode() != null)
                fk_uniqueId = customerRequest.getUserCode();
            if(customerRequest.getCustomerCode() != null)
                fk_uniqueId = customerRequest.getCustomerCode();

            addressTable.setFk_uniqueId(fk_uniqueId);

            addressTable.setAddress(customerRequest.getAddress().getAddressLine1());
            addressTable.setLandmark(customerRequest.getAddress().getAddressLine2());
            addressTable.setCity(customerRequest.getAddress().getCity());
            addressTable.setState(customerRequest.getAddress().getStateCode());
            addressTable.setCountry(customerRequest.getAddress().getCountry());
            addressTable.setPinCode(customerRequest.getAddress().getPinCode());
//            addressTable.setAddressType(customerRequest.getAddress().getAddressType());

            if (addressDao.getAddressByUniqueIdWithout(fk_uniqueId) != null)
                addressDao.update(addressTable);
            else
                addressDao.insert(addressTable);
        }
    }

    public void updateUserDetails(final MutableLiveData<CustomerResponse> mutableLiveData, CustomerRequest customerRequest) {

        RestCall restCall = new RestCall();
        restCall.setOnRestCallListener(new RestCall.RestCallListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                ResponseStatus responseStatus = new ResponseStatus();
                responseStatus.setStatusCode(ResponseStatus.FAILED);

                CustomerResponse userModelResponse = new CustomerResponse();
                userModelResponse.setStatus(responseStatus);

                mutableLiveData.postValue(userModelResponse);
            }

            @Override
            public void onResponse(RestCall.Result result, String response, List<ErrorMessage> errorMessages, String msg) {
                if (result == RestCall.Result.FAILED || result == RestCall.Result.EXCEPTION) {
                    ResponseStatus responseStatus = new ResponseStatus();
                    responseStatus.setStatusCode(ResponseStatus.FAILED);

                    CustomerResponse userModelResponse = new CustomerResponse();
                    userModelResponse.setStatus(responseStatus);

                    mutableLiveData.postValue(userModelResponse);
                } else {
                    try {
                        if(response != null && response.length() > 0) {
                            CustomerResponse customerResponse = JSONAdaptor.fromJSON(response, CustomerResponse.class);
                            if (customerResponse != null) {

                                storeOrUpdateUserDetails(customerResponse.getMessage());

                                mutableLiveData.postValue(customerResponse);
                            }else{
                                ResponseStatus responseStatus = new ResponseStatus();
                                responseStatus.setStatusCode(ResponseStatus.FAILED);

                                CustomerResponse userModelResponse = new CustomerResponse();
                                userModelResponse.setStatus(responseStatus);

                                mutableLiveData.postValue(userModelResponse);
                            }
                        }else{
                            ResponseStatus responseStatus = new ResponseStatus();
                            responseStatus.setStatusCode(ResponseStatus.FAILED);

                            CustomerResponse userModelResponse = new CustomerResponse();
                            userModelResponse.setStatus(responseStatus);

                            mutableLiveData.postValue(userModelResponse);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        ResponseStatus responseStatus = new ResponseStatus();
                        responseStatus.setStatusCode(ResponseStatus.FAILED);

                        CustomerResponse userModelResponse = new CustomerResponse();
                        userModelResponse.setStatus(responseStatus);

                        mutableLiveData.postValue(userModelResponse);
                    }
                }
            }
        });
        try {
            restCall.put(null, false, "Loading items",
                    HapisApplication.getApplication().getBackendUrl()+"9000" + RestConstants.UPDATE_USER_URL+customerRequest.getUserCode(), JSONAdaptor.toJSON(customerRequest));
        } catch (IOException e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            CustomerResponse userModelResponse = new CustomerResponse();
            userModelResponse.setStatus(responseStatus);

            mutableLiveData.postValue(userModelResponse);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            CustomerResponse userModelResponse = new CustomerResponse();
            userModelResponse.setStatus(responseStatus);

            mutableLiveData.postValue(userModelResponse);
        }
    }

    public class GetUserAddressByUniqueId extends AsyncTask<Void, Void, AddressModel> {

        private MutableLiveData<AddressModel> mutableLiveData;
        private String uniqueId;

        public GetUserAddressByUniqueId(final MutableLiveData<AddressModel> mutableLiveData, String uniqueId){
            this.mutableLiveData = mutableLiveData;
            this.uniqueId = uniqueId;
        }

        @Override
        protected AddressModel doInBackground(Void... voids) {

            AddressModel addressModel = null;

            AddressTable addressTable = addressDao.getAddressByUniqueIdWithout(uniqueId);
            if(addressTable != null){
                addressModel = new AddressModel();

                if(addressTable.getAddress() != null)
                    addressModel.setAddressLine1(addressTable.getAddress());

                if(addressTable.getLandmark() != null)
                    addressModel.setAddressLine2(addressTable.getLandmark());

                if(addressTable.getCity() != null)
                    addressModel.setCity(addressTable.getCity());

                if(addressTable.getState() != null)
                    addressModel.setStateCode(addressTable.getState());

                if(addressTable.getPinCode() != null)
                    addressModel.setPinCode(addressTable.getPinCode());

                if(addressTable.getCountry() != null)
                    addressModel.setCountry(addressTable.getCountry());
            }

            return addressModel;
        }

        @Override
        protected void onPostExecute(AddressModel addressModel) {
            super.onPostExecute(addressModel);

            mutableLiveData.postValue(addressModel);
        }
    }
}
