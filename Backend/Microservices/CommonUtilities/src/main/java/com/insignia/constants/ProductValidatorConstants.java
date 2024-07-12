package com.insignia.constants;

public class ProductValidatorConstants {

	public static final String validProductIdMessage = "Please enter product id";
	public static final String validProductIdErrorCode = "621";
	public static final String validProductIdLengthMessage = "Product id should be upto 30 characters only";
	public static final String validProductIdLengthErrorCode = "622";

	public static final String validProductNameMessage = "Please enter productName";
	public static final String validProductNameErrorCode = "601";
	public static final String validProductNameLengthMessage = "Product name should be upto 100 characters only";
	public static final String validProductNameLengthErrorCode = "602";

	public static final String validMeasuringQuantityLength = "Please enter valid measuring quantity length  ";
	public static final String validMeasuringQuantityLengthErrorCode = "604";

	public static final String validDescriptionLength = "Product description should be upto 255 characters only";
	public static final String validDescriptionLengthErrorCode = "606";

	public static final String validMeasuringUnit = "Please enter measuringunit";
	public static final String validMeasuringUnitErrorCode = "607";
	public static final String validMeasuringUnitLength = "Product measuring unit should be upto 64 characters only";
	public static final String validMeasuringUnitLengthErrorCode = "608";

	public static final String regularExpression = "^[A-Za-z0-9_\\-.@]+$";

	public static final String validateProductDetailsMessage = "Details already existing in the system";
	public static final String validateProductDetailsErrorCode = "611";

	public static final String subcategoryIdIsNotFoundMessage = "Provided subcategory id is invalid";
	public static final String subcategoryIdIsNotFoundErrorCode = "612";

	public static final String productDetailsIsNotFoundMessage = "Product Details not exist in the system";
	public static final String productDetailsIsNotFoundErrorCode = "613";

	public static final String validateProductPerUnitActualPriceLengthMessage = "Please enter product per unit actual price of total length upto 10 characters. Allowed decimal value upto 2 characters only.";
	public static final String validateProductPerUnitActualPriceLengthErrorCode = "615";

	public static final String validateProductPerUnitCurrentPriceLengthMessage = "Please enter product per unit current price of total length upto 10 characters. Allowed decimal value upto 2 characters only.";
	public static final String validateProductPerUnitCurrentPriceLengthErrorCode = "617";

	public static final String validateProductLengthMessage = "Please enter product length upto 7 characters only";
	public static final String validateProductLengthErrorCode = "616";

	public static final String validateHeightLengthMessage = "Please enter height length upto 7 characters only";
	public static final String validateHeightLengthErrorCode = "617";

	public static final String validateWidthLengthMessage = "Product width should be upto 7 characters only";
	public static final String validateWidthLengthErrorCode = "618";

	public static final String validateDimensionUnitLengthMessage = "Product dimension unit should be upto 10 characters only";
	public static final String validateDimensionUnitLengthErrorCode = "619";

	public static final String validateProductIdNotUpdatedMessage = "Product id can't be updated";
	public static final String validateProductIdNotUpdatedErrorCode = "620";

	public static final String validateMaterialsLengthMessage = "Product material should be upto 128 characters only";
	public static final String validateMaterialsLengthErrorCode = "623";

	public static final String validateColoursLengthMessage = "Product colour should be upto 128 characters only";
	public static final String validateColoursLengthErrorCode = "624";
	
	public static final String validateProductFinishLengthMessage = "Product Finish should be upto 128 characters only";
	public static final String validateProductFinishLengthErrorCode = "625";

	public static final String validateProdWholeTier1PriceLengthMessage = "Please enter prod whole tier1 price length upto 8,2 only";
	public static final String validateProdWholeTier1PriceLengthErrorCode = "626";

	public static final String validateProdWholeTier2PriceLengthMessage = "Please enter prod whole tier2 price length upto 8,2 only";
	public static final String validateProdWholeTier2PriceLengthErrorCode = "627";

	public static final String validateProdWholeTier1SalePriceLengthMessage = "Please enter prod whole tier1 sale price length upto 8,2 only";
	public static final String validateProdWholeTier1SalePriceLengthErrorCode = "628";

	public static final String validateProdWholeTier2SalePriceLengthMessage = "Please enter prod whole tier2 sale price length upto 8,2 only";
	public static final String validateProdWholeTier2SalePriceLengthErrorCode = "629";
	
	public static final String validateProdRetailTier1PriceLengthMessage = "Please enter prod whole tier1 price length upto 8,2 only";
	public static final String validateProdRetailTier1PriceLengthErrorCode = "630";

	public static final String validateProdRetailTier2PriceLengthMessage = "Please enter prod whole tier2 price length upto 8,2 only";
	public static final String validateProdRetailTier2PriceLengthErrorCode = "631";

	public static final String validateProdRetailTier1SalePriceLengthMessage = "Please enter prod whole tier1 sale price length upto 8,2 only";
	public static final String validateProdRetailTier1SalePriceLengthErrorCode = "632";

	public static final String validateProdRetailTier2SalePriceLengthMessage = "Please enter prod whole tier2 sale price length upto 8,2 only";
	public static final String validateProdRetailTier2SalePriceLengthErrorCode = "633";

	public static final String validateSortBy = "Incorrect sorting by criteria." +
												"Possible values are: [PRODUCT_PRICE,NEW_ARRIVAL,AVAILABILITY]";
	public static final String validateSortByCode = "634";

	public static final String validateSortOrder = "Incorrect sorting order criteria." +
			"Possible values are: [ASC,DESC]";
	public static final String validateSortOrderCode = "635";

	public static final String validateSortCriteria = "sortBy and sortOrder should not be empty or null";
	public static final String validateCriteriaCode = "636";

	public static final String validateEtaLengthMessage = "Eta should be upto 30 characters only";
	public static final String validateEtaLengthErrorCode = "633";
	
	public static final String filter_colour = "ColourList";
	public static final String filter_material = "MaterialList";
	public static final String filter_subcategory = "SubCategoryList";
	public static final String filter_min_price = "MinPrice";
	public static final String filter_max_price = "MaxPrice";
	public static final String filter_sorting_criteria = "SortingCriteria";
	public static final String filter_availability = "Availability";
	public static final String filter_someOfAKind = "SomeOfAKind";
	public static final String filter_saleItems = "SaleItems";
	public static final String filter_closeOut = "CloseOut";
	public static final String filter_quickShip = "QuickShip";
	public static final String filter_oneOfKind = "OneOfKind";
	public static final String filter_product_family = "ProductFamilyList";
	public static final String filter_product_brand = "ProductBrandList";
	public static final String filter_product_catalogue = "productCatalogueList";
	public static final String filter_new_arrival = "NewArrival";
	public static final String filter_featured_product = "FeaturedProduct";
	public static final String filter_top_selling_product = "TopSellingProduct";
	public static final String filter_best_seller = "BestSeller";
	
	
	public static final String validateProductRequestDetailsMessage = "Please provide valid product details";
	public static final String validateProductRequestDetailsErrorCode = "630";
	
	public static final String validateProductSequenceNumberMessage = "Please provide product sequence number";
	public static final String validateProductSequenceNumberErrorCode = "631";

	public static final String filter_sorting_direction = "SortingDirection";
	public static final String filter_search_condition = "searchCondition";
	
	//640
	public static final String duplicateproductFamilyErrorMessage = "Product is already associated to one or more provided families";
	public static final String duplicateproductFamilyErrorCode = "640";
	
	public static final String duplicateproductFamilyEntryErrorMessage = "The product can not be associated with more than one family as it is an anchor product ";
	public static final String duplicateproductFamilyEntryErrorCode = "641";
	
}
