package com.insignia.model;

public class ApplicationRequest {

	
	private Integer expirationDuration;

	private Long customerSequenceNumber;

	private Integer applicationId;
	
	private String applicationName;
	
	private boolean isApplicationNameUpdate;
	
	private String applicationDescription;
	
	private boolean isApplicationDescriptionUpdated;

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
		this.isApplicationNameUpdate = true;
	}

	public boolean isApplicationNameUpdate() {
		return isApplicationNameUpdate;
	}

	public String getApplicationDescription() {
		return applicationDescription;
	}

	public void setApplicationDescription(String applicationDescription) {
		this.applicationDescription = applicationDescription;
		this.isApplicationDescriptionUpdated = true;
	}

	public boolean isApplicationDescriptionUpdated() {
		return isApplicationDescriptionUpdated;
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

	@Override
	public String toString() {
		return "ApplicationRequest [expirationDuration=" + expirationDuration + ", customerSequenceNumber="
				+ customerSequenceNumber + ", applicationId=" + applicationId + ", applicationName=" + applicationName
				+ ", isApplicationNameUpdate=" + isApplicationNameUpdate + ", applicationDescription="
				+ applicationDescription + ", isApplicationDescriptionUpdated=" + isApplicationDescriptionUpdated + "]";
	}
	
}
