package com.insignia.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CustomerManagementServiceResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private CustomerBasicDetailsResponse customerBasicDetailsResponse;

	private List<AddressResponse> addressResponseList = null;

	private List<RolesAndPermissionsResponse> rolesAndPermissionsResponseList = null;

	private List<CustomerPersonalDetailsResponse> customerPersonalDetailsResponseList = null;

	private List<CustomerStoreDetailsResponse> customerStoreDetailsResponseList = null;

	private List<CustomerAndStoreInformationResponse> customerAndStoreInformationResponseList = null;

	private String successMessage;

	private String errorCode;

	private String errorMessage;

	public CustomerBasicDetailsResponse getCustomerBasicDetailsResponse() {
		return customerBasicDetailsResponse;
	}

	public void setCustomerBasicDetailsResponse(CustomerBasicDetailsResponse customerBasicDetailsResponse) {
		this.customerBasicDetailsResponse = customerBasicDetailsResponse;
	}

	public List<AddressResponse> getAddressResponseList() {
		return addressResponseList;
	}

	public void setAddressResponseList(List<AddressResponse> addressResponseList) {
		this.addressResponseList = addressResponseList;
	}

	public List<RolesAndPermissionsResponse> getRolesAndPermissionsResponseList() {
		return rolesAndPermissionsResponseList;
	}

	public void setRolesAndPermissionsResponseList(List<RolesAndPermissionsResponse> rolesAndPermissionsResponseList) {
		this.rolesAndPermissionsResponseList = rolesAndPermissionsResponseList;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
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

	public List<CustomerPersonalDetailsResponse> getCustomerPersonalDetailsResponseList() {
		return customerPersonalDetailsResponseList;
	}

	public void setCustomerPersonalDetailsResponseList(
			List<CustomerPersonalDetailsResponse> customerPersonalDetailsResponseList) {
		this.customerPersonalDetailsResponseList = customerPersonalDetailsResponseList;
	}

	public List<CustomerStoreDetailsResponse> getCustomerStoreDetailsResponseList() {
		return customerStoreDetailsResponseList;
	}

	public void setCustomerStoreDetailsResponseList(
			List<CustomerStoreDetailsResponse> customerStoreDetailsResponseList) {
		this.customerStoreDetailsResponseList = customerStoreDetailsResponseList;
	}

	public List<CustomerAndStoreInformationResponse> getCustomerAndStoreInformationResponseList() {
		return customerAndStoreInformationResponseList;
	}

	public void setCustomerAndStoreInformationResponseList(
			List<CustomerAndStoreInformationResponse> customerAndStoreInformationResponseList) {
		this.customerAndStoreInformationResponseList = customerAndStoreInformationResponseList;
	}

	public CustomerManagementServiceResponse(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "CustomerManagementServiceResponse [customerBasicDetailsResponse=" + customerBasicDetailsResponse
				+ ", addressResponseList=" + addressResponseList + ", rolesAndPermissionsResponseList="
				+ rolesAndPermissionsResponseList + ", customerPersonalDetailsResponseList="
				+ customerPersonalDetailsResponseList + ", customerStoreDetailsResponseList="
				+ customerStoreDetailsResponseList + ", customerAndStoreInformationResponseList="
				+ customerAndStoreInformationResponseList + ", successMessage=" + successMessage + ", errorCode="
				+ errorCode + ", errorMessage=" + errorMessage + "]";
	}

	public CustomerManagementServiceResponse() {

	}

}
