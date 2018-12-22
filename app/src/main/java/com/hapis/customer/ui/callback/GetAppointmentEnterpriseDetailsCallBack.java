package com.hapis.customer.ui.callback;

import com.hapis.customer.ui.models.enterprise.EnterpriseRequest;

public interface GetAppointmentEnterpriseDetailsCallBack {
    void getHospitalDetails(EnterpriseRequest enterpriseRequest);
    void failedToGetHospitalDetails();
}
