package com.insignia.model;

public class ProductFamilyRequest {

	private Long customerSequenceNumber;
	private Integer expirationDuration;
	private Integer sequenceNumber;
	private String applicationId;
	private String tenantId;
	private String familyName;
	private boolean isFamilyNameUpdated;
	private String familyDescription;
	private boolean isFamilyDescriptionUpdated;
	private String productFamilyImagePath;
	private String defaultImage;
	private boolean isProductFamilyImagePathUpdated;
	private boolean isDefaultImageUpdated;

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

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
		this.isFamilyNameUpdated = true;
	}

	public boolean isFamilyNameUpdated() {
		return isFamilyNameUpdated;
	}

	public String getFamilyDescription() {
		return familyDescription;
	}

	public void setFamilyDescription(String familyDescription) {
		this.familyDescription = familyDescription;
		this.isFamilyDescriptionUpdated = true;
	}

	public boolean isFamilyDescriptionUpdated() {
		return isFamilyDescriptionUpdated;
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
	
	public String getDefaultImage() {
		return defaultImage;
	}
	
	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
		this.isDefaultImageUpdated = true;
	}
	
	public String getProductFamilyImagePath() {
		return productFamilyImagePath;
	}
		
	public void setProductFamilyImagePath(String productImagePath) {
		this.productFamilyImagePath = productImagePath;
		this.isProductFamilyImagePathUpdated = true;
	}
	
	public boolean isProductFamilyImagePathUpdated() {
		return isProductFamilyImagePathUpdated;
	}
	
	public boolean isDefaultImageUpdated() {
		return isDefaultImageUpdated;
	}

}
