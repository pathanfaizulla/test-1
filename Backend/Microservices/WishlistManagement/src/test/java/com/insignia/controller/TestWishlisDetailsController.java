package com.insignia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

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
import com.insignia.model.ProductDetailsRequest;
import com.insignia.model.ProductDetailsResponse;
import com.insignia.model.WishlistDetails;
import com.insignia.model.WishlistDetailsRequest;
import com.insignia.model.WishlistManagementResponse;
import com.insignia.serviceInterface.WishlistServiceInterface;

@ExtendWith(MockitoExtension.class)
public class TestWishlisDetailsController {

	@InjectMocks
	private WishlistController wishlistController;

	@Mock
	private WishlistServiceInterface serviceRepo;

	ProductDetailsRequest productDetailsRequest = new ProductDetailsRequest();
	WishlistDetailsRequest wishlistDetailsRequest = new WishlistDetailsRequest();
	WishlistDetails wishlistDetailsResponse = new WishlistDetails();
	WishlistManagementResponse wishlistManagementResponse = new WishlistManagementResponse();

	List<ProductDetailsRequest> productDetailsRequestList = new ArrayList<>();
	List<WishlistDetails> wishlistDetailsResponseList = new ArrayList<>();
	
	ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();

	public void dataInitilization() {
		wishlistDetailsRequest.setCustomerSequenceNumber(8L);
		wishlistDetailsRequest.setExpirationDuration(15);

		productDetailsRequest.setProductSequenceNumber(1L);
		productDetailsRequest.setQuantity(8);

		productDetailsRequestList.add(productDetailsRequest);

		wishlistDetailsRequest.setProductDetailsRequestList(productDetailsRequestList);

		wishlistDetailsResponse.setWishlistId(5L);
		productDetailsResponse.setProductSequenceNumber(7L);
		productDetailsResponse.setProductAvailableQuantity(5);
		productDetailsResponse.setProductName("mobile");
		productDetailsResponse.setDescription("HI");
		productDetailsResponse.setProductImagePath("phone");

		wishlistDetailsResponseList.add(wishlistDetailsResponse);

	}

	@Test
	public void testSaveCustomerWishlist() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();
		when(serviceRepo.saveWishlistForCustomer(wishlistDetailsRequest)).thenReturn(wishlistManagementResponse);

		ResponseEntity<?> saveCustomerWishlist = wishlistController.saveWishlistForCustomer(wishlistDetailsRequest);

		assertEquals(HttpStatus.OK, saveCustomerWishlist.getStatusCode());

	}

	@Test
	public void testGetCustomerWishlist() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();
		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 5;

		when(serviceRepo.getCustomerWishlist(customerSequenceNumber, expirationDuration))
				.thenReturn(wishlistManagementResponse);
		ResponseEntity<?> getCustomerWishlist = wishlistController.getCustomerWishlist(customerSequenceNumber,
				expirationDuration);

		assertEquals(HttpStatus.OK, getCustomerWishlist.getStatusCode());
	}

	@Test
	public void testRemoveCustomerWishlist() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 15;
		List<Long> productId = Arrays.asList(1L);

		doNothing().when(serviceRepo).removeProductFromWishlist(customerSequenceNumber, productId, expirationDuration);

		ResponseEntity<?> removeCustomerWishlist = wishlistController.removeProductFromWishlist(wishlistDetailsRequest);

		assertEquals(HttpStatus.OK, removeCustomerWishlist.getStatusCode());

	}

	@Test
	public void testDeleteCustomerWishlist() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 15;

		doNothing().when(serviceRepo).deleteCustomerWishList(customerSequenceNumber, expirationDuration);

		ResponseEntity<?> deleteCustomerWishlist = wishlistController.deleteCustomerWishList(customerSequenceNumber,
				expirationDuration);

		assertEquals(HttpStatus.OK, deleteCustomerWishlist.getStatusCode());

	}

	@Test
	public void testForTokenExpired() throws InvalidInputParametersException, Exception {

		dataInitilization();

		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 15;

		List<Long> productId = Arrays.asList(1L);

		when(serviceRepo.saveWishlistForCustomer(wishlistDetailsRequest)).thenThrow(new TokenExpiredException(""));

		ResponseEntity<?> saveCustomerWishlist = wishlistController.saveWishlistForCustomer(wishlistDetailsRequest);
		assertEquals(HttpStatus.BAD_REQUEST, saveCustomerWishlist.getStatusCode());

		when(serviceRepo.getCustomerWishlist(customerSequenceNumber, expirationDuration))
				.thenThrow(new TokenExpiredException(""));

		ResponseEntity<?> getCustomerWishlist = wishlistController.getCustomerWishlist(customerSequenceNumber,
				expirationDuration);
		assertEquals(HttpStatus.BAD_REQUEST, getCustomerWishlist.getStatusCode());

		doThrow(new TokenExpiredException("", "")).when(serviceRepo).deleteCustomerWishList(customerSequenceNumber,
				expirationDuration);

		doThrow(new TokenExpiredException("", "")).when(serviceRepo).removeProductFromWishlist(customerSequenceNumber,
				productId, expirationDuration);

		ResponseEntity<?> responseEntity = wishlistController.removeProductFromWishlist(wishlistDetailsRequest);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		ResponseEntity<?> deleteCustomerWishList = wishlistController.deleteCustomerWishList(customerSequenceNumber,
				expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, deleteCustomerWishList.getStatusCode());

	}

	@Test
	public void testForException() throws InvalidInputParametersException, Exception {
		dataInitilization();

		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 5;
		List<Long> productId = Arrays.asList(1L);

		when(serviceRepo.saveWishlistForCustomer(wishlistDetailsRequest)).thenThrow(new NullPointerException(""));

		ResponseEntity<?> saveCustomerWishlist = wishlistController.saveWishlistForCustomer(wishlistDetailsRequest);
		assertEquals(HttpStatus.BAD_REQUEST, saveCustomerWishlist.getStatusCode());

		when(serviceRepo.getCustomerWishlist(customerSequenceNumber, expirationDuration))
				.thenThrow(new NullPointerException(""));

		ResponseEntity<?> getCustomerWishlist = wishlistController.getCustomerWishlist(customerSequenceNumber,
				expirationDuration);
		assertEquals(HttpStatus.BAD_REQUEST, getCustomerWishlist.getStatusCode());

		doThrow(new NullPointerException("")).when(serviceRepo).deleteCustomerWishList(customerSequenceNumber,
				expirationDuration);

		ResponseEntity<?> deleteCustomerWishList = wishlistController.deleteCustomerWishList(customerSequenceNumber,
				expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, deleteCustomerWishList.getStatusCode());

		doThrow(new NullPointerException("")).when(serviceRepo).removeProductFromWishlist(customerSequenceNumber,
				productId, expirationDuration);

		ResponseEntity<?> responseEntity = wishlistController.removeProductFromWishlist(wishlistDetailsRequest);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

	}

}
