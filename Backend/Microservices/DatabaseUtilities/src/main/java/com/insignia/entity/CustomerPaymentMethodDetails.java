package com.insignia.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer_payment_method_details")
public class CustomerPaymentMethodDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_method_sequence_number")
	private Long paymentMethodSequenceNumber;

	@Column(name = "customer_sequence_number")
	private Long customerSequenceNumber;

	@Column(name = "payment_method_type")
	private String paymentMethodType;

	@Column(name = "payment_method_details")
	private String paymentMethodDetails;

	@Column(name = "is_default_payment_method")
	private boolean isDefaultPaymentMethod;

	@Column(name = "valid_from")
	private String validFrom;

	@Column(name = "valid_upto")
	private String validUpto;

	public Long getPaymentMethodSequenceNumber() {
		return paymentMethodSequenceNumber;
	}

	public void setPaymentMethodSequenceNumber(Long paymentMethodSequenceNumber) {
		this.paymentMethodSequenceNumber = paymentMethodSequenceNumber;
	}

	public Long getCustomerSequenceNumber() {
		return customerSequenceNumber;
	}

	public void setCustomerSequenceNumber(Long customerSequenceNumber) {
		this.customerSequenceNumber = customerSequenceNumber;
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

	@Override
	public String toString() {
		return "CustomerPaymentMethodDetails [paymentMethodSequenceNumber=" + paymentMethodSequenceNumber
				+ ", customerSequenceNumber=" + customerSequenceNumber + ", paymentMethodType=" + paymentMethodType
				+ ", paymentMethodDetails=" + paymentMethodDetails + ", isDefaultPaymentMethod="
				+ isDefaultPaymentMethod + ", validFrom=" + validFrom + ", validUpto=" + validUpto + "]";
	}

}
