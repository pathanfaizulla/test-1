package com.insignia.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.CustomListDetailsDaoInterface;
import com.insignia.daoInterface.CustomerCustomlistProductsLinkDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.CustomListDetailsEntity;
import com.insignia.entity.CustomerCustomlistProductsLinkEntity;
import com.insignia.entity.ProductDetails;
import com.insignia.model.CustomListDetails;
import com.insignia.model.CustomListDetailsRequest;
import com.insignia.model.CustomListManagementRequest;
import com.insignia.model.CustomListManagementResponse;
import com.insignia.model.ProductDetailsRequest;
import com.insignia.model.ProductDetailsResponse;
import com.insignia.serviceInterface.CustomListServiceInterface;

import jakarta.transaction.Transactional;

@Service
public class CustomListServiceImpl implements CustomListServiceInterface {

	@Autowired
	private CustomListDetailsDaoInterface customListDetailsDaoInterface;

	@Autowired
	private CustomerCustomlistProductsLinkDaoInterface customerCustomlistProductsLinkDaoInterface;

	@Autowired
	private TokenDaoInterface tokenDao;

	@Transactional
	@Override
	public CustomListManagementResponse saveCustomList(CustomListManagementRequest customListManagementRequest)
			throws TokenExpiredException, InvalidInputParametersException {

		Long customerSequenceNumber = customListManagementRequest.getCustomerSequenceNumber();

		tokenDao.checkTokenValidity(customerSequenceNumber, customListManagementRequest.getTokenExpirationDuration());

		List<CustomerCustomlistProductsLinkEntity> customerCustomlistProductsLinkList = null;
		List<CustomListDetailsEntity> customListDetailsEntityList = null;

		if (customListManagementRequest != null) {

			List<CustomListDetailsRequest> customListDetailsRequestList = customListManagementRequest
					.getCustomListDetailsRequestList();

			if (customListDetailsRequestList != null && customListDetailsRequestList.size() > 0) {

				Map<Long, List<ProductDetailsRequest>> toSaveDetailsDataMap = new HashMap<>();
				List<ProductDetailsRequest> tmpProductDetailsRequestList = null;
				Set<Long> customListSeqNumberList = new HashSet<>();

				for (CustomListDetailsRequest customListDetailsRequest : customListDetailsRequestList) {
					customListSeqNumberList.add(customListDetailsRequest.getSequenceNumber());

					tmpProductDetailsRequestList = customListDetailsRequest.getProductDetailsRequestList();
					if (tmpProductDetailsRequestList != null && tmpProductDetailsRequestList.size() > 0) {
						toSaveDetailsDataMap.put(customListDetailsRequest.getSequenceNumber(),
								customListDetailsRequest.getProductDetailsRequestList());
					}
				}

				Set<Long> existingSeqNumberList = toSaveDetailsDataMap.keySet();

				if (existingSeqNumberList.size() > 0) {

					customerCustomlistProductsLinkList = customerCustomlistProductsLinkDaoInterface
							.findCustomListProductLink(existingSeqNumberList);
					List<CustomerCustomlistProductsLinkEntity> toSaveCustomListDetailsList = new ArrayList<>();

					for (Long customListSeqNum : existingSeqNumberList) {

						tmpProductDetailsRequestList = toSaveDetailsDataMap.get(customListSeqNum);

						for (ProductDetailsRequest productDetailsRequest : tmpProductDetailsRequestList) {

							boolean isProductFound = false;

							for (CustomerCustomlistProductsLinkEntity customerCustomlistProductsLinkEntity : customerCustomlistProductsLinkList) {

								if (productDetailsRequest.getProductSequenceNumber()
										.equals(customerCustomlistProductsLinkEntity.getProductSeqNumber())) {

									customerCustomlistProductsLinkEntity
											.setProductQuantity(productDetailsRequest.getQuantity());
									toSaveCustomListDetailsList.add(customerCustomlistProductsLinkEntity);
									isProductFound = true;
									break;
								}

							}

							if (!isProductFound) {

								CustomerCustomlistProductsLinkEntity tempCustomListDetails = new CustomerCustomlistProductsLinkEntity();

								tempCustomListDetails.setCustomerCustomListSeqNumber(customListSeqNum);
								tempCustomListDetails
										.setProductSeqNumber(productDetailsRequest.getProductSequenceNumber());
								tempCustomListDetails.setProductQuantity(productDetailsRequest.getQuantity());
								toSaveCustomListDetailsList.add(tempCustomListDetails);
							}
						}
					}

					customerCustomlistProductsLinkDaoInterface.saveCustomList(toSaveCustomListDetailsList);
				}

				delinkProductFromCustomList(customListDetailsRequestList);

				customListDetailsEntityList = customListDetailsDaoInterface
						.findBySequenceNumber(customListSeqNumberList);
			}
		}
		return createCustomListDetailsResponse(customListDetailsEntityList);
	}

	private void delinkProductFromCustomList(List<CustomListDetailsRequest> customListDetailsRequestList) {
		for (CustomListDetailsRequest customListDetailsRequest : customListDetailsRequestList) {
			if (customListDetailsRequest.getDelinkedProductIdList() != null
					&& !customListDetailsRequest.getDelinkedProductIdList().isEmpty()) {

				customerCustomlistProductsLinkDaoInterface.removeProductFromCustomList(customListDetailsRequest.getSequenceNumber(),
						customListDetailsRequest.getDelinkedProductIdList());
			}
		}
	}

	private CustomListManagementResponse createCustomListDetailsResponse(
			List<CustomListDetailsEntity> customListDetailsList) {

		List<CustomListDetails> tmpCustomListDetailsList = new ArrayList<>();

		if (customListDetailsList != null && customListDetailsList.size() >0 ) {

			Map<String, List<CustomerCustomlistProductsLinkEntity>> listAndProductMap = new HashMap<>();
			List<Long> productSequenceNumberList = new ArrayList<>();
			List<CustomerCustomlistProductsLinkEntity> tmpLinkEntityList = null;

			for (CustomListDetailsEntity customListDetailsEntity : customListDetailsList) {
				tmpLinkEntityList = customListDetailsEntity.getCusomListProductLinkList();
				
				for (CustomerCustomlistProductsLinkEntity customerCustomlistProductsLinkEntity : tmpLinkEntityList) {
					productSequenceNumberList.add(customerCustomlistProductsLinkEntity.getProductSeqNumber());
				}
				
				listAndProductMap.put(customListDetailsEntity.getCustomListName(), tmpLinkEntityList);
		
			}

			List<ProductDetails> productDetailsList = customerCustomlistProductsLinkDaoInterface
					.getCustomListProductDetails(productSequenceNumberList);

			for (CustomListDetailsEntity customListDetailsEntity : customListDetailsList) {

				tmpLinkEntityList = listAndProductMap.get(customListDetailsEntity.getCustomListName());

				List<ProductDetailsResponse> productDetailsResponseList = new ArrayList<>();

				for (CustomerCustomlistProductsLinkEntity linkEntity : tmpLinkEntityList) {

					for (ProductDetails productDetails : productDetailsList) {

						if (linkEntity.getProductSeqNumber()
								.equals(productDetails.getProductSequenceNumber())) {

							ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();

							productDetailsResponse.setProductSequenceNumber(productDetails.getProductSequenceNumber());
							productDetailsResponse.setProductAvailableQuantity(productDetails.getProductQuantity());
							productDetailsResponse.setCustomListQuantity(linkEntity.getProductQuantity());
							productDetailsResponse.setProductId(productDetails.getProductId());
							productDetailsResponse.setProductName(productDetails.getProductName());
							productDetailsResponse.setDescription(productDetails.getDescription());
							productDetailsResponse.setMeasuringUnit(productDetails.getMeasuringUnit());
							productDetailsResponse.setProductImagePath(productDetails.getProductImagePath());
							productDetailsResponse.setDefaultImage(productDetails.getDefaultImage());
							productDetailsResponse.setMeasuringQuantity(productDetails.getMeasuringQuantity());
							productDetailsResponse
									.setProductPerUnitActualPrice(productDetails.getProductPerUnitActualPrice());
							productDetailsResponse
									.setProductPerUnitCurrentPrice(productDetails.getProductPerUnitCurrentPrice());

							productDetailsResponseList.add(productDetailsResponse);
							break;
						}
					}
				}
				CustomListDetails customListDetailsResponse = new CustomListDetails();
				customListDetailsResponse.setCustomListName(customListDetailsEntity.getCustomListName());
				customListDetailsResponse.setCustomListSequenceNumber(customListDetailsEntity.getSequenceNumber());
				customListDetailsResponse.setProductDetailsResponse(productDetailsResponseList);
				tmpCustomListDetailsList.add(customListDetailsResponse);
			}
		}

		CustomListManagementResponse customListManagementResponse = new CustomListManagementResponse();
		customListManagementResponse.setCustomListDetails(tmpCustomListDetailsList);
		return customListManagementResponse;
	}


	@Transactional
	@Override
	public CustomListManagementResponse getCustomListForCustomer(Long customerSequenceNumber,
			Integer tokenExpirationDuration) throws TokenExpiredException {

		tokenDao.checkTokenValidity(customerSequenceNumber, tokenExpirationDuration);

		List<CustomListDetailsEntity> listOfCustomListDetails = customListDetailsDaoInterface.getCustomListForCustomer(customerSequenceNumber);

		return createCustomListDetailsResponse(listOfCustomListDetails);
	}

	@Transactional
	@Override
	public void deleteAllCustomListForCustomer(Long customerSequenceNumber, Integer tokenExpirationDuration)
			throws TokenExpiredException {

		tokenDao.checkTokenValidity(customerSequenceNumber, tokenExpirationDuration);

		customListDetailsDaoInterface.deleteAllCustomListForCustomer(customerSequenceNumber);

	}

	@Transactional
	@Override
	public void deleteCustomList(Long customerSequenceNumber, List<String> customListNameList,
			Integer expirationDuration) throws TokenExpiredException {

		tokenDao.checkTokenValidity(customerSequenceNumber, expirationDuration);

		customListDetailsDaoInterface.deleteCustomList(customerSequenceNumber, customListNameList);

	}

	@Transactional
	@Override
	public CustomListManagementResponse createCustomList(CustomListManagementRequest customListManagementRequest)
			throws TokenExpiredException, InvalidInputParametersException {

		tokenDao.checkTokenValidity(customListManagementRequest.getCustomerSequenceNumber(),
				customListManagementRequest.getTokenExpirationDuration());

		List<CustomListDetailsEntity> customListDetailsEntityList = new ArrayList<>();

		if (customListManagementRequest != null) {

			List<CustomListDetailsRequest> customListDetailsRequestList = customListManagementRequest
					.getCustomListDetailsRequestList();

			if (customListDetailsRequestList != null && customListDetailsRequestList.size() > 0) {

				for (CustomListDetailsRequest customListDetailsRequest : customListDetailsRequestList) {

					CustomListDetailsEntity customListDetailsEntity = new CustomListDetailsEntity();

					customListDetailsEntity
							.setCustomerSequenceNumber(customListManagementRequest.getCustomerSequenceNumber());
					customListDetailsEntity.setCustomListName(customListDetailsRequest.getCustomListName());
					customListDetailsEntityList.add(customListDetailsEntity);

				}
				customListDetailsEntityList = customListDetailsDaoInterface.saveAll(customListDetailsEntityList);

			}

		}

		return createCustomListResponse(customListDetailsEntityList);
	}

	private CustomListManagementResponse createCustomListResponse(
			List<CustomListDetailsEntity> tempCustomListDetailsEntityList) {

		CustomListManagementResponse customListManagementResponse = new CustomListManagementResponse();

		List<CustomListDetails> customListDetailsList = new ArrayList<>();

		for (CustomListDetailsEntity customListDetailsEntity : tempCustomListDetailsEntityList) {

			CustomListDetails customListDetails = new CustomListDetails();

			customListDetails.setCustomListSequenceNumber(customListDetailsEntity.getCustomerSequenceNumber());
			customListDetails.setCustomListName(customListDetailsEntity.getCustomListName());

			customListDetailsList.add(customListDetails);
		}
		customListManagementResponse.setCustomListDetails(customListDetailsList);
		return customListManagementResponse;
	}

	@Transactional(rollbackOn = InvalidInputParametersException.class)
	@Override
	public CustomListManagementResponse updateCustomListName(CustomListManagementRequest customListManagementRequest)
			throws TokenExpiredException, InvalidInputParametersException {

		tokenDao.checkTokenValidity(customListManagementRequest.getCustomerSequenceNumber(),
				customListManagementRequest.getTokenExpirationDuration());

		List<CustomListDetailsRequest> customListDetailsRequestList = customListManagementRequest
				.getCustomListDetailsRequestList();

		Set<Long> sequenceNumberList = new HashSet<>();

		for (CustomListDetailsRequest customListDetailsRequest : customListDetailsRequestList) {
			sequenceNumberList.add(customListDetailsRequest.getSequenceNumber());
		}

		List<CustomListDetailsEntity> customListDetailsEntityList = customListDetailsDaoInterface
				.findBySequenceNumber(sequenceNumberList);

		for (CustomListDetailsRequest customListDetailsRequest : customListDetailsRequestList) {

			for (CustomListDetailsEntity customListDetailsEntity : customListDetailsEntityList) {

				if (customListDetailsRequest.getSequenceNumber().equals(customListDetailsEntity.getSequenceNumber())) {
					customListDetailsEntity.setCustomListName(customListDetailsRequest.getCustomListName());

					break;
				}

			}
		}

		return createCustomListResponse(customListDetailsDaoInterface.saveAll(customListDetailsEntityList));

	}
}
