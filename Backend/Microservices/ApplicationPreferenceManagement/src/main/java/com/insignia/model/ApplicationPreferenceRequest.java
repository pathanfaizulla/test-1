package com.insignia.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationPreferenceRequest {

	private Integer expirationDuration;

	private Long customerSequenceNumber;

	private Integer applicationId;

	private String applicationName;

	private List<AppPreferenceRequest> appPreferenceRequestList = new ArrayList<>();

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

	public List<AppPreferenceRequest> getAppPreferenceRequestList() {
		return appPreferenceRequestList;
	}

	public void setAppPreferenceRequestList(List<AppPreferenceRequest> appPreferenceRequestList) {
		this.appPreferenceRequestList = appPreferenceRequestList;
	}

	@Override
	public String toString() {
		return "ApplicationPreferenceRequest [expirationDuration=" + expirationDuration + ", customerSequenceNumber="
				+ customerSequenceNumber + ", applicationId=" + applicationId + ", applicationName=" + applicationName
				+ ", appPreferenceRequestList=" + appPreferenceRequestList + "]";
	}

	

}
