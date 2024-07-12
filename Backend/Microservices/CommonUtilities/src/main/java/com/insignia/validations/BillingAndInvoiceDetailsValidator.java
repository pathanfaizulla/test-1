package com.insignia.validations;

import com.insignia.constants.BillingAndInvoiceDetailsConstant;
import com.insignia.constants.CommonConstant;
import com.insignia.customExceptions.InvalidInputParametersException;

public class BillingAndInvoiceDetailsValidator {

	public static void validateCustomerSequenceNumber(Long customerSequenceNumber, int length)
			throws InvalidInputParametersException {
		if (customerSequenceNumber == null || customerSequenceNumber.toString().isBlank()) {
			throw new InvalidInputParametersException(
					BillingAndInvoiceDetailsConstant.validCustomerSequenceNumberErrorCode,
					BillingAndInvoiceDetailsConstant.validCustomerSequenceNumberErrorMessage);
		}

		String customerSequenceNumberStr = customerSequenceNumber.toString();

		if (customerSequenceNumberStr.length() < length) {
			throw new InvalidInputParametersException(
					BillingAndInvoiceDetailsConstant.validCustomerSequenceNumberLengthErrorCode,
					BillingAndInvoiceDetailsConstant.validCustomerSequenceNumberErrorMessage);
		}
	}

	public static void validateOrderSequenceNumber(Long orderId) throws InvalidInputParametersException {

		if (orderId == null || orderId <= 0) {
			throw new InvalidInputParametersException(
					BillingAndInvoiceDetailsConstant.validOrderSequenceNumberErrorCode,
					BillingAndInvoiceDetailsConstant.validOrderSequenceNumberErrorMessage);

		}
	}

	public static void validateInvoiceDate(String invoiceDate) throws InvalidInputParametersException {

		if (invoiceDate != null && !invoiceDate.matches(CommonConstant.validateDDMMYYYYExpression)) {
			throw new InvalidInputParametersException(
					BillingAndInvoiceDetailsConstant.validInvoiceDateExpressionErrorCode,
					BillingAndInvoiceDetailsConstant.validInvoiceDateExpressionErrorMessage);

		}

	}

	public static void validateDueDate(String dueDate) throws InvalidInputParametersException {

		if (dueDate == null || !dueDate.matches(CommonConstant.validateDDMMYYYYExpression)) {
			throw new InvalidInputParametersException(BillingAndInvoiceDetailsConstant.validDueDateExpressionErrorCode,
					BillingAndInvoiceDetailsConstant.validDueDateExpressionErrorMessage);
		}
	}

	public static void validateDateofPayment(String dateOfPayment) throws InvalidInputParametersException {

		if (dateOfPayment != null && !dateOfPayment.matches(CommonConstant.validateDDMMYYYYExpression)) {
			throw new InvalidInputParametersException(
					BillingAndInvoiceDetailsConstant.validDateOfPaymentExpressionErrorCode,
					BillingAndInvoiceDetailsConstant.validDateOfPaymentExpressionErrorMessage);
		}
	}


}
