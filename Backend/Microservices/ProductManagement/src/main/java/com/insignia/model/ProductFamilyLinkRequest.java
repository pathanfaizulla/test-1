package com.insignia.model;

public class ProductFamilyLinkRequest {

	private Integer sequenceNumber;
	private Long productSequenceNumber;
	private Integer productFamilySequenceNumber;

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public Long getProductSequenceNumber() {
		return productSequenceNumber;
	}

	public void setProductSequenceNumber(Long productSequenceNumber) {
		this.productSequenceNumber = productSequenceNumber;
	}

	public Integer getProductFamilySequenceNumber() {
		return productFamilySequenceNumber;
	}

	public void setProductFamilySequenceNumber(Integer productFamilySequenceNumber) {
		this.productFamilySequenceNumber = productFamilySequenceNumber;
	}

}
