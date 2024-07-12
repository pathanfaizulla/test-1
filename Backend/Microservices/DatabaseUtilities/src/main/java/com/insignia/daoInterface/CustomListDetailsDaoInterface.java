package com.insignia.daoInterface;

import java.util.List;
import java.util.Set;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.entity.CustomListDetailsEntity;
import com.insignia.entity.ProductDetails;

public interface CustomListDetailsDaoInterface {
	
	public List<CustomListDetailsEntity> saveAll(List<CustomListDetailsEntity> customListDetailsEntity)
			throws InvalidInputParametersException;
	
	public List<CustomListDetailsEntity> getCustomListForCustomer(Long customerSequenceNumber);

	public void deleteAllCustomListForCustomer(Long customerSequenceNumber);
	
	public List<ProductDetails> getCustomListProductDetails(List<Long> productSequenceNumberList);
	
	public void deleteCustomList(Long customerSequenceNumber, List<String> customListNameList);

	public void removeProductFromCustomList(Long customerSequenceNumber, String customListName,
			List<Long> productSequenceNumberList);

	public CustomListDetailsEntity updateCustomListName(CustomListDetailsEntity customListDetailsEntity);

	public List<CustomListDetailsEntity> findBySequenceNumber(Set<Long> sequenceNumberList);

}
