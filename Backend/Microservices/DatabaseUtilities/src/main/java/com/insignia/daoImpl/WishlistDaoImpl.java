package com.insignia.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.insignia.constants.WishlistDetailsConstants;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.daoInterface.WishlistDaoInterface;
import com.insignia.entity.ProductDetails;
import com.insignia.entity.WishlistDetailsEntity;
import com.insignia.repo.ProductDetailsRepository;
import com.insignia.repo.WishlistRepository;

@Repository
public class WishlistDaoImpl implements WishlistDaoInterface {

	@Autowired
	private WishlistRepository wishlistRepository;

	@Autowired
	private ProductDetailsRepository productDetailsRepository;

	@Override
	public List<WishlistDetailsEntity> saveWishlistForCustomer(List<WishlistDetailsEntity> wishlistDetailsEntityList)
			throws InvalidInputParametersException {
		try {
			List<WishlistDetailsEntity> wishlistList = new ArrayList<>();
			for (WishlistDetailsEntity wishlistDetailsEntity : wishlistDetailsEntityList) {
				wishlistRepository.save(wishlistDetailsEntity);
				wishlistList.add(wishlistDetailsEntity);
			}
			return wishlistList;

		} catch (DataIntegrityViolationException e) {
			throw new InvalidInputParametersException(WishlistDetailsConstants.validateWishlistDetailsErrorCode,
					WishlistDetailsConstants.validateWishlistDetailsMessage);
		}
	}

	@Override
	public void deleteCustomerWishList(Long customerSequenceNumber) {
		wishlistRepository.deleteByCustomerSequenceNumber(customerSequenceNumber);

	}

	@Override
	public List<WishlistDetailsEntity> getCustomerWishlist(Long customerSequenceNumber) {

		return wishlistRepository.findByCustomerSequenceNumber(customerSequenceNumber);
	}

	@Override
	public void removeProductFromWishlist(Long customerSequenceNumber, List<Long> productSequenceNumber) {
		wishlistRepository.deleteCustomerWishList(customerSequenceNumber, productSequenceNumber);

	}

	@Override
	public List<ProductDetails> getCustomListProductDetails(List<Long> productSequenceNumberList) {

		return productDetailsRepository.getCustomListProductDetails(productSequenceNumberList);
	}

	@Override
	public List<WishlistDetailsEntity> findWishlistDetailByProductSequenceNumber(Long customerSequenceNumber,
			List<Long> productSequenceNumber) {

		return wishlistRepository.findWishlistDetailByProductIdAndSequenceNumber(customerSequenceNumber,
				productSequenceNumber);
	}

}
