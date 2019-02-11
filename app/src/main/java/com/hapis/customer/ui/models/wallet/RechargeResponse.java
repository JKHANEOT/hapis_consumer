/**
 * 
 */
package com.hapis.customer.ui.models.wallet;

import com.hapis.customer.ui.models.ResponseMessageModel;

/**
 * @author Hidayathulla.Khan
 *
 */
public class RechargeResponse extends ResponseMessageModel<RechargeRequest> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3926609051976711791L;

	private RechargeRequest message;

	@Override
	public RechargeRequest getMessage() {
		return message;
	}

	@Override
	public void setMessage(RechargeRequest message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "RechargeResponse [message=" + message + "]";
	}

}
