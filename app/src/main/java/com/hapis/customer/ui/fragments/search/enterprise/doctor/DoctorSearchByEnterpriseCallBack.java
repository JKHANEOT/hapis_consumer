package com.hapis.customer.ui.fragments.search.enterprise.doctor;

import com.hapis.customer.ui.models.appointments.DoctorDetails;
import com.hapis.customer.ui.models.enterprise.EnterpriseRequest;

public interface DoctorSearchByEnterpriseCallBack {
    void updateSelectedValue(DoctorDetails doctorDetails);
}
