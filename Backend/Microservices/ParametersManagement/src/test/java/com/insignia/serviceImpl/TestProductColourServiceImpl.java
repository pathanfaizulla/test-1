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
import com.insignia.daoInterface.ProductColourDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.ProductColour;
import com.insignia.model.ProductColourRequest;
import com.insignia.model.ProductColourResponse;

@ExtendWith(MockitoExtension.class)
public class TestProductColourServiceImpl {

	@InjectMocks
	private ProductColourServiceImpl productColourServiceImpl;

	@Mock
	private ProductColourDaoInterface productColourDaoInterface;

	@Mock
	private TokenDaoInterface tokenRepo;

	ProductColourRequest productColourRequest = new ProductColourRequest();
	ProductColourRequest productColourRequestForUpdate = new ProductColourRequest();

	ProductColourResponse productColourResponse = new ProductColourResponse();

	List<ProductColourResponse> productColourResponseList = new ArrayList<>();
	ProductColour productColour = new ProductColour();

	List<ProductColour> productColoursList = new ArrayList<>();

	public void dataInitilization() {

		productColourRequest.setCustomerSequenceNumber(5L);
		productColourRequest.setExpirationDuration(15);
		productColourRequest.setColourName("pink");
		productColourRequest.setColourDescription("for bench");
		productColourRequest.setApplicationId("insignia");
		productColourRequest.setTenantId("LU008");
		productColourRequest.setHexCode("#1AFFa1");

		productColourResponse.setSequenceNumber(5);
		productColourResponse.setColourName("pink");
		productColourResponse.setColourDescription("for bench");
		productColourResponse.setHexCode("#1AFFa1");

		productColourResponseList.add(productColourResponse);

		productColour.setColourName("pink");
		productColour.setColourDescription("for bench");
		productColour.setApplicationId("insignia");
		productColour.setTenantId("LU008");
		productColour.setHexCode("#1AFFa1");

		productColoursList.add(productColour);
	}

	@Test
	public void testSaveProductColour() throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productColourDaoInterface.findByColourName(productColourRequest.getColourName(),
				productColourRequest.getApplicationId(), productColourRequest.getTenantId())).thenReturn(null);

		when(productColourDaoInterface.saveProductColour(productColour)).thenReturn(productColour);

		ProductColourResponse saveProductColour = productColourServiceImpl.saveProductColour(productColourRequest);

		assertNotNull(saveProductColour);
	}

	@Test
	public void testSaveProductColourDuplicateDataException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();

		String colourName = "pink";
		when(productColourDaoInterface.findByColourName(productColourRequest.getColourName(),
				productColourRequest.getApplicationId(), productColourRequest.getTenantId())).thenReturn(colourName);

		assertThrows(InvalidInputParametersException.class, () -> {

			productColourServiceImpl.saveProductColour(productColourRequest);
		});
		verify(productColourDaoInterface, times(1)).findByColourName(productColourRequest.getColourName(),
				productColourRequest.getApplicationId(), productColourRequest.getTenantId());
	}

	@Test
	public void testUpdateProductColourName()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		productColourRequestForUpdate.setCustomerSequenceNumber(105L);
		productColourRequestForUpdate.setSequenceNumber(111);
		productColourRequestForUpdate.setColourName("LightSteelBlue");
		productColourRequestForUpdate.setApplicationId("Hinges-Design");
		productColourRequestForUpdate.setTenantId("t012");
		productColourRequestForUpdate.setColourDescription("aaa");
		productColourRequestForUpdate.setHexCode("#1AFFa1");

		Integer sequenceNumber = 111;
		String applicationId = "Hinges-Design";
		String tenantId = "t012";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productColourDaoInterface.findBySequenceNumber(sequenceNumber, applicationId, tenantId))
				.thenReturn(Optional.of(productColour));
		
		when(productColourDaoInterface.updateProductColour(productColour)).thenReturn(productColour);

		ProductColourResponse updateProductColour = productColourServiceImpl
				.updateProductColour(productColourRequestForUpdate);

		assertNotNull(updateProductColour);
	}

	@Test
	public void testUpdateProductColourDescription()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		productColourRequestForUpdate.setCustomerSequenceNumber(105L);
		productColourRequestForUpdate.setSequenceNumber(111);
		productColourRequestForUpdate.setColourName("LightSteelBlue");
		productColourRequestForUpdate.setApplicationId("Hinges-Design");
		productColourRequestForUpdate.setTenantId("t012");
		productColourRequestForUpdate.setColourDescription("aaa");
		productColourRequestForUpdate.setHexCode("#1AFFa1");

		Integer sequenceNumber = 111;
		String applicationId = "Hinges-Design";
		String tenantId = "t012";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productColourDaoInterface.findBySequenceNumber(sequenceNumber, applicationId, tenantId))
				.thenReturn(Optional.of(productColour));
		
		when(productColourDaoInterface.updateProductColour(productColour)).thenReturn(productColour);

		ProductColourResponse updateProductColour = productColourServiceImpl
				.updateProductColour(productColourRequestForUpdate);

		assertNotNull(updateProductColour);
	}

	@Test
	public void testUpdateProductColourNameAndColourDescription()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		productColourRequestForUpdate.setCustomerSequenceNumber(105L);
		productColourRequestForUpdate.setSequenceNumber(111);
		productColourRequestForUpdate.setColourName("LightSteelBlue");
		productColourRequestForUpdate.setApplicationId("Hinges-Design");
		productColourRequestForUpdate.setTenantId("t012");
		productColourRequestForUpdate.setColourDescription("aaa");
		productColourRequestForUpdate.setHexCode("#1AFFa1");

		Integer sequenceNumber = 111;
		String applicationId = "Hinges-Design";
		String tenantId = "t012";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productColourDaoInterface.findBySequenceNumber(sequenceNumber, applicationId, tenantId))
				.thenReturn(Optional.of(productColour));
		
		when(productColourDaoInterface.updateProductColour(productColour)).thenReturn(productColour);

		ProductColourResponse updateProductColour = productColourServiceImpl
				.updateProductColour(productColourRequestForUpdate);

		assertNotNull(updateProductColour);
	}

	@Test
	public void testUpdateMeasurementUnitsDetailsNoExistException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();

		when(productColourDaoInterface.findBySequenceNumber(productColourRequest.getSequenceNumber(), productColourRequest.getApplicationId(), productColourRequest.getTenantId()))
				.thenReturn(Optional.empty());

		assertThrows(InvalidInputParametersException.class, () -> {

			productColourServiceImpl.updateProductColour(productColourRequest);
		});
		verify(productColourDaoInterface, times(1)).findBySequenceNumber(productColourRequest.getSequenceNumber(), productColourRequest.getApplicationId(), productColourRequest.getTenantId());
	}

	@Test
	public void testDeleteProductColour()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();
		String colourName = "pink";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productColourDaoInterface.findByColourName(productColourRequest.getColourName(),
				productColourRequest.getApplicationId(), productColourRequest.getTenantId())).thenReturn(colourName);

		doNothing().when(productColourDaoInterface).deleteProductColour(productColourRequest.getColourName(),
				productColourRequest.getApplicationId(), productColourRequest.getTenantId());

		productColourServiceImpl.deleteProductColour(productColourRequest);

		verify(productColourDaoInterface, times(1)).deleteProductColour(productColourRequest.getColourName(),
				productColourRequest.getApplicationId(), productColourRequest.getTenantId());
	}

	@Test
	public void testDeleteMeasurementUnitsDetailsNotExistedException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();

		when(productColourDaoInterface.findByColourName(productColourRequest.getColourName(), productColourRequest.getApplicationId(), productColourRequest.getTenantId())).thenReturn(null);

		assertThrows(InvalidInputParametersException.class, () -> {

			productColourServiceImpl.deleteProductColour(productColourRequest);
		});
		verify(productColourDaoInterface, times(1)).findByColourName(productColourRequest.getColourName(), productColourRequest.getApplicationId(), productColourRequest.getTenantId());
	}

	@Test
	public void testGetAllProductColor() throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productColourDaoInterface.getAllProductColour(productColourRequest.getApplicationId(),
				productColourRequest.getTenantId())).thenReturn(productColoursList);

		List<ProductColourResponse> allProductColour = productColourServiceImpl
				.getAllProductColour(productColourRequest);

		assertNotNull(allProductColour);
	}

}
