package com.insignia.validations;

import com.insignia.constants.AppPreferenceConstants;
import com.insignia.customExceptions.InvalidInputParametersException;

public class AppPreferenceDetailsValidation {

	public static void validateApplicationName(String applicationName, int length)
			throws InvalidInputParametersException {
		if (applicationName != null && applicationName.length() > length) {
			throw new InvalidInputParametersException(AppPreferenceConstants.validApplicationNameLengthErrorCode,
					AppPreferenceConstants.validApplicationNameLength);

		}

	}

	public static void validatePreferenceValue(String preferenceValue, int length)
			throws InvalidInputParametersException {
		if (preferenceValue != null && preferenceValue.length() > length) {
			throw new InvalidInputParametersException(AppPreferenceConstants.validPreferenceValueLengthErrorCode,
					AppPreferenceConstants.validPreferenceValueLength);
		}
	}

	public static void validatePreferenceType(String preferenceType, int length)
			throws InvalidInputParametersException {
		if (preferenceType != null && preferenceType.length() > length) {
			throw new InvalidInputParametersException(AppPreferenceConstants.validPreferenceTypeLengthErrorCode,
					AppPreferenceConstants.validPreferenceTypeLength);
		}
	}

}
