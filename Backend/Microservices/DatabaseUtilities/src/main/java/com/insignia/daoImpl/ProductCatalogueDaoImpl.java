package com.insignia.daoImpl;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insignia.daoInterface.ProductCatalogueDaoInterface;
import com.insignia.entity.ProductCatalogue;
import com.insignia.repo.ProductCatalogueRepository;

@Repository
public class ProductCatalogueDaoImpl implements ProductCatalogueDaoInterface {

	@Autowired
	private ProductCatalogueRepository productCatalogueRepository;

	@Autowired
	private EntityManager entityManager;

	@Override
	public ProductCatalogue saveProductCatalogue(ProductCatalogue productCatalogue) {

		return productCatalogueRepository.save(productCatalogue);
	}

	@Override
	public ProductCatalogue updateProductCatalogue(ProductCatalogue productCatalogue) {

		return entityManager.merge(productCatalogue);
	}

	@Override
	public void deleteProductCatalogue(String catalogueName, String applicationId, String tenantId) {
		productCatalogueRepository.deleteByCatalogueName(catalogueName, applicationId, tenantId);

	}
	

	@Override
	public List<ProductCatalogue> getAllProductCatalogue(String applicationId, String tenantId) {

		return productCatalogueRepository.fetchQueryForAllProductCatalogue(applicationId, tenantId);
	}

	@Override
	public Object findByCatalogueName(String catalogueName, String applicationId, String tenantId) {

		return productCatalogueRepository.findByCatalogueName(catalogueName, applicationId, tenantId);
	}

	@Override
	public Optional<ProductCatalogue> findBySequenceNumber(Integer sequenceNumber, String applicationId, String tenantId) {

		return productCatalogueRepository.fetchQueryForUpdate(sequenceNumber, applicationId, tenantId);
	}

	@Override
	public void updateCatalogueName(String catalogueName, Integer sequenceNumber, String applicationId, String tenantId) {
		productCatalogueRepository.updateQueryForCatalogueName(catalogueName, sequenceNumber, applicationId, tenantId);
		
	}

	@Override
	public void updateCatalogueDescription(String catalogueDescrption, Integer sequenceNumber, String applicationId, String tenantId) {
		productCatalogueRepository.updateQueryForCatalogueDescription(catalogueDescrption, sequenceNumber, applicationId, tenantId);
		
	}

	@Override
	public void updateCatalogueNameAndDescription(String catalogueName, String catalogueDescrption,
			Integer sequenceNumber, String applicationId, String tenantId) {
		productCatalogueRepository.updateQueryForCatalogueNameAndDescription(catalogueName, catalogueDescrption, sequenceNumber, applicationId, tenantId);
		
	}

}
