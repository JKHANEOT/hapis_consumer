/*
 * Copyright © EasOfTech 2015. All rights reserved.
 *
 * This software is the confidential and proprietary information
 *  of EasOfTech. You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms and
 *  conditions entered into with EasOfTech.
 *
 */
package com.hapis.customer.networking.util;

public interface RequestTypes {

    int RES_SUCCESS = 0;

    /**
     * The Dummy Request Type.
     */
    int REQUEST_DUMMY = -1;

    int OTP_REQ = 1;
    int VERIFY_OTP_REQ = 2;
    int LOGIN_REQ = 3;
    int FORGOT_PASSWORD_REQ = 4;
    int UPDATE_CUSTOMER_PROFILE_REQ = 5;
    int LOGOUT_REQ = 6;
    int SEND_FEEDBACK_REQ = 7;
    int DOWNLOAD_DIGITAL_KIT_REQ = 8;
    int ADD_PRODUCT_REQ = 9;
    int EDIT_PRODUCT_REQ = 10;
    int UPLOAD_CRASH_LOG_URL = 11;
    int FETCH_RESOURCES_LINKS_URL = 12;
    int CHANGE_DIGITAL_KIT_STATUS_URL = 13;
    int RESEND_OTP_REQ = 14;
    int CHANGE_PASSWORD_REQ = 15;
    int CREATE_PASSWORD_REQ = 16;

    int GET_WARRANTY_REQ = 17;
    int CREATE_SERVICE_REQUEST_REQ = 18;
    int GET_SERVICE_REQUEST_DETAILS_REQ = 19;
    int REPLY_SERVICE_REQUEST_REQ = 20;
    int CLOSE_SERVICE_REQUEST_REQ = 21;

    int GET_PAYUMONEY_SERVER_SIDE_HASH_REQ = 22;
    int POST_PURCHASE_LOG_TO_SERVER_REQ = 23;
    int ADD_USER_MOBILE_NUMBER_REQUEST_REQ = 31;
    int VALIDATE_OTP_USER_MOBILE_NUMBER_REQUEST_REQ = 32;
    int REMOVE_USER_MOBILE_NUMBER_REQUEST_REQ = 33;
    int RESEND_OTP_USER_MOBILE_NUMBER_REQUEST_REQ = 34;
    int FETCH_DIGITAL_KIT_DOCUMENT_LINKS_URL_REQ = 35;
    int ADD_DK_ADDITIONAL_DOCUMENT_URL_REQ = 36;

    int POST_SERVICE_REQUEST_PAYMENT_TO_SERVER_REQ = 37;

    int DOWNLOAD_USER_DOCUMENTS_REQ = 38;
    int UPLOAD_USER_DOCUMENT_REQ = 39;
    int DELETE_USER_DOCUMENT_REQ = 40;

    int PERFORM_SYNC_DKS_BASIC_REQ = 61;
    int PERFORM_SYNC_ADDRESS_REQ = 62;
    int PERFORM_SYNC_PROFILE_REQ = 63;
    int PERFORM_SYNC_NOTIFICATION_REQ = 64;
    int PERFORM_SYNC_INVOICE_REQ = 65;
    int PERFORM_SYNC_PRODUCT_MASTER_DATA_REQ = 66;
    int PERFORM_SYNC_SERVICE_REQUEST_REQ = 67;


    int ADD_ADDRESS_REQ = 68;
    int UPDATE_ADDRESS_REQ = 69;
    int DELETE_ADDRESS_REQ = 70;


    int FETCH_DIGI_LOCKER_REQ = 71;

    int VALIDATE_UNIQUE_CODE_FOR_PRODUCT_REGISTRATION = 72;
    int COMPLETE_PRODUCT_REGISTRATION = 73;
    int FETCH_MODELS_BASED_ON_SUB_CATEGORY_MANUFACTURER = 74;


    int PERFORM_SYNC_MY_ORDERS_REQ = 75;

    int FETCH_COUPONS_REQ = 76;
    int APPLY_COUPON_REQ = 77;

}
