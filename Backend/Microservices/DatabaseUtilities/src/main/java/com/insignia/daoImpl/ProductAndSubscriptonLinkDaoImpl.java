package com.insignia.daoImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insignia.daoInterface.ProductAndSubscriptonLinkDaoInterface;
import com.insignia.entity.ProductSubscriptionPlanLink;
import com.insignia.repo.ProductAndSubscriptonLinkRepository;

@Repository
public class ProductAndSubscriptonLinkDaoImpl implements ProductAndSubscriptonLinkDaoInterface {

	@Autowired
	private ProductAndSubscriptonLinkRepository productAndSubscriptonLinkRepository;

	@Override
	public ProductSubscriptionPlanLink associateSubscriptionPlanWithProduct(
			ProductSubscriptionPlanLink productSubscriptionPlanLink) {

		return productAndSubscriptonLinkRepository.save(productSubscriptionPlanLink);
	}

	@Override
	public void removeSubscriptionPlanForProduct(Long productSequenceNumber, Long subscriptionPlanSequenceNumber) {
		productAndSubscriptonLinkRepository.removedProduct(productSequenceNumber, subscriptionPlanSequenceNumber);
	}

	@Override
	public Optional<ProductSubscriptionPlanLink> findProductSubscritipionDetails(Long productSequenceNumber,
			Long subscriptionPlanSequenceNumber) {
		return productAndSubscriptonLinkRepository.findProductDetailsByProductSequenceNumber(productSequenceNumber,
				subscriptionPlanSequenceNumber);

	}

	@Override
	public List<ProductSubscriptionPlanLink> findAllProductForGivenSubscription(List<Long> subscriptionPlanSequenceNumber) {
		return productAndSubscriptonLinkRepository.findAllProductForGivenSubscription(subscriptionPlanSequenceNumber);
	}

}
