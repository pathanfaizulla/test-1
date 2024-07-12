package com.insignia.serviceInterface;

import java.util.List;


import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.CurrencyDetailsRequest;
import com.insignia.model.CurrencyDetailsResponse;


public interface CurrencyDetailsServiceInterface {

	public CurrencyDetailsResponse saveCurrencyDetails(CurrencyDetailsRequest currencyDetailsRequest) throws InvalidInputParametersException, TokenExpiredException;

	public CurrencyDetailsResponse updateCurrencyDetails(CurrencyDetailsRequest currencyDetailsRequest) throws InvalidInputParametersException, TokenExpiredException;

	public void deleteCurrencyDetails(CurrencyDetailsRequest currencyDetailsRequest) throws InvalidInputParametersException, TokenExpiredException;

	public List<CurrencyDetailsResponse> getAllCurrencyDetails(CurrencyDetailsRequest currencyDetailsRequest) throws TokenExpiredException;

}
