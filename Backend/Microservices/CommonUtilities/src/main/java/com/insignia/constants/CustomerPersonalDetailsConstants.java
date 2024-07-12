package com.insignia.constants;

public class CustomerPersonalDetailsConstants {

	public static final String validCustomerSequenceNumber= "Please enter customer sequence number";
	public static final String validCustomerSequenceNumberErrorCode = "998";
	public static final String validCustomerSequenceNumberLength = "Please enter customer sequence number length greaterthan 0 characters";
	public static final String validCustomerSequenceNumberLengthErrorCode = "999";
	
	
	public static final String validFirstName = "Please enter first name";
	public static final String validFirstNameErrorCode = "201";
	public static final String validFirstNameLength = "Please enter first name length upto 60 characters only";
	public static final String validFirstNameLengthErrorCode = "220";
	public static final String validFirstNameExpression = "Please provide valid first name, the allowed characters are [ A-Z, a-z, 0-9, _, -, .] only";
	public static final String validFirstNameInvalidCharactersErrorCode = "202";

	public static final String validLastName = "Please enter last name";
	public static final String validLastNameErrorCode = "203";
	public static final String validLastNameLength = "Please enter lastname length upto 60 characters only";
	public static final String validLastNameLengthErrorCode = "204";
	public static final String validLastNameExpression = "Please provide valid last name, the allowed characters are [ A-Z, a-z, 0-9, _, -, .] only";
	public static final String validLastNameInvalidCharactersErrorCode = "205";

	public static final String validMiddleNameLength = "Please enter middle name length upto 60 characters only";
	public static final String validMiddleNameLengthErrorCode = "206";

	public static final String validAgeLength = "Please enter age length upto 2 characters only";
	public static final String validAgeLengthErrorCode = "207";
	public static final String validAgeExpression = "Please provide valid age, the allowed characters are [0-9] only";
	public static final String validAgeInvalidCharactersErrorCode = "207";

	public static final String validGenderLength = "Please enter gender length upto 6 characters only";
	public static final String validGenderLengthErrorCode = "208";

	public static final String validCustomerEmailIdLength = "Please enter customer emailId length upto 60 characters only";
	public static final String validCustomerEmailIdLengthErrorCode = "209";
	public static final String validCustomerEmailIdExpression = "Please provide valid customer emailId, the allowed characters are [ A-Z, a-z, 0-9, _, -, @,.] only";
	public static final String validCustomerEmailIdInvalidCharactersErrorCode = "210";

	public static final String validAlternativeEmailIdLength = "Please enter alternative emailId length upto 60 characters only";
	public static final String validAlternativeEmailIdLengthErrorCode = "211";
	public static final String validAlternativeEmailIdExpression = "Please provide valid alternative emailId, the allowed characters are [ A-Z, a-z, 0-9, _, -, @,.] only";
	public static final String validAlternativeEmailIdInvalidCharactersErrorCode = "212";

	public static final String validCustomerMobileNumberLength = "Please enter mobile number length upto 10 characters only";
	public static final String validCustomerMobileNumberLengthErrorCode = "213";
	public static final String validCustomerMobileNumberExpression = "Please provide valid mobile number, the allowed characters are [0-9] only";
	public static final String validCustomerMobileNumberInvalidCharactersErrorCode = "214";

	public static final String validAlternativeMobileNumberLength = "Please enter alternative mobile number length upto 10 characters only";
	public static final String validAlternativeMobileNumberLengthErrorCode = "215";
	public static final String validAlternativeMobileNumberExpression = "Please provide valid alternative mobile number, the allowed characters are [0-9] only";
	public static final String validAlternativeMobileNumberInvalidCharactersErrorCode = "216";

	public static final String validCustomerLandlineNumberLength = "Please enter customer landline number length upto 19 characters only";
	public static final String validCustomerLandlineNumberLengthErrorCode = "217";
	public static final String validCustomerLandlineNumberExpression = "Please provide valid customer landline number, the allowed characters are [0-9] only";
	public static final String validCustomerLandlineNumberInvalidCharactersErrorCode = "218";

	public static final String regularExpression = "^[A-Za-z0-9_\\-.@]+$";
	public static final String regularNumbericExpression = "^[0-9]+$";

}
