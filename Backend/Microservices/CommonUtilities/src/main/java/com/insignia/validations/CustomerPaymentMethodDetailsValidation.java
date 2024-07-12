package com.insignia.validations;

import com.insignia.constants.CustomerPaymentMethodDetailsConstant;
import com.insignia.customExceptions.InvalidInputParametersException;

public class CustomerPaymentMethodDetailsValidation {

	public static void validateApplicationId(String applicationId, int length) throws InvalidInputParametersException {

		if (applicationId == null || applicationId == "" || applicationId.isBlank()) {
			throw new InvalidInputParametersException(CustomerPaymentMethodDetailsConstant.validApplicationIdErrorCode,
					CustomerPaymentMethodDetailsConstant.validApplicationId);

		} else if (applicationId.length() > length) {
			throw new InvalidInputParametersException(
					CustomerPaymentMethodDetailsConstant.validApplicationIdLengthErrorCode,
					CustomerPaymentMethodDetailsConstant.validApplicationIdLength);

		} else if (!applicationId.matches(CustomerPaymentMethodDetailsConstant.regularExpression)) {
			throw new InvalidInputParametersException(
					CustomerPaymentMethodDetailsConstant.validApplicationIdInvalidCharactersErrorCode,
					CustomerPaymentMethodDetailsConstant.validApplicationIdExpression);

		}

	}

	public static void validateTenantId(String tenantId, int length) throws InvalidInputParametersException {

		if (tenantId == null || tenantId == "" || tenantId.isBlank()) {
			throw new InvalidInputParametersException(CustomerPaymentMethodDetailsConstant.validTenantIdErrorCode,
					CustomerPaymentMethodDetailsConstant.validTenantId);

		} else if (tenantId.length() > length) {
			throw new InvalidInputParametersException(CustomerPaymentMethodDetailsConstant.validTenantIdLengthErrorCode,
					CustomerPaymentMethodDetailsConstant.validTenantIdLength);

		} else if (!tenantId.matches(CustomerPaymentMethodDetailsConstant.regularExpression)) {
			throw new InvalidInputParametersException(
					CustomerPaymentMethodDetailsConstant.validTenantIdInvalidCharactersErrorCode,
					CustomerPaymentMethodDetailsConstant.validTenantIdExpression);
		}

	}

	public static void validateCustomerId(String customerId, int length) throws InvalidInputParametersException {

		if (customerId == null || customerId == "" || customerId.isBlank()) {
			throw new InvalidInputParametersException(CustomerPaymentMethodDetailsConstant.validCustomerIdErrorCode,
					CustomerPaymentMethodDetailsConstant.validCustomerId);

		} else if (customerId.length() > length) {
			throw new InvalidInputParametersException(
					CustomerPaymentMethodDetailsConstant.validCustomerIdLengthErrorCode,
					CustomerPaymentMethodDetailsConstant.validCustomerIdLength);

		} else if (!customerId.matches(CustomerPaymentMethodDetailsConstant.regularExpression)) {
			throw new InvalidInputParametersException(
					CustomerPaymentMethodDetailsConstant.validCustomerIdInvalidCharactersErrorCode,
					CustomerPaymentMethodDetailsConstant.validCustomerIdExpression);
		}

	}

	public static void validatePaymentMethodType(String paymentMethodType, int length)
			throws InvalidInputParametersException {

		if (paymentMethodType == null || paymentMethodType == "" || paymentMethodType.isBlank()) {
			throw new InvalidInputParametersException(
					CustomerPaymentMethodDetailsConstant.validPaymentMethodTypeErrorCode,
					CustomerPaymentMethodDetailsConstant.validPaymentMethodType);

		} else if (paymentMethodType.length() > length) {
			throw new InvalidInputParametersException(
					CustomerPaymentMethodDetailsConstant.validPaymentMethodTypeLengthErrorCode,
					CustomerPaymentMethodDetailsConstant.validPaymentMethodTypeLength);

		} else if (!paymentMethodType.matches(CustomerPaymentMethodDetailsConstant.regularExpression1)) {
			throw new InvalidInputParametersException(
					CustomerPaymentMethodDetailsConstant.validPaymentMethodTypeInvalidCharactersErrorCode,
					CustomerPaymentMethodDetailsConstant.validPaymentMethodTypeExpression);
		}

	}

	public static void validatePaymentMethodDetail(String paymentMethodDetail, int length)
			throws InvalidInputParametersException {

		if (paymentMethodDetail.length() > length) {
			throw new InvalidInputParametersException(
					CustomerPaymentMethodDetailsConstant.validPaymentMethodTypeLengthErrorCode,
					CustomerPaymentMethodDetailsConstant.validPaymentMethodTypeLength);

		} else if (!paymentMethodDetail.matches(CustomerPaymentMethodDetailsConstant.regularExpression1)) {
			throw new InvalidInputParametersException(
					CustomerPaymentMethodDetailsConstant.validPaymentMethodTypeInvalidCharactersErrorCode,
					CustomerPaymentMethodDetailsConstant.validPaymentMethodTypeExpression);
		}

	}

	public static void validatePaymentMethodId(String paymentMethodId, int length)
			throws InvalidInputParametersException {

		if (paymentMethodId == null || paymentMethodId == "" || paymentMethodId.isBlank()) {
			throw new InvalidInputParametersException(
					CustomerPaymentMethodDetailsConstant.validPaymentMethodIdErrorCode,
					CustomerPaymentMethodDetailsConstant.validPaymentMethodId);

		} else if (paymentMethodId.length() > length) {
			throw new InvalidInputParametersException(
					CustomerPaymentMethodDetailsConstant.validPaymentMethodIdLengthErrorCode,
					CustomerPaymentMethodDetailsConstant.validPaymentMethodIdLength);

		} else if (!paymentMethodId.matches(CustomerPaymentMethodDetailsConstant.regularExpression2)) {
			throw new InvalidInputParametersException(
					CustomerPaymentMethodDetailsConstant.validPaymentMethodIdInvalidCharactersErrorCode,
					CustomerPaymentMethodDetailsConstant.validPaymentMethodIdExpression);
		}

	}

	public static void validateValidFrom(String validFrom, int length) throws InvalidInputParametersException {

		if (validFrom != null && validFrom.length() != length) {
			throw new InvalidInputParametersException(
					CustomerPaymentMethodDetailsConstant.validateValidFromDateErrorCode,
					CustomerPaymentMethodDetailsConstant.validateValidFromDateMessage);

		} else if (validFrom != null
				&& !validFrom.matches(CustomerPaymentMethodDetailsConstant.regularForValidFromAndValidUpto)) {
			throw new InvalidInputParametersException(
					CustomerPaymentMethodDetailsConstant.validateValidFromDateErrorCode,
					CustomerPaymentMethodDetailsConstant.validateValidFromDateMessage);
		}

	}

	public static void validateValidUpto(String validUpto, int length) throws InvalidInputParametersException {

		if (validUpto != null && validUpto.length() != length) {
			throw new InvalidInputParametersException(
					CustomerPaymentMethodDetailsConstant.validateValidUptoDateErrorCode,
					CustomerPaymentMethodDetailsConstant.validateValidUptoDateMessage);

		} else if (validUpto != null
				&& !validUpto.matches(CustomerPaymentMethodDetailsConstant.regularForValidFromAndValidUpto)) {
			throw new InvalidInputParametersException(
					CustomerPaymentMethodDetailsConstant.validateValidUptoDateErrorCode,
					CustomerPaymentMethodDetailsConstant.validateValidUptoDateMessage);
		}

	}

}
