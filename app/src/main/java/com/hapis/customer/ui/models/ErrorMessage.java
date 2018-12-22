/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: ErrorMessage.java
*
* Date Author Changes
* 29 Aug, 2017 Saroj Created
*/
package com.hapis.customer.ui.models;

import java.io.Serializable;

/**
 * The Class ErrorMessage.
 */
public class ErrorMessage implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The message description. */
	private String messageDescription;
	
	/** The error code. */
	private String errorCode;

	/**
	 * Gets the message description.
	 *
	 * @return the message description
	 */
	public String getMessageDescription() {
		return messageDescription;
	}

	/**
	 * Sets the message description.
	 *
	 * @param messageDescription the new message description
	 */
	public void setMessageDescription(String messageDescription) {
		this.messageDescription = messageDescription;
	}

	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Sets the error code.
	 *
	 * @param errorCode the new error code
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
