/**
 * 
 */
package com.hapis.customer.ui.models.appointments;

import com.hapis.customer.ui.models.MessageModel;

/**
 * @author Hidayathulla.Khan
 *
 */
public class DoctorDetails extends MessageModel {

	private static final long serialVersionUID = -3866159213779172342L;
	
	private String doctorCode;
	
	private String hospitalCode;
	
	private String fullName;
	
	private String specialization;

	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	

}
