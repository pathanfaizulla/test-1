package com.insignia.model;

import com.insignia.constants.BillingAndInvoiceDetailsConstant;
import com.insignia.customExceptions.InvalidInputParametersException;

public enum ModeOfPayment {
	COD("COD"), CREDIT_CARD("CREDIT CARD"), DEBIT_CARD("DEBIT CARD"), ONLINE_BANKING("ONLINE BANKING"),
	E_WALLET("E-WALLET"), UPI("UPI"), GIFT_CARD("GIFT CARD"), SHOPPING_VOUCHER("SHOPPING VOUCHER"),
	CREDIT_NOTE("CREDIT NOTE"), REWARD_POINTS("REWARD POINTS");

	private final String modeOfPayment;

	ModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public static ModeOfPayment fromValue(String userInput) throws InvalidInputParametersException {
		for (ModeOfPayment mode : ModeOfPayment.values()) {
			if (mode.getModeOfPayment().equals(userInput)) {
				return mode;
			}
		}
	
			throw new InvalidInputParametersException(BillingAndInvoiceDetailsConstant.invalidModeOfPaymentErrorCode,
					BillingAndInvoiceDetailsConstant.invalidModeOfPaymentErrorMessage);

	}

}
