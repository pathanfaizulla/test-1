package com.insignia.model;

import java.util.List;

public class BillingAndInvoiceRequest {

	private Long invoiceNumber;
	private Long customerSequenceNumber;
	private Long orderSequenceNumber;
	private String invoiceDate;
	private boolean isInvoiceDateUpdated;
	private String dueDate;
	private boolean isDueDateUpdated;
	private String status;
	private boolean isStatusUpdated;
	private String dateOfPayment;
	private boolean isDateOfPaymentUpdated;
	private String currency;
	private boolean isCurrencyUpdated;
	private String modeOfPayment;
	private boolean isModeOfPaymentUpdated;
	private Integer expirationDuration;
	private List<Long> invoiceNumberList;

	public Long getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Long getCustomerSequenceNumber() {
		return customerSequenceNumber;
	}

	public void setCustomerSequenceNumber(Long customerSequenceNumber) {
		this.customerSequenceNumber = customerSequenceNumber;
	}

	public Long getOrderSequenceNumber() {
		return orderSequenceNumber;
	}

	public void setOrderSequenceNumber(Long orderSequenceNumber) {
		this.orderSequenceNumber = orderSequenceNumber;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
		this.isInvoiceDateUpdated = true;
	}

	public boolean isInvoiceDateUpdated() {
		return isInvoiceDateUpdated;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
		this.isDueDateUpdated = true;
	}

	public boolean isDueDateUpdated() {
		return isDueDateUpdated;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		this.isStatusUpdated = true;
	}

	public boolean isStatusUpdated() {
		return isStatusUpdated;
	}

	public String getDateOfPayment() {
		return dateOfPayment;
	}

	public void setDateOfPayment(String dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
		this.isDateOfPaymentUpdated = true;
	}

	public boolean isDateOfPaymentUpdated() {
		return isDateOfPaymentUpdated;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
		this.isCurrencyUpdated = true;
	}

	public boolean isCurrencyUpdated() {
		return isCurrencyUpdated;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
		this.isModeOfPaymentUpdated = true;
	}

	public boolean isModeOfPaymentUpdated() {
		return isModeOfPaymentUpdated;
	}

	public Integer getExpirationDuration() {
		return expirationDuration;
	}

	public void setExpirationDuration(Integer expirationDuration) {
		this.expirationDuration = expirationDuration;
	}

	public List<Long> getInvoiceNumberList() {
		return invoiceNumberList;
	}

	public void setInvoiceNumberList(List<Long> invoiceNumberList) {
		this.invoiceNumberList = invoiceNumberList;
	}

}
