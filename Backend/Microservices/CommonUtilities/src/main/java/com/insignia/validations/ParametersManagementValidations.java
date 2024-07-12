package com.insignia.validations;

import java.math.BigDecimal;

import com.insignia.constants.ParametersManagementConstants;
import com.insignia.customExceptions.InvalidInputParametersException;

public class ParametersManagementValidations {

	// Measurement Units
	public static void validateUnitName(String unitName, int length, boolean checkNullValue)
			throws InvalidInputParametersException {
		if (checkNullValue && (unitName == null || unitName.trim().isEmpty())) {
			throw new InvalidInputParametersException(ParametersManagementConstants.validateUnitNameErrorCode,
					ParametersManagementConstants.validateUnitNameMessage);

		} else if (unitName != null && unitName.length() > length) {
			throw new InvalidInputParametersException(ParametersManagementConstants.validateUnitNameLengthErrorCode,
					ParametersManagementConstants.validateUnitNameLengthMessage);

		}

	}

	public static void validateUnitDescription(String unitDescription, int length)
			throws InvalidInputParametersException {
		if (unitDescription != null && unitDescription.length() > length) {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.validateUnitDescriptionLengthErrorCode,
					ParametersManagementConstants.validateUnitDescriptionLengthMessage);

		}

	}

	// Product Color
	public static void validateColourName(String colourName, int length, boolean checkNullValue)
			throws InvalidInputParametersException {
		if (checkNullValue && (colourName == null || colourName.trim().isEmpty())) {
			throw new InvalidInputParametersException(ParametersManagementConstants.validateColourNameErrorCode,
					ParametersManagementConstants.validateColourNameMessage);

		} else if (colourName != null && colourName.length() > length) {
			throw new InvalidInputParametersException(ParametersManagementConstants.validateColourNameLengthErrorCode,
					ParametersManagementConstants.validateColourNameLengthMessage);

		}

	}

	public static void validateColourDescription(String colourDescription, int length)
			throws InvalidInputParametersException {
		if (colourDescription != null && colourDescription.length() > length) {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.validateColourDescriptionLengthErrorCode,
					ParametersManagementConstants.validateColourDescriptionLengthMessage);

		}

	}

	// Product Family
	public static void validateFamilyName(String familyName, int length, boolean checkNullValue)
			throws InvalidInputParametersException {
		if (checkNullValue && (familyName == null || familyName.trim().isEmpty())) {
			throw new InvalidInputParametersException(ParametersManagementConstants.validateFamilyNameErrorCode,
					ParametersManagementConstants.validateFamilyNameMessage);

		} else if (familyName != null && familyName.length() > length) {
			throw new InvalidInputParametersException(ParametersManagementConstants.validateFamilyNameLengthErrorCode,
					ParametersManagementConstants.validateFamilyNameLengthMessage);

		}

	}

	public static void validateFamilyDescription(String familyDescription, int length)
			throws InvalidInputParametersException {
		if (familyDescription != null && familyDescription.length() > length) {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.validateFamilyDescriptionLengthErrorCode,
					ParametersManagementConstants.validateFamilyDescriptionLengthMessage);

		}

	}

	// Product Material
	public static void validateMaterialName(String materialName, int length, boolean checkNullValue)
			throws InvalidInputParametersException {
		if (checkNullValue && (materialName == null || materialName.trim().isEmpty())) {
			throw new InvalidInputParametersException(ParametersManagementConstants.validateMaterialNameErrorCode,
					ParametersManagementConstants.validateMaterialNameMessage);

		} else if (materialName != null && materialName.length() > length) {
			throw new InvalidInputParametersException(ParametersManagementConstants.validateMaterialNameLengthErrorCode,
					ParametersManagementConstants.validateMaterialNameLengthMessage);

		}

	}

	public static void validateMaterialDescription(String materialDescription, int length)
			throws InvalidInputParametersException {
		if (materialDescription != null && materialDescription.length() > length) {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.validateMaterialDescriptionLengthErrorCode,
					ParametersManagementConstants.validateMaterialDescriptionLengthMessage);

		}

	}

	// Product Brand
	public static void validateBrandName(String brandName, int length, boolean checkNullValue)
			throws InvalidInputParametersException {
		if (checkNullValue && (brandName == null || brandName.trim().isEmpty())) {
			throw new InvalidInputParametersException(ParametersManagementConstants.validateBrandNameErrorCode,
					ParametersManagementConstants.validateBrandNameMessage);

		} else if (brandName != null && brandName.length() > length) {
			throw new InvalidInputParametersException(ParametersManagementConstants.validateBrandNameLengthErrorCode,
					ParametersManagementConstants.validateBrandNameLengthMessage);

		}

	}

	public static void validateBrandDescription(String brandDescription, int length)
			throws InvalidInputParametersException {
		if (brandDescription != null && brandDescription.length() > length) {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.validateBrandDescriptionLengthErrorCode,
					ParametersManagementConstants.validateBrandDescriptionLengthMessage);

		}

	}

	// Product Catalog
	public static void validateCatalogueName(String catalogueName, int length, boolean checkNullValue)
			throws InvalidInputParametersException {
		if (checkNullValue && (catalogueName == null || catalogueName.trim().isEmpty())) {
			throw new InvalidInputParametersException(ParametersManagementConstants.validateCatalogueNameErrorCode,
					ParametersManagementConstants.validateCatalogueNameMessage);

		} else if (catalogueName != null && catalogueName.length() > length) {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.validateCatalogueNameLengthErrorCode,
					ParametersManagementConstants.validateCatalogueNameLengthMessage);

		}

	}

	public static void validateCatalogueDescription(String catalogueDescription, int length)
			throws InvalidInputParametersException {
		if (catalogueDescription != null && catalogueDescription.length() > length) {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.validateCatalogueDescriptionLengthErrorCode,
					ParametersManagementConstants.validateCatalogueDescriptionLengthMessage);
		}

	}

	public static void validateHexCode(String hexCode, int length) throws InvalidInputParametersException {
		if (hexCode != null) {
			if (hexCode.length() > length) {
				throw new InvalidInputParametersException(ParametersManagementConstants.validateHexCodeLengthErrorCode,
						ParametersManagementConstants.validateHexCodeLengthMessage);
			} else if (!hexCode.matches(ParametersManagementConstants.regularExpressionHexCode)) {
				throw new InvalidInputParametersException(
						ParametersManagementConstants.validHexCodeInvalidCharactersErrorCode,
						ParametersManagementConstants.validHexCodeExpression);
			}
		}

	}

	// Currency Details
	public static void validateCurrencyName(String currencyName, int length, boolean checkNullValue)
			throws InvalidInputParametersException {
		if (checkNullValue && (currencyName == null || currencyName.trim().isEmpty())) {
			throw new InvalidInputParametersException(ParametersManagementConstants.validateCurrencyNameErrorCode,
					ParametersManagementConstants.validateCurrencyNameMessage);

		} else if (currencyName != null && currencyName.length() > length) {
			throw new InvalidInputParametersException(ParametersManagementConstants.validateCurrencyNameLengthErrorCode,
					ParametersManagementConstants.validateCurrencyNameLengthMessage);

		}

	}

	public static void validateCurrencyDescription(String currencyDescription, int length)
			throws InvalidInputParametersException {
		if (currencyDescription != null && currencyDescription.length() > length) {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.validateCurrencyDescriptionLengthErrorCode,
					ParametersManagementConstants.validateCurrencyDescriptionLengthMessage);

		}

	}

	public static void validateCurrencyCode(String currencyCode, int length) throws InvalidInputParametersException {

		if (currencyCode == null || currencyCode.trim().isEmpty()) {
			throw new InvalidInputParametersException(ParametersManagementConstants.validateCurrencyCodeErrorCode,
					ParametersManagementConstants.validateCurrencyCodeMessage);
		}

		else if (currencyCode != null && currencyCode.length() > length) {
			throw new InvalidInputParametersException(ParametersManagementConstants.validateCurrencyCodeLengthErrorCode,
					ParametersManagementConstants.validateCurrencyCodeLengthMessage);

		}

	}

	public static void validateBaseRateCurrency(String baseRateCurrency, int length)
			throws InvalidInputParametersException {
		if (baseRateCurrency != null && baseRateCurrency.length() > length) {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.validateBaseRateCurrencyLengthErrorCode,
					ParametersManagementConstants.validateBaseRateCurrencyLengthMessage);

		}

	}

	public static void validateBaseRate(BigDecimal baseRate, int length) throws InvalidInputParametersException {
		String value = String.valueOf(baseRate);

		if ((baseRate != null && value.length() > length) || !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(ParametersManagementConstants.validateBaseRateLengthErrorCode,
					ParametersManagementConstants.validateBaseRateLengthMessage);
		}
	}

}
