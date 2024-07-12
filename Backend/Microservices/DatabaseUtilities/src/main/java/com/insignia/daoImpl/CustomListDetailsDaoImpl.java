package com.insignia.daoImpl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.insignia.constants.WishlistDetailsConstants;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.daoInterface.CustomListDetailsDaoInterface;
import com.insignia.entity.CustomListDetailsEntity;
import com.insignia.entity.ProductDetails;
import com.insignia.repo.CustomListDetailsRepository;
import com.insignia.repo.ProductDetailsRepository;

import jakarta.persistence.EntityManager;

@Repository
public class CustomListDetailsDaoImpl implements CustomListDetailsDaoInterface {

	@Autowired
	private CustomListDetailsRepository customListDetailsRepository;

	@Autowired
	private ProductDetailsRepository productDetailsRepository;

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<CustomListDetailsEntity> saveAll(List<CustomListDetailsEntity> customListDetailsEntityList)
			throws InvalidInputParametersException {
		try {

			return customListDetailsRepository.saveAll(customListDetailsEntityList);
			
		} catch (DataIntegrityViolationException e) {
			
			throw new InvalidInputParametersException(WishlistDetailsConstants.validateWishlistDetailsErrorCode,
					WishlistDetailsConstants.validateWishlistDetailsMessage);
		}
	}

	@Override
	public void deleteAllCustomListForCustomer(Long customerSequenceNumber) {
		customListDetailsRepository.deleteAllCustomListForCustomer(customerSequenceNumber);

	}

	@Override
	public List<CustomListDetailsEntity> getCustomListForCustomer(Long customerSequenceNumber) {

		return customListDetailsRepository.findByCustomerSequenceNumber(customerSequenceNumber);
	}

	@Override
	public List<ProductDetails> getCustomListProductDetails(List<Long> productSequenceNumberList) {

		return productDetailsRepository.getCustomListProductDetails(productSequenceNumberList);
	}


	@Override
	public void deleteCustomList(Long customerSequenceNumber, List<String> customListNameList) {
		customListDetailsRepository.deleteCustomList(customerSequenceNumber, customListNameList);

	}

	@Override
	public void removeProductFromCustomList(Long customerSequenceNumber, String customListName,
			List<Long> productSequenceNumberList) {

		customListDetailsRepository.removeProductFromCustomList(customerSequenceNumber, customListName,
				productSequenceNumberList);
	}

	@Override
	public CustomListDetailsEntity updateCustomListName(CustomListDetailsEntity customListDetailsEntity) {
		return entityManager.merge(customListDetailsEntity);
	}

	@Override
	public List<CustomListDetailsEntity> findBySequenceNumber(Set<Long> sequenceNumberList) {
		
		return customListDetailsRepository.findBySequenceNumber(sequenceNumberList);
	}

}
