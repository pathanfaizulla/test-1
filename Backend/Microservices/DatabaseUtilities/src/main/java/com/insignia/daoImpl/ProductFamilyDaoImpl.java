package com.insignia.daoImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insignia.daoInterface.ProductFamilyDaoInterface;
import com.insignia.entity.ProductFamily;
import com.insignia.repo.ProductFamilyRepository;

import jakarta.persistence.EntityManager;

@Repository
public class ProductFamilyDaoImpl implements ProductFamilyDaoInterface {

	@Autowired
	private ProductFamilyRepository productFamilyRepository;
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public ProductFamily saveProductFamily(ProductFamily productFamily) {
		return productFamilyRepository.save(productFamily);
	}

	@Override
	public void deleteProductFamily(String familyName, String applicationId, String tenantId) {
		productFamilyRepository.deleteByFamilyName(familyName, applicationId, tenantId);

	}

	@Override
	public List<ProductFamily> getAllProductFamily(String applicationId, String tenantId) {
		return productFamilyRepository.fetchQueryForAllProductFamily(applicationId, tenantId);
	}

	@Override
	public Object findByFamilyName(String familyName, String applicationId, String tenantId) {

		return productFamilyRepository.findByFamilyName(familyName, applicationId, tenantId);
	}

	@Override
	public Optional<ProductFamily> findBySequenceNumber(Integer sequenceNumber, String applicationId, String tenantId) {

		return productFamilyRepository.fetchQueryForUpdate(sequenceNumber, applicationId, tenantId);
	}

	@Override
	public void updateFamilyName(String familyName, Integer sequenceNumber, String applicationId, String tenantId) {
		productFamilyRepository.updateQueryForFamilyName(familyName, sequenceNumber, applicationId, tenantId);
	}

	@Override
	public void updateFamilyDescription(String familyDescrption, Integer sequenceNumber, String applicationId, String tenantId) {
		productFamilyRepository.updateQueryForFamilyDescription(familyDescrption, sequenceNumber, applicationId, tenantId);
	}

	@Override
	public void updateFamilyNameAndDescription(String familyName, String familyDescrption, Integer sequenceNumber, String applicationId, String tenantId) {
		productFamilyRepository.updateQueryForFamilyNameAndDescription(familyName, familyDescrption, sequenceNumber, applicationId, tenantId);;

	}

	@Override
	public ProductFamily updateProductFamily(ProductFamily productFamily) {
		return entityManager.merge(productFamily);
	}

}
