package com.insignia.constant;

public class PaymentConstants {
	
	public static final int applicationIdLength = 30;
	public static final int tenantIdLength = 30;
	public static final int customerIdLength = 30;
	public static final int paymentMethodTypeLength = 16;
	public static final int paymentMethohdDetailLength = 256;
	public static final int paymentMethodIdLength = 64;
	
	public static final boolean statusTrue = true;
	public static final boolean statusFalse = false;
	
	
	public static final Integer validFrom = 5;
	public static final Integer validUpto = 5;
	
	public static final String Null = null;
	
	public static final String duplicatePaymentErrorCode = "221";
	public static final String duplicatePaymentErrorMessage = "A payment method with the same details already exists.";
	
	public static final String noUserFoundErrorCode = "222";
	public static final String noUserFoundErrorMessage = "No Account is found with given details to add the payment.";
	
	public static final String addPaymentErrorCode = "223";
	public static final String addPaymentErrorMessage ="Failed to add Payment Method.";
	
	public static final String getPaymentIdErrorCode = "224";
	public static final String getPaymentIdErrorMessage ="Failed to retrieve payment method ID.";
	
	public static final String noRowPresentErrorCode = "225";
	public static final String noRowPresentErrorMessage = "There is no row with the given details in the database."; 
	
	public static final String removePaymentErrorCode = "226";
	public static final String removePaymentErrorMessage = "Failed to remove Payment Method.";
	
	public static final String noDataFoundErrorCode = "227";
	public static final String noDataFoundErrorMessage = "There is no payment method in the database associated with the given user data.";
	
	public static final String listPaymentErrorCode = "228";
	public static final String listPaymentErrorMessage = "Failed to list Payment Methods.";
	
	public static final String noPaymentMethodFoundErrorCode = "229";
	public static final String noPaymentMethodFoundErrorMessages = "The requested payment method was not found.";
	
	public static final String updateDefaultPaymentMethodErrorCode = "230";
	public static final String updateDefaultPaymentMethodErrorMessages = "Failed to update the default Payment Method.";
	
	public static final String verifyPaymentMethodErrorCode = "231";
	public static final String verifyPaymentMethodErrorMessage = "Failed to verify the Payment Method.";
	
	public static final String successMessageForDeleteMethod = "Payment successfully deleted."; 
	
}
