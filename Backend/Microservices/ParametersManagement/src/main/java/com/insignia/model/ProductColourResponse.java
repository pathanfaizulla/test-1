package com.insignia.model;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ProductColourResponse {

	private String errorCode;
	private String errorMessage;
	
	private Integer sequenceNumber;
	private String colourName;
	private String colourDescription;
	private String hexCode;
	
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
	public String getColourName() {
		return colourName;
	}
	public void setColourName(String colourName) {
		this.colourName = colourName;
	}
	public String getColourDescription() {
		return colourDescription;
	}
	public void setColourDescription(String colourDescription) {
		this.colourDescription = colourDescription;
	}
		
	public String getHexCode() {
		return hexCode;
	}
	public void setHexCode(String hexCode) {
		this.hexCode = hexCode;
	}
	
	@Override
	public String toString() {
		return "ProductColourResponse [errorCode=" + errorCode + ", errorMessage=" + errorMessage + ", sequenceNumber="
				+ sequenceNumber + ", colourName=" + colourName + ", colourDescription=" + colourDescription
				+ ", hexCode=" + hexCode + "]";
	}
	public ProductColourResponse(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	public ProductColourResponse() {
		super();
	}
	
	

}
