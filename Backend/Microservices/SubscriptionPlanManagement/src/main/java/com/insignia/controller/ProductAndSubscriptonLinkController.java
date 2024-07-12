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
import com.insignia.model.ProductSubscriptionLinkRequest;
import com.insignia.model.SubscriptionResponse;
import com.insignia.service.ProductAndSubscriptonLinkServiceInterface;

@CrossOrigin
@RestController
@RequestMapping("/productSubscriptionPlanLink")
public class ProductAndSubscriptonLinkController {

	@Autowired
	private ProductAndSubscriptonLinkServiceInterface productAndSubscriptonLinkServiceInterface;

	@PostMapping("/associateSubscriptionPlanWithProduct")
	public ResponseEntity<?> associateSubscriptionPlanWithProduct(
			@RequestBody ProductSubscriptionLinkRequest productSubscriptionLinkRequest) {
		try {
			return ResponseEntity.ok(productAndSubscriptonLinkServiceInterface
					.associateSubscriptionPlanWithProduct(productSubscriptionLinkRequest));
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

	@PostMapping("/removeSubscriptionPlanForProduct")
	public ResponseEntity<?> removeSubscriptionPlanForProduct(
			@RequestBody ProductSubscriptionLinkRequest productSubscriptionLinkRequest) {
		try {
			productAndSubscriptonLinkServiceInterface.removeSubscriptionPlanForProduct(productSubscriptionLinkRequest);
			return ResponseEntity.ok(SubscriptionConstants.SuccessMessageForDeleteInProduct);
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

	@PostMapping("/getSubscriptionPlanWithProduct")
	public ResponseEntity<?> getSubscriptionPlanWithProduct(
			@RequestBody ProductSubscriptionLinkRequest productSubscriptionLinkRequest) {
		try {
			return ResponseEntity.ok(productAndSubscriptonLinkServiceInterface
					.getAllSubscriptionPlanForProduct(productSubscriptionLinkRequest));
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
