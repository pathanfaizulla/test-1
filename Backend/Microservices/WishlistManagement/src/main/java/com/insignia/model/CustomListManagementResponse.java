package com.insignia.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomListManagementResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long customerSequenceNumber;

	private String errorCode;
	private String errorMessage;
	private Integer successCode;
	private String successMessage;

	private List<CustomListDetails> customListDetails = null;

	public Long getCustomerSequenceNumber() {
		return customerSequenceNumber;
	}

	public void setCustomerSequenceNumber(Long customerSequenceNumber) {
		this.customerSequenceNumber = customerSequenceNumber;
	}

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

	public Integer getSuccessCode() {
		return successCode;
	}

	public void setSuccessCode(Integer successCode) {
		this.successCode = successCode;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public List<CustomListDetails> getCustomListDetails() {
		return customListDetails;
	}

	public void setCustomListDetails(List<CustomListDetails> customListDetails) {
		this.customListDetails = customListDetails;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

	@Override
	public String toString() {
		return "CustomListManagementResponse [customerSequenceNumber=" + customerSequenceNumber + ", errorCode="
				+ errorCode + ", errorMessage=" + errorMessage + ", successCode=" + successCode + ", successMessage="
				+ successMessage + ", customListDetails=" + customListDetails + "]";
	}

	public CustomListManagementResponse(Integer successCode, String successMessage) {
		super();
		this.successCode = successCode;
		this.successMessage = successMessage;
	}

	public CustomListManagementResponse(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public CustomListManagementResponse() {
		super();
	}

}
