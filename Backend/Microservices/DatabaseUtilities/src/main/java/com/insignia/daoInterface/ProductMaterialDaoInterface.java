package com.insignia.daoInterface;

import java.util.List;
import java.util.Optional;

import com.insignia.entity.ProductMaterial;

public interface ProductMaterialDaoInterface {

	public ProductMaterial saveProductMaterial(ProductMaterial productMaterial);

	public ProductMaterial updateProductMaterial(ProductMaterial productMaterial);

	public void deleteProductMaterial(String materialName, String applicationId, String tenantId);

	public List<ProductMaterial> getAllProductMaterials(String applicationId, String tenantId);

	public Object findByMaterialName(String materialName, String applicationId, String tenantId);

	public Optional<ProductMaterial> findBySequenceNumber(Integer sequenceNumber, String applicationId, String tenantId);

	public void updateMaterialName(String materialName, Integer sequenceNumber, String applicationId, String tenantId);

	public void updateMaterialDescription(String materialDescrption, Integer sequenceNumber, String applicationId, String tenantId);

	public void updateMaterialNameAndDescription(String materialName, String materialDescrption,
			Integer sequenceNumber, String applicationId, String tenantId);
}
