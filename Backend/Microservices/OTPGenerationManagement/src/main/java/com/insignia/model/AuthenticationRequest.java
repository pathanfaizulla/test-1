package com.insignia.model;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private String applicationId;
	private String tenantId;
	private String otp;
	private Integer otpExpiryDuration;
	private String email;
	private String customerId;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Integer getOtpExpiryDuration() {
		return otpExpiryDuration;
	}

	public void setOtpExpiryDuration(Integer otpExpiryDuration) {
		this.otpExpiryDuration = otpExpiryDuration;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	@Override
	public String toString() {
		return "AuthenticationRequest [userId=" + userId + ", applicationId=" + applicationId + ", tenantId=" + tenantId
				+ ", otp=" + otp + ", otpExpiryDuration=" + otpExpiryDuration + ", email=" + email + ", customerId="
				+ customerId + "]";
	}

}
