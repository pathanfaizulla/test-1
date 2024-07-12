package com.insignia.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.insignia.entity.AddressDetails;

@Repository
public interface AddressRepository extends JpaRepository<AddressDetails, Serializable> {

	public List<AddressDetails> findByCustomerSequenceNumber(Long customerSequenceNumber);
	
	public AddressDetails findByCustomerSequenceNumberAndSequenceNumber(Long customersequencenumber,Integer sequenceNumber);
	
	public void deleteByCustomerSequenceNumber(Long customersequencenumber);
}