package com.insignia.validations;

import com.insignia.constants.AddressDetailsConstants;
import com.insignia.customExceptions.InvalidInputParametersException;

public class AddressDetailsValidation {

	public static void validateAddressLine1(String addressLine1, int length, boolean isToCheckBlank)
			throws InvalidInputParametersException {
		if (isToCheckBlank && (addressLine1 == null || addressLine1.trim().isEmpty())) {
			throw new InvalidInputParametersException(AddressDetailsConstants.validAddressLine1ErrorCode,
					AddressDetailsConstants.validAddressLine1);

		} else if (addressLine1 != null && addressLine1.length() > length) {
			throw new InvalidInputParametersException(AddressDetailsConstants.validAddressLine1LengthErrorCode,
					AddressDetailsConstants.validAddressLine1Length);
		}

	}

	public static void validateAddressLine2(String addressLine2, int length) throws InvalidInputParametersException {
		if (addressLine2 != null && addressLine2.length() > length) {
			throw new InvalidInputParametersException(AddressDetailsConstants.validAddressLine2LengthErrorCode,
					AddressDetailsConstants.validAddressLine2Length);

		}

	}

	public static void validateCity(String city, int length, boolean isToCheckBlank)
			throws InvalidInputParametersException {
		if (isToCheckBlank && (city == null || city.trim().isEmpty())) {
			throw new InvalidInputParametersException(AddressDetailsConstants.validCityErrorCode,
					AddressDetailsConstants.validCity);

		} else if (city != null && city.length() > length) {
			throw new InvalidInputParametersException(AddressDetailsConstants.validCityLengthErrorCode,
					AddressDetailsConstants.validCityLength);

		}
	}

	public static void validateState(String state, int length, boolean isToCheckBlank)
			throws InvalidInputParametersException {
		if (isToCheckBlank && (state == null || state.trim().isEmpty())) {
			throw new InvalidInputParametersException(AddressDetailsConstants.validStateErrorCode,
					AddressDetailsConstants.validState);

		} else if (state != null && state.length() > length) {
			throw new InvalidInputParametersException(AddressDetailsConstants.validStateLengthErrorCode,
					AddressDetailsConstants.validStateLength);

		}
	}

	public static void validateCountry(String country, int length, boolean isToCheckBlank)
			throws InvalidInputParametersException {
		if (isToCheckBlank && (country == null || country.trim().isEmpty())) {
			throw new InvalidInputParametersException(AddressDetailsConstants.validCountryErrorCode,
					AddressDetailsConstants.validCountry);

		} else if (country != null && country.length() > length) {
			throw new InvalidInputParametersException(AddressDetailsConstants.validCountryLengthErrorCode,
					AddressDetailsConstants.validCountryLength);

		}
	}

	public static void validateZipCode(String zipCode, int length, boolean isToCheckBlank)
			throws InvalidInputParametersException {
		if (isToCheckBlank && (zipCode == null || zipCode.trim().isEmpty())) {
			throw new InvalidInputParametersException(AddressDetailsConstants.validZipCodeErrorCode,
					AddressDetailsConstants.validZipCode);

		} else if (zipCode != null && zipCode.length() > length) {
			throw new InvalidInputParametersException(AddressDetailsConstants.validZipCodeLengthErrorCode,
					AddressDetailsConstants.validZipCodeLength);

		} else if (zipCode != null && !zipCode.matches(AddressDetailsConstants.regularExpressionZipcode)) {
			throw new InvalidInputParametersException(AddressDetailsConstants.validZipCodeInvalidCharactersErrorCode,
					AddressDetailsConstants.validZipCodeExpression);
		}
	}

	public static void validateLandmark(String landmark, int length) throws InvalidInputParametersException {
		if (landmark != null && landmark.length() > length) {
			throw new InvalidInputParametersException(AddressDetailsConstants.validLandMarkLengthErrorCode,
					AddressDetailsConstants.validLandMarkLength);
		}
	}

	public static void validateMobileNumber(String mobileNumber, int length) throws InvalidInputParametersException {
		if (mobileNumber != null && mobileNumber.length() > length) {
			throw new InvalidInputParametersException(AddressDetailsConstants.validMobileNumberLengthErrorCode,
					AddressDetailsConstants.validMobileNumberLength);
		} else if (mobileNumber != null && !mobileNumber.matches(AddressDetailsConstants.regularNumbericExpression)) {
			throw new InvalidInputParametersException(AddressDetailsConstants.validMobileNumberCharactersErrorCode,
					AddressDetailsConstants.validMobileNumberCharacters);
		}
	}

	public static void validateLandLineNumber(String landlineNumber, int length)
			throws InvalidInputParametersException {
		if (landlineNumber != null && landlineNumber.length() > length) {
			throw new InvalidInputParametersException(AddressDetailsConstants.validLandLineNumberLengthErrorCode,
					AddressDetailsConstants.validLandLineNumberLength);
		} else if (landlineNumber != null
				&& !landlineNumber.matches(AddressDetailsConstants.regularNumbericExpression)) {
			throw new InvalidInputParametersException(AddressDetailsConstants.validLandLineNumberCharactersErrorCode,
					AddressDetailsConstants.validLandLineNumberCharacters);
		}
	}

	public static void validateEmailId(String emailId, int length) throws InvalidInputParametersException {
		if (emailId != null && emailId.length() > length) {
			throw new InvalidInputParametersException(AddressDetailsConstants.validEmailIdErrorCode,
					AddressDetailsConstants.validEmailIdLength);
		} else if (emailId != null && !emailId.matches(AddressDetailsConstants.regularExpressionZipcode)) {
			throw new InvalidInputParametersException(AddressDetailsConstants.validEmailIdInvalidCharactersErrorCode,
					AddressDetailsConstants.validEmailIdExpression);
		}
	}

}
