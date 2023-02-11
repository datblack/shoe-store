/**
 * Luvina Software JSC, 2022
 * VariantValueService.java, Bui Quang Hieu
 */
package com.pro2111.service;

import java.util.List;

import com.pro2111.entities.Option;
import com.pro2111.entities.OptionValue;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductOption;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.VariantValue;

/**
 * @author BUI_QUANG_HIEU
 */
public interface VariantValueService {

	/**
	 * Lấy tất cả VariantValue
	 * 
	 * @return
	 */
	public List<VariantValue> findAll();

	/**
	 * Lấy list VariantValue theo ProductOption
	 * 
	 * @param productOption
	 * @return
	 */
	public List<VariantValue> findByProductOption(ProductOption productOption);

	/**
	 * Lấy ProductOption theo Product, Option
	 * 
	 * @param product
	 * @param option
	 * @return
	 */
	public ProductOption findByProductsLikeAndOptionsLike(Product product, Option option);

	/**
	 * Lấy list VariantValue theo ProductVariant
	 * 
	 * @param productVariant
	 * @return
	 */
	public List<VariantValue> findByProductVariant(ProductVariant productVariant);

	/**
	 * Lấy list VariantValue theo ProductVariant, status
	 * 
	 * @param productVariant
	 * @param status
	 * @return
	 */
	public List<VariantValue> findByProductVariantsLikeAndStatusLike(ProductVariant productVariant, Integer status);

	/**
	 * Tạo mới VariantValue
	 * 
	 * @param productVariants
	 * @param products
	 * @param options
	 * @param optionValues
	 */
	public void createVariantValue(ProductVariant productVariants, Product products, Option options,
			OptionValue optionValues);

	/**
	 * Lấy VariantValue theo Product
	 * 
	 * @param product
	 * @return
	 */
	public List<VariantValue> findByProduct(Product product);

	/**
	 * Lấy VariantValue theo ProductVariant
	 * 
	 * @param productVariant
	 * @return
	 */
	public List<VariantValue> findByProductVariantOrigin(ProductVariant productVariant);

	/**
	 * Tạo mới VariantValue từ list VariantValue
	 * 
	 * @param variantValues
	 * @return
	 */
	public List<VariantValue> create(List<VariantValue> variantValues);

}