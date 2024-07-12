package com.insignia.repo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insignia.entity.CustomerBasicDetailsEntity;

public interface CustomerBasicDetailsRepository extends JpaRepository<CustomerBasicDetailsEntity, Serializable> {

	String fetchOtpDetails = "SELECT new CustomerBasicDetailsEntity(otp, otpExpiryAt,customerSequenceNumber) FROM CustomerBasicDetailsEntity"
			+ " WHERE applicationId = :application_id AND tenantId = :tenant_id AND userId = :user_id";

	String getPassword = "select customer_password from customer_basic_details where application_id = :application_id AND tenant_id = :tenant_id AND user_id = :user_id";

	String updatePassword = "Update customer_basic_details set customer_password =:customer_password where user_id =:user_id";

	String getOtp = "select OTP, OTP_expiry_at from customer_basic_details where application_id = :application_id AND tenant_id = :tenant_id AND user_id = :user_id";

	String getCustomerAndStoreInformation = "SELECT cbd.customer_sequence_number, cbd.full_Name, cbd.registered_on, csd.store_name, csd.store_contact, csd.store_address, csd.business_type FROM customer_basic_details cbd LEFT JOIN customer_store_details csd ON cbd.customer_sequence_number = csd.customer_sequence_number";

	String getCountFromCustomerBasicDetails = "SELECT MAX(customer_sequence_number) FROM `customer_basic_details`";

	String FETCH_USER_DETAILS = "SELECT CONCAT(user_id,application_id,tenant_id) AS userName,COALESCE(customer_password,'') AS PASSWORD,cbd.customer_sequence_number,rol.role_name,rol.role_id,rol.permission1,rol.permission2, rol.permission3,rol.permission4,rol.role_approved_date, rol.role_revoked_date, rol.permission_change_date,rol.updated_permissions,cbd.OTP,cbd.is_otp_authenticated,cbd.OTP_generated_at,cbd.OTP_expiry_at FROM customer_basic_details AS cbd LEFT JOIN roles_and_permissions AS rol ON cbd.customer_sequence_number=rol.customer_sequence_number WHERE CONCAT(user_id,application_id,tenant_id)=:userName or (user_id=:userName and customer_password='')";

	String UPDATE_OTP_QUERY = "Update customer_basic_details c set c.OTP = null ,c.otp_generated_at = null ,c.otp_expiry_at= null where c.customer_sequence_number =:customerSequence";

	String updateQueryForOtp = "update customer_basic_details SET OTP =:OTP, otp_generated_at =:otp_generated_at, otp_expiry_at =:otp_expiry_at where application_id =:application_id and tenant_id =:tenant_id and user_id = :user_id";

	String getCustomerDetails = "select * from customer_basic_details where customer_sequence_number =:customer_sequence_number and application_id =:application_id and tenant_id =:tenant_id";

	String deleteCustomerDetails = "delete from customer_basic_details where customer_sequence_number =:customer_sequence_number and application_id =:application_id and tenant_id =:tenant_id";
	
	
	@Modifying
	@Query(value = deleteCustomerDetails, nativeQuery = true)
	public void deleteCustomerDetails(
			@Param("customer_sequence_number") Long customerSequenceNumber,
			@Param("application_id") String applicationId, @Param("tenant_id") String tenantId);

	
	@Query(value = getCustomerDetails, nativeQuery = true)
	public Optional<CustomerBasicDetailsEntity> getCustomerDetails(
			@Param("customer_sequence_number") Long customerSequenceNumber,
			@Param("application_id") String applicationId, @Param("tenant_id") String tenantId);

	String getAllCustomerDetails = "select * from customer_basic_details where application_id =:application_id and tenant_id =:tenant_id";

	@Query(value = getAllCustomerDetails, nativeQuery = true)
	public List<CustomerBasicDetailsEntity> getAllCustomerDetails(
			@Param("application_id") String applicationId, @Param("tenant_id") String tenantId);

	@Modifying
	@Query(value = updateQueryForOtp, nativeQuery = true)
	public void updateQueryForOtp(@Param("application_id") String applicationId, @Param("tenant_id") String tenantId,
			@Param("user_id") String userId, @Param("OTP") String otp, @Param("otp_generated_at") Date otpGeneratedAt,
			@Param("otp_expiry_at") Date otpExpiryAt);

	@Query(value = getCountFromCustomerBasicDetails, nativeQuery = true)
	Long getCountFromCustomerBasicDetails();

	@Modifying
	@Query(value = updatePassword, nativeQuery = true)
	void updatePassword(@Param("customer_password") String customerPassword, @Param("user_id") String userId);

	@Query(value = fetchOtpDetails)
	CustomerBasicDetailsEntity fetchOtpDetails(@Param("application_id") String applicationId,
			@Param("tenant_id") String tenantId, @Param("user_id") String userId);

	@Query(value = FETCH_USER_DETAILS, nativeQuery = true)
	List<Object[]> fetchUserDetails(@Param("userName") String userName);

	@Query(value = getPassword, nativeQuery = true)
	Object[] getPassword(@Param("application_id") String applicationId, @Param("tenant_id") String tenantId,
			@Param("user_id") String userId);

	@Query(value = getOtp, nativeQuery = true)
	List<Object[]> getOtp(@Param("application_id") String applicationId, @Param("tenant_id") String tenantId,
			@Param("user_id") String userId);

	@Query(value = getCustomerAndStoreInformation, nativeQuery = true)
	List<Object[]> getCustomerAndStoreInformation();

	@Modifying
	@Query(value = UPDATE_OTP_QUERY, nativeQuery = true)
	void updateOTPPostValidation(@Param("customerSequence") Long customerSequence);
}
