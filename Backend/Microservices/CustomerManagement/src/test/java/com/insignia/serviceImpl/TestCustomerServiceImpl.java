package com.insignia.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.CustomerDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.AddressDetails;
import com.insignia.entity.CustomerBasicDetailsEntity;
import com.insignia.entity.CustomerPersonalDetails;
import com.insignia.entity.CustomerStoreDetails;
import com.insignia.entity.RolesAndPermissions;
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
import com.insignia.repo.CustomerBasicDetailsRepository;
import com.insignia.security.EncryptionUtility;

import jakarta.persistence.EntityManager;

public class TestCustomerServiceImpl {

	@BeforeEach
	void setupBefore() {
		MockitoAnnotations.openMocks(this);
	}

	@InjectMocks
	private CustomerServiceImpl customerServiceImpl;

	@Mock
	private CustomerDaoInterface customerRepo;

	@Mock
	private TokenDaoInterface tokenRepo;

	@Mock
	private CustomerBasicDetailsRepository customerBasicDetailsRepo;

	@Mock
	private EncryptionUtility encryptionUtility;

	@Mock
	private EntityManager entityManager;

	@InjectMocks
	private CustomerManagementServiceResponse customerManagementService;

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
	CustomerBasicDetailsEntity initializeCustmerDetailsEntity = new CustomerBasicDetailsEntity();

	CustomerStoreDetails customerStoreDetails = new CustomerStoreDetails();
	CustomerStoreDetailsRequest customerStoreDetailsRequest = new CustomerStoreDetailsRequest();
	CustomerStoreDetailsResponse customerStoreDetailsResponse = new CustomerStoreDetailsResponse();
	List<CustomerStoreDetails> customerStoreDetailsList = new ArrayList<>();
	List<CustomerBasicDetailsEntity> customerBasicDetailsEntityList = new ArrayList<>();

	public void dataInitilization() {

		customerBasicDetailsRequest.setCustomerSequenceNumber(8L);
		customerBasicDetailsRequest.setExpirationDuration(15);

		customerBasicDetailsRequest.setApplicationId("hinges-design");
		customerBasicDetailsRequest.setTenantId("LU008");
		customerBasicDetailsRequest.setUserId("2545");
		customerBasicDetailsRequest.setPassword("5485");
		customerBasicDetailsRequest.setCustomerSequenceNumber(8L);
		customerBasicDetailsRequest.setEmailId("lakshminagu54@gmail.com");
		customerBasicDetailsRequest.setUserName("Lakshmi");
		customerBasicDetailsRequest.setExpirationDuration(5);

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
		addressRequest.setSequenceNumber(8);
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
		customerStoreDetailsRequest.setStoreCity("Kakinada");
		customerStoreDetailsRequest.setBusinessType("Software");
		customerStoreDetailsRequest.setResaleLicense("123HI");
		customerStoreDetailsRequest.setStoreZipCode("1233");
		customerStoreDetailsRequest.setTelephone("123456");
		customerStoreDetailsRequest.setWebsite("www.google.com");
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
		customerStoreDetailsResponse.setStoreCity("Kakinada");
		customerStoreDetailsResponse.setBusinessType("Software");
		customerStoreDetailsResponse.setResaleLicense("123HI");
		customerStoreDetailsResponseList.add(customerStoreDetailsResponse);

		customerManagementServiceResponse.setCustomerStoreDetailsResponseList(customerStoreDetailsResponseList);

		customerStoreDetails.setStoreSequenceNumber(5);
		customerStoreDetails.setStoreManager("XYZ");
		customerStoreDetails.setMarkupFactor("xyz");
		customerStoreDetails.setHearAboutUs("good");
		customerStoreDetails.setMarkets("SuperMarket");
		customerStoreDetails.setStoreName("Insignia");
		customerStoreDetails.setStoreContact("1234");
		customerStoreDetails.setStoreAddress("USA");
		customerStoreDetails.setStoreCountry("india");
		customerStoreDetails.setStoreState("West");
		customerStoreDetails.setStoreCity("Kakinada");
		customerStoreDetails.setBusinessType("Software");
		customerStoreDetails.setResaleLicense("123HI");

		customerPersonalDetailsResponseList.add(customerPersonalDetailsResponse);
		customerManagementServiceResponse.setCustomerPersonalDetailsResponseList(customerPersonalDetailsResponseList);

	}

	public CustomerBasicDetailsEntity getDetailsEntity() {

		customerBasicDetailsEntity.setCustomerSequenceNumber(8L);

		customerBasicDetailsEntity.setApplicationId("112");
		customerBasicDetailsEntity.setTenantId("1124");
		customerBasicDetailsEntity.setUserId("2545");
		customerBasicDetailsEntity.setCustomerPassword("5485");
		customerBasicDetailsEntity.setCustomerSequenceNumber(8L);
		customerBasicDetailsEntity.setUserName("Lakshmi");

		AddressDetails addressDetails = new AddressDetails();

		addressDetails.setSequenceNumber(8);
		addressDetails.setCustomerSequenceNumber(8L);
		addressDetails.setAddressLine1("VinayakaTemple");
		addressDetails.setAddressLine2("CinemaRoad");
		addressDetails.setLandmark("Opp:ApolloHospital");
		addressDetails.setCity("Kakinada");
		addressDetails.setState("AndhraPradesh");
		addressDetails.setCountry("India");
		addressDetails.setEmailId("lakshmisidharth678@gmail.com");
		addressDetails.setZipCode("8765");
		addressDetails.setMobileNumber("9875689378");
		addressDetails.setLandlineNumber("98765895");
		addressDetails.setBillingAddress(false);
		addressDetails.setDefaultAddress(false);

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

		RolesAndPermissions rolesAndPermissions = new RolesAndPermissions();

		rolesAndPermissions.setRoleId(5);
		rolesAndPermissions.setPermission1(null);
		rolesAndPermissions.setPermission2(null);
		rolesAndPermissions.setPermission3(null);
		rolesAndPermissions.setPermission4(null);
		rolesAndPermissions.setPermissionChangeDate(null);
		rolesAndPermissions.setRoleApprovedDate(null);
		rolesAndPermissions.setRoleName("admin");
		rolesAndPermissions.setRoleRevokedDate(null);
		rolesAndPermissions.setUpdatedPermissions("yes");

		CustomerPersonalDetails customerPersonalDetails = new CustomerPersonalDetails();

		customerPersonalDetails.setSequenceNumber(8);
		customerPersonalDetails.setSequenceNumber(5);
		customerPersonalDetails.setFirstName("lakshmi");
		customerPersonalDetails.setLastName("Pragallapati");
		customerPersonalDetails.setMiddleName("Nagu");
		customerPersonalDetails.setAge("29");
		customerPersonalDetails.setGender("female");
		customerPersonalDetails.setCustomerMobileNumber("1234567891");
		customerPersonalDetails.setAlternativeMobileNumber("765897653");
		customerPersonalDetails.setCustomerEmailId("lakshmisidarth4@gmail.com");
		customerPersonalDetails.setAlternativeEmailId("sidharthlakshmi4@gmail.com");
		customerPersonalDetails.setCustomerLandlineNumber("8765987");

		CustomerStoreDetails customerStoreDetails = new CustomerStoreDetails();
		customerStoreDetails.setStoreSequenceNumber(5);
		customerStoreDetails.setStoreManager("XYZ");
		customerStoreDetails.setMarkupFactor("xyz");
		customerStoreDetails.setHearAboutUs("good");
		customerStoreDetails.setMarkets("SuperMarket");
		customerStoreDetails.setStoreName("Insignia");
		customerStoreDetails.setStoreContact("1234");
		customerStoreDetails.setStoreAddress("USA");
		customerStoreDetails.setStoreCountry("india");
		customerStoreDetails.setStoreState("West");

		customerBasicDetailsEntity.setCustomerStoreDetailsEntity(customerStoreDetailsList);

		initializeCustmerDetailsEntity.setAddressDetails(Arrays.asList(addressDetails));
		initializeCustmerDetailsEntity.setRolesAndPermissions(Arrays.asList(rolesAndPermissions));
		initializeCustmerDetailsEntity.setCustomerPersonalDetails(Arrays.asList(customerPersonalDetails));
		initializeCustmerDetailsEntity.setCustomerSequenceNumber(8l);
		initializeCustmerDetailsEntity.setCustomerStoreDetailsEntity(customerStoreDetailsList);

		return initializeCustmerDetailsEntity;
	}

	@Test
	public void testUpdateAllCustomerDetails() throws Exception {

		dataInitilization();
		getDetailsEntity();

		CustomerBasicDetailsEntity basicDetailsEntityResponse = customerBasicDetailsEntity;
		basicDetailsEntityResponse.setApplicationId("123l");

		when(customerRepo.getCustomerDetails(any(), any(), any()))
				.thenReturn(Optional.ofNullable(initializeCustmerDetailsEntity));
		customerServiceImpl.updateAllCustomerDetails(customerManagementServiceRequest);
		assertNotNull(customerServiceImpl.updateAllCustomerDetails(customerManagementServiceRequest));
	}

	@Test
	public void testSaveAllCustomerDetails() throws Exception {

		dataInitilization();

		CustomerBasicDetailsEntity cbde = new CustomerBasicDetailsEntity();

		when(customerRepo.saveAllCustomerDetails(any())).thenReturn(cbde);

		assertNotNull(customerServiceImpl.saveAllCustomerDetails(customerManagementServiceRequest));
	}

	@Test
	public void testGetCustomerDetails() throws Exception {
		dataInitilization();
		getDetailsEntity();

		Optional<CustomerBasicDetailsEntity> response3 = Optional.ofNullable(initializeCustmerDetailsEntity);

		when(customerRepo.getCustomerDetails(any(), any(), any())).thenReturn(response3);
		Optional<CustomerManagementServiceResponse> responseOptional = customerServiceImpl
				.getCustomerDetails(customerManagementServiceRequest);

		assertTrue(responseOptional.isPresent());

		CustomerManagementServiceResponse response = responseOptional.get();
		assertNotNull(response);
	}

	@Test
	public void testGetAllCustomerData() throws Exception {
		dataInitilization();
		getDetailsEntity();

		when(customerRepo.getAllCustomerData(any(), any())).thenReturn(customerBasicDetailsEntityList);
		List<CustomerManagementServiceResponse> allCustomerData = customerServiceImpl
				.getAllCustomerData(customerManagementServiceRequest);

		assertNotNull(allCustomerData);
	}

	@Test
	public void testDeleteCustomerAssociatedDetails_Success()
			throws TokenExpiredException, InvalidInputParametersException {

		CustomerManagementServiceRequest customerManagementDetails = new CustomerManagementServiceRequest();
		CustomerBasicDetailsRequest customerBasicDetailsRequest = new CustomerBasicDetailsRequest();
		customerBasicDetailsRequest.setCustomerSequenceNumber(123L);
		customerBasicDetailsRequest.setApplicationId("appId");
		customerBasicDetailsRequest.setTenantId("tenantId");
		customerManagementDetails.setCustomerBasicDetailsRequest(customerBasicDetailsRequest);

		CustomerBasicDetailsEntity customerBasicDetailsEntity = new CustomerBasicDetailsEntity();
		when(customerRepo.getCustomerDetails(any(), any(), any())).thenReturn(Optional.of(customerBasicDetailsEntity));

		customerServiceImpl.deleteCustomerAssociatedDetails(customerManagementDetails);
		verify(customerRepo).deleteCustomerAssociatedDetails(123L, "appId", "tenantId");
	}

	@Test
	public void testCreateResponse() throws Exception {

		getDetailsEntity();
		customerStoreDetailsList.add(customerStoreDetails);

		Method method = CustomerServiceImpl.class.getDeclaredMethod("createResponse", CustomerBasicDetailsEntity.class);
		method.setAccessible(true);
		Object customerServiceResponceObject = method.invoke(customerServiceImpl,
				new Object[] { initializeCustmerDetailsEntity });

		assertNotNull(customerServiceResponceObject);
	}

	@Test
	void testGetCustomerAndStoreInformation() throws TokenExpiredException, ParseException {

		Long customerSequenceNumber = 5L;
		Integer expirationDuration = 15;
		List<Object[]> testData = new ArrayList<>();

		Object[] customerStoreDetails = new Object[7];

		customerStoreDetails[0] = 5L;
		customerStoreDetails[1] = "Full Name";
		customerStoreDetails[2] = "2022-01-01";
		customerStoreDetails[3] = "Store Name";
		customerStoreDetails[4] = "Store Contact";
		customerStoreDetails[5] = "Store Address";
		customerStoreDetails[6] = "Business Type";

		testData.add(customerStoreDetails);

		when(customerRepo.getCustomerAndStoreInformation()).thenReturn(testData);

		CustomerManagementServiceResponse getCustomerAndStoreInformation = customerServiceImpl
				.getCustomerAndStoreInformation(customerSequenceNumber, expirationDuration);

		assertNotNull(getCustomerAndStoreInformation);

		verify(customerRepo, times(1)).getCustomerAndStoreInformation();
	}

}
