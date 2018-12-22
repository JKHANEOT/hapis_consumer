package com.hapis.customer.ui.view;

import com.hapis.customer.ui.models.appointments.DoctorDetails;
import com.hapis.customer.ui.models.enterprise.EnterpriseRequest;

import java.util.List;

public interface BookAppointmentFragmentView extends BaseView {

    void updateEnterpriseByTypeAndCity(List<EnterpriseRequest> enterpriseRequestList);

    void failedToProcess(String errorMsg);

    void updateDoctorsByEnterpriseAndSpecialization(List<DoctorDetails> doctorDetailsList);

    void updateDoctorAvailableTimeSlot(List<String> availableTimeSlot);

    void createAppointment(String msg);

}
