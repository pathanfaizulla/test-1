package com.insignia.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ProductMaterialResponse {

	private String errorCode;
	private String errorMessage;

	private Integer sequenceNumber;
	private String materialName;
	private String materialDescription;

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

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialDescription() {
		return materialDescription;
	}

	public void setMaterialDescription(String materialDescription) {
		this.materialDescription = materialDescription;
	}

	@Override
	public String toString() {
		return "ProductMaterialResponse [errorCode=" + errorCode + ", errorMessage=" + errorMessage
				+ ", sequenceNumber=" + sequenceNumber + ", materialName=" + materialName + ", materialDescription="
				+ materialDescription + "]";
	}

	public ProductMaterialResponse(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public ProductMaterialResponse() {
		super();
	}

}
