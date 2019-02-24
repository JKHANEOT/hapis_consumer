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
 * The Class CustomerResponse.
 */
public class CustomerResponse extends ResponseMessageModel<CustomerRequest> {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7995837752021710288L;

	/** The message. */
	private CustomerRequest message;

	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#getMessage()
	 */
	@Override
	public CustomerRequest getMessage() {
		return message;
	}

	/* (non-Javadoc)
	 * @see com.nhance.messaging.base.ResponseMessageModel#setMessage(com.nhance.messaging.base.MessageModel)
	 */
	@Override
	public void setMessage(CustomerRequest message) {
		this.message = message;
	}


}
