package com.insignia.daoImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insignia.daoInterface.CustomerAndSubscriptonLinkDaoInterface;
import com.insignia.entity.CustomerSubscriptionPlanLink;
import com.insignia.repo.CustomerAndSubscriptonLinkRepository;

@Repository
public class CustomerAndSubscriptonLinkDaoImpl implements CustomerAndSubscriptonLinkDaoInterface {

	@Autowired
	private CustomerAndSubscriptonLinkRepository customerAndSubscriptonLinkRepository;

	@Override
	public CustomerSubscriptionPlanLink associateSubscriptionPlanWithCustomer(
			CustomerSubscriptionPlanLink customerSubscriptionPlanLink) {

		return customerAndSubscriptonLinkRepository.save(customerSubscriptionPlanLink);
	}

	@Override
	public void removeSubscriptionPlanForCustomer(Long customerSequenceNumber, Long subscriptionPlanSequenceNumber) {
		customerAndSubscriptonLinkRepository.removedCustomer(customerSequenceNumber, subscriptionPlanSequenceNumber);
	}

	@Override
	public Optional<CustomerSubscriptionPlanLink> findCustomerSubscritipionDetails(Long customerSequenceNumber,
			Long subscriptionPlanSequenceNumber) {
		return customerAndSubscriptonLinkRepository.findCustomerDetailsByCustomerSequenceNumber(customerSequenceNumber,
				subscriptionPlanSequenceNumber);

	}

	@Override
	public List<CustomerSubscriptionPlanLink> findAllCustomerSubscriptionDetails(Long customerSequenceNumber) {
		return customerAndSubscriptonLinkRepository.findAllCustomerSubscriptionDetails(customerSequenceNumber);
	}

}
