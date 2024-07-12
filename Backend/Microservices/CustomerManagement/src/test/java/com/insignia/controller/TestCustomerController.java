package com.insignia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.entity.AddressDetails;
import com.insignia.entity.CustomerBasicDetailsEntity;
import com.insignia.entity.CustomerPersonalDetails;
import com.insignia.model.AddressRequest;
import com.insignia.model.AddressResponse;
import com.insignia.model.CustomerBasicDetailsRequest;
import com.insignia.model.CustomerManagementServiceRequest;
import com.insignia.model.CustomerManagementServiceResponse;
import com.insignia.model.CustomerPersonalDetailsRequest;
import com.insignia.model.CustomerPersonalDetailsResponse;
import com.insignia.model.CustomerStoreDetailsRequest;
import com.insignia.model.CustomerStoreDetailsResponse;
import com.insignia.model.RolesAndPermissionsRequest;
import com.insignia.model.RolesAndPermissionsResponse;
import com.insignia.serviceInterface.CustomerServiceInterface;

@ExtendWith(MockitoExtension.class)
public class TestCustomerController {

	@InjectMocks
	private CustomerController customerController;

	@Mock
	private CustomerServiceInterface customerService;

	@Mock
	private TokenExpiredException tokenExpire;

	List<CustomerManagementServiceResponse> customerManagementServiceResponseList = new ArrayList<>();

	CustomerManagementServiceRequest customerManagementServiceRequest = new CustomerManagementServiceRequest();
	CustomerManagementServiceResponse customerManagementServiceResponse = new CustomerManagementServiceResponse();

	AddressRequest addressRequest = new AddressRequest();
	AddressResponse addressRes = new AddressResponse();
	AddressDetails addressDetails = new AddressDetails();
	CustomerBasicDetailsRequest customerBasicDetailsRequest = new CustomerBasicDetailsRequest();
	CustomerBasicDetailsEntity customerBasicDetailsEntity = new CustomerBasicDetailsEntity();
	CustomerPersonalDetailsRequest customerPersonalDetailsRequest = new CustomerPersonalDetailsRequest();
	CustomerPersonalDetails customerPersonalDetails = new CustomerPersonalDetails();
	CustomerPersonalDetailsResponse customerPersonalDetailsResponse = new CustomerPersonalDetailsResponse();
	RolesAndPermissionsRequest rolesAndPermissionsRequest = new RolesAndPermissionsRequest();
	RolesAndPermissionsResponse rolesAndPermissionsResponse = new RolesAndPermissionsResponse();

	CustomerStoreDetailsRequest customerStoreDetailsRequest = new CustomerStoreDetailsRequest();
	CustomerStoreDetailsResponse customerStoreDetailsResponse = new CustomerStoreDetailsResponse();

	public void dataInitilization() {
		customerManagementServiceRequest.setCustomerSequenceNumber(6L);

		customerBasicDetailsRequest.setApplicationId("112");
		customerBasicDetailsRequest.setTenantId("1124");
		customerBasicDetailsRequest.setUserId("2545");
		customerBasicDetailsRequest.setPassword("5485");
		customerBasicDetailsRequest.setCustomerSequenceNumber(8L);
		customerBasicDetailsRequest.setEmailId("lakshminagu54@gmail.com");
		customerBasicDetailsRequest.setUserName("Lakshmi");
		customerBasicDetailsRequest.setExpirationDuration(5);
		customerBasicDetailsRequest.setCustomerId("100920");

		customerManagementServiceRequest.setCustomerBasicDetailsRequest(customerBasicDetailsRequest);

		List<RolesAndPermissionsRequest> rolesAndPermissionsList = new ArrayList<>();

		rolesAndPermissionsRequest.setRoleId(5);
		rolesAndPermissionsRequest.setCustomerSequenceNumber(8L);
		rolesAndPermissionsRequest.setPermission1(null);
		rolesAndPermissionsRequest.setPermission2(null);
		rolesAndPermissionsRequest.setPermission3(null);
		rolesAndPermissionsRequest.setPermission4(null);
		rolesAndPermissionsRequest.setPermissionChangeDate(null);
		rolesAndPermissionsRequest.setRoleApprovedDate(null);
		rolesAndPermissionsRequest.setRoleName("admin");
		rolesAndPermissionsRequest.setRoleRevokedDate(null);
		rolesAndPermissionsRequest.setUpdatedPermissions("yes");
		rolesAndPermissionsRequest.setExpirationDuration(5);

		rolesAndPermissionsList.add(rolesAndPermissionsRequest);

		customerManagementServiceRequest.setRolesAndPermissionsRequestList(rolesAndPermissionsList);

		List<CustomerPersonalDetailsRequest> customerPersonalDetailsRequestList = new ArrayList<>();

		customerPersonalDetailsRequest.setCustomerSequenceNumber(8L);
		customerPersonalDetailsRequest.setSequenceNumber(5);
		customerPersonalDetailsRequest.setFirstName("lakshmi");
		customerPersonalDetailsRequest.setLastName("Pragallapati");
		customerPersonalDetailsRequest.setMiddleName("Nagu");
		customerPersonalDetailsRequest.setAge("29");
		customerPersonalDetailsRequest.setGender("female");
		customerPersonalDetailsRequest.setCustomerMobileNumber("1234567891");
		customerPersonalDetailsRequest.setAlternativeMobileNumber("765897653");
		customerPersonalDetailsRequest.setCustomerEmailId("lakshmisidarth4@gmail.com");
		customerPersonalDetailsRequest.setAlternativeEmailId("sidharthlakshmi4@gmail.com");
		customerPersonalDetailsRequest.setCustomerLandlineNumber("8765987");
		customerPersonalDetailsRequest.setExpirationDuration(5);

		customerPersonalDetailsRequestList.add(customerPersonalDetailsRequest);

		customerManagementServiceRequest.setCustomerPersonalDetailsRequestList(customerPersonalDetailsRequestList);

		List<AddressRequest> addressRequestList = new ArrayList<>();

		addressRequest.setCustomerSequenceNumber(8L);
		addressRequest.setAddressLine1("VinayakaTemple");
		addressRequest.setAddressLine2("CinemaRoad");
		addressRequest.setLandmark("Opp:ApolloHospital");
		addressRequest.setCity("Kakinada");
		addressRequest.setState("AndhraPradesh");
		addressRequest.setCountry("India");
		addressRequest.setEmailId("lakshmisidharth678@gmail.com");
		addressRequest.setZipCode("8765");
		addressRequest.setMobileNumber("9875689378");
		addressRequest.setLandlineNumber("98765895");
		addressRequest.setisBillingAddress(false);
		addressRequest.setIsDefaultAddress(false);
		addressRequest.setExpirationDuration(5);

		addressRequestList.add(addressRequest);

		customerManagementServiceRequest.setAddressRequestList(addressRequestList);

		List<RolesAndPermissionsResponse> rolesAndPermissionsResponseList = new ArrayList<>();

		rolesAndPermissionsResponse.setPermission1(null);
		rolesAndPermissionsResponse.setPermission2(null);
		rolesAndPermissionsResponse.setPermission3(null);
		rolesAndPermissionsResponse.setPermission4(null);
		rolesAndPermissionsResponse.setPermissionChangeDate(null);
		rolesAndPermissionsResponse.setRoleApprovedDate(null);
		rolesAndPermissionsResponse.setRoleName("admin");
		rolesAndPermissionsResponse.setRoleRevokedDate(null);
		rolesAndPermissionsResponse.setUpdatedPermissions("yes");

		rolesAndPermissionsResponseList.add(rolesAndPermissionsResponse);

		customerManagementServiceResponse.setRolesAndPermissionsResponseList(rolesAndPermissionsResponseList);

		List<AddressResponse> addressResponseList = new ArrayList<>();

		addressRes.setAddressLine1("VinayakaTemple");
		addressRes.setAddressLine2("CinemaRoad");
		addressRes.setLandmark("Opp:ApolloHospital");
		addressRes.setCity("Kakinada");
		addressRes.setState("AndhraPradesh");
		addressRes.setCountry("India");
		addressRes.setEmailId("lakshmisidharth678@gmail.com");
		addressRes.setZipCode("8765");
		addressRes.setMobileNumber("9875689378");
		addressRes.setLandlineNumber("98765895");
		addressRes.setBillingAddress(false);
		addressRes.setDefaultAddress(false);

		addressResponseList.add(addressRes);

		customerManagementServiceResponse.setAddressResponseList(addressResponseList);

		List<CustomerPersonalDetailsResponse> customerPersonalDetailsResponseList = new ArrayList<>();

		customerPersonalDetailsResponse.setFirstName("lakshmi");
		customerPersonalDetailsResponse.setLastName("Pragallapati");
		customerPersonalDetailsResponse.setMiddleName("Nagu");
		customerPersonalDetailsResponse.setAge("29");
		customerPersonalDetailsResponse.setGender("female");
		customerPersonalDetailsResponse.setCustomerMobileNumber("1234567891");
		customerPersonalDetailsResponse.setAlternativeMobileNumber("765897653");
		customerPersonalDetailsResponse.setCustomerEmailId("lakshmisidarth4@gmail.com");
		customerPersonalDetailsResponse.setAlternativeEmailId("sidharthlakshmi4@gmail.com");
		customerPersonalDetailsResponse.setCustomerLandlineNumber("8765987");

		List<CustomerStoreDetailsRequest> customerStoreDetailsRequestList = new ArrayList<>();

		customerStoreDetailsRequest.setStoreSequenceNumber(5);
		customerStoreDetailsRequest.setCustomerSequenceNumber(5L);
		customerStoreDetailsRequest.setStoreManager("XYZ");
		customerStoreDetailsRequest.setMarkupFactor("xyz");
		customerStoreDetailsRequest.setHearAboutUs("good");
		customerStoreDetailsRequest.setMarkets("SuperMarket");
		customerStoreDetailsRequest.setStoreName("Insignia");
		customerStoreDetailsRequest.setStoreContact("1234");
		customerStoreDetailsRequest.setStoreAddress("USA");
		customerStoreDetailsRequest.setStoreCountry("india");
		customerStoreDetailsRequest.setStoreState("West");
		customerStoreDetailsRequestList.add(customerStoreDetailsRequest);

		customerManagementServiceRequest.setCustomerStoreDetailsRequestList(customerStoreDetailsRequestList);

		List<CustomerStoreDetailsResponse> customerStoreDetailsResponseList = new ArrayList<>();

		customerStoreDetailsResponse.setStoreSequenceNumber(5);
		customerStoreDetailsResponse.setStoreManager("XYZ");
		customerStoreDetailsResponse.setMarkupFactor("xyz");
		customerStoreDetailsResponse.setHearAboutUs("good");
		customerStoreDetailsResponse.setMarkets("SuperMarket");
		customerStoreDetailsResponse.setStoreName("Insignia");
		customerStoreDetailsResponse.setStoreContact("1234");
		customerStoreDetailsResponse.setStoreAddress("USA");
		customerStoreDetailsResponse.setStoreCountry("india");
		customerStoreDetailsResponse.setStoreState("West");
		customerStoreDetailsResponseList.add(customerStoreDetailsResponse);

		customerManagementServiceResponse.setCustomerStoreDetailsResponseList(customerStoreDetailsResponseList);

		customerPersonalDetailsResponseList.add(customerPersonalDetailsResponse);
		customerManagementServiceResponse.setCustomerPersonalDetailsResponseList(customerPersonalDetailsResponseList);
		customerManagementServiceResponseList.add(customerManagementServiceResponse);
	}

	@Test
	public void testSaveAndUpdateCustomerDetailsValidations() throws InvalidInputParametersException {
		dataInitilization();
		customerBasicDetailsRequest.setApplicationId(null);
		customerBasicDetailsRequest.setTenantId(null);
		customerBasicDetailsRequest.setUserId(null);
		customerBasicDetailsRequest.setPassword(null);

		addressRequest.setAddressLine1(null);
		addressRequest.setCity(null);
		addressRequest.setState(null);
		addressRequest.setCountry(null);
		addressRequest.setZipCode(null);

		ResponseEntity<?> saveAllCustomerDetails = customerController
				.saveAllCustomerDetails(customerManagementServiceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, saveAllCustomerDetails.getStatusCode());

		ResponseEntity<?> updateCustomerDetails = customerController
				.updateAllCustomerDetails(customerManagementServiceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, updateCustomerDetails.getStatusCode());
	}

	@Test
	public void testForSaveAllCustomerDetails() throws InvalidInputParametersException, Exception {
		dataInitilization();

		when(customerService.saveAllCustomerDetails(customerManagementServiceRequest))
				.thenReturn(customerManagementServiceResponse);
		ResponseEntity<?> response = customerController.saveAllCustomerDetails(customerManagementServiceRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	public void testForUpdateAllCustomerDetails() throws InvalidInputParametersException, Exception {
		dataInitilization();
		when(customerService.updateAllCustomerDetails(customerManagementServiceRequest))
				.thenReturn(customerManagementServiceResponse);

		ResponseEntity<?> updateAllCustomerDetails = customerController
				.updateAllCustomerDetails(customerManagementServiceRequest);
		assertEquals(HttpStatus.OK, updateAllCustomerDetails.getStatusCode());
	}

	@Test
	public void testForDeleteAllCustomerDetails() throws InvalidInputParametersException, Exception {
		dataInitilization();

		doNothing().when(customerService).deleteCustomerAssociatedDetails(customerManagementServiceRequest);

		ResponseEntity<?> deleteCustomerAssociatedDetails = customerController
				.deleteCustomerAssociatedDetails(customerManagementServiceRequest);
		verify(customerService, times(1)).deleteCustomerAssociatedDetails(customerManagementServiceRequest);
		assertEquals("Record Successfully Deleted", deleteCustomerAssociatedDetails.getBody());

	}

	@Test
	public void testGetCustomerDetails() throws InvalidInputParametersException, Exception {
		dataInitilization();
		CustomerManagementServiceResponse response = new CustomerManagementServiceResponse();

		Optional<CustomerManagementServiceResponse> expectedResult = Optional.of(response);

		when(customerService.getCustomerDetails(customerManagementServiceRequest)).thenReturn(expectedResult);
		ResponseEntity<?> getAllCustomersWithDetails = customerController
				.getCustomerDetails(customerManagementServiceRequest);
		assertEquals(HttpStatus.OK, getAllCustomersWithDetails.getStatusCode());

	}

	@Test
	public void testGetAllCustomerDetails() throws InvalidInputParametersException, Exception {

		dataInitilization();
		when(customerService.getAllCustomerData(customerManagementServiceRequest))
				.thenReturn(customerManagementServiceResponseList);
		ResponseEntity<?> getAllCustomersWithDetails = customerController
				.getAllCustomerData(customerManagementServiceRequest);
		assertEquals(HttpStatus.OK, getAllCustomersWithDetails.getStatusCode());

	}

	@Test
	public void testGetCustomerAndStoreInformation() throws InvalidInputParametersException, Exception {

		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 5;

		when(customerService.getCustomerAndStoreInformation(customerSequenceNumber, expirationDuration))
				.thenReturn(customerManagementServiceResponse);
		ResponseEntity<?> getCustomerAndStoreInformation = customerController
				.getCustomerAndStoreInformation(customerSequenceNumber, expirationDuration);
		assertEquals(HttpStatus.OK, getCustomerAndStoreInformation.getStatusCode());

	}

	@Test
	public void testForTokenException() throws InvalidInputParametersException, TokenExpiredException,
			InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, ParseException {
		dataInitilization();
		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 5;

		// Update CustomerDetails
		when(customerService.updateAllCustomerDetails(customerManagementServiceRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> updateAllCustomerDetails = customerController
				.updateAllCustomerDetails(customerManagementServiceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, updateAllCustomerDetails.getStatusCode());

		// Delete CustomerDetails
		doThrow(new TokenExpiredException("")).when(customerService)
				.deleteCustomerAssociatedDetails(customerManagementServiceRequest);

		ResponseEntity<?> responseEntity = customerController
				.deleteCustomerAssociatedDetails(customerManagementServiceRequest);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		// Get CustomeDetails
		when(customerService.getCustomerDetails(customerManagementServiceRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getAllCustomersWithDetails = customerController
				.getCustomerDetails(customerManagementServiceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, getAllCustomersWithDetails.getStatusCode());

		// Get All CustomeDetails
		when(customerService.getAllCustomerData(customerManagementServiceRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getAllCustomerData = customerController.getAllCustomerData(customerManagementServiceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, getAllCustomerData.getStatusCode());

		// Get Custome&StoreDetails
		when(customerService.getCustomerAndStoreInformation(customerSequenceNumber, expirationDuration))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getCustomerAndStoreInformation = customerController
				.getCustomerAndStoreInformation(customerSequenceNumber, expirationDuration);
		assertEquals(HttpStatus.BAD_REQUEST, getCustomerAndStoreInformation.getStatusCode());
	}

	@Test
	public void testForException() throws InvalidInputParametersException, TokenExpiredException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException,
			ParseException {
		dataInitilization();
		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 5;

		// Save CustomerDetails
		when(customerService.saveAllCustomerDetails(customerManagementServiceRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> saveAllCustomerDetails = customerController
				.saveAllCustomerDetails(customerManagementServiceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, saveAllCustomerDetails.getStatusCode());

		// Update CustomerDetails
		when(customerService.updateAllCustomerDetails(customerManagementServiceRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> updateAllCustomerDetails = customerController
				.updateAllCustomerDetails(customerManagementServiceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, updateAllCustomerDetails.getStatusCode());

		// Delete CustomerDetails
		doThrow(new NullPointerException("")).when(customerService)
				.deleteCustomerAssociatedDetails(customerManagementServiceRequest);;

		ResponseEntity<?> responseEntity = customerController.deleteCustomerAssociatedDetails(customerManagementServiceRequest);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		// Get CustomeDetails
		when(customerService.getCustomerDetails(customerManagementServiceRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> getAllCustomersWithDetails = customerController.getCustomerDetails(customerManagementServiceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, getAllCustomersWithDetails.getStatusCode());

		// Get All CustomeDetails
		when(customerService.getAllCustomerData(customerManagementServiceRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> getAllCustomerData = customerController.getAllCustomerData(customerManagementServiceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, getAllCustomerData.getStatusCode());

		// Get CustomerAndStoreDetails
		when(customerService.getCustomerAndStoreInformation(customerSequenceNumber, expirationDuration))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> getCustomerAndStoreInformation = customerController
				.getCustomerAndStoreInformation(customerSequenceNumber, expirationDuration);
		assertEquals(HttpStatus.BAD_REQUEST, getCustomerAndStoreInformation.getStatusCode());
	}
}
