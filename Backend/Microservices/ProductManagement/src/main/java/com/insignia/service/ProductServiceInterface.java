package com.insignia.service;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.ProductFilterResponse;
import com.insignia.model.ProductManagementRequest;
import com.insignia.model.ProductResponse;

import java.util.List;
import java.util.Optional;

public interface ProductServiceInterface {
	ProductResponse addProduct(ProductManagementRequest productManagementRequest)
			throws InvalidInputParametersException, TokenExpiredException;

	ProductResponse updateProduct(ProductManagementRequest productManagementRequest)
			throws InvalidInputParametersException, TokenExpiredException;

	void deleteProductById(ProductManagementRequest productManagementRequest) throws InvalidInputParametersException, TokenExpiredException;

	Optional<ProductResponse> getProductById(ProductManagementRequest productManagementRequest) throws InvalidInputParametersException, TokenExpiredException;

	ProductResponse getAllProducts(Long customerSequenceNumber, Integer expirationDuration)
			throws TokenExpiredException, InvalidInputParametersException;

	ProductResponse filterProduct(ProductManagementRequest productManagementRequest) throws TokenExpiredException;

	List<ProductFilterResponse> getProductFilters(ProductManagementRequest productManagementRequest)
			throws TokenExpiredException;
	
	List<ProductResponse> getProductDetailsById(ProductManagementRequest productManagementRequest) throws InvalidInputParametersException, TokenExpiredException;

}
