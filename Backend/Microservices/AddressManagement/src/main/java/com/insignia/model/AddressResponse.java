package com.insignia.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AddressResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer sequenceNumber;
	
	private String errorCode;

	private String errorMessage;

	private String addressLine1;

	private String addressLine2;

	private String landmark;

	private String city;

	private String state;

	private String country;

	private String zipCode;

	private String mobileNumber;

	private String landlineNumber;

	private String emailId;

	private boolean isDefaultAddress;

	private boolean isBillingAddress;
	
	

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
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

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getLandlineNumber() {
		return landlineNumber;
	}

	public void setLandlineNumber(String landlineNumber) {
		this.landlineNumber = landlineNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public boolean isDefaultAddress() {
		return isDefaultAddress;
	}

	public void setDefaultAddress(boolean isDefaultAddress) {
		this.isDefaultAddress = isDefaultAddress;
	}

	public boolean isBillingAddress() {
		return isBillingAddress;
	}

	public void setBillingAddress(boolean isBillingAddress) {
		this.isBillingAddress = isBillingAddress;
	}

	public AddressResponse(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public AddressResponse() {
	}

	@Override
	public String toString() {
		return "AddressResponse [sequenceNumber=" + sequenceNumber + ", errorCode=" + errorCode + ", errorMessage="
				+ errorMessage + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", landmark="
				+ landmark + ", city=" + city + ", state=" + state + ", country=" + country + ", zipCode=" + zipCode
				+ ", mobileNumber=" + mobileNumber + ", landlineNumber=" + landlineNumber + ", emailId=" + emailId
				+ ", isDefaultAddress=" + isDefaultAddress + ", isBillingAddress=" + isBillingAddress + "]";
	}

	

}
