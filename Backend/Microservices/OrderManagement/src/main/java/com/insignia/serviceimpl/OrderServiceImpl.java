package com.insignia.serviceimpl;

import java.math.BigDecimal;
import java.util.*;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insignia.constants.OrderValidatorConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.OrderDaoInterface;
import com.insignia.daoInterface.ProductDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.OrderAndProductLink;
import com.insignia.entity.OrderDetails;
import com.insignia.entity.OrderStatus;
import com.insignia.entity.ProductDetails;
import com.insignia.model.OrderAndProductLinkRequest;
import com.insignia.model.OrderAndProductLinkResponse;
import com.insignia.model.OrderRequest;
import com.insignia.model.OrderResponse;
import com.insignia.model.ProductDetailsResponse;
import com.insignia.service.OrderServiceInterface;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderServiceInterface {

	@Autowired
	private OrderDaoInterface orderDaoInterface;

	@Autowired
	private TokenDaoInterface tokenDao;

	@Autowired
	private ProductDaoInterface orderDaoImpl;

	@Transactional
	@Override
	public OrderResponse createOrder(OrderRequest orderRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(orderRequest.getCustomerSequenceNumber(), orderRequest.getExpirationDuration());
		validateOrderDetails(orderRequest);
		OrderDetails orderEntity = new OrderDetails();

		orderEntity.setOrderId(orderRequest.getOrderId());
		orderEntity.setOrderDate(orderRequest.getOrderDate());
		orderEntity.setOrderStatus(orderRequest.getOrderStatus());
		orderEntity.setInvoiceId(orderRequest.getInvoiceId());
		orderEntity.setCustomerSequenceNumber(orderRequest.getCustomerSequenceNumber());
		orderEntity.setAddressSequenceNumber(orderRequest.getAddressSequenceNumber());
		orderEntity.setTotalPrice(orderRequest.getTotalPrice());

		List<OrderAndProductLinkRequest> orderAndProductLinkRequestList = orderRequest
				.getOrderAndProductLinkRequestList();
		if (orderAndProductLinkRequestList != null) {

			List<OrderAndProductLink> orderAndProductLinkList = new ArrayList<>();
			for (OrderAndProductLinkRequest orderAndProductLinkRequest : orderAndProductLinkRequestList) {
				OrderAndProductLink orderAndProductLink = new OrderAndProductLink();
				orderAndProductLink.setProductQuantity(orderAndProductLinkRequest.getProductQuantity());
				orderAndProductLink.setTotalPrice(orderAndProductLinkRequest.getTotalPrice());
				orderAndProductLink.setPerUnitPrice(orderAndProductLinkRequest.getPerUnitPrice());
				orderAndProductLink.setOrderDetails(orderEntity);

				ProductDetails productDetails = new ProductDetails();
				productDetails.setProductSequenceNumber(orderAndProductLinkRequest.getProductSequenceNumber());
				orderAndProductLink.setProductDetails(productDetails);
				orderAndProductLinkList.add(orderAndProductLink);

			}
			orderEntity.setOrderAndProductLinkList(orderAndProductLinkList);
		}
		OrderDetails orderDetailsEntity = orderDaoInterface.CreateOrder(orderEntity);
		return createResponseForOrderDetailsEntity(orderDetailsEntity);
	}

	private void validateOrderDetails(OrderRequest orderRequest) throws InvalidInputParametersException {

		BigDecimal totalPrice = new BigDecimal("0");

		Map<Long, Integer> productDetailRequest = orderRequest.getOrderAndProductLinkRequestList()
				.stream().collect(Collectors.toMap(OrderAndProductLinkRequest::getProductSequenceNumber,
						OrderAndProductLinkRequest::getProductQuantity));

		List<ProductDetails> productDetailsList = orderDaoImpl.getSelectedProductDetailsListForCartAndOrder(new ArrayList<>(productDetailRequest.keySet()));
		for (ProductDetails productDetails : productDetailsList) {
			totalPrice = totalPrice.add(productDetails.getProductPerUnitCurrentPrice().multiply(new BigDecimal(productDetailRequest.get(productDetails.getProductSequenceNumber()))));
		}
		if (totalPrice.compareTo(orderRequest.getTotalPrice()) != 0) {
			log.error(OrderValidatorConstant.validateOrderPriceMessage + "systemPrice:" + totalPrice, "OrderRequestTotalPrice :" + orderRequest.getTotalPrice());
			throw new InvalidInputParametersException(OrderValidatorConstant.validateOrderPriceErrorCode,
					OrderValidatorConstant.validateOrderPriceMessage);
		}
	}

	@Transactional
	@Override
	public OrderResponse updateOrder(OrderRequest orderRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(orderRequest.getCustomerSequenceNumber(), orderRequest.getExpirationDuration());

		Optional<OrderDetails> orderDetailsList = orderDaoInterface.findById(orderRequest.getOrderSequenceNumber());

		if (orderDetailsList.isPresent()) {

			OrderDetails orderEntity = orderDetailsList.get();

			orderEntity.setOrderStatus(orderRequest.getOrderStatus());

			OrderDetails updateOrder = orderDaoInterface.updateOrder(orderEntity);

			OrderResponse responce = createResponce(orderRequest, updateOrder);

			return responce;
		} else {
			throw new InvalidInputParametersException(OrderValidatorConstant.orderDetailsIsnotFoundMessage,
					OrderValidatorConstant.orderDetailsIsnotFoundMessageErrorCode);
		}

	}

	@Transactional
	@Override
	public void deleteOrder(Long orderSequenceNumber, Long customerSequenceNumber, Integer expirationDuration)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(customerSequenceNumber, expirationDuration);

		Optional<OrderDetails> orderDetailsList = orderDaoInterface.findById(orderSequenceNumber);

		if (!orderDetailsList.isPresent()) {
			throw new InvalidInputParametersException(OrderValidatorConstant.orderDetailsIsnotFoundMessageErrorCode,
					OrderValidatorConstant.orderDetailsIsnotFoundMessage);
		} else {
			OrderDetails orderDetails = orderDetailsList.get();
			if (OrderStatus.CA.name().equals(orderDetails.getOrderStatus())) {
				orderDaoInterface.deleteOrder(orderSequenceNumber);
			} else {
				throw new InvalidInputParametersException(OrderValidatorConstant.validateOrderStatusDeleteErrorCode,
						OrderValidatorConstant.validateOrderStatusDeleteMessage);
			}
		}
	}

	@Transactional
	@Override
	public List<OrderResponse> getOrders(Long customerSequenceNumber, Integer expirationDuration, boolean isToGetAll)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(customerSequenceNumber, expirationDuration);
		List<OrderResponse> orderResponceList = new ArrayList<>();

		List<OrderDetails> orderDetailsList = null;

		if (isToGetAll) {
			orderDetailsList = orderDaoInterface.getAllOrders();
		} else {
			orderDetailsList = orderDaoInterface.getALLorderById(customerSequenceNumber);
		}

		for (OrderDetails orderDetails : orderDetailsList) {

			OrderResponse orderResponce = createResponseForOrderDetailsEntity(orderDetails);

			orderResponceList.add(orderResponce);
		}
		return orderResponceList;
	}

//----------------------------------------------------------------------------------------------------
	@Transactional
	@Override
	public Optional<OrderResponse> getOrderById(Long orderSequenceNumber, Long customerSequenceNumber,
			Integer expirationDuration) throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(customerSequenceNumber, expirationDuration);
		Optional<OrderDetails> orderDetailsList = orderDaoInterface.findById(orderSequenceNumber);
		if (!orderDetailsList.isEmpty()) {
			OrderDetails orderDetails = orderDetailsList.get();

			OrderResponse createResponse = createResponseForOrderDetailsEntity(orderDetails);

			return Optional.of(createResponse);
		}
		return Optional.empty();

	}

	private OrderResponse createResponseForOrderDetailsEntity(OrderDetails orderDetails) {
		OrderResponse orderResponse = new OrderResponse();
		orderResponse.setOrderSequenceNumber(orderDetails.getOrderSequenceNumber());
		orderResponse.setOrderId(orderDetails.getOrderId());
		orderResponse.setOrderDate(orderDetails.getOrderDate());
		orderResponse.setOrderStatus(orderDetails.getOrderStatus());
		orderResponse.setInvoiceId(orderDetails.getInvoiceId());
		orderResponse.setTotalPrice(orderDetails.getTotalPrice());
		orderResponse.setCustomerSequenceNumber(orderDetails.getCustomerSequenceNumber());
		orderResponse.setAddressSequenceNumber(orderDetails.getAddressSequenceNumber());

		List<OrderAndProductLinkResponse> orderAndProductLinkResponses = new ArrayList<>();

		List<OrderAndProductLink> orderAndProductLinkList = orderDetails.getOrderAndProductLinkList();
		if (orderAndProductLinkList != null) {
			for (OrderAndProductLink orderAndProductLink : orderAndProductLinkList) {
				OrderAndProductLinkResponse orderAndProductLinkResponse = new OrderAndProductLinkResponse();
				orderAndProductLinkResponse.setProductQuantity(orderAndProductLink.getProductQuantity());
				orderAndProductLinkResponse.setPerUnitPrice(orderAndProductLink.getPerUnitPrice());
				orderAndProductLinkResponse.setTotalPrice(orderAndProductLink.getTotalPrice());

				ProductDetails productDetails = orderAndProductLink.getProductDetails();
				ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();

				productDetailsResponse.setProductSequenceNumber(productDetails.getProductSequenceNumber());
				productDetailsResponse.setProductId(productDetails.getProductId());
				productDetailsResponse.setProductName(productDetails.getProductName());
				productDetailsResponse.setDescription(productDetails.getDescription());
				productDetailsResponse.setMeasuringQuantity(productDetails.getMeasuringQuantity());
				productDetailsResponse.setMeasuringUnit(productDetails.getMeasuringUnit());
				productDetailsResponse.setSubcategoryId(productDetails.getSubcategoryId());
				productDetailsResponse.setProductImagePath(productDetails.getProductImagePath());
				productDetailsResponse.setProductPerUnitActualPrice(productDetails.getProductPerUnitActualPrice());
				productDetailsResponse.setProductPerUnitCurrentPrice(productDetails.getProductPerUnitCurrentPrice());
				productDetailsResponse.setProductLength(productDetails.getLength());
				productDetailsResponse.setWidth(productDetails.getWidth());
				productDetailsResponse.setHeight(productDetails.getHeight());
				productDetailsResponse.setDimensionUnit(productDetails.getDimensionUnit());
				productDetailsResponse.setMaterials(productDetails.getMaterials());
				productDetailsResponse.setColours(productDetails.getColours());
				productDetailsResponse.setDefaultImage(productDetails.getDefaultImage());
				orderAndProductLinkResponse.setProductDetailsResponse(productDetailsResponse);
				orderAndProductLinkResponses.add(orderAndProductLinkResponse);
			}
		}

		orderResponse.setOrderAndProductLinkResponseList(orderAndProductLinkResponses);

		return orderResponse;
	}

	private OrderResponse createResponce(OrderRequest orderRequest, OrderDetails orderDetails) {
		OrderResponse responce = new OrderResponse();
		responce.setOrderId(orderRequest.getOrderId());
		responce.setOrderDate(orderRequest.getOrderDate());
		responce.setOrderStatus(orderRequest.getOrderStatus());
		responce.setCustomerSequenceNumber(orderRequest.getCustomerSequenceNumber());
		responce.setAddressSequenceNumber(orderRequest.getAddressSequenceNumber());
		responce.setInvoiceId(orderRequest.getInvoiceId());
		responce.setTotalPrice(orderRequest.getTotalPrice());

		return responce;
	}

}
