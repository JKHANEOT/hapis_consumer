/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: UserModelResponse.java
*
* Date Author Changes
* 1 Aug, 2017 Sudhanshu Created
*/

package com.hapis.customer.ui.models;

/**
 * The Class UserModelResponse.
 */
public class UserModelResponse extends ResponseMessageModel<UserModel> {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7995837752021710288L;

	/** The message. */
	private UserModel message;

	private String customerCode;

	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#getMessage()
	 */
	@Override
	public UserModel getMessage() {
		return message;
	}

	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#setMessage(com.nhance.messaging.base.MessageModel)
	 */
	@Override
	public void setMessage(UserModel message) {
		this.message = message;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	
}
