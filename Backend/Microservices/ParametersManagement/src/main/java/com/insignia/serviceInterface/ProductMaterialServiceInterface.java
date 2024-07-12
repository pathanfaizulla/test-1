package com.insignia.serviceInterface;

import java.util.List;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.ProductMaterialRequest;
import com.insignia.model.ProductMaterialResponse;

public interface ProductMaterialServiceInterface {

	public ProductMaterialResponse saveProductMaterial(ProductMaterialRequest productMaterialRequest)
			throws InvalidInputParametersException, TokenExpiredException;

	public ProductMaterialResponse updateProductMaterial(ProductMaterialRequest productMaterialRequest)
			throws InvalidInputParametersException, TokenExpiredException;

	public void deleteProductMaterial(ProductMaterialRequest productMaterialRequest)
			throws InvalidInputParametersException, TokenExpiredException;

	public List<ProductMaterialResponse> getAllProductMaterials(ProductMaterialRequest productMaterialRequest)
			throws TokenExpiredException;

}
