package com.insignia.validations;

import java.math.BigDecimal;

import com.insignia.constants.WishlistDetailsConstants;
import com.insignia.customExceptions.InvalidInputParametersException;

public class WishlistDetailsValidation {

	public static void validateCustomerSequenceNumber(Long customerSequenceNumber, int length)
			throws InvalidInputParametersException {

		if (customerSequenceNumber == null) {
			throw new InvalidInputParametersException(WishlistDetailsConstants.validCustomerSequenceNumberErrorCode,
					WishlistDetailsConstants.validCustomerSequenceNumber);
		}
	}

	public static void validateProductSequenceNumber(Long productSequenceNumber, int length)
			throws InvalidInputParametersException {

		if (productSequenceNumber == null) {
			throw new InvalidInputParametersException(WishlistDetailsConstants.validProductSequenceNumberErrorCode,
					WishlistDetailsConstants.validProductSequenceNumber);
		} else if (productSequenceNumber != null && String.valueOf(productSequenceNumber).length() < length) {
			throw new InvalidInputParametersException(
					WishlistDetailsConstants.validProductSequenceNumberLengthErrorCode,
					WishlistDetailsConstants.validProductSequenceNumberLength);
		}
	}

	public static void validateQuantity(Integer quantity, int length) throws InvalidInputParametersException {

		if (quantity != null && String.valueOf(quantity).length() > length) {
			throw new InvalidInputParametersException(WishlistDetailsConstants.validQuantityLengthErrorCode,
					WishlistDetailsConstants.validQuantityLength);
		}
	}

	public static void validateProductPerUnitActualPrice(BigDecimal productPerUnitActualPrice, int length)
			throws InvalidInputParametersException {
		String value = String.valueOf(productPerUnitActualPrice);

		if ((productPerUnitActualPrice != null && value.length() > length)
				|| !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(
					WishlistDetailsConstants.validateProductPerUnitActualPriceLengthErrorCode,
					WishlistDetailsConstants.validateProductPerUnitActualPriceLengthMessage);
		}
	}

	public static void validateProductPerUnitCurrentPrice(BigDecimal productPerUnitCurrentPrice, int length)
			throws InvalidInputParametersException {
		String value = String.valueOf(productPerUnitCurrentPrice);

		if ((productPerUnitCurrentPrice != null && value.length() > length)
				|| !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(
					WishlistDetailsConstants.validateProductPerUnitCurrentPriceLengthErrorCode,
					WishlistDetailsConstants.validateProductPerUnitCurrentPriceLengthMessage);
		}
	}

	public static void validateCustomListName(String customListName, int length)
			throws InvalidInputParametersException {

		if ((customListName == null || customListName.trim().isEmpty()) || customListName.length() > length) {
			throw new InvalidInputParametersException(WishlistDetailsConstants.validCustomListNameLengthErrorCode,
					WishlistDetailsConstants.validCustomListNameLengthMessage);
		}

	}
}
