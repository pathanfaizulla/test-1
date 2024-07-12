package com.insignia.model;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;


@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class BillingAndInvoiceResponse {
	private Long invoiceNumber;	
	private Long customerSequenceNumber;
	private Long orderSequenceNumber;
	private Date invoiceDate;
	private Date dueDate;
	private String status;
	private Date dateOfPayment;
	private String currency;
	private String modeOfPayment;
	private boolean success;
	private String errorCode;
	private String errorMessage;
	
	public Long getCustomerSequenceNumber() {
		return customerSequenceNumber;
	}
	public void setCustomerSequenceNumber(Long customerSequenceNumber) {
		this.customerSequenceNumber = customerSequenceNumber;
	}
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
	
	
	public BillingAndInvoiceResponse()
	{
		super();
	}
	
	
	public BillingAndInvoiceResponse(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	public Long getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public Long getOrderSequenceNumber() {
		return orderSequenceNumber;
	}
	public void setOrderSequenceNumber(Long orderSequenceNumber) {
		this.orderSequenceNumber = orderSequenceNumber;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDateOfPayment() {
		return dateOfPayment;
	}
	public void setDateOfPayment(Date dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getModeOfPayment() {
		return modeOfPayment;
	}
	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}
	

	

}
