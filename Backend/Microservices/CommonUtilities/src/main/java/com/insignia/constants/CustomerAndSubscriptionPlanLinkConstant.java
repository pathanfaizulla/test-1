package com.insignia.constants;

public class CustomerAndSubscriptionPlanLinkConstant {

	public static final String validateCustomerSubscriptionDetailsDuplicateDataErrorCode = "901";
	public static final String validateCustomerSubscriptionDetailsDuplicateDataMessage = "This subscription plan is already associated with the customer";

	public static final String validateSubscriptionDetailsNotFoundErrorCode = "902";
	public static final String validateSubscriptionDetailsNotFoundMessage = "The provided subscription id is invalid";

	public static final String validateCustomerAnsSubscriptionDetailsNotFoundErrorCode = "903";
	public static final String validateCustomerAnsSubscriptionDetailsNotFoundMessage = "This subscription plan association with the customer is not available in the system";

	public static final String validateSubscriptionDetailsActiveStateErrorCode = "904";
	public static final String validateSubscriptionDetailsActiveStateMessage = "The provided subscription is not in active state or is expired";

	public static final String validateCustomerSubscriptionLinkToForceDeleteErrorCode = "905";
	public static final String validateCustomerSubscriptionLinkToForceDeleteMessage = "This subscription plan is currently active. Please confirm to delete";

}
