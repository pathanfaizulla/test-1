package com.insignia.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.insignia.entity.CustomerCartInformation;

@Repository
public interface CustomerCartInformationRepo extends JpaRepository<CustomerCartInformation, Serializable> {

	List<CustomerCartInformation> findBycartSequenceNumber(Long cartSequenceNumber);

}