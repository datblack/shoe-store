/**
 * DATN_FALL2022, 2022
 * DashboardService.java, BUI_QUANG_HIEU
 */
package com.pro2111.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.pro2111.beans.RevenueMonthByYearBean;
import com.pro2111.beans.RevenueOnlineOfflineAllBean;
import com.pro2111.beans.TopCustomerBean;
import com.pro2111.beans.TopProductVariantSelling;
import com.pro2111.beans.TurnoverRateBean;
import com.pro2111.entities.ProductVariant;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public interface DashboardService {

	/**
	 * Lấy doanh thu các tháng trong năm
	 * 
	 * @param date
	 * @return
	 */
	List<RevenueMonthByYearBean> getElementMonthByYear(int year);

	/**
	 * Lấy doanh thu theo tháng
	 * 
	 * @param date
	 * @return
	 */
	BigDecimal getMonthlyRevenue(LocalDate date);

	/**
	 * Lấy doanh thu theo ngày
	 * 
	 * @param date
	 * @return
	 */
	BigDecimal getRevenueToday(LocalDate date);

	/**
	 * Lấy doanh thu offline, online và tổng cả online và offline
	 * 
	 * @param date
	 * @return
	 */
	RevenueOnlineOfflineAllBean getRevenueYear(int year);

	/**
	 * Lấy doanh thu theo khoảng ngày
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	BigDecimal getRevenueByAboutDays(LocalDate startDate, LocalDate endDate);

	/**
	 * Lấy list năm mà có hóa đơn
	 * 
	 * @return
	 */
	List<Integer> getAllYearInBill();

	/**
	 * Lấy tỉ lệ doanh thu online và offline
	 * 
	 * @return
	 */
	TurnoverRateBean getTurnoverRate();

	/**
	 * Lấy doanh thu ngày theo loại hóa đơn
	 * 
	 * @param date
	 * @param billType
	 * @return
	 */
	BigDecimal getRevenueTodayByBillType(LocalDate date, int billType);

	/**
	 * Lấy doanh thu theo khoảng ngày và loại hóa đơn
	 * 
	 * @param start
	 * @param end
	 * @param billType
	 * @return
	 */
	BigDecimal getRevenueByAboutDaysByBillType(LocalDate start, LocalDate end, int billType);

	/**
	 * Lấy doanh thu tháng theo loại hóa đơn
	 * 
	 * @param date
	 * @param billType
	 * @return
	 */
	BigDecimal getMonthlyRevenueByBillType(LocalDate date, int billType);

	/**
	 * Lấy list sản phẩm có số lượng <= x
	 * 
	 * @param quantity
	 * @return
	 */
	List<ProductVariant> getProductVariantByLessQuantity(int quantity);

	/**
	 * Lấy list sản phẩm bán chạy theo tháng x
	 * 
	 * @param top
	 * @param date
	 * @return
	 * @throws ReflectiveOperationException
	 */
	List<TopProductVariantSelling> findTopProductVariantSellingIsMonth(int top, LocalDate date)
			throws ReflectiveOperationException;

	/**
	 * Lấy list sản phẩm bán ế trong tháng x
	 * 
	 * @param top
	 * @param date
	 * @return
	 * @throws ReflectiveOperationException
	 */
	List<TopProductVariantSelling> findTopProductVariantNoSellingIsMonth(int top, LocalDate date)
			throws ReflectiveOperationException;

	/**
	 * Lấy list sản phẩm bán chạy trong năm x
	 * 
	 * @param top
	 * @param date
	 * @return
	 * @throws ReflectiveOperationException
	 */
	List<TopProductVariantSelling> findTopProductVariantSellingIsYear(int top, LocalDate date)
			throws ReflectiveOperationException;

	/**
	 * Lấy list sản phẩm bán ế trong năm x
	 * 
	 * @param top
	 * @param date
	 * @return
	 * @throws ReflectiveOperationException
	 */
	List<TopProductVariantSelling> findTopProductVariantNoSellingIsYear(int top, LocalDate date)
			throws ReflectiveOperationException;

	/**
	 * Lấy list sản phẩm bán chạy trong khoảng ngày
	 * 
	 * @param top
	 * @param start
	 * @param end
	 * @return
	 */
	List<TopProductVariantSelling> findTopProductVariantSellingIsSevenDay(int top, LocalDateTime start, LocalDateTime end)
			throws ReflectiveOperationException;

	/**
	 * Lấy list sản phẩm bán ế trong khoảng ngày
	 * 
	 * @param top
	 * @param start
	 * @param end
	 * @return
	 */
	List<TopProductVariantSelling> findTopProductVariantNoSellingIsSevenDay(int top, LocalDate start, LocalDate end)
			throws ReflectiveOperationException;

	/**
	 * Lấy list khách hàng mua hàng nhiều nhất theo top và năm
	 * 
	 * @param top
	 * @param year
	 * @return
	 */
	List<TopCustomerBean> findTopCustomer(int top, int year);

	/**
	 * Lấy tổng số hóa đơn theo trạng thái theo kiểu ngày
	 * 
	 * @param status
	 * @param date
	 * @return
	 */
	long countBillByStatusInDay(int status, LocalDate date);

	/**
	 * Lấy tổng số hóa đơn theo trạng thái theo kiểu tháng
	 * 
	 * @param status
	 * @param date
	 * @return
	 */
	long countBillByStatusInMonth(int status, LocalDate date);

	/**
	 * Lấy tổng số hóa đơn theo trạng thái 7 ngày qua
	 * 
	 * @param status
	 * @param start
	 * @param end
	 * @return
	 */
	long countBillByStatusInSevenDay(int status, LocalDate start, LocalDate end);

	/**
	 * Lấy tổng số hóa đơn chờ xác nhận
	 * 
	 * @return
	 */
	long countBillWaitConfirm();

	/**
	 * Lấy tổng số hóa đơn chờ xác nhận trả hàng
	 * 
	 * @return
	 */
	long countBillWaitConfirmReturn();

}
