package com.pro2111.restcontrollers.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro2111.entities.Bill;
import com.pro2111.entities.BillDetail;
import com.pro2111.service.BillDetailService;

@RestController
@CrossOrigin("*")
@RequestMapping("user/rest/billdetails")
public class BillDetailRestController {

	@Autowired
	private BillDetailService billDetailService;

	/**
	 * Lấy billDetail theo Id
	 * 
	 * @param bill
	 * @return
	 */
	@GetMapping("{id}")
	public ResponseEntity<List<BillDetail>> getBillDetailByBill(@PathVariable("id") Bill bill) {
		try {
			return ResponseEntity.ok(billDetailService.findByBill(bill));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy billDetail chưa trả hàng
	 * 
	 * @param bill
	 * @return
	 */
	@GetMapping("noReturn/{billId}")
	public ResponseEntity<List<BillDetail>> getAllSttOkByBillId(@PathVariable("billId") Bill bill) {
		try {
			return ResponseEntity.ok(billDetailService.getSttOkByBill(bill));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Trả hàng phía khách hàng
	 * 
	 * @param billDetails
	 * @return
	 */
	@PutMapping("return-product")
	public ResponseEntity<List<BillDetail>> updateBillDetails(@RequestBody List<BillDetail> billDetails) {
		try {
			return ResponseEntity.ok(billDetailService.returnProductByCustomer(billDetails));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

}
