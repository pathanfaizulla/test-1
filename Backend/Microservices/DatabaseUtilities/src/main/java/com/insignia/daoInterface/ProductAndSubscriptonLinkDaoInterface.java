package com.insignia.daoInterface;

import java.util.List;
import java.util.Optional;

import com.insignia.entity.ProductSubscriptionPlanLink;

public interface ProductAndSubscriptonLinkDaoInterface {

	public ProductSubscriptionPlanLink associateSubscriptionPlanWithProduct(ProductSubscriptionPlanLink productSubscriptionPlanLink);
	public void removeSubscriptionPlanForProduct(Long productSequenceNumber, Long subscriptionPlanSequenceNumber);
	public Optional<ProductSubscriptionPlanLink> findProductSubscritipionDetails(Long productSequenceNumber, Long subscriptionPlanSequenceNumber);
	public List<ProductSubscriptionPlanLink> findAllProductForGivenSubscription(List<Long> subscriptionPlanSequenceNumber);
	
}
