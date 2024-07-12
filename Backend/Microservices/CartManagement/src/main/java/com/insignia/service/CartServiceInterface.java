package com.insignia.service;

import java.util.List;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.CartRequest;
import com.insignia.model.CartResponce;
import com.insignia.model.CustomerCartRequest;
import com.insignia.model.CustomerCartResponse;

public interface CartServiceInterface {

	public CartResponce saveCart(CartRequest cartRequest) throws InvalidInputParametersException, TokenExpiredException;

	public void deleteCartForCustomer(Long cartSequenceNumber, Long customerSequenceNumber, Integer expirationDuration)
			throws TokenExpiredException;

	public CustomerCartResponse getCustomerCartInformation(Long customerSequenceNumber, Integer expirationDuration)
			throws TokenExpiredException, InvalidInputParametersException;

	public CustomerCartResponse addProductToCart(CustomerCartRequest customerCartRequest)
			throws TokenExpiredException, InvalidInputParametersException;

	public CustomerCartResponse updateCustomerCartProduct(CustomerCartRequest customerCartRequest)
			throws InvalidInputParametersException, TokenExpiredException;

	public void deleteProductToCart(Long customerCartSequenceNumber, Long customerSequenceNumber,
			Integer expirationDuration) throws TokenExpiredException;

	public List<CustomerCartResponse>getProductByCustomer(Long cartSequenceNumber, Long customerSequenceNumber, Integer expirationDuration) throws TokenExpiredException;

}
