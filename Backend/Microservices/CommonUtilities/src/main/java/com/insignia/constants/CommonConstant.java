package com.insignia.constants;

public class CommonConstant {

	public final static Integer applicationIdlength = 30;

	public final static Integer tenantIdlength = 30;
	
	public static final String validateToken = "Token is Expired";
	public static final String validateTokenErrorCode = "252";
	
	public static final String validateUnexpectedErrorMessage= "Some " +
			"unexpected error has occurred please contact your support team";
	public static final String validateUnexpectedErrorCode = "253";
	
	public static final long nonLoggedCustomerSequenceNumber = -1;
	
	public static final String paddingLength = "%08d";
	
	public static final String validApplicationId = "Please enter applicationid";
	public static final String validApplicationIdErrorCode = "303";
	public static final String validApplicationIdLength = "Application id should be upto 30 characters only";
	public static final String validApplicationIdLengthErrorCode = "304";
	public static final String validApplicationIdExpression = "Please provide valid applicationid, the allowed characters are [ A-Z, a-z, 0-9, _, -, .] only";
	public static final String validApplicationIdInvalidCharactersErrorCode = "305";

	public static final String validTenantId = "Please enter tenantid";
	public static final String validTenantIdErrorCode = "306";
	public static final String validTenantIdLength = "Tenant id should be upto 30 characters only";
	public static final String validTenantIdLengthErrorCode = "307";
	public static final String validTenantIdExpression = "Please provide valid tenantid, the allowed characters are [ A-Z, a-z, 0-9, _, -, .] only";
	public static final String validTenantIdInvalidCharactersErrorCode = "308";
	public final static Integer decimalCountlength = 2;
	public final static Integer percentageFieldLength = 6;
	public final static Integer maxAllowedPercentage = 100;

	public static final String validateDDMMYYYYExpression = "^(0[1-9]|[12][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\\d{4})$";

	public static final String validateHexCodeLengthErrorCode = "971";
	public static final String validateHexCodeLengthMessage = "Please enter hex code length upto 8 characters only";
	
	public static final String validHexCodeInvalidCharactersErrorCode = "972";
	public static final String validHexCodeExpression = "Please provide valid hex code, the allowed characters are [ A-Z, a-z, 0-9, #] only";
		
	public static final String regularExpressionHexCode = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
}
