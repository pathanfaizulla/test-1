package com.insignia.daoInterface;

import java.util.List;
import java.util.Optional;

import com.insignia.entity.ProductCatalogue;

public interface ProductCatalogueDaoInterface {

	public ProductCatalogue saveProductCatalogue(ProductCatalogue productCatalogue);

	public ProductCatalogue updateProductCatalogue(ProductCatalogue productCatalogue);

	public void deleteProductCatalogue(String catalogueName, String applicationId, String tenantId);

	public List<ProductCatalogue> getAllProductCatalogue(String applicationId, String tenantId);

	public Object findByCatalogueName(String catalogueName, String applicationId, String tenantId);

	public Optional<ProductCatalogue> findBySequenceNumber(Integer sequenceNumber, String applicationId, String tenantId);

	public void updateCatalogueName(String catalogueName, Integer sequenceNumber, String applicationId, String tenantId);

	public void updateCatalogueDescription(String catalogueDescrption, Integer sequenceNumber, String applicationId, String tenantId);

	public void updateCatalogueNameAndDescription(String catalogueName, String catalogueDescrption, Integer sequenceNumber, String applicationId, String tenantId);
}
