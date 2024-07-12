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
import com.insignia.daoInterface.ProductCatalogueDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.ProductCatalogue;
import com.insignia.model.ProductCatalogueRequest;
import com.insignia.model.ProductCatalogueResponse;

@ExtendWith(MockitoExtension.class)
public class TestProductCatalogueServiceImpl {

	@InjectMocks
	private ProductCatalogueServiceImpl productCatalogueServiceImpl;

	@Mock
	private ProductCatalogueDaoInterface productCatalogueDaoInterface;

	@Mock
	private TokenDaoInterface tokenRepo;

	ProductCatalogueRequest productCatalogueRequest = new ProductCatalogueRequest();
	ProductCatalogueRequest productCatalogueRequestForUpdate = new ProductCatalogueRequest();
	ProductCatalogueResponse productCatalogueResponse = new ProductCatalogueResponse();

	List<ProductCatalogueResponse> productCatalogueResponseList = new ArrayList<>();
	ProductCatalogue productCatalogue = new ProductCatalogue();

	List<ProductCatalogue> productCataloguesList = new ArrayList<>();

	public void dataInitilization() {

		productCatalogueRequest.setCustomerSequenceNumber(105L);
		productCatalogueRequest.setExpirationDuration(15);
		productCatalogueRequest.setCatalogueName("abc");
		productCatalogueRequest.setCatalogueDescription("for bench");
		productCatalogueRequest.setApplicationId("insignia");
		productCatalogueRequest.setTenantId("LU008");

		productCatalogueResponse.setSequenceNumber(5);
		productCatalogueResponse.setCatalogueName("abc");
		productCatalogueResponse.setCatalogueDescription("for bench");

		productCatalogueResponseList.add(productCatalogueResponse);

		productCatalogue.setCatalogueName("abc");
		productCatalogue.setCatalogueDescription("for bench");
		productCatalogue.setApplicationId("insignia");
		productCatalogue.setTenantId("LU008");

		productCataloguesList.add(productCatalogue);
	}

	@Test
	public void testSaveProductCatalogue()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productCatalogueDaoInterface.findByCatalogueName(productCatalogueRequest.getCatalogueName(),
				productCatalogueRequest.getApplicationId(), productCatalogueRequest.getTenantId())).thenReturn(null);

		when(productCatalogueDaoInterface.saveProductCatalogue(any(ProductCatalogue.class)))
				.thenReturn(productCatalogue);

		ProductCatalogueResponse savedProductCatalogue = productCatalogueServiceImpl
				.saveProductCatalogue(productCatalogueRequest);

		assertNotNull(savedProductCatalogue);
	}

	@Test
	public void testSaveProductCatalogueDuplicateDataException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();

		String catalogueName = "abc";
		when(productCatalogueDaoInterface.findByCatalogueName(productCatalogueRequest.getCatalogueName(),
				productCatalogueRequest.getApplicationId(), productCatalogueRequest.getTenantId()))
				.thenReturn(catalogueName);

		assertThrows(InvalidInputParametersException.class, () -> {

			productCatalogueServiceImpl.saveProductCatalogue(productCatalogueRequest);
		});
		verify(productCatalogueDaoInterface, times(1)).findByCatalogueName(productCatalogueRequest.getCatalogueName(),
				productCatalogueRequest.getApplicationId(), productCatalogueRequest.getTenantId());
	}

	@Test
	public void testUpdateProdutCatalogueNameAndProductCatalogueDescription()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		productCatalogueRequestForUpdate.setCustomerSequenceNumber(105L);
		productCatalogueRequestForUpdate.setSequenceNumber(17);
		productCatalogueRequestForUpdate.setCatalogueName("9999");
		productCatalogueRequestForUpdate.setCatalogueDescription("ttttt");
		productCatalogueRequestForUpdate.setApplicationId("abc");
		productCatalogueRequestForUpdate.setTenantId("xyz");
		productCatalogueRequestForUpdate.setProductCatalogueImagePath("aaaaa");
		productCatalogueRequestForUpdate.setDefaultImage("aaaaa");

		Integer sequenceNumber = 17;
		String applicationId = "abc";
		String tenantId = "xyz";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productCatalogueDaoInterface.findBySequenceNumber(sequenceNumber, applicationId, tenantId))
				.thenReturn(Optional.of(productCatalogue));

		when(productCatalogueDaoInterface.updateProductCatalogue(productCatalogue)).thenReturn(productCatalogue);

		ProductCatalogueResponse productCatalogue = productCatalogueServiceImpl
				.updateProductCatalogue(productCatalogueRequestForUpdate);

		assertNotNull(productCatalogue);
	}

	@Test
	public void testUpdateProductCatalogueDescription()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		productCatalogueRequestForUpdate.setCustomerSequenceNumber(105L);
		productCatalogueRequestForUpdate.setSequenceNumber(17);
		productCatalogueRequestForUpdate.setCatalogueName("9999");
		productCatalogueRequestForUpdate.setCatalogueDescription("ttttt");
		productCatalogueRequestForUpdate.setApplicationId("abc");
		productCatalogueRequestForUpdate.setTenantId("xyz");
		productCatalogueRequestForUpdate.setProductCatalogueImagePath("aaaaa");
		productCatalogueRequestForUpdate.setDefaultImage("aaaaa");

		Integer sequenceNumber = 17;
		String applicationId = "abc";
		String tenantId = "xyz";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productCatalogueDaoInterface.findBySequenceNumber(sequenceNumber, applicationId, tenantId))
				.thenReturn(Optional.of(productCatalogue));

		when(productCatalogueDaoInterface.updateProductCatalogue(productCatalogue)).thenReturn(productCatalogue);

		ProductCatalogueResponse productCatalogue = productCatalogueServiceImpl
				.updateProductCatalogue(productCatalogueRequestForUpdate);

		assertNotNull(productCatalogue);
	}

	@Test
	public void testUpdateProdutCatalogueName()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		productCatalogueRequestForUpdate.setCustomerSequenceNumber(105L);
		productCatalogueRequestForUpdate.setSequenceNumber(17);
		productCatalogueRequestForUpdate.setCatalogueName("9999");
		productCatalogueRequestForUpdate.setCatalogueDescription("ttttt");
		productCatalogueRequestForUpdate.setApplicationId("abc");
		productCatalogueRequestForUpdate.setTenantId("xyz");
		productCatalogueRequestForUpdate.setProductCatalogueImagePath("aaaaa");
		productCatalogueRequestForUpdate.setDefaultImage("aaaaa");

		Integer sequenceNumber = 17;
		String applicationId = "abc";
		String tenantId = "xyz";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productCatalogueDaoInterface.findBySequenceNumber(sequenceNumber, applicationId, tenantId))
				.thenReturn(Optional.of(productCatalogue));

		when(productCatalogueDaoInterface.updateProductCatalogue(productCatalogue)).thenReturn(productCatalogue);

		ProductCatalogueResponse productCatalogue = productCatalogueServiceImpl
				.updateProductCatalogue(productCatalogueRequestForUpdate);

		assertNotNull(productCatalogue);
	}

	@Test
	public void testUpdateProductCatalogueNoExistException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();
		productCatalogueRequest.setSequenceNumber(5);
		when(productCatalogueDaoInterface.findBySequenceNumber(productCatalogueRequest.getSequenceNumber(),
				productCatalogueRequest.getApplicationId(), productCatalogueRequest.getTenantId()))
				.thenReturn(Optional.empty());

		assertThrows(InvalidInputParametersException.class, () -> {

			productCatalogueServiceImpl.updateProductCatalogue(productCatalogueRequest);
		});
		verify(productCatalogueDaoInterface, times(1)).findBySequenceNumber(productCatalogueRequest.getSequenceNumber(),
				productCatalogueRequest.getApplicationId(), productCatalogueRequest.getTenantId());
	}

	@Test
	public void testDeleteProductCatalogue()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();
		String catalogueName = "abc";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productCatalogueDaoInterface.findByCatalogueName(productCatalogueRequest.getCatalogueName(),
				productCatalogueRequest.getApplicationId(), productCatalogueRequest.getTenantId()))
				.thenReturn(catalogueName);

		doNothing().when(productCatalogueDaoInterface).deleteProductCatalogue(
				productCatalogueRequest.getCatalogueName(), productCatalogueRequest.getApplicationId(),
				productCatalogueRequest.getTenantId());

		productCatalogueServiceImpl.deleteProductCatalogue(productCatalogueRequest);

		verify(productCatalogueDaoInterface, times(1)).deleteProductCatalogue(
				productCatalogueRequest.getCatalogueName(), productCatalogueRequest.getApplicationId(),
				productCatalogueRequest.getTenantId());
	}

	@Test
	public void testDeleteMeasurementUnitsDetailsNotExistedException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();

		when(productCatalogueDaoInterface.findByCatalogueName(productCatalogueRequest.getCatalogueName(),
				productCatalogueRequest.getApplicationId(), productCatalogueRequest.getTenantId())).thenReturn(null);

		assertThrows(InvalidInputParametersException.class, () -> {

			productCatalogueServiceImpl.deleteProductCatalogue(productCatalogueRequest);
		});
		verify(productCatalogueDaoInterface, times(1)).findByCatalogueName(productCatalogueRequest.getCatalogueName(),
				productCatalogueRequest.getApplicationId(), productCatalogueRequest.getTenantId());
	}

	@Test
	public void testGetAllProductCatalogue()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productCatalogueDaoInterface.getAllProductCatalogue(productCatalogueRequest.getApplicationId(),
				productCatalogueRequest.getTenantId())).thenReturn(productCataloguesList);

		List<ProductCatalogueResponse> allProductCatalogue = productCatalogueServiceImpl
				.getAllProductCatalogue(productCatalogueRequest);

		assertNotNull(allProductCatalogue);
	}

}
