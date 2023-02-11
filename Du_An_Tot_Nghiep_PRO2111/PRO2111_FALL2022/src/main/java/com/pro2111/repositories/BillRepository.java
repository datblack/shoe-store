/**
 * DATN_FALL2022, 2022
 * BillRepository.java, BUI_QUANG_HIEU
 */
package com.pro2111.repositories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.beans.RevenueMonthByYearBean;
import com.pro2111.beans.TopCustomerBean;
import com.pro2111.entities.Bill;
import com.pro2111.entities.User;
import com.pro2111.utils.Constant;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public interface BillRepository extends JpaRepository<Bill, String> {

	/**
	 * Lấy list hóa đơn theo Id
	 * 
	 * @param id
	 * @return
	 */
	List<Bill> findByBillIdLike(String id);

	/**
	 * Lấy tất cả hóa đơn
	 * 
	 * @return
	 */
	@Query("SELECT b FROM Bill b ORDER BY createdDate DESC, status ASC")
	List<Bill> getAllBills();

	/**
	 * Lấy list hóa đơn theo status
	 * 
	 * @param status
	 * @return
	 */
	@Query("SELECT b FROM Bill b WHERE b.status =:status ORDER BY createdDate DESC")
	List<Bill> findBillByStatus(@Param("status") int status);

	/**
	 * Lấy tổng số hóa đơn theo Id gần đúng
	 * 
	 * @param billId
	 * @return
	 */
	@Query("SELECT COUNT(b) FROM Bill b WHERE b.billId LIKE %:billId%")
	Long countBillId(@Param("billId") String billId);

	/**
	 * Lấy list hóa đơn theo Id gần đúng
	 * 
	 * @param inputString
	 * @return
	 */
	@Query("SELECT b FROM Bill b WHERE b.billId LIKE %:input% ORDER BY createdDate DESC, status ASC")
	List<Bill> searchApproximateBill(@Param("input") String inputString);

	/**
	 * Lấy list hóa đơn theo tên người mua gần đúng
	 * 
	 * @param inputString
	 * @return List<Bill>
	 */
	@Query("SELECT b FROM Bill b WHERE b.customer.fullName LIKE %:input% ORDER BY createdDate DESC, status ASC")
	List<Bill> searchApproximateCustomer(@Param("input") String inputString);

	/**
	 * Lấy list hóa đơn theo số điện thoại người mua gần đúng
	 * 
	 * @param inputString
	 * @return List<Bill>
	 */
	@Query("SELECT b FROM Bill b WHERE b.phone LIKE %:input% ORDER BY createdDate DESC, status ASC")
	List<Bill> searchApproximatePhone(@Param("input") String inputString);

	/**
	 * Lấy list hóa đơn theo địa chỉ gần đúng
	 * 
	 * @param inputString
	 * @return List<Bill>
	 */
	@Query("SELECT b FROM Bill b WHERE b.address LIKE %:input% ORDER BY createdDate DESC, status ASC")
	List<Bill> searchApproximateAddress(@Param("input") String inputString);

	/**
	 * Lấy hóa đơn theo tên người bán gần đúng
	 * 
	 * @param inputString
	 * @return List<Bill>
	 */
	@Query("SELECT b FROM Bill b WHERE b.user.fullName LIKE %:input% ORDER BY createdDate DESC, status ASC")
	List<Bill> searchApproximateSeller(@Param("input") String inputString);

	/**
	 * Lấy list hóa đơn đủ điều kiện trả hàng
	 * 
	 * @return
	 */
	@Query("SELECT b FROM Bill b WHERE datediff(now(), b.successDate) <=10 AND b.status = "
			+ Constant.STATUS_BILL_SUCCESS
			+ " AND NOT EXISTS (SELECT 1 FROM BillDetail bd WHERE bd.bills = b AND bd.status != 0) ORDER BY b.createdDate DESC")
	List<Bill> findBillEligibleForReturn();

	/**
	 * Lấy list hóa đơn chưa trả hàng
	 * 
	 * @return
	 */
	@Query("SELECT b FROM Bill b WHERE EXISTS (SELECT 1 FROM BillDetail bd WHERE bd.bills = b AND bd.status != "
			+ Constant.STATUS_BILL_DETAIL_OK + ") ORDER BY b.createdDate DESC")
	List<Bill> findBillReturnInvoices();

	/**
	 * Lấy list hóa đơn theo customer
	 * 
	 * @param user
	 * @return
	 */
	@Query("SELECT b FROM Bill b WHERE b.customer LIKE :user ORDER BY b.createdDate DESC")
	List<Bill> findByCustomerLike(@Param("user") User user);

	/**
	 * Doanh thu các tháng trong năm
	 * 
	 * @param year
	 * @return
	 */
	@Query("SELECT new RevenueMonthByYearBean(month(b.createdDate), SUM(b.totalMoney)) FROM Bill b "
			+ "WHERE year(b.createdDate) = :year AND b.status = " + Constant.STATUS_BILL_SUCCESS
			+ " GROUP BY month(b.createdDate) ORDER BY month(b.createdDate) ASC")
	List<RevenueMonthByYearBean> getMonthByYearRevenue(@Param("year") int year);

	/**
	 * Doanh thu theo năm
	 * 
	 * @param year
	 * @return
	 */
	@Query("SELECT SUM(b.totalMoney) FROM Bill b WHERE b.status = " + Constant.STATUS_BILL_SUCCESS
			+ " AND year(b.createdDate) = :year GROUP BY year(b.createdDate)")
	BigDecimal getRevenueYear(@Param("year") int year);

	/**
	 * Doanh thu theo năm, bill_type
	 * 
	 * @param year
	 * @param billType
	 * @return
	 */
	@Query("SELECT SUM(b.totalMoney) FROM Bill b WHERE b.status = " + Constant.STATUS_BILL_SUCCESS
			+ " AND b.billType = :billType AND year(b.createdDate) = :year GROUP BY year(b.createdDate)")
	BigDecimal getRevenueYearByBillType(@Param("year") int year, @Param("billType") int billType);

	/**
	 * Doanh thu theo tháng
	 * 
	 * @param date
	 * @return
	 */
	@Query("SELECT SUM(b.totalMoney) FROM Bill b WHERE b.status = " + Constant.STATUS_BILL_SUCCESS
			+ " AND month(b.createdDate) = month(:createdDate) AND year(b.createdDate) = year(:createdDate)"
			+ " GROUP BY month(b.createdDate)")
	BigDecimal getMonthlyRevenue(@Param("createdDate") LocalDate date);

	/**
	 * Doanh thu theo tháng, bill_type
	 * 
	 * @param date
	 * @param billType
	 * @return
	 */
	@Query("SELECT SUM(b.totalMoney) FROM Bill b WHERE b.status = " + Constant.STATUS_BILL_SUCCESS
			+ " AND b.billType = :billType AND month(b.createdDate) = month(:createdDate) AND year(b.createdDate) = year(:createdDate)"
			+ " GROUP BY month(b.createdDate)")
	BigDecimal getMonthlyRevenueByBillType(@Param("createdDate") LocalDate date, @Param("billType") int billType);

	/**
	 * Doanh thu theo ngày
	 * 
	 * @param date
	 * @return
	 */
	@Query("SELECT SUM(b.totalMoney) FROM Bill b WHERE b.status = " + Constant.STATUS_BILL_SUCCESS
			+ " AND day(b.createdDate) = day(:createdDate) AND month(b.successDate) = month(:createdDate)"
			+ " AND year(b.createdDate) = year(:createdDate) GROUP BY day(b.createdDate)")
	BigDecimal getRevenueToday(@Param("createdDate") LocalDate date);

	/**
	 * Doanh thu theo ngày, bill_type
	 * 
	 * @param date
	 * @param billType
	 * @return
	 */
	@Query("SELECT SUM(b.totalMoney) FROM Bill b WHERE b.status = " + Constant.STATUS_BILL_SUCCESS
			+ " AND b.billType = :billType AND day(b.createdDate) = day(:createdDate) AND month(b.successDate) = month(:createdDate)"
			+ " AND year(b.createdDate) = year(:createdDate) GROUP BY day(b.createdDate)")
	BigDecimal getRevenueTodayByBillType(@Param("createdDate") LocalDate date, @Param("billType") int billType);

	/**
	 * Doanh thu theo khoảng ngày
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Query("SELECT SUM(b.totalMoney) FROM Bill b WHERE b.status = " + Constant.STATUS_BILL_SUCCESS
			+ " AND (b.createdDate between :startDate AND :endDate)")
	BigDecimal getRevenueByAboutDays(@Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate);

	/**
	 * Doanh thu theo khoảng ngày, billType
	 * 
	 * @param startDate
	 * @param endDate
	 * @param billType
	 * @return
	 */
	@Query("SELECT SUM(b.totalMoney) FROM Bill b WHERE b.status = " + Constant.STATUS_BILL_SUCCESS
			+ " AND b.billType = :billType AND (b.createdDate between :startDate AND :endDate)")
	BigDecimal getRevenueByAboutDaysByBillType(@Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate, @Param("billType") int billType);

	/**
	 * Lấy ra tất cả các năm có bill
	 * 
	 * @return
	 */
	@Query("SELECT YEAR(b.createdDate) FROM Bill b GROUP BY YEAR(b.createdDate) ORDER BY YEAR(b.createdDate) DESC")
	List<Integer> getAllYearInBill();

	/**
	 * Tổng số Bill theo status
	 * 
	 * @param status
	 * @return
	 */
	@Query("SELECT COUNT(b) FROM Bill b WHERE b.status = :status")
	Integer countAllBillByStatus(@Param("status") int status);

	/**
	 * Tổng số Bill hoàn thành bán online
	 * 
	 * @return
	 */
	@Query("SELECT COUNT(b) FROM Bill b WHERE b.status = " + Constant.STATUS_BILL_SUCCESS + " AND billType = "
			+ Constant.BILL_TYPE_ONLINE)
	Integer countAllBillSuccessByOnline();

	/**
	 * Tổng số Bill hoàn thành bán offline
	 * 
	 * @return
	 */
	@Query("SELECT COUNT(b) FROM Bill b WHERE b.status = " + Constant.STATUS_BILL_SUCCESS + " AND billType = "
			+ Constant.BILL_TYPE_OFFLINE)
	Integer countAllBillSuccessByOffline();

	/**
	 * Lấy list hóa đơn trả hàng thành công
	 * 
	 * @param user
	 * @return
	 */
	@Query("SELECT b FROM Bill b WHERE b.customer = :user AND EXISTS (SELECT 1 FROM BillDetail bd WHERE bd.bills = b AND bd.status != "
			+ Constant.STATUS_BILL_DETAIL_OK + ")")
	List<Bill> findBillReturnByUser(@Param("user") User user);

	/**
	 * Lấy list hóa đơn chờ xác nhận trả hàng
	 * 
	 * @return
	 */
	@Query("SELECT b FROM Bill b WHERE EXISTS (SELECT 1 FROM BillDetail bd WHERE bd.bills = b AND bd.status = "
			+ Constant.STATUS_BILL_DETAIL_CONFIRM_RETURN + ") ORDER BY b.createdDate DESC")
	List<Bill> findBillByWaitConfirmReturn();

	/**
	 * Lấy list hóa đơn hủy trả hàng
	 * 
	 * @return
	 */
	@Query("SELECT b FROM Bill b WHERE EXISTS (SELECT 1 FROM BillDetail bd WHERE bd.bills = b AND bd.status = "
			+ Constant.STATUS_BILL_DETAIL_RETURN_FAIL + ") ORDER BY b.createdDate DESC")
	List<Bill> findBillByCancelReturn();

	/**
	 * Lấy list hóa đơn trả hàng thành công
	 * 
	 * @return
	 */
	@Query("SELECT b FROM Bill b WHERE EXISTS (SELECT 1 FROM BillDetail bd WHERE bd.bills = b AND bd.status = "
			+ Constant.STATUS_BILL_DETAIL_RETURN_OK + ") ORDER BY b.createdDate DESC")
	List<Bill> findBillBySuccessReturn();

	/**
	 * Lấy ra list hóa đơn không được trả hàng theo customer
	 * 
	 * @return
	 */
	@Query("SELECT b FROM Bill b WHERE b.customer = :customer AND b.status = " + Constant.STATUS_BILL_SUCCESS
			+ " AND EXISTS (SELECT 1 FROM BillDetail bd WHERE bd.bills = b AND bd.status != 0) ORDER BY b.createdDate DESC")
	List<Bill> findNotEligibleForReturnByCustomer(@Param("customer") User user);

	/**
	 * Lấy ra list KH mua hàng nhiều nhất và tổng tiền họ mua theo năm
	 */
	@Query("SELECT new TopCustomerBean(b.customer, SUM(b.totalMoney)) FROM Bill b WHERE b.status = "
			+ Constant.STATUS_BILL_SUCCESS
			+ " AND year(b.successDate) = :year GROUP BY b.customer ORDER BY SUM(b.totalMoney) DESC")
	List<TopCustomerBean> findByTopCustomerSellingByYear(@Param("year") int year);

	/**
	 * Lấy ra tổng hóa đơn hoàn thành theo ngày
	 * 
	 * @param status
	 * @param date
	 * @return
	 */
	@Query("SELECT COUNT(b) FROM Bill b WHERE b.status = " + Constant.STATUS_BILL_SUCCESS
			+ " AND year(b.successDate) = year(:date) AND month(b.successDate) = month(:date) AND day(b.successDate) = day(:date)")
	long countBillBySuccessInDay(@Param("date") LocalDate date);

	/**
	 * Lấy ra tổng hóa đơn hủy theo ngày
	 * 
	 * @param status
	 * @param date
	 * @return
	 */
	@Query("SELECT COUNT(b) FROM Bill b WHERE b.status = " + Constant.STATUS_BILL_CANCEL
			+ " AND year(b.cancelDate) = year(:date) AND month(b.cancelDate) = month(:date) AND day(b.cancelDate) = day(:date)")
	long countBillByCancelInDay(@Param("date") LocalDate date);

	/**
	 * Lấy ra tổng hóa đơn hoàn thành theo tháng
	 * 
	 * @param date
	 * @return
	 */
	@Query("SELECT COUNT(b) FROM Bill b WHERE b.status = " + Constant.STATUS_BILL_SUCCESS
			+ " AND year(b.successDate) = year(:date) AND month(b.successDate) = month(:date)")
	long countBillBySuccessInMonth(@Param("date") LocalDate date);

	/**
	 * Lấy ra tổng hóa đơn hủy theo tháng
	 * 
	 * @param date
	 * @return
	 */
	@Query("SELECT COUNT(b) FROM Bill b WHERE b.status = " + Constant.STATUS_BILL_CANCEL
			+ " AND year(b.cancelDate) = year(:date) AND month(b.cancelDate) = month(:date)")
	long countBillByCancelInMonth(@Param("date") LocalDate date);

	/**
	 * Lấy ra tổng hóa đơn hoàn thành trong khoảng ngày
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	@Query("SELECT COUNT(b) FROM Bill b WHERE b.status = " + Constant.STATUS_BILL_SUCCESS
			+ " AND (b.successDate between :start AND :end)")
	long countBillBySuccessInSevenDay(@Param("start") LocalDate start, @Param("end") LocalDate end);

	/**
	 * Lấy ra tổng hóa đơn hủy trong khoảng ngày
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	@Query("SELECT COUNT(b) FROM Bill b WHERE b.status = " + Constant.STATUS_BILL_CANCEL
			+ " AND (b.cancelDate between :start AND :end)")
	long countBillByCancelInSevenDay(@Param("start") LocalDate start, @Param("end") LocalDate end);

	/**
	 * Lấy ra tổng số hóa đơn chờ xác nhận
	 * 
	 * @return
	 */
	@Query("SELECT COUNT(b) FROM Bill b WHERE b.status = " + Constant.STATUS_BILL_WAIT_FOR_CONFIRMATION)
	long countBillWaitConfirm();

	/**
	 * Lấy ra tổng số hóa đơn chờ xác nhận trả hàng
	 * 
	 * @return
	 */
	@Query("SELECT COUNT(b) FROM Bill b WHERE EXISTS (SELECT 1 FROM BillDetail bd WHERE bd.bills = b AND bd.status = "
			+ Constant.STATUS_BILL_DETAIL_CONFIRM_RETURN + ")")
	long countBillWaitConfirmReturn();

	/**
	 * Lấy ra tổng số hóa đơn chờ thanh toán
	 * 
	 * @return
	 */
	@Query("SELECT COUNT(b) FROM Bill b WHERE b.status = " + Constant.STATUS_BILL_WAIT_FOR_PAY)
	long countBillWaitForPay();

	/**
	 * Lấy ra tổng hóa đơn chờ giao
	 * 
	 * @return
	 */
	@Query("SELECT COUNT(b) FROM Bill b WHERE b.status = " + Constant.STATUS_BILL_WAIT_FOR_DELIVERY)
	Long countBillWaitForDelivery();

}
