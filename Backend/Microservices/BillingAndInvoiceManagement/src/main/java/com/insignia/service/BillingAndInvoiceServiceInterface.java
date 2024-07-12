package com.insignia.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.BillingAndInvoiceRequest;
import com.insignia.model.BillingAndInvoiceResponse;

@Service
public interface BillingAndInvoiceServiceInterface {

	public BillingAndInvoiceResponse createInvoice(BillingAndInvoiceRequest billingAndInvoiceRequest)
			throws TokenExpiredException, InvalidInputParametersException, NumberFormatException, ParseException;

	public BillingAndInvoiceResponse updateInvoice(BillingAndInvoiceRequest billingAndInvoiceRequest)
			throws TokenExpiredException, InvalidInputParametersException, NumberFormatException, ParseException;

	public List<BillingAndInvoiceResponse> listAllInvoice(BillingAndInvoiceRequest billingAndInvoiceRequest)
			throws TokenExpiredException, InvalidInputParametersException;

	public BillingAndInvoiceResponse getInvoiceDetail(BillingAndInvoiceRequest billingAndInvoiceRequest)
			throws TokenExpiredException, InvalidInputParametersException;

	public void deleteInvoice(BillingAndInvoiceRequest billingAndInvoiceRequest)
			throws InvalidInputParametersException, TokenExpiredException;

	public List<BillingAndInvoiceResponse> archiveInvoices(BillingAndInvoiceRequest billingAndInvoiceRequest)
			throws TokenExpiredException, InvalidInputParametersException, NumberFormatException, ParseException;

	public List<BillingAndInvoiceResponse> archiveInvoicesForCustomer(BillingAndInvoiceRequest billingAndInvoiceRequest)
			throws TokenExpiredException, InvalidInputParametersException, NumberFormatException, ParseException;

	public List<BillingAndInvoiceResponse> listArchivedInvoices(BillingAndInvoiceRequest billingAndInvoiceRequest)
			throws TokenExpiredException, InvalidInputParametersException;

}
