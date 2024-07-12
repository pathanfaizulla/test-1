package com.insignia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.insignia.constants.OrderValidatorConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.OrderRequest;
import com.insignia.model.OrderResponse;
import com.insignia.service.OrderServiceInterface;

@ExtendWith(MockitoExtension.class)
public class TestOrderController {

	@InjectMocks
	private OrderController orderController;

	@Mock
	private OrderServiceInterface orderServiceRepo;

	@Mock
	private OrderValidatorConstant orderValidatorConstant;

	List<OrderResponse> orderResponseList = new ArrayList<>();
	OrderRequest orderRequest = new OrderRequest();
	OrderResponse orderResponse = new OrderResponse();

	public void dataInitilization() {
		orderRequest.setCustomerSequenceNumber(5L);
		orderRequest.setExpirationDuration(15);
		orderRequest.setOrderSequenceNumber(5L);

		orderRequest.setOrderStatus("CA");
		orderRequest.setOrderId(5L);
		orderRequest.setInvoiceId("123");
		orderRequest.setOrderDate(new Date());
		orderRequest.setCustomerSequenceNumber(5L);
		orderRequest.setAddressSequenceNumber(1L);

		orderResponse.setOrderSequenceNumber(5L);
		orderResponse.setOrderStatus("CA");
		orderResponse.setOrderId(5L);
		orderResponse.setInvoiceId("123");
		orderResponse.setCustomerSequenceNumber(5L);
		orderResponse.setAddressSequenceNumber(1L);

		orderResponseList.add(orderResponse);

	}

	public OrderResponse getOrderResponce() {

		orderResponse.setOrderSequenceNumber(5L);
		orderResponse.setOrderStatus("CA");
		orderResponse.setOrderId(5L);
		orderResponse.setInvoiceId("123");
		orderResponse.setCustomerSequenceNumber(5L);
		orderResponse.setAddressSequenceNumber(1L);
		return orderResponse;
	}

	@Test
	public void testCreateOrder() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		when(orderServiceRepo.createOrder(orderRequest)).thenReturn(orderResponse);
		ResponseEntity<?> response = orderController.createOrder(orderRequest);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testUpdateOrder() throws InvalidInputParametersException, TokenExpiredException {
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setOrderStatus("PE");

		when(orderServiceRepo.updateOrder(orderRequest)).thenReturn(orderResponse);
		ResponseEntity<?> response = orderController.updateOrder(orderRequest);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testDeleteByOrderSequenceNumber() throws Exception {

		Long orderSequenceNumber = 5L;
		Long customerSequenceNumber = 5L;
		Integer expirationDuration = 15;

		doNothing().when(orderServiceRepo).deleteOrder(orderSequenceNumber, customerSequenceNumber, expirationDuration);

		orderController.deleteByOrderSequenceNumber(orderSequenceNumber, customerSequenceNumber, expirationDuration);
		verify(orderServiceRepo, times(1)).deleteOrder(orderSequenceNumber, customerSequenceNumber, expirationDuration);

	}

	@Test
	public void testGetAllOrderForCustomer() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		Long customerSequenceNumber = 5L;
		Integer expirationDuration = 15;

		when(orderServiceRepo.getOrders(customerSequenceNumber, expirationDuration, false))
				.thenReturn(orderResponseList);
		ResponseEntity<?> response = orderController.getAllOrderForcustomer(customerSequenceNumber, expirationDuration);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testGetAllOrders() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		Long customerSequenceNumber = 5L;
		Integer expirationDuration = 15;

		when(orderServiceRepo.getOrders(customerSequenceNumber, expirationDuration, true))
				.thenReturn(orderResponseList);
		ResponseEntity<?> response = orderController.getAllOrders(customerSequenceNumber, expirationDuration);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testGetAllOrderByOrderSequenceNumber() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();
		getOrderResponce();
		Long orderSequenceNumber = 5L;
		Long customerSequenceNumber = 5L;
		Integer expirationDuration = 15;

		Optional<OrderResponse> optional = Optional.ofNullable(orderResponse);

		when(orderServiceRepo.getOrderById(orderSequenceNumber, customerSequenceNumber, expirationDuration))
				.thenReturn(optional);
		ResponseEntity<?> response = orderController.getAllOrderByOrderSequenceNumber(orderSequenceNumber,
				customerSequenceNumber, expirationDuration);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testForTokenExpired() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();
		Long orderSequenceNumber = 5L;
		Long customerSequenceNumber = 5L;
		Integer expirationDuration = 15;

		when(orderServiceRepo.createOrder(orderRequest)).thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> createOrder = orderController.createOrder(orderRequest);

		assertEquals(HttpStatus.BAD_REQUEST, createOrder.getStatusCode());

		doThrow(new TokenExpiredException("")).when(orderServiceRepo).deleteOrder(orderSequenceNumber,
				customerSequenceNumber, expirationDuration);
		ResponseEntity<?> deleteByOrderSequenceNumber = orderController.deleteByOrderSequenceNumber(orderSequenceNumber,
				customerSequenceNumber, expirationDuration);
		assertEquals(HttpStatus.BAD_REQUEST, deleteByOrderSequenceNumber.getStatusCode());

		when(orderServiceRepo.getOrders(customerSequenceNumber, expirationDuration, false))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getAllOrderBycustomer = orderController.getAllOrderForcustomer(customerSequenceNumber,
				expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, getAllOrderBycustomer.getStatusCode());

		when(orderServiceRepo.getOrders(customerSequenceNumber, expirationDuration, true))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getAllOrders = orderController.getAllOrders(customerSequenceNumber, expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, getAllOrders.getStatusCode());

		when(orderServiceRepo.getOrderById(orderSequenceNumber, customerSequenceNumber, expirationDuration))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getAllOrderByOrderSequenceNumber = orderController
				.getAllOrderByOrderSequenceNumber(orderSequenceNumber, customerSequenceNumber, expirationDuration);
		assertEquals(HttpStatus.BAD_REQUEST, getAllOrderByOrderSequenceNumber.getStatusCode());

	}

	@Test
	public void testForTokenExpirationForUpdateMethod() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setOrderStatus("PE");

		when(orderServiceRepo.updateOrder(orderRequest)).thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> updateOrder = orderController.updateOrder(orderRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateOrder.getStatusCode());
	}

	@Test
	public void testForException() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();
		Long orderSequenceNumber = 5L;
		Long customerSequenceNumber = 5L;
		Integer expirationDuration = 15;

		when(orderServiceRepo.createOrder(orderRequest)).thenThrow(new NullPointerException(""));
		ResponseEntity<?> createOrder = orderController.createOrder(orderRequest);

		assertEquals(HttpStatus.BAD_REQUEST, createOrder.getStatusCode());

		doThrow(new NullPointerException("")).when(orderServiceRepo).deleteOrder(orderSequenceNumber,
				orderSequenceNumber, expirationDuration);
		ResponseEntity<?> deleteByOrderSequenceNumber = orderController.deleteByOrderSequenceNumber(orderSequenceNumber,
				orderSequenceNumber, expirationDuration);
		assertEquals(HttpStatus.BAD_REQUEST, deleteByOrderSequenceNumber.getStatusCode());

		when(orderServiceRepo.getOrders(customerSequenceNumber, expirationDuration, false))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> getAllOrderBycustomer = orderController.getAllOrderForcustomer(customerSequenceNumber,
				expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, getAllOrderBycustomer.getStatusCode());

		when(orderServiceRepo.getOrders(customerSequenceNumber, expirationDuration, true))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> getAllOrders = orderController.getAllOrders(customerSequenceNumber, expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, getAllOrders.getStatusCode());

		when(orderServiceRepo.getOrderById(orderSequenceNumber, orderSequenceNumber, expirationDuration))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> getAllOrderByOrderSequenceNumber = orderController
				.getAllOrderByOrderSequenceNumber(orderSequenceNumber, orderSequenceNumber, expirationDuration);
		assertEquals(HttpStatus.BAD_REQUEST, getAllOrderByOrderSequenceNumber.getStatusCode());

	}

	@Test
	public void testForExceptionForUpdateMethod() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setOrderStatus("PE");

		when(orderServiceRepo.updateOrder(orderRequest)).thenThrow(new NullPointerException(""));
		ResponseEntity<?> updateOrder = orderController.updateOrder(orderRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateOrder.getStatusCode());
	}

	@Test
	public void testValidateOrderStatusEnum_InvalidStatus() throws NoSuchMethodException {

		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setOrderStatus("available"); // Set an invalid status

		Method validateOrderStatusEnumMethod = OrderController.class.getDeclaredMethod("validateOrderStatusEnum",
				OrderRequest.class);
		validateOrderStatusEnumMethod.setAccessible(true);

		Throwable thrown = assertThrows(InvocationTargetException.class,
				() -> validateOrderStatusEnumMethod.invoke(orderController, orderRequest));

		Throwable actualException = thrown.getCause();

		assertTrue(actualException instanceof InvalidInputParametersException);

		InvalidInputParametersException exception = (InvalidInputParametersException) actualException;

		assertEquals(OrderValidatorConstant.validateOrderStatusEnumErrorCode, exception.getErrorCode());
		assertEquals(OrderValidatorConstant.validateOrderStatusEnumMessage, exception.getStrMsg());
	}

	@Test
	public void testValidateUpdateMethodNotAllowedParameters() throws NoSuchMethodException {

		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setOrderId(3L); // Set an invalid status

		Method validateUpdateMethodNotAllowedParameters = OrderController.class
				.getDeclaredMethod("validateUpdateMethodNotAllowedParameters", OrderRequest.class);
		validateUpdateMethodNotAllowedParameters.setAccessible(true);

		Throwable thrown = assertThrows(InvocationTargetException.class,
				() -> validateUpdateMethodNotAllowedParameters.invoke(orderController, orderRequest));

		Throwable actualException = thrown.getCause();

		assertTrue(actualException instanceof InvalidInputParametersException);

		InvalidInputParametersException exception = (InvalidInputParametersException) actualException;

		assertEquals(OrderValidatorConstant.validateUpdateMethodErrorCode, exception.getErrorCode());
		assertEquals(OrderValidatorConstant.validateUpdateMethodMessage, exception.getStrMsg());
	}

	@Test
	public void testForInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		Long orderSequenceNumber = 5L;
		Long customerSequenceNumber = 5L;
		Integer expirationDuration = 15;

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(orderServiceRepo)
				.createOrder(orderRequest);

		ResponseEntity<?> createOrder = orderController.createOrder(orderRequest);
		assertEquals(HttpStatus.BAD_REQUEST, createOrder.getStatusCode());

		ResponseEntity<?> updateOrder = orderController.updateOrder(orderRequest);
		assertEquals(HttpStatus.BAD_REQUEST, updateOrder.getStatusCode());

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(orderServiceRepo)
				.deleteOrder(orderSequenceNumber, orderSequenceNumber, expirationDuration);

		ResponseEntity<?> deleteByOrderSequenceNumber = orderController.deleteByOrderSequenceNumber(orderSequenceNumber,
				customerSequenceNumber, expirationDuration);
		assertEquals(HttpStatus.BAD_REQUEST, deleteByOrderSequenceNumber.getStatusCode());
	}

}
