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
import java.util.HashSet;
import java.util.Set;


/**
 * Enterprise class.
 *
 * @author Javeed
 *
 */
public class EnterpriseRequest extends MessageModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The list of enterprise address. */
	private Set<EnterpriseAddressRequest> addresses = new HashSet<>();

	/** The list of enterprise contacts. */
	private Set<EnterpriseContactRequest> contacts = new HashSet<>();

	private String enterpriseCode;

	/** The enterprise type. */
	private Integer enterpriseType;

	/** The enterprise name. */
	private String enterpriseName;

	/** The legal status. */
	private Integer legalStatus;

	/** The tax id 1. */
	private String taxId1;

	/** The tax id 2. */
	private String taxId2;

	/** The doing business as. */
	private String doingBusinessAs;

	/** The financial year start month. */
	private Integer financialYearStartMonth;

	/** The financial year end month. */
	private Integer financialYearEndMonth;

	/** The gl identifier. */
	private String glIdentifier;

	/** The business type. */
	private Integer businessType;

	/** The establishment date. */
	private Date establishmentDate;

	/** The tax residence country. */
	private String taxResidenceCountryCode;

	/** The registration code. */
	private String registrationCode;

	/** The registration date. */
	private Date registrationDate;

	/** The parent enterprise id. */
	private String parentEnterpriseCode;

	/** The other bcnm. */
	private Boolean otherBcnm;

	/** The created by. */
//	private String createdBy;

	/** The creation date. */
	private Date creationDate;

	public String getEnterpriseCode() {
		return enterpriseCode;
	}

	public void setEnterpriseCode(String enterpriseCode) {
		this.enterpriseCode = enterpriseCode;
	}

	/**
	 * Gets the list of enterprise address.
	 *
	 * @return the list of enterprise address
	 */
	public Set<EnterpriseAddressRequest> getAddresses() {
		return addresses;
	}

	/**
	 * Sets the list of enterprise address.
	 *
	 * @param listOfEnterpriseAddress the new list of enterprise address
	 */
	public void setAddresses(Set<EnterpriseAddressRequest> listOfEnterpriseAddress) {
		this.addresses = listOfEnterpriseAddress;
	}

	/**
	 * Gets the enterprise type.
	 *
	 * @return the enterprise type
	 */
	public Integer getEnterpriseType() {
		return enterpriseType;
	}

	/**
	 * Sets the enterprise type.
	 *
	 * @param enterpriseType the new enterprise type
	 */
	public void setEnterpriseType(Integer enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	/**
	 * Gets the enterprise name.
	 *
	 * @return the enterprise name
	 */
	public String getEnterpriseName() {
		return enterpriseName;
	}

	/**
	 * Sets the enterprise name.
	 *
	 * @param enterpriseName the new enterprise name
	 */
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	/**
	 * Gets the legal status.
	 *
	 * @return the legal status
	 */
	public Integer getLegalStatus() {
		return legalStatus;
	}

	/**
	 * Sets the legal status.
	 *
	 * @param legalStatus the new legal status
	 */
	public void setLegalStatus(Integer legalStatus) {
		this.legalStatus = legalStatus;
	}

	/**
	 * Gets the tax id 1.
	 *
	 * @return the tax id 1
	 */
	public String getTaxId1() {
		return taxId1;
	}

	/**
	 * Sets the tax id 1.
	 *
	 * @param taxId1 the new tax id 1
	 */
	public void setTaxId1(String taxId1) {
		this.taxId1 = taxId1;
	}

	/**
	 * Gets the tax id 2.
	 *
	 * @return the tax id 2
	 */
	public String getTaxId2() {
		return taxId2;
	}

	/**
	 * Sets the tax id 2.
	 *
	 * @param taxId2 the new tax id 2
	 */
	public void setTaxId2(String taxId2) {
		this.taxId2 = taxId2;
	}

	/**
	 * Gets the doing business as.
	 *
	 * @return the doing business as
	 */
	public String getDoingBusinessAs() {
		return doingBusinessAs;
	}

	/**
	 * Sets the doing business as.
	 *
	 * @param doingBusinessAs the new doing business as
	 */
	public void setDoingBusinessAs(String doingBusinessAs) {
		this.doingBusinessAs = doingBusinessAs;
	}

	/**
	 * Gets the financial year start month.
	 *
	 * @return the financial year start month
	 */
	public Integer getFinancialYearStartMonth() {
		return financialYearStartMonth;
	}

	/**
	 * Sets the financial year start month.
	 *
	 * @param financialYearStartMonth the new financial year start month
	 */
	public void setFinancialYearStartMonth(Integer financialYearStartMonth) {
		this.financialYearStartMonth = financialYearStartMonth;
	}

	/**
	 * Gets the financial year end month.
	 *
	 * @return the financial year end month
	 */
	public Integer getFinancialYearEndMonth() {
		return financialYearEndMonth;
	}

	/**
	 * Sets the financial year end month.
	 *
	 * @param financialYearEndMonth the new financial year end month
	 */
	public void setFinancialYearEndMonth(Integer financialYearEndMonth) {
		this.financialYearEndMonth = financialYearEndMonth;
	}

	/**
	 * Gets the gl identifier.
	 *
	 * @return the gl identifier
	 */
	public String getGlIdentifier() {
		return glIdentifier;
	}

	/**
	 * Sets the gl identifier.
	 *
	 * @param glIdentifier the new gl identifier
	 */
	public void setGlIdentifier(String glIdentifier) {
		this.glIdentifier = glIdentifier;
	}

	/**
	 * Gets the business type.
	 *
	 * @return the business type
	 */
	public Integer getBusinessType() {
		return businessType;
	}

	/**
	 * Sets the business type.
	 *
	 * @param businessType the new business type
	 */
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	/**
	 * Gets the establishment date.
	 *
	 * @return the establishment date
	 */
	public Date getEstablishmentDate() {
		return establishmentDate;
	}

	/**
	 * Sets the establishment date.
	 *
	 * @param establishmentDate the new establishment date
	 */
	public void setEstablishmentDate(Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}

	/**
	 * Gets the tax residence country code.
	 *
	 * @return the tax residence country code
	 */
	public String getTaxResidenceCountryCode() {
		return taxResidenceCountryCode;
	}

	/**
	 * Sets the tax residence country code.
	 *
	 * @param taxResidenceCountryCode the new tax residence country code
	 */
	public void setTaxResidenceCountryCode(String taxResidenceCountryCode) {
		this.taxResidenceCountryCode = taxResidenceCountryCode;
	}

	/**
	 * Gets the registration code.
	 *
	 * @return the registration code
	 */
	public String getRegistrationCode() {
		return registrationCode;
	}

	/**
	 * Sets the registration code.
	 *
	 * @param registrationCode the new registration code
	 */
	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}

	/**
	 * Gets the registration date.
	 *
	 * @return the registration date
	 */
	public Date getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * Sets the registration date.
	 *
	 * @param registrationDate the new registration date
	 */
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	/**
	 * Gets the parent enterprise code.
	 *
	 * @return the parent enterprise code
	 */
	public String getParentEnterpriseCode() {
		return parentEnterpriseCode;
	}

	/**
	 * Sets the parent enterprise code.
	 *
	 * @param parentEnterpriseCode the new parent enterprise code
	 */
	public void setParentEnterpriseCode(String parentEnterpriseCode) {
		this.parentEnterpriseCode = parentEnterpriseCode;
	}

	/**
	 * Gets the contacts.
	 *
	 * @return the contacts
	 */
	public Set<EnterpriseContactRequest> getContacts() {
		return contacts;
	}

	/**
	 * Sets the contacts.
	 *
	 * @param contacts the new contacts
	 */
	public void setContacts(Set<EnterpriseContactRequest> contacts) {
		this.contacts = contacts;
	}


	/**
	 * Gets the other bcnm.
	 *
	 * @return the otherBcnm
	 */
	public Boolean getOtherBcnm() {
		return otherBcnm;
	}

	/**
	 * Sets the other bcnm.
	 *
	 * @param otherBcnm the otherBcnm to set
	 */
	public void setOtherBcnm(Boolean otherBcnm) {
		this.otherBcnm = otherBcnm;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
//	public String getCreatedBy() {
//		return createdBy;
//	}

	/**
	 * Gets the creation date.
	 *
	 * @return the creation date
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
//	public void setCreatedBy(String createdBy) {
//		this.createdBy = createdBy;
//	}

	/**
	 * Sets the creation date.
	 *
	 * @param creationDate the new creation date
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EnterpriseRequest [");
		if (addresses != null) {
			builder.append("addresses=");
			builder.append(addresses);
			builder.append(", ");
		}
		if (contacts != null) {
			builder.append("contacts=");
			builder.append(contacts);
			builder.append(", ");
		}
		if (enterpriseType != null) {
			builder.append("enterpriseType=");
			builder.append(enterpriseType);
			builder.append(", ");
		}
		if (enterpriseName != null) {
			builder.append("enterpriseName=");
			builder.append(enterpriseName);
			builder.append(", ");
		}
		if (legalStatus != null) {
			builder.append("legalStatus=");
			builder.append(legalStatus);
			builder.append(", ");
		}
		if (taxId1 != null) {
			builder.append("taxId1=");
			builder.append(taxId1);
			builder.append(", ");
		}
		if (taxId2 != null) {
			builder.append("taxId2=");
			builder.append(taxId2);
			builder.append(", ");
		}
		if (doingBusinessAs != null) {
			builder.append("doingBusinessAs=");
			builder.append(doingBusinessAs);
			builder.append(", ");
		}
		if (financialYearStartMonth != null) {
			builder.append("financialYearStartMonth=");
			builder.append(financialYearStartMonth);
			builder.append(", ");
		}
		if (financialYearEndMonth != null) {
			builder.append("financialYearEndMonth=");
			builder.append(financialYearEndMonth);
			builder.append(", ");
		}
		if (glIdentifier != null) {
			builder.append("glIdentifier=");
			builder.append(glIdentifier);
			builder.append(", ");
		}
		if (businessType != null) {
			builder.append("businessType=");
			builder.append(businessType);
			builder.append(", ");
		}
		if (establishmentDate != null) {
			builder.append("establishmentDate=");
			builder.append(establishmentDate);
			builder.append(", ");
		}
		if (taxResidenceCountryCode != null) {
			builder.append("taxResidenceCountryCode=");
			builder.append(taxResidenceCountryCode);
			builder.append(", ");
		}
		if (registrationCode != null) {
			builder.append("registrationCode=");
			builder.append(registrationCode);
			builder.append(", ");
		}
		if (registrationDate != null) {
			builder.append("registrationDate=");
			builder.append(registrationDate);
			builder.append(", ");
		}
		if (parentEnterpriseCode != null) {
			builder.append("parentEnterpriseCode=");
			builder.append(parentEnterpriseCode);
			builder.append(", ");
		}
		if (otherBcnm != null) {
			builder.append("otherBcnm=");
			builder.append(otherBcnm);
		}
		builder.append("]");
		return builder.toString();
	}
}
