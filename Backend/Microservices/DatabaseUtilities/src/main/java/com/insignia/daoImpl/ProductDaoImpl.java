package com.insignia.daoImpl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.insignia.constants.ProductValidatorConstants;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.daoInterface.ProductDaoInterface;
import com.insignia.entity.ProductDetails;
import com.insignia.entity.ProductFamilyLink;
import com.insignia.repo.ProductAdditionalProductsRepository;
import com.insignia.repo.ProductDetailsRepository;
import com.insignia.repo.ProductFamilyLinkRepository;
import com.insignia.repo.ProductSubCategoryRepo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class ProductDaoImpl implements ProductDaoInterface {

	private static final String AND2 = "' and ";
	private static final String AND_TENANT_ID = "' and tenant_id = '";
	private static final String APPLICATION_ID = " application_id = '";
	private static final String TRUE = "true";
	private static final String OPEN_BRACKET = "(";
	private static final String EMPTY_STRING = "";

	private static final String OR = " OR ";

	private static final String AND = " and ";

	private static final String STR2 = "'";

	private static final String STR = "%'";

	private static final String LOWER = "lower(";

	private static final String CLOSING_ROUND_BRACE = ")";

	private static final String PRODUCT_FAMILY_IN = "product_family IN (";

	private static final String PRODUCT_SEQUENCE_NUMBER_IN = "product_sequence_number IN (";

	private static final String SELECT_PRODUCT_SEQUENCE_NUMBER_FROM_PRODUCT_FAMILY_WHERE_PRODUCT_FAMILY_SEQUENCE_NUMBER_IN = "SELECT product_sequence_number FROM product_family_link WHERE product_family_sequence_number IN (";

	private static final String COLOURS_LIKE = LOWER + "colours" + CLOSING_ROUND_BRACE + " LIKE '%";

	private static final String MATERIALS_LIKE = LOWER + "materials" + CLOSING_ROUND_BRACE + " " + "LIKE '%";
	private static final String PRODUCT_FAMILY_LIKE = LOWER + "product_family" + CLOSING_ROUND_BRACE + " = '%";

	private static final String PRODUCT_BRAND_LIKE = LOWER + "product_brand" + CLOSING_ROUND_BRACE + " LIKE '%";

	private static final String PRODUCT_CATALOGUE_LIKE = LOWER + "product_catalogue" + CLOSING_ROUND_BRACE + " LIKE '%";

	private static final String SUBCATEGORY_ID_IN = "subcategory_id IN(";

	private static final String PRODUCT_PER_UNIT_CURRENT_PRICE2 = "product_per_unit_current_price >= '";

	private static final String PRODUCT_PER_UNIT_CURRENT_PRICE = "product_per_unit_current_price <= '";

	private static final String AVAILABILITY = "product_quantity > 0";
	private static final String SOME_OF_KIND = "some_of_a_kind = true";
	private static final String SALES_ITEM = "sale_items = true";
	private static final String QUICK_SHIP = "quick_ship = true";
	private static final String CLOSE_OUT = "close_out = true";
	private static final String ONE_OF_KIND = "one_of_kind = true";

	private static final String NEW_ARRIVAL = "new_arrival = 1";

	private static final String FEATURED_PRODUCT = "featured_product = 1";

	private static final String TOP_SELLING_PRODUCT = "top_selling_product = 1";

	private static final String BEST_SELLER = "best_seller = 1";

	private static final String ORDER_BY = " ORDER BY ";

	private static final String ORDER_BY_PRODUCT_ID = " product_id ASC";

	private static final String SELECT_FROM_PRODUCT_DETAILS_WHERE = "select * from product_details where";

	@Autowired
	private ProductDetailsRepository productRepo;

	@Autowired
	private ProductSubCategoryRepo productSubCategoryRepo;

	@Autowired
	private ProductAdditionalProductsRepository productAdditionalProductsRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private ProductFamilyLinkRepository productFamilyLinkRepository;

	@Modifying
	@Transactional(rollbackOn = InvalidInputParametersException.class)
	@Override
	public ProductDetails addProduct(ProductDetails productRequest) throws InvalidInputParametersException {

		return productRepo.save(productRequest);

	}

	@Override
	public boolean findByProductSubCategory(Long subcategoryId) {

		return productSubCategoryRepo.existsById(subcategoryId);
	}

	@Override
	public ProductDetails updateProduct(ProductDetails request) throws InvalidInputParametersException {

		return entityManager.merge(request);

	}

	@Override
	public void deleteProductById(Long productSequenceNumber, String applicationId, String tenantId) {
		productRepo.deleteProductById(productSequenceNumber, applicationId, tenantId);

	}

	@Override
	public Optional<ProductDetails> getProductDetails(Long productSequenceNumber, String applicationId,
			String tenantId) {

		return productRepo.getProductDetails(productSequenceNumber, applicationId, tenantId);
	}

	@Override
	public Page<ProductDetails> getAllProducts(Pageable pageRequest) {
		return productRepo.findAll(pageRequest);

	}

	@Override
	public List<ProductDetails> getSelectedProductDetailsList(String applicationId, String tenantId,
			List<Long> productSequenceNumberList) {

		return productRepo.getSelectedProductDetailsList(applicationId, tenantId, productSequenceNumberList);
	}

	@Override
	public HashMap filterProducts(String applicationId, String tenantId, Map<String, Object> filterCriteria,
			Pageable pageRequest) {
		StringBuilder query = new StringBuilder();
		query.append(SELECT_FROM_PRODUCT_DETAILS_WHERE).append(APPLICATION_ID).append(applicationId)
				.append(AND_TENANT_ID).append(tenantId).append(AND2);

		String and = EMPTY_STRING;
		int size = 0;

		if (filterCriteria.get(ProductValidatorConstants.filter_colour) != null) {
			List<String> coloursList = (List<String>) filterCriteria.get(ProductValidatorConstants.filter_colour);
			query.append(and + OPEN_BRACKET);
			int count = 0;
			size = coloursList.size();
			for (String colour : coloursList) {
				query.append(COLOURS_LIKE).append(colour).append(STR);

				if (count < (size - 1)) {
					query.append(OR);
					count++;
				}
				if (and.isEmpty()) {
					and = AND;
				}
			}
			query.append(CLOSING_ROUND_BRACE);
		}
		if (filterCriteria.get(ProductValidatorConstants.filter_material) != null) {
			List<String> materialList = (List<String>) filterCriteria.get(ProductValidatorConstants.filter_material);
			query.append(and + OPEN_BRACKET);
			int count = 0;
			size = materialList.size();
			for (String material : materialList) {
				query.append(MATERIALS_LIKE).append(material).append(STR);
				if (count < (size - 1)) {
					query.append(OR);
					count++;
				}
				if (and.isEmpty()) {
					and = AND;
				}
			}
			query.append(CLOSING_ROUND_BRACE);
		}
		if (filterCriteria.get(ProductValidatorConstants.filter_subcategory) != null) {
			query.append(and);
			query.append(SUBCATEGORY_ID_IN).append(filterCriteria.get(ProductValidatorConstants.filter_subcategory)
					.toString().replace("[", "").replace("]", "").replace(", ", ",")).append(CLOSING_ROUND_BRACE);
			if (and.isEmpty()) {
				and = AND;
			}
		}

		if (filterCriteria.get(ProductValidatorConstants.filter_product_family) != null) {
			List<Integer> productFamilyList = (List<Integer>) filterCriteria
					.get(ProductValidatorConstants.filter_product_family);
			if (!productFamilyList.isEmpty()) {
				query.append(and);
				query.append(" (");
				query.append(PRODUCT_FAMILY_IN);
				for (int i = 0; i < productFamilyList.size(); i++) {
					query.append(productFamilyList.get(i));
					if (i < productFamilyList.size() - 1) {
						query.append(", ");
					}
				}
				query.append(CLOSING_ROUND_BRACE);
				query.append(" OR ");
				query.append(PRODUCT_SEQUENCE_NUMBER_IN);
				query.append(
						SELECT_PRODUCT_SEQUENCE_NUMBER_FROM_PRODUCT_FAMILY_WHERE_PRODUCT_FAMILY_SEQUENCE_NUMBER_IN);
				for (int i = 0; i < productFamilyList.size(); i++) {
					query.append(productFamilyList.get(i));
					if (i < productFamilyList.size() - 1) {
						query.append(", ");
					}
				}
				query.append(CLOSING_ROUND_BRACE);
				query.append(CLOSING_ROUND_BRACE);
				query.append(CLOSING_ROUND_BRACE);
				and = AND;
			}
		}

		if (filterCriteria.get(ProductValidatorConstants.filter_product_brand) != null) {

			List<String> productBrandList = (List<String>) filterCriteria
					.get(ProductValidatorConstants.filter_product_brand);
			query.append(and + OPEN_BRACKET);
			int count = 0;
			size = productBrandList.size();
			for (String productBrand : productBrandList) {
				query.append(PRODUCT_BRAND_LIKE).append(productBrand).append(STR);
				if (count < (size - 1)) {
					query.append(OR);
					count++;
				}
				if (and.isEmpty()) {
					and = AND;
				}
			}
			query.append(CLOSING_ROUND_BRACE);
		}

		if (filterCriteria.get(ProductValidatorConstants.filter_product_catalogue) != null) {
			List<String> productCatalogueList = (List<String>) filterCriteria
					.get(ProductValidatorConstants.filter_product_catalogue);
			query.append(and + OPEN_BRACKET);
			int count = 0;
			size = productCatalogueList.size();
			for (String productCatalogue : productCatalogueList) {
				query.append(PRODUCT_CATALOGUE_LIKE).append(productCatalogue).append(STR);
				if (count < (size - 1)) {
					query.append(OR);
					count++;
				}
				if (and.isEmpty()) {
					and = AND;
				}
			}
			query.append(CLOSING_ROUND_BRACE);
		}

		if (filterCriteria.get(ProductValidatorConstants.filter_availability) != null) {
			query.append(and);
			query.append(AVAILABILITY).append(filterCriteria.get(ProductValidatorConstants.filter_availability)
					.toString().replace(TRUE, EMPTY_STRING));
			if (and.isEmpty()) {
				and = AND;
			}
		}

		if (filterCriteria.get(ProductValidatorConstants.filter_someOfAKind) != null) {
			query.append(and);
			query.append(SOME_OF_KIND);
			if (and.isEmpty()) {
				and = AND;
			}
		}

		if (filterCriteria.get(ProductValidatorConstants.filter_saleItems) != null) {
			query.append(and);
			query.append(SALES_ITEM);
			if (and.isEmpty()) {
				and = AND;
			}
		}

		if (filterCriteria.get(ProductValidatorConstants.filter_closeOut) != null) {
			query.append(and);
			query.append(CLOSE_OUT);
			if (and.isEmpty()) {
				and = AND;
			}
		}

		if (filterCriteria.get(ProductValidatorConstants.filter_quickShip) != null) {
			query.append(and);
			query.append(QUICK_SHIP);
			if (and.isEmpty()) {
				and = AND;
			}
		}

		if (filterCriteria.get(ProductValidatorConstants.filter_oneOfKind) != null) {
			query.append(and);
			query.append(ONE_OF_KIND);
			if (and.isEmpty()) {
				and = AND;
			}
		}

		if (filterCriteria.get(ProductValidatorConstants.filter_new_arrival) != null) {
			query.append(and);
			query.append(NEW_ARRIVAL).append(filterCriteria.get(ProductValidatorConstants.filter_new_arrival).toString()
					.replace(TRUE, EMPTY_STRING));
			if (and.isEmpty()) {
				and = AND;
			}
		}
		if (filterCriteria.get(ProductValidatorConstants.filter_featured_product) != null) {
			query.append(and);
			query.append(FEATURED_PRODUCT).append(filterCriteria.get(ProductValidatorConstants.filter_featured_product)
					.toString().replace(TRUE, EMPTY_STRING));
			if (and.isEmpty()) {
				and = AND;
			}
		}
		if (filterCriteria.get(ProductValidatorConstants.filter_top_selling_product) != null) {
			query.append(and);
			query.append(TOP_SELLING_PRODUCT).append(filterCriteria
					.get(ProductValidatorConstants.filter_top_selling_product).toString().replace(TRUE, EMPTY_STRING));
			if (and.isEmpty()) {
				and = AND;
			}
		}
		if (filterCriteria.get(ProductValidatorConstants.filter_best_seller) != null) {
			query.append(and);
			query.append(BEST_SELLER).append(filterCriteria.get(ProductValidatorConstants.filter_best_seller).toString()
					.replace(TRUE, EMPTY_STRING));
			if (and.isEmpty()) {
				and = AND;
			}
		}

		if (filterCriteria.get(ProductValidatorConstants.filter_min_price) != null) {
			query.append(and);
			query.append(PRODUCT_PER_UNIT_CURRENT_PRICE2)
					.append(filterCriteria.get(ProductValidatorConstants.filter_min_price).toString()).append(STR2);
			if (and.isEmpty()) {
				and = AND;
			}
		}
		if (filterCriteria.get(ProductValidatorConstants.filter_max_price) != null) {
			query.append(and);
			query.append(PRODUCT_PER_UNIT_CURRENT_PRICE)
					.append(filterCriteria.get(ProductValidatorConstants.filter_max_price).toString()).append(STR2);
			if (and.isEmpty()) {
				and = AND;
			}
		}

		if (filterCriteria.get(ProductValidatorConstants.filter_search_condition) != null) {
			query.append(and);
			query.append(filterCriteria.get(ProductValidatorConstants.filter_search_condition));
			if (and.isEmpty()) {
				and = AND;
			}
		}

		query.append(ORDER_BY);
		if (filterCriteria.get(ProductValidatorConstants.filter_sorting_direction) != null)
			query.append(filterCriteria.get(ProductValidatorConstants.filter_sorting_direction));
		else
			query.append(ORDER_BY_PRODUCT_ID);

		return getResult(query, pageRequest);
	}

	private HashMap getResult(StringBuilder query, Pageable pageRequest) {

		List<ProductDetails> productDetails = Collections.EMPTY_LIST;
		HashMap productDetailsMap = new HashMap();
		String countQuery = query.toString().replace("*", "COUNT(*)");
		Query nativeQuery = entityManager.createNativeQuery(countQuery);
		Long totalRecords = (Long) nativeQuery.getSingleResult();
		if (totalRecords != null && totalRecords > 0) {
			query.append(" limit ").append(pageRequest.getPageSize()).append(" offset ")
					.append(pageRequest.getOffset());
			productDetails = entityManager.createNativeQuery(query.toString(), ProductDetails.class).getResultList();
		}
		productDetailsMap.put("totalCount", totalRecords);
		productDetailsMap.put("records", productDetails);
		return productDetailsMap;
	}

	@Override
	public void deleteAdditionalProduct(Long productSquenceNumber, List<Long> additionalProduct) {

		productAdditionalProductsRepository.deleteAdditionalProducts(productSquenceNumber, additionalProduct);
	}

	@Override
	public List<Object[]> getProductFilters(String applicationId, String tenantId) {
		return productRepo.getProductFilters(applicationId, tenantId);
	}

	@Override
	public List<ProductDetails> fetchAddtionalProductDataList(String applicationId, String tenantId,
			List<Long> additionalProductsList) {

		return productRepo.fetchAddtionalProductDataList(applicationId, tenantId, additionalProductsList);
	}

	@Override
	public Optional<ProductDetails> findByIdForCart(Long productSequenceNumber) {

		return productRepo.findById(productSequenceNumber);
	}

	@Override
	public List<ProductDetails> getSelectedProductDetailsListForCartAndOrder(List<Long> productSequenceNumberList) {

		return productRepo.getSelectedProductDetailsListForCartAndOrder(productSequenceNumberList);
	}

	@Override
	public List<ProductDetails> getProductDetailsById(String productId, String applicationId, String tenantId) {

		return productRepo.getProductDetailsById(productId, applicationId, tenantId);

	}

	@Override
	public ProductFamilyLink existByProductSequenceNumber(Long productSequenceNumber) {

		return productFamilyLinkRepository.existByProductSequenceNumber(productSequenceNumber);
	}

	@Override
	public ProductFamilyLink saveProductFamilyLink(ProductFamilyLink productFamilyLink) {

		return productFamilyLinkRepository.save(productFamilyLink);
	}
	
	@Override
	public void deleteProductFamilyLink(Long productSequenceNumber, List<Integer> removedProductFamilyList) {
		productFamilyLinkRepository.deleteProductFamilyLink(productSequenceNumber, removedProductFamilyList);
	}
}
