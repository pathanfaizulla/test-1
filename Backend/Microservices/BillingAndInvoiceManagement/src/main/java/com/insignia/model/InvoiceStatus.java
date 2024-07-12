package com.insignia.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.insignia.constants.BillingAndInvoiceDetailsConstant;
import com.insignia.customExceptions.InvalidInputParametersException;

public enum InvoiceStatus {
	DUE, PAID, CANCELLED;

	@JsonCreator
	public static InvoiceStatus fromValue(String userInput) throws InvalidInputParametersException {
		for (InvoiceStatus invoiceStatus : InvoiceStatus.values()) {
			if (String.valueOf(invoiceStatus).equals(userInput)) {
				return invoiceStatus;
			}
		}
		throw new InvalidInputParametersException(BillingAndInvoiceDetailsConstant.invalidInvoiceStatusErrorCode,
				BillingAndInvoiceDetailsConstant.invalidInvoiceStatusErrorMessage);
	}
}
