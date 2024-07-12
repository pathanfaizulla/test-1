package com.insignia.repo;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insignia.entity.BillingAndInvoiceDetails;

public interface BillingAndInvoiceRepository extends JpaRepository<BillingAndInvoiceDetails, Serializable> {

	public static final String CUSTOMER_SEQUENCE_NUMBER = "customer_sequence_number";

	public static final String INVOICE_NUMBER = "invoice_number";

	public static final String ORDER_SEQUENCE_NUMBER = "order_sequence_number";

	public static final String INVOICE_NUMBER_LIST = "invoiceNumberList";

	public static final String listAllInvoiceQuery = "SELECT * FROM customer_billing_and_invoice_details WHERE customer_sequence_number =:customer_sequence_number AND is_archived = 0";

	public static final String getInvoiceDetailQuery = "SELECT * FROM customer_billing_and_invoice_details WHERE customer_sequence_number =:customer_sequence_number AND invoice_number =:invoice_number AND is_archived = 0";

	public static final String deleteBySequenceNumberQuery = "delete from customer_billing_and_invoice_details where customer_sequence_number =:customer_sequence_number";

	public static final String deleteBySequenceNumberAndInvoiceNumberQuery = "delete from customer_billing_and_invoice_details where customer_sequence_number =:customer_sequence_number and invoice_number =:invoice_number";

	public static final String getBillingAndInvoiceDetailsQuery = "SELECT * FROM customer_billing_and_invoice_details WHERE customer_sequence_number =:customer_sequence_number AND invoice_number =:invoice_number";

	public static final String findBySequenceNumberAndOrderSequenceNumberQuery = "SELECT * FROM customer_billing_and_invoice_details WHERE customer_sequence_number =:customer_sequence_number AND order_sequence_number =:order_sequence_number";

	public static final String findByInvoiceNumberListQuery = "SELECT * FROM customer_billing_and_invoice_details WHERE invoice_number IN (:invoiceNumberList) AND is_archived = 0";

	public static final String findByInvoiceNumberListAndCustomerSequenceNumberQuery = "SELECT * FROM customer_billing_and_invoice_details WHERE invoice_number IN (:invoiceNumberList) AND customer_sequence_number =:customer_sequence_number AND is_archived = 0";

	public static final String listArchivedInvoicesBySequenceNumberQuery = "SELECT * FROM customer_billing_and_invoice_details WHERE customer_sequence_number =:customer_sequence_number AND is_archived = 1";

	public static final String listAllArchivedInvoicesQuery = "SELECT * FROM customer_billing_and_invoice_details WHERE is_archived = 1";

	@Query(value = "SELECT token_expires_at FROM tokens_table WHERE customer_sequence_number = :customer_sequence_number AND token_type = 'JWT' AND (token_expires_at > CURRENT_TIMESTAMP OR token_expires_at IS NULL);", nativeQuery = true)
	public List<Object[]> isTokenNotValid(Long customer_sequence_number);

	@Query(value = getBillingAndInvoiceDetailsQuery, nativeQuery = true)
	public Optional<BillingAndInvoiceDetails> getBillingAndInvoiceDetails(
			@Param(CUSTOMER_SEQUENCE_NUMBER) Long customerSequenceNumber, @Param(INVOICE_NUMBER) Long invoiceNumber);

	@Query(value = listAllInvoiceQuery, nativeQuery = true)
	public List<BillingAndInvoiceDetails> listAllInvoice(@Param(CUSTOMER_SEQUENCE_NUMBER) Long customerSequenceNumber);

	@Query(value = getInvoiceDetailQuery, nativeQuery = true)
	public Optional<BillingAndInvoiceDetails> getInvoiceDetail(@Param(CUSTOMER_SEQUENCE_NUMBER) Long customerSequenceNumber,
			@Param(INVOICE_NUMBER) Long invoiceNumber);

	@Modifying
	@Query(value = deleteBySequenceNumberQuery, nativeQuery = true)
	public void deleteBySequenceNumber(@Param(CUSTOMER_SEQUENCE_NUMBER) Long customerSequenceNumber);

	@Modifying
	@Query(value = deleteBySequenceNumberAndInvoiceNumberQuery, nativeQuery = true)
	public void deleteBySequenceNumberAndInvoiceNumber(@Param(CUSTOMER_SEQUENCE_NUMBER) Long customerSequenceNumber,
			@Param(INVOICE_NUMBER) Long invoiceNumber);

	@Query(value = findBySequenceNumberAndOrderSequenceNumberQuery, nativeQuery = true)
	public Optional<BillingAndInvoiceDetails> findBySequenceNumberAndOrderSequenceNumber(
			@Param(CUSTOMER_SEQUENCE_NUMBER) Long customerSequenceNumber,
			@Param(ORDER_SEQUENCE_NUMBER) Long orderSequenceNumber);

	@Query(value = findByInvoiceNumberListQuery, nativeQuery = true)
	public List<BillingAndInvoiceDetails> findByInvoiceNumberList(
			@Param(INVOICE_NUMBER_LIST) List<Long> invoiceNumberList);

	@Query(value = findByInvoiceNumberListAndCustomerSequenceNumberQuery, nativeQuery = true)
	public List<BillingAndInvoiceDetails> findByInvoiceNumberListAndCustomerSequenceNumber(
			@Param(INVOICE_NUMBER_LIST) List<Long> invoiceNumberList,
			@Param(CUSTOMER_SEQUENCE_NUMBER) Long customerSequenceNumber);

	@Query(value = listArchivedInvoicesBySequenceNumberQuery, nativeQuery = true)
	public List<BillingAndInvoiceDetails> listArchivedInvoicesBySequenceNumber(
			@Param(CUSTOMER_SEQUENCE_NUMBER) Long customerSequenceNumber);

	@Query(value = listAllArchivedInvoicesQuery, nativeQuery = true)
	public List<BillingAndInvoiceDetails> listAllArchivedInvoices();

}
