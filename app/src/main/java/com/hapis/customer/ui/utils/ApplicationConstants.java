/*
 * Copyright © EasOfTech 2015. All rights reserved.
 *
 * This software is the confidential and proprietary information
 *  of EasOfTech. You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms and
 *  conditions entered into with EasOfTech.
 *
 */
package com.hapis.customer.ui.utils;

/*
 *
 * Id: ApplicationConstants
 *
 * Date Author Changes
 * 23-Dec-15 afsar Created
 */
public interface ApplicationConstants {
    /**
     * The FIL pre_ path.
     */
    String FILE_PRE_PATH = "/mnt/sdcard";

    /**
     * The FIL e_ path.
     */
    String FOLDER_NAME = "Hapis";

    /**
     * The FIL e_ path.
     */
    String TEMP_FOLDER_NAME = "TempNhance";

    /**
     * The Log_ file.
     */
    String LOG_FILE = "Hapis.txt";

    /**
     * The Log_ file.
     */
    String DB_FILE = "HapisDB.db";

    String APPLICATION_FOLDER_PATH = FILE_PRE_PATH + "/" + FOLDER_NAME;

    String TEMP_APPLICATION_FOLDER_PATH = FILE_PRE_PATH + "/" + TEMP_FOLDER_NAME;

    // SMS provider identification
    // It should match with your SMS gateway origin
    String SMS_ORIGIN = "Hapis";

    // special character to prefix the otp. Make sure this character appears only once in the sms
    String OTP_DELIMITER = ":";

    int OTP_LENGTH = 6;

    String APPLICATION_TYPE = "EOT";

    String SOURCE_TYPE = "ANDROID";

    String SMS_ORIGIN_KEY = "SMS_ORIGIN";

    String SMS_MESSAGE_KEY = "MESSAGE";

    String OTP_KEY = "OTP";

    String IS_EXISTING_USER = "IS_EXISTING_USER";

    String MOBILE_NO_EXTRA = "MOBILE_NO";

    String EMAIL_ID_EXTRA = "EMAIL_ID";

    String APPLICATION_TYPE_EXTRA = "APPLICATION_TYPE";

    String SOURCE_TYPE_EXTRA = "SOURCE_TYPE";

    String SERVICE_URL_EXTRA = "SERVICE_URL";

    String CRASH_LOG_FOLDER_PATH_EXTRA = "CRASH_LOG_FOLDER_PATH";

    Integer LOCATION_ACTIVE = 1;

    Integer LOCATION_INACTIVE = 2;

    Integer DIGITAL_KIT_NOT_DOWNLOADED = 0;

    Integer DIGITAL_KIT_DOWNLOAD_INITIATED = 2;

    Integer DIGITAL_KIT_DOWNLOADED = 1;

    Integer DEFAULT_LOCATION_SET = 3;
    Integer DEFAULT_LOCATION_NOT_SET = 0;

    String CRASH_LOG_FILES_FOLDER = "CrashLogs";
    String CRASH_LOG_FILES_NAME = "CrashLog_";
    String CRASH_LOG_FILES_EXTENSION = ".stacktrace";
    int BYTE_DATA = 1;
    int LESS_THAN_KB_DATA = 2;
    int KB_DATA = 3;
    int LESS_THAN_MB_DATA = 4;
    int MB_DATA = 5;
    String CRASH_LOG_FILE_NAME = "crash_log_file_name";
    int DEFAULT_VALUE = -1;

    String IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN";
    String APPLICATION_REFERRER_CODE = "APPLICATION_REFERRER_CODE";
    int USER_NEW = 1;
    int USER_SIGNED_UP = 2;
    int USER_LOGGED_IN = 3;
    int USER_LOGGED_OUT = 4;
    String LOGGED_IN_USER_GUID = "LOGGED_IN_USER_GUID";
    String LOGGED_IN_USER_MOBILE_EMAIL = "LOGGED_IN_USER_MOBILE_EMAIL";

    String SEEN_NOTIFICATIONS = "seen_notifications";
    String UNSEEN_NOTIFICATIONS_COUNT = "unseen_notifications_count";
    String DEFAULT_LOCATION_ID = "default_location_id";
    String IS_DEFAULT_LOCATION_ID_CHANGED = "is_default_location_id_changed";

    String DISMISS_NOTIFICATION_ID = "dismissNotificationId";
    String SNOOZE_EVENT = "eventSnooze";

    String SEEN_OFFERS = "seen_offers";
    String UNSEEN_OFFERS_COUNT = "unseen_offers_count";

    String UNSEEN_MESSAGES_COUNT = "unseen_messages_count";

    String NOTIFICATION_DAYS_TO_LIVE = "notificationDaysToLive";
    String OFFERS_TIME_TO_LIVE = "offersTimeToLive";

    String ENABLE_DISABLE_NOTIFICATIONS = "enable_disable_notifications";

    String NHANCE_JWT_HEADER = "nhancenow_jwt_header";


    /*=================PRODUCT-MASTER-DATA-DETAILS-ACTIONS-CLASS/METHOD-CONSTANTS====================*/

    String CLASS_MASTER_DATA_ACTIONS = "com.nhance.b2c.ui.action.MasterDataAction";
    String METHOD_GET_PRODUCT_CATEGORY_TYPES = "getProductCategoryTypesFromDB";
    String METHOD_GET_PRODUCT_CATEGORY_TYPES_BASED_PRODUCT_TYPE_ID = "getProductCategoryTypesFromDB";
    String METHOD_GET_BRANDS = "getBrandsFromDB";
    String METHOD_GET_BRANDS_BASED_PRODUCT_TYPE_ID = "getManufacturerFromDBByProdSubCat";
    String METHOD_GET_SELLERS = "getSellersFromDB";
    String METHOD_GET_SELLERS_BASED_PRODUCT_TYPE_ID = "getSellersFromDB";
    byte METHOD_GET_PRODUCT_CATEGORY_TYPES_CONSTANT = 1;
    byte METHOD_GET_PRODUCT_CATEGORY_TYPES_BASED_PRODUCT_TYPE_ID_CONSTANT = 2;
    byte METHOD_GET_BRANDS_CONSTANT = 3;
    byte METHOD_GET_BRANDS_BASED_PRODUCT_TYPE_ID_CONSTANT = 4;
    byte METHOD_GET_SELLERS_CONSTANT = 5;
    byte METHOD_GET_SELLERS_BASED_PRODUCT_TYPE_ID_CONSTANT = 6;

    /*===============================================================================================*/

    String IS_CUSTOMER_PRODUCT_DATA_EXISTS = "IS_CUSTOMER_PRODUCT_DATA_EXISIST";
    String IMAGE_PATH = "IMAGE_PATH";
    String IMAGE_URL = "IMAGE_URL";
    String PROFILE_PIC_FILE_NAME_TAG = "ProfilePic";
    String PROFILE_PIC_TEMP_FILE_NAME_TAG = "TempProfilePic";
    String FEEDBACK_NAVIGATED_TAG = "FEEDBACK_NAVIGATED_TAG";
    int FEEDBACK_NAVIGATED_FROM_NAVIGATION_DRAWER = 1;
    int FEEDBACK_NAVIGATED_FROM_DK_DETAILS = 2;
    String REQUESTING_RESET_PASSWORD = "REQUESTING_RESET_PASSWORD";

    String REDIRECTING_FROM_CREATE_PASSWORD = "REDIRECTING_FROM_CREATE_PASSWORD";

    String PRODUCT_SERVICE_LOCATOR_LIST_TAG = "PRODUCT_SERVICE_LOCATOR_LIST_TAG";
    String PRODUCT_SERVICE__REQUEST_SELECTED_TAG = "PRODUCT_SERVICE__REQUEST_SELECTED_TAG";

    int NO_OF_ADDRESS_PER_USER_LIMIT = 3;

    String CAPTURED_PHOTO_RESULT_OR_FILE_PATH_TAG = "CapturedPhotoResultOrFilePathTag";

    String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    String REGISTRATION_COMPLETE = "registrationComplete";


    String ACTION_CODE_OPEN_PLAY_STORE = "16000001";

    String ACTION_CODE_ISSUE_DIGITAL_KIT = "NOTIFY_DK_ISSUE";
    String ACTION_CODE_MODIFY_DK = "NOTIFY_DK_UPDATE";
    String ACTION_CODE_DIGITAL_KIT_DELETE = "NOTIFY_DK_DELETE";
    String ACTION_CODE_PRODUCT_UPDATE = "NOTIFY_PRODUCT_UPDATE";
    String ACTION_CODE_CUSTOMER_UPDATE = "NOTIFY_CUSTOMER_UPDATE";

    String ACTION_CODE_SERVICE_REQUEST = "NOTIFY_SR_UPDATE";
    String ACTION_CODE_INCOMING_VIDEO_CALL = "NOTIFY_VIDEO_CALL";
    String ACTION_CODE_END_INCOMING_VIDEO_CALL = "NOTIFY_END_VIDEO_CALL";
    String ACTION_CODE_OPEN_APP_PURCHASE_SCREEN = "18000004";
    String ACTION_CODE_DE_REGISTER_DEVICE = "NOTIFY_DEVICE_DEREGISTER";

    String ACTION_CODE_CAMPAIGN = "NOTIFY_CAMPAIGN";
    String ACTION_CODE_OFFERS = "NOTIFY_OFFER";
    String ACTION_CODE_COUPONS = "NOTIFY_COUPON";
    String ACTION_CODE_POLLS_FEEDBACK = "NOTIFY_FEEDBACK";
    String ACTION_CODE_OPEN_APPLICATION = "18000009";
    String ACTION_CODE_DK_DOCUMENT_APPROVED = "NOTIFY_DK_APPROVED";
    String ACTION_CODE_REJECT_DOCUMENT = "18000010";
    String ACTION_CODE_SERVICE_REQUEST_INVOICE_GENERATED = "NOTIFY_SR_INVOICE_GENERATED";
    String ACTION_CODE_SERVICE_REQUEST_INVOICE_PAID = "NOTIFY_SR_INVOICE_PAID";
    String ACTION_CODE_PURCHASE_ORDER_UPDATE = "NOTIFY_PURCHASE_UPDATE";

    int UPDATE_DUE_TO_CHANGE_IN_PRODUCT = 1;
    int UPDATE_DUE_TO_CHANGE_IN_DK = 2;
    int UPDATE_DUE_TO_DELETE_DK = 3;

    String SELECTED_DK = "selected_dk";
    String STARTED_FROM_NOTIFICATION_TAG = "StartedFromNotificationClick";
    String PREVIOUS_DEFAULT_LOCATION_ID = "previous_default_location_id";
    String IS_DK_UPDATE_DAILOG_SHOWING = "is_dk_update_dialog_showing";
    String LIST_OF_DKS_TO_UPDATE = "listofdkstoupdate";
    String LIST_OF_DK_CODES_TO_UPDATE = "listofdkcodestoupdate";
    String SELECTED_DK_GUID = "selected_dk_guid";
    String INCREMENTAL_NUMBER = "incremental_number";
    String IS_DELETE_DK = "is_delete_dk";
    String CAN_SHOW_SUMMARY = "can_show_summary";


    String ERROR_CODE_TOKEN_EXPIRED = "token_expired";


    enum AppMode {
        PLAYSTORE_RELEASE {
            @Override
            public String toString() {
                return "playstore_release";
            }
        },
        DEV_RELEASE {
            @Override
            public String toString() {
                return "dev_release";
            }
        },
        DEMO_RELEASE {
            @Override
            public String toString() {
                return "demo_release";
            }
        }
    }

    AppMode AppExt = AppMode.DEV_RELEASE;

    int CUSTOMER_TYPE_INDIVIDUAL = 1;
    int CUSTOMER_TYPE_ORGANISATION = 2;

    long FILE_SIZE_IN_KB = 1024;
    long FILE_SIZE_IN_MB = FILE_SIZE_IN_KB * 1024;

    long MAX_UPLOAD_FILE_SIZE = 2 * FILE_SIZE_IN_MB; // Size is in MB


    String OPEN_URL = "OPEN_URL";
    String OPEN_URL_SCREEN_TITLE = "OPEN_URL_SCREEN_TITLE";

    String TEXT_TO_SHARE = "TEXT_TO_SHARE";
    String SCREEN_TITLE = "SCREEN_TITLE";

    int STATUS_FAIL = 8;
    int STATUS_SUCCESS = 13;

    int ATTRIBUTE_NOT_CAPTURED = 0;
    int ATTRIBUTE_CAPTURED = 1;

    int DOC_STATUS_PENDING = 21;
    int DOC_STATUS_APPROVED = 22;
    int DOC_STATUC_REJECTED = 23;

    /* -------------- MOBILE PHONE MANUFACTURERS ----------------*/
    String XIAOMI = "xiaomi";

    int SYNC_IN_PROGRESS = 1;
    int SYNC_COMPLETED = 2;

    int SYNC_DKS_BASIC = 1;
    int SYNC_ADDRESS = 2;
    int SYNC_PROFILE = 3;
    int SYNC_NOTIFICATION = 4;
    int SYNC_INVOICE = 5;
    int SYNC_PRODUCT_MASTER_DATA = 6;
    int SYNC_SERVICE_REQUEST = 7;

    int PERSONAL_DOCUMENT_NOT_SYNCED = 1;
    int PERSONAL_DOCUMENT_SYNCED = 2;

    int DOC_STATUS_ADD = 1;
    int DOC_STATUS_DELETE = 2;
    int DOC_STATUC_UPDATE = 3;

    int DB_INSERTION = 1;
    int DB_UPDATION = 2;
    int DB_DELETION = 3;

    String IS_CUSTOM_SERVER_URL = "is_custom_server_url";
    String CUSTOM_SERVER_URL = "custom_server_url";
    String NHANCE_SERVER_URL = "nhancenow_server_url";

    String SELECTED_LOCALE_CODE = "selected_locale_code";
    String DEFAULT_LOCALE_CODE = "en";

    String IS_ALL_DK_SYNC_REQUIRED = "IS_ALL_DK_SYNC_REQUIRED";

    /**
     * The FIL e_ path.
     */
    String SHARED_DOC_FOLDER_NAME = "NhanceSharedDocs";

    int PAYMENT_MODE_ONLINE = 1;
    int PAYMENT_MODE_COD = 2;

    String SIGNUP_DETAILS = "SIGN_UP_RESPONSE";

    int ENTERPRISE_TYPE_INDIVIDUAL = 1;
    int ENTERPRISE_TYPE_HOSPITAL = 2;

}
