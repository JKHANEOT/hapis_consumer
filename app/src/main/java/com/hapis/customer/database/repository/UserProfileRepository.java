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
import com.hapis.customer.ui.models.ErrorMessage;
import com.hapis.customer.ui.models.ResponseStatus;
import com.hapis.customer.ui.models.UserModel;
import com.hapis.customer.ui.models.UserModelResponse;
import com.hapis.customer.ui.utils.AccessPreferences;
import com.hapis.customer.ui.utils.ApplicationConstants;
import com.hapis.customer.utils.DateUtil;

import java.io.IOException;
import java.util.ArrayList;
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

            if(userProfileTable == null) {
                userProfileTable = new UserProfileTable();

                userProfileTable.setUniqueId("CT000014");
                userProfileTable.setState(901);
            }

            return userProfileTable;
        }

        @Override
        protected void onPostExecute(UserProfileTable userProfileTable) {
            super.onPostExecute(userProfileTable);

            doLogin(mutableLiveData, userName, password, userProfileTable);
        }
    }

    public void doLogin(final MutableLiveData<UserModelResponse> mutableLiveData, final String userName, final String password, UserProfileTable userProfileTable) {

        if(userProfileTable != null){
            UserModel userModel = new UserModel();
//            if(userProfileTable.getAgentCode() != null)
//                userModel.setAgentCode(userProfileTable.getAgentCode());
//            userModel.setCustomerCode(userProfileTable.getUniqueId());
//            userModel.setState(userProfileTable.getState());

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
                        try {
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

                            AccessPreferences.put(HapisApplication.getApplication(), ApplicationConstants.LOGGED_IN_USER_GUID, userProfileTable.getUniqueId());

                            userProfileTable.setLastLoginDate(new Date().getTime());
                            if(userProfileDao.getNumberOfRows(userProfileTable.getUniqueId()) > 0)
                                userProfileDao.update(userProfileTable);
                            else
                                userProfileDao.insert(userProfileTable);

                            ResponseStatus responseStatus = new ResponseStatus();
                            responseStatus.setStatusCode(ResponseStatus.SUCCESS);

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
        }else{
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setMessageDescription(HapisApplication.getApplication().getResources().getString(R.string.details_not_available));

            List<ErrorMessage> errorMessages = new ArrayList<>();
            errorMessages.add(errorMessage);

            responseStatus.setErrorMessages(errorMessages);

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
}
