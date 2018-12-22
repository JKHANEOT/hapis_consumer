/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: ResponseMessageModel.java
*
* Date Author Changes
* 1 Aug, 2017 Saroj Created
*/
package com.hapis.customer.ui.models;

/**
 * The Class ResponseMessageModel.
 *
 * @param <M> the generic type
 */
public abstract class ResponseMessageModel<M extends MessageModel> extends ResponseModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7777856636963847605L;

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public abstract M getMessage();
	
	/**
	 * Sets the message.
	 *
	 * @param m the new message
	 */
	public abstract void setMessage(M m);

}
