package com.insignia.daoInterface;

import java.util.List;
import java.util.Set;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.entity.CustomerCustomlistProductsLinkEntity;
import com.insignia.entity.ProductDetails;

public interface CustomerCustomlistProductsLinkDaoInterface {
	
	public List<CustomerCustomlistProductsLinkEntity> saveCustomList(List<CustomerCustomlistProductsLinkEntity> customListDetailsEntity)
			throws InvalidInputParametersException;
	

	public List<CustomerCustomlistProductsLinkEntity> findCustomListProductLink(Set<Long> existingSeqNumberList);
	
	
	public List<ProductDetails> getCustomListProductDetails(List<Long> productSequenceNumberList);
	
	

	public void removeProductFromCustomList(Long customListSeqNumber,
			List<Long> productSequenceNumberList);

}
