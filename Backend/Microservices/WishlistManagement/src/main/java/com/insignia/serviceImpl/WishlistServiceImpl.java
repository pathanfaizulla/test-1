package com.insignia.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.daoInterface.WishlistDaoInterface;
import com.insignia.entity.ProductDetails;
import com.insignia.entity.WishlistDetailsEntity;
import com.insignia.model.ProductDetailsRequest;
import com.insignia.model.ProductDetailsResponse;
import com.insignia.model.WishlistDetails;
import com.insignia.model.WishlistDetailsRequest;
import com.insignia.model.WishlistManagementResponse;
import com.insignia.serviceInterface.WishlistServiceInterface;

import jakarta.transaction.Transactional;

@Service
public class WishlistServiceImpl implements WishlistServiceInterface {

	@Autowired
	private WishlistDaoInterface wishlistDaoInterface;

	@Autowired
	private TokenDaoInterface tokenDao;

	@Transactional
	@Override
	public WishlistManagementResponse saveWishlistForCustomer(WishlistDetailsRequest wishlistDetailsRequest)
			throws TokenExpiredException, InvalidInputParametersException {

		tokenDao.checkTokenValidity(wishlistDetailsRequest.getCustomerSequenceNumber(),
				wishlistDetailsRequest.getExpirationDuration());

		WishlistDetailsEntity wishlistDetailsEntity = new WishlistDetailsEntity();

		if (wishlistDetailsRequest != null) {
			wishlistDetailsEntity.setCustomerSequenceNumber(wishlistDetailsRequest.getCustomerSequenceNumber());

			List<ProductDetailsRequest> productDetailsRequestList = wishlistDetailsRequest
					.getProductDetailsRequestList();

			List<Long> productSequenceNumberList = new ArrayList<>();

			for (ProductDetailsRequest productDetailsRequest : productDetailsRequestList) {
				productSequenceNumberList.add(productDetailsRequest.getProductSequenceNumber());
			}

			List<WishlistDetailsEntity> wishlistDetailsList = wishlistDaoInterface
					.findWishlistDetailByProductSequenceNumber(wishlistDetailsRequest.getCustomerSequenceNumber(),
							productSequenceNumberList);

			for (ProductDetailsRequest productDetailsRequest : productDetailsRequestList) {
				boolean matchFound = false;
				for (WishlistDetailsEntity detailsEntity : wishlistDetailsList) {
					if (productDetailsRequest.getProductSequenceNumber()
							.equals(detailsEntity.getProductSequenceNumber())) {
						if (!productDetailsRequest.getQuantity().equals(detailsEntity.getQuantity())) {
							detailsEntity.setQuantity(productDetailsRequest.getQuantity());
							matchFound = true;
							break;
						} else if (productDetailsRequest.getQuantity().equals(detailsEntity.getQuantity())) {
							matchFound = true;
						}
					}
				}

				if (!matchFound) {
					WishlistDetailsEntity tempWishlistDetail = new WishlistDetailsEntity();
					tempWishlistDetail.setProductSequenceNumber(productDetailsRequest.getProductSequenceNumber());
					tempWishlistDetail.setQuantity(productDetailsRequest.getQuantity());
					tempWishlistDetail.setTimestamp(new Date());
					tempWishlistDetail.setCustomerSequenceNumber(wishlistDetailsRequest.getCustomerSequenceNumber());
					wishlistDetailsList.add(tempWishlistDetail);
				}

			}
			wishlistDaoInterface.saveWishlistForCustomer(wishlistDetailsList);
		}

		return getCustomerWishlist(wishlistDetailsRequest.getCustomerSequenceNumber(), 0);
	}

	@Transactional
	@Override
	public WishlistManagementResponse getCustomerWishlist(Long customerSequenceNumber, Integer expirationDuration)
			throws TokenExpiredException {

		tokenDao.checkTokenValidity(customerSequenceNumber, expirationDuration);

		List<WishlistDetailsEntity> listOfWishlistDetails = wishlistDaoInterface
				.getCustomerWishlist(customerSequenceNumber);

		List<WishlistDetails> wishlistDetailsList = new ArrayList<>();
		List<Long> productSequenceNumberList = new ArrayList<>();
		for (WishlistDetailsEntity wishlistDetailsEntity : listOfWishlistDetails) {
			productSequenceNumberList.add(wishlistDetailsEntity.getProductSequenceNumber());
		}

		List<ProductDetails> productDetailsList = wishlistDaoInterface
				.getCustomListProductDetails(productSequenceNumberList);

		for (WishlistDetailsEntity wishlistDetailsEntity : listOfWishlistDetails) {
			for (ProductDetails productDetails : productDetailsList) {
				if (wishlistDetailsEntity.getProductSequenceNumber()
						.equals(productDetails.getProductSequenceNumber())) {

					WishlistDetails wishlistDetailsResponse = new WishlistDetails();
					
					ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();
					productDetailsResponse.setProductSequenceNumber(productDetails.getProductSequenceNumber());
					productDetailsResponse.setProductAvailableQuantity(productDetails.getProductQuantity());
					productDetailsResponse.setProductId(productDetails.getProductId());
					productDetailsResponse.setProductName(productDetails.getProductName());
					productDetailsResponse.setDescription(productDetails.getDescription());
					productDetailsResponse.setMeasuringUnit(productDetails.getMeasuringUnit());
					productDetailsResponse.setProductImagePath(productDetails.getProductImagePath());
					productDetailsResponse.setDefaultImage(productDetails.getDefaultImage());
					productDetailsResponse.setMeasuringQuantity(productDetails.getMeasuringQuantity());
					productDetailsResponse.setProductPerUnitActualPrice(productDetails.getProductPerUnitActualPrice());
					productDetailsResponse
							.setProductPerUnitCurrentPrice(productDetails.getProductPerUnitCurrentPrice());

					wishlistDetailsResponse.setWishlistId(wishlistDetailsEntity.getWishlistId());
					wishlistDetailsResponse.setProductDetailsResponse(productDetailsResponse);

					wishlistDetailsList.add(wishlistDetailsResponse);
					break;
				}
			}
		}

		WishlistManagementResponse wishManagementResponse = new WishlistManagementResponse();

		wishManagementResponse.setCustomerSequenceNumber(customerSequenceNumber);
		wishManagementResponse.setWishlistDetailsList(wishlistDetailsList);

		return wishManagementResponse;
	}

	@Transactional
	@Override
	public void removeProductFromWishlist(Long customerSequenceNumber, List<Long> productSequenceNumber,
			Integer expirationDuration) throws TokenExpiredException {

		tokenDao.checkTokenValidity(customerSequenceNumber, expirationDuration);

		wishlistDaoInterface.removeProductFromWishlist(customerSequenceNumber, productSequenceNumber);

	}

	@Transactional
	@Override
	public void deleteCustomerWishList(Long customerSequenceNumber, Integer expirationDuration)
			throws TokenExpiredException {

		tokenDao.checkTokenValidity(customerSequenceNumber, expirationDuration);

		wishlistDaoInterface.deleteCustomerWishList(customerSequenceNumber);

	}

}
