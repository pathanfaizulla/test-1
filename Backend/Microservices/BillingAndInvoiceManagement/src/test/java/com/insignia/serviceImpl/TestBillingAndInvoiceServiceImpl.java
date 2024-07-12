package com.insignia.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.BillingAndInvoiceDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.dateutils.DateUtils;
import com.insignia.entity.BillingAndInvoiceDetails;
import com.insignia.model.BillingAndInvoiceRequest;
import com.insignia.model.BillingAndInvoiceResponse;

@ExtendWith(MockitoExtension.class)
public class TestBillingAndInvoiceServiceImpl {

	@InjectMocks
	private BillingAndInvoiceServiceImpl billingAndInvoiceServiceImpl;

	@Mock
	private BillingAndInvoiceDaoInterface billingAndInvoiceDaoInterface;

	@Mock
	private TokenDaoInterface tokenRepo;

	BillingAndInvoiceRequest billingAndInvoiceRequest = new BillingAndInvoiceRequest();
	BillingAndInvoiceRequest billingAndInvoiceRequestForUpdate = new BillingAndInvoiceRequest();
	BillingAndInvoiceResponse billingAndInvoiceResponse = new BillingAndInvoiceResponse();
	List<BillingAndInvoiceResponse> billingAndInvoiceResponseList = new ArrayList<>();
	BillingAndInvoiceDetails billingAndInvoiceDetails = new BillingAndInvoiceDetails();
	List<BillingAndInvoiceDetails> billingAndInvoiceDetailsList = new ArrayList<>();

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
		billingAndInvoiceRequest.setModeOfPayment("Online");

		List<Long> invoiceNumberList = new ArrayList<>();
		invoiceNumberList.add(40L);
		invoiceNumberList.add(41L);
		billingAndInvoiceRequest.setInvoiceNumberList(invoiceNumberList);

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

		billingAndInvoiceDetails.setInvoiceDate(date);
		billingAndInvoiceDetails.setDueDate(date);
		billingAndInvoiceDetails.setDateOfPayment(date);

		billingAndInvoiceDetails.setCurrency("Dollor");
		billingAndInvoiceDetails.setStatus("PAID");
		billingAndInvoiceDetails.setModeOfPayment("Online");

		billingAndInvoiceDetails.setArchived(false);

		billingAndInvoiceDetailsList.add(billingAndInvoiceDetails);

	}

	@Test
	public void testCreateInvoice() throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		billingAndInvoiceRequest.setInvoiceDate(null);
		when(billingAndInvoiceDaoInterface.createInvoice(any(BillingAndInvoiceDetails.class)))
				.thenReturn(billingAndInvoiceDetails);

		BillingAndInvoiceResponse billingAndInvoiceResponse = billingAndInvoiceServiceImpl
				.createInvoice(billingAndInvoiceRequest);

		assertNotNull(billingAndInvoiceResponse);
	}

	@Test
	public void testCreateInvoiceWithGivenInvoiceDate()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(billingAndInvoiceDaoInterface.createInvoice(any(BillingAndInvoiceDetails.class)))
				.thenReturn(billingAndInvoiceDetails);

		BillingAndInvoiceResponse billingAndInvoiceResponse = billingAndInvoiceServiceImpl
				.createInvoice(billingAndInvoiceRequest);

		assertNotNull(billingAndInvoiceResponse);
	}

	@Test
	public void testCreateInvoiceDuplicateDataException() throws ParseException, TokenExpiredException {

		dataInitilization();

		when(billingAndInvoiceDaoInterface.findBySequenceNumberAndOrderSequenceNumber(
				billingAndInvoiceRequest.getCustomerSequenceNumber(),
				billingAndInvoiceRequest.getOrderSequenceNumber())).thenReturn(Optional.of(billingAndInvoiceDetails));

		assertThrows(InvalidInputParametersException.class, () -> {

			billingAndInvoiceServiceImpl.createInvoice(billingAndInvoiceRequest);
		});
		verify(billingAndInvoiceDaoInterface, times(1)).findBySequenceNumberAndOrderSequenceNumber(
				billingAndInvoiceRequest.getCustomerSequenceNumber(),
				billingAndInvoiceRequest.getOrderSequenceNumber());
	}

	@Test
	public void testListAllInvoice() throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(billingAndInvoiceDaoInterface.listAllInvoice(billingAndInvoiceRequest.getCustomerSequenceNumber()))
				.thenReturn(billingAndInvoiceDetailsList);

		List<BillingAndInvoiceResponse> billingAndInvoiceResponseList = billingAndInvoiceServiceImpl
				.listAllInvoice(billingAndInvoiceRequest);

		assertNotNull(billingAndInvoiceResponseList);
	}

	@Test
	public void testArchiveInvoices() throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());
		when(billingAndInvoiceDaoInterface.findByInvoiceNumberList(billingAndInvoiceRequest.getInvoiceNumberList()))
				.thenReturn(billingAndInvoiceDetailsList);
		;

		List<BillingAndInvoiceResponse> billingAndInvoiceResponseList = billingAndInvoiceServiceImpl
				.archiveInvoices(billingAndInvoiceRequest);

		assertNotNull(billingAndInvoiceResponseList);
	}

	@Test
	public void testArchiveInvoicesForCustomer()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());
		when(billingAndInvoiceDaoInterface.findByInvoiceNumberListAndSequenceNumber(
				billingAndInvoiceRequest.getInvoiceNumberList(), billingAndInvoiceRequest.getCustomerSequenceNumber()))
				.thenReturn(billingAndInvoiceDetailsList);
		;

		List<BillingAndInvoiceResponse> billingAndInvoiceResponseList = billingAndInvoiceServiceImpl
				.archiveInvoicesForCustomer(billingAndInvoiceRequest);

		assertNotNull(billingAndInvoiceResponseList);
	}

	@Test
	public void testListArchivedInvoices()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();
		billingAndInvoiceDetails.setArchived(true);
		doNothing().when(tokenRepo).checkTokenValidity(any(), any());
		when(billingAndInvoiceDaoInterface
				.listArchivedInvoicesBySequenceNumber(billingAndInvoiceRequest.getCustomerSequenceNumber()))
				.thenReturn(billingAndInvoiceDetailsList);
		;

		List<BillingAndInvoiceResponse> billingAndInvoiceResponseList = billingAndInvoiceServiceImpl
				.listArchivedInvoices(billingAndInvoiceRequest);

		assertNotNull(billingAndInvoiceResponseList);
	}

	@Test
	public void testGetInvoiceDetail() throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(billingAndInvoiceDaoInterface.getInvoiceDetail(billingAndInvoiceRequest.getCustomerSequenceNumber(),
				billingAndInvoiceRequest.getInvoiceNumber())).thenReturn(Optional.of(billingAndInvoiceDetails));

		BillingAndInvoiceResponse billingAndInvoiceResponse = billingAndInvoiceServiceImpl
				.getInvoiceDetail(billingAndInvoiceRequest);

		assertNotNull(billingAndInvoiceResponse);
	}

	@Test
	public void testDeleteBySequenceNumberAndInvoiceNumber()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();
		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(billingAndInvoiceDaoInterface.getInvoiceDetail(billingAndInvoiceRequest.getCustomerSequenceNumber(),
				billingAndInvoiceRequest.getInvoiceNumber())).thenReturn(Optional.of(billingAndInvoiceDetails));

		billingAndInvoiceServiceImpl.deleteInvoice(billingAndInvoiceRequest);

		verify(billingAndInvoiceDaoInterface, times(1)).deleteBySequenceNumberAndInvoiceNumber(
				billingAndInvoiceRequest.getCustomerSequenceNumber(), billingAndInvoiceRequest.getInvoiceNumber());
	}

	@Test
	public void testDeleteBySequenceNumber()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();
		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		billingAndInvoiceRequest.setInvoiceNumber(null);

		when(billingAndInvoiceDaoInterface.listAllInvoice(billingAndInvoiceRequest.getCustomerSequenceNumber()))
				.thenReturn(billingAndInvoiceDetailsList);

		billingAndInvoiceServiceImpl.deleteInvoice(billingAndInvoiceRequest);

		verify(billingAndInvoiceDaoInterface, times(1))
				.deleteBySequenceNumber(billingAndInvoiceRequest.getCustomerSequenceNumber());
	}

	@Test
	public void testUpdateInvoice() throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();

		billingAndInvoiceRequest.setCustomerSequenceNumber(105L);
		billingAndInvoiceRequest.setExpirationDuration(15);
		billingAndInvoiceRequest.setInvoiceNumber(40L);
		billingAndInvoiceRequest.setOrderSequenceNumber(20L);

		billingAndInvoiceRequest.setInvoiceDate("10-10-2024");
		billingAndInvoiceRequest.setDateOfPayment("10-10-2024");
		billingAndInvoiceRequest.setDueDate("10-10-2024");
		billingAndInvoiceRequest.setCurrency("Rupee");
		billingAndInvoiceRequest.setStatus("PAID");
		billingAndInvoiceRequest.setModeOfPayment("Cash");

		Long customerSequenceNumber = 105L;
		Long invoiceNumber = 40L;

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(billingAndInvoiceDaoInterface.getBillingAndInvoiceDetails(customerSequenceNumber, invoiceNumber))
				.thenReturn(Optional.of(billingAndInvoiceDetails));

		when(billingAndInvoiceDaoInterface.updateInvoice(billingAndInvoiceDetails))
				.thenReturn(billingAndInvoiceDetails);

		BillingAndInvoiceResponse billingAndInvoiceResponse = billingAndInvoiceServiceImpl
				.updateInvoice(billingAndInvoiceRequest);

		assertNotNull(billingAndInvoiceResponse);

	}

	@Test
	public void testUpdateInvoiceDuplicateDataException() throws ParseException, TokenExpiredException {

		dataInitilization();
		billingAndInvoiceRequest.setInvoiceNumber(1L);

		when(billingAndInvoiceDaoInterface.getBillingAndInvoiceDetails(
				billingAndInvoiceRequest.getCustomerSequenceNumber(), billingAndInvoiceRequest.getInvoiceNumber()))
				.thenReturn(Optional.empty());

		assertThrows(InvalidInputParametersException.class, () -> {

			billingAndInvoiceServiceImpl.updateInvoice(billingAndInvoiceRequest);
		});
		verify(billingAndInvoiceDaoInterface, times(1)).getBillingAndInvoiceDetails(
				billingAndInvoiceRequest.getCustomerSequenceNumber(), billingAndInvoiceRequest.getInvoiceNumber());
	}

}
