/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: Status.java
*
* Date Author Changes
* 25 Jul, 2017 Saroj Created
*/
package com.hapis.customer.ui.models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * The Class ResponseStatus.
 */
public class ResponseStatus implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6849678898532369124L;
	/**
	 * Success Status code.
	 */
	public static final int SUCCESS = 1;
	/**
	 * Failure Status code.
	 */
	public static final int FAILED = 0;

	/** The status code. */
	private Integer statusCode;
	
	/** The error messages. */
	private List<ErrorMessage> errorMessages;

	private String messageDescription;

	private String errorCode;
	
	/** The error arguments. */
	private Object[] errorArguments;

	/**
	 * Gets errorArguments.
	 * @return the errorArguments
	 */
	public Object[] getErrorArguments() {
		return errorArguments;
	}

	/**
	 * Sets errorArguments.
	 * @param errorArguments the errorArguments to set
	 */
	public void setErrorArguments(Object[] errorArguments) {
		this.errorArguments = errorArguments;
	}

	/**
	 * Instantiates a new status.
	 */
	public ResponseStatus() {
		// Default Constructor
	}

	public String getMessageDescription() {
		return messageDescription;
	}

	public void setMessageDescription(String messageDescription) {
		this.messageDescription = messageDescription;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Instantiates a new status.
	 *
	 * @param statusCode the status code
	 */
	public ResponseStatus(Integer statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * Gets the status code.
	 *
	 * @return the status code
	 */
	public Integer getStatusCode() {
		return statusCode;
	}

	/**
	 * Sets the status code.
	 *
	 * @param statusCode the new status code
	 */
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * Gets the error messages.
	 *
	 * @return the error messages
	 */
	public List<ErrorMessage> getErrorMessages() {
		return errorMessages;
	}

	/**
	 * Sets the error messages.
	 *
	 * @param errorMessages the new error messages
	 */
	public void setErrorMessages(List<ErrorMessage> errorMessages) {
		this.errorMessages = errorMessages;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResponseStatus [statusCode=");
		builder.append(statusCode);
		builder.append(", errorMessages=");
		builder.append(errorMessages);
		builder.append(", errorArguments=");
		builder.append(Arrays.toString(errorArguments));
		builder.append("]");
		return builder.toString();
	}

}
