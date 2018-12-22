/**
 * Copyright (C) Davinta Technologies 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Davinta Technologies. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Davinta Technologies.
 */

package com.hapis.customer.ui.models.enterprise;

/**
 * EnterpriseConstants class.
 *
 * @author Srinivas B
 *
 */
public final class EnterpriseConstants {

	/** The enterprise path. */
	public static final String ENTERPRISE_PATH = "/enterprises";

	/** The system. */
	public static final String SYSTEM = "SYSTEM";

	/** The Constant ACTIVATE_ENTERPRISE_BY_ENTERPRISE_CODE. */
	public static final String ACTIVATE_ENTERPRISE_BY_ENTERPRISE_CODE = "/activate/{enterpriseCode}/{loggedInUserId}";

	/** The Constant GET_ALL_CHILD_ENTERPRISES. */
	public static final String GET_ALL_CHILD_ENTERPRISES = "/{enterpriseCode}/childEnterprises";

	/** The Constant GET_ACTIVE_CHILD_ENTERPRISES. */
	public static final String GET_ACTIVE_CHILD_ENTERPRISES = "/{enterpriseCode}/childEnterprises/activeOnly";

	/** The Constant GET_ALL_CHILD_ENTERPRISES. */
	public static final String GET_HIERARCHY_OF_ENTERPRISE = "/{enterpriseCode}/hierarchyOfEnterprise";

	/** The Constant GET_ALL_CHILD_ENTERPRISES. */
	public static final String GET_HIERARCHY_OF_ENTERPRISE_ACTIVE = "/{enterpriseCode}/hierarchyOfEnterprise/activeOnly";
	
	/** The Constant GET_ENTERPRISES_BY_TYPE_CITY. */
	public static final String GET_ENTERPRISES_BY_TYPE_CITY = "/{enterpriseType}/{city}";

	/**
	 * Enterprise related constants.
	 */
	public static final String ENTERPRISE_TYPE_CANNNOT_BE_NULL = "enterprise_type_cannot_be_null.1";

	/** The Constant LEGAL_STATUS_CANNNOT_BE_NULL. */
	public static final String LEGAL_STATUS_CANNNOT_BE_NULL = "legal_status_cannot_be_null.1";

	/** The Constant TAX_RESIDENCY_COUNTRY_CODE_SIZE_MUST_BE_2_CHARACTERS. */
	public static final String TAX_RESIDENCY_COUNTRY_CODE_SIZE_MUST_BE_2_CHARACTERS = "tax_residency_country_code_size_must_be_2_characters.1";
	/**
	 * EnterpriseAddress related constants.
	 */
	public static final String ADDRESS_LINE1_CANNNOT_BE_NULL = "address_line1_cannot_be_null.1";

	/** The Constant ADDRESS_TYPE_CANNNOT_BE_NULL. */
	public static final String ADDRESS_TYPE_CANNNOT_BE_NULL = "address_type_cannot_be_null.1";

	/** The Constant POSTAL_CODE_CANNNOT_BE_NULL. */
	public static final String POSTAL_CODE_CANNNOT_BE_NULL = "postal_code_cannot_be_null.1";

	/** The Constant POSTAL_CODE_SIZE_MUST_BE_10_CHARACTERS. */
	public static final String POSTAL_CODE_SIZE_MUST_BE_10_CHARACTERS = "postal_code_size_must_be_10_characters.1";

	/** The Constant STATE_CODE_CANNNOT_BE_NULL. */
	public static final String STATE_CODE_CANNNOT_BE_NULL = "state_code_cannot_be_null.1";

	/** The Constant STATE_CODE_SIZE_MUST_BE_10_CHARACTERS. */
	public static final String STATE_CODE_SIZE_MUST_BE_10_CHARACTERS = "state_code_size_must_be_10_characters.1";

	/** The Constant COUNTRY_CODE_SIZE_MUST_BE_2_CHARACTERS. */
	public static final String ENTERPPRISE_COUNTRY_CODE_SIZE_MUST_BE_2_CHARACTERS = "enterprise_country_code_size_must_be_2_characters.1";

	/** The Constant IS_VERIFIED_SIZE_MUST_BE_1_CHARACTERS. */
	public static final String IS_VERIFIED_SIZE_MUST_BE_1_CHARACTERS = "is_verified_size_must_be_1_character";

	/** The Constant DOCUMENT_COLLECTED_SIZE_MUST_BE_1_CHARACTERS. */
	public static final String DOCUMENT_COLLECTED_SIZE_MUST_BE_1_CHARACTERS = "document_collected_size_must_be_1_character";

	/** The Constant OTHER_BCNM_FIELD_CANNOT_BE_NULL. */
	public static final String OTHER_BCNM_FIELD_CANNOT_BE_NULL = "Other_bcnm field should not be null";

	/**
	 * EnterpriseContact related constants.
	 */
	public static final String CONTACT_TYPE_CANNNOT_BE_NULL = "contact_type_cannot_be_null.1";

	/** The Constant FIRST_NAME_CANNNOT_BE_NULL. */
	public static final String FIRST_NAME_CANNNOT_BE_NULL = "first_name_cannot_be_null.1";

	/** The Constant LAST_NAME_CANNNOT_BE_NULL. */
	public static final String LAST_NAME_CANNNOT_BE_NULL = "last_name_cannot_be_null.1";

	/** The Constant INVALID_MOBILE_NUMBER. */
	public static final String INVALID_MOBILE_NUMBER = "invalid_mobile_number.1";

	/** The Constant MOBILE_NUMBER_CANNOT_BE_NULL. */
	public static final String MOBILE_NUMBER_CANNOT_BE_NULL = "mobile_number_cannot_be_null.1";

	/** The Constant flagForRemovingEntity. */
	public static final Boolean flagForRemovingEntity = true;

	/** The Constant INVALID_EMAIL_ADDRESS. */
	public static final String INVALID_EMAIL_ADDRESS = "invalid_mail_address.1";

	/** The Constant ENTERPRISE_NAME_CANNNOT_BE_NULL. */
	public static final String ENTERPRISE_NAME_CANNNOT_BE_NULL = "enterprise_name_cannot_be_null.1";

	/**
	 * The Constant ENTERPRISE_NAME_SIZE_MUST_BE_BETWEEN_2_AND_100_CHARACTERS.
	 */
	public static final String ENTERPRISE_NAME_SIZE_MUST_BE_BETWEEN_2_AND_100_CHARACTERS = "enterprise_name_size_must_be_between_2_and_100_characters.1";

	/** The Constant COUNTRY_CODE_CANNNOT_BE_NULL. */
	public static final String ENTERPPRISE_COUNTRY_CODE_CANNNOT_BE_NULL = "enterprise_country_code_cannot_be_null.1";

	/** The Constant TAX_RESIDENCY_COUNTRY_CODE_CANNNOT_BE_NULL. */
	public static final String TAX_RESIDENCY_COUNTRY_CODE_CANNNOT_BE_NULL = "tax_residency_country_code_cannot_be_null.1";

	/** The Constant GET_CHILD_ENTERPRISES_OF_SPECIFIED_TYPE. */
	public static final String GET_CHILD_ENTERPRISES_OF_FIRST_LEVEL = "/{enterpriseCode}/childEnterprises/firstLevel/activeOnly";

	/** The Constant ENTERPRISE_CODE_MUST_NOT_BE_NULL. */
	public static final String ENTERPRISE_CODE_MUST_NOT_BE_NULL = "enterprsie_code_must_not_be_null.1";

	/** The Constant USER_DOES_NOT_HAVE_ENTERPRISE_CHECKER_PERMISSION. */
	public static final String CHECKER_DOES_NOT_HAVE_ENTERPRISE_CHECKER_PERMISSION = "checker_does_not_have_enterprise_checker_permission.1";

	/** The Constant CHECKER_DOES_NOT_HAVE_USER_CHECKER_PERMISSION. */
	public static final String CHECKER_DOES_NOT_HAVE_USER_CHECKER_PERMISSION = "checker_does_not_have_user_checker_permission.1";
	/**
	 * Instantiates a new enterprise constants.
	 */
	private EnterpriseConstants() {
	}
}
