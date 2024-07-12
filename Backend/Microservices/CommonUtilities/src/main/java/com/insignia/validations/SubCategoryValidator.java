package com.insignia.validations;

import com.insignia.constants.SubCategoryConstant;
import com.insignia.customExceptions.InvalidInputParametersException;

public class SubCategoryValidator {

	// TODO: As per requirement need to address
	public static void validateSubCategoryName(String value, int length, boolean checkNullValue)
			throws InvalidInputParametersException {

		if (checkNullValue && (value == null || value.trim().isEmpty())) {
			throw new InvalidInputParametersException(SubCategoryConstant.validateSubCategoryNameErrorCode,
					SubCategoryConstant.validateSubCategoryNameMessage);

		} else if (value != null && value.length() > length) {
			throw new InvalidInputParametersException(SubCategoryConstant.validSubCategoryNameLengthCode,
					SubCategoryConstant.validSubCategoryNameLength);

		}

	}

	public static void validateSubCategoryDescription(String value, int length) throws InvalidInputParametersException {
		if (value != null && value.length() > length) {
			throw new InvalidInputParametersException(SubCategoryConstant.validSubCategoryDescriptionLengthCode,
					SubCategoryConstant.validSubCategoryDescriptionLength);

		}
	}

	public static void validateSubCategoryImagePath(String value, int length) throws InvalidInputParametersException {
		if (value != null && value.length() > length) {
			throw new InvalidInputParametersException(SubCategoryConstant.validSubCategoryImagePathLengthCode,
					SubCategoryConstant.validSubCategoryImagePathLength);

		}
	}
}
