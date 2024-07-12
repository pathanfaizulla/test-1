package com.insignia.service;

import java.util.List;
import java.util.Optional;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.OrderRequest;
import com.insignia.model.OrderResponse;

public interface OrderServiceInterface {

	public OrderResponse createOrder(OrderRequest orderRequest)
			throws InvalidInputParametersException, TokenExpiredException;

	public OrderResponse updateOrder(OrderRequest orderRequest)
			throws InvalidInputParametersException, TokenExpiredException;

	public void deleteOrder(Long orderSequenceNumber, Long customerSequenceNumber, Integer expirationDuration)
			throws InvalidInputParametersException, TokenExpiredException;

	public List<OrderResponse> getOrders(Long customerSequenceNumber, Integer expirationDuration, boolean isToGetAll)
			throws InvalidInputParametersException, TokenExpiredException;

	
	public Optional<OrderResponse> getOrderById(Long orderSequenceNumber, Long customerSequenceNumber,
			Integer expirationDuration) throws InvalidInputParametersException, TokenExpiredException;
}
