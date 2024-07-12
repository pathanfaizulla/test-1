package com.insignia.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.ProductFamilyDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.ProductFamily;
import com.insignia.model.ProductFamilyRequest;
import com.insignia.model.ProductFamilyResponse;

@ExtendWith(MockitoExtension.class)
public class TestProductFamilyServiceImpl {

	@InjectMocks
	private ProductFamilyServiceImpl productFamilyServiceImpl;

	@Mock
	private ProductFamilyDaoInterface productFamilyDaoInterface;

	@Mock
	private TokenDaoInterface tokenRepo;

	ProductFamilyRequest productFamilyRequest = new ProductFamilyRequest();
	ProductFamilyRequest productFamilyRequestForUpdate = new ProductFamilyRequest();
	ProductFamilyResponse productFamilyResponse = new ProductFamilyResponse();

	List<ProductFamilyResponse> productFamilyResponseList = new ArrayList<>();
	ProductFamily productFamily = new ProductFamily();

	List<ProductFamily> productFamilysList = new ArrayList<>();

	public void dataInitilization() {

		productFamilyRequest.setCustomerSequenceNumber(5L);
		productFamilyRequest.setExpirationDuration(15);
		productFamilyRequest.setFamilyName("abc");
		productFamilyRequest.setFamilyDescription("for bench");
		productFamilyRequest.setApplicationId("insignia");
		productFamilyRequest.setTenantId("LU008");

		productFamilyResponse.setSequenceNumber(5);
		productFamilyResponse.setFamilyName("abc");
		productFamilyResponse.setFamilyDescription("for bench");

		productFamilyResponseList.add(productFamilyResponse);

		productFamily.setFamilyName("abc");
		productFamily.setFamilyDescription("for bench");
		productFamily.setApplicationId("insignia");
		productFamily.setTenantId("LU008");

		productFamilysList.add(productFamily);
	}

	@Test
	public void testSaveProductFamily() throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productFamilyDaoInterface.findByFamilyName(productFamilyRequest.getFamilyName(),
				productFamilyRequest.getApplicationId(), productFamilyRequest.getTenantId())).thenReturn(null);

		when(productFamilyDaoInterface.saveProductFamily(any(ProductFamily.class))).thenReturn(productFamily);

		ProductFamilyResponse savedProductFamily = productFamilyServiceImpl.saveProductFamily(productFamilyRequest);

		assertNotNull(savedProductFamily);
	}

	@Test
	public void testSaveProductFamilyDuplicateDataException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();

		String familyName = "abc";
		when(productFamilyDaoInterface.findByFamilyName(productFamilyRequest.getFamilyName(),
				productFamilyRequest.getApplicationId(), productFamilyRequest.getTenantId())).thenReturn(familyName);

		assertThrows(InvalidInputParametersException.class, () -> {

			productFamilyServiceImpl.saveProductFamily(productFamilyRequest);
		});
		verify(productFamilyDaoInterface, times(1)).findByFamilyName(productFamilyRequest.getFamilyName(),
				productFamilyRequest.getApplicationId(), productFamilyRequest.getTenantId());
	}

	@Test
	public void testUpdateProductFamilyNameAndProductDescription()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		productFamilyRequestForUpdate.setCustomerSequenceNumber(105L);
		productFamilyRequestForUpdate.setSequenceNumber(20);
		productFamilyRequestForUpdate.setFamilyName("321");
		productFamilyRequestForUpdate.setFamilyDescription("ttttt");
		productFamilyRequestForUpdate.setApplicationId("abc");
		productFamilyRequestForUpdate.setTenantId("xyz");
		productFamilyRequestForUpdate.setProductFamilyImagePath("aaaaa");
		productFamilyRequestForUpdate.setDefaultImage("aaaaa");

		Integer sequenceNumber = 20;
		String applicationId = "abc";
		String tenantId = "xyz";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productFamilyDaoInterface.findBySequenceNumber(sequenceNumber, applicationId, tenantId))
				.thenReturn(Optional.of(productFamily));
		
		when(productFamilyDaoInterface.updateProductFamily(productFamily)).thenReturn(productFamily);

		ProductFamilyResponse productFamily = productFamilyServiceImpl.updateProductFamily(productFamilyRequestForUpdate);
		assertNotNull(productFamily);
	}

	@Test
	public void testUpdateProductDescription()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		productFamilyRequestForUpdate.setCustomerSequenceNumber(105L);
		productFamilyRequestForUpdate.setSequenceNumber(20);
		productFamilyRequestForUpdate.setFamilyName("321");
		productFamilyRequestForUpdate.setFamilyDescription("ttttt");
		productFamilyRequestForUpdate.setApplicationId("abc");
		productFamilyRequestForUpdate.setTenantId("xyz");
		productFamilyRequestForUpdate.setProductFamilyImagePath("aaaaa");
		productFamilyRequestForUpdate.setDefaultImage("aaaaa");

		Integer sequenceNumber = 20;
		String applicationId = "abc";
		String tenantId = "xyz";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productFamilyDaoInterface.findBySequenceNumber(sequenceNumber, applicationId, tenantId))
				.thenReturn(Optional.of(productFamily));
		
		when(productFamilyDaoInterface.updateProductFamily(productFamily)).thenReturn(productFamily);

		ProductFamilyResponse productFamily = productFamilyServiceImpl.updateProductFamily(productFamilyRequestForUpdate);
		assertNotNull(productFamily);
	}

	@Test
	public void testUpdateProductFamilyName()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		productFamilyRequestForUpdate.setCustomerSequenceNumber(105L);
		productFamilyRequestForUpdate.setSequenceNumber(20);
		productFamilyRequestForUpdate.setFamilyName("321");
		productFamilyRequestForUpdate.setFamilyDescription("ttttt");
		productFamilyRequestForUpdate.setApplicationId("abc");
		productFamilyRequestForUpdate.setTenantId("xyz");
		productFamilyRequestForUpdate.setProductFamilyImagePath("aaaaa");
		productFamilyRequestForUpdate.setDefaultImage("aaaaa");

		Integer sequenceNumber = 20;
		String applicationId = "abc";
		String tenantId = "xyz";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productFamilyDaoInterface.findBySequenceNumber(sequenceNumber, applicationId, tenantId))
				.thenReturn(Optional.of(productFamily));
		
		when(productFamilyDaoInterface.updateProductFamily(productFamily)).thenReturn(productFamily);

		ProductFamilyResponse productFamily = productFamilyServiceImpl.updateProductFamily(productFamilyRequestForUpdate);
		assertNotNull(productFamily);
	}

	@Test
	public void testUpdateProductFamilyDetailsNoExistException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();

		when(productFamilyDaoInterface.findBySequenceNumber(productFamilyRequest.getSequenceNumber(),
				productFamilyRequest.getApplicationId(), productFamilyRequest.getTenantId()))
				.thenReturn(Optional.empty());

		assertThrows(InvalidInputParametersException.class, () -> {

			productFamilyServiceImpl.updateProductFamily(productFamilyRequest);
		});
		verify(productFamilyDaoInterface, times(1)).findBySequenceNumber(productFamilyRequest.getSequenceNumber(),
				productFamilyRequest.getApplicationId(), productFamilyRequest.getTenantId());
	}

	@Test
	public void testDeleteProductFamily()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();
		String familyName = "abc";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productFamilyDaoInterface.findByFamilyName(productFamilyRequest.getFamilyName(),
				productFamilyRequest.getApplicationId(), productFamilyRequest.getTenantId())).thenReturn(familyName);

		doNothing().when(productFamilyDaoInterface).deleteProductFamily(productFamilyRequest.getFamilyName(),
				productFamilyRequest.getApplicationId(), productFamilyRequest.getTenantId());

		productFamilyServiceImpl.deleteProductFamily(productFamilyRequest);

		verify(productFamilyDaoInterface, times(1)).deleteProductFamily(productFamilyRequest.getFamilyName(),
				productFamilyRequest.getApplicationId(), productFamilyRequest.getTenantId());
	}

	@Test
	public void testDeleteProductFamilyDetailsNotExistedException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();

		when(productFamilyDaoInterface.findByFamilyName(productFamilyRequest.getFamilyName(),
				productFamilyRequest.getApplicationId(), productFamilyRequest.getTenantId())).thenReturn(null);

		assertThrows(InvalidInputParametersException.class, () -> {

			productFamilyServiceImpl.deleteProductFamily(productFamilyRequest);
		});
		verify(productFamilyDaoInterface, times(1)).findByFamilyName(productFamilyRequest.getFamilyName(),
				productFamilyRequest.getApplicationId(), productFamilyRequest.getTenantId());
	}

	@Test
	public void testGetAllProductFamily()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productFamilyDaoInterface.getAllProductFamily(productFamilyRequest.getApplicationId(),
				productFamilyRequest.getTenantId())).thenReturn(productFamilysList);

		List<ProductFamilyResponse> allProductFamily = productFamilyServiceImpl
				.getAllProductFamily(productFamilyRequest);

		assertNotNull(allProductFamily);
	}

}
