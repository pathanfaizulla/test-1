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
import com.insignia.entity.ProductColour;
import com.insignia.model.ProductColourRequest;
import com.insignia.model.ProductColourResponse;
import com.insignia.serviceInterface.ProductColourServiceInterface;

@ExtendWith(MockitoExtension.class)
public class TestProductColourController {

	@InjectMocks
	private ProductColourController productColourController;

	@Mock
	private ProductColourServiceInterface productColourServiceInterface;

	ProductColourRequest productColourRequest = new ProductColourRequest();
	ProductColourResponse productColourResponse = new ProductColourResponse();

	List<ProductColourResponse> productColourResponseList = new ArrayList<>();
	ProductColour productColour = new ProductColour();

	public void dataInitilization() {

		productColourRequest.setCustomerSequenceNumber(5L);
		productColourRequest.setExpirationDuration(15);
		productColourRequest.setColourName("pink");
		productColourRequest.setColourDescription("for bench");
		productColourRequest.setApplicationId("insignia");
		productColourRequest.setTenantId("LU008");
		productColourRequest.setHexCode("#1AFFa1");

		productColourResponse.setSequenceNumber(5);
		productColourResponse.setColourName("pink");
		productColourResponse.setColourDescription("for bench");
		productColourResponse.setHexCode("#1AFFa1");

		productColourResponseList.add(productColourResponse);

		productColour.setSequenceNumber(5);
		productColour.setColourName("pink");
		productColour.setColourDescription("for bench");
		productColour.setApplicationId("insignia");
		productColour.setTenantId("LU008");

	}

	@Test
	public void testSaveProductColour() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		when(productColourServiceInterface.saveProductColour(productColourRequest)).thenReturn(productColourResponse);
		ResponseEntity<?> response = productColourController.saveProductColour(productColourRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testDeleteProductColour() throws Exception {

		dataInitilization();
		doNothing().when(productColourServiceInterface).deleteProductColour(productColourRequest);

		productColourController.deleteProductColour(productColourRequest);
		verify(productColourServiceInterface, times(1)).deleteProductColour(productColourRequest);
	}

	@Test
	public void testGetAllProductColour() throws TokenExpiredException, InvalidInputParametersException {

		dataInitilization();

		when(productColourServiceInterface.getAllProductColour(productColourRequest))
				.thenReturn(productColourResponseList);
		ResponseEntity<?> response = productColourController.getAllProductColours(productColourRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testUpdateProductColour() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();
		productColourRequest.setSequenceNumber(5);

		when(productColourServiceInterface.updateProductColour(productColourRequest))
				.thenReturn(productColourResponse);
		ResponseEntity<?> response = productColourController.updateProductColour(productColourRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testForTokenExpired() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		when(productColourServiceInterface.saveProductColour(productColourRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> saveProductColour = productColourController.saveProductColour(productColourRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveProductColour.getStatusCode());

		doThrow(new TokenExpiredException("")).when(productColourServiceInterface)
				.deleteProductColour(productColourRequest);;
		ResponseEntity<?> deleteProductColour = productColourController
				.deleteProductColour(productColourRequest);
		assertEquals(HttpStatus.BAD_REQUEST, deleteProductColour.getStatusCode());

		when(productColourServiceInterface.getAllProductColour(productColourRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getAllProductColours = productColourController
				.getAllProductColours(productColourRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getAllProductColours.getStatusCode());

		when(productColourServiceInterface.updateProductColour(productColourRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> updateProductColour = productColourController
				.updateProductColour(productColourRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateProductColour.getStatusCode());

	}
	
	@Test
	public void testForException() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		when(productColourServiceInterface.saveProductColour(productColourRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> saveProductColour = productColourController.saveProductColour(productColourRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveProductColour.getStatusCode());

		doThrow(new NullPointerException("")).when(productColourServiceInterface)
				.deleteProductColour(productColourRequest);;
		ResponseEntity<?> deleteProductColour = productColourController
				.deleteProductColour(productColourRequest);
		assertEquals(HttpStatus.BAD_REQUEST, deleteProductColour.getStatusCode());

		when(productColourServiceInterface.getAllProductColour(productColourRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> getAllProductColours = productColourController
				.getAllProductColours(productColourRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getAllProductColours.getStatusCode());

		when(productColourServiceInterface.updateProductColour(productColourRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> updateProductColour = productColourController
				.updateProductColour(productColourRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateProductColour.getStatusCode());

	}

	
	@Test
	public void testForSaveProductColourInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(productColourServiceInterface)
				.saveProductColour(productColourRequest);

		ResponseEntity<?> saveProductColour = productColourController
				.saveProductColour(productColourRequest);

		verify(productColourServiceInterface).saveProductColour(productColourRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveProductColour.getStatusCode());

	}
	@Test
	public void testForUpdateProductColourInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(productColourServiceInterface)
				.updateProductColour(productColourRequest);
		ResponseEntity<?> updateProductColour = productColourController
				.updateProductColour(productColourRequest);

		verify(productColourServiceInterface).updateProductColour(productColourRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateProductColour.getStatusCode());

	}
	@Test
	public void testForProductColourInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(productColourServiceInterface)
				.deleteProductColour(productColourRequest);;
		ResponseEntity<?> deleteProductColour = productColourController
				.deleteProductColour(productColourRequest);

		verify(productColourServiceInterface).deleteProductColour(productColourRequest);

		assertEquals(HttpStatus.BAD_REQUEST, deleteProductColour.getStatusCode());

	}
}
