package com.hapis.customer.utils;

/*
*
* Id: Application
*
* Author
* JKHAN
*/
public class Application {

	private static Application applicationInstance = null;
	private int applicationType;
	private String sourceType;
	private String IMEINo;
	private String versionNumber;
	private boolean applicationLevelEncryptionRequired = true;
	private String preferedLanguage;
	private int noOfLoginTried = 0;
	private String mobileNumber;
	private String emailId;
	private String loggedInUserName;
	private String loggedInUserProfilePicPath;
	private String selectedDkProductPicPath;
	private int maximumAllowedAddress;
	//private String userId;
	private String password;
	private int isdCode;
	private String referralCode;

	private String userProfileUserIdOrGuid;
	private String userReferredBy;
	private String promotionalMessage;
	private String myReferralCode;
	private String currentLanguage;

	private Application()
	{

	}
	public static Application getInstance()
	{
		if(applicationInstance == null)
		{
			applicationInstance = new Application();
		}
		return applicationInstance;
	}

	public int getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(int applicationType) {
		this.applicationType = applicationType;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getIMEINo() {
		return IMEINo;
	}
	public void setIMEINo(String iMEINo) {
		IMEINo = iMEINo;
	}
	public String getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	public boolean isApplicationLevelEncryptionRequired() {
		return applicationLevelEncryptionRequired;
	}
	public void setApplicationLevelEncryptionRequired(boolean applicationLevelEncryptionRequired) {
		this.applicationLevelEncryptionRequired = applicationLevelEncryptionRequired;
	}
	public void setPreferedLanguage(String preferedLanguage) {
		this.preferedLanguage = preferedLanguage;
	}
	public String getPreferedLanguage() {
		return preferedLanguage;
	}
	public int getNoOfLoginTried() {
		return noOfLoginTried;
	}
	public void setNoOfLoginTried(int noOfLoginTried) {
		this.noOfLoginTried = noOfLoginTried;
	}
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getLoggedInUserName() {
		return loggedInUserName;
	}

	public void setLoggedInUserName(String loggedInUserName) {
		this.loggedInUserName = loggedInUserName;
	}

	public String getLoggedInUserProfilePicPath() {
		return loggedInUserProfilePicPath;
	}

	public void setLoggedInUserProfilePicPath(String loggedInUserProfilePicPath) {
		this.loggedInUserProfilePicPath = loggedInUserProfilePicPath;
	}

	public int getMaximumAllowedAddress() {
		return maximumAllowedAddress;
	}

	public void setMaximumAllowedAddress(int maximumAllowedAddress) {
		this.maximumAllowedAddress = maximumAllowedAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSelectedDkProductPicPath() {
		return selectedDkProductPicPath;
	}

	public void setSelectedDkProductPicPath(String selectedDkProductPicPath) {
		this.selectedDkProductPicPath = selectedDkProductPicPath;
	}

	public String getUserProfileUserIdOrGuid() {
		return userProfileUserIdOrGuid;
	}

	public void setUserProfileUserIdOrGuid(String userProfileUserIdOrGuid) {
		this.userProfileUserIdOrGuid = userProfileUserIdOrGuid;
	}

	public String getUserReferredBy() {
		return userReferredBy;
	}

	public void setUserReferredBy(String userReferredBy) {
		this.userReferredBy = userReferredBy;
	}

	public String getPromotionalMessage() {
		return promotionalMessage;
	}

	public void setPromotionalMessage(String promotionalMessage) {
		this.promotionalMessage = promotionalMessage;
	}

	public String getMyReferralCode() {
		return myReferralCode;
	}

	public void setMyReferralCode(String myReferralCode) {
		this.myReferralCode = myReferralCode;
	}

	public String getCurrentLanguage() {
		return currentLanguage;
	}

	public void setCurrentLanguage(String currentLanguage) {
		this.currentLanguage = currentLanguage;
	}

	@Override
	public String toString() {
		return "Application{" +
				"applicationLevelEncryptionRequired=" + applicationLevelEncryptionRequired +
				", applicationType=" + applicationType +
				", sourceType='" + sourceType + '\'' +
				", IMEINo='" + IMEINo + '\'' +
				", versionNumber='" + versionNumber + '\'' +
				", preferedLanguage='" + preferedLanguage + '\'' +
				", noOfLoginTried=" + noOfLoginTried +
				", mobileNumber='" + mobileNumber + '\'' +
				", emailId='" + emailId + '\'' +
				", loggedInUserName='" + loggedInUserName + '\'' +
				", loggedInUserProfilePicPath='" + loggedInUserProfilePicPath + '\'' +
				", selectedDkProductPicPath='" + selectedDkProductPicPath + '\'' +
				", maximumAllowedAddress=" + maximumAllowedAddress +
				/*", userId='" + userId + '\'' +*/
				", password='" + password + '\'' +
				", isdCode=" + isdCode +
				", referralCode='" + referralCode + '\'' +
				'}';
	}

	/*public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}*/

	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	public int getIsdCode() {
		return isdCode;
	}

	public void setIsdCode(int isdCode) {
		this.isdCode = isdCode;
	}
}
