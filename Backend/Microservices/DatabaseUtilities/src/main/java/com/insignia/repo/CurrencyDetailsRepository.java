package com.insignia.repo;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insignia.entity.CurrencyDetails;


public interface CurrencyDetailsRepository extends JpaRepository<CurrencyDetails, Serializable> {

	public static final String CURRENCY_DESCRIPTION = "currency_description";

	public static final String SEQUENCE_NUMBER = "sequence_number";

	public static final String CURRENCY_NAME = "currency_name";

	public static final String APPLICATION_ID = "application_id";

	public static final String TENANT_ID = "tenant_id";

	public static final String findByCurrencyName = "select currency_name from currency_details where currency_name = :currency_name and application_id =:application_id and tenant_id =:tenant_id";

	public static final String deleteByCurrencyName = "delete from currency_details where currency_name = :currency_name and application_id =:application_id and tenant_id =:tenant_id";

	public static final String updateQueryForCurrencyName = "update currency_details SET currency_name=:currency_name where sequence_number =:sequence_number and application_id =:application_id and tenant_id =:tenant_id";

	public static final String updateQueryForCurrencyDescription = "update currency_details SET currency_description=:currency_description where sequence_number =:sequence_number and application_id =:application_id and tenant_id =:tenant_id";

	public static final String updateQueryForCurrencyNameAndDescription = "UPDATE currency_details SET currency_name =:currency_name, currency_description =:currency_description WHERE sequence_number =:sequence_number and application_id =:application_id and tenant_id =:tenant_id";

	public static final String fetchQueryForUpdate = "select * from currency_details where sequence_number = :sequence_number and application_id = :application_id and tenant_id = :tenant_id";

	public static final String fetchQueryForAllCurrencyDetails = "select * from currency_details where application_id = :application_id and tenant_id = :tenant_id";
	
	@Query(value = fetchQueryForAllCurrencyDetails, nativeQuery = true)
	public List<CurrencyDetails> fetchQueryForAllCurrencyDetails(@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);
	
	@Query(value = fetchQueryForUpdate, nativeQuery = true)
	public Optional<CurrencyDetails> fetchQueryForUpdate(@Param(SEQUENCE_NUMBER) Integer sequenceNumber, @Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);
	
	@Query(value = findByCurrencyName, nativeQuery = true)
	public Object findByCurrencyName(@Param(CURRENCY_NAME) String currencyName, @Param(APPLICATION_ID) String applicationId,
			@Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = deleteByCurrencyName, nativeQuery = true)
	public void deleteByCurrencyName(@Param(CURRENCY_NAME) String currencyName, @Param(APPLICATION_ID) String applicationId,
			@Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = updateQueryForCurrencyName, nativeQuery = true)
	public void updateQueryForCurrencyName(@Param(CURRENCY_NAME) String currencyName,
			@Param(SEQUENCE_NUMBER) Integer sequenceNumber, @Param(APPLICATION_ID) String applicationId,
			@Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = updateQueryForCurrencyDescription, nativeQuery = true)
	public void updateQueryForCurrencyDescription(@Param(CURRENCY_DESCRIPTION) String currencyDescription,
			@Param(SEQUENCE_NUMBER) Integer sequenceNumber, @Param(APPLICATION_ID) String applicationId,
			@Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = updateQueryForCurrencyNameAndDescription, nativeQuery = true)
	public void updateQueryForCurrencyNameAndDescription(@Param(CURRENCY_NAME) String currencyName,
			@Param(CURRENCY_DESCRIPTION) String currencyDescription, @Param(SEQUENCE_NUMBER) Integer sequenceNumber,
			@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

}
