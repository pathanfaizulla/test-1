package com.insignia.daoInterface;

import java.util.List;
import java.util.Optional;

import com.insignia.entity.ProductColour;

public interface ProductColourDaoInterface {

	public ProductColour saveProductColour(ProductColour productColour);
	
	public ProductColour updateProductColour(ProductColour productColour);
	
	public void deleteProductColour(String colourName, String applicationId, String tenantId);
	
	public List<ProductColour> getAllProductColour(String applicationId, String tenantId);
	
	public Object findByColourName(String colourName, String applicationId, String tenantId);
	
	public Optional<ProductColour> findBySequenceNumber(Integer sequenceNumber, String applicationId, String tenantId);
	
	public void updateColourName(String colourName,Integer sequenceNumber, String applicationId, String tenantId);
	
	public void updateColourDescription(String colourDescrption, Integer sequenceNumber, String applicationId, String tenantId);
	
	public void updateColourNameAndDescription(String colourName, String colourDescrption, Integer sequenceNumber, String applicationId, String tenantId);
}
