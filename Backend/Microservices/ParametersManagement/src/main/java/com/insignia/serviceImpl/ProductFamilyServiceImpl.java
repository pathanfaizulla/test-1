package com.insignia.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insignia.constants.ParametersManagementConstants;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.ProductFamilyDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.ProductFamily;
import com.insignia.model.ProductFamilyRequest;
import com.insignia.model.ProductFamilyResponse;
import com.insignia.serviceInterface.ProductFamilyServiceInterface;

import jakarta.transaction.Transactional;

@Service
public class ProductFamilyServiceImpl implements ProductFamilyServiceInterface {

	@Autowired
	private ProductFamilyDaoInterface productFamilyDaoInterface;

	@Autowired
	private TokenDaoInterface tokenDao;

	@Transactional
	@Override
	public ProductFamilyResponse saveProductFamily(ProductFamilyRequest productFamilyRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(productFamilyRequest.getCustomerSequenceNumber(),
				productFamilyRequest.getExpirationDuration());

		ProductFamilyResponse productFamilyResponse = null;
		Object productFamilyName = productFamilyDaoInterface.findByFamilyName(productFamilyRequest.getFamilyName(),
				productFamilyRequest.getApplicationId(), productFamilyRequest.getTenantId());

		if (productFamilyName != null
				&& productFamilyName.toString().equalsIgnoreCase(productFamilyRequest.getFamilyName())) {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.duplicateDataInProductFamilyErrorCode,
					ParametersManagementConstants.duplicateDataInProductFamilyMessage);
		}

		ProductFamily productFamilyEntity = new ProductFamily();
		productFamilyEntity.setApplicationId(productFamilyRequest.getApplicationId());
		productFamilyEntity.setTenantId(productFamilyRequest.getTenantId());
		productFamilyEntity.setFamilyName(productFamilyRequest.getFamilyName());
		productFamilyEntity.setFamilyDescription(productFamilyRequest.getFamilyDescription());
		productFamilyEntity.setProductFamilyImagePath(productFamilyRequest.getProductFamilyImagePath());
		productFamilyEntity.setDefaultImage(productFamilyRequest.getDefaultImage());
		ProductFamily productFamily = productFamilyDaoInterface.saveProductFamily(productFamilyEntity);

		productFamilyResponse = createResponseForProductFamilyEntity(productFamily);

		return productFamilyResponse;
	}

	@Transactional
	@Override
	public ProductFamilyResponse updateProductFamily(ProductFamilyRequest productFamilyRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(productFamilyRequest.getCustomerSequenceNumber(),
				productFamilyRequest.getExpirationDuration());

		Optional<ProductFamily> productFamilyList = productFamilyDaoInterface.findBySequenceNumber(
				productFamilyRequest.getSequenceNumber(), productFamilyRequest.getApplicationId(),
				productFamilyRequest.getTenantId());

		if (productFamilyList.isPresent()) {
			ProductFamily productFamily = productFamilyList.get();

			if (productFamilyRequest.isFamilyNameUpdated()) {
				productFamily.setFamilyName(productFamilyRequest.getFamilyName());
			}
			if (productFamilyRequest.isFamilyDescriptionUpdated()) {
				productFamily.setFamilyDescription(productFamilyRequest.getFamilyDescription());
			}
			if (productFamilyRequest.isProductFamilyImagePathUpdated()) {
				productFamily.setProductFamilyImagePath(productFamilyRequest.getProductFamilyImagePath());
			}
			if (productFamilyRequest.isDefaultImageUpdated()) {
				productFamily.setDefaultImage(productFamilyRequest.getDefaultImage());
			}

			productFamily = productFamilyDaoInterface.updateProductFamily(productFamily);

			return createResponseForProductFamilyEntity(productFamily);
		} else {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.productFamilyDetailsNotExistedErrorCode,
					ParametersManagementConstants.productFamilyDetailsNotExistedMessage);
		}
	}

	@Transactional
	@Override
	public void deleteProductFamily(ProductFamilyRequest productFamilyRequest)
			throws InvalidInputParametersException, TokenExpiredException {
		tokenDao.checkTokenValidity(productFamilyRequest.getCustomerSequenceNumber(),
				productFamilyRequest.getExpirationDuration());

		Object productFamilyName = productFamilyDaoInterface.findByFamilyName(productFamilyRequest.getFamilyName(),
				productFamilyRequest.getApplicationId(), productFamilyRequest.getTenantId());
		if (productFamilyName != null
				&& productFamilyName.toString().equalsIgnoreCase(productFamilyRequest.getFamilyName())) {
			productFamilyDaoInterface.deleteProductFamily(productFamilyRequest.getFamilyName(),
					productFamilyRequest.getApplicationId(), productFamilyRequest.getTenantId());
		} else {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.productFamilyDetailsNotExistedErrorCode,
					ParametersManagementConstants.productFamilyDetailsNotExistedMessage);
		}

	}

	@Transactional
	@Override
	public List<ProductFamilyResponse> getAllProductFamily(ProductFamilyRequest productFamilyRequest)
			throws TokenExpiredException {

		tokenDao.checkTokenValidity(productFamilyRequest.getCustomerSequenceNumber(),
				productFamilyRequest.getExpirationDuration());

		List<ProductFamilyResponse> productFamilyResponseList = new ArrayList<>();
		List<ProductFamily> productFamilyList = productFamilyDaoInterface
				.getAllProductFamily(productFamilyRequest.getApplicationId(), productFamilyRequest.getTenantId());

		if (productFamilyList != null) {
			for (ProductFamily productFamily : productFamilyList) {
				ProductFamilyResponse productFamilyResponse = createResponseForProductFamilyEntity(productFamily);
				productFamilyResponseList.add(productFamilyResponse);
			}
		}

		return productFamilyResponseList;
	}

	private ProductFamilyResponse createResponseForProductFamilyEntity(ProductFamily productFamily) {
		ProductFamilyResponse productFamilyResponse = new ProductFamilyResponse();
		productFamilyResponse.setSequenceNumber(productFamily.getSequenceNumber());
		productFamilyResponse.setFamilyName(productFamily.getFamilyName());
		productFamilyResponse.setFamilyDescription(productFamily.getFamilyDescription());
		productFamilyResponse.setProductFamilyImagePath(productFamily.getProductFamilyImagePath());
		productFamilyResponse.setDefaultImage(productFamily.getDefaultImage());
		return productFamilyResponse;
	}

}
