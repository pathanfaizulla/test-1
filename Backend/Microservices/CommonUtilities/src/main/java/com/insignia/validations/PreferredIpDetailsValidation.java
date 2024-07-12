package com.insignia.validations;

import com.insignia.constants.PreferredIpDetailsContants;
import com.insignia.customExceptions.InvalidInputParametersException;

public class PreferredIpDetailsValidation {

	public static String validateIpDetails(String ipDetails) throws InvalidInputParametersException {
		String ipType = null;
		if (ipDetails == null || ipDetails.trim().isEmpty()) {
			throw new InvalidInputParametersException(PreferredIpDetailsContants.validateNullIPDetailsErrorCode,
					PreferredIpDetailsContants.validateNullIPDetailsErrorMessage);

		} else if (IPAddressValidator.isValidIPv4(ipDetails)) {
			ipType = "IPv4";
		} else if (IPAddressValidator.isValidIPv6(ipDetails)) {
			ipType = "IPv6";
		} else {
			throw new InvalidInputParametersException(PreferredIpDetailsContants.validateIpDetailsErrorCode,
					PreferredIpDetailsContants.validateIpDetailsErrorMessage);

		}
		return ipType;

	}
}
