package com.insignia.daoImpl;

import java.util.List;

import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insignia.daoInterface.AppPreferenceDetailsDaoInterface;
import com.insignia.entity.AppPreferenceDetailsEntity;
import com.insignia.entity.ApplicationEntity;
import com.insignia.repo.AppPreferenceDetailsRepository;
import com.insignia.repo.ApplicationRepository;

@Repository
public class AppPreferenceDetailsDaoImpl implements AppPreferenceDetailsDaoInterface {

	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private AppPreferenceDetailsRepository appPreferenceRepository;

	@Autowired
	private EntityManager entityManager;

	@Override
	public ApplicationEntity saveApplicationDetails(ApplicationEntity applicationEntity){
		return applicationRepository.save(applicationEntity);
	}

	@Override
	public void updateApplicationDetails(AppPreferenceDetailsEntity appPreferenceDetailsEntity) {
		entityManager.merge(appPreferenceDetailsEntity);
	}

	@Override
	public List<Object[]> findByApplicationId(String applicationName,Integer applicationId) {
		return applicationRepository.findByApplicationId(applicationName,applicationId);
	}

	@Override
	public List<AppPreferenceDetailsEntity> getApplicationPreferenceDetails(Long customerSequenceNumber,
			Integer applicationId) {
		return appPreferenceRepository.findByApplicationIdAndApplicationName(customerSequenceNumber, applicationId);
	}

	@Override
	public void deleteByPreferenceId(Long preferenceId) {
		appPreferenceRepository.deleteByPreferenceId(preferenceId);
		
	}

	@Override
	public void saveAppPreferenceDetails(AppPreferenceDetailsEntity appPreferenceDetailsEntity) {
		appPreferenceRepository.save(appPreferenceDetailsEntity);	
	}

}
