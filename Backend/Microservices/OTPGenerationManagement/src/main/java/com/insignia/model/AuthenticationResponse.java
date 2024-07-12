package com.insignia.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String otp;

	private String errorCode;
	private String errorMessage;

	public String getOtp() {
		return otp;
	}

	public void setOtp(String string) {
		this.otp = string;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public AuthenticationResponse(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public AuthenticationResponse() {

	}

	@Override
	public String toString() {
		return "AuthenticationResponse [otp=" + otp + ", errorCode=" + errorCode + ", errorMessage=" + errorMessage
				+ "]";
	}

	

}
