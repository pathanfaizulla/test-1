package com.insignia.serviceInterface;

import java.util.List;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.ProductBrandRequest;
import com.insignia.model.ProductBrandResponse;

public interface ProductBrandServiceInterface {

	public ProductBrandResponse saveProductBrand(ProductBrandRequest productBrandRequest)
			throws InvalidInputParametersException, TokenExpiredException;

	public ProductBrandResponse updateProductBrand(ProductBrandRequest productBrandRequest)
			throws InvalidInputParametersException, TokenExpiredException;

	public void deleteProductBrand(ProductBrandRequest productBrandRequest)
			throws InvalidInputParametersException, TokenExpiredException;

	public List<ProductBrandResponse> getAllProductBrand(ProductBrandRequest productBrandRequest)
			throws TokenExpiredException;

}
