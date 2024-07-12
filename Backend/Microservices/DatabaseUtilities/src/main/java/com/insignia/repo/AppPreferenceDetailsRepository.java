package com.insignia.repo;

import java.io.Serializable;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insignia.entity.AppPreferenceDetailsEntity;

public interface AppPreferenceDetailsRepository extends JpaRepository<AppPreferenceDetailsEntity, Serializable> {

	public final static String findByApplicationIdAndApplicationName = "Select * from app_preference_details where customer_sequence_number=:customer_sequence_number AND application_id=:application_id";

	@Query(value = findByApplicationIdAndApplicationName, nativeQuery = true)
	public List<AppPreferenceDetailsEntity> findByApplicationIdAndApplicationName(
			@Param("customer_sequence_number") Long customerSequenceNumber,
			@Param("application_id") Integer applicationId);

	@Transactional
	@Modifying
	public void deleteByPreferenceId(Long preferenceId);

}
