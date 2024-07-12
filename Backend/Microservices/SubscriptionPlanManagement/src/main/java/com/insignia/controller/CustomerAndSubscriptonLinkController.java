package com.insignia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insignia.constant.SubscriptionConstants;
import com.insignia.constants.CommonConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.SubscriptonLinkRequest;
import com.insignia.model.CustomerSubscriptonLinkRequest;
import com.insignia.model.SubscriptionResponse;
import com.insignia.service.CustomerAndSubscriptonLinkServiceInterface;

@CrossOrigin
@RestController
@RequestMapping("/customerSubscriptionPlanLink")
public class CustomerAndSubscriptonLinkController {

	@Autowired
	private CustomerAndSubscriptonLinkServiceInterface customerAndSubscriptonLinkServiceInterface;

	@PostMapping("/associateSubscriptionPlanWithCustomer")
	public ResponseEntity<?> associateSubscriptionPlanWithCustomer(
			@RequestBody CustomerSubscriptonLinkRequest customerSubscriptonLinkRequest) {
		try {
			return ResponseEntity.ok(customerAndSubscriptonLinkServiceInterface
					.associateSubscriptionPlanWithCustomer(customerSubscriptonLinkRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new SubscriptionResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new SubscriptionResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SubscriptionResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));

		}
	}

	@PostMapping("/removeSubscriptionPlanForCustomer")
	public ResponseEntity<?> removeSubscriptionPlanForCustomer(
			@RequestBody SubscriptonLinkRequest customerAndSubscriptonLinkRequest) {
		try {
			customerAndSubscriptonLinkServiceInterface
					.removeSubscriptionPlanForCustomer(customerAndSubscriptonLinkRequest);
			return ResponseEntity.ok(SubscriptionConstants.SuccessMessageForDeleteInCustomer);
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new SubscriptionResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new SubscriptionResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SubscriptionResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));

		}
	}

	@PostMapping("/getSubscriptionPlanWithCustomer")
	public ResponseEntity<?> getSubscriptionPlanWithCustomer(
			@RequestBody SubscriptonLinkRequest customerAndSubscriptonLinkRequest) {
		try {
			return ResponseEntity.ok(customerAndSubscriptonLinkServiceInterface
					.getAllSubscriptionPlanForCustomer(customerAndSubscriptonLinkRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new SubscriptionResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new SubscriptionResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SubscriptionResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));

		}

	}
}
