package com.pro2111.serviceimpl;

import com.pro2111.beans.BillAndBillDetail;
import com.pro2111.beans.BuyRequest;
import com.pro2111.entities.Bill;
import com.pro2111.entities.BillDetail;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.User;
import com.pro2111.repositories.BillDetailRepository;
import com.pro2111.repositories.BillRepository;
import com.pro2111.repositories.CartDetailRepository;
import com.pro2111.repositories.ProductVariantRepository;
import com.pro2111.service.BuyService;
import com.pro2111.utils.Constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuyServiceImpl implements BuyService {

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private BillDetailRepository billDetailRepository;

	@Autowired
	private ProductVariantRepository productVariantRepository;

	@Autowired
	private CartDetailRepository cartDetailRepository;

	private BigDecimal totalMoneyBill;

	/**
	 * thêm mới bill
	 */
	@Override
	@Transactional
	public Bill createBill(BuyRequest buyRequest) {
		// Tạo mới Bill
		Bill bill = billRepository.save(setBill(buyRequest.getBill()));
		// Convert qua billDetail
		totalMoneyBill = new BigDecimal(0);
		List<BillDetail> billDetail = buyRequest.getCartDetails().stream().map(cartDetail -> {
			ProductVariant productVariant = getProductVariant(cartDetail.getProductVariants().getVariantId());
			BigDecimal money = BigDecimal.valueOf(cartDetail.getQuantity()).multiply(productVariant.getPrice());
			BigDecimal totalBillDetail = money
					.add(money.multiply(productVariant.getTax()).divide(BigDecimal.valueOf(100)));
			totalMoneyBill = totalMoneyBill.add(totalBillDetail);
			return new BillDetail(cartDetail.getQuantity(), productVariant.getPrice(), totalBillDetail, bill, 0,
					productVariant.getTax(), productVariant);
		}).collect(Collectors.toList());
		// Thêm mới billDetails
		billDetailRepository.saveAll(billDetail);
		// Cập nhật productVariant và xóa CartDetail
		buyRequest.getCartDetails().forEach(cartDetail -> {
			ProductVariant productVariant = productVariantRepository
					.findById(cartDetail.getProductVariants().getVariantId()).get();
			productVariant.setQuantity(productVariant.getQuantity() - cartDetail.getQuantity());
			productVariantRepository.save(productVariant);
			cartDetailRepository.delete(cartDetail);
		});
		// Set toTalMoney cho bill
		BigDecimal discount = totalMoneyBill.multiply(BigDecimal.valueOf(bill.getDiscount()))
				.divide(BigDecimal.valueOf(100));
		totalMoneyBill = totalMoneyBill.subtract(discount);
		totalMoneyBill = totalMoneyBill.add(bill.getShippingFee());
		bill.setTotalMoney(totalMoneyBill);
		bill.setStatus(1);
		// cập nhật bill
		billRepository.save(bill);
		return bill;
	}

	/**
	 * Đếm tổng số lượng bill theo id
	 */
	@Override
	public synchronized Long countBillId(String billId) {
		return billRepository.countBillId(billId);
	}

	/**
	 * tìm kiếm các bill theo id
	 */
	@Override
	public synchronized List<Bill> findByBillIdLike(String id) {
		return billRepository.findByBillIdLike(id);
	}

	/**
	 * lấy product variant theo id
	 * @param id
	 * @return
	 */
	private ProductVariant getProductVariant(Long id) {
		return productVariantRepository.findById(id).get();
	}

	/**
	 * Lấy hóa đơn của user
	 */
	@Override
	public List<Bill> getBillsByCusId(User user) {
		return billRepository.findByCustomerLike(user);
	}

	/**
	 * Trả  hàng
	 * 
	 */
	@Override
	@Transactional
	public BillAndBillDetail updateBill(BillAndBillDetail billAndBillDetail) {

		// update quantity
		List<BillDetail> oldBillDetails = billDetailRepository.findByBillsLike(billAndBillDetail.getBill());
		oldBillDetails.stream().forEach(billDetail -> {
			ProductVariant productVariant = productVariantRepository
					.findById(billDetail.getProductVariants().getVariantId()).get();
			productVariant.setQuantity(productVariant.getQuantity() + billDetail.getQuantity());
			productVariantRepository.save(productVariant);
		});
		// xóa Bill Detail theo bill
		billDetailRepository.deleteAll(oldBillDetails);
		// thêm mới billDetails
		billAndBillDetail.getBillDetails().forEach(billDetail -> {
			billDetail.setBills(billAndBillDetail.getBill());
			billDetail.setTotalMoney(billDetail.getPrice().multiply(BigDecimal.valueOf(billDetail.getQuantity())));
		});
		billDetailRepository.saveAll(billAndBillDetail.getBillDetails());
		// update quantity
		totalMoneyBill = new BigDecimal(0);
		billAndBillDetail.getBillDetails().forEach(billDetail -> {
			BigDecimal money = BigDecimal.valueOf(billDetail.getQuantity()).multiply(billDetail.getPrice());
			BigDecimal totalBillDetail = money.add(money.multiply(billDetail.getTax()).divide(BigDecimal.valueOf(100)));
			totalMoneyBill = totalMoneyBill.add(totalBillDetail);
			// update quantity
			ProductVariant productVariant = productVariantRepository
					.findById(billDetail.getProductVariants().getVariantId()).get();
			productVariant.setQuantity(productVariant.getQuantity() - billDetail.getQuantity());
			productVariantRepository.save(productVariant);
		});
		Bill bill = billAndBillDetail.getBill();
		// set total money cho bill
		BigDecimal discount = totalMoneyBill.multiply(BigDecimal.valueOf(bill.getDiscount()))
				.divide(BigDecimal.valueOf(100));
		totalMoneyBill = totalMoneyBill.subtract(discount);
		totalMoneyBill = totalMoneyBill.add(bill.getShippingFee());
		bill.setTotalMoney(totalMoneyBill);
		// update bill
		billRepository.save(bill);
		return billAndBillDetail;
	}

	private Bill setBill(Bill bill) {
		bill.setStatus(0);
		bill.setCreatedDate(LocalDateTime.now());
		return bill;
	}

	/**
	 * tìm bill không đủ tiêu chuẩn để trả hàng theo user
	 */
	@Override
	public List<Bill> findBillReturnByUser(User user) {
		return billRepository.findBillReturnByUser(user);
	}

	/**
	 * tìm bill không đủ tiêu chuẩn để trả hàng theo user
	 */
	@Override
	public List<Bill> findNotEligibleForReturnByCustomer(User user) {
		return billRepository.findNotEligibleForReturnByCustomer(user);
	}

	/**
	 * Hủy hóa đơn
	 */
	@Override
	public Bill cancelBill(Bill bill) {
		bill.setStatus(Constant.STATUS_BILL_CANCEL);
		bill.setCancelDate(LocalDate.now());
		return billRepository.save(bill);
	}

}
