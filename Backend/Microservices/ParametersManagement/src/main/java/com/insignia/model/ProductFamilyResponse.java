package com.insignia.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ProductFamilyResponse {

	private String errorCode;
	private String errorMessage;

	private Integer sequenceNumber;
	private String familyName;
	private String familyDescription;
	
	private String productFamilyImagePath;
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

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getFamilyDescription() {
		return familyDescription;
	}

	public void setFamilyDescription(String familyDescription) {
		this.familyDescription = familyDescription;
	}

	public ProductFamilyResponse(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public ProductFamilyResponse() {
		super();
	}
	
	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}
	
	public String getDefaultImage() {
		return defaultImage;
	}
	
	public void setProductFamilyImagePath(String productImagePath) {
		this.productFamilyImagePath = productImagePath;
	}

	public String getProductFamilyImagePath() {
		return productFamilyImagePath;
	}

}
