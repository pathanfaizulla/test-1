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
import com.insignia.daoInterface.ProductBrandDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.ProductBrand;
import com.insignia.model.ProductBrandRequest;
import com.insignia.model.ProductBrandResponse;

@ExtendWith(MockitoExtension.class)
public class TestProductBrandServiceImpl {

	@InjectMocks
	private ProductBrandServiceImpl productBrandServiceImpl;

	@Mock
	private ProductBrandDaoInterface productBrandDaoInterface;

	@Mock
	private TokenDaoInterface tokenRepo;

	@Mock
	private Object productBrandName = new Object();

	ProductBrandRequest productBrandRequest = new ProductBrandRequest();
	ProductBrandRequest productBrandRequestForUpdate = new ProductBrandRequest();
	ProductBrandResponse productBrandResponse = new ProductBrandResponse();

	List<ProductBrandResponse> productBrandResponseList = new ArrayList<>();
	ProductBrand productBrand = new ProductBrand();

	List<ProductBrand> productBrandsList = new ArrayList<>();

	public void dataInitilization() {

		productBrandRequest.setCustomerSequenceNumber(5L);
		productBrandRequest.setExpirationDuration(15);
		productBrandRequest.setBrandName("abc");
		productBrandRequest.setBrandDescription("for bench");
		productBrandRequest.setApplicationId("abc");
		productBrandRequest.setTenantId("xyz");

		productBrandResponse.setSequenceNumber(5);
		productBrandResponse.setBrandName("abc");
		productBrandResponse.setBrandDescription("for bench");

		productBrandResponseList.add(productBrandResponse);

		productBrand.setBrandName("abc");
		productBrand.setBrandDescription("for bench");

		productBrand.setApplicationId("insignia");
		productBrand.setTenantId("LU008");

		productBrandsList.add(productBrand);
	}

	@Test
	public void testSaveProductBrand() throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productBrandDaoInterface.findByBrandName(productBrandRequest.getBrandName(),
				productBrandRequest.getApplicationId(), productBrandRequest.getTenantId()))
				.thenReturn(productBrandName);

		when(productBrandDaoInterface.saveProductBrand(any(ProductBrand.class))).thenReturn(productBrand);

		ProductBrandResponse savedProductBrand = productBrandServiceImpl.saveProductBrand(productBrandRequest);

		assertNotNull(savedProductBrand);
	}

	@Test
	public void testSaveProductBrandDuplicateDataException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();
		String brandName = "abc";

		when(productBrandDaoInterface.findByBrandName(productBrandRequest.getBrandName(),
				productBrandRequest.getApplicationId(), productBrandRequest.getTenantId())).thenReturn(brandName);

		assertThrows(InvalidInputParametersException.class, () -> {

			productBrandServiceImpl.saveProductBrand(productBrandRequest);
		});
		verify(productBrandDaoInterface, times(1)).findByBrandName(productBrandRequest.getBrandName(),
				productBrandRequest.getApplicationId(), productBrandRequest.getTenantId());
	}

	@Test
	public void testUpdateProdutBrandNameAndProductBrandDescription()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		productBrandRequestForUpdate.setCustomerSequenceNumber(105L);
		productBrandRequestForUpdate.setSequenceNumber(15);
		productBrandRequestForUpdate.setBrandName("xyz");
		productBrandRequestForUpdate.setApplicationId("abc");
		productBrandRequestForUpdate.setTenantId("xyz");
		productBrandRequestForUpdate.setBrandDescription("aaa");
		productBrandRequestForUpdate.setProductBrandImagePath("aaaaa");
		productBrandRequestForUpdate.setDefaultImage("aaaaa");

		Integer sequenceNumber = 15;
		String applicationId = "abc";
		String tenantId = "xyz";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productBrandDaoInterface.findBySequenceNumber(sequenceNumber, applicationId, tenantId))
				.thenReturn(Optional.of(productBrand));
		
		when(productBrandDaoInterface.updateProductBrand(productBrand)).thenReturn(productBrand);

		ProductBrandResponse productBrand = productBrandServiceImpl.updateProductBrand(productBrandRequestForUpdate);

		assertNotNull(productBrand);
	}

	@Test
	public void testUpdateProductBrandDescription()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		productBrandRequestForUpdate.setCustomerSequenceNumber(105L);
		productBrandRequestForUpdate.setSequenceNumber(15);
		productBrandRequestForUpdate.setBrandName("xyz");
		productBrandRequestForUpdate.setApplicationId("abc");
		productBrandRequestForUpdate.setTenantId("xyz");
		productBrandRequestForUpdate.setBrandDescription("aaa");
		productBrandRequestForUpdate.setProductBrandImagePath("aaaaa");
		productBrandRequestForUpdate.setDefaultImage("aaaaa");

		Integer sequenceNumber = 15;
		String applicationId = "abc";
		String tenantId = "xyz";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productBrandDaoInterface.findBySequenceNumber(sequenceNumber, applicationId, tenantId))
				.thenReturn(Optional.of(productBrand));
		
		when(productBrandDaoInterface.updateProductBrand(productBrand)).thenReturn(productBrand);

		ProductBrandResponse productBrand = productBrandServiceImpl.updateProductBrand(productBrandRequestForUpdate);

		assertNotNull(productBrand);
	}

	@Test
	public void testUpdateProdutBrandName()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		productBrandRequestForUpdate.setCustomerSequenceNumber(105L);
		productBrandRequestForUpdate.setSequenceNumber(15);
		productBrandRequestForUpdate.setBrandName("xyz");
		productBrandRequestForUpdate.setApplicationId("abc");
		productBrandRequestForUpdate.setTenantId("xyz");
		productBrandRequestForUpdate.setBrandDescription("aaa");
		productBrandRequestForUpdate.setProductBrandImagePath("aaaaa");
		productBrandRequestForUpdate.setDefaultImage("aaaaa");

		Integer sequenceNumber = 15;
		String applicationId = "abc";
		String tenantId = "xyz";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productBrandDaoInterface.findBySequenceNumber(sequenceNumber, applicationId, tenantId))
				.thenReturn(Optional.of(productBrand));
		
		when(productBrandDaoInterface.updateProductBrand(productBrand)).thenReturn(productBrand);

		ProductBrandResponse productBrand = productBrandServiceImpl.updateProductBrand(productBrandRequestForUpdate);

		assertNotNull(productBrand);
	}

	@Test
	public void testUpdateProductBrandDetailsNoExistException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();
		productBrandRequest.setSequenceNumber(5);
		when(productBrandDaoInterface.findBySequenceNumber(productBrandRequest.getSequenceNumber(),
				productBrandRequest.getApplicationId(), productBrandRequest.getTenantId()))
				.thenReturn(Optional.empty());

		assertThrows(InvalidInputParametersException.class, () -> {

			productBrandServiceImpl.updateProductBrand(productBrandRequest);
		});
		verify(productBrandDaoInterface, times(1)).findBySequenceNumber(productBrandRequest.getSequenceNumber(),
				productBrandRequest.getApplicationId(), productBrandRequest.getTenantId());
	}

	@Test
	public void testDeleteProductBrandDetails()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();
		String brandName = "abc";
		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productBrandDaoInterface.findByBrandName(productBrandRequest.getBrandName(),
				productBrandRequest.getApplicationId(), productBrandRequest.getTenantId())).thenReturn(brandName);

		doNothing().when(productBrandDaoInterface).deleteProductBrand(productBrandRequest.getBrandName(),
				productBrandRequest.getApplicationId(), productBrandRequest.getTenantId());

		productBrandServiceImpl.deleteProductBrand(productBrandRequest);

		verify(productBrandDaoInterface, times(1)).deleteProductBrand(productBrandRequest.getBrandName(),
				productBrandRequest.getApplicationId(), productBrandRequest.getTenantId());
	}

	@Test
	public void testDeleteMeasurementUnitsDetailsNotExistedException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();

		when(productBrandDaoInterface.findByBrandName(productBrandRequest.getBrandName(),
				productBrandRequest.getApplicationId(), productBrandRequest.getTenantId())).thenReturn(null);

		assertThrows(InvalidInputParametersException.class, () -> {

			productBrandServiceImpl.deleteProductBrand(productBrandRequest);
		});
		verify(productBrandDaoInterface, times(1)).findByBrandName(productBrandRequest.getBrandName(),
				productBrandRequest.getApplicationId(), productBrandRequest.getTenantId());
	}

	@Test
	public void testGetAllProductBrand() throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productBrandDaoInterface.getAllProductBrand(productBrandRequest.getApplicationId(),
				productBrandRequest.getTenantId())).thenReturn(productBrandsList);

		List<ProductBrandResponse> allProductBrand = productBrandServiceImpl.getAllProductBrand(productBrandRequest);

		assertNotNull(allProductBrand);
	}

}
