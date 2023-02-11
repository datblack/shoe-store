/**
 * Luvina Software JSC, 2022
 * ProductService.java, Bui Quang Hieu
 */
package com.pro2111.service;

import java.util.List;

import com.pro2111.beans.FilterProductBean;
import com.pro2111.beans.ProductAndProductOptionBean;
import com.pro2111.entities.Product;

/**
 * @author BUI_QUANG_HIEU
 */
public interface ProductService {

	/**
	 * Lấy tất cả Product
	 * 
	 * @return
	 */
	public List<Product> findAll();

	/**
	 * Tạo mới Product
	 * 
	 * @param product
	 * @return
	 */
	public Product create(ProductAndProductOptionBean product);

	/**
	 * Lấy Product theo ProductId
	 * 
	 * @param id
	 * @return Product
	 */
	public Product findById(Integer id);

	/**
	 * Cập nhật Product
	 * 
	 * @param product
	 * @return product
	 */
	public Product update(Product product);

	/**
	 * Lấy Product theo productName
	 * 
	 * @param name
	 * @return product
	 */
	public Product findByName(String name);

	/**
	 * Lấy list Product theo status
	 * 
	 * @param int status
	 * @return
	 */
	public List<Product> findByStatusLike(int status);

	/**
	 * Lấy list Product theo productName gần đúng
	 * 
	 * @param name
	 * @return
	 */
	public List<Product> findByApproximateName(String name);

	/**
	 * Xóa Product
	 * 
	 * @param product
	 * @return
	 */
	public Product delete(Product product);

	/**
	 * Lấy Product theo khoảng giá ProductVariant
	 * 
	 * @param searchRequest
	 * @return
	 */
	List<Product> findByRangePrice(FilterProductBean searchRequest);

}
