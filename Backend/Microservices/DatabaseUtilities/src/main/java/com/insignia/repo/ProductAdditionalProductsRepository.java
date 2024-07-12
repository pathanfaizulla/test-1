package com.insignia.repo;

import java.io.Serializable;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insignia.entity.ProductAdditionalProducts;

public interface ProductAdditionalProductsRepository extends JpaRepository<ProductAdditionalProducts, Serializable> {

	public static final String queryToDeleteAdditionalProduct = "DELETE FROM product_additional_products WHERE product_sequence_number = :product_sequence_number AND additional_product IN (:removedAdditionalProductList)";

	@Modifying
	@Transactional
	@Query(value = queryToDeleteAdditionalProduct, nativeQuery = true)
	public void deleteAdditionalProducts(@Param("product_sequence_number") Long productSequenceNumber,
			@Param("removedAdditionalProductList") List<Long> removedAdditionalProductList);
}

