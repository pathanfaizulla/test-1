package com.insignia.model;

import java.util.List;

public class WishlistDetailsRequest {

	private Long customerSequenceNumber;
	private Integer expirationDuration;
	
	private List<ProductDetailsRequest> productDetailsRequestList;

	public Long getCustomerSequenceNumber() {
		return customerSequenceNumber;
	}

	public void setCustomerSequenceNumber(Long customerSequenceNumber) {
		this.customerSequenceNumber = customerSequenceNumber;
	}

	public Integer getExpirationDuration() {
		return expirationDuration;
	}

	public void setExpirationDuration(Integer expirationDuration) {
		this.expirationDuration = expirationDuration;
	}

	public List<ProductDetailsRequest> getProductDetailsRequestList() {
		return productDetailsRequestList;
	}

	public void setProductDetailsRequestList(List<ProductDetailsRequest> productDetailsRequestList) {
		this.productDetailsRequestList = productDetailsRequestList;
	}

	@Override
	public String toString() {
		return "WishlistDetailsRequest [customerSequenceNumber=" + customerSequenceNumber + ", expirationDuration="
				+ expirationDuration + ", productDetailsRequestList=" + productDetailsRequestList + "]";
	}

	
}
