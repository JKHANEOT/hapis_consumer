/**
 * 
 */
package com.hapis.customer.ui.models.appointments;

import java.util.Date;
import java.util.List;

/**
 * @author Hidayathulla.Khan
 *
 */
public class AppointmentSearchRequest extends AppointmentBaseRequest {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5678643523385943635L;
	
	private String specialization;

	private String doctorCode;
	
	private Date requestedDate;
	
	private List<String> availableSlots;
	
	private List<DoctorDetails> doctors;

	private Double fee;
	
	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public Date getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public List<String> getAvailableSlots() {
		return availableSlots;
	}

	public void setAvailableSlots(List<String> availableSlots) {
		this.availableSlots = availableSlots;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public List<DoctorDetails> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<DoctorDetails> doctors) {
		this.doctors = doctors;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}
}
