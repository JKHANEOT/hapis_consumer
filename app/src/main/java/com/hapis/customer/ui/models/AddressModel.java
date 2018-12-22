/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: AddressModel.java
*
* Date Author Changes
* 27 Jul, 2017 Saroj Created
*/
package com.hapis.customer.ui.models;

/**
 * The Class AddressModel.
 */
public class AddressModel extends MessageModel {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6178155659378255006L;

	/** The name. */
	private String name;

	private String addressName;
	
	/** The mobile number. */
	private String mobileNumber;
	
	/** The line one. */
	private String addressLine1;
	
	/** The line two. */
	private String addressLine2;
	
	/** The address type. */
	private Integer addressType;
	
	/** The country. */
	private String country;
	
	/** The stateCode. */
	private String stateCode;
	
	/** The district. */
	private String district;
	
	/** The city. */
	private String city;
	
	/** The pin code. */
	private String pinCode;
	
	/** The latitude. */
	private String latitude;
	
	/** The longitude. */
	private String longitude;
	
	/** The add status. */
	private Integer addStatus;

	private String isdCode;

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	/**
	 * Gets the mobile number.
	 *
	 * @return the mobile number
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * Sets the mobile number.
	 *
	 * @param mobileNumber the new mobile number
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * Gets the line one.
	 *
	 * @return the line one
	 */
	public String getAddressLine1() {
		return addressLine1;
	}

	/**
	 * Sets the line one.
	 *
	 * @param addressLine1 the new line one
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	/**
	 * Gets the line two.
	 *
	 * @return the line two
	 */
	public String getAddressLine2() {
		return addressLine2;
	}

	/**
	 * Sets the line two.
	 *
	 * @param addressLine2 the new line two
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
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
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the stateCode.
	 *
	 * @return the stateCode
	 */
	public String getStateCode() {
		return stateCode;
	}

	/**
	 * Sets the stateCode.
	 *
	 * @param stateCode the new stateCode
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
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
	 * Gets the pin code.
	 *
	 * @return the pin code
	 */
	public String getPinCode() {
		return pinCode;
	}

	/**
	 * Sets the pin code.
	 *
	 * @param pinCode the new pin code
	 */
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	/**
	 * Gets the latitude.
	 *
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * Sets the latitude.
	 *
	 * @param latitude the new latitude
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * Gets the longitude.
	 *
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * Sets the longitude.
	 *
	 * @param longitude the new longitude
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AddressDto [name=" + name + ", mobileNumber=" + mobileNumber + ", addressLine1=" + addressLine1 + ", addressLine2="
				+ addressLine2 + ", addressType=" + addressType + ", country=" + country + ", stateCode=" + stateCode + ", district="
				+ district + ", city=" + city + ", pinCode=" + pinCode + ", latitude=" + latitude + ", longitude="
				+ longitude + "]";
	}

	/**
	 * Gets the adds the status.
	 *
	 * @return the adds the status
	 */
	public Integer getAddStatus() {
		return addStatus;
	}

	/**
	 * Sets the adds the status.
	 *
	 * @param addStatus the new adds the status
	 */
	public void setAddStatus(Integer addStatus) {
		this.addStatus = addStatus;
	}

	public String getIsdCode() {
		return isdCode;
	}

	public void setIsdCode(String isdCode) {
		this.isdCode = isdCode;
	}
	
}
