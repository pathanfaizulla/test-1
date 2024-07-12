package com.insignia.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insignia.constants.ParametersManagementConstants;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.ProductColourDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.ProductColour;
import com.insignia.model.ProductColourRequest;
import com.insignia.model.ProductColourResponse;
import com.insignia.serviceInterface.ProductColourServiceInterface;

@Service
public class ProductColourServiceImpl implements ProductColourServiceInterface {

	@Autowired
	private ProductColourDaoInterface productColourDaoInterface;

	@Autowired
	private TokenDaoInterface tokenDao;

	@Transactional
	@Override
	public ProductColourResponse saveProductColour(ProductColourRequest productColourRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(productColourRequest.getCustomerSequenceNumber(),
				productColourRequest.getExpirationDuration());

		ProductColourResponse productColourResponse = null;
		Object productColourName = productColourDaoInterface.findByColourName(productColourRequest.getColourName(),
				productColourRequest.getApplicationId(), productColourRequest.getTenantId());

		if (productColourName != null) {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.duplicateDataInProductColourErrorCode,
					ParametersManagementConstants.duplicateDataInProductColourMessage);
		}

		ProductColour productColourEntity = new ProductColour();
		productColourEntity.setApplicationId(productColourRequest.getApplicationId());
		productColourEntity.setTenantId(productColourRequest.getTenantId());
		productColourEntity.setColourName(productColourRequest.getColourName());
		productColourEntity.setColourDescription(productColourRequest.getColourDescription());
		productColourEntity.setHexCode(productColourRequest.getHexCode());
		ProductColour productColour = productColourDaoInterface.saveProductColour(productColourEntity);

		productColourResponse = createResponseForProductColourEntity(productColour);

		return productColourResponse;
	}

	@Transactional
	@Override
	public ProductColourResponse updateProductColour(ProductColourRequest productColourRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(productColourRequest.getCustomerSequenceNumber(),
				productColourRequest.getExpirationDuration());

		Optional<ProductColour> productColourList = productColourDaoInterface.findBySequenceNumber(
				productColourRequest.getSequenceNumber(), productColourRequest.getApplicationId(),
				productColourRequest.getTenantId());

		if (productColourList.isPresent()) {
			ProductColour productColour = productColourList.get();

			if (productColourRequest.isColourNameUpdated()) {
				productColour.setColourName(productColourRequest.getColourName());
			}
			if (productColourRequest.isColourDescriptionUpdated()) {
				productColour.setColourDescription(productColourRequest.getColourDescription());
			}
			if (productColourRequest.isHexCodeUpdated()) {
				productColour.setHexCode(productColourRequest.getHexCode());
			}

			productColour = productColourDaoInterface.updateProductColour(productColour);

			return createResponseForProductColourEntity(productColour);
		} else {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.productColourDetailsNotExistedErrorCode,
					ParametersManagementConstants.productColourDetailsNotExistedMessage);
		}

	}

	@Transactional
	@Override
	public void deleteProductColour(ProductColourRequest productColourRequest)
			throws InvalidInputParametersException, TokenExpiredException {
		tokenDao.checkTokenValidity(productColourRequest.getCustomerSequenceNumber(),
				productColourRequest.getExpirationDuration());

		Object productColourName = productColourDaoInterface.findByColourName(productColourRequest.getColourName(),
				productColourRequest.getApplicationId(), productColourRequest.getTenantId());
		if (productColourName != null
				&& productColourName.toString().equalsIgnoreCase(productColourRequest.getColourName())) {
			productColourDaoInterface.deleteProductColour(productColourRequest.getColourName(),
					productColourRequest.getApplicationId(), productColourRequest.getTenantId());
		} else {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.productColourDetailsNotExistedErrorCode,
					ParametersManagementConstants.productColourDetailsNotExistedMessage);
		}

	}

	@Transactional
	@Override
	public List<ProductColourResponse> getAllProductColour(ProductColourRequest productColourRequest)
			throws TokenExpiredException {

		tokenDao.checkTokenValidity(productColourRequest.getCustomerSequenceNumber(),
				productColourRequest.getExpirationDuration());

		List<ProductColourResponse> productColourResponseList = new ArrayList<>();
		List<ProductColour> productColourList = productColourDaoInterface
				.getAllProductColour(productColourRequest.getApplicationId(), productColourRequest.getTenantId());

		if (productColourList != null) {
			for (ProductColour productColour : productColourList) {
				ProductColourResponse productColourResponse = createResponseForProductColourEntity(productColour);
				productColourResponseList.add(productColourResponse);
			}
		}

		return productColourResponseList;
	}

	private ProductColourResponse createResponseForProductColourEntity(ProductColour productColour) {
		ProductColourResponse productColourResponse = new ProductColourResponse();
		productColourResponse.setSequenceNumber(productColour.getSequenceNumber());
		productColourResponse.setColourName(productColour.getColourName());
		productColourResponse.setColourDescription(productColour.getColourDescription());
		productColourResponse.setHexCode(productColour.getHexCode());
		return productColourResponse;
	}

}
