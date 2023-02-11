/**
 * DATN_FALL2022, 2022
 * BillDetailService.java, BUI_QUANG_HIEU
 */
package com.pro2111.service;

import java.util.List;

import com.pro2111.beans.BillDetailReturnBean;
import com.pro2111.beans.BillDetailReturnCustomerViewBean;
import com.pro2111.beans.BillDetailViewBean;
import com.pro2111.entities.Bill;
import com.pro2111.entities.BillDetail;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public interface BillDetailService {

	/**
	 * Tạo mới BillDetail
	 * 
	 * @param billDetail
	 * @return
	 */
	BillDetail createdBillDetail(BillDetail billDetail);

	/**
	 * Lấy BillDetail theo Bill
	 * 
	 * @param bill
	 * @return List<BillDetail>
	 */
	List<BillDetail> findByBill(Bill bill);

	/**
	 * Lấy BillDetail chưa trả theo Bill
	 * 
	 * @param bill
	 * @return
	 */
	List<BillDetail> getSttOkByBill(Bill bill);

	/**
	 * Cập nhật BillDetail
	 * 
	 * @param billDetail
	 * @return
	 */
	BillDetail updateBillDetail(BillDetail billDetail);

	/**
	 * Trả hàng phía Admin
	 * 
	 * @param billDetailReturnBeans
	 * @return
	 */
	List<BillDetail> returnBillDetailOfAdmin(List<BillDetailReturnBean> billDetailReturnBeans);

	/**
	 * Xóa BillDetail
	 * 
	 * @param billDetail
	 * @return
	 */
	BillDetail deleteBillDetail(BillDetail billDetail);

	/**
	 * Lấy list BillDetail theo Bill và trạng thái
	 * 
	 * @param bill
	 * @return
	 */
	List<BillDetail> findByBillDetailReturnInvoicesAndStatus(Bill bill, int status);

	/**
	 * Lấy list BillDetail đủ điều kiện trả hàng theo BillDetail
	 * 
	 * @param bill
	 * @return
	 */
	List<BillDetail> findByBillDetailEligibleForReturn(Bill bill);

	/**
	 * Lấy BillDetail theo Id
	 * 
	 * @param detailBillId
	 * @return
	 */
	BillDetail findById(String detailBillId);

	/**
	 * Lấy list BillDetail theo người mua
	 * 
	 * @param billDetails
	 * @return
	 */
	List<BillDetail> returnProductByCustomer(List<BillDetail> billDetails);

	/**
	 * Xác nhận BillDetail trả hàng
	 * 
	 * @param billDetailReturnBeans
	 * @return
	 */
	List<BillDetail> verifyReturn(List<BillDetailReturnBean> billDetailReturnBeans);

	/**
	 * Từ chối BillDetail trả hàng
	 * 
	 * @param billDetails
	 * @return
	 */
	List<BillDetail> prohibitReturn(List<BillDetail> billDetails);

	/**
	 * Lấy BillDetailViewBean theo KH mua
	 * 
	 * @param bill
	 * @return
	 */
	List<BillDetailViewBean> findByBillDetailCustomerBuy(Bill bill)
			throws NumberFormatException, ReflectiveOperationException;

	/**
	 * Lấy BillDetailViewBean theo KH trả
	 * 
	 * @param bill
	 * @return
	 */
	List<BillDetailViewBean> findByBillDetailCustomerReturn(Bill bill)
			throws NumberFormatException, ReflectiveOperationException;

	/**
	 * Lấy BillDetailReturnCustomerViewBean theo cửa hàng trả cho khách
	 * 
	 * @param bill
	 * @return
	 */
	List<BillDetailReturnCustomerViewBean> findByBillDetailStoreReturn(Bill bill)
			throws NumberFormatException, ReflectiveOperationException;

}
