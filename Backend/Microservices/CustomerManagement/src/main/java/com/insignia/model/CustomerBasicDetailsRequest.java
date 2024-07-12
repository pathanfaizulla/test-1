package com.insignia.model;

import java.io.Serializable;
import java.util.Date;

public class CustomerBasicDetailsRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long customerSequenceNumber;
	private Integer expirationDuration;

	private String customerId;
	private boolean isCustomerIdUpdated;

	private String fullName;
	private boolean isFullNameUpdated;

	private String userId;
	private boolean isUserIdUpdated;

	private String applicationId;
	private boolean isApplicationIdUpdated;

	private String tenantId;
	private boolean isTenantIdUpdated;

	private String password;
	private boolean isPasswordUpdated;

	private String emailId;
	private boolean isEmailIdUpdated;

	private Boolean isToValidatePassword = false;
	private boolean isToValidatePasswordUpdated;

	private String userName;
	private boolean isUserNameUpdated;

	private Date registeredOn;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
		this.isFullNameUpdated = true;
	}

	public boolean isFullNameUpdated() {
		return isFullNameUpdated;
	}

	public Integer getExpirationDuration() {
		return expirationDuration;
	}

	public void setExpirationDuration(Integer expirationDuration) {
		this.expirationDuration = expirationDuration;
	}

	public Long getCustomerSequenceNumber() {
		return customerSequenceNumber;
	}

	public void setCustomerSequenceNumber(Long customerSequenceNumber) {
		this.customerSequenceNumber = customerSequenceNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
		this.isUserIdUpdated = true;
	}

	public boolean isUserIdUpdated() {
		return isUserIdUpdated;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
		this.isApplicationIdUpdated = true;
	}

	public boolean isApplicationIdUpdated() {
		return isApplicationIdUpdated;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
		this.isTenantIdUpdated = true;
	}

	public boolean isTenantIdUpdated() {
		return isTenantIdUpdated;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		this.isPasswordUpdated = true;
	}

	public boolean isPasswordUpdated() {
		return isPasswordUpdated;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
		this.isEmailIdUpdated = true;
	}

	public boolean isEmailIdUpdated() {
		return isEmailIdUpdated;
	}

	public Boolean getIsToValidatePassword() {
		return isToValidatePassword;
	}

	public void setIsToValidatePassword(Boolean isToValidatePassword) {
		this.isToValidatePassword = isToValidatePassword;
		this.isToValidatePasswordUpdated = true;
	}

	public boolean isToValidatePasswordUpdated() {
		return isToValidatePasswordUpdated;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
		this.isUserNameUpdated = true;
	}

	public boolean isUserNameUpdated() {
		return isUserNameUpdated;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getRegisteredOn() {
		return registeredOn;
	}

	public void setRegisteredOn(Date registeredOn) {
		this.registeredOn = registeredOn;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
		this.isCustomerIdUpdated = true;
	}

	public boolean isCustomerIdUpdated() {
		return isCustomerIdUpdated;
	}

	@Override
	public String toString() {
		return "CustomerBasicDetailsRequest [customerSequenceNumber=" + customerSequenceNumber + ", expirationDuration="
				+ expirationDuration + ", fullName=" + fullName + ", userId=" + userId + ", isUserIdUpdated="
				+ isUserIdUpdated + ", applicationId=" + applicationId + ", isApplicationIdUpdated="
				+ isApplicationIdUpdated + ", tenantId=" + tenantId + ", isTenantIdUpdated=" + isTenantIdUpdated
				+ ", password=" + password + ", isPasswordUpdated=" + isPasswordUpdated + ", emailId=" + emailId
				+ ", isEmailIdUpdated=" + isEmailIdUpdated + ", isToValidatePassword=" + isToValidatePassword
				+ ", isToValidatePasswordUpdated=" + isToValidatePasswordUpdated + ", userName=" + userName
				+ ", isUserNameUpdated=" + isUserNameUpdated + ", registeredOn=" + registeredOn + "]";
	}

}
