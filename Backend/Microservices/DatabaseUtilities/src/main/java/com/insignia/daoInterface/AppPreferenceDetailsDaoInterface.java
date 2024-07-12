package com.insignia.daoInterface;

import java.util.List;

import com.insignia.entity.AppPreferenceDetailsEntity;
import com.insignia.entity.ApplicationEntity;

public interface AppPreferenceDetailsDaoInterface {

	public ApplicationEntity saveApplicationDetails(ApplicationEntity applicationEntity);
	
	public void saveAppPreferenceDetails(AppPreferenceDetailsEntity appPreferenceDetailsEntity);
	
	public void updateApplicationDetails(AppPreferenceDetailsEntity appPreferenceDetailsEntity);
	
	public List<AppPreferenceDetailsEntity> getApplicationPreferenceDetails(Long customerSequenceNumber,Integer applicationId);
	
	public List<Object[]> findByApplicationId(String applicationName,Integer applicationId);
	
	public void deleteByPreferenceId(Long preferenceId);
	

}
