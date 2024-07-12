package com.insignia.daoImpl;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insignia.daoInterface.ProductColourDaoInterface;
import com.insignia.entity.ProductColour;
import com.insignia.repo.ProductColorRepository;

@Repository
public class ProductColourDaoImpl implements ProductColourDaoInterface {

	@Autowired
	private ProductColorRepository productColorRepository;

	@Autowired
	private EntityManager entityManager;

	@Override
	public ProductColour saveProductColour(ProductColour productColour) {

		return productColorRepository.save(productColour);
	}

	@Override
	public ProductColour updateProductColour(ProductColour productColour) {
		return entityManager.merge(productColour);
	}

	@Override
	public void deleteProductColour(String colourName, String applicationId, String tenantId) {
		productColorRepository.deleteByColourName(colourName, applicationId, tenantId);

	}

	@Override
	public List<ProductColour> getAllProductColour(String applicationId, String tenantId) {
		return productColorRepository.fetchQueryForAllProductColour(applicationId, tenantId);
	}

	@Override
	public Object findByColourName(String colourName, String applicationId, String tenantId) {
		return productColorRepository.findByColourName(colourName, applicationId, tenantId);
	}

	@Override
	public Optional<ProductColour> findBySequenceNumber(Integer sequenceNumber, String applicationId, String tenantId) {
		return productColorRepository.fetchQueryForUpdate(sequenceNumber, applicationId, tenantId);
	}

	@Override
	public void updateColourName(String colourName,Integer sequenceNumber, String applicationId, String tenantId) {
		
		productColorRepository.updateQueryForColourName(colourName, sequenceNumber, applicationId, tenantId);
		
	}

	@Override
	public void updateColourDescription(String colourDescrption, Integer sequenceNumber, String applicationId, String tenantId) {
		productColorRepository.updateQueryForColourDescription(colourDescrption, sequenceNumber, applicationId, tenantId);
		
	}

	@Override
	public void updateColourNameAndDescription(String colourName, String colourDescrption,
			Integer sequenceNumber, String applicationId, String tenantId) {
		
		productColorRepository.updateQueryForColourNameAndDescription(colourName, colourDescrption, sequenceNumber, applicationId, tenantId);;
	}

}
