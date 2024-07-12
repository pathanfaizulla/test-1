package com.insignia.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insignia.constants.ProductAndSubscriptionPlanLinkConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.ProductAndSubscriptonLinkDaoInterface;
import com.insignia.daoInterface.SubscriptionDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.ProductSubscriptionPlanLink;
import com.insignia.entity.SubscriptionDetails;
import com.insignia.entity.PlanStatus;
import com.insignia.model.ProductSubscriptionLinkRequest;
import com.insignia.model.SubscriptionResponse;
import com.insignia.model.SubscriptonLinkResponse;
import com.insignia.service.ProductAndSubscriptonLinkServiceInterface;

@Service
public class ProductAndSubscriptonLinkServiceImpl implements ProductAndSubscriptonLinkServiceInterface {

	@Autowired
	private ProductAndSubscriptonLinkDaoInterface productAndSubscriptonLinkDaoInterface;

	@Autowired
	private SubscriptionDaoInterface subscriptionDaoInterface;

	@Autowired
	private TokenDaoInterface tokenDao;

	@Transactional
	@Override
	public SubscriptonLinkResponse associateSubscriptionPlanWithProduct(
			ProductSubscriptionLinkRequest productSubscriptionLinkRequest)
			throws TokenExpiredException, InvalidInputParametersException {

		tokenDao.checkTokenValidity(productSubscriptionLinkRequest.getCustomerSequenceNumber(),
				productSubscriptionLinkRequest.getExpirationDuration());

		Optional<ProductSubscriptionPlanLink> productSubscriptionPlanLinkList = productAndSubscriptonLinkDaoInterface
				.findProductSubscritipionDetails(productSubscriptionLinkRequest.getProductSequenceNumber(),
						productSubscriptionLinkRequest.getSubscriptionPlanSequenceNumber());

		if (productSubscriptionPlanLinkList.isPresent()) {
			throw new InvalidInputParametersException(
					ProductAndSubscriptionPlanLinkConstant.validateProductSubscriptionDetailsDuplicateDataErrorCode,
					ProductAndSubscriptionPlanLinkConstant.validateProductSubscriptionDetailsDuplicateDataMessage);
		}
		Optional<SubscriptionDetails> subscriptionDetailsList = subscriptionDaoInterface
				.findById(productSubscriptionLinkRequest.getSubscriptionPlanSequenceNumber());

		if (!subscriptionDetailsList.isPresent()) {
			throw new InvalidInputParametersException(
					ProductAndSubscriptionPlanLinkConstant.validateSubscriptionDetailsNotFoundErrorCode,
					ProductAndSubscriptionPlanLinkConstant.validateSubscriptionDetailsNotFoundMessage);
		}
		SubscriptionDetails subscriptionDetails = subscriptionDetailsList.get();
		if (!PlanStatus.ACTIVE.name().equals(subscriptionDetails.getPlanActivationStatus())
				|| subscriptionDetails.getPlanActivationDate().compareTo(new Date()) > 0
				|| subscriptionDetails.getPlanActiveTill().compareTo(new Date()) < 0) {
			throw new InvalidInputParametersException(
					ProductAndSubscriptionPlanLinkConstant.validateSubscriptionDetailsActiveStateErrorCode,
					ProductAndSubscriptionPlanLinkConstant.validateSubscriptionDetailsActiveStateMessage);
		}

		ProductSubscriptionPlanLink productSubscriptionPlanLink = new ProductSubscriptionPlanLink();
		productSubscriptionPlanLink.setProductSequenceNumber(productSubscriptionLinkRequest.getProductSequenceNumber());
		productSubscriptionPlanLink.setSubscriptionDetails(subscriptionDetails);

		ProductSubscriptionPlanLink associateSubscriptionPlanWithProduct = productAndSubscriptonLinkDaoInterface
				.associateSubscriptionPlanWithProduct(productSubscriptionPlanLink);

		return createResponseForProductAndSubscriptonWithParentEntity(associateSubscriptionPlanWithProduct);
	}

	@Transactional
	@Override
	public void removeSubscriptionPlanForProduct(ProductSubscriptionLinkRequest productSubscriptionLinkRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(productSubscriptionLinkRequest.getCustomerSequenceNumber(),
				productSubscriptionLinkRequest.getExpirationDuration());

		Optional<ProductSubscriptionPlanLink> productSubscriptionPlanLinkList = productAndSubscriptonLinkDaoInterface
				.findProductSubscritipionDetails(productSubscriptionLinkRequest.getProductSequenceNumber(),
						productSubscriptionLinkRequest.getSubscriptionPlanSequenceNumber());

		if (!productSubscriptionPlanLinkList.isPresent()) {
			throw new InvalidInputParametersException(
					ProductAndSubscriptionPlanLinkConstant.validateProductAnsSubscriptionDetailsNotFoundErrorCode,
					ProductAndSubscriptionPlanLinkConstant.validateProductAnsSubscriptionDetailsNotFoundMessage);
		}

		if (!productSubscriptionLinkRequest.getIsToForceDelete()) {

			ProductSubscriptionPlanLink productSubscriptionPlanLink = productSubscriptionPlanLinkList.get();
			SubscriptionDetails subscriptionDetails = productSubscriptionPlanLink.getSubscriptionDetails();
			if (PlanStatus.ACTIVE.name().equals(subscriptionDetails.getPlanActivationStatus())
					&& subscriptionDetails.getPlanActivationDate().compareTo(new Date()) < 0
					&& subscriptionDetails.getPlanActiveTill().compareTo(new Date()) > 0) {
				throw new InvalidInputParametersException(
						ProductAndSubscriptionPlanLinkConstant.validateProductSubscriptionLinkToForceDeleteErrorCode,
						ProductAndSubscriptionPlanLinkConstant.validateProductSubscriptionLinkToForceDeleteMessage);
			}
		}
		productAndSubscriptonLinkDaoInterface.removeSubscriptionPlanForProduct(
				productSubscriptionLinkRequest.getProductSequenceNumber(),
				productSubscriptionLinkRequest.getSubscriptionPlanSequenceNumber());

	}

	@Transactional
	@Override
	public Optional<List<SubscriptonLinkResponse>> getAllSubscriptionPlanForProduct(
			ProductSubscriptionLinkRequest productSubscriptionLinkRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(productSubscriptionLinkRequest.getCustomerSequenceNumber(),
				productSubscriptionLinkRequest.getExpirationDuration());

		List<SubscriptonLinkResponse> responseList = new ArrayList<>();

		if (productSubscriptionLinkRequest.getSubscriptionPlanSequenceNumber() != null) {
			if (productSubscriptionLinkRequest.getProductSequenceNumber() != null) {
				Optional<ProductSubscriptionPlanLink> productSubscriptionPlanLinkList = productAndSubscriptonLinkDaoInterface
						.findProductSubscritipionDetails(productSubscriptionLinkRequest.getProductSequenceNumber(),
								productSubscriptionLinkRequest.getSubscriptionPlanSequenceNumber());

				if (productSubscriptionPlanLinkList.isPresent()) {

					ProductSubscriptionPlanLink productSubscriptionPlanLink = productSubscriptionPlanLinkList.get();

					responseList
							.add(createResponseForProductAndSubscriptonWithParentEntity(productSubscriptionPlanLink));
				}

			} else {
				List<Long> subscriptionSequenceNumberList = new ArrayList<Long>();
				subscriptionSequenceNumberList.add(productSubscriptionLinkRequest.getSubscriptionPlanSequenceNumber());

				fetchProductlistForGivenSubscriptionList(responseList, subscriptionSequenceNumberList);
			}
		} else if (productSubscriptionLinkRequest.getSubscriptionSequenceNumberList().size() > 0) {

			fetchProductlistForGivenSubscriptionList(responseList,
					productSubscriptionLinkRequest.getSubscriptionSequenceNumberList());

		}
		return Optional.ofNullable(responseList);
	}

	private void fetchProductlistForGivenSubscriptionList(List<SubscriptonLinkResponse> responseList,
			List<Long> subscriptionSequenceNumberList) {

		List<ProductSubscriptionPlanLink> productSubscriptionPlanLinkList = productAndSubscriptonLinkDaoInterface
				.findAllProductForGivenSubscription(subscriptionSequenceNumberList);

		if (!productSubscriptionPlanLinkList.isEmpty()) {

			Map<Long, SubscriptonLinkResponse> subscriptionResponseMap = new HashMap<Long, SubscriptonLinkResponse>();

			for (Long subscriptionSequenceNumber : subscriptionSequenceNumberList) {

				SubscriptonLinkResponse productAndSubscriptonLinkResponse = null;

				for (ProductSubscriptionPlanLink productSubscriptionPlanLinkTmp : productSubscriptionPlanLinkList) {

					if (subscriptionSequenceNumber
							.equals(productSubscriptionPlanLinkTmp.getSubscriptionDetails().getSequenceNumber())) {

						productAndSubscriptonLinkResponse = subscriptionResponseMap.get(subscriptionSequenceNumber);

						if (productAndSubscriptonLinkResponse == null) {

							productAndSubscriptonLinkResponse = new SubscriptonLinkResponse();
							SubscriptionResponse subscriptionResponse = createResponseForSubscriptionDetailsEntity(
									productSubscriptionPlanLinkTmp.getSubscriptionDetails());

							subscriptionResponse.getAssociatedProductSequenceNumberList()
									.add(productSubscriptionPlanLinkTmp.getProductSequenceNumber());
							productAndSubscriptonLinkResponse.setSubscriptionResponse(subscriptionResponse);

							subscriptionResponseMap.put(subscriptionSequenceNumber, productAndSubscriptonLinkResponse);
							responseList.add(productAndSubscriptonLinkResponse);

						} else {
							productAndSubscriptonLinkResponse.getSubscriptionResponse()
									.getAssociatedProductSequenceNumberList()
									.add(productSubscriptionPlanLinkTmp.getProductSequenceNumber());
						}

					}

				}

			}
		}

	}

	private SubscriptonLinkResponse createResponseForProductAndSubscriptonWithParentEntity(
			ProductSubscriptionPlanLink productSubscriptionPlanLink) {

		SubscriptonLinkResponse subscriptonLinkResponse = new SubscriptonLinkResponse();
		subscriptonLinkResponse.setSequenceNumber(productSubscriptionPlanLink.getSequenceNumber());

		SubscriptionDetails subscriptionDetails = productSubscriptionPlanLink.getSubscriptionDetails();

		SubscriptionResponse subscriptionResponse = createResponseForSubscriptionDetailsEntity(subscriptionDetails);

		subscriptionResponse.getAssociatedProductSequenceNumberList()
				.add(productSubscriptionPlanLink.getProductSequenceNumber());
		subscriptonLinkResponse.setSubscriptionResponse(subscriptionResponse);
		return subscriptonLinkResponse;
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
		return subscriptionResponse;
	}

}
