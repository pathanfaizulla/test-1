package com.insignia.model;

public class ProductColourRequest {

	private Long customerSequenceNumber;
	private Integer expirationDuration;
	private Integer sequenceNumber;
	private String applicationId;
	private String tenantId;
	private String colourName;
	private boolean isColourNameUpdated;
	private String colourDescription;
	private boolean isColourDescriptionUpdated;
	private String hexCode;
	private boolean isHexCodeUpdated;

	public Long getCustomerSequenceNumber() {
		return customerSequenceNumber;
	}

	public void setCustomerSequenceNumber(Long customerSequenceNumber) {
		this.customerSequenceNumber = customerSequenceNumber;
	}

	public Integer getExpirationDuration() {
		return expirationDuration;
	}

	public void setExpirationDuration(Integer expirationDuration) {
		this.expirationDuration = expirationDuration;
	}

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getColourName() {
		return colourName;
	}

	public void setColourName(String colourName) {
		this.colourName = colourName;
		this.isColourNameUpdated = true;
	}

	public boolean isColourNameUpdated() {
		return isColourNameUpdated;
	}

	public String getColourDescription() {
		return colourDescription;
	}

	public void setColourDescription(String colourDescription) {
		this.colourDescription = colourDescription;
		this.isColourDescriptionUpdated = true;
	}

	public boolean isColourDescriptionUpdated() {
		return isColourDescriptionUpdated;
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

	public String getHexCode() {
		return hexCode;
	}

	public void setHexCode(String hexCode) {
		this.hexCode = hexCode;
		this.isHexCodeUpdated = true;
	}

	public boolean isHexCodeUpdated() {
		return isHexCodeUpdated;
	}
	
	

}
