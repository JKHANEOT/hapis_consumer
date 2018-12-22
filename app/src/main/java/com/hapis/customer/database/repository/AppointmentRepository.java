package com.hapis.customer.database.repository;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.hapis.customer.HapisApplication;
import com.hapis.customer.database.HapisDatabase;
import com.hapis.customer.database.daos.AddressDao;
import com.hapis.customer.database.daos.ApplicationProfileDao;
import com.hapis.customer.database.daos.UserProfileDao;
import com.hapis.customer.database.tables.UserProfileTable;
import com.hapis.customer.networking.RestCall;
import com.hapis.customer.networking.json.JSONAdaptor;
import com.hapis.customer.networking.util.RestConstants;
import com.hapis.customer.ui.callback.GetAppointmentDoctorDetailsCallBack;
import com.hapis.customer.ui.callback.GetAppointmentEnterpriseDetailsCallBack;
import com.hapis.customer.ui.models.ErrorMessage;
import com.hapis.customer.ui.models.ResponseStatus;
import com.hapis.customer.ui.models.appointments.AppointmentBaseResponse;
import com.hapis.customer.ui.models.appointments.AppointmentRequest;
import com.hapis.customer.ui.models.appointments.AppointmentResponseList;
import com.hapis.customer.ui.models.appointments.SearchEnterpriseDoctorResponseList;
import com.hapis.customer.ui.models.enterprise.EnterpriseResponse;
import com.hapis.customer.ui.models.enterprise.EnterpriseResponseList;
import com.hapis.customer.ui.models.users.UserResponse;
import com.hapis.customer.ui.utils.AccessPreferences;
import com.hapis.customer.ui.utils.ApplicationConstants;
import com.hapis.customer.ui.utils.EditTextUtils;
import com.hapis.customer.ui.view.UpComingSchedulesFragmentViewModal;
import com.hapis.customer.utils.DateUtil;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

public class AppointmentRepository {

    private final String TAG = AppointmentRepository.class.getName();

    private UserProfileDao userProfileDao;
    private ApplicationProfileDao applicationProfileDao;
    private AddressDao addressDao;

    public AppointmentRepository(){
        HapisDatabase database = HapisDatabase.getInstance(HapisApplication.getApplication());
        userProfileDao = database.getUserProfileDao();
        applicationProfileDao = database.getApplicationProfileDao();
        addressDao = database.getAddressDao();
    }

    public void getEnterprisesByEnterpriseTypeAndCity(final MutableLiveData<EnterpriseResponseList> mutableLiveData, int enterpriseType, String City) {

        RestCall restCall = new RestCall();
        restCall.setOnRestCallListener(new RestCall.RestCallListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                ResponseStatus responseStatus = new ResponseStatus();
                responseStatus.setStatusCode(ResponseStatus.FAILED);

                EnterpriseResponseList userModelResponse = new EnterpriseResponseList();
                userModelResponse.setStatus(responseStatus);

                mutableLiveData.postValue(userModelResponse);
            }

            @Override
            public void onResponse(RestCall.Result result, String response, List<ErrorMessage> errorMessages, String msg) {
                if (result == RestCall.Result.FAILED || result == RestCall.Result.EXCEPTION) {
                    ResponseStatus responseStatus = new ResponseStatus();
                    responseStatus.setStatusCode(ResponseStatus.FAILED);

                    EnterpriseResponseList userModelResponse = new EnterpriseResponseList();
                    userModelResponse.setStatus(responseStatus);

                    mutableLiveData.postValue(userModelResponse);
                } else {
                    try {
                        if(response != null && response.length() > 0) {
                            EnterpriseResponseList enterpriseResponseList = JSONAdaptor.fromJSON(response, EnterpriseResponseList.class);
                            if (enterpriseResponseList != null) {
                                mutableLiveData.postValue(enterpriseResponseList);
                            }else{
                                ResponseStatus responseStatus = new ResponseStatus();
                                responseStatus.setStatusCode(ResponseStatus.FAILED);

                                EnterpriseResponseList userModelResponse = new EnterpriseResponseList();
                                userModelResponse.setStatus(responseStatus);

                                mutableLiveData.postValue(userModelResponse);
                            }
                        }else{
                            ResponseStatus responseStatus = new ResponseStatus();
                            responseStatus.setStatusCode(ResponseStatus.FAILED);

                            EnterpriseResponseList userModelResponse = new EnterpriseResponseList();
                            userModelResponse.setStatus(responseStatus);

                            mutableLiveData.postValue(userModelResponse);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        ResponseStatus responseStatus = new ResponseStatus();
                        responseStatus.setStatusCode(ResponseStatus.FAILED);

                        EnterpriseResponseList userModelResponse = new EnterpriseResponseList();
                        userModelResponse.setStatus(responseStatus);

                        mutableLiveData.postValue(userModelResponse);
                    }
                }
            }
        });
        try {
            restCall.get(null, false, "Loading items",
                    HapisApplication.getApplication().getBackendUrl()+"9300"  + RestConstants.getEnterprisesByEnterpriseTypeAndCity+String.valueOf(enterpriseType)+"/"+City);
        } catch (IOException e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            EnterpriseResponseList userModelResponse = new EnterpriseResponseList();
            userModelResponse.setStatus(responseStatus);

            mutableLiveData.postValue(userModelResponse);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            EnterpriseResponseList userModelResponse = new EnterpriseResponseList();
            userModelResponse.setStatus(responseStatus);

            mutableLiveData.postValue(userModelResponse);
        }

    }

    public void getDoctorsByEnterpriseCodeAndSpecialization(final MutableLiveData<SearchEnterpriseDoctorResponseList> mutableLiveData, String enterpriseCode, String specialization) {

        RestCall restCall = new RestCall();
        restCall.setOnRestCallListener(new RestCall.RestCallListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                ResponseStatus responseStatus = new ResponseStatus();
                responseStatus.setStatusCode(ResponseStatus.FAILED);

                SearchEnterpriseDoctorResponseList userModelResponse = new SearchEnterpriseDoctorResponseList();
                userModelResponse.setStatus(responseStatus);

                mutableLiveData.postValue(userModelResponse);
            }

            @Override
            public void onResponse(RestCall.Result result, String response, List<ErrorMessage> errorMessages, String msg) {
                if (result == RestCall.Result.FAILED || result == RestCall.Result.EXCEPTION) {
                    ResponseStatus responseStatus = new ResponseStatus();
                    responseStatus.setStatusCode(ResponseStatus.FAILED);

                    SearchEnterpriseDoctorResponseList userModelResponse = new SearchEnterpriseDoctorResponseList();
                    userModelResponse.setStatus(responseStatus);

                    mutableLiveData.postValue(userModelResponse);
                } else {
                    try {
                        if(response != null && response.length() > 0) {
                            SearchEnterpriseDoctorResponseList enterpriseResponseList = JSONAdaptor.fromJSON(response, SearchEnterpriseDoctorResponseList.class);
                            if (enterpriseResponseList != null) {
                                mutableLiveData.postValue(enterpriseResponseList);
                            }else{
                                ResponseStatus responseStatus = new ResponseStatus();
                                responseStatus.setStatusCode(ResponseStatus.FAILED);

                                SearchEnterpriseDoctorResponseList userModelResponse = new SearchEnterpriseDoctorResponseList();
                                userModelResponse.setStatus(responseStatus);

                                mutableLiveData.postValue(userModelResponse);
                            }
                        }else{
                            ResponseStatus responseStatus = new ResponseStatus();
                            responseStatus.setStatusCode(ResponseStatus.FAILED);

                            SearchEnterpriseDoctorResponseList userModelResponse = new SearchEnterpriseDoctorResponseList();
                            userModelResponse.setStatus(responseStatus);

                            mutableLiveData.postValue(userModelResponse);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        ResponseStatus responseStatus = new ResponseStatus();
                        responseStatus.setStatusCode(ResponseStatus.FAILED);

                        SearchEnterpriseDoctorResponseList userModelResponse = new SearchEnterpriseDoctorResponseList();
                        userModelResponse.setStatus(responseStatus);

                        mutableLiveData.postValue(userModelResponse);
                    }
                }
            }
        });
        try {
            restCall.get(null, false, "Loading items",
                    HapisApplication.getApplication().getBackendUrl()+"9300" + RestConstants.GET_DOCTORS_BY_ENTERPRISECODE_SPECIALIZATION+enterpriseCode+"/"+specialization);
        } catch (IOException e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            SearchEnterpriseDoctorResponseList userModelResponse = new SearchEnterpriseDoctorResponseList();
            userModelResponse.setStatus(responseStatus);

            mutableLiveData.postValue(userModelResponse);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            SearchEnterpriseDoctorResponseList userModelResponse = new SearchEnterpriseDoctorResponseList();
            userModelResponse.setStatus(responseStatus);

            mutableLiveData.postValue(userModelResponse);
        }

    }

    public void getAvailableAppointmentsByDoctorCodeEnterpriseCodeRequestedDate(final MutableLiveData<SearchEnterpriseDoctorResponseList> mutableLiveData, String doctorCode, String enterpriseCode, String requestedDate) {

        RestCall restCall = new RestCall();
        restCall.setOnRestCallListener(new RestCall.RestCallListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                ResponseStatus responseStatus = new ResponseStatus();
                responseStatus.setStatusCode(ResponseStatus.FAILED);

                SearchEnterpriseDoctorResponseList userModelResponse = new SearchEnterpriseDoctorResponseList();
                userModelResponse.setStatus(responseStatus);

                mutableLiveData.postValue(userModelResponse);
            }

            @Override
            public void onResponse(RestCall.Result result, String response, List<ErrorMessage> errorMessages, String msg) {
                if (result == RestCall.Result.FAILED || result == RestCall.Result.EXCEPTION) {
                    ResponseStatus responseStatus = new ResponseStatus();
                    responseStatus.setStatusCode(ResponseStatus.FAILED);

                    SearchEnterpriseDoctorResponseList userModelResponse = new SearchEnterpriseDoctorResponseList();
                    userModelResponse.setStatus(responseStatus);

                    mutableLiveData.postValue(userModelResponse);
                } else {
                    try {
                        if(response != null && response.length() > 0) {
                            SearchEnterpriseDoctorResponseList enterpriseResponseList = JSONAdaptor.fromJSON(response, SearchEnterpriseDoctorResponseList.class);
                            if (enterpriseResponseList != null) {
                                mutableLiveData.postValue(enterpriseResponseList);
                            }else{
                                ResponseStatus responseStatus = new ResponseStatus();
                                responseStatus.setStatusCode(ResponseStatus.FAILED);

                                SearchEnterpriseDoctorResponseList userModelResponse = new SearchEnterpriseDoctorResponseList();
                                userModelResponse.setStatus(responseStatus);

                                mutableLiveData.postValue(userModelResponse);
                            }
                        }else{
                            ResponseStatus responseStatus = new ResponseStatus();
                            responseStatus.setStatusCode(ResponseStatus.FAILED);

                            SearchEnterpriseDoctorResponseList userModelResponse = new SearchEnterpriseDoctorResponseList();
                            userModelResponse.setStatus(responseStatus);

                            mutableLiveData.postValue(userModelResponse);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        ResponseStatus responseStatus = new ResponseStatus();
                        responseStatus.setStatusCode(ResponseStatus.FAILED);

                        SearchEnterpriseDoctorResponseList userModelResponse = new SearchEnterpriseDoctorResponseList();
                        userModelResponse.setStatus(responseStatus);

                        mutableLiveData.postValue(userModelResponse);
                    }
                }
            }
        });
        try {
            restCall.get(null, false, "Loading items",
                    HapisApplication.getApplication().getBackendUrl()+"9300" + RestConstants.getAvailableAppointments +doctorCode+"/"+requestedDate+"/"+enterpriseCode);
        } catch (IOException e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            SearchEnterpriseDoctorResponseList userModelResponse = new SearchEnterpriseDoctorResponseList();
            userModelResponse.setStatus(responseStatus);

            mutableLiveData.postValue(userModelResponse);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            SearchEnterpriseDoctorResponseList userModelResponse = new SearchEnterpriseDoctorResponseList();
            userModelResponse.setStatus(responseStatus);

            mutableLiveData.postValue(userModelResponse);
        }
    }

    public class CreateAppointmentTask extends AsyncTask<Void, Void, AppointmentRequest> {

        private MutableLiveData<AppointmentBaseResponse> mutableLiveData;
        private String appointmentDate; private String doctorCode; private String hospitalCode; private int slotBooked;

        public CreateAppointmentTask(final MutableLiveData<AppointmentBaseResponse> mutableLiveData, final String appointmentDate, final String doctorCode, final String hospitalCode, final int slotBooked){
            this.mutableLiveData = mutableLiveData;
            this.appointmentDate = appointmentDate;
            this.doctorCode = doctorCode;
            this.hospitalCode = hospitalCode;
            this.slotBooked = slotBooked;
        }

        @Override
        protected AppointmentRequest doInBackground(Void... voids) {

            AppointmentRequest appointmentRequest = null;

            String loggedInUser = AccessPreferences.get(HapisApplication.getApplication(), ApplicationConstants.LOGGED_IN_USER_GUID, null);
            UserProfileTable userProfileTable = null;

            if(loggedInUser != null){
                userProfileTable = userProfileDao.getUserProfileByUniqueId(loggedInUser);

                appointmentRequest = new AppointmentRequest();
                appointmentRequest.setAppointmentDate(appointmentDate);
                appointmentRequest.setDoctorCode(doctorCode);
                appointmentRequest.setHospitalCode(hospitalCode);
                appointmentRequest.setSlotBooked(slotBooked);

                if(userProfileTable != null){

                    if(userProfileTable.getDateOfBirth() > 0)
                        appointmentRequest.setPatientAge(DateUtil.convertDobToAge(new Date(userProfileTable.getDateOfBirth())));
                    if(userProfileTable.getGender() != null)
                        appointmentRequest.setPatientGender(userProfileTable.getGender());

                    StringBuilder stringBuilder = new StringBuilder();

                    if(!EditTextUtils.isEmpty(userProfileTable.getFirstName()))
                        stringBuilder.append(userProfileTable.getFirstName()+" ");
                    if(!EditTextUtils.isEmpty(userProfileTable.getMiddleName()))
                        stringBuilder.append(userProfileTable.getMiddleName()+" ");
                    if(!EditTextUtils.isEmpty(userProfileTable.getLastName()))
                        stringBuilder.append(userProfileTable.getLastName());

                    appointmentRequest.setPatientName(stringBuilder.toString());
                    appointmentRequest.setPatientRelation("Self");

                    appointmentRequest.setCustomerCode(userProfileTable.getUniqueId());
                }
            }

            return appointmentRequest;
        }

        @Override
        protected void onPostExecute(AppointmentRequest userProfileTable) {
            super.onPostExecute(userProfileTable);

            initCreateAppointment(mutableLiveData, userProfileTable);
        }
    }

    public void initCreateAppointment(final MutableLiveData<AppointmentBaseResponse> mutableLiveData, AppointmentRequest appointmentRequest) {

        RestCall restCall = new RestCall();
        restCall.setOnRestCallListener(new RestCall.RestCallListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                ResponseStatus responseStatus = new ResponseStatus();
                responseStatus.setStatusCode(ResponseStatus.FAILED);

                AppointmentBaseResponse userModelResponse = new AppointmentBaseResponse();
                userModelResponse.setStatus(responseStatus);

                mutableLiveData.postValue(userModelResponse);
            }

            @Override
            public void onResponse(RestCall.Result result, String response, List<ErrorMessage> errorMessages, String msg) {
                if (result == RestCall.Result.FAILED || result == RestCall.Result.EXCEPTION) {
                    ResponseStatus responseStatus = new ResponseStatus();
                    responseStatus.setStatusCode(ResponseStatus.FAILED);

                    AppointmentBaseResponse userModelResponse = new AppointmentBaseResponse();
                    userModelResponse.setStatus(responseStatus);

                    mutableLiveData.postValue(userModelResponse);
                } else {
                    try {
                        ResponseStatus responseStatus = new ResponseStatus();
                        responseStatus.setStatusCode(ResponseStatus.SUCCESS);

                        AppointmentBaseResponse userModelResponse = new AppointmentBaseResponse();
                        userModelResponse.setStatus(responseStatus);

                        mutableLiveData.postValue(userModelResponse);

                    } catch (Exception e) {
                        e.printStackTrace();
                        ResponseStatus responseStatus = new ResponseStatus();
                        responseStatus.setStatusCode(ResponseStatus.FAILED);

                        AppointmentBaseResponse userModelResponse = new AppointmentBaseResponse();
                        userModelResponse.setStatus(responseStatus);

                        mutableLiveData.postValue(userModelResponse);
                    }
                }
            }
        });
        try {
            restCall.post(null, false, "Loading items",
                    HapisApplication.getApplication().getBackendUrl()+"9300"  + RestConstants.CREATE_APPOINTMENTS,
                    JSONAdaptor.toJSON(appointmentRequest));
        } catch (IOException e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            AppointmentBaseResponse userModelResponse = new AppointmentBaseResponse();
            userModelResponse.setStatus(responseStatus);

            mutableLiveData.postValue(userModelResponse);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            AppointmentBaseResponse userModelResponse = new AppointmentBaseResponse();
            userModelResponse.setStatus(responseStatus);

            mutableLiveData.postValue(userModelResponse);
        }
    }

    public void getAppointmentsByCustomer(final MutableLiveData<AppointmentResponseList> mutableLiveData, String customerCode, Integer status) {

        RestCall restCall = new RestCall();
        restCall.setOnRestCallListener(new RestCall.RestCallListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                ResponseStatus responseStatus = new ResponseStatus();
                responseStatus.setStatusCode(ResponseStatus.FAILED);

                AppointmentResponseList userModelResponse = new AppointmentResponseList();
                userModelResponse.setStatus(responseStatus);

                mutableLiveData.postValue(userModelResponse);
            }

            @Override
            public void onResponse(RestCall.Result result, String response, List<ErrorMessage> errorMessages, String msg) {
                if (result == RestCall.Result.FAILED || result == RestCall.Result.EXCEPTION) {
                    ResponseStatus responseStatus = new ResponseStatus();
                    responseStatus.setStatusCode(ResponseStatus.FAILED);

                    AppointmentResponseList userModelResponse = new AppointmentResponseList();
                    userModelResponse.setStatus(responseStatus);

                    mutableLiveData.postValue(userModelResponse);
                } else {
                    try {
                        if(response != null && response.length() > 0) {
                            AppointmentResponseList appointmentResponseList = JSONAdaptor.fromJSON(response, AppointmentResponseList.class);
                            if (appointmentResponseList != null) {
                                mutableLiveData.postValue(appointmentResponseList);
                            }else{
                                ResponseStatus responseStatus = new ResponseStatus();
                                responseStatus.setStatusCode(ResponseStatus.FAILED);

                                AppointmentResponseList userModelResponse = new AppointmentResponseList();
                                userModelResponse.setStatus(responseStatus);

                                mutableLiveData.postValue(userModelResponse);
                            }
                        }else{
                            ResponseStatus responseStatus = new ResponseStatus();
                            responseStatus.setStatusCode(ResponseStatus.FAILED);

                            AppointmentResponseList userModelResponse = new AppointmentResponseList();
                            userModelResponse.setStatus(responseStatus);

                            mutableLiveData.postValue(userModelResponse);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        ResponseStatus responseStatus = new ResponseStatus();
                        responseStatus.setStatusCode(ResponseStatus.FAILED);

                        AppointmentResponseList userModelResponse = new AppointmentResponseList();
                        userModelResponse.setStatus(responseStatus);

                        mutableLiveData.postValue(userModelResponse);
                    }
                }
            }
        });
        try {
            restCall.get(null, false, "Loading items",
                    HapisApplication.getApplication().getBackendUrl()+"9300" + RestConstants.getAppointmentsByCustomer +customerCode+"/"+String.valueOf(status));
        } catch (IOException e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            AppointmentResponseList userModelResponse = new AppointmentResponseList();
            userModelResponse.setStatus(responseStatus);

            mutableLiveData.postValue(userModelResponse);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            AppointmentResponseList userModelResponse = new AppointmentResponseList();
            userModelResponse.setStatus(responseStatus);

            mutableLiveData.postValue(userModelResponse);
        }
    }

    public void getAppointment(final MutableLiveData<AppointmentBaseResponse> mutableLiveData, String appointmentCode) {

        RestCall restCall = new RestCall();
        restCall.setOnRestCallListener(new RestCall.RestCallListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                ResponseStatus responseStatus = new ResponseStatus();
                responseStatus.setStatusCode(ResponseStatus.FAILED);

                AppointmentBaseResponse userModelResponse = new AppointmentBaseResponse();
                userModelResponse.setStatus(responseStatus);

                mutableLiveData.postValue(userModelResponse);
            }

            @Override
            public void onResponse(RestCall.Result result, String response, List<ErrorMessage> errorMessages, String msg) {
                if (result == RestCall.Result.FAILED || result == RestCall.Result.EXCEPTION) {
                    ResponseStatus responseStatus = new ResponseStatus();
                    responseStatus.setStatusCode(ResponseStatus.FAILED);

                    AppointmentBaseResponse userModelResponse = new AppointmentBaseResponse();
                    userModelResponse.setStatus(responseStatus);

                    mutableLiveData.postValue(userModelResponse);
                } else {
                    try {
                        if(response != null && response.length() > 0) {
                            AppointmentBaseResponse appointmentResponseList = JSONAdaptor.fromJSON(response, AppointmentBaseResponse.class);
                            if (appointmentResponseList != null) {
                                mutableLiveData.postValue(appointmentResponseList);
                            }else{
                                ResponseStatus responseStatus = new ResponseStatus();
                                responseStatus.setStatusCode(ResponseStatus.FAILED);

                                AppointmentBaseResponse userModelResponse = new AppointmentBaseResponse();
                                userModelResponse.setStatus(responseStatus);

                                mutableLiveData.postValue(userModelResponse);
                            }
                        }else{
                            ResponseStatus responseStatus = new ResponseStatus();
                            responseStatus.setStatusCode(ResponseStatus.FAILED);

                            AppointmentBaseResponse userModelResponse = new AppointmentBaseResponse();
                            userModelResponse.setStatus(responseStatus);

                            mutableLiveData.postValue(userModelResponse);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        ResponseStatus responseStatus = new ResponseStatus();
                        responseStatus.setStatusCode(ResponseStatus.FAILED);

                        AppointmentBaseResponse userModelResponse = new AppointmentBaseResponse();
                        userModelResponse.setStatus(responseStatus);

                        mutableLiveData.postValue(userModelResponse);
                    }
                }
            }
        });
        try {
            restCall.get(null, false, "Loading items",
                    HapisApplication.getApplication().getBackendUrl()+"9300" + RestConstants.getAppointment+appointmentCode);
        } catch (IOException e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            AppointmentBaseResponse userModelResponse = new AppointmentBaseResponse();
            userModelResponse.setStatus(responseStatus);

            mutableLiveData.postValue(userModelResponse);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            AppointmentBaseResponse userModelResponse = new AppointmentBaseResponse();
            userModelResponse.setStatus(responseStatus);

            mutableLiveData.postValue(userModelResponse);
        }
    }

    public void updateAppointment(final MutableLiveData<AppointmentBaseResponse> mutableLiveData, AppointmentRequest appointmentRequest) {

        RestCall restCall = new RestCall();
        restCall.setOnRestCallListener(new RestCall.RestCallListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                ResponseStatus responseStatus = new ResponseStatus();
                responseStatus.setStatusCode(ResponseStatus.FAILED);

                AppointmentBaseResponse userModelResponse = new AppointmentBaseResponse();
                userModelResponse.setStatus(responseStatus);

                mutableLiveData.postValue(userModelResponse);
            }

            @Override
            public void onResponse(RestCall.Result result, String response, List<ErrorMessage> errorMessages, String msg) {
                if (result == RestCall.Result.FAILED || result == RestCall.Result.EXCEPTION) {
                    ResponseStatus responseStatus = new ResponseStatus();
                    responseStatus.setStatusCode(ResponseStatus.FAILED);

                    AppointmentBaseResponse userModelResponse = new AppointmentBaseResponse();
                    userModelResponse.setStatus(responseStatus);

                    mutableLiveData.postValue(userModelResponse);
                } else {
                    try {
                        if(response != null && response.length() > 0) {
                            AppointmentBaseResponse appointmentResponseList = JSONAdaptor.fromJSON(response, AppointmentBaseResponse.class);
                            if (appointmentResponseList != null) {
                                mutableLiveData.postValue(appointmentResponseList);
                            }else{
                                ResponseStatus responseStatus = new ResponseStatus();
                                responseStatus.setStatusCode(ResponseStatus.FAILED);

                                AppointmentBaseResponse userModelResponse = new AppointmentBaseResponse();
                                userModelResponse.setStatus(responseStatus);

                                mutableLiveData.postValue(userModelResponse);
                            }
                        }else{
                            ResponseStatus responseStatus = new ResponseStatus();
                            responseStatus.setStatusCode(ResponseStatus.FAILED);

                            AppointmentBaseResponse userModelResponse = new AppointmentBaseResponse();
                            userModelResponse.setStatus(responseStatus);

                            mutableLiveData.postValue(userModelResponse);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        ResponseStatus responseStatus = new ResponseStatus();
                        responseStatus.setStatusCode(ResponseStatus.FAILED);

                        AppointmentBaseResponse userModelResponse = new AppointmentBaseResponse();
                        userModelResponse.setStatus(responseStatus);

                        mutableLiveData.postValue(userModelResponse);
                    }
                }
            }
        });
        try {
            restCall.put(null, false, "Loading items",
                    HapisApplication.getApplication().getBackendUrl()+"9300" + RestConstants.updateAppointment, JSONAdaptor.toJSON(appointmentRequest));
        } catch (IOException e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            AppointmentBaseResponse userModelResponse = new AppointmentBaseResponse();
            userModelResponse.setStatus(responseStatus);

            mutableLiveData.postValue(userModelResponse);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(ResponseStatus.FAILED);

            AppointmentBaseResponse userModelResponse = new AppointmentBaseResponse();
            userModelResponse.setStatus(responseStatus);

            mutableLiveData.postValue(userModelResponse);
        }
    }

    public class GetAppointmentTask extends AsyncTask<Void, Void, String> {

        private MutableLiveData<AppointmentResponseList> mutableLiveData;
        private int mStatus;

        public GetAppointmentTask(final MutableLiveData<AppointmentResponseList> mutableLiveData, final int status){
            this.mutableLiveData = mutableLiveData;
            mStatus = status;
        }

        @Override
        protected String doInBackground(Void... voids) {

            String loggedInUser = AccessPreferences.get(HapisApplication.getApplication(), ApplicationConstants.LOGGED_IN_USER_GUID, null);
            UserProfileTable userProfileTable = null;

            if(loggedInUser != null){
                userProfileTable = userProfileDao.getUserProfileByUniqueId(loggedInUser);

                if(userProfileTable != null){
                    return userProfileTable.getUniqueId();
                }else
                    return "CT000014";
            }

            return null;
        }

        @Override
        protected void onPostExecute(String userCode) {
            super.onPostExecute(userCode);

            getAppointmentsByCustomer(mutableLiveData, userCode, mStatus);
        }
    }

    public void getUserDetails(String userCode, GetAppointmentDoctorDetailsCallBack appointmentDetailsCallBack) {

        RestCall restCall = new RestCall();
        restCall.setOnRestCallListener(new RestCall.RestCallListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                appointmentDetailsCallBack.failedToGetDoctorDetails();
            }

            @Override
            public void onResponse(RestCall.Result result, String response, List<ErrorMessage> errorMessages, String msg) {
                if (result == RestCall.Result.FAILED || result == RestCall.Result.EXCEPTION) {
                    appointmentDetailsCallBack.failedToGetDoctorDetails();
                } else {
                    try {
                        if(response != null && response.length() > 0) {
                            UserResponse userModelResponse = JSONAdaptor.fromJSON(response, UserResponse.class);
                            if (userModelResponse != null && userModelResponse.getMessage() != null) {
                                appointmentDetailsCallBack.getDoctorDetails(userModelResponse.getMessage());
                            }else{
                                appointmentDetailsCallBack.failedToGetDoctorDetails();
                            }
                        }else{
                            appointmentDetailsCallBack.failedToGetDoctorDetails();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        appointmentDetailsCallBack.failedToGetDoctorDetails();
                    }
                }
            }
        });
        try {
            restCall.get(null, false, "Loading items",
                    HapisApplication.getApplication().getBackendUrl()+"9300" + RestConstants.getUserByCode+userCode);
        } catch (IOException e) {
            e.printStackTrace();
            appointmentDetailsCallBack.failedToGetDoctorDetails();
        } catch (Exception e) {
            e.printStackTrace();
            appointmentDetailsCallBack.failedToGetDoctorDetails();
        }
    }

    public void getEnterpriseDetails(String enterpriseCode, GetAppointmentEnterpriseDetailsCallBack appointmentDetailsCallBack) {

        RestCall restCall = new RestCall();
        restCall.setOnRestCallListener(new RestCall.RestCallListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                appointmentDetailsCallBack.failedToGetHospitalDetails();
            }

            @Override
            public void onResponse(RestCall.Result result, String response, List<ErrorMessage> errorMessages, String msg) {
                if (result == RestCall.Result.FAILED || result == RestCall.Result.EXCEPTION) {
                    appointmentDetailsCallBack.failedToGetHospitalDetails();
                } else {
                    try {
                        if(response != null && response.length() > 0) {
                            EnterpriseResponse enterpriseResponse = JSONAdaptor.fromJSON(response, EnterpriseResponse.class);
                            if (enterpriseResponse != null && enterpriseResponse.getMessage() != null) {
                                appointmentDetailsCallBack.getHospitalDetails(enterpriseResponse.getMessage());
                            }else{
                                appointmentDetailsCallBack.failedToGetHospitalDetails();
                            }
                        }else{
                            appointmentDetailsCallBack.failedToGetHospitalDetails();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        appointmentDetailsCallBack.failedToGetHospitalDetails();
                    }
                }
            }
        });
        try {
            restCall.get(null, false, "Loading items",
                    HapisApplication.getApplication().getBackendUrl()+"9300" + RestConstants.getEnterpriseByEnterpriseCode+enterpriseCode);
        } catch (IOException e) {
            e.printStackTrace();
            appointmentDetailsCallBack.failedToGetHospitalDetails();
        } catch (Exception e) {
            e.printStackTrace();
            appointmentDetailsCallBack.failedToGetHospitalDetails();
        }
    }

}
