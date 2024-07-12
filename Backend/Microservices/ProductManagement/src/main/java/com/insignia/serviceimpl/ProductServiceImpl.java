package com.insignia.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.insignia.constant.ProductConstants;
import com.insignia.constants.CommonConstant;
import com.insignia.constants.ProductValidatorConstants;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.ProductDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.ProductAdditionalProducts;
import com.insignia.entity.ProductDetails;
import com.insignia.entity.ProductFamilyLink;
import com.insignia.model.ProductDetailsResponse;
import com.insignia.model.ProductFilterCriteria;
import com.insignia.model.ProductFilterResponse;
import com.insignia.model.ProductManagementRequest;
import com.insignia.model.ProductRequest;
import com.insignia.model.ProductResponse;
import com.insignia.model.SearchBy;
import com.insignia.service.ProductServiceInterface;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductServiceInterface {

	private final ProductDaoInterface productDaoInterface;
	private final TokenDaoInterface tokenDao;

	@Transactional
	@Override
	public ProductResponse addProduct(ProductManagementRequest productManagementRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(productManagementRequest.getCustomerSequenceNumber(),
				productManagementRequest.getExpirationDuration());
		ProductResponse productResponse = new ProductResponse();

		ProductDetails productEntity = new ProductDetails();
		boolean isValueUpdated = false;

		ProductRequest productRequest = productManagementRequest.getProductRequest();

		if (!productDaoInterface.findByProductSubCategory(productRequest.getSubcategoryId())) {
			throw new InvalidInputParametersException(ProductValidatorConstants.subcategoryIdIsNotFoundErrorCode,
					ProductValidatorConstants.subcategoryIdIsNotFoundMessage);
		}
		productEntity.setProductId(productRequest.getProductId());
		productEntity.setProductName(productRequest.getProductName());
		productEntity.setDescription(productRequest.getDescription());
		productEntity.setMeasuringUnit(productRequest.getMeasuringUnit());
		productEntity.setMeasuringQuantity(productRequest.getMeasuringQuantity());
		productEntity.setSubcategoryId(productRequest.getSubcategoryId());
		productEntity.setProductImagePath(productRequest.getProductImagePath());
		productEntity.setProductPerUnitActualPrice(productRequest.getProductPerUnitActualPrice());
		productEntity.setProductPerUnitCurrentPrice(productRequest.getProductPerUnitCurrentPrice());
		productEntity.setLength(productRequest.getProductLength());
		productEntity.setWidth(productRequest.getWidth());
		productEntity.setHeight(productRequest.getHeight());
		productEntity.setDimensionUnit(productRequest.getDimensionUnit());
		productEntity.setMaterials(productRequest.getMaterials());
		productEntity.setColours(productRequest.getColours());
		productEntity.setHexCode(productRequest.getHexCode());
		productEntity.setProductQuantity(productRequest.getProductQuantity());
		productEntity.setProductBrand(productRequest.getProductBrand());
		productEntity.setProductCatalogue(productRequest.getProductCatalogue());
		productEntity.setNewArrival(productRequest.getNewArrival());
		productEntity.setBestSeller(productRequest.getBestSeller());
		productEntity.setFeaturedProduct(productRequest.getFeaturedProduct());
		productEntity.setTopSellingProduct(productRequest.getTopSellingProduct());
		productEntity.setProductFinish(productRequest.getProductFinish());
		productEntity.setProdWholesaleTier1Price(productRequest.getProdWholesaleTier1Price());
		productEntity.setProdWholesaleTier2Price(productRequest.getProdWholesaleTier2Price());
		productEntity.setProdWholesaleTier1SalePrice(productRequest.getProdWholesaleTier1SalePrice());
		productEntity.setProdWholesaleTier2SalePrice(productRequest.getProdWholesaleTier2SalePrice());
		productEntity.setProdRetailTier1Price(productRequest.getProdRetailTier1Price());
		productEntity.setProdRetailTier2Price(productRequest.getProdRetailTier2Price());
		productEntity.setProdRetailTier1SalePrice(productRequest.getProdRetailTier1SalePrice());
		productEntity.setProdRetailTier2SalePrice(productRequest.getProdRetailTier2SalePrice());
		productEntity.setDefaultImage(productRequest.getDefaultImage());
		productEntity.setApplicationId(productRequest.getApplicationId());
		productEntity.setTenantId(productRequest.getTenantId());
		productEntity.setSomeOfAKind(productRequest.getSomeOfAKind());
		productEntity.setSaleItems(productRequest.getSaleItems());
		productEntity.setCloseOut(productRequest.getCloseOut());
		productEntity.setQuickShip(productRequest.getQuickShip());
		productEntity.setEta(productRequest.getEta());
		productEntity.setOneOfKind(productRequest.getOneOfKind());
		productEntity.setIsAnchorProduct(productRequest.getIsAnchorProduct());
		productEntity.setIsPackageProduct(productRequest.getIsPackageProduct());

		productEntity = productDaoInterface.addProduct(productEntity);

		if (ProductConstants.newDefaultId.equals(productRequest.getProductId())) {

			String productId = String.format(CommonConstant.paddingLength, productEntity.getProductSequenceNumber());

			productEntity.setProductId(productId);
			isValueUpdated = true;
		}
		List<Long> additionalProductList = productRequest.getAdditionalProductList();
		if (additionalProductList != null && !additionalProductList.isEmpty()) {
			List<ProductAdditionalProducts> productAdditionalProductsList = new ArrayList<>();
			for (Long additionalProduct : additionalProductList) {
				ProductAdditionalProducts productAdditionalProducts = new ProductAdditionalProducts();
				productAdditionalProducts.setAdditionalProduct(additionalProduct);
				productAdditionalProducts.setProductSequenceNumber(productEntity.getProductSequenceNumber());
				productAdditionalProductsList.add(productAdditionalProducts);
			}

			productEntity.setProductAdditionalProducts(productAdditionalProductsList);

			isValueUpdated = true;
		}

		List<Integer> productFamilyList = productRequest.getProductFamilyList();
		if (productFamilyList != null && productFamilyList.size() > 0) {

			if (productRequest.getIsAnchorProduct() && productFamilyList.size() > 1) {
				throw new InvalidInputParametersException(
						ProductValidatorConstants.duplicateproductFamilyEntryErrorCode,
						ProductValidatorConstants.duplicateproductFamilyEntryErrorMessage);
			}

			if (!productRequest.getIsAnchorProduct() && !productRequest.getIsPackageProduct()) {
				productEntity.setProductFamily(productFamilyList.get(0));
			}

			if (productRequest.getIsAnchorProduct() || productRequest.getIsPackageProduct()) {
				List<ProductFamilyLink> productFamilyLinks = new ArrayList<>();

				for (Integer productFamilySequenceNumber : productFamilyList) {
					ProductFamilyLink productFamilyLink = new ProductFamilyLink();
					productFamilyLink.setProductFamilySequenceNumber(productFamilySequenceNumber);
					productFamilyLink.setProductSequenceNumber(productEntity.getProductSequenceNumber());

					productFamilyLinks.add(productFamilyLink);
				}
				productEntity.setProductFamilyLinkList(productFamilyLinks);
				isValueUpdated = true;

			}
		}

		if (isValueUpdated) {

			productEntity = productDaoInterface.addProduct(productEntity);
		}

		productResponse.setProductDetailsResponse(
				Collections.singletonList(createResponseForProductDetailsEntity(productEntity)));
		createAdditionalProductResponse(productResponse, productEntity);

		return productResponse;
	}

	@Transactional
	@Override
	public ProductResponse updateProduct(ProductManagementRequest productManagementRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(productManagementRequest.getCustomerSequenceNumber(),
				productManagementRequest.getExpirationDuration());
		ProductResponse productResponse = new ProductResponse();
		ProductRequest productRequest = productManagementRequest.getProductRequest();

		if (productRequest.getProductSequenceNumber() == null) {
			throw new InvalidInputParametersException(ProductValidatorConstants.validateProductSequenceNumberErrorCode,
					ProductValidatorConstants.validateProductSequenceNumberMessage);
		}

		Optional<ProductDetails> optionproduct = productDaoInterface.getProductDetails(
				productRequest.getProductSequenceNumber(), productRequest.getApplicationId(),
				productRequest.getTenantId());

		if (optionproduct.isPresent()) {

			ProductDetails productEntity = optionproduct.get();

			if (productRequest.isProductIdUpdated()) {
				productEntity.setProductId(productRequest.getProductId());
			}
			if (productRequest.isProductNameUpdated()) {
				productEntity.setProductName(productRequest.getProductName());
			}
			if (productRequest.isDescriptionUpdated()) {
				productEntity.setDescription(productRequest.getDescription());
			}
			if (productRequest.isMeasuringUnitUpdated()) {
				productEntity.setMeasuringUnit(productRequest.getMeasuringUnit());
			}
			if (productRequest.isMeasuringQuantityUpdated()) {
				productEntity.setMeasuringQuantity(productRequest.getMeasuringQuantity());
			}
			if (productRequest.isSubcategoryIdUpdated()) {
				if (!productDaoInterface.findByProductSubCategory(productRequest.getSubcategoryId())) {
					throw new InvalidInputParametersException(
							ProductValidatorConstants.subcategoryIdIsNotFoundErrorCode,
							ProductValidatorConstants.subcategoryIdIsNotFoundMessage);
				}

				productEntity.setSubcategoryId(productRequest.getSubcategoryId());
			}
			if (productRequest.isProductImagePathUpdated()) {
				productEntity.setProductImagePath(productRequest.getProductImagePath());
			}
			if (productRequest.isProductPerUnitActualPriceUpdated()) {
				productEntity.setProductPerUnitActualPrice(productRequest.getProductPerUnitActualPrice());
			}
			if (productRequest.isProductPerUnitCurrentPriceUpdated()) {
				productEntity.setProductPerUnitCurrentPrice(productRequest.getProductPerUnitCurrentPrice());
			}
			if (productRequest.isLengthUpdated()) {
				productEntity.setLength(productRequest.getProductLength());
			}
			if (productRequest.isHeightUpdated()) {
				productEntity.setHeight(productRequest.getHeight());
			}
			if (productRequest.isWidthUpdated()) {
				productEntity.setWidth(productRequest.getWidth());
			}
			if (productRequest.isDefaultImageUpdated()) {
				productEntity.setDefaultImage(productRequest.getDefaultImage());
			}
			if (productRequest.isDimensionUnitUpdated()) {
				productEntity.setDimensionUnit(productRequest.getDimensionUnit());
			}
			if (productRequest.isMaterialsUpdated()) {
				productEntity.setMaterials(productRequest.getMaterials());
			}
			if (productRequest.isColoursUpdated()) {
				productEntity.setColours(productRequest.getColours());
			}
			if (productRequest.isHexCodeUpdated()) {
				productEntity.setHexCode(productRequest.getHexCode());
			}
			if (productRequest.isProductBrandUpdated()) {
				productEntity.setProductBrand(productRequest.getProductBrand());
			}
			if (productRequest.isProductCatalogueUpdated()) {
				productEntity.setProductCatalogue(productRequest.getProductCatalogue());
			}
			if (productRequest.isNewArrivalUpdated()) {
				productEntity.setNewArrival(productRequest.getNewArrival());
			}
			if (productRequest.isTopSellingProductUpdated()) {
				productEntity.setTopSellingProduct(productRequest.getTopSellingProduct());
			}
			if (productRequest.isBestSellerUpdated()) {
				productEntity.setBestSeller(productRequest.getBestSeller());
			}
			if (productRequest.isFeaturedProductUpdated()) {
				productEntity.setFeaturedProduct(productRequest.getFeaturedProduct());
			}
			if (productRequest.isProductFinishUpdated()) {
				productEntity.setProductFinish(productRequest.getProductFinish());
			}
			if (productRequest.isProductWholeTier1PriceUpdated()) {
				productEntity.setProdWholesaleTier1Price(productRequest.getProdWholesaleTier1Price());
			}
			if (productRequest.isProductWholeTier2PriceUpdated()) {
				productEntity.setProdWholesaleTier2Price(productRequest.getProdWholesaleTier2Price());
			}
			if (productRequest.isProductWholeTier1SalePriceUpdated()) {
				productEntity.setProdWholesaleTier1SalePrice(productRequest.getProdWholesaleTier1SalePrice());
			}
			if (productRequest.isProductWholeTier2SalePriceUpdated()) {
				productEntity.setProdWholesaleTier2SalePrice(productRequest.getProdWholesaleTier2SalePrice());
			}
			if (productRequest.isProductRetailTier1PriceUpdated()) {
				productEntity.setProdRetailTier1Price(productRequest.getProdRetailTier1Price());
			}
			if (productRequest.isProductRetailTier2PriceUpdated()) {
				productEntity.setProdRetailTier2Price(productRequest.getProdRetailTier2Price());
			}
			if (productRequest.isProdRetailTier1SalePriceUpdated()) {
				productEntity.setProdRetailTier1SalePrice(productRequest.getProdRetailTier1SalePrice());
			}
			if (productRequest.isProdRetailTier2SalePriceUpdated()) {
				productEntity.setProdRetailTier2SalePrice(productRequest.getProdRetailTier2SalePrice());
			}

			if (productRequest.isSomeOfAKindUpdated()) {
				productEntity.setSomeOfAKind(productRequest.getSomeOfAKind());
			}
			if (productRequest.isSaleItemsUpdated()) {
				productEntity.setSaleItems(productRequest.getSaleItems());
			}
			if (productRequest.isCloseOutUpdated()) {
				productEntity.setCloseOut(productRequest.getCloseOut());
			}
			if (productRequest.isQuickShipUpdated()) {
				productEntity.setQuickShip(productRequest.getQuickShip());
			}
			if (productRequest.isOneOfKindUpdated()) {
				productEntity.setOneOfKind(productRequest.getOneOfKind());
			}
			if (productRequest.isEtaUpdated()) {
				productEntity.setEta(productRequest.getEta());
			}

			List<Long> removedAdditionalProductList = productRequest.getRemovedAdditionalProductList();
			if (removedAdditionalProductList != null && !removedAdditionalProductList.isEmpty()) {
				productDaoInterface.deleteAdditionalProduct(productRequest.getProductSequenceNumber(),
						removedAdditionalProductList);
			}			

			List<Long> additionalProductList = productRequest.getAdditionalProductList();
			if (additionalProductList != null && !additionalProductList.isEmpty()) {
				for (Long additionalProduct : additionalProductList) {
					ProductAdditionalProducts productAdditionalProducts = new ProductAdditionalProducts();
					productAdditionalProducts.setAdditionalProduct(additionalProduct);
					productAdditionalProducts.setProductSequenceNumber(productEntity.getProductSequenceNumber());
					productEntity.getProductAdditionalProducts().add(productAdditionalProducts);
				}
			}

			List<Integer> removedProductFamilyList = productRequest.getRemovedProductFamilyList();
			if (removedProductFamilyList != null && !removedProductFamilyList.isEmpty()) {
				productDaoInterface.deleteProductFamilyLink(productRequest.getProductSequenceNumber(),
						removedProductFamilyList);
			}
			
			if (productRequest.isAnchorProductUpdated() && productRequest.getIsAnchorProduct()) {
				productEntity.setIsAnchorProduct(productRequest.getIsAnchorProduct());

				List<ProductFamilyLink> productFamilyLinkList = productEntity.getProductFamilyLinkList();
				if (productFamilyLinkList != null && !productFamilyLinkList.isEmpty()) {
					for (ProductFamilyLink productFamilyLink : productFamilyLinkList) {
						if (productFamilyLink.getProductSequenceNumber() == productEntity.getProductSequenceNumber()) {
							throw new InvalidInputParametersException(
									ProductValidatorConstants.duplicateproductFamilyEntryErrorCode,
									ProductValidatorConstants.duplicateproductFamilyEntryErrorMessage);
						}
					}
				}
			}

			if (productRequest.isPackageProductUpdated()) {
				productEntity.setIsPackageProduct(productRequest.getIsPackageProduct());
			}

			if (productRequest.isProductFamilyUpdated() && !productRequest.getProductFamilyList().isEmpty()) {
				if (!productRequest.getIsAnchorProduct() && !productRequest.getIsPackageProduct()) {
					productEntity.setProductFamily(productRequest.getProductFamilyList().get(0));
				}

				else {

					List<Integer> productFamilyList = productRequest.getProductFamilyList();
					List<ProductFamilyLink> productFamilyLinkList = productEntity.getProductFamilyLinkList();
					if (productFamilyLinkList != null && !productFamilyLinkList.isEmpty()) {
						for (Integer productFamilySequenceNumber : productFamilyList) {

							for (ProductFamilyLink productFamilyLink : productFamilyLinkList) {
								if (productFamilySequenceNumber == productFamilyLink.getProductFamilySequenceNumber()) {
									throw new InvalidInputParametersException(
											ProductValidatorConstants.duplicateproductFamilyErrorCode,
											ProductValidatorConstants.duplicateproductFamilyErrorMessage);
								}
							}

							populateNewProductFamilyLinkEntity(productEntity).setProductFamilySequenceNumber(productFamilySequenceNumber);

						}
					}

				}
			} else {
				if ((productRequest.isAnchorProductUpdated() && productRequest.getIsAnchorProduct()
						|| productRequest.isPackageProductUpdated() && productRequest.getIsPackageProduct())
						&& productEntity.getProductFamily() != null) {
					populateNewProductFamilyLinkEntity(productEntity);
				}
			}

			productResponse.setProductDetailsResponse(Collections.singletonList(
					createResponseForProductDetailsEntity(productDaoInterface.updateProduct(productEntity))));
			createAdditionalProductResponse(productResponse, productEntity);
			return productResponse;

		} else {
			throw new InvalidInputParametersException(ProductValidatorConstants.productDetailsIsNotFoundErrorCode,
					ProductValidatorConstants.productDetailsIsNotFoundMessage);
		}

	}

	private ProductFamilyLink populateNewProductFamilyLinkEntity(ProductDetails productEntity) {
		ProductFamilyLink productFamilyLink = new ProductFamilyLink();
		productFamilyLink.setProductFamilySequenceNumber(productEntity.getProductFamily());
		productFamilyLink.setProductSequenceNumber(productEntity.getProductSequenceNumber());
		productEntity.getProductFamilyLinkList().add(productFamilyLink);
		
		return productFamilyLink;
	}

	@Transactional
	@Override
	public void deleteProductById(ProductManagementRequest productManagementRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(productManagementRequest.getCustomerSequenceNumber(),
				productManagementRequest.getExpirationDuration());

		ProductRequest productRequest = productManagementRequest.getProductRequest();
		Optional<ProductDetails> productDetails = productDaoInterface.getProductDetails(
				productRequest.getProductSequenceNumber(), productRequest.getApplicationId(),
				productRequest.getTenantId());

		if (productDetails.isPresent()) {
			productDaoInterface.deleteProductById(productRequest.getProductSequenceNumber(),
					productRequest.getApplicationId(), productRequest.getTenantId());

		} else {
			throw new InvalidInputParametersException(ProductValidatorConstants.productDetailsIsNotFoundErrorCode,
					ProductValidatorConstants.productDetailsIsNotFoundMessage);
		}
	}

	@Override
	@Transactional
	public Optional<ProductResponse> getProductById(ProductManagementRequest productManagementRequest)
			throws TokenExpiredException {

		Long customerSequenceNumber = productManagementRequest.getCustomerSequenceNumber();
		Integer expirationDuration = productManagementRequest.getExpirationDuration();

		if (customerSequenceNumber != null
				&& !customerSequenceNumber.equals(CommonConstant.nonLoggedCustomerSequenceNumber)) {
			tokenDao.checkTokenValidity(customerSequenceNumber, expirationDuration);
		}

		ProductRequest productRequest = productManagementRequest.getProductRequest();
		ProductResponse productResponse = new ProductResponse();

		Optional<ProductDetails> getProductDetails = productDaoInterface.getProductDetails(
				productRequest.getProductSequenceNumber(), productRequest.getApplicationId(),
				productRequest.getTenantId());

		if (getProductDetails.isPresent()) {
			ProductDetails productDetails = getProductDetails.get();

			productResponse.setProductDetailsResponse(
					Collections.singletonList(createResponseForProductDetailsEntity(productDetails)));

			createAdditionalProductResponse(productResponse, productDetails);

			return Optional.of(productResponse);
		}

		return Optional.empty();
	}

	@Override
	@Transactional
	public List<ProductResponse> getProductDetailsById(ProductManagementRequest productManagementRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		Long customerSequenceNumber = productManagementRequest.getCustomerSequenceNumber();
		Integer expirationDuration = productManagementRequest.getExpirationDuration();

		if (customerSequenceNumber != null
				&& !customerSequenceNumber.equals(CommonConstant.nonLoggedCustomerSequenceNumber)) {
			tokenDao.checkTokenValidity(customerSequenceNumber, expirationDuration);
		}

		ProductRequest productRequest = productManagementRequest.getProductRequest();

		List<ProductDetails> getProductDetailsList = productDaoInterface.getProductDetailsById(
				productRequest.getProductId(), productRequest.getApplicationId(), productRequest.getTenantId());

		List<ProductResponse> productResponseList = new ArrayList<>();
		if (getProductDetailsList != null && !getProductDetailsList.isEmpty()) {

			for (ProductDetails productDetails : getProductDetailsList) {
				ProductResponse productResponse = new ProductResponse();
				productResponse.setProductDetailsResponse(
						Collections.singletonList(createResponseForProductDetailsEntity(productDetails)));
				createAdditionalProductResponse(productResponse, productDetails);
				productResponseList.add(productResponse);
			}
		}
		return productResponseList;
	}

	private void createAdditionalProductResponse(ProductResponse productResponse, ProductDetails productDetails) {
		List<ProductAdditionalProducts> additionalProducts = productDetails.getProductAdditionalProducts();

		if (additionalProducts != null && additionalProducts.size() > 0) {
			List<ProductDetails> productDetailsList = new ArrayList<>();
			List<Long> additionalProductsSequenceList = new ArrayList<>();
			List<ProductFilterResponse> additionalProductsList = new ArrayList<>();

			for (ProductAdditionalProducts additionalProduct : additionalProducts) {
				additionalProductsSequenceList.add(additionalProduct.getAdditionalProduct());
			}

			productDetailsList = productDaoInterface.fetchAddtionalProductDataList(productDetails.getApplicationId(),
					productDetails.getTenantId(), additionalProductsSequenceList);

			for (ProductDetails productDetail : productDetailsList) {

				additionalProductsList.add(
						ProductFilterResponse.builder().productSequenceNumber(productDetail.getProductSequenceNumber())
								.productId(productDetail.getProductId()).productName(productDetail.getProductName())
								.description(productDetail.getDescription())

								.productPerUnitCurrentPrice(productDetail.getProductPerUnitCurrentPrice())
								.productImagePath(productDetail.getProductImagePath())
								.defaultImage(productDetail.getDefaultImage()).build());

			}
			productResponse.getProductDetailsResponse().get(0).setAdditionalProducts(additionalProductsList);

		}
	}

	@Transactional
	@Override
	public ProductResponse getAllProducts(Long customerSequenceNumber, Integer expirationDuration)
			throws TokenExpiredException, InvalidInputParametersException {

		return getAllProductsInternal(customerSequenceNumber, expirationDuration, null);
	}

	private ProductResponse getAllProductsInternal(Long customerSequenceNumber, Integer expirationDuration,
			Pageable pageRequest) throws TokenExpiredException {
		if (customerSequenceNumber != CommonConstant.nonLoggedCustomerSequenceNumber) {
			tokenDao.checkTokenValidity(customerSequenceNumber, expirationDuration);
		}

		ProductResponse productResponse = new ProductResponse();
		List<ProductDetailsResponse> productResponseList = new ArrayList();
		Page<ProductDetails> productDetailsList = productDaoInterface.getAllProducts(pageRequest);
		if (productDetailsList != null && !productDetailsList.isEmpty()) {
			for (ProductDetails productDetails : productDetailsList) {
				productResponseList.add(createResponseForProductDetailsEntity(productDetails));
			}

		}
		productResponse.setProductDetailsResponse(productResponseList);
		productResponse.setTotalRecords(productDetailsList.getTotalElements());
		return productResponse;
	}

	@Transactional
	@Override
	public ProductResponse filterProduct(ProductManagementRequest productManagementRequest)
			throws TokenExpiredException {

		Pageable pageRequest;
		ProductFilterCriteria productFilterCriteria = productManagementRequest.getFilterCriteria();

		Long customerSequenceNumber = productManagementRequest.getCustomerSequenceNumber();
		Integer expirationDuration = productManagementRequest.getExpirationDuration();
		if (productManagementRequest.getPagination().getOffset() != null
				&& productManagementRequest.getPagination().getLimit() != null) {

			pageRequest = PageRequest.of((int) (long) productManagementRequest.getPagination().getOffset(),
					(int) (long) productManagementRequest.getPagination().getLimit());
		} else {
			pageRequest = PageRequest.of(0, 50);
		}
		if (null != productFilterCriteria.getIsToGetAllProduct() && productFilterCriteria.getIsToGetAllProduct()) {
			return getAllProductsInternal(customerSequenceNumber, expirationDuration, pageRequest);
		} else {
			return filterProductInternal(customerSequenceNumber, expirationDuration, productManagementRequest,
					pageRequest);
		}
	}

	@Override
	public List<ProductFilterResponse> getProductFilters(ProductManagementRequest productManagementRequest)
			throws TokenExpiredException {

		if (productManagementRequest.getCustomerSequenceNumber() != CommonConstant.nonLoggedCustomerSequenceNumber) {
			tokenDao.checkTokenValidity(productManagementRequest.getCustomerSequenceNumber(),
					productManagementRequest.getExpirationDuration());
		}
		ProductRequest productRequest = productManagementRequest.getProductRequest();
		List<ProductFilterResponse> productDetailsData = new ArrayList<>();
		List<Object[]> resultList = productDaoInterface.getProductFilters(productRequest.getApplicationId(),
				productRequest.getTenantId());
		for (Object[] object : resultList) {
			productDetailsData
					.add(ProductFilterResponse.builder().productId(object[0] != null ? (String) object[0] : "")
							.productName(object[1] != null ? (String) object[1] : "")
							.description(object[2] != null ? (String) object[2] : "").build());
		}
		return productDetailsData;
	}

	private ProductResponse filterProductInternal(Long customerSequenceNumber, Integer expirationDuration,
			ProductManagementRequest productManagementRequest, Pageable pageRequest) throws TokenExpiredException {
		if (customerSequenceNumber != CommonConstant.nonLoggedCustomerSequenceNumber) {
			tokenDao.checkTokenValidity(customerSequenceNumber, expirationDuration);
		}

		return filterProducts(productManagementRequest, pageRequest);
	}

	private ProductResponse filterProducts(ProductManagementRequest productManagementRequest, Pageable pageRequest) {

		Page<ProductDetails> productDetailsList = null;
		Map<String, Object> filterCriteria = new HashMap<>();
		ProductFilterCriteria productFilterCriteria = productManagementRequest.getFilterCriteria();

		if (productFilterCriteria != null) {

			if (productFilterCriteria.getColourList() != null && !productFilterCriteria.getColourList().isEmpty()) {
				filterCriteria.put(ProductValidatorConstants.filter_colour, productFilterCriteria.getColourList());
			}
			if (productFilterCriteria.getMaterialList() != null && !productFilterCriteria.getMaterialList().isEmpty()) {
				filterCriteria.put(ProductValidatorConstants.filter_material, productFilterCriteria.getMaterialList());
			}
			if (productFilterCriteria.getProductFamilyList() != null
					&& !productFilterCriteria.getProductFamilyList().isEmpty()) {
				filterCriteria.put(ProductValidatorConstants.filter_product_family,
						productFilterCriteria.getProductFamilyList());
			}
			if (productFilterCriteria.getProductBrandList() != null
					&& !productFilterCriteria.getProductBrandList().isEmpty()) {
				filterCriteria.put(ProductValidatorConstants.filter_product_brand,
						productFilterCriteria.getProductBrandList());
			}
			if (productFilterCriteria.getProductCatalogueList() != null
					&& !productFilterCriteria.getProductCatalogueList().isEmpty()) {
				filterCriteria.put(ProductValidatorConstants.filter_product_catalogue,
						productFilterCriteria.getProductCatalogueList());
			}
			if (productFilterCriteria.getMinPrice() != null) {
				filterCriteria.put(ProductValidatorConstants.filter_min_price, productFilterCriteria.getMinPrice());
			}
			if (productFilterCriteria.getMaxPrice() != null) {
				filterCriteria.put(ProductValidatorConstants.filter_max_price, productFilterCriteria.getMaxPrice());
			}

			if (productFilterCriteria.getSubCategoryList() != null
					&& !productFilterCriteria.getSubCategoryList().isEmpty()) {
				filterCriteria.put(ProductValidatorConstants.filter_subcategory,
						productFilterCriteria.getSubCategoryList());
			}
			if (productFilterCriteria.getAvailability() != null && productFilterCriteria.getAvailability() == true) {
				filterCriteria.put(ProductValidatorConstants.filter_availability,
						productFilterCriteria.getAvailability());
			}

			if (productFilterCriteria.getSomeOfAKind() != null && productFilterCriteria.getSomeOfAKind() == true) {
				filterCriteria.put(ProductValidatorConstants.filter_someOfAKind,
						productFilterCriteria.getSomeOfAKind());
			}

			if (productFilterCriteria.getSaleItems() != null && productFilterCriteria.getSaleItems() == true) {
				filterCriteria.put(ProductValidatorConstants.filter_saleItems, productFilterCriteria.getSaleItems());
			}

			if (productFilterCriteria.getCloseOut() != null && productFilterCriteria.getCloseOut() == true) {
				filterCriteria.put(ProductValidatorConstants.filter_closeOut, productFilterCriteria.getCloseOut());
			}

			if (productFilterCriteria.getQuickShip() != null && productFilterCriteria.getQuickShip() == true) {
				filterCriteria.put(ProductValidatorConstants.filter_quickShip, productFilterCriteria.getQuickShip());
			}

			if (productFilterCriteria.getOneOfKind() != null && productFilterCriteria.getOneOfKind() == true) {
				filterCriteria.put(ProductValidatorConstants.filter_oneOfKind, productFilterCriteria.getOneOfKind());
			}

			if (productFilterCriteria.getNewArrival() != null && productFilterCriteria.getNewArrival() == true) {
				filterCriteria.put(ProductValidatorConstants.filter_new_arrival, productFilterCriteria.getNewArrival());
			}
			if (productFilterCriteria.getFeaturedProduct() != null
					&& productFilterCriteria.getFeaturedProduct() == true) {
				filterCriteria.put(ProductValidatorConstants.filter_featured_product,
						productFilterCriteria.getFeaturedProduct());
			}
			if (productFilterCriteria.getTopSellingProduct() != null
					&& productFilterCriteria.getTopSellingProduct() == true) {
				filterCriteria.put(ProductValidatorConstants.filter_top_selling_product,
						productFilterCriteria.getTopSellingProduct());
			}
			if (productFilterCriteria.getBestSeller() != null && productFilterCriteria.getBestSeller() == true) {
				filterCriteria.put(ProductValidatorConstants.filter_best_seller, productFilterCriteria.getBestSeller());
			}

			// TODO
			if (!StringUtils.isEmpty(productFilterCriteria.getSearchString())) {
				StringBuilder searchCondition = new StringBuilder();
				searchCondition.append("(");
				boolean isFirstIteration = true;
				for (SearchBy search : SearchBy.values()) {
					if (!isFirstIteration)
						searchCondition.append(" or ");

					searchCondition.append(" Lower(").append(search.getSearchBy()).append(") like '%")
							.append(productFilterCriteria.getSearchString().toLowerCase()).append("%'");
					isFirstIteration = false;
				}
				searchCondition.append(")");

				filterCriteria.put(ProductValidatorConstants.filter_search_condition, searchCondition);
			}
		}

		if (filterCriteria.isEmpty()) {
			productDetailsList = productDaoInterface.getAllProducts(pageRequest);
		} else {

			if (productManagementRequest.getSortCriteria() != null) {
				if (!StringUtils.isEmpty(productManagementRequest.getSortCriteria().getSortBy().name()))
					filterCriteria.put(ProductValidatorConstants.filter_sorting_direction,
							productManagementRequest.getSortCriteria().getSortBy().getSortBy() + " "
									+ productManagementRequest.getSortCriteria().getSortOrder());
			}
			HashMap filterProducts = productDaoInterface.filterProducts(productFilterCriteria.getApplicationId(),
					productFilterCriteria.getTenantId(), filterCriteria, pageRequest);
			if (filterProducts != null) {

				productDetailsList = new PageImpl((List) filterProducts.get("records"), pageRequest,
						(long) filterProducts.get("totalCount"));
			}
		}

		return createResponseForProductDetailsEntity(productDetailsList);
	}

	private ProductResponse createResponseForProductDetailsEntity(Page<ProductDetails> productDetailsList) {

		ProductResponse productResponse = new ProductResponse();
		List<ProductDetailsResponse> productDetailsListData = new ArrayList<>();
		if (productDetailsList != null && !productDetailsList.isEmpty()) {
			for (ProductDetails productDetails : productDetailsList) {
				productDetailsListData.add(createResponseForProductDetailsEntity(productDetails));
			}
		}
		productResponse.setProductDetailsResponse(productDetailsListData);
		productResponse.setTotalRecords(productDetailsList.getTotalElements());
		return productResponse;
	}

	private ProductDetailsResponse createResponseForProductDetailsEntity(ProductDetails productDetails) {

		ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();
		productDetailsResponse.setProductSequenceNumber(productDetails.getProductSequenceNumber());
		productDetailsResponse.setProductId(productDetails.getProductId());
		productDetailsResponse.setProductName(productDetails.getProductName());
		productDetailsResponse.setDescription(productDetails.getDescription());
		productDetailsResponse.setMeasuringQuantity(productDetails.getMeasuringQuantity());
		productDetailsResponse.setMeasuringUnit(productDetails.getMeasuringUnit());
		productDetailsResponse.setSubcategoryId(productDetails.getSubcategoryId());
		productDetailsResponse.setProductImagePath(productDetails.getProductImagePath());
		productDetailsResponse.setProductPerUnitActualPrice(productDetails.getProductPerUnitActualPrice());
		productDetailsResponse.setProductPerUnitCurrentPrice(productDetails.getProductPerUnitCurrentPrice());
		productDetailsResponse.setProductLength(productDetails.getLength());
		productDetailsResponse.setWidth(productDetails.getWidth());
		productDetailsResponse.setHeight(productDetails.getHeight());
		productDetailsResponse.setDimensionUnit(productDetails.getDimensionUnit());
		productDetailsResponse.setMaterials(productDetails.getMaterials());
		productDetailsResponse.setColours(productDetails.getColours());
		productDetailsResponse.setHexCode(productDetails.getHexCode());
		productDetailsResponse.setProductQuantity(productDetails.getProductQuantity());
		productDetailsResponse.setProductFamily(productDetails.getProductFamily());
		productDetailsResponse.setProductBrand(productDetails.getProductBrand());
		productDetailsResponse.setProductCatalogue(productDetails.getProductCatalogue());
		productDetailsResponse.setNewArrival(productDetails.getNewArrival());
		productDetailsResponse.setTopSellingProduct(productDetails.getTopSellingProduct());
		productDetailsResponse.setBestSeller(productDetails.getBestSeller());
		productDetailsResponse.setFeaturedProduct(productDetails.getFeaturedProduct());
		productDetailsResponse.setProductFinish(productDetails.getProductFinish());
		productDetailsResponse.setProdWholesaleTier1Price(productDetails.getProdWholesaleTier1Price());
		productDetailsResponse.setProdWholesaleTier2Price(productDetails.getProdWholesaleTier2Price());
		productDetailsResponse.setProdWholesaleTier1SalePrice(productDetails.getProdWholesaleTier1SalePrice());
		productDetailsResponse.setProdWholesaleTier2SalePrice(productDetails.getProdWholesaleTier2SalePrice());
		productDetailsResponse.setProdRetailTier1Price(productDetails.getProdRetailTier1Price());
		productDetailsResponse.setProdRetailTier2Price(productDetails.getProdRetailTier2Price());
		productDetailsResponse.setProdRetailTier1SalePrice(productDetails.getProdRetailTier1SalePrice());
		productDetailsResponse.setProdRetailTier2SalePrice(productDetails.getProdRetailTier2SalePrice());
		productDetailsResponse.setDefaultImage(productDetails.getDefaultImage());
		productDetailsResponse.setSomeOfAKind(productDetails.getSomeOfAKind());
		productDetailsResponse.setSaleItems(productDetails.getSaleItems());
		productDetailsResponse.setCloseOut(productDetails.getCloseOut());
		productDetailsResponse.setQuickShip(productDetails.getQuickShip());
		productDetailsResponse.setEta(productDetails.getEta());
		productDetailsResponse.setOneOfKind(productDetails.getOneOfKind());
		productDetailsResponse.setIsAnchorProduct(productDetails.getIsAnchorProduct());
		productDetailsResponse.setIsPackageProduct(productDetails.getIsPackageProduct());

		return productDetailsResponse;
	}

//	private ProductExtendedDetails createEntityFromProductExtendedDetailsRequest(ProductDetails productEntity,
//			ProductExtendedDetailsRequest productExtendedDetailsRequest) {
//
//        return ProductExtendedDetails.builder()
//				.productFinish(productExtendedDetailsRequest.getProductFinish())
//				.prodWholesaleTier1Price(productExtendedDetailsRequest.getProdWholesaleTier1Price())
//				.prodWholesaleTier2Price(productExtendedDetailsRequest.getProdWholesaleTier2Price())
//				.prodWholesaleTier1SalePrice(productExtendedDetailsRequest.getProdWholesaleTier1SalePrice())
//				.prodWholesaleTier2SalePrice(productExtendedDetailsRequest.getProdWholesaleTier2SalePrice())
//				.prodRetailTier1Price(productExtendedDetailsRequest.getProdRetailTier1Price())
//				.prodRetailTier2Price(productExtendedDetailsRequest.getProdRetailTier2Price())
//				.prodRetailTier1SalePrice(productExtendedDetailsRequest.getProdRetailTier1SalePrice())
//				.prodRetailTier2SalePrice(productExtendedDetailsRequest.getProdRetailTier2SalePrice())
//				.productSequenceNumber(productEntity.getProductSequenceNumber()).build();
//	}

}
