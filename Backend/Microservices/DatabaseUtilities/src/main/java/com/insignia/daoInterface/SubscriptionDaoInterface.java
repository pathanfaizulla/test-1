package com.insignia.daoInterface;

import java.util.List;
import java.util.Optional;

import com.insignia.entity.SubscriptionDetails;

public interface SubscriptionDaoInterface {

	public SubscriptionDetails saveSubscriptionPlan(SubscriptionDetails subscriptionDetails);

	public SubscriptionDetails updateSubscriptionPlan(SubscriptionDetails subscriptionDetails);

	public void deleteSubscriptionPlan(Long sequenceNumber, String applicationId, String tenantId);

	public SubscriptionDetails activateSubscriptionPlan(SubscriptionDetails subscriptionDetails);

	public SubscriptionDetails deactivateSubscriptionPlan(SubscriptionDetails subscriptionDetails);

	public List<SubscriptionDetails> findAllSubscriptionDetails(String applicationId, String tenantId);

	public Optional<SubscriptionDetails> findBySequenceNumber(Long sequenceNumber, String applicationId,
			String tenantId);

	public Optional<SubscriptionDetails> findByPlanIdAndPlanName(String planId, String planName, String applicationId,
			String tenantId);

	public void deleteSubscriptionPlanByName(String planId, String planName, String applicationId, String tenantId);

	public Optional<SubscriptionDetails> findById(Long sequenceNumber);
}
