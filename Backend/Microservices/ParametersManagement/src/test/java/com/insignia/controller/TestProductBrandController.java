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
import com.insignia.entity.ProductBrand;
import com.insignia.model.ProductBrandRequest;
import com.insignia.model.ProductBrandResponse;
import com.insignia.serviceInterface.ProductBrandServiceInterface;

@ExtendWith(MockitoExtension.class)
public class TestProductBrandController {

	@InjectMocks
	private ProductBrandController productBrandController;

	@Mock
	private ProductBrandServiceInterface productBrandServiceInterface;

	ProductBrandRequest productBrandRequest = new ProductBrandRequest();
	ProductBrandResponse productBrandResponse = new ProductBrandResponse();

	List<ProductBrandResponse> productBrandResponseList = new ArrayList<>();
	ProductBrand productBrand = new ProductBrand();

	public void dataInitilization() {

		productBrandRequest.setCustomerSequenceNumber(5L);
		productBrandRequest.setExpirationDuration(15);
		productBrandRequest.setBrandName("pink");
		productBrandRequest.setBrandDescription("for bench");
		productBrandRequest.setApplicationId("insignia");
		productBrandRequest.setTenantId("LU008");

		productBrandResponse.setSequenceNumber(5);
		productBrandResponse.setBrandName("pink");
		productBrandResponse.setBrandDescription("for bench");

		productBrandResponseList.add(productBrandResponse);

		productBrand.setSequenceNumber(5);
		productBrand.setBrandName("pink");
		productBrand.setBrandDescription("for bench");

	}

	@Test
	public void testSaveProductBrand() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		when(productBrandServiceInterface.saveProductBrand(productBrandRequest)).thenReturn(productBrandResponse);
		ResponseEntity<?> response = productBrandController.saveProductBrand(productBrandRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testDeleteProductBrand() throws Exception {

		dataInitilization();
		doNothing().when(productBrandServiceInterface).deleteProductBrand(productBrandRequest);

		productBrandController.deleteProductBrand(productBrandRequest);
		verify(productBrandServiceInterface, times(1)).deleteProductBrand(productBrandRequest);
	}

	@Test
	public void testGetAllProductBrand() throws TokenExpiredException, InvalidInputParametersException {

		dataInitilization();

		when(productBrandServiceInterface.getAllProductBrand(productBrandRequest))
				.thenReturn(productBrandResponseList);
		ResponseEntity<?> response = productBrandController.getAllProductBrands(productBrandRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testUpdateProductBrand() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();
		productBrandRequest.setSequenceNumber(5);

		when(productBrandServiceInterface.updateProductBrand(productBrandRequest))
				.thenReturn(productBrandResponse);
		ResponseEntity<?> response = productBrandController.updateProductBrand(productBrandRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testForTokenExpired() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		when(productBrandServiceInterface.saveProductBrand(productBrandRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> saveProductBrand = productBrandController.saveProductBrand(productBrandRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveProductBrand.getStatusCode());

		doThrow(new TokenExpiredException("")).when(productBrandServiceInterface)
				.deleteProductBrand(productBrandRequest);;
		ResponseEntity<?> deleteProductBrand = productBrandController
				.deleteProductBrand(productBrandRequest);
		assertEquals(HttpStatus.BAD_REQUEST, deleteProductBrand.getStatusCode());

		when(productBrandServiceInterface.getAllProductBrand(productBrandRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getAllProductBrands = productBrandController
				.getAllProductBrands(productBrandRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getAllProductBrands.getStatusCode());

		when(productBrandServiceInterface.updateProductBrand(productBrandRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> updateProductBrand = productBrandController
				.updateProductBrand(productBrandRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateProductBrand.getStatusCode());

	}
	
	@Test
	public void testForException() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		when(productBrandServiceInterface.saveProductBrand(productBrandRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> saveProductBrand = productBrandController.saveProductBrand(productBrandRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveProductBrand.getStatusCode());

		doThrow(new NullPointerException("")).when(productBrandServiceInterface)
				.deleteProductBrand(productBrandRequest);;
		ResponseEntity<?> deleteProductBrand = productBrandController
				.deleteProductBrand(productBrandRequest);
		assertEquals(HttpStatus.BAD_REQUEST, deleteProductBrand.getStatusCode());

		when(productBrandServiceInterface.getAllProductBrand(productBrandRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> getAllProductBrands = productBrandController
				.getAllProductBrands(productBrandRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getAllProductBrands.getStatusCode());

		when(productBrandServiceInterface.updateProductBrand(productBrandRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> updateProductBrand = productBrandController
				.updateProductBrand(productBrandRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateProductBrand.getStatusCode());

	}

	
	@Test
	public void testForSaveProductBrandInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(productBrandServiceInterface)
				.saveProductBrand(productBrandRequest);

		ResponseEntity<?> saveProductBrand = productBrandController
				.saveProductBrand(productBrandRequest);

		verify(productBrandServiceInterface).saveProductBrand(productBrandRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveProductBrand.getStatusCode());

	}
	@Test
	public void testForUpdateProductBrandInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(productBrandServiceInterface)
				.updateProductBrand(productBrandRequest);
		ResponseEntity<?> updateProductBrand = productBrandController
				.updateProductBrand(productBrandRequest);

		verify(productBrandServiceInterface).updateProductBrand(productBrandRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateProductBrand.getStatusCode());

	}
	@Test
	public void testForProductBrandInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(productBrandServiceInterface)
				.deleteProductBrand(productBrandRequest);;
		ResponseEntity<?> deleteProductBrand = productBrandController
				.deleteProductBrand(productBrandRequest);

		verify(productBrandServiceInterface).deleteProductBrand(productBrandRequest);

		assertEquals(HttpStatus.BAD_REQUEST, deleteProductBrand.getStatusCode());

	}
}
