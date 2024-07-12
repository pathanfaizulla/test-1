package com.insignia.validations;

import java.math.BigDecimal;

import com.insignia.constants.OrderValidatorConstant;
import com.insignia.customExceptions.InvalidInputParametersException;

public class OrderValidator {

	public static void validateOrderId(Long OrderId, int length) throws InvalidInputParametersException {
		if (OrderId == null) {
			throw new InvalidInputParametersException(OrderValidatorConstant.validOrderIdErrorCode,
					OrderValidatorConstant.validOrderId);

		} else if (OrderId > length) {
			throw new InvalidInputParametersException(OrderValidatorConstant.validOrderIdLengthErrorCode,
					OrderValidatorConstant.validOrderIdLength);
		}
	}

	public static void validateOrderStatus(String OrderStatus, int length) throws InvalidInputParametersException {
		if (OrderStatus == null || OrderStatus == "" || OrderStatus.isBlank()) {
			throw new InvalidInputParametersException(OrderValidatorConstant.validOrder_statusErrorCode,
					OrderValidatorConstant.validOrder_status);

		} else if (OrderStatus.length() > length) {
			throw new InvalidInputParametersException(OrderValidatorConstant.validOrderLengthErrorCode,
					OrderValidatorConstant.validOrder_statusLength);

		}

	}

	public static void validInvoiceId(String InvoiceId, int length) throws InvalidInputParametersException {
		if (InvoiceId == null || InvoiceId == "" || InvoiceId.isBlank()) {
			throw new InvalidInputParametersException(OrderValidatorConstant.validInvoiceIdErrorCode,
					OrderValidatorConstant.validInvoiceId);

		} else if (InvoiceId.length() > length) {
			throw new InvalidInputParametersException(OrderValidatorConstant.validInvoiceIdLengthErrorCode,
					OrderValidatorConstant.validInvoiceIdLength);
		}
	}

	public static void validateTotalPrice(BigDecimal totalPrice, int length) throws InvalidInputParametersException {
		String value = String.valueOf(totalPrice);

		if ((totalPrice != null && value.length() > length) || !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(OrderValidatorConstant.validTotalPriceLengthErrorCode,
					OrderValidatorConstant.validTotalPriceLength);
		}
	}

	public static void validatePerUnitPrice(BigDecimal perUnitPrice, int length)
			throws InvalidInputParametersException {
		String value = String.valueOf(perUnitPrice);

		if ((perUnitPrice != null && value.length() > length) || !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(OrderValidatorConstant.validatePerUnitPriceLengthMessageErrorCode,
					OrderValidatorConstant.validatePerUnitPriceLengthMessage);
		}
	}

}
