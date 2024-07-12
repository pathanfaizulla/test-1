package com.insignia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.ProductDetailsResponse;
import com.insignia.model.ProductFilterCriteria;
import com.insignia.model.ProductManagementRequest;
import com.insignia.model.ProductRequest;
import com.insignia.model.ProductResponse;
import com.insignia.service.ProductServiceInterface;

@ExtendWith(MockitoExtension.class)
public class TestProductController {

	@InjectMocks
	private ProductController productController;

	@Mock
	private ProductServiceInterface productServiceRepo;

	ProductManagementRequest productManagementRequest = new ProductManagementRequest();

	ProductRequest productRequest = new ProductRequest();
	ProductResponse productResponse = new ProductResponse();

	ProductFilterCriteria filterCriteria = new ProductFilterCriteria();
	ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();

	public void dataInitilization() {     

		productManagementRequest.setCustomerSequenceNumber(5L);
		productManagementRequest.setExpirationDuration(15);

		productRequest.setProductSequenceNumber(5L);
		productRequest.setProductId("5L");
		productRequest.setProductName("mobile");
		productRequest.setDescription("Very smart Mobile");
		productRequest.setMeasuringQuantity("2");
		productRequest.setMeasuringUnit("5");
		productRequest.setSubcategoryId(5L);
		productRequest.setProductImagePath("Mobile");
		productRequest.setProductPerUnitActualPrice(BigDecimal.valueOf(5025627.25));
		productRequest.setProductPerUnitCurrentPrice(BigDecimal.valueOf(50.25));
		productRequest.setProductFinish("Wood");
		productRequest.setProdWholesaleTier1Price(BigDecimal.valueOf(10.25));
		productRequest.setProdWholesaleTier2Price(BigDecimal.valueOf(20.25));
		productRequest.setProdWholesaleTier1SalePrice(BigDecimal.valueOf(30.25));
		productRequest.setProdWholesaleTier2SalePrice(BigDecimal.valueOf(40.25));
		productRequest.setProdRetailTier1Price(BigDecimal.valueOf(50.25));
		productRequest.setProdRetailTier2Price(BigDecimal.valueOf(60.25));
		productRequest.setProdRetailTier1SalePrice(BigDecimal.valueOf(70.25));
		productRequest.setProdRetailTier2SalePrice(BigDecimal.valueOf(80.25));
		productRequest.setApplicationId("insignia");
		productRequest.setTenantId("LU008");
		productRequest.setSomeOfAKind(true);
		productRequest.setCloseOut(true);
		productRequest.setEta("abc");
		productRequest.setOneOfKind(true);
		productRequest.setSaleItems(true);
		productRequest.setQuickShip(true);
		productRequest.setColours("Red");
		productRequest.setHexCode("#FF0000");

		filterCriteria.setApplicationId("hinges-design");
		filterCriteria.setTenantId("LU008");

		productManagementRequest.setProductRequest(productRequest);
		productManagementRequest.setFilterCriteria(filterCriteria);
		productDetailsResponse.setProductSequenceNumber(5L);
		productDetailsResponse.setProductId("5L");
		productDetailsResponse.setProductName("mobile");
		productDetailsResponse.setDescription("Very smart Mobile");
		productDetailsResponse.setMeasuringQuantity("2");
		productDetailsResponse.setMeasuringUnit("5");
		productDetailsResponse.setSubcategoryId(5L);
		productDetailsResponse.setProductImagePath("Mobile");
		productDetailsResponse.setSomeOfAKind(true);
		productDetailsResponse.setCloseOut(true);
		productDetailsResponse.setEta("abc");
		productDetailsResponse.setOneOfKind(true);
		productDetailsResponse.setSaleItems(true);
		productDetailsResponse.setQuickShip(true);

	}

	public ProductResponse getProductResponse() {
		productDetailsResponse.setProductSequenceNumber(5L);
		productDetailsResponse.setProductId("5L");
		productDetailsResponse.setProductName("mobile");
		productDetailsResponse.setDescription("Very smart Mobile");
		productDetailsResponse.setMeasuringQuantity("2");
		productDetailsResponse.setMeasuringUnit("5");
		productDetailsResponse.setSubcategoryId(5L);
		productDetailsResponse.setProductImagePath("Mobile");
		productDetailsResponse.setSomeOfAKind(true);
		productDetailsResponse.setCloseOut(true);
		productDetailsResponse.setEta("abc");
		productDetailsResponse.setOneOfKind(true);
		productDetailsResponse.setSaleItems(true);
		productDetailsResponse.setQuickShip(true);
		productDetailsResponse.setColours("Red");
		productDetailsResponse.setHexCode("#FF0000");
		productResponse.setProductDetailsResponse(Collections.singletonList(productDetailsResponse));

		return productResponse;

	}

	@Test
	public void testAddProduct() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		when(productServiceRepo.addProduct(productManagementRequest)).thenReturn(productResponse);
		ResponseEntity<?> response = productController.addProduct(productManagementRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testUpdateProduct() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		when(productServiceRepo.updateProduct(productManagementRequest)).thenReturn(productResponse);
		ResponseEntity<?> response = productController.updateProduct(productManagementRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testDeleteByproductId() throws Exception {

		dataInitilization();

		doNothing().when(productServiceRepo).deleteProductById(productManagementRequest);

		productController.deleteByProductId(productManagementRequest);
		verify(productServiceRepo, times(1)).deleteProductById(productManagementRequest);

	}	

	@Test
	public void testGetproductById() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();
		getProductResponse();

		Optional<ProductResponse> optionalResponse = Optional.ofNullable(productResponse);

		when(productServiceRepo.getProductById(productManagementRequest)).thenReturn(optionalResponse);
		ResponseEntity<?> response = productController.getProductById(productManagementRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testGetProductDetailsById() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();
		getProductResponse();

		List<ProductResponse> listResponse = new ArrayList<>();

		when(productServiceRepo.getProductDetailsById(productManagementRequest)).thenReturn(listResponse);
		ResponseEntity<?> response = productController.getProductDetailsById(productManagementRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testGetAllProducts() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();
		getProductResponse();

		Long customerSequenceNumber = 5L;
		Integer expirationDuration = 15;

		when(productServiceRepo.getAllProducts(customerSequenceNumber, expirationDuration)).thenReturn(productResponse);
		ResponseEntity<?> response = productController.getAllProducts(customerSequenceNumber, expirationDuration);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testFilterCriteria() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();
		getProductResponse();

		// when(productServiceRepo.filterProduct(productManagementRequest)).thenReturn(productResponseList);
		ResponseEntity<?> filterCriteria = productController.filterProduct(productManagementRequest);
		assertEquals(HttpStatus.OK, filterCriteria.getStatusCode());
	}

	@Test
	public void testForTokenExpired() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();
		Long customerSequenceNumber = 5L;
		Integer expirationDuration = 15;

		when(productServiceRepo.addProduct(productManagementRequest)).thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> addProduct = productController.addProduct(productManagementRequest);

		assertEquals(HttpStatus.BAD_REQUEST, addProduct.getStatusCode());

		when(productServiceRepo.getProductFilters(productManagementRequest)).thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getProductFilters = productController.getProductFilters(productManagementRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getProductFilters.getStatusCode());

		when(productServiceRepo.updateProduct(productManagementRequest)).thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> updateProduct = productController.updateProduct(productManagementRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateProduct.getStatusCode());

		doThrow(new TokenExpiredException("")).when(productServiceRepo).deleteProductById(productManagementRequest);
		ResponseEntity<?> deleteByproductId = productController.deleteByProductId(productManagementRequest);

		assertEquals(HttpStatus.BAD_REQUEST, deleteByproductId.getStatusCode());

		when(productServiceRepo.getProductById(productManagementRequest)).thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getproductById = productController.getProductById(productManagementRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getproductById.getStatusCode());

		when(productServiceRepo.getProductDetailsById(productManagementRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getProductDetailsById = productController.getProductDetailsById(productManagementRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getProductDetailsById.getStatusCode());

		when(productServiceRepo.getAllProducts(customerSequenceNumber, expirationDuration))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getAllProducts = productController.getAllProducts(customerSequenceNumber, expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, getAllProducts.getStatusCode());

		when(productServiceRepo.filterProduct(productManagementRequest)).thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> filterCriteria = productController.filterProduct(productManagementRequest);

		assertEquals(HttpStatus.BAD_REQUEST, filterCriteria.getStatusCode());

	}

	@Test
	public void testForException() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();
		Long customerSequenceNumber = 5L;
		Integer expirationDuration = 15;

		when(productServiceRepo.addProduct(productManagementRequest)).thenThrow(new NullPointerException(""));
		ResponseEntity<?> addProduct = productController.addProduct(productManagementRequest);

		assertEquals(HttpStatus.BAD_REQUEST, addProduct.getStatusCode());

		when(productServiceRepo.updateProduct(productManagementRequest)).thenThrow(new NullPointerException(""));
		ResponseEntity<?> updateProduct = productController.updateProduct(productManagementRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateProduct.getStatusCode());

		doThrow(new NullPointerException("")).when(productServiceRepo).deleteProductById(productManagementRequest);
		ResponseEntity<?> deleteByproductId = productController.deleteByProductId(productManagementRequest);

		assertEquals(HttpStatus.BAD_REQUEST, deleteByproductId.getStatusCode());

		when(productServiceRepo.getProductById(productManagementRequest)).thenThrow(new NullPointerException(""));
		ResponseEntity<?> getProductById = productController.getProductById(productManagementRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getProductById.getStatusCode());

		when(productServiceRepo.getProductDetailsById(productManagementRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> getProductDetailsById = productController.getProductDetailsById(productManagementRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getProductDetailsById.getStatusCode());

		when(productServiceRepo.getAllProducts(customerSequenceNumber, expirationDuration))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> getAllProducts = productController.getAllProducts(customerSequenceNumber, expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, getAllProducts.getStatusCode());

		when(productServiceRepo.filterProduct(productManagementRequest)).thenThrow(new NullPointerException(""));
		ResponseEntity<?> filterCriteria = productController.filterProduct(productManagementRequest);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, filterCriteria.getStatusCode());

		when(productServiceRepo.getProductFilters(productManagementRequest)).thenThrow(new NullPointerException(""));
		ResponseEntity<?> productFilters = productController.getProductFilters(productManagementRequest);

		assertEquals(HttpStatus.BAD_REQUEST, productFilters.getStatusCode());

	}

	@Test
	public void testForInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(productServiceRepo)
				.addProduct(productManagementRequest);

		ResponseEntity<?> response = productController.addProduct(productManagementRequest);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(productServiceRepo)
				.updateProduct(productManagementRequest);

		ResponseEntity<?> updateProduct = productController.updateProduct(productManagementRequest);
		assertEquals(HttpStatus.BAD_REQUEST, updateProduct.getStatusCode());

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(productServiceRepo)
				.deleteProductById(productManagementRequest);

		ResponseEntity<?> deleteByProductId = productController.deleteByProductId(productManagementRequest);
		assertEquals(HttpStatus.BAD_REQUEST, deleteByProductId.getStatusCode());

	}

	@Test
	void testForInvalidInputParametersExceptionForProductRequestDetailsNotFoundInAddProduct() {

		ProductManagementRequest request = new ProductManagementRequest();

		ResponseEntity<?> response = productController.addProduct(request);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	void testForInvalidInputParametersExceptionForProductRequestDetailsNotFoundInUpdateProduct()
			throws InvalidInputParametersException {

		ProductManagementRequest request = new ProductManagementRequest();

		ResponseEntity<?> response = productController.updateProduct(request);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	void testgetDetailsProducts() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		ResponseEntity<?> response = productController.getProductFilters(productManagementRequest);

		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	void testForInvalidInputParametersExceptionForProductRequestDetailsNotFoundInFilterProduct() {

		ProductManagementRequest request = new ProductManagementRequest();

		ResponseEntity<?> response = productController.filterProduct(request);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	void testForInvalidInputParametersExceptionForProductRequestDetailsNotFoundInGetProductById() {

		ProductManagementRequest request = new ProductManagementRequest();

		ResponseEntity<?> response = productController.getProductById(request);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	void testForInvalidInputParametersExceptionForProductRequestDetailsNotFoundInGetetProductDetailsById() {

		ProductManagementRequest request = new ProductManagementRequest();

		ResponseEntity<?> response = productController.getProductDetailsById(request);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	void testForInvalidInputParametersExceptionForProductRequestDetailsNotFoundIngetProductFilters() {

		ProductManagementRequest request = new ProductManagementRequest();

		ResponseEntity<?> response = productController.getProductFilters(request);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}
}
