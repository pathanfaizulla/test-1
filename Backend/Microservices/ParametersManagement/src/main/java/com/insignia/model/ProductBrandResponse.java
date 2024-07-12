package com.insignia.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ProductBrandResponse {

	private String errorCode;
	private String errorMessage;

	private Integer sequenceNumber;
	private String brandName;
	private String brandDescription;
	
	private String productBrandImagePath;
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

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandDescription() {
		return brandDescription;
	}

	public void setBrandDescription(String brandDescription) {
		this.brandDescription = brandDescription;
	}

	public ProductBrandResponse(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public ProductBrandResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}
	
	public String getDefaultImage() {
		return defaultImage;
	}
	
	public void setProductBrandImagePath(String productImagePath) {
		this.productBrandImagePath = productImagePath;
	}

	public String getProductBrandImagePath() {
		return productBrandImagePath;
	}

}
