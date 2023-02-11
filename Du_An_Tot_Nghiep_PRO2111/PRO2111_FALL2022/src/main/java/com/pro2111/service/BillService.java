/**
 * DATN_FALL2022, 2022
 * BillService.java, BUI_QUANG_HIEU
 */
package com.pro2111.service;

import java.util.List;

import com.pro2111.beans.BillAndBillDetail;
import com.pro2111.beans.StatusBillBean;
import com.pro2111.entities.Bill;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public interface BillService {

	/**
	 * Lấy tổng Bill theo id
	 * 
	 * @param billId
	 * @return Long countBillId
	 */
	Long countBillId(String billId);

	/**
	 * Lấy list Bill theo Id
	 * 
	 * @param id
	 * @return List<Bill>
	 */
	List<Bill> findByBillIdLike(String id);

	/**
	 * 
	 * Tạo mới Bill
	 * 
	 * @param bill
	 * @return Bill
	 */
	Bill createBill(Bill bill);

	/**
	 * Tạo mới Bill và BillDetail
	 * 
	 * @param billAndBillDetail
	 * @return Bill
	 */
	Bill createdBillAndBillDetail(BillAndBillDetail billAndBillDetail);

	/**
	 * Lấy ra tất cả Bill
	 * 
	 * @return List<Bill>
	 */
	List<Bill> getAllBill();

	Bill findById(String id);

	/**
	 * Lấy list Bill theo idBill gần đúng
	 * 
	 * @return List<Bill>
	 */
	List<Bill> searchApproximateBill(String inputString);

	/**
	 * Lấy list Bill theo tên người mua gần đúng
	 * 
	 * @param inputString
	 * @return
	 */
	List<Bill> searchApproximateCustomer(String inputString);

	/**
	 * Lấy list Bill theo sđt người mua gần đúng
	 * 
	 * @param inputString
	 * @return
	 */
	List<Bill> searchApproximatePhone(String inputString);

	/**
	 * Lấy list Bill theo địa chỉ người nhận gần đúng
	 * 
	 * @param inputString
	 * @return
	 */
	List<Bill> searchApproximateAddress(String inputString);

	/**
	 * Lấy list Bill theo tên nhân viên gần đúng
	 * 
	 * @param inputString
	 * @return
	 */
	List<Bill> searchApproximateSeller(String inputString);

	/**
	 * Cập nhật Bill từ BillAndBillDetail
	 * 
	 * @param idBill
	 * @return
	 */
	Bill updateBill(BillAndBillDetail bill);

	/**
	 * Lấy list Bill đủ điều kiện trả hàng
	 * 
	 * @return
	 */
	List<Bill> findBillEligibleForReturn();

	/**
	 * Lấy list Bill đã từng trả hàng
	 * 
	 * @return
	 */
	List<Bill> findBillReturnInvoices();

	/**
	 * Lấy list Bill theo trạng thái
	 * 
	 * @param status
	 * @return
	 */
	List<Bill> getBillByStatus(int status);

	/**
	 * Cập nhật trạng thái Bill
	 * 
	 * @param bill
	 * @return
	 */
	List<Bill> updateStatusBill(List<StatusBillBean> statusBillBeans);

	/**
	 * Lấy list Bill chờ xác nhận trả hàng
	 * 
	 * @return
	 */
	List<Bill> findBillByWaitConfirmReturn();

	/**
	 * Lấy list Bill hủy trả hàng
	 * 
	 * @return
	 */
	List<Bill> findBillByCancelReturn();

	/**
	 * Lấy list Bill trả hàng thành công
	 * 
	 * @return
	 */
	List<Bill> findBillBySuccessReturn();

	/**
	 * Lấy list Bill chờ xác nhận
	 * 
	 * @return
	 */
	Long countBillWaitForConfirmation();

	/**
	 * Lấy tổng hóa đơn chờ thanh toán
	 */
	Long countBillWaitForPay();

	/**
	 * Lấy ra tổng hóa đơn chờ giao
	 */
	Long countBillWaitForDelivery();
}
