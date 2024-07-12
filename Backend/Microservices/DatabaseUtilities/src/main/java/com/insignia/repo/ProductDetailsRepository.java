package com.insignia.repo;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insignia.entity.ProductDetails;

public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Serializable> {

	public static final String PRODUCT_SEQUENCE_NUMBER_LIST = "productSequenceNumberList";

	public static final String TENANT_ID2 = "tenantId";

	public static final String APPLICATION_ID2 = "applicationId";

	public static final String PRODUCT_SEQUENCE_NUMBER = "product_sequence_number";

	public static final String PRODUCT_ID = "product_id";

	public static final String TENANT_ID = "tenant_id";

	public static final String APPLICATION_ID = "application_id";

	public final static String getProductDetails = "select * from product_details where product_sequence_number=:product_sequence_number and application_id =:application_id and tenant_id =:tenant_id";

	public final static String getProductDetailsForCustomList = "select * from product_details where product_sequence_number In (:productSequenceNumberList)";
	public final static String getSelectedProductDetailsList = "Select * From product_details Where application_id =:application_id and tenant_id =:tenant_id and product_sequence_number In (:productSequenceNumberList)";

	public final static String fetchAddtionalProductDataList = "Select new ProductDetails(productSequenceNumber,productId,productName,description,productPerUnitCurrentPrice,productImagePath,defaultImage) from ProductDetails where applicationId =:applicationId and tenantId =:tenantId and productSequenceNumber in (:productSequenceNumberList)";

	public final static String deleteProductById = "Delete from product_details where product_sequence_number =:product_sequence_number and application_id =:application_id and tenant_id =:tenant_id";

	public final static String getSelectedProductDetailsListForCartAndOrder = "Select * From product_details Where product_sequence_number In (:productSequenceNumberList)";

	public final static String getProductFilters = "Select product_id,product_name,description from product_details where application_id =:application_id and tenant_id =:tenant_id";

	public final static String getProductDetailsByIdQuery = "select * from product_details where product_id=:product_id and application_id =:application_id and tenant_id =:tenant_id";

	@Query(value = getProductFilters, nativeQuery = true)
	List<Object[]> getProductFilters(@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	@Query(value = getSelectedProductDetailsListForCartAndOrder, nativeQuery = true)
	public List<ProductDetails> getSelectedProductDetailsListForCartAndOrder(
			@Param(PRODUCT_SEQUENCE_NUMBER_LIST) List<Long> productSequenceNumberList);

	@Modifying
	@Query(value = deleteProductById, nativeQuery = true)
	public void deleteProductById(@Param(PRODUCT_SEQUENCE_NUMBER) Long productSequenceNumber,
			@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	@Query(value = getProductDetails, nativeQuery = true)
	public Optional<ProductDetails> getProductDetails(@Param(PRODUCT_SEQUENCE_NUMBER) Long productSequenceNumber,
			@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	@Query(value = getProductDetailsForCustomList, nativeQuery = true)
	public List<ProductDetails> getCustomListProductDetails(
			@Param(PRODUCT_SEQUENCE_NUMBER_LIST) List<Long> productSequenceNumber);

	@Query(value = getSelectedProductDetailsList, nativeQuery = true)
	public List<ProductDetails> getSelectedProductDetailsList(@Param(APPLICATION_ID) String applicationId,
			@Param(TENANT_ID) String tenantId,
			@Param(PRODUCT_SEQUENCE_NUMBER_LIST) List<Long> productSequenceNumberList);

	@Query(value = fetchAddtionalProductDataList)
	public List<ProductDetails> fetchAddtionalProductDataList(@Param(APPLICATION_ID2) String applicationId,
			@Param(TENANT_ID2) String tenantId,
			@Param(PRODUCT_SEQUENCE_NUMBER_LIST) List<Long> productSequenceNumberList);

	@Query(value = getProductDetailsByIdQuery, nativeQuery = true)
	public List<ProductDetails> getProductDetailsById(@Param(PRODUCT_ID) String productId,
			@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

}
