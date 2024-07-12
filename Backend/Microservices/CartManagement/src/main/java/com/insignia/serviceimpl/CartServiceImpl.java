package com.insignia.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insignia.constants.CartValidaterConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.CartDaoInterface;
import com.insignia.daoInterface.ProductDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.CartInformation;
import com.insignia.entity.CustomerCartInformation;
import com.insignia.entity.ProductDetails;
import com.insignia.model.CartProductDetails;
import com.insignia.model.CartRequest;
import com.insignia.model.CartResponce;
import com.insignia.model.CustomerCartRequest;
import com.insignia.model.CustomerCartResponse;
import com.insignia.model.ProductDetailsResponse;
import com.insignia.service.CartServiceInterface;

@Service
public class CartServiceImpl implements CartServiceInterface {

	@Autowired
	public CartDaoInterface cartDaoInterface;

	@Autowired
	private TokenDaoInterface tokenDao;

	@Autowired
	private ProductDaoInterface productDaoInterface;

	@Transactional
	@Override
	public CartResponce saveCart(CartRequest cartRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(cartRequest.getCustomerSequenceNumber(), cartRequest.getExpirationDuration());

		CartInformation cartInformationEntity = new CartInformation();
		cartInformationEntity.setCustomerSequenceNumber(cartRequest.getCustomerSequenceNumber());

		CartInformation updateInformation = cartDaoInterface.saveCart(cartInformationEntity);

		CartResponce Responce = createResponce(cartRequest, updateInformation);

		return Responce;
	}

	@Transactional
	@Override
	public void deleteCartForCustomer(Long cartSequenceNumber, Long customerSequenceNumber, Integer expirationDuration)
			throws TokenExpiredException {
		tokenDao.checkTokenValidity(customerSequenceNumber, expirationDuration);

		cartDaoInterface.deleteCartForCustomer(cartSequenceNumber);

	}

	@Transactional
	@Override
	public CustomerCartResponse getCustomerCartInformation(Long customerSequenceNumber, Integer expirationDuration)
			throws TokenExpiredException {

		tokenDao.checkTokenValidity(customerSequenceNumber, expirationDuration);
		CustomerCartResponse response = null;

		List<CartInformation> cartInformationList = cartDaoInterface.getCustomerCartInformation(customerSequenceNumber);
		if (cartInformationList.size() > 0) {

			response = new CustomerCartResponse();

			List<CartProductDetails> cartProductDetailsList = new ArrayList<>();
			List<Long> productSequenceNumberList = new ArrayList<>();

			for (CartInformation cartInformation : cartInformationList) {
				response.setCartSequenceNumber(cartInformation.getCartSequenceNumber());
				response.setCartProductDetailsList(cartProductDetailsList);
				List<CustomerCartInformation> customerCartInformationList = cartInformation
						.getCustomerCartInformationsList();

				for (CustomerCartInformation customerCartInformation : customerCartInformationList) {

					productSequenceNumberList.add(customerCartInformation.getProductSequenceNumber());
				}

				List<ProductDetails> productDetailsList = productDaoInterface
						.getSelectedProductDetailsListForCartAndOrder(productSequenceNumberList);

				for (CustomerCartInformation customerCartInformation : customerCartInformationList) {

					for (ProductDetails productDetails : productDetailsList) {
						if (productDetails.getProductSequenceNumber()
								.equals(customerCartInformation.getProductSequenceNumber())) {

							cartProductDetailsList.add(createResponseForcartProductDetailsResponse(
									customerCartInformation, productDetails));

							break;

						}

					}
				}

			}

		}

		return response;
	}

//---------------------------------------------------customerCartInformation-------------------------------------	
	@Transactional
	@Override
	public CustomerCartResponse addProductToCart(CustomerCartRequest customerCartRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(customerCartRequest.getCustomerSequenceNumber(),
				customerCartRequest.getExpirationDuration());

		Optional<ProductDetails> productDetailsList = productDaoInterface
				.findByIdForCart(customerCartRequest.getProductSequenceNumber());
		if (!productDetailsList.isPresent()) {
			throw new InvalidInputParametersException(
					CartValidaterConstant.productSequeceNumberNotFoundMessageErrorCode,
					CartValidaterConstant.productSequeceNumberNotFoundMessage);
		}

		ProductDetails productDetails = productDetailsList.get();

		CustomerCartInformation customerCartInformation = new CustomerCartInformation();
		customerCartInformation.setCartSequenceNumber(customerCartRequest.getCartSequenceNumber());

		customerCartInformation.setProductSequenceNumber(customerCartRequest.getProductSequenceNumber());
		customerCartInformation.setProductQuantity(customerCartRequest.getProductQuantity());

		CustomerCartInformation updateCustomerCartInformation = cartDaoInterface
				.addProductToCart(customerCartInformation);

		CustomerCartResponse responce = new CustomerCartResponse();
		responce.setCartSequenceNumber(customerCartInformation.getCartSequenceNumber());
		List<CartProductDetails> cartProductDetailsList = new ArrayList<>();

		cartProductDetailsList
				.add(createResponseForcartProductDetailsResponse(updateCustomerCartInformation, productDetails));
		responce.setCartProductDetailsList(cartProductDetailsList);

		return responce;

	}

	@Transactional
	@Override
	public CustomerCartResponse updateCustomerCartProduct(CustomerCartRequest customerCartRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(customerCartRequest.getCustomerSequenceNumber(),
				customerCartRequest.getExpirationDuration());

		Optional<CustomerCartInformation> customerCartInformationList = cartDaoInterface
				.findById(customerCartRequest.getCustomerCartSequenceNumber());

		if (customerCartInformationList.isPresent()) {
			CustomerCartInformation customerCartInformation = customerCartInformationList.get();

			Optional<ProductDetails> optIsPresent = productDaoInterface
					.findByIdForCart(customerCartRequest.getProductSequenceNumber());
			if (!optIsPresent.isPresent()) {
				throw new InvalidInputParametersException(
						CartValidaterConstant.productSequeceNumberNotFoundMessageErrorCode,
						CartValidaterConstant.productSequeceNumberNotFoundMessage);
			}

			ProductDetails productDetails = optIsPresent.get();

			customerCartInformation.setProductSequenceNumber(customerCartRequest.getProductSequenceNumber());
			customerCartInformation.setCartSequenceNumber(customerCartRequest.getCartSequenceNumber());
			customerCartInformation.setProductQuantity(customerCartRequest.getProductQuantity());

			CustomerCartInformation updateCustomerCartInformation = cartDaoInterface
					.updateCustomerCartProduct(customerCartInformation);

			CustomerCartResponse responce = new CustomerCartResponse();
			responce.setCartSequenceNumber(customerCartInformation.getCartSequenceNumber());
			List<CartProductDetails> cartProductDetailsList = new ArrayList<>();
			cartProductDetailsList
					.add(createResponseForcartProductDetailsResponse(updateCustomerCartInformation, productDetails));
			responce.setCartProductDetailsList(cartProductDetailsList);

			return responce;
		} else {
			throw new InvalidInputParametersException(
					CartValidaterConstant.customerCartInformationNotFoundMessageErrorCode,
					CartValidaterConstant.customerCartInformationNotFoundMessage);

		}

	}

	@Transactional
	@Override
	public void deleteProductToCart(Long customerCartSequenceNumber, Long customerSequenceNumber,
			Integer expirationDuration) throws TokenExpiredException {

		tokenDao.checkTokenValidity(customerSequenceNumber, expirationDuration);

		cartDaoInterface.deleteProductToCart(customerCartSequenceNumber);

	}

	@Transactional
	@Override
	public List<CustomerCartResponse> getProductByCustomer(Long cartSequenceNumber, Long customerSequenceNumber,
			Integer expirationDuration) throws TokenExpiredException {
		tokenDao.checkTokenValidity(customerSequenceNumber, expirationDuration);

		List<CustomerCartResponse> customerCartResponseList = new ArrayList<>();

		List<CustomerCartInformation> customerCartInformationList = cartDaoInterface
				.findByCartSequenceNumber(cartSequenceNumber);

		if (!customerCartInformationList.isEmpty()) {
			CustomerCartResponse customerCartResponse = new CustomerCartResponse();
			customerCartResponse.setCartSequenceNumber(cartSequenceNumber);

			List<CartProductDetails> cartProductDetailsList = new ArrayList<>();

			for (CustomerCartInformation customerCartInformation : customerCartInformationList) {
				List<Long> productSequenceNumberList = new ArrayList<>();
				productSequenceNumberList.add(customerCartInformation.getProductSequenceNumber());

				List<ProductDetails> productDetailsList = cartDaoInterface
						.findSelectedProductList(productSequenceNumberList);

				if (!productDetailsList.isEmpty()) {
					ProductDetails productDetails = productDetailsList.get(0);

					CartProductDetails cartProductDetailsResponse = createResponseForcartProductDetailsResponse(
							customerCartInformation, productDetails);

					cartProductDetailsList.add(cartProductDetailsResponse);
				}
			}

			customerCartResponse.setCartProductDetailsList(cartProductDetailsList);
			customerCartResponseList.add(customerCartResponse);
		}

		return customerCartResponseList;
	}

	private CartProductDetails createResponseForcartProductDetailsResponse(
			CustomerCartInformation customerCartInformation, ProductDetails productDetails) {
		CartProductDetails cartProductDetailsResponse = new CartProductDetails();
		cartProductDetailsResponse
				.setCustomerCartSequenceNumber(customerCartInformation.getCustomerCartSequenceNumber());
		cartProductDetailsResponse.setProductQuantity(customerCartInformation.getProductQuantity());

		ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();
		productDetailsResponse.setProductSequenceNumber(customerCartInformation.getProductSequenceNumber());
		productDetailsResponse.setProductId(productDetails.getProductId());
		productDetailsResponse.setProductName(productDetails.getProductName());
		productDetailsResponse.setDescription(productDetails.getDescription());
		productDetailsResponse.setProductImagePath(productDetails.getProductImagePath());
		productDetailsResponse.setDefaultImage(productDetails.getDefaultImage());
		productDetailsResponse.setProductPerUnitActualPrice(productDetails.getProductPerUnitActualPrice());
		productDetailsResponse.setProductPerUnitCurrentPrice(productDetails.getProductPerUnitCurrentPrice());
		productDetailsResponse.setProductLength(productDetails.getLength());
		productDetailsResponse.setWidth(productDetails.getWidth());
		productDetailsResponse.setHeight(productDetails.getHeight());
		productDetailsResponse.setDimensionUnit(productDetails.getDimensionUnit());
		productDetailsResponse.setMaterials(productDetails.getMaterials());
		productDetailsResponse.setColours(productDetails.getColours());
		cartProductDetailsResponse.setProductDetailsResponse(productDetailsResponse);

		return cartProductDetailsResponse;
	}

	private CartResponce createResponce(CartRequest cartRequest, CartInformation updateInformation) {
		CartResponce responce = new CartResponce();
		responce.setCustomerSequenceNumber(updateInformation.getCustomerSequenceNumber());
		responce.setCartSequenceNumber(updateInformation.getCartSequenceNumber());
		return responce;
	}
}
