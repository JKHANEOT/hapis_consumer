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
 * EnterpriseContactRequest class.
 *
 * @author Javeed
 *
 */
public class EnterpriseContactRequest extends MessageModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The contact type. */
	private Integer contactType;

	/** The name prefix. */
	private String namePrefix;

	/** The first name. */
	private String firstName;

	/** The middle name. */
	private String middleName;

	/** The last name. */
	private String lastName;

	/** The job title. */
	private String jobTitle;

	/** The mobile no. */
	private String mobileNo;

	/** The phone no. */
	private String phoneNo;

	/** The fax no. */
	private String faxNo;

	/** The email address. */
	private String emailAddress;

	/** The other contact. */
	private String otherContact;

	/** The effective date. */
	private Date effectiveDate;

	/** The enterpriseCode code. */
	private String enterpriseCode;

	/**
	 * Gets the contact type.
	 *
	 * @return the contact type
	 */
	public Integer getContactType() {
		return contactType;
	}

	/**
	 * Sets the contact type.
	 *
	 * @param contactType the new contact type
	 */
	public void setContactType(Integer contactType) {
		this.contactType = contactType;
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
	 * Gets the job title.
	 *
	 * @return the job title
	 */
	public String getJobTitle() {
		return jobTitle;
	}

	/**
	 * Sets the job title.
	 *
	 * @param jobTitle the new job title
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
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

	/**
	 * Gets the phone no.
	 *
	 * @return the phone no
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * Sets the phone no.
	 *
	 * @param phoneNo the new phone no
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	/**
	 * Gets the fax no.
	 *
	 * @return the fax no
	 */
	public String getFaxNo() {
		return faxNo;
	}

	/**
	 * Sets the fax no.
	 *
	 * @param faxNo the new fax no
	 */
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
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
	 * Gets the other contact.
	 *
	 * @return the other contact
	 */
	public String getOtherContact() {
		return otherContact;
	}

	/**
	 * Sets the other contact.
	 *
	 * @param otherContact the new other contact
	 */
	public void setOtherContact(String otherContact) {
		this.otherContact = otherContact;
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
		builder.append("EnterpriseContactRequest [");
		if (contactType != null) {
			builder.append("contactType=");
			builder.append(contactType);
			builder.append(", ");
		}
		if (namePrefix != null) {
			builder.append("namePrefix=");
			builder.append(namePrefix);
			builder.append(", ");
		}
		if (firstName != null) {
			builder.append("firstName=");
			builder.append(firstName);
			builder.append(", ");
		}
		if (middleName != null) {
			builder.append("middleName=");
			builder.append(middleName);
			builder.append(", ");
		}
		if (lastName != null) {
			builder.append("lastName=");
			builder.append(lastName);
			builder.append(", ");
		}
		if (jobTitle != null) {
			builder.append("jobTitle=");
			builder.append(jobTitle);
			builder.append(", ");
		}
		if (mobileNo != null) {
			builder.append("mobileNo=");
			builder.append(mobileNo);
			builder.append(", ");
		}
		if (phoneNo != null) {
			builder.append("phoneNo=");
			builder.append(phoneNo);
			builder.append(", ");
		}
		if (faxNo != null) {
			builder.append("faxNo=");
			builder.append(faxNo);
			builder.append(", ");
		}
		if (emailAddress != null) {
			builder.append("emailAddress=");
			builder.append(emailAddress);
			builder.append(", ");
		}
		if (otherContact != null) {
			builder.append("otherContact=");
			builder.append(otherContact);
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
