package com.insignia.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class MeasurementUnitResponse {

	private String errorCode;
	private String errorMessage;

	private Integer sequenceNumber;
	private String unitName;
	private String unitDescription;
	
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
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getUnitDescription() {
		return unitDescription;
	}
	public void setUnitDescription(String unitDescription) {
		this.unitDescription = unitDescription;
	}
	@Override
	public String toString() {
		return "MeasurementUnitResponse [errorCode=" + errorCode + ", errorMessage=" + errorMessage
				+ ", sequenceNumber=" + sequenceNumber + ", unitName=" + unitName + ", unitDescription="
				+ unitDescription + "]";
	}
	public MeasurementUnitResponse(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	public MeasurementUnitResponse() {
		super();
	}

	
	
	
}
