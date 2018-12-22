package com.hapis.customer.ui.callback;

import com.hapis.customer.ui.models.users.UserRequest;

public interface GetAppointmentDoctorDetailsCallBack {

    void getDoctorDetails(UserRequest userRequest);
    void failedToGetDoctorDetails();
}
