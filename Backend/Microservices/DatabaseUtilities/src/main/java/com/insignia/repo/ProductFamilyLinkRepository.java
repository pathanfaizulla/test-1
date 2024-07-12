package com.insignia.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insignia.entity.ProductFamilyLink;

import jakarta.transaction.Transactional;

public interface ProductFamilyLinkRepository extends JpaRepository<ProductFamilyLink, Serializable> {

	public static final String existByProductSequenceNumber = "select * from product_family_link where product_sequence_number = :product_sequence_number";

	public static final String deleteProductFamilyLinkQuery = "delete from product_family_link where product_sequence_number = :product_sequence_number AND product_family_sequence_number IN (:removedProductFamilyList)";

	@Query(value = existByProductSequenceNumber, nativeQuery = true)
	public ProductFamilyLink existByProductSequenceNumber(@Param("product_sequence_number") Long productSequenceNumber);

	@Modifying
	@Transactional
	@Query(value = deleteProductFamilyLinkQuery, nativeQuery = true)
	public void deleteProductFamilyLink(@Param("product_sequence_number") Long productSequenceNumber, @Param("removedProductFamilyList") List<Integer> removedProductFamilyList);

}
