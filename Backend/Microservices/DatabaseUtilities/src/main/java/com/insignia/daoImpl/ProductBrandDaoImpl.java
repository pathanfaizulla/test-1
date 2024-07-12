package com.insignia.daoImpl;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insignia.daoInterface.ProductBrandDaoInterface;
import com.insignia.entity.ProductBrand;
import com.insignia.repo.ProductBrandRepository;

@Repository
public class ProductBrandDaoImpl implements ProductBrandDaoInterface {

	@Autowired
	private ProductBrandRepository productBrandRepository;

	@Autowired
	private EntityManager entityManager;

	@Override
	public ProductBrand saveProductBrand(ProductBrand productBrand) {

		return productBrandRepository.save(productBrand);
	}

	@Override
	public ProductBrand updateProductBrand(ProductBrand productBrand) {

		return entityManager.merge(productBrand);
	}

	@Override
	public void deleteProductBrand(String brandName, String applicationId, String tenantId) {
		productBrandRepository.deleteByBrandName(brandName, applicationId, tenantId);
	}

	@Override
	public List<ProductBrand> getAllProductBrand(String applicationId, String tenantId) {

		return productBrandRepository.fetchQueryForAllProductBrand(applicationId, tenantId);
	}

	@Override
	public Object findByBrandName(String brandName, String applicationId, String tenantId) {

		return productBrandRepository.findByBrandName(brandName, applicationId, tenantId);
	}

	@Override
	public Optional<ProductBrand> findBySequenceNumber(Integer sequenceNumber, String applicationId, String tenantId) {

		return productBrandRepository.fetchQueryForUpdate(sequenceNumber, applicationId, tenantId);
	}

	@Override
	public void updateBrandName(String brandName, Integer sequenceNumber, String applicationId, String tenantId) {
		productBrandRepository.updateQueryForBrandName(brandName, sequenceNumber, applicationId, tenantId);

	}

	@Override
	public void updateBrandDescription(String brandDescrption, Integer sequenceNumber, String applicationId, String tenantId) {
		productBrandRepository.updateQueryForBrandDescription(brandDescrption, sequenceNumber, applicationId, tenantId);

	}

	@Override
	public void updateBrandNameAndDescription(String brandName, String brandDescrption, Integer sequenceNumber, String applicationId, String tenantId) {
		productBrandRepository.updateQueryForBrandNameAndDescription(brandName, brandDescrption, sequenceNumber, applicationId, tenantId);;
	}

}
