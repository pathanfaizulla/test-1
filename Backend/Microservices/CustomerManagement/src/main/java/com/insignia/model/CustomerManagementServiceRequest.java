package com.insignia.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class CustomerManagementServiceRequest {

	private Long customerSequenceNumber;

	private CustomerBasicDetailsRequest customerBasicDetailsRequest;

	private List<AddressRequest> addressRequestList =null;

	private List<RolesAndPermissionsRequest> rolesAndPermissionsRequestList=null;

	private List<CustomerPersonalDetailsRequest> customerPersonalDetailsRequestList =null;

	private List<CustomerStoreDetailsRequest> customerStoreDetailsRequestList = null;

}
