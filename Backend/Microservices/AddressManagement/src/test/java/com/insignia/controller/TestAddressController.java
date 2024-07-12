package com.insignia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.entity.AddressDetails;
import com.insignia.model.AddressRequest;
import com.insignia.model.AddressResponse;
import com.insignia.serviceInterface.AddressServiceInterface;
import com.insignia.validations.AddressDetailsValidation;

@ExtendWith(MockitoExtension.class)
public class TestAddressController {

	@InjectMocks
	private AddressController addressController;

	@Mock
	private AddressServiceInterface serviceRepo;

	@Mock
	private TokenExpiredException tokenExpire;

	@InjectMocks
	private AddressDetailsValidation addressValidator;

	AddressRequest addressRequest = new AddressRequest();
	AddressResponse addressRes = new AddressResponse();
	AddressDetails addressDetails = new AddressDetails();

	public void dataInitilization() {
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

	}

	@Test
	public void testSaveAddressValidator() throws InvalidInputParametersException {
		dataInitilization();
		addressRequest.setAddressLine1(null);
		addressRequest.setCity(null);
		addressRequest.setState(null);
		addressRequest.setCountry(null);
		addressRequest.setZipCode(null);

		ResponseEntity<?> saveAddress = addressController.saveAddress(addressRequest);
		assertEquals(HttpStatus.BAD_REQUEST, saveAddress.getStatusCode());

		ResponseEntity<?> updateAddress = addressController.updateAddressDetails(addressRequest);
		assertEquals(HttpStatus.BAD_REQUEST, updateAddress.getStatusCode());

	}

	@Test
	public void testSaveAddress() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		when(serviceRepo.saveAddressDetails(addressRequest)).thenReturn(addressRes);
		ResponseEntity<?> response = addressController.saveAddress(addressRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testUpdateAddress() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		when(serviceRepo.updateAddressDetails(addressRequest)).thenReturn(addressRes);
		ResponseEntity<?> response = addressController.updateAddressDetails(addressRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testForDeleteAddressDetails() throws InvalidInputParametersException, Exception {

		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 5;
		
		doNothing().when(serviceRepo).deleteAddressForCustomer(customerSequenceNumber,expirationDuration);

		ResponseEntity<?> deleteAddressForCustomer = addressController.deleteAddressForCustomer(customerSequenceNumber,expirationDuration);
		verify(serviceRepo, times(1)).deleteAddressForCustomer(customerSequenceNumber,expirationDuration);
		assertEquals("Record Successfully Deleted", deleteAddressForCustomer.getBody());

	}

	@Test
	public void testForDeleteAddressDetailsBasedOnSequenceNumber() throws InvalidInputParametersException, Exception {

		Integer sequenceNumber = 8;
		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 5;

		doNothing().when(serviceRepo).deleteByAddressId(sequenceNumber, customerSequenceNumber,expirationDuration);

		ResponseEntity<?> deleteByAddressId = addressController.deleteByAddressId(sequenceNumber,
				customerSequenceNumber,expirationDuration);
		verify(serviceRepo, times(1)).deleteByAddressId(sequenceNumber, customerSequenceNumber,expirationDuration);
		assertEquals("Record Successfully Deleted", deleteByAddressId.getBody());

	}

	@Test
	public void testForTokenExpired() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();
		Integer sequenceNumber = 1;
		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 5;

		when(serviceRepo.saveAddressDetails(addressRequest)).thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> saveAddress = addressController.saveAddress(addressRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveAddress.getStatusCode());

		when(serviceRepo.updateAddressDetails(addressRequest)).thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> updateAddressDetails = addressController.updateAddressDetails(addressRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateAddressDetails.getStatusCode());

		when(serviceRepo.getAddressList(customerSequenceNumber,expirationDuration)).thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getAddressList = addressController.getAddressList(customerSequenceNumber,expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, getAddressList.getStatusCode());

		doThrow(new TokenExpiredException("")).when(serviceRepo).deleteByAddressId(sequenceNumber,
				customerSequenceNumber,expirationDuration);
		ResponseEntity<?> deleteByAddressId = addressController.deleteByAddressId(sequenceNumber,
				customerSequenceNumber,expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, deleteByAddressId.getStatusCode());

		doThrow(new TokenExpiredException("")).when(serviceRepo).deleteAddressForCustomer(customerSequenceNumber,expirationDuration);
		ResponseEntity<?> deleteAddressForCustomer = addressController.deleteAddressForCustomer(customerSequenceNumber,expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, deleteAddressForCustomer.getStatusCode());

	}

	@Test
	public void testForDataIntegrityViolationException() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		when(serviceRepo.saveAddressDetails(addressRequest)).thenThrow(new DataIntegrityViolationException(""));
		ResponseEntity<?> saveAddress = addressController.saveAddress(addressRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveAddress.getStatusCode());

		when(serviceRepo.updateAddressDetails(addressRequest)).thenThrow(new DataIntegrityViolationException(""));
		ResponseEntity<?> updateAddressDetails = addressController.updateAddressDetails(addressRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateAddressDetails.getStatusCode());

	}

	@Test
	public void testForException() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();
		Integer sequenceNumber = 1;
		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 5;

		when(serviceRepo.saveAddressDetails(addressRequest)).thenThrow(new NullPointerException(""));
		ResponseEntity<?> saveAddress = addressController.saveAddress(addressRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveAddress.getStatusCode());
		
		when(serviceRepo.updateAddressDetails(addressRequest)).thenThrow(new NullPointerException(""));
		ResponseEntity<?> updateAddressDetails = addressController.updateAddressDetails(addressRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateAddressDetails.getStatusCode());

		doThrow(new NullPointerException("")).when(serviceRepo).deleteByAddressId(sequenceNumber,
				customerSequenceNumber,expirationDuration);
		ResponseEntity<?> deleteByAddressId = addressController.deleteByAddressId(sequenceNumber,
				customerSequenceNumber,expirationDuration);
		assertEquals(HttpStatus.BAD_REQUEST, deleteByAddressId.getStatusCode());

		doThrow(new NullPointerException("")).when(serviceRepo).deleteAddressForCustomer(customerSequenceNumber,expirationDuration);

		ResponseEntity<?> deleteAddressForCustomer = addressController.deleteAddressForCustomer(customerSequenceNumber,expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, deleteAddressForCustomer.getStatusCode());

		when(serviceRepo.getAddressList(customerSequenceNumber,expirationDuration)).thenThrow(new NullPointerException(""));
		ResponseEntity<?> getAddressList = addressController.getAddressList(customerSequenceNumber,expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, getAddressList.getStatusCode());
	}

}
