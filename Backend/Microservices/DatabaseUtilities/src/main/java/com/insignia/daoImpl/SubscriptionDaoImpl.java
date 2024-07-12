package com.insignia.daoImpl;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insignia.daoInterface.SubscriptionDaoInterface;
import com.insignia.entity.SubscriptionDetails;
import com.insignia.repo.UserSubscriptionRepository;

@Repository
@Transactional
public class SubscriptionDaoImpl implements SubscriptionDaoInterface {

	@Autowired
	private UserSubscriptionRepository userRepo;

	@Autowired
	private EntityManager entityManager;

	@Override
	public SubscriptionDetails saveSubscriptionPlan(SubscriptionDetails subscriptionDetails) {
		return userRepo.save(subscriptionDetails);

	}

	@Override
	public SubscriptionDetails updateSubscriptionPlan(SubscriptionDetails subscriptionDetails) {
		return entityManager.merge(subscriptionDetails);

	}

	@Override
	public void deleteSubscriptionPlan(Long sequenceNumber,String applicationId, String tenantId) {
		userRepo.deleteSubscriptionDetails(sequenceNumber, applicationId, tenantId);
	}

	@Override
	public List<SubscriptionDetails> findAllSubscriptionDetails(String applicationId, String tenantId) {
		return userRepo.findAllSubscriptionDetails(applicationId, tenantId);
	}

	@Override
	public Optional<SubscriptionDetails> findBySequenceNumber(Long sequenceNumber, String applicationId, String tenantId) {
		return userRepo.queryToFetchDetails(sequenceNumber, applicationId, tenantId);
	}
	
	@Override
	public Optional<SubscriptionDetails> findById(Long sequenceNumber) {
		return userRepo.findById(sequenceNumber);
	}

	@Override
	public SubscriptionDetails deactivateSubscriptionPlan(SubscriptionDetails subscriptionDetails) {
		userRepo.deactivateSubscriptionPlan(subscriptionDetails.getPlanId(), subscriptionDetails.getPlanName(),
				subscriptionDetails.getPlanDeactivationDate(), subscriptionDetails.getApplicationId(), subscriptionDetails.getTenantId());
		return subscriptionDetails;
	}

	@Override
	public SubscriptionDetails activateSubscriptionPlan(SubscriptionDetails subscriptionDetails) {
		userRepo.activateSubscriptionPlan(subscriptionDetails.getPlanId(), subscriptionDetails.getPlanName(),
				subscriptionDetails.getPlanActivationDate(), subscriptionDetails.getApplicationId(), subscriptionDetails.getTenantId());
		return subscriptionDetails;

	}

	@Override
	public void deleteSubscriptionPlanByName(String planId, String planName,String applicationId, String tenantId) {
		userRepo.deleteSubscriptionPlanByName(planId, planName, applicationId, tenantId);

	}
	
	@Override
	public Optional<SubscriptionDetails> findByPlanIdAndPlanName(String planId, String planName, String applicationId, String tenantId) {
		
		return userRepo.findByPlanIdAndName(planId, planName, applicationId, tenantId);
	}

}
