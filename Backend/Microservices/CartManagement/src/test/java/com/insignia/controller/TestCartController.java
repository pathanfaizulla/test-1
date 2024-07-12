package com.insignia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.insignia.model.CartProductDetails;
import com.insignia.model.CartRequest;
import com.insignia.model.CartResponce;
import com.insignia.model.CustomerCartRequest;
import com.insignia.model.CustomerCartResponse;
import com.insignia.model.ProductDetailsResponse;
import com.insignia.service.CartServiceInterface;

@ExtendWith(MockitoExtension.class)
public class TestCartController {

	@InjectMocks
	private CartController cartController;

	@Mock
	private CartServiceInterface cartServiceRepo;

	CartRequest cartRequest = new CartRequest();
	CartResponce cartResponse = new CartResponce();
	CustomerCartRequest customerCartRequest = new CustomerCartRequest();

	List<CustomerCartResponse> customerCartResponseList = new ArrayList<>();
	CustomerCartResponse customerCartResponse = new CustomerCartResponse();

	List<CartProductDetails> cartProductDetailsList = new ArrayList<>();
	CartProductDetails cartProductDetails = new CartProductDetails();

	ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse(); 
	
	public void dataInitilization() {
		cartRequest.setCartId(5);
		cartRequest.setCustomerSequenceNumber(5L);
		cartRequest.setCartSequenceNumber(5L);
		cartRequest.setExpirationDuration(15);

		cartResponse.setCustomerSequenceNumber(5L);
		cartResponse.setCartSequenceNumber(5L);

		customerCartRequest.setCustomerSequenceNumber(5L);
		customerCartRequest.setCustomerCartSequenceNumber(5L);
		customerCartRequest.setExpirationDuration(15);
		customerCartRequest.setProductSequenceNumber(5L);
		customerCartRequest.setCartSequenceNumber(5L);
		customerCartRequest.setProductQuantity("5");

		List<Long> productSequenceNumber = Arrays.asList(5L);
		customerCartResponse.setCartSequenceNumber(5L);
		customerCartResponse.setProductSequenceNumber(productSequenceNumber);

		cartProductDetails.setCustomerCartSequenceNumber(5L);
		cartProductDetails.setProductQuantity("5");
		
		
		productDetailsResponse.setProductSequenceNumber(5L);
		productDetailsResponse.setProductId("5L");
		productDetailsResponse.setProductName("Mobile");
		productDetailsResponse.setDescription("I Phone");
		productDetailsResponse.setProductImagePath("Mobile");

		cartProductDetailsList.add(cartProductDetails);

		customerCartResponse.setCartProductDetailsList(cartProductDetailsList);

		customerCartResponseList.add(customerCartResponse);
	}

	@Test
	public void testSaveCart() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		when(cartServiceRepo.saveCart(cartRequest)).thenReturn(cartResponse);
		ResponseEntity<?> response = cartController.saveCart(cartRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testDeleteCartForCustomer() throws Exception {

		Long cartSequenceNumber = 5L;
		Long customerSequenceNumber = 5L;
		Integer expirationDuration = 15;

		doNothing().when(cartServiceRepo).deleteCartForCustomer(cartSequenceNumber, customerSequenceNumber,
				expirationDuration);

		cartController.deleteCartForCustomer(cartSequenceNumber, customerSequenceNumber, expirationDuration);
		verify(cartServiceRepo, times(1)).deleteCartForCustomer(cartSequenceNumber, customerSequenceNumber,
				expirationDuration);

	}

	@Test
	public void testGetCustomerCartInformation() throws TokenExpiredException, InvalidInputParametersException {

		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 5;

		when(cartServiceRepo.getCustomerCartInformation(customerSequenceNumber, expirationDuration))
				.thenReturn(customerCartResponse);
		ResponseEntity<?> response = cartController.getCustomerCartInformation(customerSequenceNumber,
				expirationDuration);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
//----------------------------------------------------------------------------------------	

	@Test
	public void testAddProductToCart() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		when(cartServiceRepo.addProductToCart(customerCartRequest)).thenReturn(customerCartResponse);
		ResponseEntity<?> response = cartController.addProductToCart(customerCartRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testUpdateProductToCart() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		when(cartServiceRepo.updateCustomerCartProduct(customerCartRequest)).thenReturn(customerCartResponse);
		ResponseEntity<?> response = cartController.updateProductToCart(customerCartRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testDeleteProductToCart() throws Exception {
		Long customerCartSequenceNumber = 5L;
		Long customerSequenceNumber = 5L;
		Integer expirationDuration = 15;

		doNothing().when(cartServiceRepo).deleteProductToCart(customerCartSequenceNumber, customerSequenceNumber,
				expirationDuration);

		cartController.deleteProductToCart(customerCartSequenceNumber, customerSequenceNumber, expirationDuration);
		verify(cartServiceRepo, times(1)).deleteProductToCart(customerCartSequenceNumber, customerSequenceNumber,
				expirationDuration);

	}

	@Test
	public void testGetProductByCustomer() throws TokenExpiredException, InvalidInputParametersException {
		Long cartSequenceNumber = 5L;
		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 5;

		when(cartServiceRepo.getProductByCustomer(cartSequenceNumber, customerSequenceNumber, expirationDuration))
				.thenReturn(customerCartResponseList);
		ResponseEntity<?> response = cartController.getProductByCustomer(cartSequenceNumber, customerSequenceNumber,
				expirationDuration);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testForTokenExpired() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();
		Long cartSequenceNumber = 5L;
		Long customerSequenceNumber = 5L;
		Long customerCartSequenceNumber = 5L;
		Integer expirationDuration = 15;

		when(cartServiceRepo.saveCart(cartRequest)).thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> saveCart = cartController.saveCart(cartRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveCart.getStatusCode());

		doThrow(new TokenExpiredException("")).when(cartServiceRepo).deleteCartForCustomer(cartSequenceNumber,
				customerSequenceNumber, expirationDuration);
		ResponseEntity<?> deleteCartForCustomer = cartController.deleteCartForCustomer(cartSequenceNumber,
				customerSequenceNumber, expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, deleteCartForCustomer.getStatusCode());

		when(cartServiceRepo.getCustomerCartInformation(customerSequenceNumber, expirationDuration))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getCustomerCartInformation = cartController.getCustomerCartInformation(customerSequenceNumber,
				expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, getCustomerCartInformation.getStatusCode());

//--------------------------------------------------------------------------------------		

		when(cartServiceRepo.addProductToCart(customerCartRequest)).thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> addProductToCart = cartController.addProductToCart(customerCartRequest);

		assertEquals(HttpStatus.BAD_REQUEST, addProductToCart.getStatusCode());

		when(cartServiceRepo.updateCustomerCartProduct(customerCartRequest)).thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> updateProductToCart = cartController.updateProductToCart(customerCartRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateProductToCart.getStatusCode());

		doThrow(new TokenExpiredException("")).when(cartServiceRepo).deleteProductToCart(customerCartSequenceNumber,
				customerSequenceNumber, expirationDuration);
		ResponseEntity<?> deleteProductToCart = cartController.deleteProductToCart(customerCartSequenceNumber,
				customerSequenceNumber, expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, deleteProductToCart.getStatusCode());

		when(cartServiceRepo.getProductByCustomer(cartSequenceNumber, customerSequenceNumber, expirationDuration))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getProductByCustomer = cartController.getProductByCustomer(cartSequenceNumber,
				customerSequenceNumber, expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, getProductByCustomer.getStatusCode());
	}

	@Test
	public void testForExceptionInCart() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		Long cartSequenceNumber = 5L;
		Long customerSequenceNumber = 5L;
		Long customerCartSequenceNumber = 5L;
		Integer expirationDuration = 15;

		when(cartServiceRepo.saveCart(cartRequest)).thenThrow(new NullPointerException(""));
		ResponseEntity<?> saveCart = cartController.saveCart(cartRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveCart.getStatusCode());

		doThrow(new NullPointerException("")).when(cartServiceRepo).deleteCartForCustomer(cartSequenceNumber,
				customerSequenceNumber, expirationDuration);
		ResponseEntity<?> deleteCartForCustomer = cartController.deleteCartForCustomer(cartSequenceNumber,
				customerSequenceNumber, expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, deleteCartForCustomer.getStatusCode());

		when(cartServiceRepo.getCustomerCartInformation(customerSequenceNumber, expirationDuration))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> getCustomerCartInformation = cartController.getCustomerCartInformation(customerSequenceNumber,
				expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, getCustomerCartInformation.getStatusCode());

//--------------------------------------------------------------------------------------		

		when(cartServiceRepo.addProductToCart(customerCartRequest)).thenThrow(new NullPointerException(""));
		ResponseEntity<?> addProductToCart = cartController.addProductToCart(customerCartRequest);

		assertEquals(HttpStatus.BAD_REQUEST, addProductToCart.getStatusCode());

		when(cartServiceRepo.updateCustomerCartProduct(customerCartRequest)).thenThrow(new NullPointerException(""));
		ResponseEntity<?> updateProductToCart = cartController.updateProductToCart(customerCartRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateProductToCart.getStatusCode());

		doThrow(new NullPointerException("")).when(cartServiceRepo).deleteProductToCart(customerCartSequenceNumber,
				customerSequenceNumber, expirationDuration);
		ResponseEntity<?> deleteProductToCart = cartController.deleteProductToCart(customerCartSequenceNumber,
				customerSequenceNumber, expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, deleteProductToCart.getStatusCode());

		when(cartServiceRepo.getProductByCustomer(cartSequenceNumber, customerSequenceNumber, expirationDuration))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> getProductByCustomer = cartController.getProductByCustomer(cartSequenceNumber,
				customerSequenceNumber, expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, getProductByCustomer.getStatusCode());
	}

	@Test
	public void testForInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(cartServiceRepo).saveCart(cartRequest);

		ResponseEntity<?> saveCart = cartController.saveCart(cartRequest);
		assertEquals(HttpStatus.BAD_REQUEST, saveCart.getStatusCode());

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(cartServiceRepo)
				.addProductToCart(customerCartRequest);

		ResponseEntity<?> addProductToCart = cartController.addProductToCart(customerCartRequest);
		assertEquals(HttpStatus.BAD_REQUEST, addProductToCart.getStatusCode());

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(cartServiceRepo)
				.updateCustomerCartProduct(customerCartRequest);

		ResponseEntity<?> updateProductToCart = cartController.updateProductToCart(customerCartRequest);
		assertEquals(HttpStatus.BAD_REQUEST, updateProductToCart.getStatusCode());

	}

}
