/**
 * 
 */
package com.hapis.customer.ui.models.wallet;

import com.hapis.customer.ui.models.MessageModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Hidayathulla.Khan
 *
 */
public class RechargeRequest extends MessageModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5043174496319595097L;
	
	/** The customer code. */
	private String customerCode;
	
	private String customerBank;
	
	private String customerCardNo;
	
	private BigDecimal customerBalance;
	
	private Date creationDate;
	
	private String lastModifiedBy;
	
	private  Date lastModifiedDate;
	
	private BigDecimal rechargeAmount;

	public BigDecimal getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(BigDecimal rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerBank() {
		return customerBank;
	}

	public void setCustomerBank(String customerBank) {
		this.customerBank = customerBank;
	}

	public String getCustomerCardNo() {
		return customerCardNo;
	}

	public void setCustomerCardNo(String customerCardNo) {
		this.customerCardNo = customerCardNo;
	}

	public BigDecimal getCustomerBalance() {
		return customerBalance;
	}

	public void setCustomerBalance(BigDecimal customerBalance) {
		this.customerBalance = customerBalance;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Override
	public String toString() {
		return "RechargeRequest [customerCode=" + customerCode + ", customerBank=" + customerBank + ", customerCardNo="
				+ customerCardNo + ", customerBalance=" + customerBalance
				+ ", creationDate=" + creationDate + ", lastModifiedBy=" + lastModifiedBy + ", lastModifiedDate="
				+ lastModifiedDate + ", rechargeAmount=" + rechargeAmount + "]";
	}

	
}
