package com.insignia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
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
import com.insignia.entity.CurrencyDetails;
import com.insignia.model.CurrencyDetailsRequest;
import com.insignia.model.CurrencyDetailsResponse;
import com.insignia.serviceInterface.CurrencyDetailsServiceInterface;

@ExtendWith(MockitoExtension.class)
public class TestCurrencyDetailsController {

	@InjectMocks
	private CurencyDetailsController curencyDetailsController;

	@Mock
	private CurrencyDetailsServiceInterface currencyDetailsServiceInterface;

	CurrencyDetailsRequest currencyDetailsRequest = new CurrencyDetailsRequest();
	CurrencyDetailsResponse currencyDetailsResponse = new CurrencyDetailsResponse();

	List<CurrencyDetailsResponse> currencyDetailsResponseList = new ArrayList<>();
	CurrencyDetails currencyDetails = new CurrencyDetails();

	public void dataInitilization() {

		currencyDetailsRequest.setCustomerSequenceNumber(5L);
		currencyDetailsRequest.setExpirationDuration(15);
		currencyDetailsRequest.setCurrencyName("indian rupee");
		currencyDetailsRequest.setCurrencyDescription("for india");
		currencyDetailsRequest.setApplicationId("hinges-design");
		currencyDetailsRequest.setTenantId("LU008");
		currencyDetailsRequest.setCurrencyCode("INR");
		currencyDetailsRequest.setBaseRateCurrency("inr");

		currencyDetailsResponse.setSequenceNumber(5);
		currencyDetailsResponse.setCurrencyName("indian rupee");
		currencyDetailsResponse.setCurrencyDescription("for india");
		currencyDetailsResponse.setCurrencyCode("INR");
		currencyDetailsResponse.setBaseRateCurrency("inr");

		currencyDetailsResponseList.add(currencyDetailsResponse);

		currencyDetails.setCurrencyName("indian rupee");
		currencyDetails.setCurrencyDescription("for india");
		currencyDetails.setApplicationId("hinges-design");
		currencyDetails.setTenantId("LU008");
		currencyDetails.setCurrencyCode("INR");
		currencyDetails.setBaseRateCurrency("inr");

	}

	@Test
	public void testSaveCurrencyDetails() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		when(currencyDetailsServiceInterface.saveCurrencyDetails(currencyDetailsRequest)).thenReturn(currencyDetailsResponse);
		ResponseEntity<?> response = curencyDetailsController.saveCurrencyDetails(currencyDetailsRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testDeleteCurrencyDetails() throws Exception {

		dataInitilization();
		doNothing().when(currencyDetailsServiceInterface).deleteCurrencyDetails(currencyDetailsRequest);

		curencyDetailsController.deleteCurrencyDetails(currencyDetailsRequest);
		verify(currencyDetailsServiceInterface, times(1)).deleteCurrencyDetails(currencyDetailsRequest);
	}

	@Test
	public void testGetAllCurrencyDetails() throws TokenExpiredException, InvalidInputParametersException {

		dataInitilization();

		when(currencyDetailsServiceInterface.getAllCurrencyDetails(currencyDetailsRequest))
				.thenReturn(currencyDetailsResponseList);
		ResponseEntity<?> response = curencyDetailsController.getAllCurrencyDetails(currencyDetailsRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testUpdateCurrencyDetails() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();
		currencyDetailsRequest.setSequenceNumber(5);

		when(currencyDetailsServiceInterface.updateCurrencyDetails(currencyDetailsRequest))
				.thenReturn(currencyDetailsResponse);
		ResponseEntity<?> response = curencyDetailsController.updateCurrencyDetails(currencyDetailsRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testForTokenExpired() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		when(currencyDetailsServiceInterface.saveCurrencyDetails(currencyDetailsRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> saveProductFamily = curencyDetailsController.saveCurrencyDetails(currencyDetailsRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveProductFamily.getStatusCode());

		doThrow(new TokenExpiredException("")).when(currencyDetailsServiceInterface)
				.deleteCurrencyDetails(currencyDetailsRequest);;
		ResponseEntity<?> deleteProductFamily = curencyDetailsController
				.deleteCurrencyDetails(currencyDetailsRequest);
		assertEquals(HttpStatus.BAD_REQUEST, deleteProductFamily.getStatusCode());

		when(currencyDetailsServiceInterface.getAllCurrencyDetails(currencyDetailsRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getAllProductFamilys = curencyDetailsController
				.getAllCurrencyDetails(currencyDetailsRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getAllProductFamilys.getStatusCode());

		when(currencyDetailsServiceInterface.updateCurrencyDetails(currencyDetailsRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> updateProductFamily = curencyDetailsController
				.updateCurrencyDetails(currencyDetailsRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateProductFamily.getStatusCode());

	}
	
	@Test
	public void testForException() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		when(currencyDetailsServiceInterface.saveCurrencyDetails(currencyDetailsRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> saveProductFamily = curencyDetailsController.saveCurrencyDetails(currencyDetailsRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveProductFamily.getStatusCode());

		doThrow(new NullPointerException("")).when(currencyDetailsServiceInterface)
				.deleteCurrencyDetails(currencyDetailsRequest);;
		ResponseEntity<?> deleteProductFamily = curencyDetailsController
				.deleteCurrencyDetails(currencyDetailsRequest);
		assertEquals(HttpStatus.BAD_REQUEST, deleteProductFamily.getStatusCode());

		when(currencyDetailsServiceInterface.getAllCurrencyDetails(currencyDetailsRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> getAllProductFamilys = curencyDetailsController
				.getAllCurrencyDetails(currencyDetailsRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getAllProductFamilys.getStatusCode());

		when(currencyDetailsServiceInterface.updateCurrencyDetails(currencyDetailsRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> updateProductFamily = curencyDetailsController
				.updateCurrencyDetails(currencyDetailsRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateProductFamily.getStatusCode());

	}

	
	@Test
	public void testForSaveCurrencyDetailsInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(currencyDetailsServiceInterface)
				.saveCurrencyDetails(currencyDetailsRequest);

		ResponseEntity<?> saveProductFamily = curencyDetailsController
				.saveCurrencyDetails(currencyDetailsRequest);

		verify(currencyDetailsServiceInterface).saveCurrencyDetails(currencyDetailsRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveProductFamily.getStatusCode());

	}
	@Test
	public void testForUpdateCurrencyDetailsInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(currencyDetailsServiceInterface)
				.updateCurrencyDetails(currencyDetailsRequest);
		ResponseEntity<?> updateCurrencyDetails= curencyDetailsController
				.updateCurrencyDetails(currencyDetailsRequest);

		verify(currencyDetailsServiceInterface).updateCurrencyDetails(currencyDetailsRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateCurrencyDetails.getStatusCode());

	}
	@Test
	public void testForProductFamilyInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(currencyDetailsServiceInterface)
				.deleteCurrencyDetails(currencyDetailsRequest);;
		ResponseEntity<?> deleteProductFamily = curencyDetailsController
				.deleteCurrencyDetails(currencyDetailsRequest);

		verify(currencyDetailsServiceInterface).deleteCurrencyDetails(currencyDetailsRequest);

		assertEquals(HttpStatus.BAD_REQUEST, deleteProductFamily.getStatusCode());

	}
}
