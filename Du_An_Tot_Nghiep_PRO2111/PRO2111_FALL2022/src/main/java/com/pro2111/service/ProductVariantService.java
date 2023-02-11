/**
 * Luvina Software JSC, 2022
 * ProductService.java, Bui Quang Hieu
 */
package com.pro2111.service;

import java.math.BigDecimal;
import java.util.List;

import com.pro2111.beans.ProAndOpAndOv;
import com.pro2111.beans.ProductVariansBean;
import com.pro2111.beans.PvAndOv;
import com.pro2111.beans.PvAndVv;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.VariantValue;

/**
 * @author BUI_QUANG_HIEU
 */
public interface ProductVariantService {

	/**
	 * Lấy tất cả ProductVariant
	 * 
	 * @return
	 */
	public List<ProductVariant> findAll();

	/**
	 * Lấy ProductVariant theo Id
	 * 
	 * @param id
	 * @return ProductVariant
	 */
	public ProductVariant findById(Long id);

	/**
	 * Lấy khoảng giá min và max của tất cả ProductVariant
	 * 
	 * @return
	 */
	Object getPriceRange();

	/**
	 * Cập nhật ProductVariant
	 * 
	 * @param productVariant
	 * @return ProductVariant
	 */
	public ProductVariant create(ProductVariant productVariant);

	/**
	 * Cập nhật ProductVariant từ PvAndVv
	 * 
	 * @param pvAndVv
	 * @return ProductVariant
	 */
	public ProductVariant update(PvAndVv pvAndVv);

	/**
	 * Lấy list ProductVariant từ Product
	 * 
	 * @param product
	 * @return
	 */
	public List<ProductVariant> findByProduct(Product product);

	/**
	 * Lấy list ProductVariant đang được khuyến mãi
	 * 
	 * @return
	 */
	public List<ProductVariant> getProductVariantOfSale();

	/**
	 * Tạo ProductVariant từ ProductVariant và VariantValue
	 * 
	 * @param productVariant
	 * @param variantValue
	 * @return
	 */
	public ProductVariant createProductVariantAndVariantValue(ProductVariant productVariant,
			List<VariantValue> variantValue);

	/**
	 * Lấy list ProductVariant từ ProductName gần đúng
	 * 
	 * @param name
	 * @return List<ProductVariant>
	 */
	public List<ProductVariant> findByApproximateProductName(String name);

	/**
	 * Lấy list ProductVariant từ mọi key
	 * 
	 * @param inputString
	 * @return List<ProductVariant>
	 */
	List<ProductVariant> dynamicSearchByKey(String inputString);

	/**
	 * Lấy ProductVariant từ Product, Option, OptionValue
	 * 
	 * @param proAndOpAndOv
	 * @return
	 */
	public ProductVariant findByProductAndOptionAndOptionValue(ProAndOpAndOv proAndOpAndOv);

	/**
	 * Lấy giá min ProductVariant từ Product
	 * 
	 * @param product
	 * @return
	 */
	public BigDecimal findMinPriceByProduct(Product product);

	/**
	 * Lấy giá max ProductVariant từ Product
	 * 
	 * @param product
	 * @return
	 */
	public BigDecimal findMaxPriceByProduct(Product product);

	/**
	 * Tạo mới ProductVariant từ PvAndOv
	 * 
	 * @param pvAndVvs
	 * @return
	 */
	public List<ProductVariant> createV2(List<PvAndOv> pvAndOvs);

	/**
	 * Cập nhật giá ProductVariant
	 * 
	 * @param bean
	 * @return
	 */
	public ProductVariansBean updatePrice(ProductVariansBean bean);

	/**
	 * Cập nhật trạng thái ProductVariant
	 * 
	 * @param productVariant
	 * @return
	 */
	public ProductVariant updateStatus(ProductVariant productVariant);

}
