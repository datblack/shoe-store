/**
 * DATN_FALL2022, 2022
 * BillServiceIml.java, BUI_QUANG_HIEU
 */
package com.pro2111.serviceimpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.pro2111.beans.BillAndBillDetail;
import com.pro2111.beans.StatusBillBean;
import com.pro2111.entities.Bill;
import com.pro2111.entities.BillDetail;
import com.pro2111.entities.ProductVariant;
import com.pro2111.repositories.BillDetailRepository;
import com.pro2111.repositories.BillRepository;
import com.pro2111.repositories.ProductVariantRepository;
import com.pro2111.service.BillService;
import com.pro2111.service.SmtpMailSender;
import com.pro2111.utils.Constant;
import com.pro2111.utils.FormateNumber;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Service
public class BillServiceImpl implements BillService {

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private BillDetailRepository billDetailRepository;

	@Autowired
	private ProductVariantRepository productVariantRepository;

	@Autowired
	private SmtpMailSender smtpMailSender;

	private Bill _billReturn;

	private BigDecimal _totalMoneyBigDecimal;
	private BigDecimal _totalMoneySend;

	private Integer _countRun = 0;

	private String _to = "";
	private String _subject = "";
	private String _content = "";

	/**
	 * Lấy tổng số Bill theo Id
	 */
	@Override
	public synchronized Long countBillId(String billId) {
		return billRepository.countBillId(billId);
	}

	/**
	 * Lấy list Bill theo Id
	 */
	@Override
	public synchronized List<Bill> findByBillIdLike(String id) {
		return billRepository.findByBillIdLike(id);
	}

	/**
	 * Tạo mới Bill
	 */
	@Override
	public synchronized Bill createBill(Bill bill) {
		return billRepository.save(bill);
	}

	/**
	 * Tạo mới Bill và BillDetail
	 */
	@Override
	@Transactional
	public synchronized Bill createdBillAndBillDetail(BillAndBillDetail billAndBillDetail) {
		try {
			List<BillDetail> lstBillDetails = new ArrayList<BillDetail>();
			_totalMoneyBigDecimal = new BigDecimal("0.0");
			// set date in bill
			billAndBillDetail.getBill().setCreatedDate(LocalDateTime.now());
			// create bill
			Bill bill = billRepository.save(billAndBillDetail.getBill());

			// duyệt mảng BillDetails
			billAndBillDetail.getBillDetails().forEach(detail -> {
				// set bill in billDetail
				detail.setBills(bill);
				BigDecimal money = BigDecimal.valueOf(detail.getQuantity()).multiply(detail.getPrice());
				money = money.add(money.multiply(detail.getTax()).divide(BigDecimal.valueOf(100)));
				detail.setTotalMoney(money);
				detail.setStatus(0);
				// create billDetail
				BillDetail billDetail = billDetailRepository.save(detail);
				_totalMoneyBigDecimal = _totalMoneyBigDecimal.add(billDetail.getTotalMoney());

				lstBillDetails.add(billDetail);
				// set quantity productVariant
				Long pvId = detail.getProductVariants().getVariantId();
				Integer quantityBill = detail.getQuantity();
				ProductVariant pv = productVariantRepository.findById(pvId).get();
				Integer quantityPv = pv.getQuantity();
				pv.setQuantity(quantityPv - quantityBill);
				productVariantRepository.save(pv);
			});

			// update total_money bill
			_totalMoneySend = _totalMoneyBigDecimal;
			BigDecimal totalMoney = _totalMoneyBigDecimal
					.subtract((_totalMoneyBigDecimal.multiply(BigDecimal.valueOf(bill.getDiscount())))
							.divide(BigDecimal.valueOf(100)));
			totalMoney = totalMoney.add(bill.getShippingFee());

			bill.setTotalMoney(totalMoney);

			if (bill.getStatus() == Constant.STATUS_BILL_SUCCESS && bill.getSuccessDate() == null) {
				bill.setSuccessDate(LocalDate.now());
			}
			_billReturn = billRepository.save(bill);
			// SendMail
			if (_billReturn.getCustomers() != null && bill.getStatus() == Constant.STATUS_BILL_SUCCESS) {
				_countRun = 0;
				run();
			}
			return bill;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Lấy tất cả Bill
	 */
	@Override
	public synchronized List<Bill> getAllBill() {
		return billRepository.getAllBills();
	}

	/**
	 * Lấy list Bill theo id
	 */
	@Override
	public synchronized Bill findById(String id) {
		return billRepository.findById(id).get();
	}

	/**
	 * Lấy list Bill theo idBill gần đúng
	 */
	@Override
	public synchronized List<Bill> searchApproximateBill(String inputString) {
		return billRepository.searchApproximateBill(inputString);
	}

	/**
	 * Lấy list Bill theo tên người mua gần đúng
	 */
	@Override
	public synchronized List<Bill> searchApproximateCustomer(String inputString) {
		return billRepository.searchApproximateCustomer(inputString);
	}

	/**
	 * Lấy list Bil theo số điện thoại người mua gần đúng
	 */
	@Override
	public synchronized List<Bill> searchApproximatePhone(String inputString) {
		return billRepository.searchApproximatePhone(inputString);
	}

	/**
	 * Lấy list Bill theo địa chỉ nhận hàng gần đúng
	 */
	@Override
	public synchronized List<Bill> searchApproximateAddress(String inputString) {
		return billRepository.searchApproximateAddress(inputString);
	}

	/**
	 * Lấy list Bill theo tên nhân viên gần đúng
	 */
	@Override
	public synchronized List<Bill> searchApproximateSeller(String inputString) {
		return billRepository.searchApproximateSeller(inputString);
	}

	/**
	 * Cập nhật Trạng thái hóa đơn, sản phẩm bên phía user
	 */
	@Override
	@Transactional
	public synchronized Bill updateBill(BillAndBillDetail billAndBillDetail) {
//		if (bill.getStatus() == Constant.STATUS_BILL_SUCCESS && bill.getSuccessDate() == null) {
//			bill.setSuccessDate(LocalDate.now());
//		} else if (bill.getStatus() == Constant.STATUS_BILL_CANCEL) {
//			List<BillDetail> billDetails = billDetailRepository.findByBillsLike(bill);
//			billDetails.forEach(d -> {
//				ProductVariant pv = d.getProductVariants();
//				pv.setQuantity(pv.getQuantity() + d.getQuantity());
//				productVariantRepository.save(pv);
//			});
//		}

//		List<BillDetail> details = billDetailRepository.findByBillsLike(bill);
//		totalMoney = new BigDecimal("0.0");
//		details.forEach(d -> {
//			totalMoney = totalMoney.add(d.getTotalMoney());
//		});
//		BigDecimal totalMoneyNew = totalMoney.subtract(
//				(totalMoney.multiply(BigDecimal.valueOf(bill.getDiscount()))).divide(BigDecimal.valueOf(100)));
//		totalMoneyNew = totalMoneyNew.add(bill.getShippingFee());
//		bill.setTotalMoney(totalMoneyNew);
//		return billRepository.save(bill);

		// Khởi tạo lại tổng tiền
		_totalMoneyBigDecimal = new BigDecimal("0.0");

		// Cập nhật lại bill
		Bill billReturn = billRepository.save(billAndBillDetail.getBill());

		// Lấy list bill detail cũ, xóa và update lại quantity
		List<BillDetail> listDetailOld = billDetailRepository.findByBillsLike(billReturn);
		listDetailOld.forEach(d -> {
			// Cập nhật lại quantity sản phẩm
			ProductVariant pv = productVariantRepository.findById(d.getProductVariants().getVariantId()).get();
			pv.setQuantity(pv.getQuantity() + d.getQuantity());
			productVariantRepository.save(pv);

			// Xóa billDetail
			billDetailRepository.delete(d);
		});

		// Tạo billDetail mới
		billAndBillDetail.getBillDetails().forEach(dNew -> {
			// Tạo billDetail
			dNew.setBills(billReturn);
			BigDecimal money = BigDecimal.valueOf(dNew.getQuantity()).multiply(dNew.getPrice());
			money = money.add(money.multiply(dNew.getTax()).divide(BigDecimal.valueOf(100)));
			dNew.setTotalMoney(money);
			dNew.setStatus(0);
			BillDetail billDetail = billDetailRepository.save(dNew);
			_totalMoneyBigDecimal = _totalMoneyBigDecimal.add(billDetail.getTotalMoney());

			// Cập nhật quantity sản phẩm

			ProductVariant pv = productVariantRepository.findById(dNew.getProductVariants().getVariantId()).get();
			pv.setQuantity(pv.getQuantity() - dNew.getQuantity());
			productVariantRepository.save(pv);
		});

		// Cập nhật tổng tiền bill
		BigDecimal totalMoneyNew = _totalMoneyBigDecimal
				.subtract((_totalMoneyBigDecimal.multiply(BigDecimal.valueOf(billReturn.getDiscount())))
						.divide(BigDecimal.valueOf(100)));
		totalMoneyNew = totalMoneyNew.add(billReturn.getShippingFee());
		billReturn.setTotalMoney(totalMoneyNew);

		return billReturn;
	}

	/**
	 * Lấy list Bill đủ điều kiện trả hàng
	 */
	@Override
	public synchronized List<Bill> findBillEligibleForReturn() {
		return billRepository.findBillEligibleForReturn();
	}

	/*
	 * Tạo mới hóa đơn
	 */
	@Override
	public synchronized List<Bill> findBillReturnInvoices() {
		List<Bill> bills = billRepository.findBillReturnInvoices();
		bills.forEach(b -> {
			List<BillDetail> billDetails = new ArrayList<>(b.getDetailBills());
			_totalMoneyBigDecimal = new BigDecimal("0.0");
			billDetails.forEach(detail -> {
				if (detail.getStatus() != 0) {
					_totalMoneyBigDecimal = _totalMoneyBigDecimal.add(detail.getTotalMoney());
				}
			});
			b.setTotalMoney(_totalMoneyBigDecimal);
		});
		return bills;
	}

	@Scheduled(fixedRate = 1000, initialDelay = 2000)
	public void run() throws MessagingException {
		try {
			if (_countRun < 0) {
				_to = "";
				return;
			}
			_countRun = -1;
			_to = _billReturn.getCustomers().getEmail();
			_subject = "[ĐẶT HÀNG THÀNH CÔNG]";
			StringBuffer text = new StringBuffer();
			text.append("<p>Xin chào, <strong>" + _billReturn.getCustomers().getFullName() + "</strong></p>");
			text.append("<p>Cảm ơn bạn ghé thăm và mua sản phẩm bên Shop !</p>");
			text.append("<p>Mã hóa đơn: <strong>" + _billReturn.getBillId() + "</strong></p>");
			text.append("<p>Trạng thái hóa đơn: <strong>Hoàn thành</strong></p>");
			text.append(
					"<p>Dưới đây là danh sách sản phẩm bạn mua. Nếu có vấn đề liên quan đến sản phẩm và giá cả. Bạn vui lòng liên hệ lại cho Shop !</p>");
			text.append("<p><i>Xin chân thành cảm ơn !</i></p><br>");
			text.append("<table width='100%' border='1' align='center' style='font-size: 11pt;'>");
			// thead
			text.append("<thead>");
			text.append("<tr>");
			text.append("<th>Sản phẩm</th>");
			text.append("<th>Số lượng</th>");
			text.append("<th>Đơn giá (VNĐ)</th>");
			text.append("<th>VAT (%)</th>");
			text.append("<th>Thành tiền (VNĐ)</th>");
			text.append("</tr>");
			text.append("</thead>");
			// tbody
			text.append("<tbody>");
			List<BillDetail> details = billDetailRepository.findByBillsLike(_billReturn);

			for (int i = 0; i < details.size(); i++) {
				ProductVariant pv = details.get(i).getProductVariants();

				text.append("<tr>");

				text.append("<td>");
				text.append(pv.getProducts().getProductName());
				text.append("</td>");

				text.append("<td align='right'>");
				text.append(details.get(i).getQuantity());
				text.append("</td>");

				text.append("<td align='right'>");
				text.append(FormateNumber.formateBigDecimal(details.get(i).getPrice()));
				text.append("</td>");

				text.append("<td align='right'>");
				text.append(FormateNumber.formateBigDecimal(details.get(i).getTax()));
				text.append("</td>");

				text.append("<td align='right'>");
				text.append(FormateNumber.formateBigDecimal(details.get(i).getTotalMoney()));
				text.append("</td>");

				text.append("</tr>");

			}

			text.append("<tr>");
			text.append("<td colspan='4'><b>Tổng tiền:</b></td>");
			text.append("<td align='right'>");
			text.append(FormateNumber.formateBigDecimal(_totalMoneySend));
			text.append("</td>");
			text.append("</tr>");

			text.append("<tr>");
			text.append("<td colspan='4'><b>Phí ship:</b></td>");
			text.append("<td align='right'>");
			text.append(FormateNumber.formateBigDecimal(_billReturn.getShippingFee()));
			text.append("</td>");
			text.append("</tr>");

			text.append("<tr>");
			text.append("<td colspan='4'><b>Giảm giá:</b></td>");
			text.append("<td align='right'>");
			text.append(_billReturn.getDiscount());
			text.append("%");
			text.append("</td>");
			text.append("</tr>");

			text.append("<tr>");
			text.append("<td colspan='4'><b>Thành tiền:</b></td>");
			text.append("<td align='right'>");
			text.append(FormateNumber.formateBigDecimal(_billReturn.getTotalMoney()));
			text.append("</td>");
			text.append("</tr>");

			text.append("</tbody>");
			text.append("</table>");
			_content = text.toString();
			smtpMailSender.sendMail(_to, _subject, _content);

		} catch (Exception e) {

		}

	}

	/**
	 * Lấy list Bill theo status
	 */
	@Override
	public synchronized List<Bill> getBillByStatus(int status) {
		return billRepository.findBillByStatus(status);
	}

	/**
	 * Cập nhật status bill
	 */
	@Override
	@Transactional
	public synchronized List<Bill> updateStatusBill(List<StatusBillBean> statusBillBeans) {
		List<Bill> bills = new ArrayList<>();
		statusBillBeans.forEach(b -> {
			if (b.getStatus() >= Constant.STATUS_MIN_BILL && b.getStatus() <= Constant.STATUS_MAX_BILL) {
				Bill bill = billRepository.findById(b.getBill().getBillId()).get();
				bill.setStatus(b.getStatus());
				if (b.getStatus() == Constant.STATUS_BILL_SUCCESS && bill.getSuccessDate() == null) {
					bill.setSuccessDate(LocalDate.now());
				} else if (b.getStatus() == Constant.STATUS_BILL_CANCEL) {
					bill.setCancelDate(LocalDate.now());
					List<BillDetail> billDetails = billDetailRepository.findByBillsLike(bill);
					billDetails.forEach(d -> {
						ProductVariant pv = d.getProductVariants();
						pv.setQuantity(pv.getQuantity() + d.getQuantity());
						productVariantRepository.save(pv);
					});
				}
				billRepository.save(bill);
				bills.add(bill);
			} else {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
			}

		});

		return bills;
	}

	/**
	 * Lấy list Bill chờ xác nhận trả hàng
	 */
	@Override
	public synchronized List<Bill> findBillByWaitConfirmReturn() {
		return billRepository.findBillByWaitConfirmReturn();
	}

	/**
	 * Lấy list Bill hủy trả hàng
	 */
	@Override
	public synchronized List<Bill> findBillByCancelReturn() {
		return billRepository.findBillByCancelReturn();
	}

	/**
	 * Lấy list Bill trả hàng thành công
	 */
	@Override
	public synchronized List<Bill> findBillBySuccessReturn() {
		return billRepository.findBillBySuccessReturn();
	}

	/**
	 * Lấy tổng hóa đơn chờ xác nhận
	 */
	@Override
	public synchronized Long countBillWaitForConfirmation() {
		return billRepository.countBillWaitConfirm();
	}

	/**
	 * Lấy tổng hóa đơn chờ thanh toán
	 */
	@Override
	public Long countBillWaitForPay() {
		return billRepository.countBillWaitForPay();
	}

	/**
	 * Lấy tổng hóa đơn chờ giao
	 */
	@Override
	public Long countBillWaitForDelivery() {
		return billRepository.countBillWaitForDelivery();
	}

}
