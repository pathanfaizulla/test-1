package com.insignia.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insignia.constants.ParametersManagementConstants;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.ProductBrandDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.ProductBrand;
import com.insignia.model.ProductBrandRequest;
import com.insignia.model.ProductBrandResponse;
import com.insignia.serviceInterface.ProductBrandServiceInterface;

import jakarta.transaction.Transactional;

@Service
public class ProductBrandServiceImpl implements ProductBrandServiceInterface {

	@Autowired
	private ProductBrandDaoInterface productBrandDaoInterface;

	@Autowired
	private TokenDaoInterface tokenDao;

	@Transactional
	@Override
	public ProductBrandResponse saveProductBrand(ProductBrandRequest productBrandRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(productBrandRequest.getCustomerSequenceNumber(),
				productBrandRequest.getExpirationDuration());

		ProductBrandResponse productBrandResponse = null;
		Object productBrandName = productBrandDaoInterface.findByBrandName(productBrandRequest.getBrandName(),
				productBrandRequest.getApplicationId(), productBrandRequest.getTenantId());

		if (productBrandName != null
				&& productBrandName.toString().equalsIgnoreCase(productBrandRequest.getBrandName())) {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.duplicateDataInProductBrandErrorCode,
					ParametersManagementConstants.duplicateDataInProductBrandMessage);
		}

		ProductBrand productBrandEntity = new ProductBrand();
		productBrandEntity.setApplicationId(productBrandRequest.getApplicationId());
		productBrandEntity.setTenantId(productBrandRequest.getTenantId());
		productBrandEntity.setBrandName(productBrandRequest.getBrandName());
		productBrandEntity.setBrandDescription(productBrandRequest.getBrandDescription());
		productBrandEntity.setProductBrandImagePath(productBrandRequest.getProductBrandImagePath());
		productBrandEntity.setDefaultImage(productBrandRequest.getDefaultImage());
		ProductBrand productBrand = productBrandDaoInterface.saveProductBrand(productBrandEntity);

		productBrandResponse = createResponseForProductBrandEntity(productBrand);

		return productBrandResponse;
	}

	@Transactional
	@Override
	public ProductBrandResponse updateProductBrand(ProductBrandRequest productBrandRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(productBrandRequest.getCustomerSequenceNumber(),
				productBrandRequest.getExpirationDuration());

		Optional<ProductBrand> productBrandList = productBrandDaoInterface.findBySequenceNumber(
				productBrandRequest.getSequenceNumber(), productBrandRequest.getApplicationId(),
				productBrandRequest.getTenantId());

		if (productBrandList.isPresent()) {
			ProductBrand productBrand = productBrandList.get();

			if (productBrandRequest.isBrandNameUpdated()) {
				productBrand.setBrandName(productBrandRequest.getBrandName());
			}
			if (productBrandRequest.isBrandDescriptionUpdated()) {
				productBrand.setBrandDescription(productBrandRequest.getBrandDescription());
			}
			if (productBrandRequest.isProductBrandImagePathUpdated()) {
				productBrand.setProductBrandImagePath(productBrandRequest.getProductBrandImagePath());
			}
			if (productBrandRequest.isDefaultImageUpdated()) {
				productBrand.setDefaultImage(productBrandRequest.getDefaultImage());
			}

			productBrand = productBrandDaoInterface.updateProductBrand(productBrand);

			return createResponseForProductBrandEntity(productBrand);
		} else {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.productBrandDetailsNotFoundErrorCode,
					ParametersManagementConstants.productBrandDetailsNotFoundMessage);
		}

	}

	@Transactional
	@Override
	public void deleteProductBrand(ProductBrandRequest productBrandRequest)
			throws InvalidInputParametersException, TokenExpiredException {
		tokenDao.checkTokenValidity(productBrandRequest.getCustomerSequenceNumber(),
				productBrandRequest.getExpirationDuration());

		Object productBrandName = productBrandDaoInterface.findByBrandName(productBrandRequest.getBrandName(),
				productBrandRequest.getApplicationId(), productBrandRequest.getTenantId());

		if (productBrandName != null
				&& productBrandName.toString().equalsIgnoreCase(productBrandRequest.getBrandName())) {
			productBrandDaoInterface.deleteProductBrand(productBrandRequest.getBrandName(),
					productBrandRequest.getApplicationId(), productBrandRequest.getTenantId());
		} else {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.productBrandDetailsNotExistedWithThisNameErrorCode,
					ParametersManagementConstants.productBrandDetailsNotExistedWithThisNameMessage);
		}

	}

	@Transactional
	@Override
	public List<ProductBrandResponse> getAllProductBrand(ProductBrandRequest productBrandRequest)
			throws TokenExpiredException {

		tokenDao.checkTokenValidity(productBrandRequest.getCustomerSequenceNumber(),
				productBrandRequest.getExpirationDuration());

		List<ProductBrandResponse> productBrandResponseList = new ArrayList<>();
		List<ProductBrand> productBrandList = productBrandDaoInterface
				.getAllProductBrand(productBrandRequest.getApplicationId(), productBrandRequest.getTenantId());

		if (productBrandList != null) {
			for (ProductBrand productBrand : productBrandList) {
				ProductBrandResponse productBrandResponse = createResponseForProductBrandEntity(productBrand);
				productBrandResponseList.add(productBrandResponse);
			}
		}

		return productBrandResponseList;
	}

	private ProductBrandResponse createResponseForProductBrandEntity(ProductBrand productBrand) {
		ProductBrandResponse productBrandResponse = new ProductBrandResponse();
		productBrandResponse.setSequenceNumber(productBrand.getSequenceNumber());
		productBrandResponse.setBrandName(productBrand.getBrandName());
		productBrandResponse.setBrandDescription(productBrand.getBrandDescription());
		productBrandResponse.setProductBrandImagePath(productBrand.getProductBrandImagePath());
		productBrandResponse.setDefaultImage(productBrand.getDefaultImage());

		return productBrandResponse;
	}

}
