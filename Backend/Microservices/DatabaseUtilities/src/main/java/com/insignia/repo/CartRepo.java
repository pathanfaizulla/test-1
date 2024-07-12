package com.insignia.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.insignia.entity.CartInformation;

@Repository
public interface CartRepo extends JpaRepository<CartInformation, Serializable> {

	List<CartInformation> findByCustomerSequenceNumber(Long customerSequenceNumber);

	public static final String deleteCartById = "delete from cart_information where cart_sequence_number =:cart_sequence_number";

	@Modifying
	@Query(value = deleteCartById, nativeQuery = true)
	public void deleteCartById(@Param("cart_sequence_number") Long cartSequenceNumber);

}
