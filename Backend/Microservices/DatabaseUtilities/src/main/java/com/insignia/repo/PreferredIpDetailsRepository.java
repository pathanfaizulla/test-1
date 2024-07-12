package com.insignia.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insignia.entity.PreferredIpDetails;

public interface PreferredIpDetailsRepository extends JpaRepository<PreferredIpDetails, Serializable> {

	public static final String CUSTOMER_SEQUENCE_NUMBER = "customer_sequence_number";
	public static final String IP_DETAILS = "ip_details";

	public static final String getDetailsByCustomerSequenceNumber = "select * from preferred_ip_details where customer_sequence_number = :customer_sequence_number";

	public static final String deleteDetailsByCustomerSequenceNumber = "DELETE FROM preferred_ip_details WHERE customer_sequence_number = :customer_sequence_number";
	public static final String deleteDetailsByIpDetails = "DELETE FROM preferred_ip_details WHERE customer_sequence_number = :customer_sequence_number and ip_details = :ip_details";

	public static final String ipDetailsExistOrNot = "select ip_details from preferred_ip_details where customer_sequence_number = :customer_sequence_number and ip_details = :ip_details";
	public static final String detailsExistByCustomerSequenceNumber = "SELECT p.customerSequenceNumber FROM PreferredIpDetails p WHERE p.customerSequenceNumber = :customerSequenceNumber";

	@Query(value = getDetailsByCustomerSequenceNumber, nativeQuery = true)
	public List<PreferredIpDetails> getDetailsByCustomerSequenceNumber(
			@Param(CUSTOMER_SEQUENCE_NUMBER) Long customerSequenceNumber);

	@Modifying
	@Query(value = deleteDetailsByCustomerSequenceNumber, nativeQuery = true)
	public void deleteDetailsByCustomerSequenceNumber(@Param(CUSTOMER_SEQUENCE_NUMBER) Long customerSequenceNumber);

	@Modifying
	@Query(value = deleteDetailsByIpDetails, nativeQuery = true)
	public void deleteDetailsByIpDetails(@Param(CUSTOMER_SEQUENCE_NUMBER) Long customerSequenceNumber,
			@Param(IP_DETAILS) String ipDetails);

	@Query(value = detailsExistByCustomerSequenceNumber)
	public List<Long> detailsExistByCustomerSequenceNumber(
			@Param("customerSequenceNumber") Long customerSequenceNumber);

	@Query(value = ipDetailsExistOrNot, nativeQuery = true)
	public Object detailsExistByIpDetails(@Param(CUSTOMER_SEQUENCE_NUMBER) Long customerSequenceNumber,
			@Param(IP_DETAILS) String ipDetails);

}
