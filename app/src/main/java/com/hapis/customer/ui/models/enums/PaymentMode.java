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
public enum PaymentMode {

	COD(1, "CASH ON DELIVERY"),
	CARD(2,"DEBIT / CREDIT CARD"),
	WALLET(3,"HAPIS WALLET"),
	NET_BANKING(4,"NET BANKING");
	
	/** The code. */
	private Integer code;

	/** The value. */
	private String value;

	/**
	 * Instantiates a new pyament mode type enum.
	 *
	 * @param code
	 *            the code
	 * @param value
	 *            the value
	 */
	PaymentMode(Integer code, String value) {
		this.code = code;
		this.value = value;
	}

	/** The values. */
	private static Map<Integer, String> values = new HashMap<>();

	/** The codes. */
	private static Map<String, Integer> codes = new HashMap<>();

	static {
		for (PaymentMode paymentModeTypeEnum : PaymentMode.values()) {
			values.put(paymentModeTypeEnum.code(), paymentModeTypeEnum.value());
			codes.put(paymentModeTypeEnum.value(), paymentModeTypeEnum.code());
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
	public static String getValue(Integer code) {
		return values.get(code);
	}

	/**
	 * Gets the code.
	 *
	 * @param value
	 *            the value
	 * @return the code
	 */
	public static Integer getCode(String value) {
		return codes.get(value);
	}
}
