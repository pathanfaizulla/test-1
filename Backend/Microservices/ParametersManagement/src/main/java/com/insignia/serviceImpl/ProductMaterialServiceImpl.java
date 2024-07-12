package com.insignia.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insignia.constants.ParametersManagementConstants;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.ProductMaterialDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.ProductMaterial;
import com.insignia.model.ProductMaterialRequest;
import com.insignia.model.ProductMaterialResponse;
import com.insignia.serviceInterface.ProductMaterialServiceInterface;

import jakarta.transaction.Transactional;

@Service
public class ProductMaterialServiceImpl implements ProductMaterialServiceInterface {

	@Autowired
	private ProductMaterialDaoInterface productMaterialDaoInterface;

	@Autowired
	private TokenDaoInterface tokenDao;

	@Transactional
	@Override
	public ProductMaterialResponse saveProductMaterial(ProductMaterialRequest productMaterialRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(productMaterialRequest.getCustomerSequenceNumber(),
				productMaterialRequest.getExpirationDuration());
		Object materialName = productMaterialDaoInterface.findByMaterialName(productMaterialRequest.getMaterialName(),
				productMaterialRequest.getApplicationId(), productMaterialRequest.getTenantId());

		if (materialName != null
				&& materialName.toString().equalsIgnoreCase(productMaterialRequest.getMaterialName())) {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.duplicateDataInProductMaterialErrorCode,
					ParametersManagementConstants.duplicateDataInProductMaterialMessage);
		}

		ProductMaterial productMaterialEntity = new ProductMaterial();
		productMaterialEntity.setApplicationId(productMaterialRequest.getApplicationId());
		productMaterialEntity.setTenantId(productMaterialRequest.getTenantId());
		productMaterialEntity.setMaterialName(productMaterialRequest.getMaterialName());
		productMaterialEntity.setMaterialDescription(productMaterialRequest.getMaterialDescription());
		ProductMaterial productMaterial = productMaterialDaoInterface.saveProductMaterial(productMaterialEntity);

		return createResponseForProductMaterialEntity(productMaterial);
	}

	@Transactional
	@Override
	public ProductMaterialResponse updateProductMaterial(ProductMaterialRequest productMaterialRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(productMaterialRequest.getCustomerSequenceNumber(),
				productMaterialRequest.getExpirationDuration());

		Optional<ProductMaterial> productMaterialList = productMaterialDaoInterface.findBySequenceNumber(
				productMaterialRequest.getSequenceNumber(), productMaterialRequest.getApplicationId(),
				productMaterialRequest.getTenantId());

		if (productMaterialList.isPresent()) {
			ProductMaterial productMaterial = productMaterialList.get();

			if (productMaterialRequest.isMaterialNameUpdated()) {
				productMaterial.setMaterialName(productMaterialRequest.getMaterialName());
			}
			if (productMaterialRequest.isMaterialDescriptionUpdated()) {
				productMaterial.setMaterialDescription(productMaterialRequest.getMaterialDescription());
			}

			productMaterial = productMaterialDaoInterface.updateProductMaterial(productMaterial);

			return createResponseForProductMaterialEntity(productMaterial);
		} else {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.productBrandDetailsNotFoundErrorCode,
					ParametersManagementConstants.productBrandDetailsNotFoundMessage);
		}

	}

	@Transactional
	@Override
	public void deleteProductMaterial(ProductMaterialRequest productMaterialRequest)
			throws InvalidInputParametersException, TokenExpiredException {
		tokenDao.checkTokenValidity(productMaterialRequest.getCustomerSequenceNumber(),
				productMaterialRequest.getExpirationDuration());

		Object materialName = productMaterialDaoInterface.findByMaterialName(productMaterialRequest.getMaterialName(),
				productMaterialRequest.getApplicationId(), productMaterialRequest.getTenantId());

		if (materialName != null
				&& materialName.toString().equalsIgnoreCase(productMaterialRequest.getMaterialName())) {
			productMaterialDaoInterface.deleteProductMaterial(productMaterialRequest.getMaterialName(),
					productMaterialRequest.getApplicationId(), productMaterialRequest.getTenantId());
		} else {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.productMaterialDetailsNotExistedErrorCode,
					ParametersManagementConstants.productMaterialDetailsNotExistedMessage);
		}
	}

	@Transactional
	@Override
	public List<ProductMaterialResponse> getAllProductMaterials(ProductMaterialRequest productMaterialRequest)
			throws TokenExpiredException {

		tokenDao.checkTokenValidity(productMaterialRequest.getCustomerSequenceNumber(),
				productMaterialRequest.getExpirationDuration());

		List<ProductMaterialResponse> productMaterialResponseList = new ArrayList<>();
		List<ProductMaterial> productMaterialList = productMaterialDaoInterface.getAllProductMaterials(
				productMaterialRequest.getApplicationId(), productMaterialRequest.getTenantId());

		if (productMaterialList != null) {
			for (ProductMaterial productMaterial : productMaterialList) {
				ProductMaterialResponse productMaterialResponse = createResponseForProductMaterialEntity(
						productMaterial);
				productMaterialResponseList.add(productMaterialResponse);
			}
		}

		return productMaterialResponseList;
	}

	private ProductMaterialResponse createResponseForProductMaterialEntity(ProductMaterial productMaterial) {
		ProductMaterialResponse productMaterialResponse = new ProductMaterialResponse();
		productMaterialResponse.setSequenceNumber(productMaterial.getSequenceNumber());
		productMaterialResponse.setMaterialName(productMaterial.getMaterialName());
		productMaterialResponse.setMaterialDescription(productMaterial.getMaterialDescription());
		return productMaterialResponse;
	}

}
