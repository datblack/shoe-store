package com.pro2111.service;

import com.pro2111.beans.BillAndBillDetail;
import com.pro2111.beans.BuyRequest;
import com.pro2111.entities.Bill;
import com.pro2111.entities.User;

import java.util.List;

public interface BuyService {

	/**
	 * Tạo bill
	 * 
	 * @param buyRequest
	 * @return
	 */
	Bill createBill(BuyRequest buyRequest);

	/**
	 * Tổng số bill theo billId
	 * 
	 * @param billId
	 * @return
	 */
	Long countBillId(String billId);

	/**
	 * Lấy Bill theo billId
	 * 
	 * @param id
	 * @return
	 */
	List<Bill> findByBillIdLike(String id);

	/**
	 * Lấy list Bill theo người mua
	 * 
	 * @param user
	 * @return
	 */
	List<Bill> getBillsByCusId(User user);

	/**
	 * Cập nhật bil
	 * 
	 * @param billAndBillDetail
	 * @return
	 */
	BillAndBillDetail updateBill(BillAndBillDetail billAndBillDetail);

	/**
	 * Lấy list Bill trả hàng theo user
	 * 
	 * @param user
	 * @return
	 */
	List<Bill> findBillReturnByUser(User user);

	/**
	 * Lấy list Bill không đủ điều kiện trả hàng theo người mua
	 * 
	 * @param user
	 * @return
	 */
	List<Bill> findNotEligibleForReturnByCustomer(User user);

	/**
	 * Hủy hóa đơn
	 * 
	 */
	Bill cancelBill(Bill bill);

}
