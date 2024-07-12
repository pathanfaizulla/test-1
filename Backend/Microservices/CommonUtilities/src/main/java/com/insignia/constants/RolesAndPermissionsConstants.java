package com.insignia.constants;

public class RolesAndPermissionsConstants {

	
	public static final String validCustomerSequenceNumber= "Please enter customer sequence number";
	public static final String validCustomerSequenceNumberErrorCode = "998";
	public static final String validCustomerSequenceNumberLength = "Please enter customer sequence number length greaterthan 0 characters";
	public static final String validCustomerSequenceNumberLengthErrorCode = "999";
	
	public static final String validRoleName = "Please enter role name";
	public static final String validRoleNameErrorCode = "501";
	public static final String validRoleNameLength = "Please enter role name length upto 16 characters only";
	public static final String validRoleNameLengthErrorCode = "502";
	public static final String validRoleNameExpression = "Please provide valid role name, the allowed characters are [ A-Z, a-z, 0-9, _, -, .] only";
	public static final String validRoleNameInvalidCharactersErrorCode = "503";

	
	public static final String validUpdatePermissionsLength = "Please enter update permissions length upto 30 characters only";
	public static final String validUpdatePermissionsLengthErrorCode = "504";
	
	
	public static final String regularExpression = "^[A-Za-z0-9_\\-.@]+$";
}
