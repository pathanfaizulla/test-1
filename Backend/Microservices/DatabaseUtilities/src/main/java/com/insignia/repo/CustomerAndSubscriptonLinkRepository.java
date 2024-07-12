package com.insignia.repo;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insignia.entity.CustomerSubscriptionPlanLink;

public interface CustomerAndSubscriptonLinkRepository
		extends JpaRepository<CustomerSubscriptionPlanLink, Serializable> {

	public static final String SUBSCRIPTION_PLAN_SEQUENCE_NUMBER = "subscription_plan_sequence_number";
	public static final String CUSTOMER_SEQUENCE_NUMBER = "customer_sequence_number";
	public static final String queryToFindAllCustomerSubscriptionDetails = "Select * from customer_subscription_plan_link where customer_sequence_number=:customer_sequence_number";
	public static final String queryToRemovedCustomer = "Delete from customer_subscription_plan_link where customer_sequence_number = :customer_sequence_number and subscription_plan_sequence_number =:subscription_plan_sequence_number";
	public static final String findCustomerSubscriptionDetails = "Select * from customer_subscription_plan_link where customer_sequence_number = :customer_sequence_number and subscription_plan_sequence_number =:subscription_plan_sequence_number";

	@Modifying
	@Query(value = queryToRemovedCustomer, nativeQuery = true)
	public void removedCustomer(@Param(CUSTOMER_SEQUENCE_NUMBER) Long customerSequenceNumber,
			@Param(SUBSCRIPTION_PLAN_SEQUENCE_NUMBER) Long subscriptionPlanSequenceNumber);

	@Query(value = findCustomerSubscriptionDetails, nativeQuery = true)
	public Optional<CustomerSubscriptionPlanLink> findCustomerDetailsByCustomerSequenceNumber(
			@Param(CUSTOMER_SEQUENCE_NUMBER) Long customerSequenceNumber,
			@Param(SUBSCRIPTION_PLAN_SEQUENCE_NUMBER) Long subscriptionPlanSequenceNumber);

	@Query(value = queryToFindAllCustomerSubscriptionDetails, nativeQuery = true)
	public List<CustomerSubscriptionPlanLink> findAllCustomerSubscriptionDetails(
			@Param(CUSTOMER_SEQUENCE_NUMBER) Long customerSequenceNumber);

}
