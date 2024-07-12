package com.insignia.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ApplicationPreferenceResponse implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	private String errorCode;

	private String errorMessage;
	
	private Integer applicationId;

	private String applicationName;

	private List<AppPreferenceResponse> appPreferenceResponseList = new ArrayList<>();

	
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

	
	
	public Integer getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	

	public List<AppPreferenceResponse> getAppPreferenceResponseList() {
		return appPreferenceResponseList;
	}

	public void setAppPreferenceResponseList(List<AppPreferenceResponse> appPreferenceResponseList) {
		this.appPreferenceResponseList = appPreferenceResponseList;
	}

	
	public ApplicationPreferenceResponse(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public ApplicationPreferenceResponse() {
		super();
	}

	@Override
	public String toString() {
		return "ApplicationPreferenceResponse [errorCode=" + errorCode + ", errorMessage=" + errorMessage
				+ ", applicationId=" + applicationId + ", applicationName=" + applicationName
				+ ", appPreferenceResponseList=" + appPreferenceResponseList + "]";
	}

}
