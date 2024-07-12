package com.insignia.daoInterface;

import java.util.List;
import java.util.Optional;

import com.insignia.entity.ProductBrand;

public interface ProductBrandDaoInterface {

	public ProductBrand saveProductBrand(ProductBrand productBrand);

	public ProductBrand updateProductBrand(ProductBrand productBrand);

	public void deleteProductBrand(String brandName, String applicationId, String tenantId);

	public List<ProductBrand> getAllProductBrand(String applicationId, String tenantId);

	public Object findByBrandName(String brandName, String applicationId, String tenantId);

	public Optional<ProductBrand> findBySequenceNumber(Integer sequenceNumber, String applicationId, String tenantId);

	public void updateBrandName(String brandName, Integer sequenceNumber, String applicationId, String tenantId);

	public void updateBrandDescription(String brandDescrption, Integer sequenceNumber, String applicationId, String tenantId);

	public void updateBrandNameAndDescription(String brandName, String brandDescrption, Integer sequenceNumber, String applicationId, String tenantId);
}
