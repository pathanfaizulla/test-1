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
import com.insignia.entity.ProductFamily;
import com.insignia.model.ProductFamilyRequest;
import com.insignia.model.ProductFamilyResponse;
import com.insignia.serviceInterface.ProductFamilyServiceInterface;

@ExtendWith(MockitoExtension.class)
public class TestProductFamilyController {

	@InjectMocks
	private ProductFamilyController productFamilyController;

	@Mock
	private ProductFamilyServiceInterface productFamilyServiceInterface;

	ProductFamilyRequest productFamilyRequest = new ProductFamilyRequest();
	ProductFamilyResponse productFamilyResponse = new ProductFamilyResponse();

	List<ProductFamilyResponse> productFamilyResponseList = new ArrayList<>();
	ProductFamily productFamily = new ProductFamily();

	public void dataInitilization() {

		productFamilyRequest.setCustomerSequenceNumber(5L);
		productFamilyRequest.setExpirationDuration(15);
		productFamilyRequest.setFamilyName("pink");
		productFamilyRequest.setFamilyDescription("for bench");
		productFamilyRequest.setApplicationId("insignia");
		productFamilyRequest.setTenantId("LU008");

		productFamilyResponse.setSequenceNumber(5);
		productFamilyResponse.setFamilyName("pink");
		productFamilyResponse.setFamilyDescription("for bench");

		productFamilyResponseList.add(productFamilyResponse);

		productFamily.setSequenceNumber(5);
		productFamily.setFamilyName("pink");
		productFamily.setFamilyDescription("for bench");

	}

	@Test
	public void testSaveProductFamily() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		when(productFamilyServiceInterface.saveProductFamily(productFamilyRequest)).thenReturn(productFamilyResponse);
		ResponseEntity<?> response = productFamilyController.saveProductFamily(productFamilyRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testDeleteProductFamily() throws Exception {

		dataInitilization();
		doNothing().when(productFamilyServiceInterface).deleteProductFamily(productFamilyRequest);

		productFamilyController.deleteProductFamily(productFamilyRequest);
		verify(productFamilyServiceInterface, times(1)).deleteProductFamily(productFamilyRequest);
	}

	@Test
	public void testGetAllProductFamily() throws TokenExpiredException, InvalidInputParametersException {

		dataInitilization();

		when(productFamilyServiceInterface.getAllProductFamily(productFamilyRequest))
				.thenReturn(productFamilyResponseList);
		ResponseEntity<?> response = productFamilyController.getAllProductFamily(productFamilyRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testUpdateProductFamily() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();
		productFamilyRequest.setSequenceNumber(5);

		when(productFamilyServiceInterface.updateProductFamily(productFamilyRequest))
				.thenReturn(productFamilyResponse);
		ResponseEntity<?> response = productFamilyController.updateProductFamily(productFamilyRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testForTokenExpired() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		when(productFamilyServiceInterface.saveProductFamily(productFamilyRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> saveProductFamily = productFamilyController.saveProductFamily(productFamilyRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveProductFamily.getStatusCode());

		doThrow(new TokenExpiredException("")).when(productFamilyServiceInterface)
				.deleteProductFamily(productFamilyRequest);;
		ResponseEntity<?> deleteProductFamily = productFamilyController
				.deleteProductFamily(productFamilyRequest);
		assertEquals(HttpStatus.BAD_REQUEST, deleteProductFamily.getStatusCode());

		when(productFamilyServiceInterface.getAllProductFamily(productFamilyRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getAllProductFamilys = productFamilyController
				.getAllProductFamily(productFamilyRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getAllProductFamilys.getStatusCode());

		when(productFamilyServiceInterface.updateProductFamily(productFamilyRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> updateProductFamily = productFamilyController
				.updateProductFamily(productFamilyRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateProductFamily.getStatusCode());

	}
	
	@Test
	public void testForException() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		when(productFamilyServiceInterface.saveProductFamily(productFamilyRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> saveProductFamily = productFamilyController.saveProductFamily(productFamilyRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveProductFamily.getStatusCode());

		doThrow(new NullPointerException("")).when(productFamilyServiceInterface)
				.deleteProductFamily(productFamilyRequest);;
		ResponseEntity<?> deleteProductFamily = productFamilyController
				.deleteProductFamily(productFamilyRequest);
		assertEquals(HttpStatus.BAD_REQUEST, deleteProductFamily.getStatusCode());

		when(productFamilyServiceInterface.getAllProductFamily(productFamilyRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> getAllProductFamilys = productFamilyController
				.getAllProductFamily(productFamilyRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getAllProductFamilys.getStatusCode());

		when(productFamilyServiceInterface.updateProductFamily(productFamilyRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> updateProductFamily = productFamilyController
				.updateProductFamily(productFamilyRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateProductFamily.getStatusCode());

	}

	
	@Test
	public void testForSaveProductFamilyInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(productFamilyServiceInterface)
				.saveProductFamily(productFamilyRequest);

		ResponseEntity<?> saveProductFamily = productFamilyController
				.saveProductFamily(productFamilyRequest);

		verify(productFamilyServiceInterface).saveProductFamily(productFamilyRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveProductFamily.getStatusCode());

	}
	@Test
	public void testForUpdateProductFamilyInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(productFamilyServiceInterface)
				.updateProductFamily(productFamilyRequest);
		ResponseEntity<?> updateProductFamily = productFamilyController
				.updateProductFamily(productFamilyRequest);

		verify(productFamilyServiceInterface).updateProductFamily(productFamilyRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateProductFamily.getStatusCode());

	}
	@Test
	public void testForProductFamilyInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(productFamilyServiceInterface)
				.deleteProductFamily(productFamilyRequest);;
		ResponseEntity<?> deleteProductFamily = productFamilyController
				.deleteProductFamily(productFamilyRequest);

		verify(productFamilyServiceInterface).deleteProductFamily(productFamilyRequest);

		assertEquals(HttpStatus.BAD_REQUEST, deleteProductFamily.getStatusCode());

	}
}
