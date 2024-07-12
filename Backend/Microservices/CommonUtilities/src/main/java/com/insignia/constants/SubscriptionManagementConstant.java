package com.insignia.constants;

public class SubscriptionManagementConstant {

	public static final String validatePlanIdMessage = "Please enter plan id";
	public static final String validatePlanIdErrorCode = "801";
	public static final String validatePlanIdLengthMessage = "Please enter plan id length upto 30 characters";
	public static final String validatePlanIdLengthMessageErrorCode = "802";

	public static final String validatePlanNameMessage = "Please enter plan name";
	public static final String validatePlanNameErrorCode = "803";
	public static final String validatePlanNameLengthMessage = "Please enter plan name length upto 30 characters";
	public static final String validatePlanNameLengthMessageErrorCode = "804";

	public static final String validatePlanActivationDateMessage = "Activation date and till date for activation is mandatory";
	public static final String validatePlanActivationDateMessageErrorCode = "805";
	public static final String validatePlanActivationDateExpressionMessage = "Either activation date or till date for activation is incorrect. The allowed format is 'dd-MM-yyyy HH:mm:ss' only";
	public static final String validatePlanActivationDateCharactersErrorCode = "806";

	public static final String validatePlanDeActivationDateMessage = "Please enter  de-activation date";
	public static final String validatePlanDeActivationDateMessageErrorCode = "807";
	public static final String validatePlanDeActivationDateExpressionMessage = "Please provide valid plan de-activation date, the allowed format is (i.e., 23-02-2023 14:30:00 only";
	public static final String validatePlanDeActivationDateCharactersErrorCode = "808";

	public static final String validatePlanPricingLengthMessage = "Please enter plan pricing length upto 10,2 only";
	public static final String validatePlanPricingLengthMessageErrorCode = "809";

	public static final String validatePlanDescriptionLengthMessage = "Please enter plan description length upto 255 characters";
	public static final String validatePlanDescriptionLengthMessageErrorCode = "810";

	public static final String validatePlanDurationLengthMessage = "Please enter plan duration length upto 2 characters";
	public static final String validatePlanDurationLengthMessageErrorCode = "812";

	public static final String regularExpressionForDateFormat = "^\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}$";

	public static final String validateSubscriptionDetailsDuplicateDataMessageErrorCode = "813";
	public static final String validateSubscriptionDetailsDuplicateDataMessage = "This subscription plan already exist in the system. To use please activate it.";

	public static final String validateSubscriptionDetailsDuplicateDataActivateStateMessageErrorCode = "814";
	public static final String validateSubscriptionDetailsDuplicateDataActivateStateMessage = "This subscription plan already exist in the system";

	public static final String validateSubscriptionDetailsDuplicateDataDe_ActivateStateMessageErrorCode = "815";
	public static final String validateSubscriptionDetailsDuplicateDataDe_ActivateStateMessage = "This subscription plan is already available and is in de_activated state, do you want to activate it?";

	public static final String validateSubscriptionDetailsNotFoundErrorCode = "816";
	public static final String validateSubscriptionDetailsNotFoundMessage = "Subscription details not found on the system";

	public static final String validateSubscriptionDetailsNoActivationPlanErrorCode = "817";
	public static final String validateSubscriptionDetailsNoActivationPlanMessage = "This plan can't be de_activated it hasn't been activated yet.";

	public static final String validateSubscriptionActivateStateErrorCode = "819";
	public static final String validateSubscriptionActivateStateMessage = "This subscription plan is already activate";

	public static final String validateSubscriptionDateComparisionErrorCode = "820";
	public static final String validateSubscriptionDateComparisionMessage = "Deactivation date must be after the activation date";
	public static final String validateSubscriptionDateComparisionForActiveTillMessage = "Deactivation date must be after the date till when the plan is active";
	public static final String validateActiveTillDateAfterActivationDateMessage = "Activation date must be before the date till when the plan is active";

	public static final String validateSubscriptionUnableToProcessErrorCode = "821";
	public static final String validateSubscriptionUnableToProcessMessage = "This subscription plan is in activate state, so unable to process your request";

	public static final String validateSubscriptionDetailsDe_ActivateStateMessageErrorCode = "822";
	public static final String validateSubscriptionDetailsDe_ActivateStateMessage = "This subscription plan is already de-activate";

	public static final String validateSubscriptionUpdateActivateStateErrorCode = "823";
	public static final String validateSubscriptionUpdatesActivateStateMessage = "This plan can't be updated as it is in active state";

	public static final String validateSubscriptionUpdateDeActivateStateErrorCode = "824";
	public static final String validateSubscriptionUpdatesDeActivateStateMessage = "This plan can't be updated as it is in deactive state, please delete it and recreate";

	public static final String validateSubscriptionDeleteActivateStateErrorCode = "825";
	public static final String validateSubscriptionDeleteActivateStateMessage = "This plan can't be deleted as it is in active state";

	public static final String validatePercentageDiscountLengthMessageErrorCode = "826";
	public static final String validatePercentageDiscountLengthMessage = "Please enter plan pricing length upto 10,2 only";

	public static final String validateSubscriptionDetailsMessage = "Subscription details already existing in the system";
	public static final String validateSubscriptionDetailsErrorCode = "827";

	public static final String validateSubscriptionDeleteErrorCode = "828";
	public static final String validateSubscriptionDeleteMessage = "To delete subscription plan either sequenceNumber or combination of planId, planName is required";
	
	public static final String validateMinOrderValueLengthMessageErrorCode = "829";
	public static final String validateMinOrderValueLengthMessage = "Please enter minimum order value length upto 10,2 only";

}
