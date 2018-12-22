/**
 * Copyright (C) Davinta Technologies 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Davinta Technologies. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Davinta Technologies.
 */

package com.hapis.customer.ui.models.enterprise;

import com.hapis.customer.ui.models.MessageModel;

import java.util.Date;

/**
 * EnterpriseAddressRequest class.
 *
 * @author Javeed
 *
 */
public class EnterpriseAddressRequest extends MessageModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The address type. */
	private Integer addressType;

	/** The address line 1. */
	private String addressLine1;

	/** The address line 2. */
	private String addressLine2;

	/** The address line 3. */
	private String addressLine3;

	/** The village. */
	private String village;

	/** The city. */
	private String city;

	/** The sub district. */
	private String subDistrict;

	/** The district. */
	private String district;

	/** The postal code. */
	private String postalCode;

	/** The state code. */
	private String stateCode;

	/** The country code. */
	private String countryCode;

	/** The census code. */
	private String censusCode;

	/** The is verified. */
	private Boolean isVerified;

	/** The document collected. */
	private Boolean documentCollected;

	/** The effective date. */
	private Date effectiveDate;

	/** The enterprise code. */
	private String enterpriseCode;

	/**
	 * Gets the address line 1.
	 *
	 * @return the address line 1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}

	/**
	 * Sets the address line 1.
	 *
	 * @param addressLine1 the new address line 1
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	/**
	 * Gets the address line 2.
	 *
	 * @return the address line 2
	 */
	public String getAddressLine2() {
		return addressLine2;
	}

	/**
	 * Sets the address line 2.
	 *
	 * @param addressLine2 the new address line 2
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	/**
	 * Gets the address line 3.
	 *
	 * @return the address line 3
	 */
	public String getAddressLine3() {
		return addressLine3;
	}

	/**
	 * Sets the address line 3.
	 *
	 * @param addressLine3 the new address line 3
	 */
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	/**
	 * Gets the village.
	 *
	 * @return the village
	 */
	public String getVillage() {
		return village;
	}

	/**
	 * Sets the village.
	 *
	 * @param village the new village
	 */
	public void setVillage(String village) {
		this.village = village;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the sub district.
	 *
	 * @return the sub district
	 */
	public String getSubDistrict() {
		return subDistrict;
	}

	/**
	 * Sets the sub district.
	 *
	 * @param subDistrict the new sub district
	 */
	public void setSubDistrict(String subDistrict) {
		this.subDistrict = subDistrict;
	}

	/**
	 * Gets the district.
	 *
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * Sets the district.
	 *
	 * @param district the new district
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * Gets the postal code.
	 *
	 * @return the postal code
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * Sets the postal code.
	 *
	 * @param postalCode the new postal code
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * Gets the state code.
	 *
	 * @return the state code
	 */
	public String getStateCode() {
		return stateCode;
	}

	/**
	 * Sets the state code.
	 *
	 * @param stateCode the new state code
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	/**
	 * Gets the country code.
	 *
	 * @return the country code
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * Sets the country code.
	 *
	 * @param countryCode the new country code
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * Gets the census code.
	 *
	 * @return the census code
	 */
	public String getCensusCode() {
		return censusCode;
	}

	/**
	 * Sets the census code.
	 *
	 * @param censusCode the new census code
	 */
	public void setCensusCode(String censusCode) {
		this.censusCode = censusCode;
	}

	/**
	 * Gets the checks if is verified.
	 *
	 * @return the checks if is verified
	 */
	public Boolean getIsVerified() {
		return isVerified;
	}

	/**
	 * Sets the checks if is verified.
	 *
	 * @param isVerified the new checks if is verified
	 */
	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}

	/**
	 * Gets the address type.
	 *
	 * @return the address type
	 */
	public Integer getAddressType() {
		return addressType;
	}

	/**
	 * Sets the address type.
	 *
	 * @param addressType the new address type
	 */
	public void setAddressType(Integer addressType) {
		this.addressType = addressType;
	}

	/**
	 * Gets the document collected.
	 *
	 * @return the document collected
	 */
	public Boolean getDocumentCollected() {
		return documentCollected;
	}

	/**
	 * Sets the document collected.
	 *
	 * @param documentCollected the new document collected
	 */
	public void setDocumentCollected(Boolean documentCollected) {
		this.documentCollected = documentCollected;
	}

	/**
	 * Gets the effective date.
	 *
	 * @return the effective date
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * Sets the effective date.
	 *
	 * @param effectiveDate the new effective date
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * Gets the enterprise code.
	 *
	 * @return the enterprise code
	 */
	public String getEnterpriseCode() {
		return enterpriseCode;
	}

	/**
	 * Sets the enterprise code.
	 *
	 * @param enterpriseCode the new enterprise code
	 */
	public void setEnterpriseCode(String enterpriseCode) {
		this.enterpriseCode = enterpriseCode;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EnterpriseAddressRequest [");
		if (addressType != null) {
			builder.append("addressType=");
			builder.append(addressType);
			builder.append(", ");
		}
		if (addressLine1 != null) {
			builder.append("addressLine1=");
			builder.append(addressLine1);
			builder.append(", ");
		}
		if (addressLine2 != null) {
			builder.append("addressLine2=");
			builder.append(addressLine2);
			builder.append(", ");
		}
		if (addressLine3 != null) {
			builder.append("addressLine3=");
			builder.append(addressLine3);
			builder.append(", ");
		}
		if (village != null) {
			builder.append("village=");
			builder.append(village);
			builder.append(", ");
		}
		if (city != null) {
			builder.append("city=");
			builder.append(city);
			builder.append(", ");
		}
		if (subDistrict != null) {
			builder.append("subDistrict=");
			builder.append(subDistrict);
			builder.append(", ");
		}
		if (district != null) {
			builder.append("district=");
			builder.append(district);
			builder.append(", ");
		}
		if (postalCode != null) {
			builder.append("postalCode=");
			builder.append(postalCode);
			builder.append(", ");
		}
		if (stateCode != null) {
			builder.append("stateCode=");
			builder.append(stateCode);
			builder.append(", ");
		}
		if (countryCode != null) {
			builder.append("countryCode=");
			builder.append(countryCode);
			builder.append(", ");
		}
		if (censusCode != null) {
			builder.append("censusCode=");
			builder.append(censusCode);
			builder.append(", ");
		}
		if (isVerified != null) {
			builder.append("isVerified=");
			builder.append(isVerified);
			builder.append(", ");
		}
		if (documentCollected != null) {
			builder.append("documentCollected=");
			builder.append(documentCollected);
			builder.append(", ");
		}
		if (effectiveDate != null) {
			builder.append("effectiveDate=");
			builder.append(effectiveDate);
			builder.append(", ");
		}
		if (enterpriseCode != null) {
			builder.append("enterpriseCode=");
			builder.append(enterpriseCode);
		}
		builder.append("]");
		return builder.toString();
	}

}
