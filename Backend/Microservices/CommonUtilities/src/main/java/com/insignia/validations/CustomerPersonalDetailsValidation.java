package com.insignia.validations;

import com.insignia.constants.CustomerPersonalDetailsConstants;
import com.insignia.customExceptions.InvalidInputParametersException;

public class CustomerPersonalDetailsValidation {

	public static void validateFirstName(String firstName, int length) throws InvalidInputParametersException {

		if (firstName == null || firstName == "" || firstName.isBlank()) {
			throw new InvalidInputParametersException(CustomerPersonalDetailsConstants.validFirstNameErrorCode,
					CustomerPersonalDetailsConstants.validFirstName);

		} else if (firstName.length() > length) {
			throw new InvalidInputParametersException(CustomerPersonalDetailsConstants.validFirstNameLengthErrorCode,
					CustomerPersonalDetailsConstants.validFirstNameLength);

		} else if (!firstName.matches(CustomerPersonalDetailsConstants.regularExpression)) {
			throw new InvalidInputParametersException(
					CustomerPersonalDetailsConstants.validFirstNameInvalidCharactersErrorCode,
					CustomerPersonalDetailsConstants.validFirstNameExpression);

		}

	}

	public static void validateLastName(String lastName, int length) throws InvalidInputParametersException {

		if (lastName == null || lastName == "" || lastName.isBlank()) {
			throw new InvalidInputParametersException(CustomerPersonalDetailsConstants.validLastNameErrorCode,
					CustomerPersonalDetailsConstants.validLastName);

		} else if (lastName.length() > length) {
			throw new InvalidInputParametersException(CustomerPersonalDetailsConstants.validLastNameLengthErrorCode,
					CustomerPersonalDetailsConstants.validLastNameLength);

		} else if (!lastName.matches(CustomerPersonalDetailsConstants.regularExpression)) {
			throw new InvalidInputParametersException(
					CustomerPersonalDetailsConstants.validLastNameInvalidCharactersErrorCode,
					CustomerPersonalDetailsConstants.validLastNameExpression);

		}

	}

	public static void validateMiddleName(String middleName, int length) throws InvalidInputParametersException {

		if (middleName != null && middleName.length() > length) {
			throw new InvalidInputParametersException(CustomerPersonalDetailsConstants.validMiddleNameLengthErrorCode,
					CustomerPersonalDetailsConstants.validMiddleNameLength);

		}

	}

	public static void validateAge(String age, int length) throws InvalidInputParametersException {

		if (age != null && age.length() > length) {
			throw new InvalidInputParametersException(CustomerPersonalDetailsConstants.validAgeLengthErrorCode,
					CustomerPersonalDetailsConstants.validAgeLength);

		} else if (age != null && !age.matches(CustomerPersonalDetailsConstants.regularNumbericExpression)) {
			throw new InvalidInputParametersException(
					CustomerPersonalDetailsConstants.validAgeInvalidCharactersErrorCode,
					CustomerPersonalDetailsConstants.validAgeExpression);
		}

	}

	public static void validateGender(String gender, int length) throws InvalidInputParametersException {

		if (gender != null && gender.length() > length) {
			throw new InvalidInputParametersException(CustomerPersonalDetailsConstants.validGenderLengthErrorCode,
					CustomerPersonalDetailsConstants.validGenderLength);

		}

	}

	public static void validateCustomerEmailId(String customerEmailId, int length)
			throws InvalidInputParametersException {

		if (customerEmailId != null && customerEmailId.length() > length) {
			throw new InvalidInputParametersException(CustomerPersonalDetailsConstants.validAgeLengthErrorCode,
					CustomerPersonalDetailsConstants.validAgeLength);

		} else if (customerEmailId != null
				&& !customerEmailId.matches(CustomerPersonalDetailsConstants.regularExpression)) {
			throw new InvalidInputParametersException(
					CustomerPersonalDetailsConstants.validCustomerEmailIdInvalidCharactersErrorCode,
					CustomerPersonalDetailsConstants.validCustomerEmailIdExpression);
		}

	}

	public static void validateAlternativeEmailId(String alternativeEmailId, int length)
			throws InvalidInputParametersException {

		if (alternativeEmailId != null && alternativeEmailId.length() > length) {
			throw new InvalidInputParametersException(
					CustomerPersonalDetailsConstants.validAlternativeEmailIdLengthErrorCode,
					CustomerPersonalDetailsConstants.validAlternativeEmailIdLength);

		} else if (alternativeEmailId != null
				&& !alternativeEmailId.matches(CustomerPersonalDetailsConstants.regularExpression)) {
			throw new InvalidInputParametersException(
					CustomerPersonalDetailsConstants.validAlternativeEmailIdInvalidCharactersErrorCode,
					CustomerPersonalDetailsConstants.validAlternativeEmailIdExpression);
		}

	}

	public static void validateCustomeMobileNumber(String customeMobileNumber, int length)
			throws InvalidInputParametersException {

		if (customeMobileNumber != null && customeMobileNumber.length() > length) {
			throw new InvalidInputParametersException(
					CustomerPersonalDetailsConstants.validCustomerMobileNumberLengthErrorCode,
					CustomerPersonalDetailsConstants.validCustomerMobileNumberLength);

		} else if (customeMobileNumber != null
				&& !customeMobileNumber.matches(CustomerPersonalDetailsConstants.regularNumbericExpression)) {
			throw new InvalidInputParametersException(
					CustomerPersonalDetailsConstants.validCustomerMobileNumberInvalidCharactersErrorCode,
					CustomerPersonalDetailsConstants.validCustomerMobileNumberExpression);
		}

	}

	public static void validateAlternativeMobileNumber(String alternativeMobileNumber, int length)
			throws InvalidInputParametersException {

		if (alternativeMobileNumber != null && alternativeMobileNumber.length() > length) {
			throw new InvalidInputParametersException(
					CustomerPersonalDetailsConstants.validAlternativeMobileNumberLengthErrorCode,
					CustomerPersonalDetailsConstants.validAlternativeMobileNumberLength);

		} else if (alternativeMobileNumber != null
				&& !alternativeMobileNumber.matches(CustomerPersonalDetailsConstants.regularNumbericExpression)) {
			throw new InvalidInputParametersException(
					CustomerPersonalDetailsConstants.validAlternativeMobileNumberInvalidCharactersErrorCode,
					CustomerPersonalDetailsConstants.validAlternativeMobileNumberExpression);
		}

	}

	public static void validateCustomerLandlineNumber(String customerlandlineNumber, int length)
			throws InvalidInputParametersException {

		if (customerlandlineNumber != null && customerlandlineNumber.length() > length) {
			throw new InvalidInputParametersException(
					CustomerPersonalDetailsConstants.validCustomerLandlineNumberLengthErrorCode,
					CustomerPersonalDetailsConstants.validCustomerLandlineNumberLength);

		} else if (customerlandlineNumber != null
				&& !customerlandlineNumber.matches(CustomerPersonalDetailsConstants.regularNumbericExpression)) {
			throw new InvalidInputParametersException(
					CustomerPersonalDetailsConstants.validCustomerLandlineNumberInvalidCharactersErrorCode,
					CustomerPersonalDetailsConstants.validCustomerLandlineNumberExpression);
		}

	}

	public static void validateCustomerSequenceNumber(Long customerSequenceNumber, int length)
			throws InvalidInputParametersException {
		if (customerSequenceNumber == null || customerSequenceNumber.toString().isBlank()) {
			throw new InvalidInputParametersException(
					CustomerPersonalDetailsConstants.validCustomerSequenceNumberErrorCode,
					CustomerPersonalDetailsConstants.validCustomerSequenceNumber);
		}

		String customerSequenceNumberStr = customerSequenceNumber.toString();

		if (customerSequenceNumberStr.length() < length) {
			throw new InvalidInputParametersException(
					CustomerPersonalDetailsConstants.validCustomerSequenceNumberLengthErrorCode,
					CustomerPersonalDetailsConstants.validCustomerSequenceNumberLength);
		}
	}

}
