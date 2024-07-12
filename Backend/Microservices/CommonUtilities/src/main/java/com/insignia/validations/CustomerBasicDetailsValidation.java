package com.insignia.validations;

import com.insignia.constants.CustomerBasicDetailsConstants;
import com.insignia.customExceptions.InvalidInputParametersException;

public class CustomerBasicDetailsValidation {

	public static void validateCustomerId(String customerId, int length, boolean checkNullValue)
			throws InvalidInputParametersException {

		if (checkNullValue && (customerId == null || customerId.trim().isEmpty())) {
			throw new InvalidInputParametersException(CustomerBasicDetailsConstants.validCustomerIdErrorCode,
					CustomerBasicDetailsConstants.validCustomerIdMessage);

		} else if (customerId != null && customerId.length() > length) {
			throw new InvalidInputParametersException(CustomerBasicDetailsConstants.validCustomerIdLengthErrorCode,
					CustomerBasicDetailsConstants.validCustomerIdLengthMessage);

		}

	}
	
	public static void validateUserId(String userId, int length) throws InvalidInputParametersException {

		if (userId == null || userId.trim().isEmpty()) {
			throw new InvalidInputParametersException(CustomerBasicDetailsConstants.validUserIdErrorCode,
					CustomerBasicDetailsConstants.validUserId);

		} else if (userId.length() > length) {
			throw new InvalidInputParametersException(CustomerBasicDetailsConstants.validUserIdLengthErrorCode,
					CustomerBasicDetailsConstants.validUserIdLength);

		} else if (!userId.matches(CustomerBasicDetailsConstants.regularExpression)) {
			throw new InvalidInputParametersException(
					CustomerBasicDetailsConstants.validUserIDInvalidCharactersErrorCode,
					CustomerBasicDetailsConstants.validUserIdExpression);

		}

	}

	public static void validatePassword(String password, int length) throws InvalidInputParametersException {

		if (password == null || password.trim().isEmpty()) {
			throw new InvalidInputParametersException(CustomerBasicDetailsConstants.validPasswordErrorCode,
					CustomerBasicDetailsConstants.validPassword);

		} else if (password.length() > length) {
			throw new InvalidInputParametersException(CustomerBasicDetailsConstants.validPasswordLengthErrorCode,
					CustomerBasicDetailsConstants.validPasswordLength);

		} else if (!password.matches(CustomerBasicDetailsConstants.regularExpression)) {
			throw new InvalidInputParametersException(
					CustomerBasicDetailsConstants.validPasswordInvalidCharactersErrorCode,
					CustomerBasicDetailsConstants.validPasswordExpression);

		}

	}

	public static void validateNewPassword(String newPassword, int maxLength, int minimumLength)
			throws InvalidInputParametersException {

		if (newPassword == null || newPassword.trim().isEmpty()) {
			throw new InvalidInputParametersException(CustomerBasicDetailsConstants.validNewPasswordErrorCode,
					CustomerBasicDetailsConstants.validNewPassword);

		} else if (newPassword.length() > maxLength) {
			throw new InvalidInputParametersException(CustomerBasicDetailsConstants.validNewPasswordLengthErrorCode,
					CustomerBasicDetailsConstants.validNewPasswordMaxLength);

		} else if (!newPassword.matches(CustomerBasicDetailsConstants.regularExpression)) {
			throw new InvalidInputParametersException(
					CustomerBasicDetailsConstants.validNewPasswordInvalidCharactersErrorCode,
					CustomerBasicDetailsConstants.validNewPasswordExpression);

		} else if (newPassword.length() < minimumLength) {
			throw new InvalidInputParametersException(CustomerBasicDetailsConstants.validNewPasswordLengthErrorCode,
					CustomerBasicDetailsConstants.validNewPasswordMinLength);

		}

	}



	public static void validateCustomerEmail(String customerEmail, int length) throws InvalidInputParametersException {

		if (customerEmail != null && customerEmail.length() > length) {
			throw new InvalidInputParametersException(CustomerBasicDetailsConstants.validCustomerEmailLengthErrorCode,
					CustomerBasicDetailsConstants.validCustomerEmailLength);

		} else if (customerEmail != null && customerEmail != null
				&& !customerEmail.matches(CustomerBasicDetailsConstants.regularExpressionForEmail)) {
			throw new InvalidInputParametersException(
					CustomerBasicDetailsConstants.validCustomerEmailInvalidCharactersErrorCode,
					CustomerBasicDetailsConstants.validCustomerEmailExpression);
		}

	}

	public static void validateUsername(String username, int length) throws InvalidInputParametersException {

		if (username != null && username.length() > length) {
			throw new InvalidInputParametersException(CustomerBasicDetailsConstants.validUserNameLengthErrorCode,
					CustomerBasicDetailsConstants.validUserNameLength);

		} else if (username != null && !username.matches(CustomerBasicDetailsConstants.regularExpression)) {
			throw new InvalidInputParametersException(
					CustomerBasicDetailsConstants.validUserNameInvalidCharactersErrorCode,
					CustomerBasicDetailsConstants.validUserNameExpression);
		}

	}
}
