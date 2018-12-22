/**
 * 
 */
package com.hapis.customer.ui.models.appointments;

import com.hapis.customer.ui.models.ResponseMessageModel;

/**
 * @author Hidayathulla.Khan
 *
 */
public class AppointmentBaseResponse extends ResponseMessageModel<AppointmentBaseRequest> {

	private static final long serialVersionUID = -912640212304381896L;

	private AppointmentBaseRequest appointmentBaseRequest;

	@Override
	public AppointmentBaseRequest getMessage() {
		return appointmentBaseRequest;
	}

	@Override
	public void setMessage(AppointmentBaseRequest appointmentBaseRequest) {
		this.appointmentBaseRequest = appointmentBaseRequest;

	}
}
