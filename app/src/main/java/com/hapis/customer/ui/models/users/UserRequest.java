/**
 * Copyright (C) Davinta Technologies 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Davinta Technologies. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Davinta Technologies.
 */

package com.hapis.customer.ui.models.users;

import com.hapis.customer.ui.models.MessageModel;

import java.util.Date;
import java.util.List;

/**
 * It will use as the request payload from user.
 *
 * @author Sarabjeet Chouhan
 */
public class UserRequest extends MessageModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The fullname. */
	private String firstName;

	/** The last name. */
	private String lastName;

	/** The middle name. */
	private String middleName;

	/** The name prefix. */
	private String namePrefix;

	/** The gender. */
	private Integer gender;

	/** The dob. */
	// @NotNull(message = UserMessageConstant.DATE_OF_BIRTH_MUST_NOT_BE_NULL)
	private Date dateOfBirth;

	/** The email id. */
	private String emailAddress;

	/** The contact number. */
	private String phoneNumber;

	/** The mobile no. */
	private String mobileNo;

	/** The user type. */
	private Integer userType;

	/** The idam ref. */
	private String idamRef;

	/** The password. */
	private String password;

	/** The roles. */
	private String roles;

	/** The roles. */
	private List<String> effectiveRoles;

	/** The agent code. */
	private String agentCode;

	private Boolean isPasswordResetRequired;

	/** The creation date. */
	private Date creationDate;

	/**
	 * Gets the creation date.
	 *
	 * @return the creation date
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date.
	 *
	 * @param creationDate the new creation date
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the middle name.
	 *
	 * @return the middle name
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * Sets the middle name.
	 *
	 * @param middleName the new middle name
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * Gets the name prefix.
	 *
	 * @return the name prefix
	 */
	public String getNamePrefix() {
		return namePrefix;
	}

	/**
	 * Sets the name prefix.
	 *
	 * @param namePrefix the new name prefix
	 */
	public void setNamePrefix(String namePrefix) {
		this.namePrefix = namePrefix;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	/**
	 * Gets the date of birth.
	 *
	 * @return the date of birth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * Sets the date of birth.
	 *
	 * @param dateOfBirth the new date of birth
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Gets the email address.
	 *
	 * @return the email address
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Sets the email address.
	 *
	 * @param emailAddress the new email address
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Gets the phone number.
	 *
	 * @return the phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Sets the phone number.
	 *
	 * @param phoneNumber the new phone number
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Gets the mobile no.
	 *
	 * @return the mobile no
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * Sets the mobile no.
	 *
	 * @param mobileNo the new mobile no
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	/**
	 * Gets the idam ref.
	 *
	 * @return the idam ref
	 */
	public String getIdamRef() {
		return idamRef;
	}

	/**
	 * Sets the idam ref.
	 *
	 * @param idamRef the new idam ref
	 */
	public void setIdamRef(String idamRef) {
		this.idamRef = idamRef;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	public String getRoles() {
		return roles;
	}

	/**
	 * Sets the roles.
	 *
	 * @param roles the new roles
	 */
	public void setRoles(String roles) {
		this.roles = roles;
	}

	/**
	 * Gets the agent code.
	 *
	 * @return the agent code
	 */
	public String getAgentCode() {
		return agentCode;
	}

	/**
	 * Sets the agent code.
	 *
	 * @param agentCode the new agent code
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public List<String> getEffectiveRoles() {
		return effectiveRoles;
	}

	public void setEffectiveRoles(List<String> effectiveRoles) {
		this.effectiveRoles = effectiveRoles;
	}

	public Boolean getIsPasswordResetRequired() {
		return isPasswordResetRequired;
	}

	public void setIsPasswordResetRequired(Boolean isPasswordResetRequired) {
		this.isPasswordResetRequired = isPasswordResetRequired;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserRequest [");
		if (firstName != null) {
			builder.append("firstName=");
			builder.append(firstName);
			builder.append(", ");
		}
		if (lastName != null) {
			builder.append("lastName=");
			builder.append(lastName);
			builder.append(", ");
		}
		if (middleName != null) {
			builder.append("middleName=");
			builder.append(middleName);
			builder.append(", ");
		}
		if (namePrefix != null) {
			builder.append("namePrefix=");
			builder.append(namePrefix);
			builder.append(", ");
		}
		if (gender != null) {
			builder.append("gender=");
			builder.append(gender);
			builder.append(", ");
		}
		if (dateOfBirth != null) {
			builder.append("dateOfBirth=");
			builder.append(dateOfBirth);
			builder.append(", ");
		}
		if (emailAddress != null) {
			builder.append("emailAddress=");
			builder.append(emailAddress);
			builder.append(", ");
		}
		if (phoneNumber != null) {
			builder.append("phoneNumber=");
			builder.append(phoneNumber);
			builder.append(", ");
		}
		if (mobileNo != null) {
			builder.append("mobileNo=");
			builder.append(mobileNo);
			builder.append(", ");
		}
		if (userType != null) {
			builder.append("userType=");
			builder.append(userType);
			builder.append(", ");
		}
		if (idamRef != null) {
			builder.append("idamRef=");
			builder.append(idamRef);
			builder.append(", ");
		}
		if (password != null) {
			builder.append("password=");
			builder.append(password);
			builder.append(", ");
		}
		if (roles != null) {
			builder.append("roles=");
			builder.append(roles);
			builder.append(", ");
		}
		if (agentCode != null) {
			builder.append("agentCode=");
			builder.append(agentCode);
		}
		builder.append("]");
		return builder.toString();
	}

}
