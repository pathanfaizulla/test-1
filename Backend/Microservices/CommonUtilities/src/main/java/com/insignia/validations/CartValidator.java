package com.insignia.validations;

import java.math.BigDecimal;

import com.insignia.constants.CartValidaterConstant;
import com.insignia.customExceptions.InvalidInputParametersException;

public class CartValidator {

	public static void validateproductQuantity(String productQuantity, int length)
			throws InvalidInputParametersException {
		if (productQuantity == null || productQuantity == "" || productQuantity.isBlank()) {
			throw new InvalidInputParametersException(CartValidaterConstant.validProductQuantityErrorCode,
					CartValidaterConstant.validProductQuantity);

		} else if (productQuantity.length() > length) {
			throw new InvalidInputParametersException(CartValidaterConstant.validProductQuantityLengthErrorCode,
					CartValidaterConstant.validProductQuantityLength);

		}

	}
	
	public static void validateCartSequenceNumber(Long cartSequenceNumber, int length) throws InvalidInputParametersException {

		if (cartSequenceNumber == null) {
			throw new InvalidInputParametersException(CartValidaterConstant.validCartSequenceNumberErrorCode,
					CartValidaterConstant.validCartSequenceNumber);

		} else if (cartSequenceNumber != null && Long.toString(cartSequenceNumber).length() > length) {
			throw new InvalidInputParametersException(CartValidaterConstant.validCartSequenceNumberLengthErrorCode,
					CartValidaterConstant.validCartSequenceNumberLength);
		}

	}
	
	public static void validateProductSequenceNumber(Long productSequenceNumber, int length) throws InvalidInputParametersException {

		if (productSequenceNumber == null) {
			throw new InvalidInputParametersException(CartValidaterConstant.validProductSequenceNumberErrorCode,
					CartValidaterConstant.validProductSequenceNumber);

		} else if (productSequenceNumber != null && Long.toString(productSequenceNumber).length() > length) {
			throw new InvalidInputParametersException(CartValidaterConstant.validProductSequenceNumberLengthErrorCode,
					CartValidaterConstant.validProductSequenceNumberLength);
		}

	}
	
	public static void validateProductPerUnitActualPrice(BigDecimal productPerUnitActualPrice, int length)
			throws InvalidInputParametersException {
		String value = String.valueOf(productPerUnitActualPrice);

		if ((productPerUnitActualPrice != null && value.length() > length)
				|| !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(
					CartValidaterConstant.validateProductPerUnitActualPriceLengthErrorCode,
					CartValidaterConstant.validateProductPerUnitActualPriceLengthMessage);
		}
	}

	public static void validateProductPerUnitCurrentPrice(BigDecimal productPerUnitCurrentPrice, int length)
			throws InvalidInputParametersException {
		String value = String.valueOf(productPerUnitCurrentPrice);

		if ((productPerUnitCurrentPrice != null && value.length() > length)
				|| !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(
					CartValidaterConstant.validateProductPerUnitCurrentPriceLengthErrorCode,
					CartValidaterConstant.validateProductPerUnitCurrentPriceLengthMessage);
		}
	}

	public static void validateProductLength(BigDecimal productLength, int length) throws InvalidInputParametersException {
		String value = String.valueOf(productLength);

		if ((productLength != null && value.length() > length)
				|| !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(CartValidaterConstant.validateProductLengthErrorCode,
					CartValidaterConstant.validateProductLengthMessage);
		}
	}

	public static void validateHeight(BigDecimal height, int length) throws InvalidInputParametersException {
		String value = String.valueOf(height);

		if ((height != null && value.length() > length)
				|| !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(CartValidaterConstant.validateHeightLengthErrorCode,
					CartValidaterConstant.validateHeightLengthMessage);
		}
	}

	public static void validateWidth(BigDecimal width, int length) throws InvalidInputParametersException {
		String value = String.valueOf(width);

		if ((width != null && value.length() > length)
				|| !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(CartValidaterConstant.validateWidthLengthErrorCode,
					CartValidaterConstant.validateWidthLengthMessage);
		}
	}
	

}
