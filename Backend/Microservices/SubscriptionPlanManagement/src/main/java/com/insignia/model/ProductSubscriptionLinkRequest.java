package com.insignia.model;

import java.util.List;


public class ProductSubscriptionLinkRequest extends SubscriptonLinkRequest {

	private Long productSequenceNumber;
	private List<Long> subscriptionSequenceNumberList;
	
	
	public Long getProductSequenceNumber() {
		return productSequenceNumber;
	}
	public void setProductSequenceNumber(Long productSequenceNumber) {
		this.productSequenceNumber = productSequenceNumber;
	}
	public List<Long> getSubscriptionSequenceNumberList() {
		return subscriptionSequenceNumberList;
	}
	public void setSubscriptionSequenceNumberList(List<Long> subscriptionSequenceNumberList) {
		this.subscriptionSequenceNumberList = subscriptionSequenceNumberList;
	}
	
	
	
	
	

}
