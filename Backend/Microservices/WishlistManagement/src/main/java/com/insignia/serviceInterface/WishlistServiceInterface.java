package com.insignia.serviceInterface;

import java.util.List;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.WishlistDetailsRequest;
import com.insignia.model.WishlistManagementResponse;

public interface WishlistServiceInterface {

	public WishlistManagementResponse saveWishlistForCustomer(WishlistDetailsRequest wishlistDetailsRequest) throws TokenExpiredException, InvalidInputParametersException;

	public void deleteCustomerWishList(Long customerSequenceNumber, Integer expirationDuration) throws TokenExpiredException;

	public void removeProductFromWishlist(Long customerSequenceNumber, List<Long> productId, Integer expirationDuration) throws TokenExpiredException;

	public WishlistManagementResponse getCustomerWishlist(Long customerSequenceNumber, Integer expirationDuration)
			throws TokenExpiredException;
}
