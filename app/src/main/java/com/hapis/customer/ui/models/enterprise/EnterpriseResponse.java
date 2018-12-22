/**
 * Copyright (C) Davinta Technologies 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Davinta Technologies. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Davinta Technologies.
 */

package com.hapis.customer.ui.models.enterprise;

import com.hapis.customer.ui.models.ResponseMessageModel;

/**
 * EnterpriseAddressResponse clas.
 *
 * @author Javeed
 *
 */
public class EnterpriseResponse extends ResponseMessageModel<EnterpriseRequest> {

	private static final long serialVersionUID = 1L;

	private EnterpriseRequest message;

	@Override
	public EnterpriseRequest getMessage() {
		return message;
	}

	@Override
	public void setMessage(EnterpriseRequest enterpriseRequest) {
		this.message = enterpriseRequest;
	}

}
