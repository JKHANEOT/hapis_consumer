/**
 * 
 */
package com.hapis.customer.ui.models.appointments;

import com.hapis.customer.ui.models.enterprise.EnterpriseRequest;
import com.hapis.customer.ui.models.users.UserRequest;

/**
 * @author Hidayathulla.Khan
 *
 */
public class AppointmentRequest extends AppointmentBaseRequest {

	private static final long serialVersionUID = -5950575486385534665L;
	
	private String appointmentCode;
	
	private String customerCode;
	
	private String appointmentDate;

	private UserRequest doctorDetails;

	private String doctorCode;

	private EnterpriseRequest enterpriseRequest;

	private String hospitalCode;
	
	private Integer slotBooked;
	
	private String externalReference;
	
	private String patientName;
	
	private String patientGender;
	
	private Integer patientAge;
	
	private String patientRelation;

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

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

	public Integer getSlotBooked() {
		return slotBooked;
	}

	public void setSlotBooked(Integer slotBooked) {
		this.slotBooked = slotBooked;
	}

	public String getExternalReference() {
		return externalReference;
	}

	public void setExternalReference(String externalReference) {
		this.externalReference = externalReference;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientGender() {
		return patientGender;
	}

	public void setPatientGender(String patientGender) {
		this.patientGender = patientGender;
	}

	public Integer getPatientAge() {
		return patientAge;
	}

	public void setPatientAge(Integer patientAge) {
		this.patientAge = patientAge;
	}

	public String getPatientRelation() {
		return patientRelation;
	}

	public void setPatientRelation(String patientRelation) {
		this.patientRelation = patientRelation;
	}

	public String getAppointmentCode() {
		return appointmentCode;
	}

	public void setAppointmentCode(String appointmentCode) {
		this.appointmentCode = appointmentCode;
	}

	public UserRequest getDoctorDetails() {
		return doctorDetails;
	}

	public void setDoctorDetails(UserRequest doctorDetails) {
		this.doctorDetails = doctorDetails;
	}

	public EnterpriseRequest getEnterpriseRequest() {
		return enterpriseRequest;
	}

	public void setEnterpriseRequest(EnterpriseRequest enterpriseRequest) {
		this.enterpriseRequest = enterpriseRequest;
	}
}
