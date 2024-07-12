package com.insignia.daoInterface;

import java.util.List;
import java.util.Optional;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.entity.CartInformation;
import com.insignia.entity.CustomerCartInformation;
import com.insignia.entity.ProductDetails;

public interface CartDaoInterface {

	public CartInformation saveCart(CartInformation cartInformation) throws InvalidInputParametersException;

	public void deleteCartForCustomer(Long cartSequenceNumber);

	public List<CartInformation> getCustomerCartInformation(Long customerSequenceNumber);

	public CustomerCartInformation addProductToCart(CustomerCartInformation cartInformation)
			throws InvalidInputParametersException;

	public CustomerCartInformation updateCustomerCartProduct(CustomerCartInformation cartInformation);

	public void deleteProductToCart(Long customerCartSequenceNumber);

	public Optional<CustomerCartInformation> findById(Long customerCartSequenceNumber);

	public List<CustomerCartInformation> findByCartSequenceNumber(Long cartSequenceNumber);

	public List<ProductDetails> findSelectedProductList(List<Long> productSequenceNumberList);

}
