package com.insignia.model;

public class AddressRequest {

	private Integer sequenceNumber;
	
	private Integer expirationDuration;

	private Long customerSequenceNumber;

	private String addressLine1;

	private boolean isAddressLine1Updated;

	private String addressLine2;

	private boolean isAddressLine2Updated;

	private String landmark;

	private boolean isLandMarkUpdated;

	private String city;

	private boolean isCityUpdated;

	private String state;

	private boolean isStateUpdated;

	private String country;

	private boolean isCountryUpdated;

	private String zipCode;

	private boolean isZipCodeUpdated;

	private String mobileNumber;

	private boolean isMobileNumberUpdated;

	private String landlineNumber;

	private boolean isLandLineNumberUpdated;

	private String emailId;

	private boolean isEmailIdUpdated;

	private boolean isDefaultAddress;

	private boolean isDefaultAddressUpdated;

	private boolean isBillingAddress;

	private boolean isBillingAddressUpdated;

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

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

	public String getAddressLine1() {
		return addressLine1;
	}

	public boolean isAddressLine1Updated() {
		return isAddressLine1Updated;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
		this.isAddressLine1Updated = true;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
		this.isAddressLine2Updated = true;
	}

	public boolean isAddressLine2Updated() {
		return isAddressLine2Updated;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
		this.isLandMarkUpdated = true;
	}

	public boolean isLandMarkUpdated() {
		return isLandMarkUpdated;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
		this.isCityUpdated = true;
	}

	public boolean isCityUpdated() {
		return isCityUpdated;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
		this.isStateUpdated = true;
	}

	public boolean isStateUpdated() {
		return isStateUpdated;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
		this.isCountryUpdated = true;
	}

	public boolean isCountryUpdated() {
		return isCountryUpdated;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
		this.isZipCodeUpdated = true;
	}

	public boolean isZipCodeUpdated() {
		return isZipCodeUpdated;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
		this.isMobileNumberUpdated = true;
	}

	public boolean isMobileNumberUpdated() {
		return isMobileNumberUpdated;
	}

	public String getLandlineNumber() {
		return landlineNumber;
	}

	public void setLandlineNumber(String landlineNumber) {
		this.landlineNumber = landlineNumber;
		this.isLandLineNumberUpdated = true;
	}

	public boolean isLandLineNumberUpdated() {
		return isLandLineNumberUpdated;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
		this.isEmailIdUpdated = true;
	}

	public boolean isEmailAddressUpdated() {
		return isEmailIdUpdated;
	}

	public Boolean getIsDefaultAddress() {
		return isDefaultAddress;
	}

	public void setIsDefaultAddress(Boolean isDefaultAddress) {
		this.isDefaultAddress = isDefaultAddress;
		this.isDefaultAddressUpdated = true;
	}

	public boolean isDefaultAddressUpdated() {
		return isDefaultAddressUpdated;
	}

	public boolean getIsBillingAddress() {
		return isBillingAddress;
	}

	public void setisBillingAddress(Boolean isBillingAddress) {
		this.isBillingAddress = isBillingAddress;
		this.isBillingAddressUpdated = true;
	}

	public boolean isBillingAddressUpdated() {
		return isBillingAddressUpdated;
	}

	@Override
	public String toString() {
		return "AddressRequest [sequenceNumber=" + sequenceNumber + ", expirationDuration=" + expirationDuration
				+ ", customerSequenceNumber=" + customerSequenceNumber + ", addressLine1=" + addressLine1
				+ ", isAddressLine1Updated=" + isAddressLine1Updated + ", addressLine2=" + addressLine2
				+ ", isAddressLine2Updated=" + isAddressLine2Updated + ", landmark=" + landmark + ", isLandMarkUpdated="
				+ isLandMarkUpdated + ", city=" + city + ", isCityUpdated=" + isCityUpdated + ", state=" + state
				+ ", isStateUpdated=" + isStateUpdated + ", country=" + country + ", isCountryUpdated="
				+ isCountryUpdated + ", zipCode=" + zipCode + ", isZipCodeUpdated=" + isZipCodeUpdated
				+ ", mobileNumber=" + mobileNumber + ", isMobileNumberUpdated=" + isMobileNumberUpdated
				+ ", landlineNumber=" + landlineNumber + ", isLandLineNumberUpdated=" + isLandLineNumberUpdated
				+ ", emailId=" + emailId + ", isEmailIdUpdated=" + isEmailIdUpdated + ", isDefaultAddress="
				+ isDefaultAddress + ", isDefaultAddressUpdated=" + isDefaultAddressUpdated + ", isBillingAddress="
				+ isBillingAddress + ", isBillingAddressUpdated=" + isBillingAddressUpdated + "]";
	}

	
}
