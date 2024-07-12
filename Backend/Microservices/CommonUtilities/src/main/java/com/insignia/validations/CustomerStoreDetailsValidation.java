package com.insignia.validations;

import com.insignia.constants.CustomerStoreDetailsConstants;
import com.insignia.customExceptions.InvalidInputParametersException;

public class CustomerStoreDetailsValidation {

	public static void validateMarkupFactor(String markupFactor, int length) throws InvalidInputParametersException {

		if (markupFactor != null && markupFactor.length() > length) {
			throw new InvalidInputParametersException(CustomerStoreDetailsConstants.validMarkupFactorLengthErrorCode,
					CustomerStoreDetailsConstants.validMarkupFactorLength);
		}
	}

	public static void validateHearAboutUs(String hearAboutUs, int length) throws InvalidInputParametersException {

		if (hearAboutUs != null && hearAboutUs.length() > length) {
			throw new InvalidInputParametersException(CustomerStoreDetailsConstants.validHearAboutUsLengthErrorCode,
					CustomerStoreDetailsConstants.validHearAboutUsLength);
		}
	}

	public static void validateMarkets(String markets, int length) throws InvalidInputParametersException {

		if (markets != null && markets.length() > length) {
			throw new InvalidInputParametersException(CustomerStoreDetailsConstants.validMarketsLengthErrorCode,
					CustomerStoreDetailsConstants.validMarketsLength);
		}
	}

	public static void validateStoreName(String storeName, int length) throws InvalidInputParametersException {

		if (storeName != null && storeName.length() > length) {
			throw new InvalidInputParametersException(CustomerStoreDetailsConstants.validStoreNameLengthErrorCode,
					CustomerStoreDetailsConstants.validStoreNameLength);
		}
	}

	public static void validateStoreContact(String storeContact, int length) throws InvalidInputParametersException {

		if (storeContact != null && storeContact.length() > length) {
			throw new InvalidInputParametersException(
					CustomerStoreDetailsConstants.validStoreContactCharactersErrorCode,
					CustomerStoreDetailsConstants.validStoreContactCharacters);
		} else if (storeContact != null
				&& !storeContact.matches(CustomerStoreDetailsConstants.regularMobileNumberExpression)) {
			throw new InvalidInputParametersException(
					CustomerStoreDetailsConstants.validStoreContactCharactersErrorCode,
					CustomerStoreDetailsConstants.validStoreContactCharacters);
		}
	}

	public static void validateStoreAddress(String storeAddress, int length) throws InvalidInputParametersException {

		if (storeAddress != null && storeAddress.length() > length) {
			throw new InvalidInputParametersException(CustomerStoreDetailsConstants.validStoreAddressLengthErrorCode,
					CustomerStoreDetailsConstants.validStoreAddressLength);
		}
	}

	public static void validateStoreCountry(String storeCountry, int length) throws InvalidInputParametersException {

		if (storeCountry != null && storeCountry.length() > length) {
			throw new InvalidInputParametersException(CustomerStoreDetailsConstants.validStoreCountryLengthErrorCode,
					CustomerStoreDetailsConstants.validStoreCountryLength);
		}
	}

	public static void validateStoreState(String storeState, int length) throws InvalidInputParametersException {

		if (storeState != null && storeState.length() > length) {
			throw new InvalidInputParametersException(CustomerStoreDetailsConstants.validStoreStateLengthErrorCode,
					CustomerStoreDetailsConstants.validStoreStateLength);
		}
	}

	public static void validateStoreCity(String storeCity, int length) throws InvalidInputParametersException {

		if (storeCity != null && storeCity.length() > length) {
			throw new InvalidInputParametersException(CustomerStoreDetailsConstants.validStoreCityLengthErrorCode,
					CustomerStoreDetailsConstants.validStoreCityLengthMessage);
		}
	}

	public static void validateStoreZipCode(String storeZipCode, int length) throws InvalidInputParametersException {

		if (storeZipCode != null && storeZipCode.length() > length) {
			throw new InvalidInputParametersException(CustomerStoreDetailsConstants.validStoreZipCodeLengthErrorCode,
					CustomerStoreDetailsConstants.validStoreZipCodeLengthMessage);
		} else if (storeZipCode != null
				&& !storeZipCode.matches(CustomerStoreDetailsConstants.regularExpressionZipcode)) {
			throw new InvalidInputParametersException(
					CustomerStoreDetailsConstants.validZipCodeInvalidCharactersErrorCode,
					CustomerStoreDetailsConstants.validZipCodeExpression);
		}
	}

	public static void validateTelephone(String telephone, int length) throws InvalidInputParametersException {

		if (telephone != null && telephone.length() > length) {
			throw new InvalidInputParametersException(CustomerStoreDetailsConstants.validTelephoneLengthErrorCode,
					CustomerStoreDetailsConstants.validTelephoneLengthMessage);
		} else if (telephone != null
				&& !telephone.matches(CustomerStoreDetailsConstants.regularMobileNumberExpression)) {
			throw new InvalidInputParametersException(CustomerStoreDetailsConstants.validTelephoneCharactersErrorCode,
					CustomerStoreDetailsConstants.validTelephoneCharactersMessage);
		}
	}

	public static void validateWebsite(String website, int length) throws InvalidInputParametersException {
		if (website != null && website.length() > length) {
			throw new InvalidInputParametersException(CustomerStoreDetailsConstants.validWebsiteLengthErrorCode,
					CustomerStoreDetailsConstants.validWebsiteLengthMessage);
		} else if (website != null && !(website.matches(CustomerStoreDetailsConstants.regularExpressionWebsite)
				|| website.matches(CustomerStoreDetailsConstants.regularExpressionWebsiteForHttps))) {
			throw new InvalidInputParametersException(CustomerStoreDetailsConstants.validWebsiteCharactersErrorCode,
					CustomerStoreDetailsConstants.validWebsiteCharactersMessage);
		}
	}

	public static void validateResaleLicense(String resaleLicense, int length) throws InvalidInputParametersException {

		if (resaleLicense != null && resaleLicense.length() > length) {
			throw new InvalidInputParametersException(CustomerStoreDetailsConstants.validWebsiteLengthErrorCode,
					CustomerStoreDetailsConstants.validResaleLicenseLengthMessage);
		} else if (resaleLicense != null
				&& !resaleLicense.matches(CustomerStoreDetailsConstants.regularExpressionResaleLicense)) {
			throw new InvalidInputParametersException(
					CustomerStoreDetailsConstants.validResaleLicenseExpressionCharactersErrorCode,
					CustomerStoreDetailsConstants.validResaleLicenseExpressionMessage);
		}
	}

	public static void validateBusinessType(String businessType, int length) throws InvalidInputParametersException {

		if (businessType != null && businessType.length() > length) {
			throw new InvalidInputParametersException(CustomerStoreDetailsConstants.validBusinessTypeLengthErrorCode,
					CustomerStoreDetailsConstants.validBusinessTypeLengthMessage);
		}
	}

}
