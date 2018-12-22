/**
 * Copyright (C) Davinta Technologies 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Davinta Technologies. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Davinta Technologies.
 */

package com.hapis.customer.ui.models.appointments;

import com.hapis.customer.ui.models.ResponseMessageModel;

/**
 * EnterpriseResponseList class.
 * @author Javeed
 *
 */
public class SearchEnterpriseDoctorResponseList extends ResponseMessageModel<AppointmentSearchRequest> {

	private static final long serialVersionUID = 1L;

	private AppointmentSearchRequest message;

	@Override
	public AppointmentSearchRequest getMessage() {
		return message;
	}

	@Override
	public void setMessage(AppointmentSearchRequest appointmentSearchRequest) {
		message = appointmentSearchRequest;
	}
}
