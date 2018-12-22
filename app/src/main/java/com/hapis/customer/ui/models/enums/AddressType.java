/* Copyright � Inspirion 2017. All rights reserved.
*
* This software is the confidential and proprietary information
* of Inspirion. You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms and
* conditions entered into with Inspirion.
*
* Id: AddressType.java
*
* Date Author Changes
* 8 Jun, 2017 Saroj Created
*/
package com.hapis.customer.ui.models.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * The Enum AddressType..
 */
public enum AddressType {

	/** The default. */
	DEFAULT(1, "Hapis"),
	
	/** The permanent. */
	PERMANENT(2, "Permanent"),
	
	/** The temp. */
	TEMP(3, "Temprorary"),
	
	/** The billing. */
	BILLING(4, "Billing"),
	
	/** The office. */
	OFFICE(5, "Office"),
	
	BRANCH_OFFICE(6, "Branch Office"),
	
	/** The user profile default. */
	USER_PROFILE_DEFAULT(7, "User Profile Default_"),
	
	/** The others. */
	OTHERS(99, "Others" );
	
	/** The code. */
	private Integer code;
	
	/** The text. */
	private String text;
	
	/** The address type map. */
	private static Map<Integer, String> addressTypeMap = new HashMap<Integer, String>();

	static {
		for ( AddressType addressType : AddressType.values() ) {
			addressTypeMap.put( addressType.getCode(), addressType.getText() );
		}
	}
	
	/**
	 * Instantiates a new address type.
	 *
	 * @param code the code
	 * @param text the text
	 */
    AddressType(Integer code, String text) {
		this.code = code;
		this.text = text;
	}
	
	/**
	 * Gets the address type map.
	 *
	 * @return the address type map
	 */
	public static Map<Integer, String> getAddressTypeMap() {
		return addressTypeMap;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Gets the address type map.
	 *
	 * @param code the code
	 * @return the address type map
	 */
	public static String getAddressTypeMap( final Integer code ) {
		if ( addressTypeMap.get( code ) != null )  {
			return addressTypeMap.get( code );
		}
		return "";
	}
}
