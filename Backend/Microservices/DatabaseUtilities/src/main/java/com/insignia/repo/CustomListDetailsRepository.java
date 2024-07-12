package com.insignia.repo;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insignia.entity.CustomListDetailsEntity;

import jakarta.transaction.Transactional;

public interface CustomListDetailsRepository extends JpaRepository<CustomListDetailsEntity, Serializable> {

	public final static String deleteAllCustomListForCustomer = "delete from customer_customlist_details where customer_sequence_number=:customer_sequence_number";

	public final static String findCustomListDetailByCustomerSequenceNumberAndCustomListName = "Select * from customer_customlist_details WHERE customer_sequence_number =:customer_sequence_number AND custom_list_name IN (:custom_list_name)";

	public final static String findByCustomerSequenceNumber = "Select * from customer_customlist_details WHERE customer_sequence_number =:customer_sequence_number";
	
	public final static String deleteCustomList = "delete from customer_customlist_details where customer_sequence_number=:customer_sequence_number AND custom_list_name IN (:custom_list_name)";
	
	public final static String removeProductFromCustomList = "delete FROM customer_customlist_details WHERE customer_sequence_number = :customer_sequence_number AND custom_list_name IN (:custom_list_name) AND product_sequence_number IN (:product_sequence_number)";
	
	public final static String findBySequenceNumber = "select * from customer_customlist_details WHERE sequence_number IN (:sequence_number)";

	@Query(value = findCustomListDetailByCustomerSequenceNumberAndCustomListName, nativeQuery = true)
	public Optional<CustomListDetailsEntity> findCustomListDetailByCustomerSequenceNumberAndCustomListName(
			@Param("customer_sequence_number") Long customerSequenceNumber,
			@Param("custom_list_name") Set<String> customListNameDetailsList);

	@Query(value = findByCustomerSequenceNumber, nativeQuery = true)
	public List<CustomListDetailsEntity> findByCustomerSequenceNumber(
			@Param("customer_sequence_number") Long customerSequenceNumber);
	
	@Transactional
	@Modifying
	@Query(value = deleteAllCustomListForCustomer, nativeQuery = true)
	public void deleteAllCustomListForCustomer(@Param("customer_sequence_number") Long customerSequenceNumber);


	@Transactional
	@Modifying
	@Query(value = deleteCustomList, nativeQuery = true)
	public void deleteCustomList(@Param("customer_sequence_number") Long customerSequenceNumber,
			@Param("custom_list_name") List<String> customListNameList);

	@Transactional
	@Modifying
	@Query(value = removeProductFromCustomList, nativeQuery = true)
	public void removeProductFromCustomList(@Param("customer_sequence_number") Long customerSequenceNumber,@Param("custom_list_name") String customListName,
			@Param("product_sequence_number")List<Long> productSequenceNumberList);

	@Query(value = findBySequenceNumber, nativeQuery = true)
	public List<CustomListDetailsEntity> findBySequenceNumber(@Param("sequence_number") Set<Long> sequenceNumberList);
}
