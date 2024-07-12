package com.insignia.repo;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insignia.entity.ProductSubscriptionPlanLink;

public interface ProductAndSubscriptonLinkRepository
		extends JpaRepository<ProductSubscriptionPlanLink, Serializable> {

	public static final String SUBSCRIPTION_PLAN_SEQUENCE_NUMBER = "subscription_plan_sequence_number";
	public static final String PRODUCT_SEQUENCE_NUMBER = "product_sequence_number";
	public static final String queryToFindAllProductForGivenSubscription = "Select * from product_subscription_plan_link where subscription_plan_sequence_number IN (:subscription_plan_sequence_number)";
	public static final String queryToRemovedProduct = "Delete from product_subscription_plan_link where product_sequence_number = :product_sequence_number and subscription_plan_sequence_number =:subscription_plan_sequence_number";
	public static final String findProductSubscriptionDetails = "Select * from product_subscription_plan_link where product_sequence_number = :product_sequence_number and subscription_plan_sequence_number =:subscription_plan_sequence_number";

	@Modifying
	@Query(value = queryToRemovedProduct, nativeQuery = true)
	public void removedProduct(@Param(PRODUCT_SEQUENCE_NUMBER) Long productSequenceNumber,
			@Param(SUBSCRIPTION_PLAN_SEQUENCE_NUMBER) Long subscriptionPlanSequenceNumber);

	@Query(value = findProductSubscriptionDetails, nativeQuery = true)
	public Optional<ProductSubscriptionPlanLink> findProductDetailsByProductSequenceNumber(
			@Param(PRODUCT_SEQUENCE_NUMBER) Long productSequenceNumber,
			@Param(SUBSCRIPTION_PLAN_SEQUENCE_NUMBER) Long subscriptionPlanSequenceNumber);

	@Query(value = queryToFindAllProductForGivenSubscription, nativeQuery = true)
	public List<ProductSubscriptionPlanLink> findAllProductForGivenSubscription(
			@Param(SUBSCRIPTION_PLAN_SEQUENCE_NUMBER) List<Long> subscriptionPlanSequenceNumber);

}
