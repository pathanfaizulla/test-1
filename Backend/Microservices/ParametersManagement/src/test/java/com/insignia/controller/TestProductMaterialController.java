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
import com.insignia.entity.ProductMaterial;
import com.insignia.model.ProductMaterialRequest;
import com.insignia.model.ProductMaterialResponse;

import com.insignia.serviceInterface.ProductMaterialServiceInterface;

@ExtendWith(MockitoExtension.class)
public class TestProductMaterialController {

	@InjectMocks
	private ProductMaterialController productMaterialController;

	@Mock
	private ProductMaterialServiceInterface productMaterialServiceInterface;

	ProductMaterialRequest productMaterialRequest = new ProductMaterialRequest();
	ProductMaterialResponse productMaterialResponse = new ProductMaterialResponse();

	List<ProductMaterialResponse> productMaterialResponseList = new ArrayList<>();
	ProductMaterial productMaterial = new ProductMaterial();

	public void dataInitilization() {

		productMaterialRequest.setCustomerSequenceNumber(5L);
		productMaterialRequest.setExpirationDuration(15);
		productMaterialRequest.setMaterialName("pink");
		productMaterialRequest.setMaterialDescription("for bench");
		productMaterialRequest.setApplicationId("insignia");
		productMaterialRequest.setTenantId("LU008");

		productMaterialResponse.setSequenceNumber(5);
		productMaterialResponse.setMaterialName("pink");
		productMaterialResponse.setMaterialDescription("for bench");

		productMaterialResponseList.add(productMaterialResponse);

		productMaterial.setSequenceNumber(5);
		productMaterial.setMaterialName("pink");
		productMaterial.setMaterialDescription("for bench");

	}

	@Test
	public void testSaveProductMaterial() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		when(productMaterialServiceInterface.saveProductMaterial(productMaterialRequest)).thenReturn(productMaterialResponse);
		ResponseEntity<?> response = productMaterialController.saveProductMaterial(productMaterialRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testDeleteProductMaterial() throws Exception {

		dataInitilization();
		doNothing().when(productMaterialServiceInterface).deleteProductMaterial(productMaterialRequest);

		productMaterialController.deleteProductMaterial(productMaterialRequest);
		verify(productMaterialServiceInterface, times(1)).deleteProductMaterial(productMaterialRequest);
	}

	@Test
	public void testGetAllProductMaterial() throws TokenExpiredException, InvalidInputParametersException {

		dataInitilization();

		when(productMaterialServiceInterface.getAllProductMaterials(productMaterialRequest))
				.thenReturn(productMaterialResponseList);
		ResponseEntity<?> response = productMaterialController.getAllProductMaterials(productMaterialRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testUpdateProductMaterial() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();
		productMaterialRequest.setSequenceNumber(5);

		when(productMaterialServiceInterface.updateProductMaterial(productMaterialRequest))
				.thenReturn(productMaterialResponse);
		ResponseEntity<?> response = productMaterialController.updateProductMaterial(productMaterialRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testForTokenExpired() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		when(productMaterialServiceInterface.saveProductMaterial(productMaterialRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> saveProductMaterial = productMaterialController.saveProductMaterial(productMaterialRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveProductMaterial.getStatusCode());

		doThrow(new TokenExpiredException("")).when(productMaterialServiceInterface)
				.deleteProductMaterial(productMaterialRequest);;
		ResponseEntity<?> deleteProductMaterial = productMaterialController
				.deleteProductMaterial(productMaterialRequest);
		assertEquals(HttpStatus.BAD_REQUEST, deleteProductMaterial.getStatusCode());

		when(productMaterialServiceInterface.getAllProductMaterials(productMaterialRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getAllProductMaterials = productMaterialController
				.getAllProductMaterials(productMaterialRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getAllProductMaterials.getStatusCode());

		when(productMaterialServiceInterface.updateProductMaterial(productMaterialRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> updateProductMaterial = productMaterialController
				.updateProductMaterial(productMaterialRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateProductMaterial.getStatusCode());

	}
	
	@Test
	public void testForException() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		when(productMaterialServiceInterface.saveProductMaterial(productMaterialRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> saveProductMaterial = productMaterialController.saveProductMaterial(productMaterialRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveProductMaterial.getStatusCode());

		doThrow(new NullPointerException("")).when(productMaterialServiceInterface)
				.deleteProductMaterial(productMaterialRequest);;
		ResponseEntity<?> deleteProductMaterial = productMaterialController
				.deleteProductMaterial(productMaterialRequest);
		assertEquals(HttpStatus.BAD_REQUEST, deleteProductMaterial.getStatusCode());

		when(productMaterialServiceInterface.getAllProductMaterials(productMaterialRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> getAllProductMaterials = productMaterialController
				.getAllProductMaterials(productMaterialRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getAllProductMaterials.getStatusCode());

		when(productMaterialServiceInterface.updateProductMaterial(productMaterialRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> updateProductMaterial = productMaterialController
				.updateProductMaterial(productMaterialRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateProductMaterial.getStatusCode());

	}

	
	@Test
	public void testForSaveProductMaterialInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(productMaterialServiceInterface)
				.saveProductMaterial(productMaterialRequest);

		ResponseEntity<?> saveProductMaterial = productMaterialController
				.saveProductMaterial(productMaterialRequest);

		verify(productMaterialServiceInterface).saveProductMaterial(productMaterialRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveProductMaterial.getStatusCode());

	}
	@Test
	public void testForUpdateProductMaterialInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(productMaterialServiceInterface)
				.updateProductMaterial(productMaterialRequest);
		ResponseEntity<?> updateProductMaterial = productMaterialController
				.updateProductMaterial(productMaterialRequest);

		verify(productMaterialServiceInterface).updateProductMaterial(productMaterialRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateProductMaterial.getStatusCode());

	}
	@Test
	public void testForProductMaterialInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(productMaterialServiceInterface)
				.deleteProductMaterial(productMaterialRequest);;
		ResponseEntity<?> deleteProductMaterial = productMaterialController
				.deleteProductMaterial(productMaterialRequest);

		verify(productMaterialServiceInterface).deleteProductMaterial(productMaterialRequest);

		assertEquals(HttpStatus.BAD_REQUEST, deleteProductMaterial.getStatusCode());

	}
}
