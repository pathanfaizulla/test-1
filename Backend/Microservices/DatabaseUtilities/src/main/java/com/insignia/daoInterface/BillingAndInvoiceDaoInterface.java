package com.insignia.daoInterface;

import java.util.List;
import java.util.Optional;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.entity.BillingAndInvoiceDetails;

public interface BillingAndInvoiceDaoInterface {

	public BillingAndInvoiceDetails createInvoice(BillingAndInvoiceDetails billingAndInvoiceDetails)
			throws InvalidInputParametersException;

	public BillingAndInvoiceDetails updateInvoice(BillingAndInvoiceDetails billingAndInvoiceDetails)
			throws InvalidInputParametersException;

	public List<BillingAndInvoiceDetails> listAllInvoice(Long customerSequenceNumber);

	public Optional<BillingAndInvoiceDetails> getInvoiceDetail(Long customerSequenceNumber, Long invoiceNumber);

	public void deleteBySequenceNumber(Long customerSequenceNumber);

	public void deleteBySequenceNumberAndInvoiceNumber(Long customerSequenceNumber, Long invoiceNumber);

	public Optional<BillingAndInvoiceDetails> getBillingAndInvoiceDetails(Long customerSequenceNumber,
			Long invoiceNumber);

	public Optional<BillingAndInvoiceDetails> findBySequenceNumberAndOrderSequenceNumber(Long customerSequenceNumber,
			Long orderSequenceNumber);

	public List<BillingAndInvoiceDetails> findByInvoiceNumberList(List<Long> invoiceNumberList)
			throws InvalidInputParametersException;

	public List<BillingAndInvoiceDetails> findByInvoiceNumberListAndSequenceNumber(List<Long> invoiceNumberList,
			Long customerSequenceNumber) throws InvalidInputParametersException;
	
	public List<BillingAndInvoiceDetails> archiveInvoices(List<BillingAndInvoiceDetails> billingAndInvoiceDetailsList)
			throws InvalidInputParametersException;
	
	public List<BillingAndInvoiceDetails> listArchivedInvoicesBySequenceNumber(Long customerSequenceNumber);
	
	public List<BillingAndInvoiceDetails> listAllArchivedInvoices();

}
