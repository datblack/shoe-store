/**
 * DATN_FALL2022, 2022
 * BillDetailRepository.java, BUI_QUANG_HIEU
 */
package com.pro2111.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.beans.TopProductVariantSelling;
import com.pro2111.entities.Bill;
import com.pro2111.entities.BillDetail;
import com.pro2111.entities.ProductVariant;
import com.pro2111.utils.Constant;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public interface BillDetailRepository extends JpaRepository<BillDetail, String> {

	/**
	 * Lấy list hóa đơn chi tiết theo bill
	 * 
	 * @param bill
	 * @return
	 */
	List<BillDetail> findByBillsLike(Bill bill);

	/**
	 * Lấy list hóa đơn chi tiết theo product variant và hóa đơn
	 * 
	 * @param productVariant
	 * @param bill
	 * @return
	 */
	List<BillDetail> findByProductVariantsLikeAndBillsLike(ProductVariant productVariant, Bill bill);

	/**
	 * Lấy list hóa đơn chi tiết theo hóa đơn và status
	 * 
	 * @param bill
	 * @param status
	 * @return
	 */
	@Query("SELECT bd FROM BillDetail bd WHERE bd.bills =:bill AND bd.status =:status")
	List<BillDetail> findByBillDetailReturnInvoicesAndStatus(@Param("bill") Bill bill, @Param("status") int status);

	/**
	 * Lấy list hóa đơn chi tiết theo hóa đơn và status = STATUS_BILL_DETAIL_OK
	 * 
	 * @param bill
	 * @param status
	 * @return
	 */
	@Query("SELECT bd FROM BillDetail bd WHERE bd.bills =:bill AND bd.status = " + Constant.STATUS_BILL_DETAIL_OK)
	List<BillDetail> findByBillDetailEligibleForReturn(@Param("bill") Bill bill);

	/**
	 * Lấy list hóa đơn chi tiết theo hóa đơn và status!=
	 * STATUS_BILL_DETAIL_RETURN_OK
	 * 
	 * @param bill
	 * @param status
	 * @return
	 */
	@Query("SELECT bd FROM BillDetail bd WHERE bd.bills =:bill AND bd.status != "
			+ Constant.STATUS_BILL_DETAIL_RETURN_OK)
	List<BillDetail> findByBillDetailNoReturn(@Param("bill") Bill bill);

	/**
	 * Lấy list hóa đơn chi tiết theo hóa đơn và status!= STATUS_BILL_DETAIL_OK
	 * 
	 * @param bill
	 * @param status
	 * @return
	 */
	@Query("SELECT bd FROM BillDetail bd WHERE bd.bills =:bill AND bd.status != " + Constant.STATUS_BILL_DETAIL_OK)
	List<BillDetail> findByBillDetailReturnInvoices(@Param("bill") Bill bill);

	/**
	 * Lấy list hóa đơn chi tiết mà người dùng mua ban đầu theo hóa đơn
	 * 
	 * @param bill
	 * @param status
	 * @return
	 */
	@Query(value = "SELECT bd.variant_id as productVariant, SUM(bd.quantity) as quantity, bd.price as price, bd.tax as tax, SUM(bd.total_money) as totalMoney, bd.user_confirm as userConfirm, bd.note as note FROM bill_details AS bd WHERE bd.bill_id =:bill AND bd.status != "
			+ Constant.STATUS_BILL_DETAIL_RETURN_CUSTOMER
			+ " GROUP BY bd.variant_id, bd.price, bd.tax, bd.user_confirm, bd.note", nativeQuery = true)
	List<Object[]> findByBillDetailCustomerOrder(@Param("bill") Bill bill);

	/**
	 * Lấy ra list hóa đơn chi tiết người dùng trả theo hóa đơn
	 * 
	 * @param bill
	 * @return
	 */
	@Query(value = "SELECT bd.variant_id as productVariant, SUM(bd.quantity) as quantity, bd.price as price, bd.tax as tax, SUM(bd.total_money) as totalMoney, bd.user_confirm as userConfirm, bd.note as note FROM bill_details AS bd WHERE bd.bill_id =:bill AND bd.status = "
			+ Constant.STATUS_BILL_DETAIL_RETURN_OK
			+ " GROUP BY bd.variant_id, bd.price, bd.tax, bd.user_confirm, bd.note", nativeQuery = true)
	List<Object[]> findByBillDetailCustomerReturn(Bill bill);

	/**
	 * Lấy ra list hoad đơn chi tiết mà cửa hàng hoàn trả theo hóa đơn
	 * 
	 * @param bill
	 * @return
	 */
	@Query(value = "SELECT bd.variant_id as productVariant, SUM(bd.quantity) as quantity, bd.price as price, bd.tax as tax, SUM(bd.total_money) as totalMoney, bd.user_confirm as userConfirm, bd.note as note, bd.bill_detail_id_parent as billDetailParent  FROM bill_details AS bd WHERE bd.bill_id = :bill AND bd.status = "
			+ Constant.STATUS_BILL_DETAIL_RETURN_CUSTOMER
			+ " GROUP BY bd.variant_id, bd.price, bd.tax, bd.user_confirm, bd.note, bd.bill_detail_id_parent", nativeQuery = true)
	List<Object[]> findByBillDetailStoreReturn(Bill bill);

	/**
	 * Top sản phẩm bán chạy trong tháng x của năm nay
	 * 
	 * @param now
	 * @return
	 */
	@Query("SELECT new TopProductVariantSelling(bd.productVariants, SUM(bd.quantity)) FROM BillDetail bd JOIN Bill b ON bd.bills = b "
			+ "WHERE year(b.createdDate) = year(:date) AND month(b.createdDate) = month(:date) AND b.status = 4 AND (bd.status = 0 OR bd.status = 4) "
			+ "GROUP BY bd.productVariants ORDER BY SUM(bd.quantity) DESC")
	List<TopProductVariantSelling> findTopProductVariantSellingIsMonth(@Param("date") LocalDate now);

	/**
	 * Top sản phẩm bán ế trong tháng x của năm nay
	 * 
	 * @param now
	 * @return
	 */
	@Query("SELECT new TopProductVariantSelling(bd.productVariants, SUM(bd.quantity)) FROM BillDetail bd JOIN Bill b ON bd.bills = b "
			+ "WHERE year(b.createdDate) = year(:date) AND month(b.createdDate) = month(:date) AND b.status = 4 AND (bd.status = 0 OR bd.status = 4) "
			+ "GROUP BY bd.productVariants ORDER BY SUM(bd.quantity) ASC")
	List<TopProductVariantSelling> findTopProductVariantNoSellingIsMonth(@Param("date") LocalDate now);

	/**
	 * Top sản phẩm bán chạy trong năm x
	 * 
	 * @param now
	 * @return
	 */
	@Query("SELECT new TopProductVariantSelling(bd.productVariants, SUM(bd.quantity)) FROM BillDetail bd JOIN Bill b ON bd.bills = b "
			+ "WHERE year(b.createdDate) = year(:date) AND b.status = 4 AND (bd.status = 0 OR bd.status = 4) "
			+ "GROUP BY bd.productVariants ORDER BY SUM(bd.quantity) DESC")
	List<TopProductVariantSelling> findTopProductVariantSellingIsYear(@Param("date") LocalDate now);

	/**
	 * Top sản phẩm bán ế trong năm x
	 * 
	 * @param now
	 * @return
	 */
	@Query("SELECT new TopProductVariantSelling(bd.productVariants, SUM(bd.quantity)) FROM BillDetail bd JOIN Bill b ON bd.bills = b "
			+ "WHERE year(b.createdDate) = year(:date) AND b.status = 4 AND (bd.status = 0 OR bd.status = 4) "
			+ "GROUP BY bd.productVariants ORDER BY SUM(bd.quantity) ASC")
	List<TopProductVariantSelling> findTopProductVariantNoSellingIsYear(@Param("date") LocalDate now);

	/**
	 * Top sản phẩm bán chạy trong khoảng ngày
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	@Query("SELECT new TopProductVariantSelling(bd.productVariants, SUM(bd.quantity)) FROM BillDetail bd JOIN Bill b ON bd.bills = b "
			+ "WHERE (b.createdDate between :start AND :end) AND b.status = 4 AND (bd.status = 0 OR bd.status = 4) "
			+ "GROUP BY bd.productVariants ORDER BY SUM(bd.quantity) DESC")
	List<TopProductVariantSelling> findTopProductVariantSellingIsSevenDay(@Param("start") LocalDateTime start,
			@Param("end") LocalDateTime end);

	/**
	 * Top sản phẩm bán ế trong khoảng ngày
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	@Query("SELECT new TopProductVariantSelling(bd.productVariants, SUM(bd.quantity)) FROM BillDetail bd JOIN Bill b ON bd.bills = b "
			+ "WHERE (b.createdDate between :start AND :end) AND b.status = 4 AND (bd.status = 0 OR bd.status = 4) "
			+ "GROUP BY bd.productVariants ORDER BY SUM(bd.quantity) ASC")
	List<TopProductVariantSelling> findTopProductVariantNoSellingIsSevenDay(LocalDate start, LocalDate end);

}
