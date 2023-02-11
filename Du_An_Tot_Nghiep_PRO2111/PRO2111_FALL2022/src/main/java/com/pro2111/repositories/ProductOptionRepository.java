package com.pro2111.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.entities.Option;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductOption;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Integer> {

	/**
	 * Lấy list ProductOption theo Product
	 * 
	 * @param product
	 * @return
	 */
	List<ProductOption> findByProductsLike(Product product);

	/**
	 * Lấy list ProductOption theo Product, status
	 * 
	 * @param product
	 * @param status
	 * @return
	 */
	List<ProductOption> findByProductsLikeAndStatusLike(Product product, Integer status);

	/**
	 * Tạo mới ProductOption
	 * 
	 * @param product
	 * @param option
	 * @param status
	 */
	@Modifying
	@Query(value = "INSERT INTO product_options (Product_id, Option_id, Status) VALUES (:products, :options, :status)", nativeQuery = true)
	@Transactional
	void insertPO(@Param("products") Product product, @Param("options") Option option, @Param("status") Integer status);

	/**
	 * Lấy ProductOption theo Product, Option
	 * 
	 * @param product
	 * @param option
	 * @return
	 */
	ProductOption findByProductsLikeAndOptionsLike(Product product, Option option);

	/**
	 * Cập nhật ProductOption
	 * 
	 * @param product
	 * @param option
	 * @param optionOld
	 * @param status
	 */
	@Modifying
	@Query(value = "UPDATE product_options SET Option_id = :options, Status = :status WHERE Product_id = :products AND Option_id = :optionOld", nativeQuery = true)
	@Transactional
	void updatePO(@Param("products") Product product, @Param("options") Option option,
			@Param("optionOld") Option optionOld, @Param("status") Integer status);

	/**
	 * Cập nhật trạng thái ProductOption
	 * 
	 * @param product
	 * @param option
	 * @param status
	 */
	@Modifying
	@Query(value = "UPDATE product_options SET Status = :status WHERE Product_id = :products AND Option_id = :options", nativeQuery = true)
	@Transactional
	void updateStatusPO(@Param("products") Product product, @Param("options") Option option,
			@Param("status") Integer status);

	/**
	 * Xóa ProductOption theo Product
	 * 
	 * @param product
	 */
	@Modifying
	@Query(value = "DELETE FROM product_options WHERE Product_id = :products", nativeQuery = true)
	@Transactional
	void deletePOByProduct(@Param("products") Product product);

	/**
	 * Xóa ProductOption
	 * 
	 * @param product
	 * @param option
	 */
	@Modifying
	@Query(value = "DELETE FROM product_options WHERE Product_id = :product AND Option_id =:option", nativeQuery = true)
	@Transactional
	void deletePO(@Param("product") Product product, @Param("option") Option option);

	/**
	 * Lấy list ProductOption theo Option
	 * 
	 * @param option
	 * @return
	 */
	List<ProductOption> findByOptionsLike(Option option);

}
