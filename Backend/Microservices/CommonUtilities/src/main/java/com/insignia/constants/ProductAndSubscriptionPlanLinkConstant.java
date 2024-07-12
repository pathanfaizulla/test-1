package com.insignia.constants;

public class ProductAndSubscriptionPlanLinkConstant {

	public static final String validateProductSubscriptionDetailsDuplicateDataErrorCode = "950";
	public static final String validateProductSubscriptionDetailsDuplicateDataMessage = "This subscription plan is already associated with the product";

	public static final String validateSubscriptionDetailsNotFoundErrorCode = "951";
	public static final String validateSubscriptionDetailsNotFoundMessage = "The provided subscription id is invalid";

	public static final String validateProductAnsSubscriptionDetailsNotFoundErrorCode = "952";
	public static final String validateProductAnsSubscriptionDetailsNotFoundMessage = "This subscription plan association with the product is not available in the system";

	public static final String validateSubscriptionDetailsActiveStateErrorCode = "953";
	public static final String validateSubscriptionDetailsActiveStateMessage = "The provided subscription is not in active state or is expired";

	public static final String validateProductSubscriptionLinkToForceDeleteErrorCode = "954";
	public static final String validateProductSubscriptionLinkToForceDeleteMessage = "This subscription plan is currently active. Please confirm to delete";

}
