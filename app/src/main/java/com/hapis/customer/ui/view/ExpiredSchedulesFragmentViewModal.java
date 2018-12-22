package com.hapis.customer.ui.view;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.hapis.customer.HapisApplication;
import com.hapis.customer.R;
import com.hapis.customer.database.repository.AppointmentRepository;
import com.hapis.customer.ui.callback.GetAppointmentDoctorDetailsCallBack;
import com.hapis.customer.ui.callback.GetAppointmentEnterpriseDetailsCallBack;
import com.hapis.customer.ui.models.HapisModel;
import com.hapis.customer.ui.models.ResponseStatus;
import com.hapis.customer.ui.models.appointments.AppointmentRequest;
import com.hapis.customer.ui.models.appointments.AppointmentResponseList;
import com.hapis.customer.ui.models.enterprise.EnterpriseRequest;
import com.hapis.customer.ui.models.enums.AppointmentStatusEnum;
import com.hapis.customer.ui.models.users.UserRequest;

import java.util.List;

public class ExpiredSchedulesFragmentViewModal extends BaseViewModal<ExpiredSchedulesFragmentView> {

    private String TAG = ExpiredSchedulesFragmentViewModal.class.getName();

    private AppointmentRepository appointmentRepository;

    public ExpiredSchedulesFragmentViewModal(LifecycleOwner owner) {
        super(owner);

        appointmentRepository = new AppointmentRepository();
    }

    @Override
    protected void initObservableData() {

    }

    @Override
    protected void handleChangedDataModal(HapisModel data) {

    }

    private GetAppointmentDoctorDetailsCallBack getAppointmentDetailsCallBack;
    private GetAppointmentEnterpriseDetailsCallBack getAppointmentEnterpriseDetailsCallBack;

    public void getUpcomingAppointments(){
        MutableLiveData<AppointmentResponseList> mutableLiveData = new MutableLiveData<>();

        appointmentRepository.new GetAppointmentTask(mutableLiveData, AppointmentStatusEnum.CLOSED.code()).execute();
        mutableLiveData.observe(mOwner, new Observer<AppointmentResponseList>() {
            @Override
            public void onChanged(@Nullable AppointmentResponseList userModelResponse) {
                if(userModelResponse != null){
                    if(userModelResponse.getStatus() != null && userModelResponse.getStatus().getStatusCode() != null && userModelResponse.getStatus().getStatusCode().intValue() == ResponseStatus.SUCCESS && userModelResponse.getResults() != null && userModelResponse.getResults().size() > 0){
                        for(int i = 0; i < userModelResponse.getResults().size(); i++){
                            final int index = i;
                            final AppointmentRequest appointmentRequest = userModelResponse.getResults().get(i);

                            getAppointmentDetailsCallBack = new GetAppointmentDoctorDetailsCallBack() {
                                @Override
                                public void getDoctorDetails(UserRequest userRequest) {
                                    appointmentRequest.setDoctorDetails(userRequest);
                                    userModelResponse.getResults().set(index, appointmentRequest);
                                    if(index == userModelResponse.getResults().size()-1)
                                        mView.fetchEnterpriseDetails(userModelResponse.getResults());
                                }

                                @Override
                                public void failedToGetDoctorDetails() {
                                    if(index == userModelResponse.getResults().size()-1)
                                        mView.fetchEnterpriseDetails(userModelResponse.getResults());
                                }
                            };

                            appointmentRepository.getUserDetails(appointmentRequest.getDoctorCode(), getAppointmentDetailsCallBack);
                        }
                    }else{
                        mView.failedToProcess(((userModelResponse.getStatus().getErrorMessages() != null && userModelResponse.getStatus().getErrorMessages().size() > 0) ? userModelResponse.getStatus().getErrorMessages().get(0).getMessageDescription() : HapisApplication.getApplication().getResources().getString(R.string.unable_to_process_request)));
                    }
                }else{
                    mView.failedToProcess(HapisApplication.getApplication().getResources().getString(R.string.unable_to_process_request));
                }
            }
        });
    }

    public void getEnterpriseDetails(List<AppointmentRequest> appointmentRequests){

        for(int i = 0; i < appointmentRequests.size(); i++){
            final int index = i;
            final AppointmentRequest appointmentRequest = appointmentRequests.get(i);

            getAppointmentEnterpriseDetailsCallBack = new GetAppointmentEnterpriseDetailsCallBack() {
                @Override
                public void getHospitalDetails(EnterpriseRequest enterpriseRequest) {
                    appointmentRequest.setEnterpriseRequest(enterpriseRequest);
                    appointmentRequests.set(index, appointmentRequest);
                    if(index == appointmentRequests.size()-1)
                        mView.showUpComingAppointments(appointmentRequests);
                    getAppointmentEnterpriseDetailsCallBack = null;
                }

                @Override
                public void failedToGetHospitalDetails() {
                    if(index == appointmentRequests.size()-1)
                        mView.showUpComingAppointments(appointmentRequests);
                    getAppointmentEnterpriseDetailsCallBack = null;
                }
            };
            appointmentRepository.getEnterpriseDetails(appointmentRequest.getHospitalCode(), getAppointmentEnterpriseDetailsCallBack);
        }
    }
}
