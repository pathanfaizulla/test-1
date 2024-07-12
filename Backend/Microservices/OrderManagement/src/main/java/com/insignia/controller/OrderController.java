package com.insignia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.insignia.constant.OrderConstants;
import com.insignia.constants.CommonConstant;
import com.insignia.constants.OrderValidatorConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.entity.OrderStatus;

import com.insignia.model.OrderRequest;
import com.insignia.model.OrderResponse;
import com.insignia.service.OrderServiceInterface;
import com.insignia.validations.OrderValidator;

@CrossOrigin
@RestController
public class OrderController {

	@Autowired
	private OrderServiceInterface orderServiceInterface;

	@PostMapping("/createOrder")
	public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
		try {
			validationForOrderRequest(orderRequest);

			validateOrderStatusEnum(orderRequest);

			return ResponseEntity.ok(orderServiceInterface.createOrder(orderRequest));
		} catch (InvalidInputParametersException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new OrderResponse(e.getErrorCode(), e.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new OrderResponse(e.getErrorCode(), e.getStrMsg()));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new OrderResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	@PutMapping("/updateOrder")
	public ResponseEntity<?> updateOrder(@RequestBody OrderRequest orderRequest)
			throws InvalidInputParametersException {
		try {

			validateUpdateMethodNotAllowedParameters(orderRequest);
			OrderValidator.validateOrderStatus(orderRequest.getOrderStatus(), OrderConstants.orderStatusLength);
			validateOrderStatusEnum(orderRequest);

			return ResponseEntity.ok(orderServiceInterface.updateOrder(orderRequest));
		} catch (InvalidInputParametersException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new OrderResponse(e.getErrorCode(), e.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new OrderResponse(e.getErrorCode(), e.getStrMsg()));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new OrderResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@DeleteMapping("/deleteByOrderSequenceNumber/{orderSequenceNumber}/{customerSequenceNumber}/{expirationDuration}")
	public ResponseEntity<?> deleteByOrderSequenceNumber(@PathVariable Long orderSequenceNumber,
			@PathVariable Long customerSequenceNumber, @PathVariable Integer expirationDuration) {
		try {
			orderServiceInterface.deleteOrder(orderSequenceNumber, customerSequenceNumber, expirationDuration);
			return ResponseEntity.ok(OrderConstants.successMessage);
		} catch (InvalidInputParametersException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new OrderResponse(e.getErrorCode(), e.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new OrderResponse(e.getErrorCode(), e.getStrMsg()));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new OrderResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	@GetMapping("/getAllOrderForCustomer/{customerSequenceNumber}/{expirationDuration}")
	public ResponseEntity<?> getAllOrderForcustomer(@PathVariable Long customerSequenceNumber,
			@PathVariable Integer expirationDuration) {
		try {
			return ResponseEntity
					.ok(orderServiceInterface.getOrders(customerSequenceNumber, expirationDuration, false));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new OrderResponse(e.getErrorCode(), e.getStrMsg()));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new OrderResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	@GetMapping("/getAllOrders/{customerSequenceNumber}/{expirationDuration}")
	public ResponseEntity<?> getAllOrders(@PathVariable Long customerSequenceNumber,
			@PathVariable Integer expirationDuration) {
		try {
			return ResponseEntity
					.ok(orderServiceInterface.getOrders(customerSequenceNumber, expirationDuration, true));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new OrderResponse(e.getErrorCode(), e.getStrMsg()));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new OrderResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	@GetMapping("/getAllOrderByOrderSequenceNumber/{orderSequenceNumber}/{customerSequenceNumber}/{expirationDuration}")
	public ResponseEntity<?> getAllOrderByOrderSequenceNumber(@PathVariable Long orderSequenceNumber,
			@PathVariable Long customerSequenceNumber, @PathVariable Integer expirationDuration)
			throws InvalidInputParametersException, TokenExpiredException {
		try {
			return ResponseEntity.ok(orderServiceInterface.getOrderById(orderSequenceNumber, customerSequenceNumber,
					expirationDuration));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new OrderResponse(e.getErrorCode(), e.getStrMsg()));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new OrderResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	private void validationForOrderRequest(OrderRequest orderRequest) throws InvalidInputParametersException {
		OrderValidator.validateOrderId(orderRequest.getOrderId(), OrderConstants.orderId);
		OrderValidator.validateOrderStatus(orderRequest.getOrderStatus(), OrderConstants.orderStatusLength);
		OrderValidator.validInvoiceId(orderRequest.getInvoiceId(), OrderConstants.orderInvoiceId);
	}

	private void validateOrderStatusEnum(OrderRequest orderRequest) throws InvalidInputParametersException {
		String requestedStatus = orderRequest.getOrderStatus();

		if (!(OrderStatus.PE.name().equals(requestedStatus) || OrderStatus.SH.name().equals(requestedStatus)
				|| OrderStatus.CA.name().equals(requestedStatus) || OrderStatus.UP.name().equals(requestedStatus)
				|| OrderStatus.DE.name().equals(requestedStatus) || OrderStatus.PL.name().equals(requestedStatus))) {
			throw new InvalidInputParametersException(OrderValidatorConstant.validateOrderStatusEnumErrorCode,
					OrderValidatorConstant.validateOrderStatusEnumMessage);
		}
	}

	private void validateUpdateMethodNotAllowedParameters(OrderRequest orderRequest)
			throws InvalidInputParametersException {
		if (orderRequest.getOrderId() != null || orderRequest.getOrderDate() != null
				|| orderRequest.getInvoiceId() != null || orderRequest.getAddressSequenceNumber() != null) {
			throw new InvalidInputParametersException(OrderValidatorConstant.validateUpdateMethodErrorCode,
					OrderValidatorConstant.validateUpdateMethodMessage);

		}
	}

}
