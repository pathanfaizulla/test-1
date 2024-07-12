package com.insignia.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PreferredIpDetailsResponse {

	private String errorCode;
	private String errorMessage;

	private List<CustomerIpDetails> customerIpDetailsList = null;

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

	public List<CustomerIpDetails> getCustomerIpDetailsList() {
		return customerIpDetailsList;
	}

	public void setCustomerIpDetailsList(List<CustomerIpDetails> customerIpDetailsList) {
		this.customerIpDetailsList = customerIpDetailsList;
	}

	public PreferredIpDetailsResponse(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public PreferredIpDetailsResponse() {
		super();
	}

}
