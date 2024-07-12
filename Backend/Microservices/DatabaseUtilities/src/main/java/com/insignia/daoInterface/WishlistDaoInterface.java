package com.insignia.daoInterface;

import java.util.List;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.entity.ProductDetails;
import com.insignia.entity.WishlistDetailsEntity;

public interface WishlistDaoInterface {

	public List<WishlistDetailsEntity> saveWishlistForCustomer(List<WishlistDetailsEntity> wishlistDetailsEntity)
			throws InvalidInputParametersException;

	public void deleteCustomerWishList(Long customerSequenceNumber);

	public void removeProductFromWishlist(Long customerSequenceNumber, List<Long> productSequenceNumber);

	public List<WishlistDetailsEntity> getCustomerWishlist(Long customerSequenceNumber);

	public List<ProductDetails> getCustomListProductDetails(List<Long> productSequenceNumberList);

	public List<WishlistDetailsEntity> findWishlistDetailByProductSequenceNumber(Long customerSequenceNumber,
			List<Long> productSequenceNumber);

}
