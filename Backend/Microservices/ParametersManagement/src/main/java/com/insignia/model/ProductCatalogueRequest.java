package com.insignia.model;

public class ProductCatalogueRequest {

	private Long customerSequenceNumber;
	private Integer expirationDuration;
	private Integer sequenceNumber;
	private String applicationId;
	private String tenantId;
	private String catalogueName;
	private boolean isCatalogueNameUpdated;
	private String catalogueDescription;
	private boolean isCatalogueDescriptionUpdated;
	private String productCatalogueImagePath;
	private String defaultImage;
	private boolean isProductCatalogueImagePathUpdated;
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

	public String getCatalogueName() {
		return catalogueName;
	}

	public void setCatalogueName(String catalogueName) {
		this.catalogueName = catalogueName;
		this.isCatalogueNameUpdated = true;
	}

	public boolean isCatalogueNameUpdated() {
		return isCatalogueNameUpdated;
	}

	public String getCatalogueDescription() {
		return catalogueDescription;
	}

	public void setCatalogueDescription(String catalogueDescription) {
		this.catalogueDescription = catalogueDescription;
		this.isCatalogueDescriptionUpdated = true;
	}

	public boolean isCatalogueDescriptionUpdated() {
		return isCatalogueDescriptionUpdated;
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
	
	public String getProductCatalogueImagePath() {
		return productCatalogueImagePath;
	}
		
	public void setProductCatalogueImagePath(String productImagePath) {
		this.productCatalogueImagePath = productImagePath;
		this.isProductCatalogueImagePathUpdated = true;
	}
	
	public boolean isProductCatalogueImagePathUpdated() {
		return isProductCatalogueImagePathUpdated;
	}
	
	public boolean isDefaultImageUpdated() {
		return isDefaultImageUpdated;
	}

}
