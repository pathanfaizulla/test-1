package com.insignia.repo;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insignia.entity.CustomerCustomlistProductsLinkEntity;

import jakarta.transaction.Transactional;

public interface CustomerCustomlistProductsLinkRepository extends JpaRepository<CustomerCustomlistProductsLinkEntity, Serializable>{

	public final static String findCustomListProductLink = "Select * from customer_customlist_products_link WHERE customer_customlist_seq_number IN (:custom_list_seq_number_list)";

	public final static String removeProductFromCustomList = "delete FROM customer_customlist_products_link WHERE customer_customlist_seq_number = :customer_customlist_seq_number AND product_seq_number IN (:product_sequence_number_list)";

	@Query(value = findCustomListProductLink, nativeQuery = true)
	public List<CustomerCustomlistProductsLinkEntity> findCustomListProductLink(
			@Param("custom_list_seq_number_list") Set<Long> existingSeqNumberList);
	
	
	@Transactional
	@Modifying
	@Query(value = removeProductFromCustomList, nativeQuery = true)
	public void removeProductFromCustomList(@Param("customer_customlist_seq_number") Long customListSeqNumber,
			@Param("product_sequence_number_list")List<Long> productSequenceNumberList);
	

}
