package com.insignia.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.CartDaoInterface;
import com.insignia.daoInterface.ProductDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.CartInformation;
import com.insignia.entity.CustomerCartInformation;
import com.insignia.entity.OrderDetails;
import com.insignia.entity.ProductDetails;
import com.insignia.model.CartProductDetails;
import com.insignia.model.CartRequest;
import com.insignia.model.CartResponce;
import com.insignia.model.CustomerCartRequest;
import com.insignia.model.CustomerCartResponse;
import com.insignia.model.ProductDetailsResponse;
import com.insignia.serviceimpl.CartServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TestCartServiceImpl {

	@InjectMocks
	private CartServiceImpl cartServiceImpl;

	@Mock
	private CartDaoInterface cartDaoRepo;

	@Mock
	private ProductDaoInterface productDaoRepo;

	@Mock
	private TokenDaoInterface tokenRepo;

	CartRequest cartRequest = new CartRequest();
	CartResponce cartResponse = new CartResponce();
	CustomerCartRequest customerCartRequest = new CustomerCartRequest();

	List<CustomerCartResponse> customerCartResponseList = new ArrayList<>();
	CustomerCartResponse customerCartResponse = new CustomerCartResponse();

	List<CartProductDetails> cartProductDetailsList = new ArrayList<>();
	CartProductDetails cartProductDetails = new CartProductDetails();

	List<CartInformation> cartInformationList = new ArrayList<>();
	CartInformation cartInformation = new CartInformation();

	List<CustomerCartInformation> customerCartInformationList = new ArrayList<>();
	CustomerCartInformation customerCartInformation = new CustomerCartInformation();

	OrderDetails orderDetails = new OrderDetails();

	ProductDetails productDetails = new ProductDetails();
	List<ProductDetails> productDetailsList = new ArrayList<>();

	ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse(); 
	public void dataInitilization() {
		cartRequest.setCartId(5);
		cartRequest.setCartSequenceNumber(5L);

		cartRequest.setCustomerSequenceNumber(5L);
		cartRequest.setExpirationDuration(15);

		cartResponse.setCustomerSequenceNumber(5L);
		cartResponse.setCartSequenceNumber(5L);

		customerCartRequest.setCustomerSequenceNumber(5L);
		customerCartRequest.setCustomerCartSequenceNumber(5L);
		customerCartRequest.setExpirationDuration(15);
		customerCartRequest.setProductSequenceNumber(5L);
		customerCartRequest.setCartSequenceNumber(5L);
		customerCartRequest.setProductQuantity("5");

		List<Long> productSequenceNumber = Arrays.asList(5L);
		customerCartResponse.setCartSequenceNumber(5L);
		customerCartResponse.setProductSequenceNumber(productSequenceNumber);


		productDetailsResponse.setProductSequenceNumber(5L);
		productDetailsResponse.setProductId("5L");
		productDetailsResponse.setProductName("Mobile");
		productDetailsResponse.setDescription("I Phone");
		productDetailsResponse.setProductImagePath("Mobile");
		productDetailsResponse.setProductPerUnitActualPrice(BigDecimal.valueOf(40.25));
		productDetailsResponse.setProductPerUnitCurrentPrice(BigDecimal.valueOf(40.25));
		productDetailsResponse.setWidth(BigDecimal.valueOf(40.25));
		productDetailsResponse.setHeight(BigDecimal.valueOf(40.25));
		productDetailsResponse.setProductLength(BigDecimal.valueOf(40.25));
		productDetailsResponse.setDimensionUnit("inches");

		customerCartResponse.setCartProductDetailsList(cartProductDetailsList);

		customerCartResponseList.add(customerCartResponse);

		customerCartInformation.setProductSequenceNumber(5L);
		customerCartInformation.setCartSequenceNumber(5L);
		customerCartInformation.setProductQuantity("5");

		customerCartInformationList.add(customerCartInformation);

		cartInformation.setCustomerSequenceNumber(5L);

		cartInformationList.add(cartInformation);

		productDetails.setProductSequenceNumber(5L);
		productDetails.setProductId("5L");
		productDetails.setProductName("Mobile");
		productDetails.setDescription("Small Mobile");
		productDetails.setMeasuringQuantity("2");
		productDetails.setMeasuringUnit("5");
		productDetails.setSubcategoryId(5L);
		productDetails.setProductImagePath("Mobile");
		productDetails.setProductPerUnitActualPrice(BigDecimal.valueOf(40.25));
		productDetails.setProductPerUnitCurrentPrice(BigDecimal.valueOf(40.25));
		productDetails.setLength(BigDecimal.valueOf(40.25));
		productDetails.setWidth(BigDecimal.valueOf(40.25));
		productDetails.setHeight(BigDecimal.valueOf(40.25));
		productDetails.setDimensionUnit("inches");

		productDetailsList.add(productDetails);
	}

	public ProductDetails getDetailsEntity() {

		productDetails.setProductSequenceNumber(5L);
		productDetails.setProductId("5L");
		productDetails.setProductName("Mobile");
		productDetails.setDescription("Small Mobile");
		productDetails.setMeasuringQuantity("2");
		productDetails.setMeasuringUnit("5");
		productDetails.setSubcategoryId(5L);
		productDetails.setProductImagePath("Mobile");

		return productDetails;
	}

	public CustomerCartInformation getCustomerCartInformationEntity() {

		customerCartInformation.setProductSequenceNumber(5L);
		customerCartInformation.setCartSequenceNumber(5L);
		customerCartInformation.setProductQuantity("5");

		return customerCartInformation;
	}

	@Test
	public void testSaveCart() throws TokenExpiredException, InvalidInputParametersException {

		dataInitilization();

		when(cartDaoRepo.saveCart(cartInformation)).thenReturn(cartInformation);

		CartResponce saveCart = cartServiceImpl.saveCart(cartRequest);

		assertNotNull(saveCart);
	}

	@Test
	public void testDeleteCartForCustomer() throws TokenExpiredException, InvalidInputParametersException {

		Long cartSequenceNumber = 5L;
		Long customerSequenceNumber = 5L;
		Integer expirationDuration = 15;

		cartServiceImpl.deleteCartForCustomer(cartSequenceNumber, customerSequenceNumber, expirationDuration);

		verify(cartDaoRepo, times(1)).deleteCartForCustomer(cartSequenceNumber);

	}

	@Test
	public void testGetCustomerCartInformation() throws TokenExpiredException, InvalidInputParametersException {

		dataInitilization();
		Long customerSequenceNumber = 5L;
		Integer expirationDuration = 15;
		cartInformation.setCustomerCartInformationsList(customerCartInformationList);

		when(productDaoRepo.getSelectedProductDetailsListForCartAndOrder(any())).thenReturn(productDetailsList);

		Mockito.when(cartDaoRepo.getCustomerCartInformation(customerSequenceNumber)).thenReturn(cartInformationList);

		CustomerCartResponse customerCartInformation = cartServiceImpl
				.getCustomerCartInformation(customerSequenceNumber, expirationDuration);

		assertNotNull(customerCartInformation);
	}

	// ------------------------------------------

	@Test
	public void testAddProductToCart() throws TokenExpiredException, InvalidInputParametersException {

		dataInitilization();
		getDetailsEntity();

		Optional<ProductDetails> optionalProductDetails = Optional.ofNullable(productDetails);

		when(productDaoRepo.findByIdForCart(any())).thenReturn(optionalProductDetails);

		when(cartDaoRepo.addProductToCart(customerCartInformation)).thenReturn(customerCartInformation);

		CustomerCartResponse addProductToCart = cartServiceImpl.addProductToCart(customerCartRequest);

		assertNotNull(addProductToCart);
	}

	@Test
	public void testAddProductToCartProduct_IsNotAvailableException()
			throws TokenExpiredException, InvalidInputParametersException {

		dataInitilization();
		getDetailsEntity();

		when(productDaoRepo.findByIdForCart(any())).thenReturn(Optional.empty());

		assertThrows(InvalidInputParametersException.class, () -> {
			cartServiceImpl.addProductToCart(customerCartRequest);

		});
		verify(productDaoRepo, times(1)).findByIdForCart(any());

	}

	@Test
	public void testUpdateProductToCart_InvalidInputParametersException()
			throws TokenExpiredException, InvalidInputParametersException {

		dataInitilization();
		getCustomerCartInformationEntity();
		getDetailsEntity();

		Optional<CustomerCartInformation> optionalCustomerCartInformation = Optional
				.ofNullable(customerCartInformation);

		when(cartDaoRepo.findById(any())).thenReturn(optionalCustomerCartInformation);

		Optional<ProductDetails> optionalProductDetails = Optional.ofNullable(productDetails);

		when(productDaoRepo.findByIdForCart(any())).thenReturn(optionalProductDetails);

		when(cartDaoRepo.updateCustomerCartProduct(customerCartInformation)).thenReturn(customerCartInformation);

		CustomerCartResponse updateCustomerCartProduct = cartServiceImpl.updateCustomerCartProduct(customerCartRequest);

		assertNotNull(updateCustomerCartProduct);
	}

	@Test
	public void updateCustomerCartProduct_IsNotFoundException()
			throws TokenExpiredException, InvalidInputParametersException {

		dataInitilization();
		getCustomerCartInformationEntity();
		getDetailsEntity();

		Optional<CustomerCartInformation> optionalCustomerCartInformation = Optional
				.ofNullable(customerCartInformation);

		when(cartDaoRepo.findById(any())).thenReturn(optionalCustomerCartInformation);

		when(productDaoRepo.findByIdForCart(any())).thenReturn(Optional.empty());

		assertThrows(InvalidInputParametersException.class, () -> {

			cartServiceImpl.updateCustomerCartProduct(customerCartRequest);
		});
		verify(productDaoRepo, times(1)).findByIdForCart(any());

	}

	@Test
	public void updateCustomerCartCustomerCartInformation_NotFoundException()
			throws TokenExpiredException, InvalidInputParametersException {

		dataInitilization();

		when(cartDaoRepo.findById(any())).thenReturn(Optional.empty());

		assertThrows(InvalidInputParametersException.class, () -> {

			cartServiceImpl.updateCustomerCartProduct(customerCartRequest);
		});
		verify(cartDaoRepo, times(1)).findById(any());

	}

	@Test
	public void testDeleteProductToCart() throws TokenExpiredException, InvalidInputParametersException {

		Long customerCartSequenceNumber = 5L;
		Long customerSequenceNumber = 5L;
		Integer expirationDuration = 15;

		cartServiceImpl.deleteProductToCart(customerCartSequenceNumber, customerSequenceNumber, expirationDuration);

		verify(cartDaoRepo, times(1)).deleteProductToCart(customerCartSequenceNumber);

	}

	@Test
	public void testGetProductByCustomer() throws TokenExpiredException, InvalidInputParametersException {

		dataInitilization();
		Long cartSequenceNumber = 5L;
		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 5;

		List<Long> productSequenceNumber = Arrays.asList(5L);
		customerCartResponse.setProductSequenceNumber(productSequenceNumber);

		when(cartDaoRepo.findByCartSequenceNumber(cartSequenceNumber)).thenReturn(customerCartInformationList);
		when(cartDaoRepo.findSelectedProductList(productSequenceNumber)).thenReturn(productDetailsList);

		List<CustomerCartResponse> productByCustomer = cartServiceImpl.getProductByCustomer(cartSequenceNumber,
				customerSequenceNumber, expirationDuration);

		assertNotNull(productByCustomer);
	}

}
