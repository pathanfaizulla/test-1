package com.insignia.daoImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insignia.daoInterface.CurrencyDetailsDaoInterface;
import com.insignia.entity.CurrencyDetails;
import com.insignia.repo.CurrencyDetailsRepository;

import jakarta.persistence.EntityManager;

@Repository
public class CurrencyDetailsDaoImpl implements CurrencyDetailsDaoInterface {

	@Autowired
	private CurrencyDetailsRepository currencyDetailsRepository;

	@Autowired
	private EntityManager entityManager;

	@Override
	public CurrencyDetails saveCurrencyDetails(CurrencyDetails currencyDetails) {
		return currencyDetailsRepository.save(currencyDetails);
	}

	@Override
	public void deleteCurrencyDetails(String currencyName, String applicationId, String tenantId) {
		currencyDetailsRepository.deleteByCurrencyName(currencyName, applicationId, tenantId);

	}

	@Override
	public List<CurrencyDetails> getAllCurrencyDetails(String applicationId, String tenantId) {
		return currencyDetailsRepository.fetchQueryForAllCurrencyDetails(applicationId, tenantId);
	}

	@Override
	public Object findByCurrencyName(String currencyName, String applicationId, String tenantId) {

		return currencyDetailsRepository.findByCurrencyName(currencyName, applicationId, tenantId);
	}

	@Override
	public Optional<CurrencyDetails> findBySequenceNumber(Integer sequenceNumber, String applicationId,
			String tenantId) {

		return currencyDetailsRepository.fetchQueryForUpdate(sequenceNumber, applicationId, tenantId);
	}

	@Override
	public void updateCurrencyName(String currencyName, Integer sequenceNumber, String applicationId, String tenantId) {
		currencyDetailsRepository.updateQueryForCurrencyName(currencyName, sequenceNumber, applicationId, tenantId);
	}

	@Override
	public void updateCurrencyDescription(String currencyDescrption, Integer sequenceNumber, String applicationId,
			String tenantId) {
		currencyDetailsRepository.updateQueryForCurrencyDescription(currencyDescrption, sequenceNumber, applicationId,
				tenantId);
	}

	@Override
	public void updateCurrencyNameAndDescription(String currencyName, String currencyDescrption, Integer sequenceNumber,
			String applicationId, String tenantId) {
		currencyDetailsRepository.updateQueryForCurrencyNameAndDescription(currencyName, currencyDescrption,
				sequenceNumber, applicationId, tenantId);
		;

	}

	@Override
	public CurrencyDetails updateCurrencyDetails(CurrencyDetails currencyDetails) {
		return entityManager.merge(currencyDetails);
	}

}
