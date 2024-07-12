package com.insignia.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CurrencyDetailsResponse {

	private String errorCode;
	private String errorMessage;

	private Integer sequenceNumber;

	private String currencyName;

	private String currencyDescription;

	private String currencyCode;

	private BigDecimal baseRate;

	private String baseRateCurrency;

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

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getCurrencyDescription() {
		return currencyDescription;
	}

	public void setCurrencyDescription(String currencyDescription) {
		this.currencyDescription = currencyDescription;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getBaseRate() {
		return baseRate;
	}

	public void setBaseRate(BigDecimal baseRate) {
		this.baseRate = baseRate;
	}

	public String getBaseRateCurrency() {
		return baseRateCurrency;
	}

	public void setBaseRateCurrency(String baseRateCurrency) {
		this.baseRateCurrency = baseRateCurrency;
	}

	public CurrencyDetailsResponse() {
		super();
	}

	public CurrencyDetailsResponse(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

}
