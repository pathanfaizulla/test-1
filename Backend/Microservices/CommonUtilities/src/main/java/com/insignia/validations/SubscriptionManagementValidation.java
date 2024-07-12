package com.insignia.validations;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import com.insignia.constants.SubscriptionManagementConstant;
import com.insignia.customExceptions.InvalidInputParametersException;

public class SubscriptionManagementValidation {

	public static void validatePlanId(String planId, int length, boolean isToCheckBlank)
			throws InvalidInputParametersException {

		if (isToCheckBlank && (planId == null || planId.trim().isEmpty())) {
			throw new InvalidInputParametersException(SubscriptionManagementConstant.validatePlanIdErrorCode,
					SubscriptionManagementConstant.validatePlanIdMessage);

		} else if (planId != null && planId.length() > length) {
			throw new InvalidInputParametersException(
					SubscriptionManagementConstant.validatePlanIdLengthMessageErrorCode,
					SubscriptionManagementConstant.validatePlanIdLengthMessage);

		}
	}

	public static void validatePlanName(String planName, int length, boolean isToCheckBlank)
			throws InvalidInputParametersException {

		if (isToCheckBlank && (planName == null || planName.trim().isEmpty())) {
			throw new InvalidInputParametersException(SubscriptionManagementConstant.validatePlanNameErrorCode,
					SubscriptionManagementConstant.validatePlanNameMessage);

		} else if (planName != null && planName.length() > length) {
			throw new InvalidInputParametersException(
					SubscriptionManagementConstant.validatePlanIdLengthMessageErrorCode,
					SubscriptionManagementConstant.validatePlanIdLengthMessage);

		}
	}

	public static void validatePlanActivationDate(String planActivationDate, String planTilldate)
			throws InvalidInputParametersException {

		if (planActivationDate == null || planActivationDate.trim().isEmpty() || planTilldate == null
				|| planTilldate.trim().isEmpty()) {
			throw new InvalidInputParametersException(
					SubscriptionManagementConstant.validatePlanActivationDateMessageErrorCode,
					SubscriptionManagementConstant.validatePlanActivationDateMessage);
		}

		if (!Pattern.compile(SubscriptionManagementConstant.regularExpressionForDateFormat).matcher(planActivationDate)
				.matches()
				|| !Pattern.compile(SubscriptionManagementConstant.regularExpressionForDateFormat).matcher(planTilldate)
						.matches()) {
			throw new InvalidInputParametersException(
					SubscriptionManagementConstant.validatePlanActivationDateCharactersErrorCode,
					SubscriptionManagementConstant.validatePlanActivationDateExpressionMessage);
		}
	}

	public static void validatePlanDeActivationDate(String planDeActivationDate, int length)
			throws InvalidInputParametersException {

		if (planDeActivationDate == null || planDeActivationDate.trim().isEmpty()) {
			throw new InvalidInputParametersException(
					SubscriptionManagementConstant.validatePlanDeActivationDateMessageErrorCode,
					SubscriptionManagementConstant.validatePlanDeActivationDateMessage);
		}

		if (!Pattern.compile(SubscriptionManagementConstant.regularExpressionForDateFormat)
				.matcher(planDeActivationDate).matches()) {
			throw new InvalidInputParametersException(
					SubscriptionManagementConstant.validatePlanDeActivationDateCharactersErrorCode,
					SubscriptionManagementConstant.validatePlanDeActivationDateExpressionMessage);
		}
	}

	public static void validatePlanPricing(BigDecimal planPricing, int length) throws InvalidInputParametersException {
		String value = String.valueOf(planPricing);

		if ((planPricing != null && value.length() > length) || !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(
					SubscriptionManagementConstant.validatePlanPricingLengthMessageErrorCode,
					SubscriptionManagementConstant.validatePlanPricingLengthMessage);
		}
	}

	public static void validatePlanDescription(String planDescription, int length)
			throws InvalidInputParametersException {

		if (planDescription != null && planDescription.length() > length) {
			throw new InvalidInputParametersException(
					SubscriptionManagementConstant.validatePlanDescriptionLengthMessageErrorCode,
					SubscriptionManagementConstant.validatePlanDescriptionLengthMessage);

		}
	}

	public static void validatePlanDuration(Integer planDuration, int length) throws InvalidInputParametersException {

		if (planDuration != null && String.valueOf(planDuration).length() > length) {
			throw new InvalidInputParametersException(
					SubscriptionManagementConstant.validatePlanDurationLengthMessageErrorCode,
					SubscriptionManagementConstant.validatePlanDurationLengthMessage);
		}
	}

	public static void validatePercentageDiscount(BigDecimal percentageDiscount, int length)
			throws InvalidInputParametersException {
		String value = String.valueOf(percentageDiscount);

		if ((percentageDiscount != null && value.length() > length)
				|| !CommonValidation.validatePercentageCount(value)) {
			throw new InvalidInputParametersException(
					SubscriptionManagementConstant.validatePercentageDiscountLengthMessageErrorCode,
					SubscriptionManagementConstant.validatePercentageDiscountLengthMessage);
		}
	}

	public static void validateMinOrderValue(BigDecimal minOrderValue, int length)
			throws InvalidInputParametersException {
		String value = String.valueOf(minOrderValue);

		if ((minOrderValue != null && value.length() > length) || !CommonValidation.validateDecimalCount(value)) {
			throw new InvalidInputParametersException(
					SubscriptionManagementConstant.validateMinOrderValueLengthMessageErrorCode,
					SubscriptionManagementConstant.validateMinOrderValueLengthMessage);
		}
	}
}
