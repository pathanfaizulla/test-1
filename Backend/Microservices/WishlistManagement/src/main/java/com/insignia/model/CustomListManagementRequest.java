package com.insignia.model;

import java.util.List;

public class CustomListManagementRequest {
	private Long customerSequenceNumber;
	private Integer tokenExpirationDuration;
	private List<CustomListDetailsRequest> customListDetailsRequestList;

	public Long getCustomerSequenceNumber() {
		return customerSequenceNumber;
	}

	public void setCustomerSequenceNumber(Long customerSequenceNumber) {
		this.customerSequenceNumber = customerSequenceNumber;
	}

	public Integer getTokenExpirationDuration() {
		return tokenExpirationDuration;
	}

	public void setTokenExpirationDuration(Integer tokenExpirationDuration) {
		this.tokenExpirationDuration = tokenExpirationDuration;
	}

	public List<CustomListDetailsRequest> getCustomListDetailsRequestList() {
		return customListDetailsRequestList;
	}

	public void setCustomListDetailsRequestList(List<CustomListDetailsRequest> customListDetailsRequestList) {
		this.customListDetailsRequestList = customListDetailsRequestList;
	}

	@Override
	public String toString() {
		return "CustomListManagementRequest [customerSequenceNumber=" + customerSequenceNumber + ", tokenExpirationDuration="
				+ tokenExpirationDuration + ", customListDetailsRequestList=" + customListDetailsRequestList + "]";
	}

}
