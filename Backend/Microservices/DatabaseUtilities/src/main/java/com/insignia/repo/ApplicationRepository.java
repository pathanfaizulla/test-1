package com.insignia.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insignia.entity.ApplicationEntity;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Serializable> {

	public final static String findByApplicationId = "Select application_id, application_name from applications where application_name=:application_name or application_id=:applicationId";
	
	
	@Query(value = findByApplicationId, nativeQuery = true)
	public List<Object[]> findByApplicationId(
			@Param("application_name") String applicationName,@Param("applicationId") Integer applicationId);

}
