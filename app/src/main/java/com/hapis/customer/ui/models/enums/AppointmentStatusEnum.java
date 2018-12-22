/**
 * Copyright (C) Davinta Technologies 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Davinta Technologies. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Davinta Technologies.
 */

package com.hapis.customer.ui.models.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * The Enum EnterpriseTypeEnum.
 * 
 * @author Javeed Khan H
 *
 */
public enum AppointmentStatusEnum {

	/** The tsp. */
	BOOKED(601, "BOOKED"),

	/** The hospital. */
	CANCEL(602, "CANCEL"),

	/** The clinic. */
	CLOSED(603, "CLOSED");

	/** The code. */
	private Integer code;

	/** The value. */
	private String value;

	/**
	 * Instantiates a new enterprise type enum.
	 *
	 * @param code
	 *            the code
	 * @param value
	 *            the value
	 */
	AppointmentStatusEnum(Integer code, String value) {
		this.code = code;
		this.value = value;
	}

	/** The values. */
	private static Map<Integer, String> values = new HashMap<>();

	/** The codes. */
	private static Map<String, Integer> codes = new HashMap<>();

	static {
		for (AppointmentStatusEnum enterpriseTypeEnum : AppointmentStatusEnum.values()) {
			values.put(enterpriseTypeEnum.code(), enterpriseTypeEnum.value());
			codes.put(enterpriseTypeEnum.value(), enterpriseTypeEnum.code());
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
