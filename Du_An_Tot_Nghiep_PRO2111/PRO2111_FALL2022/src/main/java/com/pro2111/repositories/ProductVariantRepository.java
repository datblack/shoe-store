package com.pro2111.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.entities.Product;
import com.pro2111.entities.ProductVariant;
import com.pro2111.utils.Constant;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

	/**
	 * Lấy list ProductVariant theo Product
	 * 
	 * @param product
	 * @return
	 */
	List<ProductVariant> findByProductsLike(Product product);

	/**
	 * Lấy list ProductVariant đang sale
	 * 
	 * @return
	 */
	@Query("SELECT pv FROM ProductVariant pv WHERE pv.status = " + Constant.STATUS_TRUE_PRODUCT_VARIANT + " AND EXISTS "
			+ "(SELECT vv FROM VariantValue vv WHERE vv.status = " + Constant.STATUS_TRUE_VARIANT_VALUE
			+ " AND pv = vv.productVariants)")
	List<ProductVariant> getProductVariantOfSale();

	/**
	 * Lấy list ProductVariant theo ProductName gần đúng
	 * 
	 * @param name
	 * @return
	 */
	@Query("SELECT pv FROM ProductVariant pv WHERE pv.products.productName LIKE %:name%")
	List<ProductVariant> findByApproximateProductName(@Param("name") String name);

	/**
	 * Lấy giá min ProductVariant theo Product
	 * 
	 * @param product
	 * @return
	 */
	@Query("SELECT MIN(pv.price) FROM ProductVariant pv WHERE pv.products = :product")
	BigDecimal findMinPriceByProduct(@Param("product") Product product);

	/**
	 * Lấy giá min và max của tất cả ProductVariant
	 * 
	 * @return
	 */
	@Query("SELECT MIN(p.price) AS min, MAX(p.price) AS max FROM ProductVariant AS p")
	Object getPriceRange();

	/**
	 * Lấy giá max ProductVariant theo Product
	 * 
	 * @param product
	 * @return
	 */
	@Query("SELECT MAX(pv.price) FROM ProductVariant pv WHERE pv.products = :product")
	BigDecimal findMaxPriceByProduct(@Param("product") Product product);

	/**
	 * Lấy ProductVariant theo variantId
	 * 
	 * @param id
	 * @return
	 */
	@Query("SELECT pv FROM ProductVariant pv WHERE pv.variantId =:id")
	ProductVariant findByIdInHibernate(@Param("id") Long id);

	/**
	 * Lấy list ProductVariant được phép khuyến mãi
	 * 
	 * @param listProduct
	 * @return
	 */
	@Query("SELECT pv FROM ProductVariant pv WHERE pv.products in :product AND pv.status = 1 AND pv.isSale = 1")
	List<ProductVariant> findProductVariantsPromotionIsAllowed(@Param("product") List<Product> listProduct);

	/**
	 * Lấy list ProductVariant có quantity <= ?
	 * 
	 * @param quantity
	 * @return
	 */
	@Query("SELECT pv FROM ProductVariant pv WHERE pv.quantity <=:quantity ORDER BY pv.quantity ASC")
	List<ProductVariant> getProductVariantByLessQuantity(@Param("quantity") int quantity);
}
