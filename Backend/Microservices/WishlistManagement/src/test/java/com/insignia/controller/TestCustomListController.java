package com.insignia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.CustomListDetails;
import com.insignia.model.CustomListDetailsRequest;
import com.insignia.model.CustomListManagementRequest;
import com.insignia.model.CustomListManagementResponse;
import com.insignia.model.ProductDetailsRequest;
import com.insignia.model.ProductDetailsResponse;
import com.insignia.serviceInterface.CustomListServiceInterface;

@ExtendWith(MockitoExtension.class)
public class TestCustomListController {

	@InjectMocks
	private CustomListController customListController;

	@Mock
	private CustomListServiceInterface customListServiceInterface;

	CustomListManagementRequest customListManagementRequest = new CustomListManagementRequest();
	CustomListDetailsRequest customListDetailsRequest = new CustomListDetailsRequest();
	ProductDetailsRequest productDetailsRequest = new ProductDetailsRequest();

	CustomListDetails customListDetails = new CustomListDetails();
	CustomListManagementResponse customListManagementResponse = new CustomListManagementResponse();
	ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();

	List<CustomListDetails> customListDetailsResponseList = new ArrayList<>();
	List<ProductDetailsRequest> productDetailsRequestList = new ArrayList<>();
	List<CustomListDetailsRequest> customListDetailsRequestList = new ArrayList<>();

	List<ProductDetailsResponse> productDetailsResponseList = new ArrayList<>();

	public void dataInitilization() {
		customListManagementRequest.setCustomerSequenceNumber(105L);
		customListManagementRequest.setTokenExpirationDuration(15);

		customListDetailsRequest.setSequenceNumber(1L);
		customListDetailsRequest.setCustomListName("List1");

		productDetailsRequest.setProductSequenceNumber(1L);
		productDetailsRequest.setQuantity(60);

		productDetailsRequestList.add(productDetailsRequest);

		customListDetailsRequest.setProductDetailsRequestList(productDetailsRequestList);

		customListDetailsRequestList.add(customListDetailsRequest);

		customListManagementRequest.setCustomListDetailsRequestList(customListDetailsRequestList);

		customListDetails.setCustomListSequenceNumber(105L);
		customListDetails.setCustomListName("List1");

		productDetailsResponse.setProductSequenceNumber(7L);
		productDetailsResponse.setProductAvailableQuantity(60);
		productDetailsResponse.setCustomListQuantity(5);
		productDetailsResponse.setProductId("660817");
		productDetailsResponse.setProductName("mobile");
		productDetailsResponse.setDescription("HI");
		productDetailsResponse.setMeasuringQuantity("3");
		productDetailsResponse.setMeasuringUnit("5");
		productDetailsResponse.setProductImagePath(
				"https://test-bucket-for-document.s3.ap-south-1.amazonaws.com/hinges-design/LU008/PRODUCT/660817/IMAGES/");
		productDetailsResponse.setProductPerUnitActualPrice(BigDecimal.valueOf(635.00));
		productDetailsResponse.setProductPerUnitCurrentPrice(BigDecimal.valueOf(590.00));
		productDetailsResponse.setDefaultImage("660817-PMI.jpg");

		productDetailsResponseList.add(productDetailsResponse);

		customListDetails.setProductDetailsResponse(productDetailsResponseList);

		customListDetailsResponseList.add(customListDetails);

		customListManagementResponse.setCustomerSequenceNumber(105L);
		customListManagementResponse.setCustomListDetails(customListDetailsResponseList);
	}

	@Test
	public void testSaveCustomList() throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		customListManagementRequest.getCustomListDetailsRequestList().clear();

		ResponseEntity<?> saveCustomList1 = customListController.saveCustomList(customListManagementRequest);
		assertEquals(HttpStatus.OK, saveCustomList1.getStatusCode());

		CustomListDetailsRequest customListDetailsRequest2 = new CustomListDetailsRequest();
		customListDetailsRequest2.setSequenceNumber(42L);
		customListDetailsRequest2.setCustomListName("My Collections 1");
		customListDetailsRequest2.setProductDetailsRequestList(null);
		customListManagementRequest.getCustomListDetailsRequestList().add(customListDetailsRequest2);

		ResponseEntity<?> saveCustomList2 = customListController.saveCustomList(customListManagementRequest);
		assertEquals(HttpStatus.OK, saveCustomList2.getStatusCode());

		when(customListServiceInterface.saveCustomList(customListManagementRequest))
				.thenReturn(customListManagementResponse);
		ResponseEntity<?> saveCustomList3 = customListController.saveCustomList(customListManagementRequest);
		assertEquals(HttpStatus.OK, saveCustomList3.getStatusCode());
	}

	@Test
	public void testCreateCustomList() throws TokenExpiredException, InvalidInputParametersException, ParseException {
		dataInitilization();

		Long customerSequenceNumber = 105L;
		Integer expirationDuration = 15;
		CustomListDetailsRequest customListDetailsRequest1 = new CustomListDetailsRequest();
		customListDetailsRequest1.setCustomListName("List1");
		customListDetailsRequest1.setSequenceNumber(1L);
		customListManagementRequest.getCustomListDetailsRequestList().add(customListDetailsRequest1);

		customListManagementRequest.setCustomerSequenceNumber(customerSequenceNumber);
		customListManagementRequest.setTokenExpirationDuration(expirationDuration);

		when(customListServiceInterface.createCustomList(customListManagementRequest))
				.thenReturn(customListManagementResponse);

		ResponseEntity<?> createCustomList = customListController.createCustomList(customListManagementRequest);

		assertEquals(HttpStatus.OK, createCustomList.getStatusCode());
	}

	@Test
	public void testUpdateCustomListName() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		when(customListServiceInterface.updateCustomListName(customListManagementRequest))
				.thenReturn(customListManagementResponse);
		ResponseEntity<?> response = customListController.updateCustomListName(customListManagementRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testGetCustomListForCustomer() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();
		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 5;

		when(customListServiceInterface.getCustomListForCustomer(customerSequenceNumber, expirationDuration))
				.thenReturn(customListManagementResponse);
		ResponseEntity<?> getCustomListForCustomer = customListController
				.getCustomListForCustomer(customerSequenceNumber, expirationDuration);

		assertEquals(HttpStatus.OK, getCustomListForCustomer.getStatusCode());
	}

	@Test
	public void testRemoveCustomListNameForCustomer() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 15;
		List<String> customNameList = Arrays.asList("List1");

		doNothing().when(customListServiceInterface).deleteCustomList(customerSequenceNumber, customNameList,
				expirationDuration);

		CustomListDetailsRequest customListDetailsRequest = new CustomListDetailsRequest();
		customListDetailsRequest.setCustomListName("List1");
		ProductDetailsRequest product1 = new ProductDetailsRequest();
		product1.setProductSequenceNumber(388L);
		product1.setQuantity(1);
		ProductDetailsRequest product2 = new ProductDetailsRequest();
		product2.setProductSequenceNumber(389L);
		product2.setQuantity(4);
		customListDetailsRequest.setProductDetailsRequestList(Arrays.asList(product1, product2));
		customListManagementRequest.setCustomerSequenceNumber(customerSequenceNumber);
		customListManagementRequest.setTokenExpirationDuration(expirationDuration);
		customListManagementRequest.setCustomListDetailsRequestList(Arrays.asList(customListDetailsRequest));

		ResponseEntity<?> deleteCustomList = customListController.deleteCustomList(customListManagementRequest);

		assertEquals(HttpStatus.OK, deleteCustomList.getStatusCode());

	}

	@Test
	public void testDeleteCustomList() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 15;

		doNothing().when(customListServiceInterface).deleteAllCustomListForCustomer(customerSequenceNumber,
				expirationDuration);

		ResponseEntity<?> deleteAllCustomListForCustomer = customListController
				.deleteAllCustomListForCustomer(customerSequenceNumber, expirationDuration);

		assertEquals(HttpStatus.OK, deleteAllCustomListForCustomer.getStatusCode());

	}

	@Test
	public void testForException() throws TokenExpiredException, InvalidInputParametersException, ParseException {
		dataInitilization();

		Long customerSequenceNumber = 105L;
		Integer expirationDuration = 15;
		List<String> customList = Arrays.asList("List1");

		when(customListServiceInterface.createCustomList(customListManagementRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> createEntiry = customListController.createCustomList(customListManagementRequest);
		assertEquals(HttpStatus.BAD_REQUEST, createEntiry.getStatusCode());

		when(customListServiceInterface.saveCustomList(customListManagementRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> saveEntiry = customListController.saveCustomList(customListManagementRequest);
		assertEquals(HttpStatus.BAD_REQUEST, saveEntiry.getStatusCode());

		when(customListServiceInterface.getCustomListForCustomer(customerSequenceNumber, expirationDuration))
				.thenThrow(new NullPointerException(""));

		ResponseEntity<?> getCustomListForCustomer = customListController
				.getCustomListForCustomer(customerSequenceNumber, expirationDuration);
		assertEquals(HttpStatus.BAD_REQUEST, getCustomListForCustomer.getStatusCode());

		doThrow(new NullPointerException("")).when(customListServiceInterface)
				.deleteAllCustomListForCustomer(customerSequenceNumber, expirationDuration);

		ResponseEntity<?> deleteAllCustomListForCustomer = customListController
				.deleteAllCustomListForCustomer(customerSequenceNumber, expirationDuration);
		assertEquals(HttpStatus.BAD_REQUEST, deleteAllCustomListForCustomer.getStatusCode());

		doThrow(new NullPointerException("")).when(customListServiceInterface).deleteCustomList(customerSequenceNumber,
				customList, expirationDuration);

		ResponseEntity<?> deleteCustomList = customListController.deleteCustomList(customListManagementRequest);
		assertEquals(HttpStatus.BAD_REQUEST, deleteCustomList.getStatusCode());

		when(customListServiceInterface.updateCustomListName(customListManagementRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> updateCustomListName = customListController.updateCustomListName(customListManagementRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateCustomListName.getStatusCode());

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(customListServiceInterface)
				.updateCustomListName(customListManagementRequest);

		ResponseEntity<?> updateCustomListName1 = customListController
				.updateCustomListName(customListManagementRequest);
		assertEquals(HttpStatus.BAD_REQUEST, updateCustomListName1.getStatusCode());

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(customListServiceInterface)
				.saveCustomList(customListManagementRequest);
		ResponseEntity<?> saveCustomList1 = customListController.saveCustomList(customListManagementRequest);
		assertEquals(HttpStatus.BAD_REQUEST, saveCustomList1.getStatusCode());

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(customListServiceInterface)
				.createCustomList(customListManagementRequest);
		ResponseEntity<?> createCustomList1 = customListController.createCustomList(customListManagementRequest);
		assertEquals(HttpStatus.BAD_REQUEST, createCustomList1.getStatusCode());
	}

	@Test
	public void testForTokenExpired() throws TokenExpiredException, InvalidInputParametersException, ParseException {
		dataInitilization();

		dataInitilization();
		Long customerSequenceNumber = 105L;
		Integer expirationDuration = 15;
		List<String> customList = Arrays.asList("List1");

		when(customListServiceInterface.createCustomList(customListManagementRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> createEntity = customListController.createCustomList(customListManagementRequest);
		assertEquals(HttpStatus.BAD_REQUEST, createEntity.getStatusCode());

		when(customListServiceInterface.saveCustomList(customListManagementRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> saveEntity = customListController.saveCustomList(customListManagementRequest);
		assertEquals(HttpStatus.BAD_REQUEST, saveEntity.getStatusCode());

		when(customListServiceInterface.getCustomListForCustomer(customerSequenceNumber, expirationDuration))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getCustomListForCustomer = customListController
				.getCustomListForCustomer(customerSequenceNumber, expirationDuration);
		assertEquals(HttpStatus.BAD_REQUEST, getCustomListForCustomer.getStatusCode());

		doThrow(new TokenExpiredException("")).when(customListServiceInterface)
				.deleteAllCustomListForCustomer(customerSequenceNumber, expirationDuration);

		ResponseEntity<?> deleteAllCustomListForCustomer = customListController
				.deleteAllCustomListForCustomer(customerSequenceNumber, expirationDuration);
		assertEquals(HttpStatus.BAD_REQUEST, deleteAllCustomListForCustomer.getStatusCode());

		doThrow(new TokenExpiredException("")).when(customListServiceInterface).deleteCustomList(customerSequenceNumber,
				customList, expirationDuration);

		ResponseEntity<?> deleteCustomList = customListController.deleteCustomList(customListManagementRequest);
		assertEquals(HttpStatus.BAD_REQUEST, deleteCustomList.getStatusCode());

		doThrow(new TokenExpiredException("", "")).when(customListServiceInterface)
				.deleteCustomList(customerSequenceNumber, customList, expirationDuration);

		ResponseEntity<?> deleteCustomList1 = customListController.deleteCustomList(customListManagementRequest);

		assertEquals(HttpStatus.BAD_REQUEST, deleteCustomList1.getStatusCode());

		when(customListServiceInterface.updateCustomListName(customListManagementRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> updateCustomListName = customListController.updateCustomListName(customListManagementRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateCustomListName.getStatusCode());

	}

}
