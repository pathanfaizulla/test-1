package com.insignia.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="discount")
public class Discount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sequence_number")
	private Long sequenceNumber;
	
	@Column(name="product_id")
	private Long productId;
	
	@Column(name="order_id")
	private Long orderId;
	
	@Column(name="customer_sequence_number")
	private Long customerSequenceNumber;
	
	@Column(name="discount_applied")
	private BigDecimal discountApplied;
	
	@Column(name="amount_to_pay")
	private BigDecimal amoubtToPay;

	public Long getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getCustomerSequenceNumber() {
		return customerSequenceNumber;
	}

	public void setCustomerSequenceNumber(Long customerSequenceNumber) {
		this.customerSequenceNumber = customerSequenceNumber;
	}

	public BigDecimal getDiscountApplied() {
		return discountApplied;
	}

	public void setDiscountApplied(BigDecimal discountApplied) {
		this.discountApplied = discountApplied;
	}

	public BigDecimal getAmoubtToPay() {
		return amoubtToPay;
	}

	public void setAmoubtToPay(BigDecimal amoubtToPay) {
		this.amoubtToPay = amoubtToPay;
	}

	public Discount(Long productId, Long orderId, Long customerSequenceNumber, BigDecimal discountApplied, BigDecimal amoubtToPay) {
		super();
		this.productId = productId;
		this.orderId = orderId;
		this.customerSequenceNumber = customerSequenceNumber;
		this.discountApplied = discountApplied;
		this.amoubtToPay = amoubtToPay;
	}
	public Discount()
	{
		
	}
	
}
