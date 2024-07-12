package com.insignia.constants;

public class CustomerPaymentMethodDetailsConstant {

	public static final String validApplicationId = "Please enter applicationid";
	public static final String validApplicationIdErrorCode = "303";
	public static final String validApplicationIdLength = "Please enter applicationid length upto 30 characters only";
	public static final String validApplicationIdLengthErrorCode = "304";
	public static final String validApplicationIdExpression = "Please provide valid applicationid, the allowed characters are [ A-Z, a-z, 0-9, _, -, .] only";
	public static final String validApplicationIdInvalidCharactersErrorCode = "305";

	public static final String validTenantId = "Please enter tenantid";
	public static final String validTenantIdErrorCode = "306";
	public static final String validTenantIdLength = "Please enter tenantid length upto 30 characters only";
	public static final String validTenantIdLengthErrorCode = "307";
	public static final String validTenantIdExpression = "Please provide valid tenantid, the allowed characters are [ A-Z, a-z, 0-9, _, -, .] only";
	public static final String validTenantIdInvalidCharactersErrorCode = "308";

	public static final String validCustomerId = "Please enter customerid";
	public static final String validCustomerIdErrorCode = "312";
	public static final String validCustomerIdLength = "Please enter customerid length upto 30 characters only";
	public static final String validCustomerIdLengthErrorCode = "313";
	public static final String validCustomerIdExpression = "Please provide valid customerid, the allowed characters are [ A-Z, a-z, 0-9, _, -, .] only";
	public static final String validCustomerIdInvalidCharactersErrorCode = "314";

	public static final String validPaymentMethodType = "Please enter PaymentMethodType";
	public static final String validPaymentMethodTypeErrorCode = "315";
	public static final String validPaymentMethodTypeLength = "Please enter PaymentMethodType length upto 16 characters only";
	public static final String validPaymentMethodTypeLengthErrorCode = "316";
	public static final String validPaymentMethodTypeExpression = "Please provide valid PaymentMethodType, the allowed characters are [ A-Z, a-z, _, -, .] only";
	public static final String validPaymentMethodTypeInvalidCharactersErrorCode = "317";

	public static final String validPaymentMethodId = "Please enter PaymentMethodId";
	public static final String validPaymentMethodIdErrorCode = "318";
	public static final String validPaymentMethodIdLength = "Please enter PaymentMethodId length upto 64 characters only";
	public static final String validPaymentMethodIdLengthErrorCode = "319";
	public static final String validPaymentMethodIdExpression = "Please provide valid PaymentMethodId, the allowed characters are [0-9] only";
	public static final String validPaymentMethodIdInvalidCharactersErrorCode = "320";

	public static final String validateApplicationDetailsMessage = "Details of already existing in the system";
	public static final String validateApplicationDetailsErrorCode = "321";

	public static final String validateValidFromDateMessage = "Provided valid from date is incorrect. It should be in MM/YY format.";
	public static final String validateValidFromDateErrorCode = "322";

	public static final String validateValidUptoDateMessage = "Provided valid upto date is incorrect. It should be in MM/YY format.";
	public static final String validateValidUptoDateErrorCode = "323";

	public static final String noDataFoundMessage = "Data not found in the database";
	public static final String noDataFoundMessageErrorCode = "325";

	public static final String regularExpression = "^[A-Za-z0-9_\\-.@]+$";
	public static final String regularExpression1 = "^[A-Za-z ]+$";
	public static final String regularExpression2 = "^[0-9]+$";
	public static final String regularForValidFromAndValidUpto = "\\d{2}/\\d{2}";

}
