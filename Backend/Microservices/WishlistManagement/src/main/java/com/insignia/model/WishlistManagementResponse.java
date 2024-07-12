package com.insignia.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WishlistManagementResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long customerSequenceNumber;

	private String errorCode;
	private String errorMessage;
	private Integer successCode;
	private String successMessage;

	private List<WishlistDetails> wishlistDetailsList = null;

	public Long getCustomerSequenceNumber() {
		return customerSequenceNumber;
	}

	public void setCustomerSequenceNumber(Long customerSequenceNumber) {
		this.customerSequenceNumber = customerSequenceNumber;
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

	public Integer getSuccessCode() {
		return successCode;
	}

	public void setSuccessCode(Integer successCode) {
		this.successCode = successCode;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public List<WishlistDetails> getWishlistDetailsList() {
		return wishlistDetailsList;
	}

	public void setWishlistDetailsList(List<WishlistDetails> wishlistDetailsList) {
		this.wishlistDetailsList = wishlistDetailsList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "WishlistManagementResponse [customerSequenceNumber=" + customerSequenceNumber + ", errorCode="
				+ errorCode + ", errorMessage=" + errorMessage + ", successCode=" + successCode + ", successMessage="
				+ successMessage + ", wishlistDetailsList=" + wishlistDetailsList + "]";
	}

	public WishlistManagementResponse(Integer successCode, String successMessage) {
		super();
		this.successCode = successCode;
		this.successMessage = successMessage;
	}

	public WishlistManagementResponse(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public WishlistManagementResponse() {
		super();
	}

}
