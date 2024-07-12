package com.insignia.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long customerSequenceNumber;
	private String userId;
	private String applicationId;
	private String tenantId;
	private String password;
	private Integer expirationTime;
	private boolean isRememberMeSelected;
	private String emailId;
	private Boolean isToValidatePassword = false;
	@JsonProperty("isOtpAuthenticated")
	private Boolean isOTPAuthentication = false;
	@JsonProperty("OTP")
	private String otp;

	private Integer expirationDuration;

	private String newPassword;
	private String confirmPassword;

	public Long getCustomerSequenceNumber() {
		return customerSequenceNumber;
	}

	public void setCustomerSequenceNumber(Long customerSequenceNumber) {
		this.customerSequenceNumber = customerSequenceNumber;
	}

	public Boolean getIsOTPAuthentication() {
		return isOTPAuthentication;
	}

	public void setIsOTPAuthentication(Boolean isOTPAuthentication) {
		this.isOTPAuthentication = isOTPAuthentication;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Integer expirationTime) {
		this.expirationTime = expirationTime;
	}

	public boolean isRememberMeSelected() {
		return isRememberMeSelected;
	}

	public void setRememberMeSelected(boolean isRememberMeSelected) {
		this.isRememberMeSelected = isRememberMeSelected;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public AuthenticationRequest() {

	}

	public Boolean getIsToValidatePassword() {
		return isToValidatePassword;
	}

	public void setIsToValidatePassword(Boolean isToValidatePassword) {
		this.isToValidatePassword = isToValidatePassword;
	}

	public String getCustomUserName() {

		return new StringBuilder().append(this.userId + this.applicationId + this.tenantId).toString();
	}

	public String getoTP() {
		return otp;
	}

	public void setoTP(String oTP) {
		this.otp = oTP;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Integer getExpirationDuration() {
		return expirationDuration;
	}

	public void setExpirationDuration(Integer expirationDuration) {
		this.expirationDuration = expirationDuration;
	}

	@Override
	public String toString() {
		return "AuthenticationRequest [customerSequenceNumber=" + customerSequenceNumber + ", userId=" + userId
				+ ", applicationId=" + applicationId + ", tenantId=" + tenantId + ", password=" + password
				+ ", expirationTime=" + expirationTime + ", isRememberMeSelected=" + isRememberMeSelected + ", emailId="
				+ emailId + ", isToValidatePassword=" + isToValidatePassword + ", isOTPAuthentication="
				+ isOTPAuthentication + ", otp=" + otp + ", expirationDuration=" + expirationDuration + ", newPassword="
				+ newPassword + ", confirmPassword=" + confirmPassword + "]";
	}

}
