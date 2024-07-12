package com.insignia.model;

public class SubscriptonLinkRequest {

	private Long customerSequenceNumber;
	private Integer expirationDuration;
	private Long subscriptionPlanSequenceNumber;
	private boolean isToForceDelete;

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

	public Long getSubscriptionPlanSequenceNumber() {
		return subscriptionPlanSequenceNumber;
	}

	public void setSubscriptionPlanSequenceNumber(Long subscriptionPlanSequenceNumber) {
		this.subscriptionPlanSequenceNumber = subscriptionPlanSequenceNumber;
	}

	public boolean getIsToForceDelete() {
		return isToForceDelete;
	}

	public void setToForceDelete(boolean isToForceDelete) {
		this.isToForceDelete = isToForceDelete;
	}

	@Override
	public String toString() {
		return "SubscriptonLinkRequest [customerSequenceNumber=" + customerSequenceNumber + ", expirationDuration="
				+ expirationDuration + ", subscriptionPlanSequenceNumber=" + subscriptionPlanSequenceNumber
				+ ", isToForceDelete=" + isToForceDelete + "]";
	}

}
