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
import com.insignia.daoInterface.ProductMaterialDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.ProductMaterial;
import com.insignia.model.ProductMaterialRequest;
import com.insignia.model.ProductMaterialResponse;

@ExtendWith(MockitoExtension.class)
public class TestProductMaterialServiceImpl {

	@InjectMocks
	private ProductMaterialServiceImpl productMaterialServiceImpl;

	@Mock
	private ProductMaterialDaoInterface productMaterialDaoInterface;

	@Mock
	private TokenDaoInterface tokenRepo;

	ProductMaterialRequest productMaterialRequest = new ProductMaterialRequest();
	ProductMaterialResponse productMaterialResponse = new ProductMaterialResponse();
	ProductMaterialRequest productMaterialRequestForUpdate = new ProductMaterialRequest();

	List<ProductMaterialResponse> productMaterialResponseList = new ArrayList<>();
	ProductMaterial productMaterial = new ProductMaterial();

	List<ProductMaterial> productMaterialsList = new ArrayList<>();

	public void dataInitilization() {

		productMaterialRequest.setCustomerSequenceNumber(5L);
		productMaterialRequest.setExpirationDuration(15);
		productMaterialRequest.setMaterialName("pink");
		productMaterialRequest.setMaterialDescription("for bench");
		productMaterialRequest.setApplicationId("insignia");
		productMaterialRequest.setTenantId("LU008");

		productMaterialResponse.setSequenceNumber(5);
		productMaterialResponse.setMaterialName("pink");
		productMaterialResponse.setMaterialDescription("for bench");

		productMaterialResponseList.add(productMaterialResponse);

		productMaterial.setMaterialName("pink");
		productMaterial.setMaterialDescription("for bench");
		productMaterial.setApplicationId("insignia");
		productMaterial.setTenantId("LU008");

		productMaterialsList.add(productMaterial);
	}

	@Test
	public void testSaveProductMaterial()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productMaterialDaoInterface.findByMaterialName(productMaterialRequest.getMaterialName(),
				productMaterialRequest.getApplicationId(), productMaterialRequest.getTenantId())).thenReturn(null);

		when(productMaterialDaoInterface.saveProductMaterial(productMaterial)).thenReturn(productMaterial);

		ProductMaterialResponse saveProductMaterial = productMaterialServiceImpl
				.saveProductMaterial(productMaterialRequest);

		assertNotNull(saveProductMaterial);
	}

	@Test
	public void testSaveProductMaterialDuplicateDataException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();

		String MaterialName = "pink";
		when(productMaterialDaoInterface.findByMaterialName(productMaterialRequest.getMaterialName(),
				productMaterialRequest.getApplicationId(), productMaterialRequest.getTenantId()))
				.thenReturn(MaterialName);

		assertThrows(InvalidInputParametersException.class, () -> {

			productMaterialServiceImpl.saveProductMaterial(productMaterialRequest);
		});
		verify(productMaterialDaoInterface, times(1)).findByMaterialName(productMaterialRequest.getMaterialName(),
				productMaterialRequest.getApplicationId(), productMaterialRequest.getTenantId());
	}

	@Test
	public void testUpdateProductMaterialName()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		productMaterialRequestForUpdate.setCustomerSequenceNumber(105L);
		productMaterialRequestForUpdate.setSequenceNumber(17);
		productMaterialRequestForUpdate.setMaterialName("materialForAll5");
		productMaterialRequestForUpdate.setMaterialDescription("tttt");
		productMaterialRequestForUpdate.setApplicationId("hinges-design");
		productMaterialRequestForUpdate.setTenantId("LU008");

		Integer sequenceNumber = 17;
		String applicationId = "hinges-design";
		String tenantId = "LU008";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productMaterialDaoInterface.findBySequenceNumber(sequenceNumber, applicationId, tenantId))
				.thenReturn(Optional.of(productMaterial));
		
		when(productMaterialDaoInterface.updateProductMaterial(productMaterial)).thenReturn(productMaterial);

		ProductMaterialResponse productMaterial = productMaterialServiceImpl
				.updateProductMaterial(productMaterialRequestForUpdate);

		assertNotNull(productMaterial);
	}

	@Test
	public void testUpdateProductMaterialDescription()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		productMaterialRequestForUpdate.setCustomerSequenceNumber(105L);
		productMaterialRequestForUpdate.setSequenceNumber(17);
		productMaterialRequestForUpdate.setMaterialName("materialForAll5");
		productMaterialRequestForUpdate.setMaterialDescription("tttt");
		productMaterialRequestForUpdate.setApplicationId("hinges-design");
		productMaterialRequestForUpdate.setTenantId("LU008");

		Integer sequenceNumber = 17;
		String applicationId = "hinges-design";
		String tenantId = "LU008";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productMaterialDaoInterface.findBySequenceNumber(sequenceNumber, applicationId, tenantId))
				.thenReturn(Optional.of(productMaterial));
		
		when(productMaterialDaoInterface.updateProductMaterial(productMaterial)).thenReturn(productMaterial);

		ProductMaterialResponse productMaterial = productMaterialServiceImpl
				.updateProductMaterial(productMaterialRequestForUpdate);

		assertNotNull(productMaterial);
	}

	@Test
	public void testUpdateProductMaterialNameAndMaterialDescription()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		productMaterialRequestForUpdate.setCustomerSequenceNumber(105L);
		productMaterialRequestForUpdate.setSequenceNumber(17);
		productMaterialRequestForUpdate.setMaterialName("materialForAll5");
		productMaterialRequestForUpdate.setMaterialDescription("tttt");
		productMaterialRequestForUpdate.setApplicationId("hinges-design");
		productMaterialRequestForUpdate.setTenantId("LU008");

		Integer sequenceNumber = 17;
		String applicationId = "hinges-design";
		String tenantId = "LU008";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productMaterialDaoInterface.findBySequenceNumber(sequenceNumber, applicationId, tenantId))
				.thenReturn(Optional.of(productMaterial));
		
		when(productMaterialDaoInterface.updateProductMaterial(productMaterial)).thenReturn(productMaterial);

		ProductMaterialResponse productMaterial = productMaterialServiceImpl
				.updateProductMaterial(productMaterialRequestForUpdate);

		assertNotNull(productMaterial);
	}

	@Test
	public void testUpdateProductMaterialDetailsNoExistException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();
		productMaterialRequest.setSequenceNumber(5);
		when(productMaterialDaoInterface.findBySequenceNumber(productMaterialRequest.getSequenceNumber(),
				productMaterialRequest.getApplicationId(), productMaterialRequest.getTenantId()))
				.thenReturn(Optional.empty());

		assertThrows(InvalidInputParametersException.class, () -> {

			productMaterialServiceImpl.updateProductMaterial(productMaterialRequest);
		});
		verify(productMaterialDaoInterface, times(1)).findBySequenceNumber(productMaterialRequest.getSequenceNumber(),
				productMaterialRequest.getApplicationId(), productMaterialRequest.getTenantId());
	}

	@Test
	public void testDeleteProductMaterial()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();
		String MaterialName = "pink";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productMaterialDaoInterface.findByMaterialName(productMaterialRequest.getMaterialName(),
				productMaterialRequest.getApplicationId(), productMaterialRequest.getTenantId()))
				.thenReturn(MaterialName);

		doNothing().when(productMaterialDaoInterface).deleteProductMaterial(productMaterialRequest.getMaterialName(),
				productMaterialRequest.getApplicationId(), productMaterialRequest.getTenantId());

		productMaterialServiceImpl.deleteProductMaterial(productMaterialRequest);

		verify(productMaterialDaoInterface, times(1)).deleteProductMaterial(productMaterialRequest.getMaterialName(),
				productMaterialRequest.getApplicationId(), productMaterialRequest.getTenantId());
	}

	@Test
	public void testDeleteProductMaterialDetailsNotExistedException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();

		when(productMaterialDaoInterface.findByMaterialName(productMaterialRequest.getMaterialName(),
				productMaterialRequest.getApplicationId(), productMaterialRequest.getTenantId())).thenReturn(null);

		assertThrows(InvalidInputParametersException.class, () -> {

			productMaterialServiceImpl.deleteProductMaterial(productMaterialRequest);
		});
		verify(productMaterialDaoInterface, times(1)).findByMaterialName(productMaterialRequest.getMaterialName(),
				productMaterialRequest.getApplicationId(), productMaterialRequest.getTenantId());
	}

	@Test
	public void testGetAllProductMaterial()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productMaterialDaoInterface.getAllProductMaterials(productMaterialRequest.getApplicationId(),
				productMaterialRequest.getTenantId())).thenReturn(productMaterialsList);

		List<ProductMaterialResponse> allProductMaterial = productMaterialServiceImpl
				.getAllProductMaterials(productMaterialRequest);

		assertNotNull(allProductMaterial);
	}

}
