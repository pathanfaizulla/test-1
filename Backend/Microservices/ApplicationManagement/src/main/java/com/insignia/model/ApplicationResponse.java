package com.insignia.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ApplicationResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer applicationId;
	
	private String errorCode;

	private String errorMessage;
	
	private String applicationName;
	
	private String applicationDescription;

	

	public Integer getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
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

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApplicationDescription() {
		return applicationDescription;
	}

	public void setApplicationDescription(String applicationDescription) {
		this.applicationDescription = applicationDescription;
	}

	@Override
	public String toString() {
		return "ApplicationResponse [applicationId=" + applicationId + ", errorCode=" + errorCode + ", errorMessage="
				+ errorMessage + ", applicationName=" + applicationName + ", applicationDescription="
				+ applicationDescription + "]";
	}

	public ApplicationResponse(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public ApplicationResponse() {
		super();
	}
	
	
	
	
	
	
}
