package com.insignia.model;

public class CustomerStoreDetailsRequest {

	private Integer storeSequenceNumber;

	private Long customerSequenceNumber;

	private String storeManager;
	private boolean isStoreManagerUpdated;

	private String markupFactor;
	private boolean isMarkupFactorUpdated;

	private String hearAboutUs;
	private boolean isHearAboutUsUpdated;

	private String markets;
	private boolean isMarketsUpdated;

	private String storeName;
	private boolean isStoreNameUpdated;

	private String storeContact;
	private boolean isStoreContactUpdated;

	private String storeAddress;
	private boolean isStoreAddressUpdated;

	private String storeCountry;
	private boolean isStoreCountryUpdated;

	private String storeState;
	private boolean isStoreStateUpdated;

	private String storeCity;
	private boolean isStoreCityUpdated;

	private String storeZipCode;
	private boolean isStoreZipCodeUpdated;

	private String telephone;
	private boolean isTelephoneUpdated;

	private String website;
	private boolean isWebsiteUpdated;

	private String resaleLicense;
	private boolean isResaleLicenseUpdated;

	private String businessType;
	private boolean isBusinessTypeUpdated;

	public Integer getStoreSequenceNumber() {
		return storeSequenceNumber;
	}

	public void setStoreSequenceNumber(Integer storeSequenceNumber) {
		this.storeSequenceNumber = storeSequenceNumber;
	}

	public Long getCustomerSequenceNumber() {
		return customerSequenceNumber;
	}

	public void setCustomerSequenceNumber(Long customerSequenceNumber) {
		this.customerSequenceNumber = customerSequenceNumber;
	}

	public String getMarkupFactor() {
		return markupFactor;
	}

	public void setMarkupFactor(String markupFactor) {
		this.markupFactor = markupFactor;
		this.isMarkupFactorUpdated = true;
	}

	public String getStoreManager() {
		return storeManager;
	}

	public void setStoreManager(String storeManager) {
		this.storeManager = storeManager;
		this.isStoreManagerUpdated = true;
	}

	public boolean isStoreManagerUpdated() {
		return isStoreManagerUpdated;
	}

	public boolean isMarkupFactorUpdated() {
		return isMarkupFactorUpdated;
	}

	public String getHearAboutUs() {
		return hearAboutUs;
	}

	public void setHearAboutUs(String hearAboutUs) {
		this.hearAboutUs = hearAboutUs;
		this.isHearAboutUsUpdated = true;
	}

	public boolean isHearAboutUsUpdated() {
		return isHearAboutUsUpdated;
	}

	public String getMarkets() {
		return markets;
	}

	public void setMarkets(String markets) {
		this.markets = markets;
		this.isMarketsUpdated = true;
	}

	public boolean isMarketsUpdated() {
		return isMarketsUpdated;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
		this.isStoreNameUpdated = true;
	}

	public boolean isStoreNameUpdated() {
		return isStoreNameUpdated;
	}

	public String getStoreContact() {
		return storeContact;
	}

	public void setStoreContact(String storeContact) {
		this.storeContact = storeContact;
		this.isStoreContactUpdated = true;
	}

	public boolean isStoreContactUpdated() {
		return isStoreContactUpdated;
	}

	public String getStoreAddress() {
		return storeAddress;
	}

	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
		this.isStoreAddressUpdated = true;
	}

	public boolean isStoreAddressUpdated() {
		return isStoreAddressUpdated;
	}

	public String getStoreCountry() {
		return storeCountry;
	}

	public void setStoreCountry(String storeCountry) {
		this.storeCountry = storeCountry;
		this.isStoreCountryUpdated = true;
	}

	public boolean isStoreCountryUpdated() {
		return isStoreCountryUpdated;
	}

	public String getStoreState() {
		return storeState;
	}

	public void setStoreState(String storeState) {
		this.storeState = storeState;
		this.isStoreStateUpdated = true;
	}

	public boolean isStoreStateUpdated() {
		return isStoreStateUpdated;

	}

	public String getStoreCity() {
		return storeCity;
	}

	public void setStoreCity(String storeCity) {
		this.storeCity = storeCity;
		this.isStoreCityUpdated = true;
	}

	public boolean isStoreCityUpdated() {
		return isStoreCityUpdated;
	}

	public String getStoreZipCode() {
		return storeZipCode;
	}

	public void setStoreZipCode(String storeZipCode) {
		this.storeZipCode = storeZipCode;
		this.isStoreZipCodeUpdated = true;
	}

	public boolean isStoreZipCodeUpdated() {
		return isStoreZipCodeUpdated;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
		this.isTelephoneUpdated = true;
	}

	public boolean isTelephoneUpdated() {
		return isTelephoneUpdated;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
		this.isWebsiteUpdated = true;
	}

	public boolean isWebsiteUpdated() {
		return isWebsiteUpdated;
	}

	public String getResaleLicense() {
		return resaleLicense;
	}

	public void setResaleLicense(String resaleLicense) {
		this.resaleLicense = resaleLicense;
		this.isResaleLicenseUpdated = true;
	}

	public boolean isResaleLicenseUpdated() {
		return isResaleLicenseUpdated;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
		this.isBusinessTypeUpdated = true;
	}

	public boolean isBusinessTypeUpdated() {
		return isBusinessTypeUpdated;
	}

	@Override
	public String toString() {
		return "CustomerStoreDetailsRequest [storeSequenceNumber=" + storeSequenceNumber + ", customerSequenceNumber="
				+ customerSequenceNumber + ", storeManager=" + storeManager + ", isStoreManagerUpdated="
				+ isStoreManagerUpdated + ", markupFactor=" + markupFactor + ", isMarkupFactorUpdated="
				+ isMarkupFactorUpdated + ", hearAboutUs=" + hearAboutUs + ", isHearAboutUsUpdated="
				+ isHearAboutUsUpdated + ", markets=" + markets + ", isMarketsUpdated=" + isMarketsUpdated
				+ ", storeName=" + storeName + ", isStoreNameUpdated=" + isStoreNameUpdated + ", storeContact="
				+ storeContact + ", isStoreContactUpdated=" + isStoreContactUpdated + ", storeAddress=" + storeAddress
				+ ", isStoreAddressUpdated=" + isStoreAddressUpdated + ", storeCountry=" + storeCountry
				+ ", isStoreCountryUpdated=" + isStoreCountryUpdated + ", storeState=" + storeState
				+ ", isStoreStateUpdated=" + isStoreStateUpdated + ", storeCity=" + storeCity + ", isStoreCityUpdated="
				+ isStoreCityUpdated + ", storeZipCode=" + storeZipCode + ", isStoreZipCodeUpdated="
				+ isStoreZipCodeUpdated + ", telephone=" + telephone + ", isTelephoneUpdated=" + isTelephoneUpdated
				+ ", website=" + website + ", isWebsiteUpdated=" + isWebsiteUpdated + ", resaleLicense=" + resaleLicense
				+ ", isResaleLicenseUpdated=" + isResaleLicenseUpdated + ", businessType=" + businessType
				+ ", isBusinessTypeUpdated=" + isBusinessTypeUpdated + "]";
	}
}
