package com.insignia.daoInterface;

import java.util.List;
import java.util.Optional;

import com.insignia.entity.CustomerSubscriptionPlanLink;

public interface CustomerAndSubscriptonLinkDaoInterface {

	public CustomerSubscriptionPlanLink associateSubscriptionPlanWithCustomer(CustomerSubscriptionPlanLink customerSubscriptionPlanLink);
	public void removeSubscriptionPlanForCustomer(Long customerSequenceNumber, Long subscriptionPlanSequenceNumber);
	public Optional<CustomerSubscriptionPlanLink> findCustomerSubscritipionDetails(Long customerSequenceNumber, Long subscriptionPlanSequenceNumber);
	public List<CustomerSubscriptionPlanLink> findAllCustomerSubscriptionDetails(Long customerSequenceNumber);
	
}
