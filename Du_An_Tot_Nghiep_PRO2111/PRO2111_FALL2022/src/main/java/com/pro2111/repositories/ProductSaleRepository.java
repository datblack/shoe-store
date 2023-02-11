/**
 * DATN_FALL2022, 2022
 * ProductSaleRepository.java, BUI_QUANG_HIEU
 */
package com.pro2111.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.entities.ProductSale;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.Sale;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public interface ProductSaleRepository extends JpaRepository<ProductSale, String> {

	/**
	 * Lấy sản phẩm chi tiết theo chương trình khuyến mãi
	 * 
	 * @param sale
	 * @return List<ProductVariant>
	 */
	@Query("SELECT ps.productVariants FROM ProductSale ps WHERE ps.sales =:sale")
	List<ProductVariant> findProductVariantBySale(@Param("sale") Sale sale);

	/**
	 * Tìm ProductSale theo sản phẩm chi tiết và chương trình khuyến mãi
	 * 
	 * @param pv
	 * @param saleNew
	 * @return ProductSale
	 */
	ProductSale findByProductVariantsLikeAndSalesLike(ProductVariant pv, Sale saleNew);

	/**
	 * Tìm chương trình khuyến mãi theo sản phẩm chi tiết
	 * 
	 * @param pv
	 * @return List<Sale>
	 */
	@Query("SELECT ps.sales FROM ProductSale ps WHERE ps.productVariants =:pv AND ps.sales.status=1")
	List<Sale> findSalesByProductVariant(@Param("pv") ProductVariant pv);

	/**
	 * Xóa ProductSale theo sale
	 * 
	 * @param sale
	 * @return
	 */
	@Modifying
	@Query("DELETE FROM ProductSale ps WHERE ps.sales =:sale")
	void deleteProductSaleBySale(@Param("sale") Sale s);

}
