package com.hapis.customer.exception;


import android.util.Log;

import java.util.Hashtable;

/**
* The class description goes here.
* All the exception are defined in this class
* @author JKHAN
* @see Exception
*/
public class HapisException extends Exception {

	private static byte errorCount = 0;

	/** The error type. */
	private Byte errorType;

	public static final Byte SERVER_HANDLER_INIT_ERROR = new Byte((byte)++errorCount);
	public static final Byte BUSINESS_CONTROLLER_NOT_INITIALIZED = new Byte((byte)++errorCount);
	public static final Byte GPRS_EXCEPTION = new Byte((byte)++errorCount);
	public static final Byte GPRS_ACCESS_DENIED = new Byte((byte)++errorCount);
	public static final Byte GPRS_CONNECTION_NOT_FOUND = new Byte((byte)++errorCount);
	public static final Byte GPRS_USER_ABORT = new Byte((byte)++errorCount);
	public static final Byte SERVER_EXCEPTION = new Byte((byte)++errorCount);
	public static final Byte RESPONSE_NOT_IN_SYNC = new Byte((byte)++errorCount);
	public static final Byte ENCRYPTIONKEY_NOT_INITIALIZED = new Byte((byte)++errorCount);
	public static final Byte REQ_FORMAT_EXCEPTION = new Byte((byte)++errorCount);
	public static final Byte RES_PARSE_EXCEPTION = new Byte((byte)++errorCount);
	public static final Byte DATABASE_RETREIVAL_ERR = new Byte((byte)++errorCount);
	public static final Byte DATABASE_SAVE_ERR = new Byte((byte)++errorCount);
	public static final Byte INVALID_INPUT_ARGUMENT = new Byte((byte)++errorCount);
	public static final Byte INVALID_LOGIN_PIN = new Byte((byte)++errorCount);
	public static final Byte KEY_GENERATION_ERROR = new Byte((byte)++errorCount);
	public static final Byte KEY_WRAP_ERROR = new Byte((byte)++errorCount);
	public static final Byte KEY_STORE_ERROR = new Byte((byte)++errorCount);
	public static final Byte KEY_RETREIVAL_ERROR = new Byte((byte)++errorCount);
	public static final Byte ENCRYPTION_ERROR = new Byte((byte)++errorCount);
	public static final Byte DECRYPTION_ERROR = new Byte((byte)++errorCount);
	public static final Byte PIN_ALREADY_STORED = new Byte((byte)++errorCount);
	public static final Byte ACCOUNT_DOES_NOT_EXIST = new Byte((byte)++errorCount);
	public static final Byte DATA_INCONSISTENCY = new Byte((byte)++errorCount);
	public static final Byte URL_APPENDER_PARSE_ERROR = new Byte((byte)++errorCount);
	public static final Byte GPRS_SERVICE_ERROR = new Byte((byte)++errorCount);
	public static final Byte DATABASE_CREATION_ERROR = new Byte((byte)++errorCount);
	public static final Byte DATABASE_UPGRADE_STATEMENT_ERROR = new Byte((byte)++errorCount);
	public static final Byte DATABASE_TABLE_CREATION_ERROR = new Byte((byte)++errorCount);
	public static final Byte DATABASE_UPDATE_ERR = new Byte((byte)++errorCount);
	public static final Byte DATABASE_DELETE_ERR = new Byte((byte)++errorCount);
	public static final Byte INVALID_SELECTION = new Byte((byte)++errorCount);
	public static final Byte NO_DETAILS_AVAILABLE = new Byte((byte)++errorCount);
	public static final Byte PROFILE_DETAILS_DOES_NOT_EXISTS = new Byte((byte)++errorCount);
	public static final Byte DATABASE_UPGRADE_ERROR = new Byte((byte)++errorCount);
	public static final Byte WEB_LINK_UNAVAILABLE = new Byte((byte)++errorCount);
	public static final Byte GENERIC_EXCEPTION = new Byte((byte)++errorCount);
	public static final Byte INVALID_FILE_TYPE_TO_RENAME_OR_COPY = new Byte((byte)++errorCount);
	public static final Byte INVALID_FILE_PATH = new Byte((byte)++errorCount);

	private static Hashtable errorMap = new Hashtable();
	
	static {
		
		errorMap.put(BUSINESS_CONTROLLER_NOT_INITIALIZED, "Application not initialized properly");
		errorMap.put(GPRS_EXCEPTION, "Server Error when sending request");
		errorMap.put(GPRS_ACCESS_DENIED, "Access Denied.");
		errorMap.put(GPRS_CONNECTION_NOT_FOUND, "Connection not found.");
		errorMap.put(GPRS_USER_ABORT, "Retry aborted by user.");
		errorMap.put(RESPONSE_NOT_IN_SYNC, "Response received not in sync with Request");
		errorMap.put(ENCRYPTIONKEY_NOT_INITIALIZED, "Login failed due to initialization problems");
		errorMap.put(REQ_FORMAT_EXCEPTION, "Error while formatting request");
		errorMap.put(RES_PARSE_EXCEPTION, "Error while parsing response");
		errorMap.put(DATABASE_RETREIVAL_ERR, "Error while retreiving data from database");
		errorMap.put(DATABASE_SAVE_ERR, "Error while saving data to database");
		errorMap.put(INVALID_INPUT_ARGUMENT, "The provided input argument was not valid");
		errorMap.put(INVALID_LOGIN_PIN, "Login Pin mismatch");
		errorMap.put(KEY_GENERATION_ERROR, "Error while generating Key");
		errorMap.put(KEY_WRAP_ERROR, "Error when sending Key to server");
		errorMap.put(KEY_STORE_ERROR, "Error when storing key");
		errorMap.put(KEY_RETREIVAL_ERROR, "Error when retreiving key");
		errorMap.put(ENCRYPTION_ERROR, "Error while encrypting data");
		errorMap.put(DECRYPTION_ERROR, "Error while decrypting data");
		errorMap.put(PIN_ALREADY_STORED, "Pin already stored");
		errorMap.put(DATA_INCONSISTENCY, "Data Inconsistency");
		errorMap.put(URL_APPENDER_PARSE_ERROR, "Error when parsing url appenders");
		errorMap.put(GPRS_SERVICE_ERROR, "Network error.");
		errorMap.put(DATABASE_UPGRADE_STATEMENT_ERROR, "Database upgrade invalid sql statement.");
		errorMap.put(DATABASE_TABLE_CREATION_ERROR, "Database table creation error.");
		errorMap.put(DATABASE_UPDATE_ERR, "Database update error.");
		errorMap.put(DATABASE_DELETE_ERR, "Database delete error.");
		errorMap.put(INVALID_SELECTION, "Invalid Selection.");
		errorMap.put(NO_DETAILS_AVAILABLE, "No details available.");
		errorMap.put(PROFILE_DETAILS_DOES_NOT_EXISTS, "User Profile details does not exists.");
		errorMap.put(DATABASE_UPGRADE_ERROR, "Application upgrade error.");
		errorMap.put(WEB_LINK_UNAVAILABLE, "Unable to open web Link. Web link corrupted.");
	}
	
	public HapisException(Exception e) {

		super(e.getMessage());
	}

	public HapisException(Byte errorType) {

		super((String)errorMap.get(errorType));
		this.errorType = errorType;
		Log.d("Exception ", errorType + ": " + getMessage());
	}
	
	public HapisException(Byte errorType, String logMessage) {

		super((String)(errorMap.get(errorType) == null ? logMessage : errorMap.get(errorType)));
		this.errorType = errorType;
		Log.d("Exception ", errorType + ": " + getMessage() + ": " + logMessage);
	}

	public HapisException(String message) {
		super(message);
	}


	/**
	 * Gets the error type.
	 *
	 * @return the error type
	 */
	public Byte getErrorType() {

		return errorType;
	}
}

