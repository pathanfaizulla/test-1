package com.insignia.validations;

import java.math.BigDecimal;

import com.insignia.constants.ProductValidatorConstants;
import com.insignia.customExceptions.InvalidInputParametersException;

public class ProductValidator {

	public static void validateProductId(String productId, int length, boolean checkNullValue)
			throws InvalidInputParametersException {

		if (checkNullValue && (productId == null || productId.trim().isEmpty())) {
			throw new InvalidInputParametersException(ProductValidatorConstants.validProductIdErrorCode,
					ProductValidatorConstants.validProductIdMessage);

		} else if (productId != null && productId.length() > length) {
			throw new InvalidInputParametersException(ProductValidatorConstants.validProductIdLengthErrorCode,
					ProductValidatorConstants.validProductIdLengthMessage);

		}

	}

	public static void validateProductName(String productName, int length) throws InvalidInputParametersException {
		if (productName == null || productName.trim().isEmpty()) {
			throw new InvalidInputParametersException(ProductValidatorConstants.validProductNameErrorCode,
					ProductValidatorConstants.validProductNameMessage);

		} else if (productName.length() > length) {
			throw new InvalidInputParametersException(ProductValidatorConstants.validProductNameLengthErrorCode,
					ProductValidatorConstants.validProductNameLengthMessage);

		}

	}

	public static void validateMeasuringQuantity(String measuringQuantity, int length)
			throws InvalidInputParametersException {
		if (measuringQuantity != null && measuringQuantity.length() > length) {
			throw new InvalidInputParametersException(ProductValidatorConstants.validMeasuringQuantityLengthErrorCode,
					ProductValidatorConstants.validMeasuringQuantityLength);

		}
	}

	public static void validateMeasuringUnit(String measuringUnit, int length) throws InvalidInputParametersException {
		if (measuringUnit != null && measuringUnit.length() > length) {
			throw new InvalidInputParametersException(ProductValidatorConstants.validMeasuringQuantityLengthErrorCode,
					ProductValidatorConstants.validMeasuringQuantityLength);

		}
	}

	public static void validateDescription(String description, int length) throws InvalidInputParametersException {
		if (description != null && description.length() > length) {
			throw new InvalidInputParametersException(ProductValidatorConstants.validDescriptionLengthErrorCode,
					ProductValidatorConstants.validDescriptionLength);

		}

	}

	public static void validateProductPerUnitActualPrice(BigDecimal productPerUnitActualPrice, int length)
			throws InvalidInputParametersException {
		String value = String.valueOf(productPerUnitActualPrice);

		if ((productPerUnitActualPrice != null && value.length() > length)
				|| !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(
					ProductValidatorConstants.validateProductPerUnitActualPriceLengthErrorCode,
					ProductValidatorConstants.validateProductPerUnitActualPriceLengthMessage);
		}
	}

	public static void validateProductPerUnitCurrentPrice(BigDecimal productPerUnitCurrentPrice, int length)
			throws InvalidInputParametersException {
		String value = String.valueOf(productPerUnitCurrentPrice);

		if ((productPerUnitCurrentPrice != null && value.length() > length)
				|| !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(
					ProductValidatorConstants.validateProductPerUnitCurrentPriceLengthErrorCode,
					ProductValidatorConstants.validateProductPerUnitCurrentPriceLengthMessage);
		}
	}

	public static void validateProductLength(BigDecimal productLength, int length)
			throws InvalidInputParametersException {
		String value = String.valueOf(productLength);

		if ((productLength != null && value.length() > length) || !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(ProductValidatorConstants.validateProductLengthErrorCode,
					ProductValidatorConstants.validateProductLengthMessage);
		}
	}

	public static void validateHeight(BigDecimal height, int length) throws InvalidInputParametersException {
		String value = String.valueOf(height);

		if ((height != null && value.length() > length) || !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(ProductValidatorConstants.validateHeightLengthErrorCode,
					ProductValidatorConstants.validateHeightLengthMessage);
		}
	}

	public static void validateWidth(BigDecimal width, int length) throws InvalidInputParametersException {
		String value = String.valueOf(width);

		if ((width != null && value.length() > length) || !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(ProductValidatorConstants.validateWidthLengthErrorCode,
					ProductValidatorConstants.validateWidthLengthMessage);
		}
	}

	public static void validateDimensionUnit(String dimensionUnit, int length) throws InvalidInputParametersException {

		if (dimensionUnit != null && dimensionUnit.length() > length) {
			throw new InvalidInputParametersException(ProductValidatorConstants.validateDimensionUnitLengthErrorCode,
					ProductValidatorConstants.validateDimensionUnitLengthMessage);
		}
	}

	public static void validateMaterials(String materials, int length) throws InvalidInputParametersException {

		if (materials != null && materials.length() > length) {
			throw new InvalidInputParametersException(ProductValidatorConstants.validateMaterialsLengthErrorCode,
					ProductValidatorConstants.validateMaterialsLengthMessage);
		}
	}

	public static void validateColours(String colours, int length) throws InvalidInputParametersException {

		if (colours != null && colours.length() > length) {
			throw new InvalidInputParametersException(ProductValidatorConstants.validateColoursLengthErrorCode,
					ProductValidatorConstants.validateColoursLengthMessage);
		}
	}

	public static void validateProductFinish(String productFinish, int length) throws InvalidInputParametersException {
		if (productFinish != null && productFinish.length() > length) {
			throw new InvalidInputParametersException(ProductValidatorConstants.validateProductFinishLengthErrorCode,
					ProductValidatorConstants.validateProductFinishLengthMessage);

		}
	}

	public static void validateProdWholeTier1Price(BigDecimal prodWholeTier1Price, int length)
			throws InvalidInputParametersException {
		String value = String.valueOf(prodWholeTier1Price);

		if ((prodWholeTier1Price != null && value.length() > length) || !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(
					ProductValidatorConstants.validateProdWholeTier1PriceLengthErrorCode,
					ProductValidatorConstants.validateProdWholeTier1PriceLengthMessage);
		}
	}

	public static void validateProdWholeTier2Price(BigDecimal prodWholeTier2Price, int length)
			throws InvalidInputParametersException {
		String value = String.valueOf(prodWholeTier2Price);

		if ((prodWholeTier2Price != null && value.length() > length) || !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(
					ProductValidatorConstants.validateProdWholeTier2PriceLengthErrorCode,
					ProductValidatorConstants.validateProdWholeTier2PriceLengthMessage);
		}
	}

	public static void validateProdWholeTier1SalePrice(BigDecimal prodWholeTier1SalePrice, int length)
			throws InvalidInputParametersException {
		String value = String.valueOf(prodWholeTier1SalePrice);

		if ((prodWholeTier1SalePrice != null && value.length() > length)
				|| !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(
					ProductValidatorConstants.validateProdWholeTier1SalePriceLengthErrorCode,
					ProductValidatorConstants.validateProdWholeTier1SalePriceLengthMessage);
		}
	}

	public static void validateProdWholeTier2SalePrice(BigDecimal prodWholeTier2SalePrice, int length)
			throws InvalidInputParametersException {
		String value = String.valueOf(prodWholeTier2SalePrice);

		if ((prodWholeTier2SalePrice != null && value.length() > length)
				|| !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(
					ProductValidatorConstants.validateProdWholeTier2SalePriceLengthErrorCode,
					ProductValidatorConstants.validateProdWholeTier2SalePriceLengthMessage);
		}
	}

	public static void validateRetailTier1Price(BigDecimal prodRetailTier1Price, int length)
			throws InvalidInputParametersException {
		String value = String.valueOf(prodRetailTier1Price);

		if ((prodRetailTier1Price != null && value.length() > length)
				|| !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(
					ProductValidatorConstants.validateProdRetailTier1PriceLengthErrorCode,
					ProductValidatorConstants.validateProdRetailTier1PriceLengthMessage);
		}
	}

	public static void validateRetailTier2Price(BigDecimal prodRetailTier2Price, int length)
			throws InvalidInputParametersException {
		String value = String.valueOf(prodRetailTier2Price);

		if ((prodRetailTier2Price != null && value.length() > length)
				|| !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(
					ProductValidatorConstants.validateProdRetailTier2PriceLengthErrorCode,
					ProductValidatorConstants.validateProdRetailTier2PriceLengthMessage);
		}
	}

	public static void validateRetailTier1SalePrice(BigDecimal prodRetailTier1SalePrice, int length)
			throws InvalidInputParametersException {
		String value = String.valueOf(prodRetailTier1SalePrice);

		if ((prodRetailTier1SalePrice != null && value.length() > length)
				|| !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(
					ProductValidatorConstants.validateProdRetailTier1SalePriceLengthErrorCode,
					ProductValidatorConstants.validateProdRetailTier1SalePriceLengthMessage);
		}
	}

	public static void validateRetailTier2SalePrice(BigDecimal prodRetailTier2SalePrice, int length)
			throws InvalidInputParametersException {
		String value = String.valueOf(prodRetailTier2SalePrice);

		if ((prodRetailTier2SalePrice != null && value.length() > length)
				|| !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(
					ProductValidatorConstants.validateProdRetailTier2SalePriceLengthErrorCode,
					ProductValidatorConstants.validateProdRetailTier2SalePriceLengthMessage);
		}
	}

	public static void validateEta(String eta, int length) throws InvalidInputParametersException {

		if (eta != null && eta.length() > length) {
			throw new InvalidInputParametersException(ProductValidatorConstants.validateEtaLengthErrorCode,
					ProductValidatorConstants.validateEtaLengthMessage);

		}

	}

}
