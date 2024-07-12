package com.insignia.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insignia.entity.CustomerPaymentMethodDetails;

public interface CustomerPaymentMethodRepository extends JpaRepository<CustomerPaymentMethodDetails, Serializable> {

	public final static String getListOfPaymentMethodDetails = "SELECT * from customer_payment_method_details where customer_sequence_number=:customer_sequence_number";

	@Query(value = getListOfPaymentMethodDetails, nativeQuery = true)
	public List<CustomerPaymentMethodDetails> getListOfPaymentMethodDetails(@Param("customer_sequence_number") Long customerSequenceNumber);

}