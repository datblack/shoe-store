/**
 * Luvina Software JSC, 2022
 * ProductOptionService.java, Bui Quang Hieu
 */
package com.pro2111.service;

import java.util.List;

import com.pro2111.entities.Option;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductOption;

/**
 * @author BUI_QUANG_HIEU
 */
public interface ProductOptionService {

	/**
	 * Lấy tất cả ProductOption
	 * 
	 * @return
	 */
	public List<ProductOption> findAll();

	/**
	 * Lấy list ProductOption theo Product
	 * 
	 * @param product
	 * @return List<ProductOption>
	 */
	public List<ProductOption> findByProduct(Product product);

	/**
	 * Tạo mới ProductOption
	 * 
	 * @param productOption
	 * @return
	 */
	public void create(Product product, Option option, Integer status);

	/**
	 * Cập nhật ProductOption
	 * 
	 * @param products
	 * @param options
	 * @param optionOld
	 * @param status
	 */
	public void update(Product products, Option options, Option optionOld, Integer status);

	/**
	 * Cập nhật trạng thái ProductOption
	 * 
	 * @param products
	 * @param options
	 * @param status
	 */
	public void updateStatus(Product products, Option options, Integer status);

	/**
	 * Lấy list ProductOption theo Product và trạng thái true
	 * 
	 * @param product
	 * @return
	 */
	public List<ProductOption> findByProductAndStatusTrue(Product product);

	/**
	 * Xóa ProductOption
	 * 
	 * @param productOption
	 * @return
	 */
	public ProductOption delete(ProductOption productOption);
}
