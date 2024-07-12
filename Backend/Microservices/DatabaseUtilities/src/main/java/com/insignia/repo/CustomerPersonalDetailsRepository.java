package com.insignia.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.insignia.entity.CustomerPersonalDetails;

@Repository
public interface CustomerPersonalDetailsRepository extends JpaRepository<CustomerPersonalDetails, Serializable> {

}
