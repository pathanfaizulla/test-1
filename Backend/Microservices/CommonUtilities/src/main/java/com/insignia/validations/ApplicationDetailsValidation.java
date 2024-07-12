package com.insignia.validations;

import com.insignia.constants.ApplicationConstants;
import com.insignia.customExceptions.InvalidInputParametersException;

public class ApplicationDetailsValidation {

	public static void validateApplicationName(String applicationName, int length)
			throws InvalidInputParametersException {
		if (applicationName == null || applicationName == "" || applicationName.isBlank()) {
			throw new InvalidInputParametersException(ApplicationConstants.validApplicationNameErrorCode,
					ApplicationConstants.validApplicationName);

		} else if (applicationName.length() > length) {
			throw new InvalidInputParametersException(ApplicationConstants.validApplicationNameLengthErrorCode,
					ApplicationConstants.validApplicationNameLength);
		}

	}

	public static void validateApplicationDescrption(String applicationDescription, int length)
			throws InvalidInputParametersException {
		if (applicationDescription != null && applicationDescription.length() > length) {
			throw new InvalidInputParametersException(ApplicationConstants.validApplicationDescriptionLengthErrorCode,
					ApplicationConstants.validApplicationDescriptionLength);
		}
	}

}
