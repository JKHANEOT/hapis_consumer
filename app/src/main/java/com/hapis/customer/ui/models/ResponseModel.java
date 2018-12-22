/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: ResponseModel.java
*
* Date Author Changes
* 25 Jul, 2017 Saroj Created
*/
package com.hapis.customer.ui.models;

/**
 * The Class ResponseModel.
 */
public abstract class ResponseModel implements HapisModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1896152615197682367L;
	
	/** The status. */
	private ResponseStatus status;
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public ResponseStatus getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

}
