package com.insignia.daoImpl;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.insignia.constants.CartValidaterConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.daoInterface.CartDaoInterface;
import com.insignia.entity.CartInformation;
import com.insignia.entity.CustomerCartInformation;
import com.insignia.entity.ProductDetails;
import com.insignia.repo.CartRepo;
import com.insignia.repo.CustomerCartInformationRepo;
import com.insignia.repo.ProductDetailsRepository;

@Repository
@Transactional
public class CartDaoImpl implements CartDaoInterface {

	@Autowired
	public CustomerCartInformationRepo cartInformationRepo;

	@Autowired
	public EntityManager entityManager;

	@Autowired
	public CartRepo cartRepo;


	@Autowired
	private ProductDetailsRepository productRepo;

	@Override
	public CartInformation saveCart(CartInformation cartInformation) throws InvalidInputParametersException {
		try {
			return cartRepo.save(cartInformation);
		} catch (DataIntegrityViolationException e) {
			throw new InvalidInputParametersException(CartValidaterConstant.validateCartDetailsErrorCode,
					CartValidaterConstant.validateCartDetailsMessage);
		}
	}

	@Override
	public void deleteCartForCustomer(Long cartSequenceNumber) {
		cartRepo.deleteCartById(cartSequenceNumber);

	}

	@Override
	public List<CartInformation> getCustomerCartInformation(Long customerSequenceNumber) {

		return cartRepo.findByCustomerSequenceNumber(customerSequenceNumber);
	}

	@Override
	public CustomerCartInformation addProductToCart(CustomerCartInformation customerCartInformation)
			throws InvalidInputParametersException {
		try {
			return cartInformationRepo.save(customerCartInformation);
		} catch (DataIntegrityViolationException e) {
			throw new InvalidInputParametersException(CartValidaterConstant.validateCartDetailsErrorCode,
					CartValidaterConstant.validateCartDetailsMessage);
		}

	}

	@Override
	public CustomerCartInformation updateCustomerCartProduct(CustomerCartInformation cartInformation) {

		return entityManager.merge(cartInformation);
	}

	@Override
	public void deleteProductToCart(Long customerCartSequenceNumber) {
		cartInformationRepo.deleteById(customerCartSequenceNumber);

	}

	@Override
	public Optional<CustomerCartInformation> findById(Long customerCartSequenceNumber) {

		return cartInformationRepo.findById(customerCartSequenceNumber);
	}

	@Override
	public List<CustomerCartInformation> findByCartSequenceNumber(Long cartSequenceNumber) {

		return cartInformationRepo.findBycartSequenceNumber(cartSequenceNumber);
	}


	@Override
	public List<ProductDetails> findSelectedProductList(List<Long> productSequenceNumberList) {

		return productRepo.getSelectedProductDetailsListForCartAndOrder(productSequenceNumberList);
	}

}
