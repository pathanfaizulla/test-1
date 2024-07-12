package com.insignia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insignia.constant.PaymentConstants;
import com.insignia.constants.CommonConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.PaymentMethodRequest;
import com.insignia.model.PaymentMethodResponse;
import com.insignia.serviceInterface.PaymentServiceInterface;
import com.insignia.validations.CustomerPaymentMethodDetailsValidation;

@RestController
@RequestMapping("/payment")
public class PaymentMethodController {

	@Autowired
	private PaymentServiceInterface paymentServiceInterface;

	@PostMapping("/addPaymentMethod")
	public ResponseEntity<?> addPaymentMethod(@RequestBody PaymentMethodRequest paymentMethodRequest) {
		try {
			validationForCustomerPaymentMethod(paymentMethodRequest);
			return ResponseEntity.ok(paymentServiceInterface.addPaymentMethod(paymentMethodRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new PaymentMethodResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new PaymentMethodResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PaymentMethodResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));

		}
	}

	@DeleteMapping("/removePaymentMethod/{paymentMethodSequenceNumber}/{customerSequenceNumber}/{expirationDuration}")
	public ResponseEntity<?> removePaymentMethod(@PathVariable Long paymentMethodSequenceNumber,
			@PathVariable Long customerSequenceNumber, @PathVariable Integer expirationDuration) {
		try {
			paymentServiceInterface.removePaymentMethod(paymentMethodSequenceNumber, customerSequenceNumber,
					expirationDuration);

			return ResponseEntity.ok(PaymentConstants.successMessageForDeleteMethod);
		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new PaymentMethodResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PaymentMethodResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));

		}
	}

	@GetMapping("/getListOfPaymentMethodDetails/{customerSequenceNumber}/{expirationDuration}")
	public ResponseEntity<?> listPaymentMethod(@PathVariable Long customerSequenceNumber,
			@PathVariable Integer expirationDuration) {
		try {

			return ResponseEntity.ok(
					paymentServiceInterface.getListOfPaymentMethodDetails(customerSequenceNumber, expirationDuration));
		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new PaymentMethodResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PaymentMethodResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));

		}
	}

	private void validationForCustomerPaymentMethod(PaymentMethodRequest paymentMethodRequest)
			throws InvalidInputParametersException {
		CustomerPaymentMethodDetailsValidation.validatePaymentMethodType(paymentMethodRequest.getPaymentMethodType(),
				PaymentConstants.paymentMethodTypeLength);
		CustomerPaymentMethodDetailsValidation.validatePaymentMethodDetail(paymentMethodRequest.getPaymentMethodType(),
				PaymentConstants.paymentMethodTypeLength);
		CustomerPaymentMethodDetailsValidation.validateValidFrom(paymentMethodRequest.getValidFrom(),
				PaymentConstants.validFrom);
		CustomerPaymentMethodDetailsValidation.validateValidUpto(paymentMethodRequest.getValidUpto(),
				PaymentConstants.validUpto);
	}

}
