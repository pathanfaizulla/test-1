package com.insignia.model;

import java.math.BigDecimal;

public class CurrencyDetailsRequest {

	private Long customerSequenceNumber;
	private Integer expirationDuration;
	private Integer sequenceNumber;
	private String applicationId;
	private String tenantId;

	private String currencyName;
	private boolean isCurrencyNameUpdated;

	private String currencyDescription;
	private boolean isCurrencyDescriptionUpdated;

	private String currencyCode;
	private boolean isCurrencyCodeUpdated;

	private BigDecimal baseRate;
	private boolean isBaseRateIsUpdated;

	private String baseRateCurrency;
	private boolean isBaseRateCurrencyIsUpdated;

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

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
		this.isCurrencyNameUpdated = true;
	}

	public boolean isCurrencyNameUpdated() {
		return isCurrencyNameUpdated;
	}

	public String getCurrencyDescription() {
		return currencyDescription;
	}

	public void setCurrencyDescription(String currencyDescription) {
		this.currencyDescription = currencyDescription;
		this.isCurrencyDescriptionUpdated = true;
	}

	public boolean isCurrencyDescriptionUpdated() {
		return isCurrencyDescriptionUpdated;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
		this.isCurrencyCodeUpdated = true;
	}

	public boolean isCurrencyCodeUpdated() {
		return isCurrencyCodeUpdated;
	}

	public BigDecimal getBaseRate() {
		return baseRate;
	}

	public void setBaseRate(BigDecimal baseRate) {
		this.baseRate = baseRate;
		this.isBaseRateIsUpdated = true;
	}

	public boolean isBaseRateIsUpdated() {
		return isBaseRateIsUpdated;
	}

	public String getBaseRateCurrency() {
		return baseRateCurrency;
	}

	public void setBaseRateCurrency(String baseRateCurrency) {
		this.baseRateCurrency = baseRateCurrency;
		this.isBaseRateCurrencyIsUpdated = true;
	}

	public boolean isBaseRateCurrencyIsUpdated() {
		return isBaseRateCurrencyIsUpdated;
	}

}
