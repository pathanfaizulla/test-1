package com.insignia.model;

public class PaymentMethodRequest {

	private Long customerSequenceNumber;
	private Long paymentMethodSequenceNumber;
	private String paymentMethodType;
	private String paymentMethodDetails;
	private boolean isDefaultPaymentMethod;
	private String validFrom;
	private String validUpto;
	
	private Integer expirationDuration;
	
	
	public Long getCustomerSequenceNumber() {
		return customerSequenceNumber;
	}
	public void setCustomerSequenceNumber(Long customerSequenceNumber) {
		this.customerSequenceNumber = customerSequenceNumber;
	}
	public Long getPaymentMethodSequenceNumber() {
		return paymentMethodSequenceNumber;
	}
	public void setPaymentMethodSequenceNumber(Long paymentMethodSequenceNumber) {
		this.paymentMethodSequenceNumber = paymentMethodSequenceNumber;
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
	public Integer getExpirationDuration() {
		return expirationDuration;
	}
	public void setExpirationDuration(Integer expirationDuration) {
		this.expirationDuration = expirationDuration;
	}
	
	
	

}
