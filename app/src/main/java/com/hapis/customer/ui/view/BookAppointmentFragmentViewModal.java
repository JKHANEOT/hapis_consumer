package com.hapis.customer.ui.view;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.hapis.customer.HapisApplication;
import com.hapis.customer.R;
import com.hapis.customer.database.repository.AppointmentRepository;
import com.hapis.customer.ui.models.HapisModel;
import com.hapis.customer.ui.models.ResponseStatus;
import com.hapis.customer.ui.models.appointments.AppointmentBaseResponse;
import com.hapis.customer.ui.models.appointments.SearchEnterpriseDoctorResponseList;
import com.hapis.customer.ui.models.enterprise.EnterpriseResponseList;

public class BookAppointmentFragmentViewModal extends BaseViewModal<BookAppointmentFragmentView> {

    private String TAG = BookAppointmentFragmentViewModal.class.getName();

    private AppointmentRepository appointmentRepository;

    public BookAppointmentFragmentViewModal(LifecycleOwner owner) {
        super(owner);

        appointmentRepository = new AppointmentRepository();
    }

    @Override
    protected void initObservableData() {

    }

    @Override
    protected void handleChangedDataModal(HapisModel data) {

    }

    public void getEnterprisesBy(int enterpriseType, String City) {

        MutableLiveData<EnterpriseResponseList> enterpriseResponseListMutableLiveData = new MutableLiveData<>();

        appointmentRepository.getEnterprisesByEnterpriseTypeAndCity(enterpriseResponseListMutableLiveData, enterpriseType, City);
        enterpriseResponseListMutableLiveData.observe(mOwner, new Observer<EnterpriseResponseList>() {
            @Override
            public void onChanged(@Nullable EnterpriseResponseList enterpriseResponseList) {
                if(enterpriseResponseList != null){
                    if(enterpriseResponseList.getResults() != null && enterpriseResponseList.getResults().size() > 0){
                        mView.updateEnterpriseByTypeAndCity(enterpriseResponseList.getResults());
                    }else{
                        enterpriseResponseListMutableLiveData.removeObserver(new Observer<EnterpriseResponseList>() {
                            @Override
                            public void onChanged(@Nullable EnterpriseResponseList enterpriseResponseList) {

                            }
                        });
                        mView.failedToProcess(((enterpriseResponseList.getStatus().getErrorMessages() != null && enterpriseResponseList.getStatus().getErrorMessages().size() > 0) ? enterpriseResponseList.getStatus().getErrorMessages().get(0).getMessageDescription() : HapisApplication.getApplication().getResources().getString(R.string.unable_to_process_request)));
                    }
                }else{
                    enterpriseResponseListMutableLiveData.removeObserver(new Observer<EnterpriseResponseList>() {
                        @Override
                        public void onChanged(@Nullable EnterpriseResponseList enterpriseResponseList) {

                        }
                    });
                    mView.failedToProcess(HapisApplication.getApplication().getResources().getString(R.string.unable_to_process_request));
                }
            }
        });
    }

    public void getDoctorsByEnterprise(String enterpriseCode, String specialization) {

        MutableLiveData<SearchEnterpriseDoctorResponseList> enterpriseResponseListMutableLiveData = new MutableLiveData<>();

        appointmentRepository.getDoctorsByEnterpriseCodeAndSpecialization(enterpriseResponseListMutableLiveData, enterpriseCode, specialization);
        enterpriseResponseListMutableLiveData.observe(mOwner, new Observer<SearchEnterpriseDoctorResponseList>() {
            @Override
            public void onChanged(@Nullable SearchEnterpriseDoctorResponseList enterpriseResponseList) {
                if(enterpriseResponseList != null && enterpriseResponseList.getMessage() != null && enterpriseResponseList.getMessage().getDoctors() != null && enterpriseResponseList.getMessage().getDoctors().size() > 0){
                    mView.updateDoctorsByEnterpriseAndSpecialization(enterpriseResponseList.getMessage().getDoctors());
                }else{
                    enterpriseResponseListMutableLiveData.removeObserver(new Observer<SearchEnterpriseDoctorResponseList>() {
                        @Override
                        public void onChanged(@Nullable SearchEnterpriseDoctorResponseList enterpriseResponseList) {

                        }
                    });
                    mView.failedToProcess(HapisApplication.getApplication().getResources().getString(R.string.unable_to_process_request));
                }
            }
        });
    }

    public void getTimeSlotByDoctorEnterpriseAndDate(String doctorCode, String requestedDate, String enterpriseCode) {

        MutableLiveData<SearchEnterpriseDoctorResponseList> enterpriseResponseListMutableLiveData = new MutableLiveData<>();

        appointmentRepository.getAvailableAppointmentsByDoctorCodeEnterpriseCodeRequestedDate(enterpriseResponseListMutableLiveData, doctorCode, enterpriseCode, requestedDate);
        enterpriseResponseListMutableLiveData.observe(mOwner, new Observer<SearchEnterpriseDoctorResponseList>() {
            @Override
            public void onChanged(@Nullable SearchEnterpriseDoctorResponseList enterpriseResponseList) {
                if(enterpriseResponseList != null && enterpriseResponseList.getMessage() != null && enterpriseResponseList.getMessage().getAvailableSlots() != null && enterpriseResponseList.getMessage().getAvailableSlots().size() > 0){
                    mView.updateDoctorAvailableTimeSlot(enterpriseResponseList.getMessage().getAvailableSlots());
                }else{
                    enterpriseResponseListMutableLiveData.removeObserver(new Observer<SearchEnterpriseDoctorResponseList>() {
                        @Override
                        public void onChanged(@Nullable SearchEnterpriseDoctorResponseList enterpriseResponseList) {

                        }
                    });
                    mView.failedToProcess(HapisApplication.getApplication().getResources().getString(R.string.unable_to_process_request));
                }
            }
        });
    }

    public void createAppointment(String appointmentDate, String doctorCode, String hospitalCode, int slotBooked) {

        MutableLiveData<AppointmentBaseResponse> mutableLiveData = new MutableLiveData<>();

        appointmentRepository.new CreateAppointmentTask(mutableLiveData, appointmentDate, doctorCode, hospitalCode, slotBooked).execute();
        mutableLiveData.observe(mOwner, new Observer<AppointmentBaseResponse>() {
            @Override
            public void onChanged(@Nullable AppointmentBaseResponse userModelResponse) {
                if(userModelResponse != null){
                    if(userModelResponse.getStatus() != null && userModelResponse.getStatus().getStatusCode() != null && userModelResponse.getStatus().getStatusCode().intValue() == ResponseStatus.SUCCESS){
                        mView.createAppointment(HapisApplication.getApplication().getResources().getString(R.string.appointment_successfull));
                    }else{
                        mView.failedToProcess(((userModelResponse.getStatus().getErrorMessages() != null && userModelResponse.getStatus().getErrorMessages().size() > 0) ? userModelResponse.getStatus().getErrorMessages().get(0).getMessageDescription() : HapisApplication.getApplication().getResources().getString(R.string.unable_to_process_request)));
                    }
                }else{
                    mView.failedToProcess(HapisApplication.getApplication().getResources().getString(R.string.unable_to_process_request));
                }
            }
        });
    }
}
