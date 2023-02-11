package com.pro2111.restcontrollers.user;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro2111.beans.BillAndBillDetail;
import com.pro2111.beans.BuyRequest;
import com.pro2111.entities.Bill;
import com.pro2111.entities.User;
import com.pro2111.service.BuyService;
import com.pro2111.utils.Constant;

@RestController
@CrossOrigin("*")
@RequestMapping("user/rest/bills")
public class BillRestControllerUser {

	@Autowired
	private BuyService buyService;

	/**
	 * Tạo hóa đơn của hàng
	 * 
	 * @param buyRequest
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Bill> createBill(@Valid @RequestBody BuyRequest buyRequest) {
		try {
			buyRequest.getBill().setBillId(getBillIdString());
			return ResponseEntity.ok(buyService.createBill(buyRequest));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy list hóa đơn theo id khách hàng
	 * 
	 * @param user
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<List<Bill>> findBill(@PathVariable("id") User user) {
		try {
			return ResponseEntity.ok(buyService.getBillsByCusId(user));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy list hóa đơn không được phép trả hàng theo id khách hàng
	 * 
	 * @param user
	 * @return
	 */
	@GetMapping("find-not-eligible-for-return-by-customer/{userId}")
	public ResponseEntity<List<Bill>> findNotEligibleForReturnByCustomer(@PathVariable("userId") User user) {
		try {
			return ResponseEntity.ok(buyService.findNotEligibleForReturnByCustomer(user));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Trả hàng
	 * 
	 * @param billAndBillDetail
	 * @return
	 */
	@PutMapping("/update")
	public ResponseEntity<BillAndBillDetail> updateBill(@RequestBody BillAndBillDetail billAndBillDetail) {
		try {
			return ResponseEntity.ok(buyService.updateBill(billAndBillDetail));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy list hóa đơn đã từng trả hàng theo id khách hàng
	 * 
	 * @param user
	 * @return
	 */
	@GetMapping("find-bill-return-by-user/{userId}")
	public ResponseEntity<List<Bill>> findBillReturnByUser(@PathVariable("userId") User user) {
		try {
			return ResponseEntity.ok(buyService.findBillReturnByUser(user));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Hủy hóa đơn
	 */
	@PutMapping("cancel-bill")
	public ResponseEntity<Bill> cancelBill(@RequestBody Bill bill) {
		try {
			if (bill.getStatus() != Constant.STATUS_BILL_WAIT_FOR_CONFIRMATION) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			} else {
				return ResponseEntity.ok(buyService.cancelBill(bill));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	public String getBillIdString() {
		String billId = "";
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
			Date d = new Date();
			billId = dateFormat.format(d);

			long countBill = buyService.countBillId(billId);

			boolean dup = false;
			do {
				if (countBill > 998) {
					billId = billId + (countBill + 1);
				} else if (countBill > 98) {
					billId = billId + "0" + (countBill + 1);
				} else if (countBill > 8) {
					billId = billId + "00" + (countBill + 1);
				} else {
					billId = billId + "000" + (countBill + 1);
				}
				List<Bill> list = buyService.findByBillIdLike(billId);
				if (list.size() > 0) {
					dup = true;
					countBill++;
					billId = dateFormat.format(d);
				} else {
					dup = false;
				}
				billId = "HD" + billId;
			} while (dup);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Lỗi số hóa đơn");
			billId = "";
		}
		return billId;
	}

}
