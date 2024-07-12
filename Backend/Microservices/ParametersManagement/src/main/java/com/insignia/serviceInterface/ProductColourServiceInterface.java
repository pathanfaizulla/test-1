package com.insignia.serviceInterface;

import java.util.List;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.ProductColourRequest;
import com.insignia.model.ProductColourResponse;

public interface ProductColourServiceInterface {

	public ProductColourResponse saveProductColour(ProductColourRequest productColourRequest) throws InvalidInputParametersException, TokenExpiredException;

	public ProductColourResponse updateProductColour(ProductColourRequest productColourRequest) throws InvalidInputParametersException, TokenExpiredException;

	public void deleteProductColour(ProductColourRequest productColourRequest) throws InvalidInputParametersException, TokenExpiredException;

	public List<ProductColourResponse> getAllProductColour(ProductColourRequest productColourRequest) throws TokenExpiredException;

}
