package com.insignia.model;

public class ProductBrandRequest {

	private Long customerSequenceNumber;
	private Integer expirationDuration;
	private String applicationId;
	private String tenantId;
	private Integer sequenceNumber;
	private String brandName;
	private boolean isBrandNameUpdated;
	private String brandDescription;
	private boolean isBrandDescriptionUpdated;
	private String productBrandImagePath;
	private boolean isProductBrandImagePathUpdated;
	private String defaultImage;
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

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
		this.isBrandNameUpdated = true;
	}

	public boolean isBrandNameUpdated() {
		return isBrandNameUpdated;
	}

	public String getBrandDescription() {
		return brandDescription;
	}

	public void setBrandDescription(String brandDescription) {
		this.brandDescription = brandDescription;
		this.isBrandDescriptionUpdated = true;
	}

	public boolean isBrandDescriptionUpdated() {
		return isBrandDescriptionUpdated;
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
	
	public String getProductBrandImagePath() {
		return productBrandImagePath;
	}
		
	public void setProductBrandImagePath(String productImagePath) {
		this.productBrandImagePath = productImagePath;
		this.isProductBrandImagePathUpdated = true;
	}
	
	public boolean isProductBrandImagePathUpdated() {
		return isProductBrandImagePathUpdated;
	}
	
	public boolean isDefaultImageUpdated() {
		return isDefaultImageUpdated;
	}

}
