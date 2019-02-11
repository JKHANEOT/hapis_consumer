/**
 * 
 */
package com.hapis.customer.ui.models.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hidayathulla.Khan
 *
 */
public enum PaymentStatus {

	PAID(1, "PAID"),
	PENDING(2,"PEDNING"),
	FAILED(3,"FAILED"),
	REFUNDED(4,"REFUNDED");
	
	/** The code. */
	private Integer code;

	/** The value. */
	private String value;

	/**
	 * Instantiates a new pyament status type enum.
	 *
	 * @param code
	 *            the code
	 * @param value
	 *            the value
	 */
	PaymentStatus(Integer code, String value) {
		this.code = code;
		this.value = value;
	}

	/** The values. */
	private static Map<Integer, String> values = new HashMap<>();

	/** The codes. */
	private static Map<String, Integer> codes = new HashMap<>();

	static {
		for (PaymentStatus paymentStatusTypeEnum : PaymentStatus.values()) {
			values.put(paymentStatusTypeEnum.code(), paymentStatusTypeEnum.value());
			codes.put(paymentStatusTypeEnum.value(), paymentStatusTypeEnum.code());
		}
	}

	/**
	 * Code.
	 *
	 * @return the integer
	 */
	public Integer code() {
		return this.code;
	}

	/**
	 * Value.
	 *
	 * @return the string
	 */
	public String value() {
		return this.value;
	}

	/**
	 * Gets the value.
	 *
	 * @param code
	 *            the code
	 * @return the value
	 */
	public String getValue(Integer code) {
		return values.get(code);
	}

	/**
	 * Gets the code.
	 *
	 * @param value
	 *            the value
	 * @return the code
	 */
	public Integer getCode(String value) {
		return codes.get(value);
	}
}
