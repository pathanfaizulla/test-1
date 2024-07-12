package com.insignia.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PaymentMethodResponse {

	private boolean success;
	private String errorCode;
	private String errorMessage;

	public Long paymentMethodSequenceNumber;
	private String paymentMethodType;
	private String paymentMethodDetails;
	private boolean isDefaultPaymentMethod;

	private String validFrom;
	private String validUpto;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
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

	public String getPaymentMethodType() {
		return paymentMethodType;
	}

	public void setPaymentMethodType(String paymentMethodType) {
		this.paymentMethodType = paymentMethodType;
	}

	public String getPaymentMethodDetails() {
		return paymentMethodDetails;
	}

	public void setPaymentMethodDetails(String paymentMethodDetails) {
		this.paymentMethodDetails = paymentMethodDetails;
	}

	public Long getPaymentMethodSequenceNumber() {
		return paymentMethodSequenceNumber;
	}

	public void setPaymentMethodSequenceNumber(Long paymentMethodSequenceNumber) {
		this.paymentMethodSequenceNumber = paymentMethodSequenceNumber;
	}

	public boolean getIsDefaultPaymentMethod() {
		return isDefaultPaymentMethod;
	}

	public void setIsDefaultPaymentMethod(boolean isDefaultPaymentMethod) {
		this.isDefaultPaymentMethod = isDefaultPaymentMethod;
	}

	public String getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	public String getValidUpto() {
		return validUpto;
	}

	public void setValidUpto(String validUpto) {
		this.validUpto = validUpto;
	}

	@Override
	public String toString() {
		return "PaymentMethodResponse [success=" + success + ", errorCode=" + errorCode + ", errorMessage="
				+ errorMessage + ", paymentMethodSequenceNumber=" + paymentMethodSequenceNumber + ", paymentMethodType="
				+ paymentMethodType + ", paymentMethodDetails=" + paymentMethodDetails + ", isDefaultPaymentMethod="
				+ isDefaultPaymentMethod + ", validFrom=" + validFrom + ", validUpto=" + validUpto + "]";
	}

	public PaymentMethodResponse(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public PaymentMethodResponse() {
		super();
	}

}
