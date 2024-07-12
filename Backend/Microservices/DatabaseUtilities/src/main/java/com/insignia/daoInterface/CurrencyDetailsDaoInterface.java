package com.insignia.daoInterface;

import java.util.List;
import java.util.Optional;

import com.insignia.entity.CurrencyDetails;


public interface CurrencyDetailsDaoInterface {

	public CurrencyDetails saveCurrencyDetails(CurrencyDetails currencyDetails);
	
	public CurrencyDetails updateCurrencyDetails(CurrencyDetails currencyDetails);

	public void deleteCurrencyDetails(String currencyName, String applicationId, String tenantId);

	public List<CurrencyDetails> getAllCurrencyDetails(String applicationId, String tenantId);

	public Object findByCurrencyName(String currencyName, String applicationId, String tenantId);

	public Optional<CurrencyDetails> findBySequenceNumber(Integer sequenceNumber, String applicationId, String tenantId);

	public void updateCurrencyName(String currencyName, Integer sequenceNumber, String applicationId, String tenantId);

	public void updateCurrencyDescription(String currencyDescrption, Integer sequenceNumber, String applicationId, String tenantId);

	public void updateCurrencyNameAndDescription(String currencyName, String currencyDescrption, Integer sequenceNumber, String applicationId, String tenantId);
}
