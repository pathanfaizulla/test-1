package com.insignia.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class OrderResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private Long orderSequenceNumber;
	private Long orderId;
	private Date orderDate;
	private String orderStatus;
	private String invoiceId;
	private Long customerSequenceNumber;
	private Long addressSequenceNumber;
	private String errorCode;
	private String errorMessage;
	private BigDecimal totalPrice;

	private List<OrderAndProductLinkResponse> orderAndProductLinkResponseList;

	public OrderResponse() {
		super();
	}

	public OrderResponse(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public Long getOrderSequenceNumber() {
		return orderSequenceNumber;
	}

	public void setOrderSequenceNumber(Long orderSequenceNumber) {
		this.orderSequenceNumber = orderSequenceNumber;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getAddressSequenceNumber() {
		return addressSequenceNumber;
	}

	public void setAddressSequenceNumber(Long addressSequenceNumber) {
		this.addressSequenceNumber = addressSequenceNumber;
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

	public List<OrderAndProductLinkResponse> getOrderAndProductLinkResponseList() {
		return orderAndProductLinkResponseList;
	}

	public void setOrderAndProductLinkResponseList(List<OrderAndProductLinkResponse> orderAndProductLinkResponseList) {
		this.orderAndProductLinkResponseList = orderAndProductLinkResponseList;
	}

	public Long getCustomerSequenceNumber() {
		return customerSequenceNumber;
	}

	public void setCustomerSequenceNumber(Long customerSequenceNumber) {
		this.customerSequenceNumber = customerSequenceNumber;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

}
