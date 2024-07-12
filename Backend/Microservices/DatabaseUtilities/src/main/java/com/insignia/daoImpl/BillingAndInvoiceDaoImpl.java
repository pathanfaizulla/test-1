package com.insignia.daoImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.daoInterface.BillingAndInvoiceDaoInterface;
import com.insignia.entity.BillingAndInvoiceDetails;
import com.insignia.repo.BillingAndInvoiceRepository;

import jakarta.persistence.EntityManager;

@Repository
public class BillingAndInvoiceDaoImpl implements BillingAndInvoiceDaoInterface {

	@Autowired
	BillingAndInvoiceRepository billingAndInvoiceRepo;

	@Autowired
	private EntityManager entityManager;

	@Override
	public BillingAndInvoiceDetails createInvoice(BillingAndInvoiceDetails billingAndInvoiceDetails)
			throws InvalidInputParametersException {
		return billingAndInvoiceRepo.save(billingAndInvoiceDetails);
	}

	@Override
	public BillingAndInvoiceDetails updateInvoice(BillingAndInvoiceDetails billingAndInvoiceDetails)
			throws InvalidInputParametersException {
		return entityManager.merge(billingAndInvoiceDetails);
	}

	@Override
	public List<BillingAndInvoiceDetails> listAllInvoice(Long customerSequenceNumber) {
		return billingAndInvoiceRepo.listAllInvoice(customerSequenceNumber);
	}

	@Override
	public Optional<BillingAndInvoiceDetails> getInvoiceDetail(Long customerSequenceNumber, Long invoiceNumber) {
		return billingAndInvoiceRepo.getInvoiceDetail(customerSequenceNumber, invoiceNumber);
	}

	@Override
	public void deleteBySequenceNumber(Long customerSequenceNumber) {
		billingAndInvoiceRepo.deleteBySequenceNumber(customerSequenceNumber);

	}

	@Override
	public void deleteBySequenceNumberAndInvoiceNumber(Long customerSequenceNumber, Long invoiceNumber) {
		billingAndInvoiceRepo.deleteBySequenceNumberAndInvoiceNumber(customerSequenceNumber, invoiceNumber);
	}

	@Override
	public Optional<BillingAndInvoiceDetails> getBillingAndInvoiceDetails(Long customerSequenceNumber,
			Long invoiceNumber) {
		return billingAndInvoiceRepo.getBillingAndInvoiceDetails(customerSequenceNumber, invoiceNumber);
	}

	@Override
	public Optional<BillingAndInvoiceDetails> findBySequenceNumberAndOrderSequenceNumber(Long customerSequenceNumber,
			Long orderSequenceNumber) {
		return billingAndInvoiceRepo.findBySequenceNumberAndOrderSequenceNumber(customerSequenceNumber,
				orderSequenceNumber);
	}

	@Override
	public List<BillingAndInvoiceDetails> findByInvoiceNumberList(List<Long> invoiceNumberList)
			throws InvalidInputParametersException {
		return billingAndInvoiceRepo.findByInvoiceNumberList(invoiceNumberList);
	}

	@Override
	public List<BillingAndInvoiceDetails> findByInvoiceNumberListAndSequenceNumber(List<Long> invoiceNumberList,
			Long customerSequenceNumber) throws InvalidInputParametersException {
		return billingAndInvoiceRepo.findByInvoiceNumberListAndCustomerSequenceNumber(invoiceNumberList,
				customerSequenceNumber);
	}

	@Override
	public List<BillingAndInvoiceDetails> archiveInvoices(List<BillingAndInvoiceDetails> billingAndInvoiceDetailsList)
			throws InvalidInputParametersException {

		return billingAndInvoiceRepo.saveAll(billingAndInvoiceDetailsList);
	}

	@Override
	public List<BillingAndInvoiceDetails> listArchivedInvoicesBySequenceNumber(Long customerSequenceNumber) {
		return billingAndInvoiceRepo.listArchivedInvoicesBySequenceNumber(customerSequenceNumber);
	}

	@Override
	public List<BillingAndInvoiceDetails> listAllArchivedInvoices() {
		return billingAndInvoiceRepo.listAllArchivedInvoices();
	}

}
