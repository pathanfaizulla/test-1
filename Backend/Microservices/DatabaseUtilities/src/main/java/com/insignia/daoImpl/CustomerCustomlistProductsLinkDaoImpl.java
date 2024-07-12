package com.insignia.daoImpl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.insignia.constants.WishlistDetailsConstants;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.daoInterface.CustomerCustomlistProductsLinkDaoInterface;
import com.insignia.entity.CustomerCustomlistProductsLinkEntity;
import com.insignia.entity.ProductDetails;
import com.insignia.repo.CustomerCustomlistProductsLinkRepository;
import com.insignia.repo.ProductDetailsRepository;

@Repository
public class CustomerCustomlistProductsLinkDaoImpl implements CustomerCustomlistProductsLinkDaoInterface{
	
	@Autowired
	private CustomerCustomlistProductsLinkRepository customerCustomlistProductsLinkRepository;
	
	@Autowired
	private ProductDetailsRepository productDetailsRepository;

	@Override
	public List<CustomerCustomlistProductsLinkEntity> saveCustomList(List<CustomerCustomlistProductsLinkEntity> customerCustomlistProductsLinkEntityList)
			throws InvalidInputParametersException {
		try {
			return customerCustomlistProductsLinkRepository.saveAll(customerCustomlistProductsLinkEntityList);
		} catch (DataIntegrityViolationException e) {
			throw new InvalidInputParametersException(WishlistDetailsConstants.validateWishlistDetailsErrorCode,
					WishlistDetailsConstants.validateWishlistDetailsMessage);
		}
	}

	@Override
	public List<ProductDetails> getCustomListProductDetails(List<Long> productSequenceNumberList) {

		return productDetailsRepository.getCustomListProductDetails(productSequenceNumberList);
	}

	@Override
	public List<CustomerCustomlistProductsLinkEntity> findCustomListProductLink(Set<Long> existingSeqNumberList) {
		return customerCustomlistProductsLinkRepository.findCustomListProductLink(existingSeqNumberList);
	}

	@Override
	public void removeProductFromCustomList(Long customListSeqNumber,
			List<Long> productSequenceNumberList) {

		customerCustomlistProductsLinkRepository.removeProductFromCustomList(customListSeqNumber,
				productSequenceNumberList);
	}
}
