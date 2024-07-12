package com.insignia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.insignia.constants.BillingAndInvoiceDetailsConstant;
import com.insignia.constants.CommonConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.BillingAndInvoiceRequest;
import com.insignia.model.BillingAndInvoiceResponse;
import com.insignia.model.InvoiceStatus;
import com.insignia.model.ModeOfPayment;
import com.insignia.service.BillingAndInvoiceServiceInterface;
import com.insignia.validations.BillingAndInvoiceDetailsValidator;

@CrossOrigin
@RestController
public class BillingAndInvoiceController {

	@Autowired
	private BillingAndInvoiceServiceInterface billingAndInvoiceServiceInterface;

	@PostMapping("/createInvoice")
	public ResponseEntity<?> createInvoice(@RequestBody BillingAndInvoiceRequest billingAndInvoiceRequest) {

		try {

			checkValidationsForCreatingInvoice(billingAndInvoiceRequest);
			checkCommonValidationsForInvoice(billingAndInvoiceRequest);

			return ResponseEntity.ok(billingAndInvoiceServiceInterface.createInvoice(billingAndInvoiceRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new BillingAndInvoiceResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new BillingAndInvoiceResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BillingAndInvoiceResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	@PutMapping("/updateInvoice")
	public ResponseEntity<?> updateInvoice(@RequestBody BillingAndInvoiceRequest billingAndInvoiceRequest) {
		try {
			checkCommonValidationsForInvoice(billingAndInvoiceRequest);

			return ResponseEntity.ok(billingAndInvoiceServiceInterface.updateInvoice(billingAndInvoiceRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new BillingAndInvoiceResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new BillingAndInvoiceResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BillingAndInvoiceResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}
	
	@PostMapping("/deleteInvoice")
	public ResponseEntity<?> deleteInvoice(@RequestBody BillingAndInvoiceRequest billingAndInvoiceRequest)
			throws InvalidInputParametersException {
		try {
			billingAndInvoiceServiceInterface.deleteInvoice(billingAndInvoiceRequest);
			return ResponseEntity.ok(BillingAndInvoiceDetailsConstant.successMessageForDeleteInvoice);
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new BillingAndInvoiceResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new BillingAndInvoiceResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BillingAndInvoiceResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@GetMapping("/listAllInvoice")
	public ResponseEntity<?> listAllInvoice(@RequestBody BillingAndInvoiceRequest billingAndInvoiceRequest) {
		try {

			return ResponseEntity.ok(billingAndInvoiceServiceInterface.listAllInvoice(billingAndInvoiceRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new BillingAndInvoiceResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new BillingAndInvoiceResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BillingAndInvoiceResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@GetMapping("/getInvoiceDetail")
	public ResponseEntity<?> getInvoiceDetail(@RequestBody BillingAndInvoiceRequest billingAndInvoiceRequest) {
		try {

			return ResponseEntity.ok(billingAndInvoiceServiceInterface.getInvoiceDetail(billingAndInvoiceRequest));

		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new BillingAndInvoiceResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new BillingAndInvoiceResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BillingAndInvoiceResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}
	
	@PutMapping("/archiveInvoices")
	public ResponseEntity<?> archiveInvoices(@RequestBody BillingAndInvoiceRequest billingAndInvoiceRequest) {
		try {			

			return ResponseEntity.ok(billingAndInvoiceServiceInterface.archiveInvoices(billingAndInvoiceRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new BillingAndInvoiceResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new BillingAndInvoiceResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BillingAndInvoiceResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}	
	
	@PutMapping("/archiveInvoicesForCustomer")
	public ResponseEntity<?> archiveInvoicesForCustomer(@RequestBody BillingAndInvoiceRequest billingAndInvoiceRequest) {
		try {			

			return ResponseEntity.ok(billingAndInvoiceServiceInterface.archiveInvoicesForCustomer(billingAndInvoiceRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new BillingAndInvoiceResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new BillingAndInvoiceResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BillingAndInvoiceResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}	
	
	@GetMapping("/listArchivedInvoices")
	public ResponseEntity<?> listArchivedInvoices(@RequestBody BillingAndInvoiceRequest billingAndInvoiceRequest) {
		try {

			return ResponseEntity.ok(billingAndInvoiceServiceInterface.listArchivedInvoices(billingAndInvoiceRequest));

		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new BillingAndInvoiceResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new BillingAndInvoiceResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BillingAndInvoiceResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	private void checkValidationsForCreatingInvoice(BillingAndInvoiceRequest billingAndInvoiceRequest)
			throws InvalidInputParametersException {
		BillingAndInvoiceDetailsValidator.validateInvoiceDate(billingAndInvoiceRequest.getInvoiceDate());
		BillingAndInvoiceDetailsValidator.validateDueDate(billingAndInvoiceRequest.getDueDate());

	}

	private void checkCommonValidationsForInvoice(BillingAndInvoiceRequest billingAndInvoiceRequest)
			throws InvalidInputParametersException {
		BillingAndInvoiceDetailsValidator.validateDateofPayment(billingAndInvoiceRequest.getDateOfPayment());

		if (billingAndInvoiceRequest.getStatus() != null)
			InvoiceStatus.fromValue(billingAndInvoiceRequest.getStatus());

		if (billingAndInvoiceRequest.getModeOfPayment() != null)
			ModeOfPayment.fromValue(billingAndInvoiceRequest.getModeOfPayment());
	}

}
