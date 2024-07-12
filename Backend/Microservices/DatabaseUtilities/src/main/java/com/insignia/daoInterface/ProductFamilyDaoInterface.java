package com.insignia.daoInterface;

import java.util.List;
import java.util.Optional;

import com.insignia.entity.ProductFamily;

public interface ProductFamilyDaoInterface {

	public ProductFamily saveProductFamily(ProductFamily productFamily);
	
	public ProductFamily updateProductFamily(ProductFamily productFamily);

	public void deleteProductFamily(String familyName, String applicationId, String tenantId);

	public List<ProductFamily> getAllProductFamily(String applicationId, String tenantId);

	public Object findByFamilyName(String familyName, String applicationId, String tenantId);

	public Optional<ProductFamily> findBySequenceNumber(Integer sequenceNumber, String applicationId, String tenantId);

	public void updateFamilyName(String familyName, Integer sequenceNumber, String applicationId, String tenantId);

	public void updateFamilyDescription(String familyDescrption, Integer sequenceNumber, String applicationId, String tenantId);

	public void updateFamilyNameAndDescription(String familyName, String familyDescrption, Integer sequenceNumber, String applicationId, String tenantId);
}
