/**
 * DATN_FALL2022, 2022
 * BillDetailRestControllerAdmin.java, BUI_QUANG_HIEU
 */
package com.pro2111.restcontrollers.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro2111.beans.BillDetailReturnBean;
import com.pro2111.beans.BillDetailReturnCustomerViewBean;
import com.pro2111.beans.BillDetailViewBean;
import com.pro2111.entities.Bill;
import com.pro2111.entities.BillDetail;
import com.pro2111.service.BillDetailService;
import com.pro2111.utils.Common;
import com.pro2111.utils.Constant;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("admin/rest/bill-details")
public class BillDetailRestControllerAdmin {

	@Autowired
	private BillDetailService billDetailService;

	/**
	 * Tạo 1 billDetail
	 * @param billDetail
	 * @return
	 */
	@PostMapping
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<BillDetail> createBillDetail(@Valid @RequestBody BillDetail billDetail) {
		try {
			return ResponseEntity.ok(billDetailService.createdBillDetail(billDetail));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy 1 billDetail theo id
	 * @param bill
	 * @return
	 */
	@GetMapping("find-by-bill/{idBill}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<BillDetail>> findByBill(@PathVariable("idBill") Bill bill) {
		try {
			return ResponseEntity.ok(billDetailService.findByBill(bill));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-by-bill-detail-eligible-for-return/{idBill}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<BillDetail>> findByBillDetailEligibleForReturn(@PathVariable("idBill") Bill bill) {
		try {
			List<BillDetail> billDetails = billDetailService.findByBillDetailEligibleForReturn(bill);
//			for (int i = billDetails.size() - 1; i >= 0; i--) {
//				if (billDetails.get(i).getStatus() != 0) {
//					billDetails.remove(i);
//				}
//			}
			return ResponseEntity.ok(billDetails);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Xác nhận trả hàng cho khách hàng
	 */
	@PostMapping("verify-return")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<BillDetail>> verifyReturn(
			@RequestBody List<BillDetailReturnBean> billDetailReturnBeans) {
		try {
			return ResponseEntity.ok(billDetailService.verifyReturn(billDetailReturnBeans));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Hủy trả hàng cho khách hàng
	 * @param billDetails
	 * @return
	 */
	@PostMapping("prohibit-return")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<BillDetail>> prohibitionReturn(@RequestBody List<BillDetail> billDetails) {
		try {
			return ResponseEntity.ok(billDetailService.prohibitReturn(billDetails));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Tìm kiếm billDetail theo id và trạng thái bill detail
	 * @param bill
	 * @param statusString
	 * @return
	 */
	@GetMapping("find-by-bill-detail-return-invoices-and-status/{idBill}/{status}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<BillDetail>> findByBillDetailReturnInvoices(@PathVariable("idBill") Bill bill,
			@PathVariable("status") String statusString) {
		try {
			int status = Common.convertStringToInt(statusString, Constant.STATUS_BILL_DETAIL_RETURN_OK);
			List<BillDetail> billDetails = billDetailService.findByBillDetailReturnInvoicesAndStatus(bill, status);
//			for (int i = billDetails.size() - 1; i >= 0; i--) {
//				if (billDetails.get(i).getStatus() == 0) {
//					billDetails.remove(i);
//				}
//			}
			return ResponseEntity.ok(billDetails);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Cập nhật billDetail
	 * @param billDetail
	 * @return
	 */
	@PutMapping()
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<BillDetail> updateBillDetail(@Valid @RequestBody BillDetail billDetail) {
		try {
//			if (billDetail.getBills().getStatus() == 4 || billDetail.getBills().getStatus() == 6) {
//				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//			}
			return ResponseEntity.ok(billDetailService.updateBillDetail(billDetail));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy ra những sản phẩm khách hàng mua
	 * @param bill
	 * @return
	 */
	@GetMapping("find-by-bill-detail-customer-buy/{idBill}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<BillDetailViewBean>> findByBillDetailCustomesBuy(@PathVariable("idBill") Bill bill) {
		try {
			return ResponseEntity.ok(billDetailService.findByBillDetailCustomerBuy(bill));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy ra những sản phẩm khách hàng trả
	 * @param bill
	 * @return
	 */
	@GetMapping("find-by-bill-detail-customer-return/{idBill}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<BillDetailViewBean>> findByBillDetailCustomerReturn(@PathVariable("idBill") Bill bill) {
		try {
			return ResponseEntity.ok(billDetailService.findByBillDetailCustomerReturn(bill));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy ra những sản phẩm cửa hàng trả cho khách
	 * @param bill
	 * @return
	 */
	@GetMapping("find-by-bill-detail-store-return/{idBill}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<BillDetailReturnCustomerViewBean>> findByBillDetailStoreReturn(@PathVariable("idBill") Bill bill) {
		try {
			return ResponseEntity.ok(billDetailService.findByBillDetailStoreReturn(bill));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Tạo trả hàng tại quầy
	 * @param billDetailReturnBeans
	 * @return
	 */
	@PostMapping("return-bill-detail-of-admin")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<BillDetail>> returnBillDetailOfAdmin(
			@Valid @RequestBody List<BillDetailReturnBean> billDetailReturnBeans) {
		try {

			for (int i = 0; i < billDetailReturnBeans.size(); i++) {
				BillDetail detail = billDetailReturnBeans.get(i).getBillDetail();
				BillDetail detailOld = billDetailService.findById(detail.getDetailBillId());
				if (detail.getNote() == null || detail.getNote() == "" || detail.getNote().length() > 255) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
				} else if (detail.getQuantity() < 0 || detail.getQuantity() > detailOld.getQuantity()) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
				}
			}
			return ResponseEntity.ok(billDetailService.returnBillDetailOfAdmin(billDetailReturnBeans));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Xóa billDetail theo id
	 * @param billDetail
	 * @return
	 */
	@DeleteMapping("{idBillDetail}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<BillDetail> deleteBillDetail(@PathVariable("idBillDetail") BillDetail billDetail) {
		try {
			List<BillDetail> lstDetails = billDetailService.findByBill(billDetail.getBills());
			if (lstDetails.size() == 1) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			return ResponseEntity.ok(billDetailService.deleteBillDetail(billDetail));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
