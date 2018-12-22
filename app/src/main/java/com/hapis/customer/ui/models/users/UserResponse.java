/**
 * Copyright (C) Davinta Technologies 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Davinta Technologies. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Davinta Technologies.
 */

package com.hapis.customer.ui.models.users;

import com.hapis.customer.ui.models.ResponseModel;

/**
 * It is used to create user response.
 *
 * @author Javeed Khan H
 */
public class UserResponse extends ResponseModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The message. */
	private UserRequest message;

	/*
	 * (non-Javadoc)
	 *
	 *
	 */
	public UserRequest getMessage() {
		return message;
	}

	/*
	 * (non-Javadoc)
	 *
	 *
	 */
	public void setMessage(UserRequest message) {
		this.message = message;
	}

}
