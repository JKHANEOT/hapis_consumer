package com.hapis.customer.database.repository;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.hapis.customer.HapisApplication;
import com.hapis.customer.database.HapisDatabase;
import com.hapis.customer.database.daos.UserProfileDao;
import com.hapis.customer.database.tables.UserProfileTable;
import com.hapis.customer.networking.RestCall;
import com.hapis.customer.networking.json.JSONAdaptor;
import com.hapis.customer.networking.util.RestConstants;
import com.hapis.customer.ui.models.ErrorMessage;
import com.hapis.customer.ui.models.ResponseStatus;
import com.hapis.customer.ui.models.wallet.RechargeRequest;
import com.hapis.customer.ui.models.wallet.RechargeResponse;
import com.hapis.customer.ui.models.wallet.RechargeResponseList;
import com.hapis.customer.ui.utils.AccessPreferences;
import com.hapis.customer.ui.utils.ApplicationConstants;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import okhttp3.Call;

public class UserWalletRepository {

    private final String TAG = UserWalletRepository.class.getName();

    private UserProfileDao userProfileDao;

    public UserWalletRepository(){
        HapisDatabase database = HapisDatabase.getInstance(HapisApplication.getApplication());
        userProfileDao = database.getUserProfileDao();
    }

    public class GetAvailableWalletBalance extends AsyncTask<Void, Void, String> {

        private MutableLiveData<Double> mutableLiveData;

        public GetAvailableWalletBalance(MutableLiveData<Double> mutableLiveDat){
            this.mutableLiveData = mutableLiveDat;
        }

        @Override
        protected String doInBackground(Void... voids) {

            String customerCode = null;

            String loggedInUser = AccessPreferences.get(HapisApplication.getApplication(), ApplicationConstants.LOGGED_IN_USER_GUID, null);
            UserProfileTable userProfileTable = null;

            if(loggedInUser != null) {
                userProfileTable = userProfileDao.getUserProfileByUniqueId(loggedInUser);
                if(userProfileTable != null && userProfileTable.getUniqueId() != null){
                    customerCode = userProfileTable.getUniqueId();
                }
            }

            return customerCode;
        }

        @Override
        protected void onPostExecute(String customerCode) {
            super.onPostExecute(customerCode);

            if(customerCode != null){
                getAvailableWalletBalance(mutableLiveData, customerCode);
            }else{
                mutableLiveData.postValue(new Double(0.0));
            }
        }
    }

    public void getAvailableWalletBalance(final MutableLiveData<Double> mutableLiveData, String customerCode) {

        RestCall restCall = new RestCall();
        restCall.setOnRestCallListener(new RestCall.RestCallListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                mutableLiveData.postValue(new Double(0.0));
            }

            @Override
            public void onResponse(RestCall.Result result, String response, List<ErrorMessage> errorMessages, String msg) {
                if (result == RestCall.Result.FAILED || result == RestCall.Result.EXCEPTION) {
                    mutableLiveData.postValue(new Double(0.0));
                } else {
                    try {
                        if(response != null && response.length() > 0) {
                            mutableLiveData.postValue(new Double(response));
                        }else{
                            mutableLiveData.postValue(new Double(0.0));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        mutableLiveData.postValue(new Double(0.0));
                    }
                }
            }
        });
        try {
            restCall.get(null, false, "Loading items",
                    HapisApplication.getApplication().getBackendUrl()+"9000"  + RestConstants.getCustomerBalance_for_wallet_account+customerCode+"}");
        } catch (IOException e) {
            e.printStackTrace();
            mutableLiveData.postValue(new Double(0.0));
        } catch (Exception e) {
            e.printStackTrace();
            mutableLiveData.postValue(new Double(0.0));
        }

    }

    public class RechargeWalletAccount extends AsyncTask<Void, Void, String> {

        private MutableLiveData<RechargeResponse> mutableLiveData;
        private String enteredAmount, enteredCardNumber, enteredCardValidity, enteredCardCvv;

        public RechargeWalletAccount(MutableLiveData<RechargeResponse> mutableLiveDat, String enteredAmount, String enteredCardNumber, String enteredCardValidity, String enteredCardCvv){
            this.mutableLiveData = mutableLiveDat;
            this.enteredAmount = enteredAmount;
            this.enteredCardNumber = enteredCardNumber;
            this.enteredCardValidity = enteredCardValidity;
            this.enteredCardCvv = enteredCardCvv;
        }

        @Override
        protected String doInBackground(Void... voids) {

            String customerCode = null;

            String loggedInUser = AccessPreferences.get(HapisApplication.getApplication(), ApplicationConstants.LOGGED_IN_USER_GUID, null);
            UserProfileTable userProfileTable = null;

            if(loggedInUser != null) {
                userProfileTable = userProfileDao.getUserProfileByUniqueId(loggedInUser);
                if(userProfileTable != null && userProfileTable.getUniqueId() != null){
                    customerCode = userProfileTable.getUniqueId();
                }
            }

            return customerCode;
        }

        @Override
        protected void onPostExecute(String customerCode) {
            super.onPostExecute(customerCode);

            if(customerCode != null){
                rechargeWalletAccount(mutableLiveData, customerCode, enteredAmount, enteredCardNumber, enteredCardValidity, enteredCardCvv);
            }else{
                ResponseStatus responseStatus = new ResponseStatus();
                responseStatus.setStatusCode(ResponseStatus.FAILED);

                RechargeResponse rechargeResponse = new RechargeResponse();
                rechargeResponse.setStatus(responseStatus);

                mutableLiveData.postValue(rechargeResponse);
            }
        }
    }

    public void rechargeWalletAccount(final MutableLiveData<RechargeResponse> mutableLiveData, String customerCode, String enteredAmount, String enteredCardNumber, String enteredCardValidity, String enteredCardCvv) {

        RechargeRequest rechargeRequest = new RechargeRequest();
        rechargeRequest.setCustomerCode(customerCode);
        rechargeRequest.setRechargeAmount(new BigDecimal(enteredAmount));
        rechargeRequest.setCustomerCardNo(enteredCardNumber);
        rechargeRequest.setCustomerBank("ICICI");

        RestCall restCall = new RestCall();
        restCall.setOnRestCallListener(new RestCall.RestCallListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                ResponseStatus responseStatus = new ResponseStatus();
                responseStatus.setStatusCode(ResponseStatus.FAILED);

                RechargeResponse rechargeResponse = new RechargeResponse();
                rechargeResponse.setStatus(responseStatus);

                mutableLiveData.postValue(rechargeResponse);
            }

            @Override
            public void onResponse(RestCall.Result result, String response, List<ErrorMessage> errorMessages, String msg) {
                if (result == RestCall.Result.FAILED || result == RestCall.Result.EXCEPTION) {
                    ResponseStatus responseStatus = new ResponseStatus();
                    responseStatus.setStatusCode(ResponseStatus.FAILED);

                    RechargeResponse rechargeResponse = new RechargeResponse();
                    rechargeResponse.setStatus(responseStatus);

                    mutableLiveData.postValue(rechargeResponse);
                } else {
                    try {
                        if(response != null && response.length() > 0) {
                            RechargeResponse rechargeResponse = JSONAdaptor.fromJSON(response, RechargeResponse.class);
                            if(rechargeResponse != null && rechargeResponse.getMessage() != null){
                                mutableLiveData.postValue(rechargeResponse);
                            }else{
                                ResponseStatus responseStatus = new ResponseStatus();
                                responseStatus.setStatusCode(ResponseStatus.FAILED);

                                RechargeResponse rechargeResponseFailed = new RechargeResponse();
                                rechargeResponseFailed.setStatus(responseStatus);

                                mutableLiveData.postValue(rechargeResponseFailed);
                            }
                        }else{
                            ResponseStatus responseStatus = new ResponseStatus();
                            responseStatus.setStatusCode(ResponseStatus.FAILED);

                            RechargeResponse rechargeResponse = new RechargeResponse();
                            rechargeResponse.setStatus(responseStatus);

                            mutableLiveData.postValue(rechargeResponse);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ResponseStatus responseStatus = new ResponseStatus();
                        responseStatus.setStatusCode(ResponseStatus.FAILED);

                        RechargeResponse rechargeResponse = new RechargeResponse();
                        rechargeResponse.setStatus(responseStatus);

                        mutableLiveData.postValue(rechargeResponse);
                    }
                }
            }
        });
        try {
            restCall.post(null, false, "Loading items",
                    HapisApplication.getApplication().getBackendUrl()+"9000"  + RestConstants.recharge_wallet_account, JSONAdaptor.toJSON(rechargeRequest));
        } catch (IOException e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            RechargeResponse rechargeResponse = new RechargeResponse();
            rechargeResponse.setStatus(responseStatus);

            mutableLiveData.postValue(rechargeResponse);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            RechargeResponse rechargeResponse = new RechargeResponse();
            rechargeResponse.setStatus(responseStatus);

            mutableLiveData.postValue(rechargeResponse);
        }

    }

    public class GetPastRechargesWalletAccount extends AsyncTask<Void, Void, String> {

        private MutableLiveData<RechargeResponseList> mutableLiveData;

        public GetPastRechargesWalletAccount(MutableLiveData<RechargeResponseList> mutableLiveDat){
            this.mutableLiveData = mutableLiveDat;
        }

        @Override
        protected String doInBackground(Void... voids) {

            String customerCode = null;

            String loggedInUser = AccessPreferences.get(HapisApplication.getApplication(), ApplicationConstants.LOGGED_IN_USER_GUID, null);
            UserProfileTable userProfileTable = null;

            if(loggedInUser != null) {
                userProfileTable = userProfileDao.getUserProfileByUniqueId(loggedInUser);
                if(userProfileTable != null && userProfileTable.getUniqueId() != null){
                    customerCode = userProfileTable.getUniqueId();
                }
            }

            return customerCode;
        }

        @Override
        protected void onPostExecute(String customerCode) {
            super.onPostExecute(customerCode);

            if(customerCode != null){
                getPastRechargesWalletAccount(mutableLiveData, customerCode);
            }else{
                ResponseStatus responseStatus = new ResponseStatus();
                responseStatus.setStatusCode(ResponseStatus.FAILED);

                RechargeResponseList rechargeResponseList = new RechargeResponseList();
                rechargeResponseList.setStatus(responseStatus);

                mutableLiveData.postValue(rechargeResponseList);
            }
        }
    }

    public void getPastRechargesWalletAccount(final MutableLiveData<RechargeResponseList> mutableLiveData, String customerCode) {

        RestCall restCall = new RestCall();
        restCall.setOnRestCallListener(new RestCall.RestCallListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                ResponseStatus responseStatus = new ResponseStatus();
                responseStatus.setStatusCode(ResponseStatus.FAILED);

                RechargeResponseList rechargeResponseList = new RechargeResponseList();
                rechargeResponseList.setStatus(responseStatus);

                mutableLiveData.postValue(rechargeResponseList);
            }

            @Override
            public void onResponse(RestCall.Result result, String response, List<ErrorMessage> errorMessages, String msg) {
                if (result == RestCall.Result.FAILED || result == RestCall.Result.EXCEPTION) {
                    ResponseStatus responseStatus = new ResponseStatus();
                    responseStatus.setStatusCode(ResponseStatus.FAILED);

                    RechargeResponseList rechargeResponseList = new RechargeResponseList();
                    rechargeResponseList.setStatus(responseStatus);

                    mutableLiveData.postValue(rechargeResponseList);
                } else {
                    try {
                        if(response != null && response.length() > 0) {
                            RechargeResponseList rechargeResponseList = JSONAdaptor.fromJSON(response, RechargeResponseList.class);
                            if(rechargeResponseList != null && rechargeResponseList.getResults() != null && rechargeResponseList.getResults().size() > 0){
                                mutableLiveData.postValue(rechargeResponseList);
                            }else{
                                ResponseStatus responseStatus = new ResponseStatus();
                                responseStatus.setStatusCode(ResponseStatus.FAILED);

                                RechargeResponseList rechargeResponseList1 = new RechargeResponseList();
                                rechargeResponseList1.setStatus(responseStatus);

                                mutableLiveData.postValue(rechargeResponseList1);
                            }
                        }else{
                            ResponseStatus responseStatus = new ResponseStatus();
                            responseStatus.setStatusCode(ResponseStatus.FAILED);

                            RechargeResponseList rechargeResponseList = new RechargeResponseList();
                            rechargeResponseList.setStatus(responseStatus);

                            mutableLiveData.postValue(rechargeResponseList);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        ResponseStatus responseStatus = new ResponseStatus();
                        responseStatus.setStatusCode(ResponseStatus.FAILED);

                        RechargeResponseList rechargeResponseList = new RechargeResponseList();
                        rechargeResponseList.setStatus(responseStatus);

                        mutableLiveData.postValue(rechargeResponseList);
                    }
                }
            }
        });
        try {
            restCall.get(null, false, "Loading items",
                    HapisApplication.getApplication().getBackendUrl()+"9000"  + RestConstants.getRechargesByCustomer_for_wallet_account+customerCode);
        } catch (IOException e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            RechargeResponseList rechargeResponseList = new RechargeResponseList();
            rechargeResponseList.setStatus(responseStatus);

            mutableLiveData.postValue(rechargeResponseList);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            RechargeResponseList rechargeResponseList = new RechargeResponseList();
            rechargeResponseList.setStatus(responseStatus);

            mutableLiveData.postValue(rechargeResponseList);
        }

    }
}
