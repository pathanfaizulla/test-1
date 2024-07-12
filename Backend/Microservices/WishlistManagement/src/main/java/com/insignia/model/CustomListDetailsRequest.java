package com.insignia.model;

import java.util.List;

public class CustomListDetailsRequest {

	private Long sequenceNumber;
	private String customListName;
	private List<ProductDetailsRequest> productDetailsRequestList;
	private List<Long> delinkedProductIdList;

	public Long getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getCustomListName() {
		return customListName;
	}

	public void setCustomListName(String customListName) {
		this.customListName = customListName;
	}

	public List<ProductDetailsRequest> getProductDetailsRequestList() {
		return productDetailsRequestList;
	}

	public void setProductDetailsRequestList(List<ProductDetailsRequest> productDetailsRequestList) {
		this.productDetailsRequestList = productDetailsRequestList;
	}

	public List<Long> getDelinkedProductIdList() {
		return delinkedProductIdList;
	}

	public void setDelinkedProductIdList(List<Long> delinkedProductIdList) {
		this.delinkedProductIdList = delinkedProductIdList;
	}

	@Override
	public String toString() {
		return "CustomListDetailsRequest [sequenceNumber=" + sequenceNumber + ", customListName=" + customListName
				+ ", productDetailsRequestList=" + productDetailsRequestList + ", delinkedProductIdList="
				+ delinkedProductIdList + "]";
	}

}
