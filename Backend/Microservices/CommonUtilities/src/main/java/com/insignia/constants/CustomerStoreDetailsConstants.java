package com.insignia.constants;

public class CustomerStoreDetailsConstants {

	public static final String validMarkupFactorLengthErrorCode = "101";
	public static final String validMarkupFactorLength = "Please enter markup factor length upto 60 characters only";

	public static final String validHearAboutUsLengthErrorCode = "102";
	public static final String validHearAboutUsLength = "Please enter hear About Us length upto 60 characters only";

	public static final String validMarketsLengthErrorCode = "103";
	public static final String validMarketsLength = "Please enter  Markets length upto 60 characters only";

	public static final String validStoreNameLengthErrorCode = "104";
	public static final String validStoreNameLength = "Please enter  StoreName length upto 60 characters only";

	public static final String validStoreContactCharacters = "Please provide valid store contact number, the allowed characters are [0-9] only";
	public static final String validStoreContactCharactersErrorCode = "104";

	public static final String validStoreAddressLengthErrorCode = "105";
	public static final String validStoreAddressLength = "Please enter  StoreAddress length upto 60 characters only";

	public static final String validStoreCountryLengthErrorCode = "106";
	public static final String validStoreCountryLength = "Please enter  StoreCountry length upto 64 characters only";

	public static final String validStoreStateLengthErrorCode = "107";
	public static final String validStoreStateLength = "Please enter  StoreState length upto 64 characters only";

	public static final String validStoreCityLengthErrorCode = "108";
	public static final String validStoreCityLengthMessage = "Please enter  StoreCity length upto 64 characters only";

	public static final String validStoreZipCodeLengthErrorCode = "109";
	public static final String validStoreZipCodeLengthMessage = "Please enter zipcode length upto 8 characters only";

	public static final String validTelephoneLengthErrorCode = "110";
	public static final String validTelephoneLengthMessage = "Please enter  Store telephone length upto 10 characters only";

	public static final String validTelephoneCharactersMessage = "Please provide valid telephone number, the allowed characters are [0-9] only";
	public static final String validTelephoneCharactersErrorCode = "115";

	public static final String validWebsiteLengthErrorCode = "111";
	public static final String validWebsiteLengthMessage = "Please enter  Store website length upto 60 characters only";

	public static final String validWebsiteCharactersMessage = "Please provide valid website, the allowed characters https://www.example.com";
	public static final String validWebsiteCharactersErrorCode = "116";

	public static final String validResaleLicenseLengthErrorCode = "112";
	public static final String validResaleLicenseLengthMessage = "Please enter  resale license length upto 30 characters only";

	public static final String validResaleLicenseExpressionMessage = "Please provide valid resale license, the allowed characters are [A-Z, a-z, 0-9] only";
	public static final String validResaleLicenseExpressionCharactersErrorCode = "118";

	public static final String validBusinessTypeLengthErrorCode = "113";
	public static final String validBusinessTypeLengthMessage = "Please enter  business type length upto 40 characters only";

	public static final String validZipCodeExpression = "Please provide valid zipcode, the allowed characters are [ A-Z, a-z, 0-9, _, -, .] only";
	public static final String validZipCodeInvalidCharactersErrorCode = "114";

	public static final String regularExpressionZipcode = "^[A-Za-z0-9_\\-.@]+$";

	public static final String regularExpressionWebsite = "^(?!https:\\/\\/)[wW]{3}\\.[^\\s\\/$.?#].[^\\s]*$";
	
	public static final String regularExpressionWebsiteForHttps = "^(https?|ftp):\\/\\/[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(\\/\\S*)?$";

	public static final String regularExpressionResaleLicense = "^[a-zA-Z0-9]+$";

	public static final String regularMobileNumberExpression = "^[0-9]+$";

}
