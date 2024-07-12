package com.insignia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.dateutils.DateUtils;
import com.insignia.entity.BillingAndInvoiceDetails;
import com.insignia.model.BillingAndInvoiceRequest;
import com.insignia.model.BillingAndInvoiceResponse;
import com.insignia.service.BillingAndInvoiceServiceInterface;

@ExtendWith(MockitoExtension.class)
public class TestBillingAndInvoiceController {

	@InjectMocks
	private BillingAndInvoiceController billingAndInvoiceController;

	@Mock
	private BillingAndInvoiceServiceInterface billingAndInvoiceServiceInterface;

	BillingAndInvoiceRequest billingAndInvoiceRequest = new BillingAndInvoiceRequest();
	BillingAndInvoiceResponse billingAndInvoiceResponse = new BillingAndInvoiceResponse();

	List<BillingAndInvoiceResponse> billingAndInvoiceResponseList = new ArrayList<>();
	BillingAndInvoiceDetails billingAndInvoiceDetails = new BillingAndInvoiceDetails();

	public void dataInitilization() {
		billingAndInvoiceRequest.setCustomerSequenceNumber(105L);
		billingAndInvoiceRequest.setExpirationDuration(15);
		billingAndInvoiceRequest.setInvoiceNumber(40L);
		billingAndInvoiceRequest.setOrderSequenceNumber(20L);
		billingAndInvoiceRequest.setInvoiceDate("10-10-2024");
		billingAndInvoiceRequest.setDateOfPayment("10-10-2024");
		billingAndInvoiceRequest.setDueDate("10-10-2024");
		billingAndInvoiceRequest.setCurrency("Dollor");
		billingAndInvoiceRequest.setStatus("PAID");
		billingAndInvoiceRequest.setModeOfPayment("COD");

		Date date = null;
		try {
			date = DateUtils.stringToDate("10-10-2024");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		billingAndInvoiceResponse.setCustomerSequenceNumber(105L);
		billingAndInvoiceResponse.setInvoiceNumber(40L);
		billingAndInvoiceResponse.setOrderSequenceNumber(20L);
		billingAndInvoiceResponse.setInvoiceDate(date);
		billingAndInvoiceResponse.setDateOfPayment(date);
		billingAndInvoiceResponse.setDueDate(date);
		billingAndInvoiceResponse.setCurrency("Dollor");
		billingAndInvoiceResponse.setStatus("PAID");
		billingAndInvoiceResponse.setModeOfPayment("Online");

		billingAndInvoiceResponseList.add(billingAndInvoiceResponse);

		billingAndInvoiceDetails.setCustomerSequenceNumber(105L);
		billingAndInvoiceDetails.setInvoiceNumber(40L);
		billingAndInvoiceDetails.setOrderSequenceNumber(20L);

		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date invoiceDate;
		try {
			invoiceDate = format.parse("10-10-2024");
			billingAndInvoiceDetails.setInvoiceDate(invoiceDate);
			billingAndInvoiceDetails.setDueDate(invoiceDate);
			billingAndInvoiceDetails.setDateOfPayment(invoiceDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		billingAndInvoiceDetails.setCurrency("Dollor");
		billingAndInvoiceDetails.setStatus("PAID");
		billingAndInvoiceDetails.setModeOfPayment("Online");

	}

	@Test
	public void testCreateInvoice()
			throws InvalidInputParametersException, TokenExpiredException, NumberFormatException, ParseException {
		dataInitilization();

		when(billingAndInvoiceServiceInterface.createInvoice(billingAndInvoiceRequest))
				.thenReturn(billingAndInvoiceResponse);
		ResponseEntity<?> response = billingAndInvoiceController.createInvoice(billingAndInvoiceRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testCreateInvoiceInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException, NumberFormatException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(billingAndInvoiceServiceInterface)
				.createInvoice(billingAndInvoiceRequest);

		ResponseEntity<?> response = billingAndInvoiceController.createInvoice(billingAndInvoiceRequest);

		verify(billingAndInvoiceServiceInterface).createInvoice(billingAndInvoiceRequest);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	public void testDeleteInvoice() throws Exception {

		dataInitilization();
		doNothing().when(billingAndInvoiceServiceInterface).deleteInvoice(billingAndInvoiceRequest);

		billingAndInvoiceController.deleteInvoice(billingAndInvoiceRequest);
		verify(billingAndInvoiceServiceInterface, times(1)).deleteInvoice(billingAndInvoiceRequest);
	}

	@Test
	public void testListAllInvoice() throws InvalidInputParametersException, TokenExpiredException {

		dataInitilization();

		when(billingAndInvoiceServiceInterface.listAllInvoice(billingAndInvoiceRequest))
				.thenReturn(billingAndInvoiceResponseList);
		ResponseEntity<?> response = billingAndInvoiceController.listAllInvoice(billingAndInvoiceRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testGetInvoiceDetailtInvoice() throws InvalidInputParametersException, TokenExpiredException {

		dataInitilization();

		when(billingAndInvoiceServiceInterface.getInvoiceDetail(billingAndInvoiceRequest))
				.thenReturn(billingAndInvoiceResponse);
		ResponseEntity<?> response = billingAndInvoiceController.getInvoiceDetail(billingAndInvoiceRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

//	@Test
//	public void testListAllInvoiceInvalidInputParametersException()
//			throws InvalidInputParametersException, TokenExpiredException {
//		dataInitilization();
//
//		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(billingAndInvoiceServiceInterface)
//				.listAllInvoice(billingAndInvoiceRequest);
//		ResponseEntity<?> response = billingAndInvoiceController.listAllInvoice(billingAndInvoiceRequest);
//
//		verify(billingAndInvoiceServiceInterface).listAllInvoice(billingAndInvoiceRequest);
//
//		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//
//	} 

	@Test
	public void testListAllInvoiceInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(billingAndInvoiceServiceInterface)
				.listAllInvoice(billingAndInvoiceRequest);
		ResponseEntity<?> response = billingAndInvoiceController.listAllInvoice(billingAndInvoiceRequest);

		verify(billingAndInvoiceServiceInterface).listAllInvoice(billingAndInvoiceRequest);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	public void testGetInvoiceDetailInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(billingAndInvoiceServiceInterface)
				.getInvoiceDetail(billingAndInvoiceRequest);
		ResponseEntity<?> response = billingAndInvoiceController.getInvoiceDetail(billingAndInvoiceRequest);

		verify(billingAndInvoiceServiceInterface).getInvoiceDetail(billingAndInvoiceRequest);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	public void testUpdateInvoice()
			throws InvalidInputParametersException, TokenExpiredException, NumberFormatException, ParseException {
		dataInitilization();

		when(billingAndInvoiceServiceInterface.updateInvoice(billingAndInvoiceRequest))
				.thenReturn(billingAndInvoiceResponse);
		ResponseEntity<?> response = billingAndInvoiceController.updateInvoice(billingAndInvoiceRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testUpdateInvoiceInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(billingAndInvoiceServiceInterface)
				.updateInvoice(billingAndInvoiceRequest);
		ResponseEntity<?> response = billingAndInvoiceController.updateInvoice(billingAndInvoiceRequest);

		verify(billingAndInvoiceServiceInterface).updateInvoice(billingAndInvoiceRequest);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	public void testArchiveInvoices()
			throws InvalidInputParametersException, TokenExpiredException, NumberFormatException, ParseException {
		dataInitilization();
		billingAndInvoiceDetails.setArchived(false);
		when(billingAndInvoiceServiceInterface.archiveInvoices(billingAndInvoiceRequest))
				.thenReturn(billingAndInvoiceResponseList);
		ResponseEntity<?> response = billingAndInvoiceController.archiveInvoices(billingAndInvoiceRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testArchiveInvoicesForCustomer()
			throws InvalidInputParametersException, TokenExpiredException, NumberFormatException, ParseException {
		dataInitilization();
		billingAndInvoiceDetails.setArchived(false);
		when(billingAndInvoiceServiceInterface.archiveInvoicesForCustomer(billingAndInvoiceRequest))
				.thenReturn(billingAndInvoiceResponseList);
		ResponseEntity<?> response = billingAndInvoiceController.archiveInvoicesForCustomer(billingAndInvoiceRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testListArchivedInvoices() throws InvalidInputParametersException, TokenExpiredException {

		dataInitilization();
		billingAndInvoiceDetails.setArchived(true);
		when(billingAndInvoiceServiceInterface.listArchivedInvoices(billingAndInvoiceRequest))
				.thenReturn(billingAndInvoiceResponseList);
		ResponseEntity<?> response = billingAndInvoiceController.listArchivedInvoices(billingAndInvoiceRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testArchiveInvoicesInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, NumberFormatException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(billingAndInvoiceServiceInterface)
				.archiveInvoices(billingAndInvoiceRequest);
		ResponseEntity<?> response = billingAndInvoiceController.archiveInvoices(billingAndInvoiceRequest);

		verify(billingAndInvoiceServiceInterface).archiveInvoices(billingAndInvoiceRequest);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	public void testArchiveInvoicesForCustomerInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, NumberFormatException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(billingAndInvoiceServiceInterface)
				.archiveInvoicesForCustomer(billingAndInvoiceRequest);
		ResponseEntity<?> response = billingAndInvoiceController.archiveInvoicesForCustomer(billingAndInvoiceRequest);

		verify(billingAndInvoiceServiceInterface).archiveInvoicesForCustomer(billingAndInvoiceRequest);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	public void testListArchivedInvoicesInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(billingAndInvoiceServiceInterface)
				.listArchivedInvoices(billingAndInvoiceRequest);
		ResponseEntity<?> response = billingAndInvoiceController.listArchivedInvoices(billingAndInvoiceRequest);

		verify(billingAndInvoiceServiceInterface).listArchivedInvoices(billingAndInvoiceRequest);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	public void testForException()
			throws TokenExpiredException, InvalidInputParametersException, NumberFormatException, ParseException {
		dataInitilization();

		when(billingAndInvoiceServiceInterface.createInvoice(billingAndInvoiceRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> createEntiry = billingAndInvoiceController.createInvoice(billingAndInvoiceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, createEntiry.getStatusCode());

		doThrow(new NullPointerException("")).when(billingAndInvoiceServiceInterface)
				.deleteInvoice(billingAndInvoiceRequest);
		ResponseEntity<?> deleteInvoice = billingAndInvoiceController.deleteInvoice(billingAndInvoiceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, deleteInvoice.getStatusCode());

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(billingAndInvoiceServiceInterface)
				.deleteInvoice(billingAndInvoiceRequest);
		ResponseEntity<?> deleteInvoice2 = billingAndInvoiceController.deleteInvoice(billingAndInvoiceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, deleteInvoice2.getStatusCode());

		when(billingAndInvoiceServiceInterface.getInvoiceDetail(billingAndInvoiceRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> listEntity = billingAndInvoiceController.getInvoiceDetail(billingAndInvoiceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, listEntity.getStatusCode());

		when(billingAndInvoiceServiceInterface.listAllInvoice(billingAndInvoiceRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> listAllEntity = billingAndInvoiceController.listAllInvoice(billingAndInvoiceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, listAllEntity.getStatusCode());

		when(billingAndInvoiceServiceInterface.archiveInvoices(billingAndInvoiceRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> listAllEntity2 = billingAndInvoiceController.archiveInvoices(billingAndInvoiceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, listAllEntity2.getStatusCode());

		when(billingAndInvoiceServiceInterface.archiveInvoicesForCustomer(billingAndInvoiceRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> listAllEntity3 = billingAndInvoiceController
				.archiveInvoicesForCustomer(billingAndInvoiceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, listAllEntity3.getStatusCode());

		when(billingAndInvoiceServiceInterface.listArchivedInvoices(billingAndInvoiceRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> listAllEntity4 = billingAndInvoiceController.listArchivedInvoices(billingAndInvoiceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, listAllEntity4.getStatusCode());

		when(billingAndInvoiceServiceInterface.updateInvoice(billingAndInvoiceRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> update = billingAndInvoiceController.updateInvoice(billingAndInvoiceRequest);

		assertEquals(HttpStatus.BAD_REQUEST, update.getStatusCode());
	}

	@Test
	public void testForTokenExpired()
			throws TokenExpiredException, InvalidInputParametersException, NumberFormatException, ParseException {
		dataInitilization();

		when(billingAndInvoiceServiceInterface.createInvoice(billingAndInvoiceRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> entity = billingAndInvoiceController.createInvoice(billingAndInvoiceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());

		doThrow(new TokenExpiredException("")).when(billingAndInvoiceServiceInterface)
				.deleteInvoice(billingAndInvoiceRequest);
		;
		ResponseEntity<?> entity2 = billingAndInvoiceController.deleteInvoice(billingAndInvoiceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, entity2.getStatusCode());

		when(billingAndInvoiceServiceInterface.listAllInvoice(billingAndInvoiceRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> entity3 = billingAndInvoiceController.listAllInvoice(billingAndInvoiceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, entity3.getStatusCode());

		when(billingAndInvoiceServiceInterface.archiveInvoices(billingAndInvoiceRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> entity4 = billingAndInvoiceController.archiveInvoices(billingAndInvoiceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, entity4.getStatusCode());

		when(billingAndInvoiceServiceInterface.archiveInvoicesForCustomer(billingAndInvoiceRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> entity5 = billingAndInvoiceController.archiveInvoicesForCustomer(billingAndInvoiceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, entity5.getStatusCode());

		when(billingAndInvoiceServiceInterface.listArchivedInvoices(billingAndInvoiceRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> entity6 = billingAndInvoiceController.listArchivedInvoices(billingAndInvoiceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, entity6.getStatusCode());

		when(billingAndInvoiceServiceInterface.getInvoiceDetail(billingAndInvoiceRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> entity7 = billingAndInvoiceController.getInvoiceDetail(billingAndInvoiceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, entity7.getStatusCode());

		when(billingAndInvoiceServiceInterface.updateInvoice(billingAndInvoiceRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> entity8 = billingAndInvoiceController.updateInvoice(billingAndInvoiceRequest);

		assertEquals(HttpStatus.BAD_REQUEST, entity8.getStatusCode());

	}

}
