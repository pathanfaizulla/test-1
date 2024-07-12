package com.insignia.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.CustomListDetailsDaoInterface;
import com.insignia.daoInterface.CustomerCustomlistProductsLinkDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.CustomListDetailsEntity;
import com.insignia.entity.CustomerCustomlistProductsLinkEntity;
import com.insignia.model.CustomListDetails;
import com.insignia.model.CustomListDetailsRequest;
import com.insignia.model.CustomListManagementRequest;
import com.insignia.model.CustomListManagementResponse;
import com.insignia.model.ProductDetailsRequest;
import com.insignia.model.ProductDetailsResponse;

@ExtendWith(MockitoExtension.class)
public class TestCustomListServiceImpl {
	
	@InjectMocks
	private CustomListServiceImpl customListServiceImpl;

	@Mock
	private CustomListDetailsDaoInterface customListDetailsDaoInterface;
	
	@Mock
	private CustomerCustomlistProductsLinkDaoInterface customerCustomlistProductsLinkDaoInterface;

	@Mock
	private TokenDaoInterface tokenDaoInterface;
	
	CustomListManagementRequest customListManagementRequest = new CustomListManagementRequest();
	CustomListDetailsRequest customListDetailsRequest = new CustomListDetailsRequest();
	ProductDetailsRequest productDetailsRequest = new ProductDetailsRequest();

	CustomListDetails customListDetails = new CustomListDetails();
	CustomListManagementResponse customListManagementResponse = new CustomListManagementResponse();
	ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();

	List<CustomListDetails> customListDetailsResponseList = new ArrayList<>();
	List<ProductDetailsRequest> productDetailsRequestList = new ArrayList<>();
	List<CustomListDetailsRequest> customListDetailsRequestList = new ArrayList<>();

	List<ProductDetailsResponse> productDetailsResponseList = new ArrayList<>();
	
	List<CustomListDetailsEntity> customListDetailsEntityList = new ArrayList<>();
	CustomListDetailsEntity customListDetailsEntity = new CustomListDetailsEntity();
	
	List<CustomerCustomlistProductsLinkEntity> customerCustomlistProductsLinkEntityList = new ArrayList<>();
	CustomerCustomlistProductsLinkEntity CustomerCustomlistProductsLinkEntity = new CustomerCustomlistProductsLinkEntity();
	
	CustomListDetails customListDetailsResponse = new CustomListDetails();
	
	public void dataInitilization() {

		customListManagementRequest.setCustomerSequenceNumber(105L);
		customListManagementRequest.setTokenExpirationDuration(15);

		customListDetailsRequest.setSequenceNumber(1L);
		customListDetailsRequest.setCustomListName("List1");

		productDetailsRequest.setProductSequenceNumber(1L);
		productDetailsRequest.setQuantity(60);

		productDetailsRequestList.add(productDetailsRequest);

		customListDetailsRequest.setProductDetailsRequestList(productDetailsRequestList);

		customListDetailsRequestList.add(customListDetailsRequest);

		customListManagementRequest.setCustomListDetailsRequestList(customListDetailsRequestList);

		customListDetails.setCustomListSequenceNumber(105L);
		customListDetails.setCustomListName("List1");

		productDetailsResponse.setProductSequenceNumber(7L);
		productDetailsResponse.setProductAvailableQuantity(60);
		productDetailsResponse.setCustomListQuantity(5);
		productDetailsResponse.setProductId("660817");
		productDetailsResponse.setProductName("mobile");
		productDetailsResponse.setDescription("HI");
		productDetailsResponse.setMeasuringQuantity("3");
		productDetailsResponse.setMeasuringUnit("5");
		productDetailsResponse.setProductImagePath(
				"https://test-bucket-for-document.s3.ap-south-1.amazonaws.com/hinges-design/LU008/PRODUCT/660817/IMAGES/");
		productDetailsResponse.setProductPerUnitActualPrice(BigDecimal.valueOf(635.00));
		productDetailsResponse.setProductPerUnitCurrentPrice(BigDecimal.valueOf(590.00));
		productDetailsResponse.setDefaultImage("660817-PMI.jpg");

		productDetailsResponseList.add(productDetailsResponse);

		customListDetails.setProductDetailsResponse(productDetailsResponseList);

		customListDetailsResponseList.add(customListDetails);

		customListManagementResponse.setCustomerSequenceNumber(105L);
		customListManagementResponse.setCustomListDetails(customListDetailsResponseList);
	}
	
	@Test
	public void testCreateCustomList() throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();

		doNothing().when(tokenDaoInterface).checkTokenValidity(any(), any());

		ArgumentMatcher<List<CustomListDetailsEntity>> customListDetailsEntityMatcher = list -> true;
		when(customListDetailsDaoInterface.saveAll(argThat(customListDetailsEntityMatcher)))
				.thenReturn(customListDetailsEntityList);

		CustomListManagementResponse customListManagementResponse = customListServiceImpl
				.createCustomList(customListManagementRequest);

		assertNotNull(customListManagementResponse);
	}
	
	@Test
	public void testSaveCustomList() throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();		

		doNothing().when(tokenDaoInterface).checkTokenValidity(any(), any());

		ArgumentMatcher<List<CustomerCustomlistProductsLinkEntity>> customListDetailsEntityMatcher = list -> true;
		when(customerCustomlistProductsLinkDaoInterface.saveCustomList(argThat(customListDetailsEntityMatcher)))
				.thenReturn(customerCustomlistProductsLinkEntityList);

		CustomListManagementResponse saveCustomListForCustomer = customListServiceImpl
				.saveCustomList(customListManagementRequest);

		assertNotNull(saveCustomListForCustomer);
	}
	
	@Test
	public void testUpdateCustomListName() throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();
		
		Set<Long> sequenceNumberList = new HashSet<>();
		sequenceNumberList.add(1L);

		doNothing().when(tokenDaoInterface).checkTokenValidity(any(), any());
		
		when(customListDetailsDaoInterface.findBySequenceNumber(sequenceNumberList))
		.thenReturn(customListDetailsEntityList);

		ArgumentMatcher<List<CustomListDetailsEntity>> customListDetailsEntityMatcher = list -> true;
		when(customListDetailsDaoInterface.saveAll(argThat(customListDetailsEntityMatcher)))
				.thenReturn(customListDetailsEntityList);

		CustomListManagementResponse customListManagementResponse = customListServiceImpl
				.updateCustomListName(customListManagementRequest);

		assertNotNull(customListManagementResponse);
	}

	@Test
	public void TestGetCustomListForCustomer() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 5;

		Mockito.when(customListDetailsDaoInterface.getCustomListForCustomer(customerSequenceNumber)).thenReturn(customListDetailsEntityList);

		CustomListManagementResponse customerCustomlist = customListServiceImpl.getCustomListForCustomer(customerSequenceNumber,
				expirationDuration);
		assertNotNull(customerCustomlist);
	}

	@Test
	public void testDeleteCustomList() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		Long customer_sequence_number = 8L;
		Integer expirationDuration = 5;
		
		List<String> customListNameList = Arrays.asList("List1");

		customListServiceImpl.deleteCustomList(customer_sequence_number, customListNameList, expirationDuration);

		verify(customListDetailsDaoInterface, times(1)).deleteCustomList(customer_sequence_number, customListNameList);

	}

	@Test
	public void testDeleteAllCustomListForCustomer() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		Long customer_sequence_number = 8L;
		Integer expirationDuration = 5;

		customListServiceImpl.deleteAllCustomListForCustomer(customer_sequence_number, expirationDuration);

		verify(customListDetailsDaoInterface, times(1)).deleteAllCustomListForCustomer(customer_sequence_number);

	}

}
