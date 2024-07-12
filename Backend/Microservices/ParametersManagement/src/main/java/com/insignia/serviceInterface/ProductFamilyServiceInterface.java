package com.insignia.serviceInterface;

import java.util.List;


import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.ProductFamilyRequest;
import com.insignia.model.ProductFamilyResponse;

public interface ProductFamilyServiceInterface {

	public ProductFamilyResponse saveProductFamily(ProductFamilyRequest productFamilyRequest) throws InvalidInputParametersException, TokenExpiredException;

	public ProductFamilyResponse updateProductFamily(ProductFamilyRequest productFamilyRequest) throws InvalidInputParametersException, TokenExpiredException;

	public void deleteProductFamily(ProductFamilyRequest productFamilyRequest) throws InvalidInputParametersException, TokenExpiredException;

	public List<ProductFamilyResponse> getAllProductFamily(ProductFamilyRequest productFamilyRequest) throws TokenExpiredException;

}
