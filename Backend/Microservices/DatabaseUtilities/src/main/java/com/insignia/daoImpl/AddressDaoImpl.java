package com.insignia.daoImpl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.insignia.constants.AddressDetailsConstants;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.daoInterface.AddressDaoInterface;
import com.insignia.entity.AddressDetails;
import com.insignia.repo.AddressRepository;

@Repository
public class AddressDaoImpl implements AddressDaoInterface {

	@Autowired
	private AddressRepository addressRepo;

	@Autowired
	private EntityManager entityManager;

	public AddressDaoImpl(AddressRepository addressRepo, EntityManager entityManager) {
		super();
		this.entityManager = entityManager;

	}

	@Transactional(rollbackOn = InvalidInputParametersException.class)
	@Modifying
	@Override
	public AddressDetails saveAddressDetails(AddressDetails addressDetails) throws InvalidInputParametersException {
		try {
			return addressRepo.save(addressDetails);
		} catch (DataIntegrityViolationException e) {
			throw new InvalidInputParametersException(AddressDetailsConstants.validateAddressErrorCode,
					AddressDetailsConstants.validateAddressMessage);
		}

	}

	@Override
	public AddressDetails updateAddressDetails(AddressDetails addressDetails) throws InvalidInputParametersException {
		try {
			return entityManager.merge(addressDetails);

		} catch (DataIntegrityViolationException e) {
			throw new InvalidInputParametersException(AddressDetailsConstants.validateAddressErrorCode,
					AddressDetailsConstants.validateAddressMessage);

		} catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				throw new InvalidInputParametersException(AddressDetailsConstants.validateAddressErrorCode,
						AddressDetailsConstants.validateAddressMessage);
			}
			throw e;
		}

	}

	@Override
	public List<AddressDetails> getAddressDetails(Long customerSequenceNumber) {
		return addressRepo.findByCustomerSequenceNumber(customerSequenceNumber);
	}

	@Override
	public void deleteByAddressId(Integer sequenceNumber) {
		addressRepo.deleteById(sequenceNumber);

	}

	@Transactional
	@Override
	public void deleteAddressForCustomer(Long customerSequenceNumber) {
		addressRepo.deleteByCustomerSequenceNumber(customerSequenceNumber);

	}

	@Override
	public AddressDetails findByUserAddress(Long customersequencenumber, Integer sequenceNumber) {
		return addressRepo.findByCustomerSequenceNumberAndSequenceNumber(customersequencenumber, sequenceNumber);
	}
	

}
