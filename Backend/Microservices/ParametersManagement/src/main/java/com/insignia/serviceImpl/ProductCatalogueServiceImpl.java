package com.insignia.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insignia.constants.ParametersManagementConstants;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.ProductCatalogueDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.ProductCatalogue;
import com.insignia.model.ProductCatalogueRequest;
import com.insignia.model.ProductCatalogueResponse;
import com.insignia.serviceInterface.ProductCatalogueServiceInterface;

import jakarta.transaction.Transactional;

@Service
public class ProductCatalogueServiceImpl implements ProductCatalogueServiceInterface {

	@Autowired
	private ProductCatalogueDaoInterface productCatalogueDaoInterface;

	@Autowired
	private TokenDaoInterface tokenDao;

	@Transactional
	@Override
	public ProductCatalogueResponse saveProductCatalogue(ProductCatalogueRequest productCatalogueRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(productCatalogueRequest.getCustomerSequenceNumber(),
				productCatalogueRequest.getExpirationDuration());

		ProductCatalogueResponse productCatalogueResponse = null;
		Object productCatalogueName = productCatalogueDaoInterface.findByCatalogueName(
				productCatalogueRequest.getCatalogueName(), productCatalogueRequest.getApplicationId(),
				productCatalogueRequest.getTenantId());

		if (productCatalogueName != null
				&& productCatalogueName.toString().equalsIgnoreCase(productCatalogueRequest.getCatalogueName())) {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.duplicateDataInProductCatalogueErrorCode,
					ParametersManagementConstants.duplicateDataInProductCatalogueMessage);
		}

		ProductCatalogue productCatalogueEntity = new ProductCatalogue();
		productCatalogueEntity.setApplicationId(productCatalogueRequest.getApplicationId());
		productCatalogueEntity.setTenantId(productCatalogueRequest.getTenantId());
		productCatalogueEntity.setCatalogueName(productCatalogueRequest.getCatalogueName());
		productCatalogueEntity.setCatalogueDescription(productCatalogueRequest.getCatalogueDescription());
		productCatalogueEntity.setProductCatalogueImagePath(productCatalogueRequest.getProductCatalogueImagePath());
		productCatalogueEntity.setDefaultImage(productCatalogueRequest.getDefaultImage());
		ProductCatalogue productCatalogue = productCatalogueDaoInterface.saveProductCatalogue(productCatalogueEntity);

		productCatalogueResponse = createResponseForProductCatalogueEntity(productCatalogue);

		return productCatalogueResponse;
	}

	@Transactional
	@Override
	public ProductCatalogueResponse updateProductCatalogue(ProductCatalogueRequest productCatalogueRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(productCatalogueRequest.getCustomerSequenceNumber(),
				productCatalogueRequest.getExpirationDuration());

		Optional<ProductCatalogue> productCatalogueList = productCatalogueDaoInterface.findBySequenceNumber(
				productCatalogueRequest.getSequenceNumber(), productCatalogueRequest.getApplicationId(),
				productCatalogueRequest.getTenantId());

		if (productCatalogueList.isPresent()) {
			ProductCatalogue productCatalogue = productCatalogueList.get();

			if (productCatalogueRequest.isCatalogueNameUpdated()) {
				productCatalogue.setCatalogueName(productCatalogueRequest.getCatalogueName());
			}
			if (productCatalogueRequest.isCatalogueDescriptionUpdated()) {
				productCatalogue.setCatalogueDescription(productCatalogueRequest.getCatalogueDescription());
			}
			if (productCatalogueRequest.isProductCatalogueImagePathUpdated()) {
				productCatalogue.setProductCatalogueImagePath(productCatalogueRequest.getProductCatalogueImagePath());
			}
			if (productCatalogueRequest.isDefaultImageUpdated()) {
				productCatalogue.setDefaultImage(productCatalogueRequest.getDefaultImage());
			}

			productCatalogue = productCatalogueDaoInterface.updateProductCatalogue(productCatalogue);

			return createResponseForProductCatalogueEntity(productCatalogue);
		} else {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.productCatalogueDetailsNotExistedErrorCode,
					ParametersManagementConstants.productCatalogueDetailsNotExistedMessage);
		}

	}

	@Transactional
	@Override
	public void deleteProductCatalogue(ProductCatalogueRequest productCatalogueRequest)
			throws InvalidInputParametersException, TokenExpiredException {
		tokenDao.checkTokenValidity(productCatalogueRequest.getCustomerSequenceNumber(),
				productCatalogueRequest.getExpirationDuration());

		Object productCatalogueName = productCatalogueDaoInterface.findByCatalogueName(
				productCatalogueRequest.getCatalogueName(), productCatalogueRequest.getApplicationId(),
				productCatalogueRequest.getTenantId());
		if (productCatalogueName != null) {
			productCatalogueDaoInterface.deleteProductCatalogue(productCatalogueRequest.getCatalogueName(),
					productCatalogueRequest.getApplicationId(), productCatalogueRequest.getTenantId());
		} else {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.productCatalogueDetailsNotExistedErrorCode,
					ParametersManagementConstants.productCatalogueDetailsNotExistedMessage);
		}

	}

	@Transactional
	@Override
	public List<ProductCatalogueResponse> getAllProductCatalogue(ProductCatalogueRequest productCatalogueRequest)
			throws TokenExpiredException {

		tokenDao.checkTokenValidity(productCatalogueRequest.getCustomerSequenceNumber(),
				productCatalogueRequest.getExpirationDuration());

		List<ProductCatalogueResponse> productCatalogueResponseList = new ArrayList<>();
		List<ProductCatalogue> productCatalogueList = productCatalogueDaoInterface.getAllProductCatalogue(
				productCatalogueRequest.getApplicationId(), productCatalogueRequest.getTenantId());

		if (productCatalogueList != null) {
			for (ProductCatalogue productCatalogue : productCatalogueList) {
				ProductCatalogueResponse productCatalogueResponse = createResponseForProductCatalogueEntity(
						productCatalogue);
				productCatalogueResponseList.add(productCatalogueResponse);
			}
		}

		return productCatalogueResponseList;
	}

	private ProductCatalogueResponse createResponseForProductCatalogueEntity(ProductCatalogue productCatalogue) {
		ProductCatalogueResponse productCatalogueResponse = new ProductCatalogueResponse();
		productCatalogueResponse.setSequenceNumber(productCatalogue.getSequenceNumber());
		productCatalogueResponse.setCatalogueName(productCatalogue.getCatalogueName());
		productCatalogueResponse.setCatalogueDescription(productCatalogue.getCatalogueDescription());
		productCatalogueResponse.setProductCatalogueImagePath(productCatalogue.getProductCatalogueImagePath());
		productCatalogueResponse.setDefaultImage(productCatalogue.getDefaultImage());
		return productCatalogueResponse;
	}

}
