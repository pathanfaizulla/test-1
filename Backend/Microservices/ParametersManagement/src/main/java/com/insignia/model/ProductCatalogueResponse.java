package com.insignia.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ProductCatalogueResponse {

	private String errorCode;
	private String errorMessage;

	private Integer sequenceNumber;
	private String catalogueName;
	private String catalogueDescription;
	
	private String productCatalogueImagePath;
	private String defaultImage;

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
	}

	public String getCatalogueDescription() {
		return catalogueDescription;
	}

	public void setCatalogueDescription(String catalogueDescription) {
		this.catalogueDescription = catalogueDescription;
	}

	public ProductCatalogueResponse(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public ProductCatalogueResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}
	
	public String getDefaultImage() {
		return defaultImage;
	}
	
	public void setProductCatalogueImagePath(String productImagePath) {
		this.productCatalogueImagePath = productImagePath;
	}

	public String getProductCatalogueImagePath() {
		return productCatalogueImagePath;
	}

}
