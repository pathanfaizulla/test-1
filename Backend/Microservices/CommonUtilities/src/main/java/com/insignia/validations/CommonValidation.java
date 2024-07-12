package com.insignia.validations;

import com.insignia.constants.CommonConstant;
import com.insignia.constants.CustomerBasicDetailsConstants;
import com.insignia.customExceptions.InvalidInputParametersException;

public class CommonValidation {

	public static void validateApplicationId(String applicationId) throws InvalidInputParametersException {

		if (applicationId == null || applicationId.trim().isEmpty()) {
			throw new InvalidInputParametersException(CommonConstant.validApplicationIdErrorCode,
					CommonConstant.validApplicationId);

		} else if (applicationId.length() > CommonConstant.applicationIdlength) {
			throw new InvalidInputParametersException(CommonConstant.validApplicationIdLengthErrorCode,
					CommonConstant.validApplicationIdLength);

		} else if (!applicationId.matches(CustomerBasicDetailsConstants.regularExpression)) {
			throw new InvalidInputParametersException(CommonConstant.validApplicationIdInvalidCharactersErrorCode,
					CommonConstant.validApplicationIdExpression);

		}

	}

	public static void validateTenantId(String tenantId) throws InvalidInputParametersException {

		if (tenantId == null || tenantId.trim().isEmpty()) {
			throw new InvalidInputParametersException(CommonConstant.validTenantIdErrorCode,
					CommonConstant.validTenantId);

		} else if (tenantId.length() > CommonConstant.tenantIdlength) {
			throw new InvalidInputParametersException(CommonConstant.validTenantIdLengthErrorCode,
					CommonConstant.validTenantIdLength);

		} else if (!tenantId.matches(CustomerBasicDetailsConstants.regularExpression)) {
			throw new InvalidInputParametersException(CommonConstant.validTenantIdInvalidCharactersErrorCode,
					CommonConstant.validTenantIdExpression);
		}

	}
	public static void validateApplicatinIdAndTenantId(String applicationId, String tenantId) throws InvalidInputParametersException {
		validateApplicationId(applicationId);
		validateTenantId(tenantId);
		
	}
	public static boolean validateDecimalCount(String valueAsString) {
		if (valueAsString.contains(".")) {
			String[] parts = valueAsString.split("\\.");
			if ((parts.length > 1 && parts[1].length() > CommonConstant.decimalCountlength)) {
				return false;
			}
		}
		return true;
	}

	public static boolean validatePercentageCount(String valueAsString) {
		if (valueAsString.contains(".")) {
			String[] parts = valueAsString.split("\\.");
			if ((parts[0].length() + 1 + parts[1].length() > CommonConstant.percentageFieldLength)) {
				return false;
			}else if (Double.parseDouble(valueAsString) > CommonConstant.maxAllowedPercentage) {
	            return false;
			}
			return parts.length <= 1 || parts[1].length() <= CommonConstant.decimalCountlength;
		}

		return true;
	}
	
	
	public static void validateHexCode(String hexCode, int length) throws InvalidInputParametersException {
		if (hexCode != null) {
			if (hexCode.length() > length) {
				throw new InvalidInputParametersException(CommonConstant.validateHexCodeLengthErrorCode,
						CommonConstant.validateHexCodeLengthMessage);
			} else if (!hexCode.matches(CommonConstant.regularExpressionHexCode)) {
				throw new InvalidInputParametersException(
						CommonConstant.validHexCodeInvalidCharactersErrorCode,
						CommonConstant.validHexCodeExpression);
			}
		}

	}

}
