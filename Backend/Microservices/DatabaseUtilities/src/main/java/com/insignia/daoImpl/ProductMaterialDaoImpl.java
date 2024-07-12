package com.insignia.daoImpl;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insignia.daoInterface.ProductMaterialDaoInterface;
import com.insignia.entity.ProductMaterial;
import com.insignia.repo.ProductMaterialRepository;

@Repository
public class ProductMaterialDaoImpl implements ProductMaterialDaoInterface {

	@Autowired
	private ProductMaterialRepository productMaterialRepository;

	@Autowired
	private EntityManager entityManager;

	@Override
	public ProductMaterial saveProductMaterial(ProductMaterial productMaterial) {

		return productMaterialRepository.save(productMaterial);
	}

	@Override
	public ProductMaterial updateProductMaterial(ProductMaterial productMaterial) {
		return entityManager.merge(productMaterial);
	}

	@Override
	public void deleteProductMaterial(String unitName, String applicationId, String tenantId) {
		productMaterialRepository.deleteByMaterialName(unitName, applicationId, tenantId);

	}

	@Override
	public List<ProductMaterial> getAllProductMaterials(String applicationId, String tenantId) {
		return productMaterialRepository.fetchQueryForAllProductMaterial(applicationId, tenantId);
	}

	@Override
	public Object findByMaterialName(String materialName, String applicationId, String tenantId) {
		return productMaterialRepository.findByMaterialName(materialName, applicationId, tenantId);
	}

	@Override
	public Optional<ProductMaterial> findBySequenceNumber(Integer sequenceNumber, String applicationId, String tenantId) {
		return productMaterialRepository.findById(sequenceNumber);
	}

	@Override
	public void updateMaterialName(String materialName, Integer sequenceNumber, String applicationId, String tenantId) {
		productMaterialRepository.updateQueryForMaterialName(materialName, sequenceNumber, applicationId, tenantId);

	}

	@Override
	public void updateMaterialDescription(String materialDescrption, Integer sequenceNumber, String applicationId,
			String tenantId) {
		productMaterialRepository.updateQueryForMaterialDescription(materialDescrption, sequenceNumber, applicationId,
				tenantId);

	}

	@Override
	public void updateMaterialNameAndDescription(String materialName, String materialDescrption, Integer sequenceNumber,
			String applicationId, String tenantId) {
		productMaterialRepository.updateQueryForMaterialNameAndDescription(materialName, materialDescrption,
				sequenceNumber, applicationId, tenantId);
		;
	}

}
