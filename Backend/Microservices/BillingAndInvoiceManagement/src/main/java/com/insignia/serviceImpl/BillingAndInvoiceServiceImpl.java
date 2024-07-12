package com.insignia.serviceImpl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.insignia.constants.BillingAndInvoiceDetailsConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.BillingAndInvoiceDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.dateutils.DateUtils;
import com.insignia.entity.BillingAndInvoiceDetails;
import com.insignia.model.BillingAndInvoiceRequest;
import com.insignia.model.BillingAndInvoiceResponse;
import com.insignia.model.InvoiceStatus;
import com.insignia.service.BillingAndInvoiceServiceInterface;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillingAndInvoiceServiceImpl implements BillingAndInvoiceServiceInterface {

	private final BillingAndInvoiceDaoInterface billingAndInvoiceDaoInterface;

	private final TokenDaoInterface tokenDao;

	@Transactional
	@Override
	public BillingAndInvoiceResponse createInvoice(BillingAndInvoiceRequest billingAndInvoiceRequest)
			throws TokenExpiredException, InvalidInputParametersException, NumberFormatException, ParseException {
		tokenDao.checkTokenValidity(billingAndInvoiceRequest.getCustomerSequenceNumber(),
				billingAndInvoiceRequest.getExpirationDuration());

		Optional<BillingAndInvoiceDetails> billingAndInvoiceDetailsList = billingAndInvoiceDaoInterface
				.findBySequenceNumberAndOrderSequenceNumber(billingAndInvoiceRequest.getCustomerSequenceNumber(),
						billingAndInvoiceRequest.getOrderSequenceNumber());
		if (billingAndInvoiceDetailsList != null && !billingAndInvoiceDetailsList.isEmpty()) {

			BillingAndInvoiceDetails billingAndInvoiceDetails = billingAndInvoiceDetailsList.get();
			if (billingAndInvoiceDetails != null) {
				throw new InvalidInputParametersException(BillingAndInvoiceDetailsConstant.duplicateInvoiceErrorCode,
						BillingAndInvoiceDetailsConstant.duplicateInvoiceErrorMessage);
			}
		}

		BillingAndInvoiceDetails billingAndInvoiceDetails = new BillingAndInvoiceDetails();
		billingAndInvoiceDetails.setCustomerSequenceNumber(billingAndInvoiceRequest.getCustomerSequenceNumber());
		billingAndInvoiceDetails.setOrderSequenceNumber(billingAndInvoiceRequest.getOrderSequenceNumber());

		if (billingAndInvoiceRequest.getCurrency() != null) {
			billingAndInvoiceDetails.setCurrency(billingAndInvoiceRequest.getCurrency());
		}

		if (billingAndInvoiceRequest.getInvoiceDate() == null) {
			billingAndInvoiceDetails.setInvoiceDate(DateUtils.getCurrentDate());
		} else {
			billingAndInvoiceDetails.setInvoiceDate(DateUtils.stringToDate(billingAndInvoiceRequest.getInvoiceDate()));
		}

		if (billingAndInvoiceRequest.getStatus() == null) {
			billingAndInvoiceDetails.setStatus(InvoiceStatus.DUE.name());
		} else {
			billingAndInvoiceDetails.setStatus(billingAndInvoiceRequest.getStatus());
		}

		billingAndInvoiceDetails.setDueDate(DateUtils.stringToDate(billingAndInvoiceRequest.getDueDate()));

		if (billingAndInvoiceRequest.getDateOfPayment() != null) {
			billingAndInvoiceDetails
					.setDateOfPayment(DateUtils.stringToDate(billingAndInvoiceRequest.getDateOfPayment()));
		}	

		if (billingAndInvoiceRequest.getModeOfPayment() != null) {
			billingAndInvoiceDetails.setModeOfPayment(billingAndInvoiceRequest.getModeOfPayment());
		}

		return createResponseForBillingAndInvoiceEntity(
				billingAndInvoiceDaoInterface.createInvoice(billingAndInvoiceDetails));
	}

	@Transactional
	@Override
	public BillingAndInvoiceResponse updateInvoice(BillingAndInvoiceRequest billingAndInvoiceRequest)
			throws TokenExpiredException, InvalidInputParametersException, NumberFormatException, ParseException {

		tokenDao.checkTokenValidity(billingAndInvoiceRequest.getCustomerSequenceNumber(),
				billingAndInvoiceRequest.getExpirationDuration());

		Optional<BillingAndInvoiceDetails> billingAndInvoiceDetailsList = billingAndInvoiceDaoInterface
				.getBillingAndInvoiceDetails(billingAndInvoiceRequest.getCustomerSequenceNumber(),
						billingAndInvoiceRequest.getInvoiceNumber());

		if (billingAndInvoiceDetailsList.isPresent()) {
			BillingAndInvoiceDetails billingAndInvoiceDetails = billingAndInvoiceDetailsList.get();

			if (billingAndInvoiceRequest.isStatusUpdated()) {
				billingAndInvoiceDetails.setStatus(billingAndInvoiceRequest.getStatus());
			}
			if (billingAndInvoiceRequest.isDateOfPaymentUpdated()) {
				billingAndInvoiceDetails
						.setDateOfPayment(DateUtils.stringToDate(billingAndInvoiceRequest.getDateOfPayment()));
			}
			if (billingAndInvoiceRequest.isCurrencyUpdated()) {
				billingAndInvoiceDetails.setCurrency(billingAndInvoiceRequest.getCurrency());
			}
			if (billingAndInvoiceRequest.isModeOfPaymentUpdated()) {
				billingAndInvoiceDetails.setModeOfPayment(billingAndInvoiceRequest.getModeOfPayment());
			}

			return createResponseForBillingAndInvoiceEntity(
					billingAndInvoiceDaoInterface.updateInvoice(billingAndInvoiceDetails));
		} else {
			throw new InvalidInputParametersException(
					BillingAndInvoiceDetailsConstant.detailsNotExistedWithThisInvoiceNumberErrorCode,
					BillingAndInvoiceDetailsConstant.detailsNotExistedWithThisInvoiceNumberErrorMessage);
		}

	}

	@Transactional
	@Override
	public List<BillingAndInvoiceResponse> listAllInvoice(BillingAndInvoiceRequest billingAndInvoiceRequest)
			throws TokenExpiredException, InvalidInputParametersException {
		tokenDao.checkTokenValidity(billingAndInvoiceRequest.getCustomerSequenceNumber(),
				billingAndInvoiceRequest.getExpirationDuration());

		List<BillingAndInvoiceDetails> billingAndInvoiceDetailsList = billingAndInvoiceDaoInterface
				.listAllInvoice(billingAndInvoiceRequest.getCustomerSequenceNumber());

		return listInvoices(billingAndInvoiceDetailsList);
	}
	
	@Transactional
	@Override
	public List<BillingAndInvoiceResponse> listArchivedInvoices(BillingAndInvoiceRequest billingAndInvoiceRequest)
			throws TokenExpiredException, InvalidInputParametersException {

		tokenDao.checkTokenValidity(billingAndInvoiceRequest.getCustomerSequenceNumber(),
				billingAndInvoiceRequest.getExpirationDuration());

		List<BillingAndInvoiceDetails> billingAndInvoiceDetailsList = new ArrayList<>();
		if (billingAndInvoiceRequest.getCustomerSequenceNumber() != null) {
			billingAndInvoiceDetailsList = billingAndInvoiceDaoInterface
					.listArchivedInvoicesBySequenceNumber(billingAndInvoiceRequest.getCustomerSequenceNumber());
		} else {
			billingAndInvoiceDetailsList = billingAndInvoiceDaoInterface
					.listAllArchivedInvoices();
		}

		return listInvoices(billingAndInvoiceDetailsList);
	}

	@Transactional
	@Override
	public BillingAndInvoiceResponse getInvoiceDetail(BillingAndInvoiceRequest billingAndInvoiceRequest)
			throws TokenExpiredException, InvalidInputParametersException {
		tokenDao.checkTokenValidity(billingAndInvoiceRequest.getCustomerSequenceNumber(),
				billingAndInvoiceRequest.getExpirationDuration());

		Optional<BillingAndInvoiceDetails> billingAndInvoiceDetailsList = billingAndInvoiceDaoInterface.getInvoiceDetail(
				billingAndInvoiceRequest.getCustomerSequenceNumber(), billingAndInvoiceRequest.getInvoiceNumber());

		if (billingAndInvoiceDetailsList != null && !billingAndInvoiceDetailsList.isEmpty()) {
			BillingAndInvoiceDetails billingAndInvoiceDetails = billingAndInvoiceDetailsList.get();

			return createResponseForBillingAndInvoiceEntity(billingAndInvoiceDetails);
		} else {
			throw new InvalidInputParametersException(
					BillingAndInvoiceDetailsConstant.detailsNotExistedWithThisInvoiceNumberErrorCode,
					BillingAndInvoiceDetailsConstant.detailsNotExistedWithThisInvoiceNumberErrorMessage);
		}
	}

	@Transactional
	@Override
	public void deleteInvoice(BillingAndInvoiceRequest billingAndInvoiceRequest)
			throws InvalidInputParametersException, TokenExpiredException {
		tokenDao.checkTokenValidity(billingAndInvoiceRequest.getCustomerSequenceNumber(),
				billingAndInvoiceRequest.getExpirationDuration());

		if (billingAndInvoiceRequest.getInvoiceNumber() == null) {
			List<BillingAndInvoiceDetails> billingAndInvoiceDetailsList = billingAndInvoiceDaoInterface
					.listAllInvoice(billingAndInvoiceRequest.getCustomerSequenceNumber());

			if (billingAndInvoiceDetailsList != null && !billingAndInvoiceDetailsList.isEmpty()) {
				billingAndInvoiceDaoInterface
						.deleteBySequenceNumber(billingAndInvoiceRequest.getCustomerSequenceNumber());
			} else {
				throw new InvalidInputParametersException(BillingAndInvoiceDetailsConstant.detailsNotExistedErrorCode,
						BillingAndInvoiceDetailsConstant.detailsNotExistedMessage);
			}

		} else {

			Optional<BillingAndInvoiceDetails> billingAndInvoiceDetailsList = billingAndInvoiceDaoInterface.getInvoiceDetail(
					billingAndInvoiceRequest.getCustomerSequenceNumber(), billingAndInvoiceRequest.getInvoiceNumber());

			if (billingAndInvoiceDetailsList != null && !billingAndInvoiceDetailsList.isEmpty()) {
				billingAndInvoiceDaoInterface.deleteBySequenceNumberAndInvoiceNumber(
						billingAndInvoiceRequest.getCustomerSequenceNumber(),
						billingAndInvoiceRequest.getInvoiceNumber());
			} else {
				throw new InvalidInputParametersException(BillingAndInvoiceDetailsConstant.detailsNotExistedErrorCode,
						BillingAndInvoiceDetailsConstant.detailsNotExistedMessage);
			}
		}
	}

	@Transactional
	@Override
	public List<BillingAndInvoiceResponse> archiveInvoices(BillingAndInvoiceRequest billingAndInvoiceRequest)
			throws TokenExpiredException, InvalidInputParametersException {
		tokenDao.checkTokenValidity(billingAndInvoiceRequest.getCustomerSequenceNumber(),
				billingAndInvoiceRequest.getExpirationDuration());

		List<BillingAndInvoiceDetails> billingAndInvoiceDetailsList = billingAndInvoiceDaoInterface
				.findByInvoiceNumberList(billingAndInvoiceRequest.getInvoiceNumberList());

		return archiveInvoices(billingAndInvoiceDetailsList);
	}

	@Transactional
	@Override
	public List<BillingAndInvoiceResponse> archiveInvoicesForCustomer(BillingAndInvoiceRequest billingAndInvoiceRequest)
			throws TokenExpiredException, InvalidInputParametersException {
		tokenDao.checkTokenValidity(billingAndInvoiceRequest.getCustomerSequenceNumber(),
				billingAndInvoiceRequest.getExpirationDuration());

		List<BillingAndInvoiceDetails> billingAndInvoiceDetailsList = billingAndInvoiceDaoInterface
				.findByInvoiceNumberListAndSequenceNumber(billingAndInvoiceRequest.getInvoiceNumberList(),
						billingAndInvoiceRequest.getCustomerSequenceNumber());

		return archiveInvoices(billingAndInvoiceDetailsList);
	}

	private List<BillingAndInvoiceResponse> archiveInvoices(List<BillingAndInvoiceDetails> billingAndInvoiceDetailsList)
			throws InvalidInputParametersException {
		List<BillingAndInvoiceResponse> billingAndInvoiceResponseList = new ArrayList<>();
		if (billingAndInvoiceDetailsList != null) {

			for (BillingAndInvoiceDetails billingAndInvoiceDetails : billingAndInvoiceDetailsList) {
				billingAndInvoiceDetails.setArchived(true);
				BillingAndInvoiceResponse productBrandResponse = createResponseForBillingAndInvoiceEntity(
						billingAndInvoiceDetails);
				billingAndInvoiceResponseList.add(productBrandResponse);
			}
		}
		billingAndInvoiceDaoInterface.archiveInvoices(billingAndInvoiceDetailsList);
		return billingAndInvoiceResponseList;
	}

	private List<BillingAndInvoiceResponse> listInvoices(List<BillingAndInvoiceDetails> billingAndInvoiceDetailsList) {
		List<BillingAndInvoiceResponse> billingAndInvoiceResponseList = new ArrayList<>();
		if (billingAndInvoiceDetailsList != null) {

			for (BillingAndInvoiceDetails billingAndInvoiceDetails : billingAndInvoiceDetailsList) {
				BillingAndInvoiceResponse productBrandResponse = createResponseForBillingAndInvoiceEntity(
						billingAndInvoiceDetails);
				billingAndInvoiceResponseList.add(productBrandResponse);
			}
		}
		return billingAndInvoiceResponseList;
	}

	private BillingAndInvoiceResponse createResponseForBillingAndInvoiceEntity(
			BillingAndInvoiceDetails billingAndInvoiceDetails) {

		BillingAndInvoiceResponse billingAndInvoiceResponse = new BillingAndInvoiceResponse();
		billingAndInvoiceResponse.setCustomerSequenceNumber(billingAndInvoiceDetails.getCustomerSequenceNumber());
		billingAndInvoiceResponse.setInvoiceNumber(billingAndInvoiceDetails.getInvoiceNumber());
		billingAndInvoiceResponse.setOrderSequenceNumber(billingAndInvoiceDetails.getOrderSequenceNumber());
		billingAndInvoiceResponse.setInvoiceDate(billingAndInvoiceDetails.getInvoiceDate());
		billingAndInvoiceResponse.setCurrency(billingAndInvoiceDetails.getCurrency());
		billingAndInvoiceResponse.setDateOfPayment(billingAndInvoiceDetails.getDateOfPayment());
		billingAndInvoiceResponse.setDueDate(billingAndInvoiceDetails.getDueDate());
		billingAndInvoiceResponse.setModeOfPayment(billingAndInvoiceDetails.getModeOfPayment());
		billingAndInvoiceResponse.setStatus(billingAndInvoiceDetails.getStatus());
		billingAndInvoiceResponse.setSuccess(true);
		return billingAndInvoiceResponse;
	}

}
