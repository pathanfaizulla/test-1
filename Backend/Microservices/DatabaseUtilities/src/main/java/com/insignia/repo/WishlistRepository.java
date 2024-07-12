package com.insignia.repo;

import java.io.Serializable;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insignia.entity.WishlistDetailsEntity;

public interface WishlistRepository extends JpaRepository<WishlistDetailsEntity, Serializable> {

	public final static String deleteByCustomerSequenceNumber = "delete from customer_wishlist_details where customer_sequence_number=:customer_sequence_number";

	public final static String deleteCustomerWishList = "delete from customer_wishlist_details where customer_sequence_number=:customer_sequence_number AND product_sequence_number IN (:product_sequence_number)";

	public final static String findWishlistDetailByProductId = "FROM WishlistDetailsEntity WHERE customerSequenceNumber = :customer_sequence_number AND productSequenceNumber IN (:product_sequence_number)";

	public List<WishlistDetailsEntity> findByCustomerSequenceNumber(Long customerSequenceNumber);

	@Query(value = findWishlistDetailByProductId)
	public List<WishlistDetailsEntity> findWishlistDetailByProductIdAndSequenceNumber(
			@Param("customer_sequence_number") Long customerSequenceNumber,
			@Param("product_sequence_number") List<Long> productSequenceNumber);

	@Transactional
	@Modifying
	@Query(value = deleteByCustomerSequenceNumber, nativeQuery = true)
	public void deleteByCustomerSequenceNumber(@Param("customer_sequence_number") Long customerSequenceNumber);

	@Transactional
	@Modifying
	@Query(value = deleteCustomerWishList, nativeQuery = true)
	public void deleteCustomerWishList(@Param("customer_sequence_number") Long customerSequenceNumber,
			@Param("product_sequence_number") List<Long> productSequenceNumber);

}
