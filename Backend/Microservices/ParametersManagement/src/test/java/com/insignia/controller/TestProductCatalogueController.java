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
import com.insignia.entity.ProductCatalogue;
import com.insignia.model.ProductCatalogueRequest;
import com.insignia.model.ProductCatalogueResponse;
import com.insignia.serviceInterface.ProductCatalogueServiceInterface;

@ExtendWith(MockitoExtension.class)
public class TestProductCatalogueController {

	@InjectMocks
	private ProductCatalogueController productCatalogueController;

	@Mock
	private ProductCatalogueServiceInterface productCatalogueServiceInterface;

	ProductCatalogueRequest productCatalogueRequest = new ProductCatalogueRequest();
	ProductCatalogueResponse productCatalogueResponse = new ProductCatalogueResponse();

	List<ProductCatalogueResponse> productCatalogueResponseList = new ArrayList<>();
	ProductCatalogue productCatalogue = new ProductCatalogue();

	public void dataInitilization() {

		productCatalogueRequest.setCustomerSequenceNumber(5L);
		productCatalogueRequest.setExpirationDuration(15);
		productCatalogueRequest.setCatalogueName("pink");
		productCatalogueRequest.setCatalogueDescription("for bench");
		productCatalogueRequest.setApplicationId("insignia");
		productCatalogueRequest.setTenantId("LU008");

		productCatalogueResponse.setSequenceNumber(5);
		productCatalogueResponse.setCatalogueName("pink");
		productCatalogueResponse.setCatalogueDescription("for bench");

		productCatalogueResponseList.add(productCatalogueResponse);

		productCatalogue.setSequenceNumber(5);
		productCatalogue.setCatalogueName("pink");
		productCatalogue.setCatalogueDescription("for bench");
		productCatalogue.setApplicationId("insignia");
		productCatalogue.setTenantId("LU008");

	}

	@Test
	public void testSaveProductCatalogue() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		when(productCatalogueServiceInterface.saveProductCatalogue(productCatalogueRequest)).thenReturn(productCatalogueResponse);
		ResponseEntity<?> response = productCatalogueController.saveProductCatalogue(productCatalogueRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testDeleteProductCatalogue() throws Exception {

		dataInitilization();
		doNothing().when(productCatalogueServiceInterface).deleteProductCatalogue(productCatalogueRequest);

		productCatalogueController.deleteProductCatalogue(productCatalogueRequest);
		verify(productCatalogueServiceInterface, times(1)).deleteProductCatalogue(productCatalogueRequest);
	}

	@Test
	public void testGetAllProductCatalogue() throws TokenExpiredException, InvalidInputParametersException {

		dataInitilization();

		when(productCatalogueServiceInterface.getAllProductCatalogue(productCatalogueRequest))
				.thenReturn(productCatalogueResponseList);
		ResponseEntity<?> response = productCatalogueController.getAllProductCatalogue(productCatalogueRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testUpdateProductCatalogue() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();
		productCatalogueRequest.setSequenceNumber(5);

		when(productCatalogueServiceInterface.updateProductCatalogue(productCatalogueRequest))
				.thenReturn(productCatalogueResponse);
		ResponseEntity<?> response = productCatalogueController.updateProductCatalogue(productCatalogueRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testForTokenExpired() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		when(productCatalogueServiceInterface.saveProductCatalogue(productCatalogueRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> saveProductCatalogue = productCatalogueController.saveProductCatalogue(productCatalogueRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveProductCatalogue.getStatusCode());

		doThrow(new TokenExpiredException("")).when(productCatalogueServiceInterface)
				.deleteProductCatalogue(productCatalogueRequest);;
		ResponseEntity<?> deleteProductCatalogue = productCatalogueController
				.deleteProductCatalogue(productCatalogueRequest);
		assertEquals(HttpStatus.BAD_REQUEST, deleteProductCatalogue.getStatusCode());

		when(productCatalogueServiceInterface.getAllProductCatalogue(productCatalogueRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getAllProductCatalogues = productCatalogueController
				.getAllProductCatalogue(productCatalogueRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getAllProductCatalogues.getStatusCode());

		when(productCatalogueServiceInterface.updateProductCatalogue(productCatalogueRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> updateProductCatalogue = productCatalogueController
				.updateProductCatalogue(productCatalogueRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateProductCatalogue.getStatusCode());

	}
	
	@Test
	public void testForException() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		when(productCatalogueServiceInterface.saveProductCatalogue(productCatalogueRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> saveProductCatalogue = productCatalogueController.saveProductCatalogue(productCatalogueRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveProductCatalogue.getStatusCode());

		doThrow(new NullPointerException("")).when(productCatalogueServiceInterface)
				.deleteProductCatalogue(productCatalogueRequest);;
		ResponseEntity<?> deleteProductCatalogue = productCatalogueController
				.deleteProductCatalogue(productCatalogueRequest);
		assertEquals(HttpStatus.BAD_REQUEST, deleteProductCatalogue.getStatusCode());

		when(productCatalogueServiceInterface.getAllProductCatalogue(productCatalogueRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> getAllProductCatalogues = productCatalogueController
				.getAllProductCatalogue(productCatalogueRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getAllProductCatalogues.getStatusCode());

		when(productCatalogueServiceInterface.updateProductCatalogue(productCatalogueRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> updateProductCatalogue = productCatalogueController
				.updateProductCatalogue(productCatalogueRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateProductCatalogue.getStatusCode());

	}

	
	@Test
	public void testForSaveProductCatalogueInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(productCatalogueServiceInterface)
				.saveProductCatalogue(productCatalogueRequest);

		ResponseEntity<?> saveProductCatalogue = productCatalogueController
				.saveProductCatalogue(productCatalogueRequest);

		verify(productCatalogueServiceInterface).saveProductCatalogue(productCatalogueRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveProductCatalogue.getStatusCode());

	}
	@Test
	public void testForUpdateProductCatalogueInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(productCatalogueServiceInterface)
				.updateProductCatalogue(productCatalogueRequest);
		ResponseEntity<?> updateProductCatalogue = productCatalogueController
				.updateProductCatalogue(productCatalogueRequest);

		verify(productCatalogueServiceInterface).updateProductCatalogue(productCatalogueRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateProductCatalogue.getStatusCode());

	}
	@Test
	public void testForProductCatalogueInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(productCatalogueServiceInterface)
				.deleteProductCatalogue(productCatalogueRequest);;
		ResponseEntity<?> deleteProductCatalogue = productCatalogueController
				.deleteProductCatalogue(productCatalogueRequest);

		verify(productCatalogueServiceInterface).deleteProductCatalogue(productCatalogueRequest);

		assertEquals(HttpStatus.BAD_REQUEST, deleteProductCatalogue.getStatusCode());

	}
}
