/**
 * 
 */
package com.hapis.customer.ui.models.consultation;

import com.hapis.customer.ui.models.MessageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hidayathulla.Khan
 *
 */
public class Prescription extends MessageModel {

	private static final long serialVersionUID = 2001796835749277060L;

	private List<Tablet> tablets = new ArrayList<>();
	
	private List<Syrup> syrups = new ArrayList<>();
	
	private List<Ointment> ointments = new ArrayList<>();
	
	private List<Soap> soap = new ArrayList<>();
	
	private String prescriptionImage;


	public String getPrescriptionImage() {
		return prescriptionImage;
	}

	public void setPrescriptionImage(String prescriptionImage) {
		this.prescriptionImage = prescriptionImage;
	}

	public List<Tablet> getTablets() {
		return tablets;
	}

	public void setTablets(List<Tablet> tablets) {
		this.tablets = tablets;
	}

	public List<Syrup> getSyrups() {
		return syrups;
	}

	public void setSyrups(List<Syrup> syrups) {
		this.syrups = syrups;
	}

	public List<Ointment> getOintments() {
		return ointments;
	}

	public void setOintments(List<Ointment> ointments) {
		this.ointments = ointments;
	}

	public List<Soap> getSoap() {
		return soap;
	}

	public void setSoap(List<Soap> soap) {
		this.soap = soap;
	}

	@Override
	public String toString() {
		return "Prescription [tablets=" + tablets + ", syrups=" + syrups + ", ointments=" + ointments + ", soap=" + soap
				+ ", prescriptionImage=" + prescriptionImage + "]";
	}

}
