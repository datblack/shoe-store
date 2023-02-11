package com.pro2111.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.entities.OptionValue;
import com.pro2111.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	/**
	 * Lấy Product theo productName
	 * 
	 * @param nameProduct
	 * @return
	 */
	Product findByProductNameLike(String nameProduct);

	/**
	 * Lấy list Product theo Status
	 * 
	 * @param status
	 * @return
	 */
	List<Product> findByStatusLike(int status);

	/**
	 * Lấy list Product theo name gần đúng
	 * 
	 * @param name
	 * @return
	 */
	@Query("SELECT p FROM Product p WHERE p.productName LIKE %:name%")
	List<Product> findByApproximateName(@Param("name") String name);

	/**
	 * Lấy list Product khoảng giá và list OptionValue
	 * 
	 * @param optionValues
	 * @param min
	 * @param max
	 * @return
	 */
	@Query("SELECT p FROM Product p " + " JOIN ProductOption po ON po.products = p" + " JOIN Option o ON o = po.options"
			+ " JOIN OptionValue ov ON ov.options = o" + " JOIN ProductVariant pv ON pv.products = p"
			+ " WHERE ov IN (:ov) AND price >= :min AND price <= :max GROUP BY p")
	List<Product> findByRangePriceAndOptionValues(@Param("ov") List<OptionValue> optionValues,
			@Param("min") BigDecimal min, @Param("max") BigDecimal max);

	/**
	 * Lấy list Product theo khoảng giá
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	@Query("SELECT p FROM Product p " + " JOIN ProductOption po ON po.products = p" + " JOIN Option o ON o = po.options"
			+ " JOIN OptionValue ov ON ov.options = o" + " JOIN ProductVariant pv ON pv.products = p"
			+ " WHERE price >= :min AND price <= :max GROUP BY p")
	List<Product> findByRangePrice(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

}
