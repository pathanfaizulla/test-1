package com.insignia.model;

import java.io.Serializable;
import java.util.List;

public class CustomListDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long customListSequenceNumber;
	private String customListName;
	private List<ProductDetailsResponse> productDetailsResponse;

	public Long getCustomListSequenceNumber() {
		return customListSequenceNumber;
	}

	public void setCustomListSequenceNumber(Long customListSequenceNumber) {
		this.customListSequenceNumber = customListSequenceNumber;
	}

	public String getCustomListName() {
		return customListName;
	}

	public void setCustomListName(String customListName) {
		this.customListName = customListName;
	}

	public List<ProductDetailsResponse> getProductDetailsResponse() {
		return productDetailsResponse;
	}

	public void setProductDetailsResponse(List<ProductDetailsResponse> productDetailsResponse) {
		this.productDetailsResponse = productDetailsResponse;
	}

	@Override
	public String toString() {
		return "CustomListDetails [customListSequenceNumber=" + customListSequenceNumber + ", customListName="
				+ customListName + ", productDetailsResponse=" + productDetailsResponse + "]";
	}

}
