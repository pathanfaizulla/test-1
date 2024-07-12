package com.insignia.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insignia.constants.CustomerAndSubscriptionPlanLinkConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.CustomerAndSubscriptonLinkDaoInterface;
import com.insignia.daoInterface.SubscriptionDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.CustomerSubscriptionPlanLink;
import com.insignia.entity.SubscriptionDetails;
import com.insignia.model.SubscriptonLinkRequest;
import com.insignia.model.SubscriptonLinkResponse;
import com.insignia.model.CustomerSubscriptonLinkRequest;
import com.insignia.model.CustomerSubscriptonLinkResponse;
import com.insignia.entity.PlanStatus;
import com.insignia.model.SubscriptionResponse;
import com.insignia.service.CustomerAndSubscriptonLinkServiceInterface;

@Service
public class CustomerAndSubscriptonLinkServiceImpl implements CustomerAndSubscriptonLinkServiceInterface {

	@Autowired
	private CustomerAndSubscriptonLinkDaoInterface customerAndSubscriptonLinkDaoInterface;

	@Autowired
	private SubscriptionDaoInterface subscriptionDaoInterface;

	@Autowired
	private TokenDaoInterface tokenDao;

	@Transactional
	@Override
	public SubscriptonLinkResponse associateSubscriptionPlanWithCustomer(
			CustomerSubscriptonLinkRequest customerSubscriptonLinkRequest)
			throws TokenExpiredException, InvalidInputParametersException {

		tokenDao.checkTokenValidity(customerSubscriptonLinkRequest.getCustomerSequenceNumber(),
				customerSubscriptonLinkRequest.getExpirationDuration());

		Optional<CustomerSubscriptionPlanLink> customerSubscriptionPlanLinkList = customerAndSubscriptonLinkDaoInterface
				.findCustomerSubscritipionDetails(customerSubscriptonLinkRequest.getCustomerSequenceNumber(),
						customerSubscriptonLinkRequest.getSubscriptionPlanSequenceNumber());

		if (customerSubscriptionPlanLinkList.isPresent()) {
			throw new InvalidInputParametersException(
					CustomerAndSubscriptionPlanLinkConstant.validateCustomerSubscriptionDetailsDuplicateDataErrorCode,
					CustomerAndSubscriptionPlanLinkConstant.validateCustomerSubscriptionDetailsDuplicateDataMessage);
		}
		Optional<SubscriptionDetails> subscriptionDetailsList = subscriptionDaoInterface
				.findById(customerSubscriptonLinkRequest.getSubscriptionPlanSequenceNumber());
		
		if (!subscriptionDetailsList.isPresent()) {
			throw new InvalidInputParametersException(
					CustomerAndSubscriptionPlanLinkConstant.validateSubscriptionDetailsNotFoundErrorCode,
					CustomerAndSubscriptionPlanLinkConstant.validateSubscriptionDetailsNotFoundMessage);
		}
		SubscriptionDetails subscriptionDetails = subscriptionDetailsList.get();
		if (!PlanStatus.ACTIVE.name().equals(subscriptionDetails.getPlanActivationStatus())
				|| subscriptionDetails.getPlanActivationDate().compareTo(new Date()) > 0
				|| subscriptionDetails.getPlanActiveTill().compareTo(new Date()) < 0) {
			throw new InvalidInputParametersException(
					CustomerAndSubscriptionPlanLinkConstant.validateSubscriptionDetailsActiveStateErrorCode,
					CustomerAndSubscriptionPlanLinkConstant.validateSubscriptionDetailsActiveStateMessage);
		}

		CustomerSubscriptionPlanLink customerSubscriptionPlanLink = new CustomerSubscriptionPlanLink();
		customerSubscriptionPlanLink
				.setCustomerSequenceNumber(customerSubscriptonLinkRequest.getCustomerSequenceNumber());
		
		customerSubscriptionPlanLink.setAutoRenewal(customerSubscriptonLinkRequest.getAutoRenewal());
		customerSubscriptionPlanLink.setSubscriptionPlanStatus(customerSubscriptonLinkRequest.getSubscriptionPlanStatus());
		customerSubscriptionPlanLink.setActivationDate(customerSubscriptonLinkRequest.getActivationDate());
		customerSubscriptionPlanLink.setCancellationDate(customerSubscriptonLinkRequest.getCancellationDate());
		customerSubscriptionPlanLink.setDeactivationDate(customerSubscriptonLinkRequest.getDeactivationDate());
		
		customerSubscriptionPlanLink.setSubscriptionDetails(subscriptionDetails);

		CustomerSubscriptionPlanLink associateSubscriptionPlanWithCustomer = customerAndSubscriptonLinkDaoInterface
				.associateSubscriptionPlanWithCustomer(customerSubscriptionPlanLink);

		return createResponseForCustomerAndSubscriptonWithParentEntity(
				associateSubscriptionPlanWithCustomer);
	}

	@Transactional
	@Override
	public void removeSubscriptionPlanForCustomer(SubscriptonLinkRequest customerAndSubscriptonLinkRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(customerAndSubscriptonLinkRequest.getCustomerSequenceNumber(),
				customerAndSubscriptonLinkRequest.getExpirationDuration());

		Optional<CustomerSubscriptionPlanLink> customerSubscriptionPlanLinkList = customerAndSubscriptonLinkDaoInterface
				.findCustomerSubscritipionDetails(customerAndSubscriptonLinkRequest.getCustomerSequenceNumber(),
						customerAndSubscriptonLinkRequest.getSubscriptionPlanSequenceNumber());

		if (!customerSubscriptionPlanLinkList.isPresent()) {
			throw new InvalidInputParametersException(
					CustomerAndSubscriptionPlanLinkConstant.validateCustomerAnsSubscriptionDetailsNotFoundErrorCode,
					CustomerAndSubscriptionPlanLinkConstant.validateCustomerAnsSubscriptionDetailsNotFoundMessage);
		}

		if (!customerAndSubscriptonLinkRequest.getIsToForceDelete()) {

			CustomerSubscriptionPlanLink customerSubscriptionPlanLink = customerSubscriptionPlanLinkList.get();
			SubscriptionDetails subscriptionDetails = customerSubscriptionPlanLink.getSubscriptionDetails();
			if (PlanStatus.ACTIVE.name().equals(subscriptionDetails.getPlanActivationStatus())
					&& subscriptionDetails.getPlanActivationDate().compareTo(new Date()) < 0
					&& subscriptionDetails.getPlanActiveTill().compareTo(new Date()) > 0) {
				throw new InvalidInputParametersException(
						CustomerAndSubscriptionPlanLinkConstant.validateCustomerSubscriptionLinkToForceDeleteErrorCode,
						CustomerAndSubscriptionPlanLinkConstant.validateCustomerSubscriptionLinkToForceDeleteMessage);
			}
		}
		customerAndSubscriptonLinkDaoInterface.removeSubscriptionPlanForCustomer(
				customerAndSubscriptonLinkRequest.getCustomerSequenceNumber(),
				customerAndSubscriptonLinkRequest.getSubscriptionPlanSequenceNumber());

	}

	@Transactional
	@Override
	public Optional<List<SubscriptonLinkResponse>> getAllSubscriptionPlanForCustomer(
			SubscriptonLinkRequest customerAndSubscriptonLinkRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(customerAndSubscriptonLinkRequest.getCustomerSequenceNumber(),
				customerAndSubscriptonLinkRequest.getExpirationDuration());

		List<SubscriptonLinkResponse> responseList = new ArrayList<>();
		
		if (customerAndSubscriptonLinkRequest.getCustomerSequenceNumber() != null
				&& customerAndSubscriptonLinkRequest.getSubscriptionPlanSequenceNumber() != null) {
			Optional<CustomerSubscriptionPlanLink> customerSubscriptionPlanLinkList = customerAndSubscriptonLinkDaoInterface
					.findCustomerSubscritipionDetails(customerAndSubscriptonLinkRequest.getCustomerSequenceNumber(),
							customerAndSubscriptonLinkRequest.getSubscriptionPlanSequenceNumber());

			if (customerSubscriptionPlanLinkList.isPresent()) {

				CustomerSubscriptionPlanLink customerSubscriptionPlanLink = customerSubscriptionPlanLinkList.get();

				responseList.add(createResponseForCustomerAndSubscriptonWithParentEntity(
						customerSubscriptionPlanLink));
			}

		} else {
			List<CustomerSubscriptionPlanLink> customerSubscriptionPlanLinkList = customerAndSubscriptonLinkDaoInterface
					.findAllCustomerSubscriptionDetails(customerAndSubscriptonLinkRequest.getCustomerSequenceNumber());

			if (!customerSubscriptionPlanLinkList.isEmpty()) {

				for (CustomerSubscriptionPlanLink customerSubscriptionPlanLink : customerSubscriptionPlanLinkList) {
					SubscriptonLinkResponse customerAndSubscriptonLinkResponse = new SubscriptonLinkResponse();
					customerAndSubscriptonLinkResponse
							.setSequenceNumber(customerSubscriptionPlanLink.getSequenceNumber());

					SubscriptionResponse subscriptionResponse = createResponseForSubscriptionDetailsEntity(
							customerSubscriptionPlanLink.getSubscriptionDetails());

					customerAndSubscriptonLinkResponse.setSubscriptionResponse(subscriptionResponse);

					responseList.add(customerAndSubscriptonLinkResponse);
				}
			}
		}
		return Optional.ofNullable(responseList);
	}

	private SubscriptonLinkResponse createResponseForCustomerAndSubscriptonWithParentEntity(
			CustomerSubscriptionPlanLink customerSubscriptionPlanLink) {

		CustomerSubscriptonLinkResponse customerAndSubscriptonLinkResponse = new CustomerSubscriptonLinkResponse();
		customerAndSubscriptonLinkResponse.setSequenceNumber(customerSubscriptionPlanLink.getSequenceNumber());
		customerAndSubscriptonLinkResponse.setAutoRenewal(customerSubscriptionPlanLink.getAutoRenewal());
		customerAndSubscriptonLinkResponse.setSubscriptionPlanStatus(customerSubscriptionPlanLink.getSubscriptionPlanStatus());
		customerAndSubscriptonLinkResponse.setActivationDate(customerSubscriptionPlanLink.getActivationDate());
		customerAndSubscriptonLinkResponse.setCancellationDate(customerSubscriptionPlanLink.getCancellationDate());
		customerAndSubscriptonLinkResponse.setDeactivationDate(customerSubscriptionPlanLink.getDeactivationDate());
		
		SubscriptionDetails subscriptionDetails = customerSubscriptionPlanLink.getSubscriptionDetails();

		SubscriptionResponse subscriptionResponse = createResponseForSubscriptionDetailsEntity(subscriptionDetails);

		customerAndSubscriptonLinkResponse.setSubscriptionResponse(subscriptionResponse);
		return customerAndSubscriptonLinkResponse;
	}

	private SubscriptionResponse createResponseForSubscriptionDetailsEntity(SubscriptionDetails subscriptionDetails) {
		SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
		subscriptionResponse.setSequenceNumber(subscriptionDetails.getSequenceNumber());
		subscriptionResponse.setPlanId(subscriptionDetails.getPlanId());
		subscriptionResponse.setPlanName(subscriptionDetails.getPlanName());
		subscriptionResponse.setPlanDescription(subscriptionDetails.getPlanDescription());
		subscriptionResponse.setPlanDuration(subscriptionDetails.getPlanDuration());
		subscriptionResponse.setPlanPricing(subscriptionDetails.getPlanPricing());
		subscriptionResponse.setPlanActivationDate(subscriptionDetails.getPlanActivationDate());
		subscriptionResponse.setPlanDeactivationDate(subscriptionDetails.getPlanDeactivationDate());
		subscriptionResponse.setPlanActivationStatus(subscriptionDetails.getPlanActivationStatus());
		subscriptionResponse.setPercentageDiscount(subscriptionDetails.getPercentageDiscount());
		subscriptionResponse.setPlanActiveTill(subscriptionDetails.getPlanActiveTill());
		subscriptionResponse.setAutoRenewal(subscriptionDetails.getAutoRenewal());
		subscriptionResponse.setMinOrderValue(subscriptionDetails.getMinOrderValue());
		
		return subscriptionResponse;
	}
}
