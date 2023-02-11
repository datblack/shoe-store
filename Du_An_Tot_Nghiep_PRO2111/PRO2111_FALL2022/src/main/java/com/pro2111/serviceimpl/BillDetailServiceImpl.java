/**
 * DATN_FALL2022, 2022
 * BillDetailServiceIml.java, BUI_QUANG_HIEU
 */
package com.pro2111.serviceimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.pro2111.beans.BillDetailReturnBean;
import com.pro2111.beans.BillDetailReturnCustomerViewBean;
import com.pro2111.beans.BillDetailViewBean;
import com.pro2111.entities.Bill;
import com.pro2111.entities.BillDetail;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.VariantValue;
import com.pro2111.repositories.BillDetailRepository;
import com.pro2111.repositories.BillRepository;
import com.pro2111.repositories.ProductRepository;
import com.pro2111.repositories.ProductVariantRepository;
import com.pro2111.repositories.UserRepository;
import com.pro2111.repositories.VariantValueRepository;
import com.pro2111.service.BillDetailService;
import com.pro2111.utils.Constant;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Service
public class BillDetailServiceImpl implements BillDetailService {

	@Autowired
	private BillDetailRepository billDetailRepository;
	@Autowired
	private ProductVariantRepository productVariantRepository;

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private VariantValueRepository variantValueRepository;

	@Autowired
	private ProductRepository productRepository;

	private BigDecimal totalMoney;

	/**
	 * Tạo BillDetail
	 */
	@Override
	@Transactional
	public synchronized BillDetail createdBillDetail(BillDetail billDetail) {
		List<BillDetail> details = billDetailRepository
				.findByProductVariantsLikeAndBillsLike(billDetail.getProductVariants(), billDetail.getBills());
		if (details.size() == 0) {
			BigDecimal money = BigDecimal.valueOf(billDetail.getQuantity()).multiply(billDetail.getPrice());
			money = money.add(money.multiply(billDetail.getTax()).divide(BigDecimal.valueOf(100)));
			billDetail.setTotalMoney(money);

			// update quantity ProductVariant
			ProductVariant productVariant = productVariantRepository
					.findById(billDetail.getProductVariants().getVariantId()).get();
			productVariant.setQuantity(productVariant.getQuantity() - billDetail.getQuantity());
			productVariantRepository.save(productVariant);

			// update totalmoney bill
			Bill bill = billDetail.getBills();
			List<BillDetail> details2 = billDetailRepository.findByBillsLike(bill);
			totalMoney = new BigDecimal("0.0");
			details2.forEach(d -> {
				totalMoney = totalMoney.add(d.getTotalMoney());
			});
			BigDecimal totalMoneyNew = totalMoney.subtract(
					(totalMoney.multiply(BigDecimal.valueOf(bill.getDiscount()))).divide(BigDecimal.valueOf(100)));
			totalMoneyNew = totalMoneyNew.add(bill.getShippingFee());
			bill.setTotalMoney(totalMoneyNew);

			billRepository.save(bill);
			return billDetailRepository.save(billDetail);
		} else {
			BillDetail detailOld = details.get(0);
			detailOld.setQuantity(detailOld.getQuantity() + billDetail.getQuantity());
			BigDecimal money = detailOld.getPrice().multiply(BigDecimal.valueOf(detailOld.getQuantity()));
			money = money.add(money.multiply(detailOld.getTax()).divide(BigDecimal.valueOf(100)));
			detailOld.setTotalMoney(money);

			// update quantity ProductVariant
			ProductVariant productVariant = productVariantRepository
					.findById(detailOld.getProductVariants().getVariantId()).get();
			productVariant.setQuantity(productVariant.getQuantity() - billDetail.getQuantity());
			productVariantRepository.save(productVariant);

			// update totalmoney bill
			Bill bill = detailOld.getBills();
			List<BillDetail> details2 = billDetailRepository.findByBillsLike(bill);
			totalMoney = new BigDecimal("0.0");
			details2.forEach(d -> {
				totalMoney = totalMoney.add(d.getTotalMoney());
			});
			BigDecimal totalMoneyNew = totalMoney.subtract(
					(totalMoney.multiply(BigDecimal.valueOf(bill.getDiscount()))).divide(BigDecimal.valueOf(100)));
			totalMoneyNew = totalMoneyNew.add(bill.getShippingFee());
			bill.setTotalMoney(totalMoneyNew);
			billRepository.save(bill);
			return billDetailRepository.save(detailOld);
		}
	}

	/**
	 * Lấy list BillDetail theo Bill
	 */
	@Override
	public synchronized List<BillDetail> findByBill(Bill bill) {
		return billDetailRepository.findByBillsLike(bill);
	}

	/**
	 * Cập nhật BillDetail
	 */
	@Override
	@Transactional
	public synchronized BillDetail updateBillDetail(BillDetail billDetail) {
		BillDetail detailOld = billDetailRepository.findById(billDetail.getDetailBillId()).get();
		int quantityOld = detailOld.getQuantity();
		int quantityNew = billDetail.getQuantity();

		billDetail.setTax(detailOld.getTax());
		BigDecimal money = billDetail.getPrice().multiply(BigDecimal.valueOf(quantityNew));
		money = money.add(money.multiply(billDetail.getTax()).divide(BigDecimal.valueOf(100)));
		billDetail.setTotalMoney(money);
		// update billDetail
		billDetailRepository.save(billDetail);

//		// Tìm list BillDetail theo productVarinat của billDetail
//		List<BillDetail> lstDetail = new ArrayList<BillDetail>();
//		lstDetail = billDetailRepository.findByProductVariantsLikeAndBillsLike(billDetail.getProductVariants(),
//				billDetail.getBills());
//		int quantityOld = 0;
//		int quantityNew = 0;
//		// Nếu lst.size ==1 thì update trực tiếp
//		if (lstDetail.size() == 1) {
//			quantityOld = lstDetail.get(0).getQuantity();
//			quantityNew = billDetail.getQuantity();
//			billDetail.setTotalMoney(new BigDecimal(billDetail.getPrice().floatValue() * billDetail.getQuantity()));
//			billDetailRepository.save(billDetail);
//
//		} else {
//			// kiểm tra từng pv có trong list
//			// giá billDetail update trùng với giá billDetail đã có thì tăng số lượng đã
//			// billDetail đã có,
//			// Xóa billDetail cần update,
//			// Còn không thì update trực tiếp
//			for (int i = 0; i < lstDetail.size(); i++) {
//				BillDetail detail = lstDetail.get(i);
//				if (detail.getDetailBillId() != billDetail.getDetailBillId()) {
//					if (!detail.getPrice().equals(billDetail.getPrice())) {
//						quantityOld = detail.getQuantity();
//						quantityNew = billDetail.getQuantity();
//						billDetail.setTotalMoney(
//								new BigDecimal(billDetail.getPrice().floatValue() * billDetail.getQuantity()));
//						// update billDetail
//						billDetailRepository.save(billDetail);
//
//					} else {
//						BillDetail detailOld = billDetailRepository.findById(billDetail.getDetailBillId()).get();
//						quantityOld = detailOld.getQuantity() + detail.getQuantity();
//						detail.setQuantity(detail.getQuantity() + billDetail.getQuantity());
//						detail.setTotalMoney(new BigDecimal(detail.getPrice().floatValue() * detail.getQuantity()));
//						quantityNew = detail.getQuantity();
//						// update billDetail
//						billDetailRepository.save(detail);
//						billDetailRepository.delete(billDetail);
//					}
//				}
//			}
//		}

		// update quantity ProductVariant
		ProductVariant productVariant = productVariantRepository
				.findById(billDetail.getProductVariants().getVariantId()).get();
		productVariant.setQuantity(productVariant.getQuantity() - (quantityNew - quantityOld));
		productVariantRepository.save(productVariant);

		// update totalmoney bill
		Bill bill = billDetail.getBills();
		List<BillDetail> details = billDetailRepository.findByBillsLike(bill);
		totalMoney = new BigDecimal("0.0");
		details.forEach(d -> {
			totalMoney = totalMoney.add(d.getTotalMoney());
		});
		BigDecimal totalMoneyNew = totalMoney.subtract(
				(totalMoney.multiply(BigDecimal.valueOf(bill.getDiscount()))).divide(BigDecimal.valueOf(100)));
		totalMoneyNew = totalMoneyNew.add(bill.getShippingFee());
		bill.setTotalMoney(totalMoneyNew);
		billRepository.save(bill);

		return billDetail;
	}

	/**
	 * Trả hàng phía Admin
	 */
	@Override
	@Transactional
	public synchronized List<BillDetail> returnBillDetailOfAdmin(List<BillDetailReturnBean> billDetailReturnBeans) {
		List<BillDetail> detailReturns = new ArrayList<BillDetail>();
		billDetailReturnBeans.forEach(billDetailReturnBean -> {
			// Lấy lại billDetail trước đó
			BillDetail detailOld = billDetailRepository.findById(billDetailReturnBean.getBillDetail().getDetailBillId())
					.get();

			if (billDetailReturnBean.getBillDetail().getQuantity() <= detailOld.getQuantity()) {
				if (billDetailReturnBean.getBillDetail().getQuantity() == detailOld.getQuantity()) {
					// Nếu 2 quantity bằng nhau thì update status
					detailOld.setNote(billDetailReturnBean.getBillDetail().getNote());
					detailOld.setStatus(Constant.STATUS_BILL_DETAIL_RETURN_OK);
					detailOld.setUserConfirm(billDetailReturnBean.getBillDetails().get(0).getUserConfirm());
					billDetailRepository.save(detailOld);
				} else if (billDetailReturnBean.getBillDetail().getQuantity() < detailOld.getQuantity()) {
					// Nếu quantity trả < quantity cũ thì tạo detail mới với số lượng còn lại
					// và update lại số lượng detail cũ
					// Tạo billDetail sản phẩm mà khách không hoàn trả
					BillDetail detailNew = new BillDetail();
					detailNew.setBills(detailOld.getBills());
					detailNew.setPrice(detailOld.getPrice());
					detailNew.setTax(detailOld.getTax());
					detailNew.setProductVariants(detailOld.getProductVariants());
					detailNew.setQuantity(detailOld.getQuantity() - billDetailReturnBean.getBillDetail().getQuantity());
					BigDecimal money = detailNew.getPrice().multiply(BigDecimal.valueOf(detailNew.getQuantity()));
					money = money.add(money.multiply(detailNew.getTax()).divide(BigDecimal.valueOf(100)));
					detailNew.setTotalMoney(money);
					detailNew.setStatus(Constant.STATUS_BILL_DETAIL_OK);

					// Update lại billDetail cũ với số lượng khách hoàn trả trạng thái hoàn trả
					detailOld.setQuantity(billDetailReturnBean.getBillDetail().getQuantity());
					BigDecimal totalMoney = detailOld.getPrice().multiply(BigDecimal.valueOf(detailOld.getQuantity()));
					totalMoney = totalMoney
							.add(totalMoney.multiply(detailOld.getTax()).divide(BigDecimal.valueOf(100)));
					detailOld.setTotalMoney(totalMoney);
					detailOld.setStatus(Constant.STATUS_BILL_DETAIL_RETURN_OK);
					detailOld.setUserConfirm(billDetailReturnBean.getBillDetails().get(0).getUserConfirm());
					detailOld.setNote(billDetailReturnBean.getBillDetail().getNote());

					// Update billDetail
					billDetailRepository.save(detailNew);
					billDetailRepository.save(detailOld);

				}
				// Add detailOld to detailReturns
				detailReturns.add(detailOld);

				// Update quantity productVariant
				// cập nhật số lượng pv
				// Nếu type == success => + quantity
				// Nếu type == error => + quantityError
				billDetailReturnBean.getReturnTypes().forEach(type -> {
					ProductVariant pv = productVariantRepository.findById(detailOld.getProductVariants().getVariantId())
							.get();
					if (type == Constant.RETURN_TYPE_SUCCESS) {
						pv.setQuantity(pv.getQuantity() + detailOld.getQuantity());
					} else if (type == Constant.RETURN_TYPE_ERROR) {
						pv.setQuantityError(pv.getQuantityError() + detailOld.getQuantity());
					}
					productVariantRepository.save(pv);
				});
				// Tạo bill detail cửa hàng đổi hàng or hoàn tiền cho khách hàng
				billDetailReturnBean.getBillDetails().forEach(bd -> {
					bd.setBillDetail(detailOld);
					bd.setStatus(Constant.STATUS_BILL_DETAIL_RETURN_CUSTOMER);
					totalMoney = bd.getPrice().multiply(BigDecimal.valueOf(bd.getQuantity()));
					totalMoney = totalMoney.add(totalMoney.multiply(bd.getTax().divide(BigDecimal.valueOf(100))));
					bd.setTotalMoney(totalMoney);
					billDetailRepository.save(bd);

					if (bd.getProductVariants() != null) {
						ProductVariant pvReturnCustomer = productVariantRepository
								.findById(bd.getProductVariants().getVariantId()).get();
						pvReturnCustomer.setQuantity(pvReturnCustomer.getQuantity() - bd.getQuantity());
						productVariantRepository.save(pvReturnCustomer);
					}
				});
			}
		});
		// Update totalMoney Bill
		Bill bill = billRepository.findById(billDetailReturnBeans.get(0).getBillDetail().getBills().getBillId()).get();
		List<BillDetail> details = billDetailRepository.findByBillsLike(bill);
		totalMoney = new BigDecimal("0.0");
		details.forEach(d -> {
			if (d.getStatus() == 0 || d.getStatus() == 4) {
				totalMoney = totalMoney.add(d.getTotalMoney());
			}
		});
		totalMoney = totalMoney.subtract(
				(totalMoney.multiply(BigDecimal.valueOf(bill.getDiscount()))).divide(BigDecimal.valueOf(100)));
		totalMoney = totalMoney.add(bill.getShippingFee());
		bill.setTotalMoney(totalMoney);
		billRepository.save(bill);
		return detailReturns;
	}

	/**
	 * Xóa BillDetail
	 */
	@Override
	@Transactional
	public synchronized BillDetail deleteBillDetail(BillDetail billDetail) {
		billDetailRepository.delete(billDetail);

		// update quantity ProductVariant
		ProductVariant productVariant = productVariantRepository
				.findById(billDetail.getProductVariants().getVariantId()).get();
		productVariant.setQuantity(productVariant.getQuantity() + billDetail.getQuantity());
		productVariantRepository.save(productVariant);
		return billDetail;
	}

	/**
	 * Lấy BillDetail theo Bill và Status
	 */
	@Override
	public List<BillDetail> findByBillDetailReturnInvoicesAndStatus(Bill bill, int status) {
		return billDetailRepository.findByBillDetailReturnInvoicesAndStatus(bill, status);
	}

	/**
	 * Lấy list BillDetail đủ điều kiện trả hàng theo Bill
	 */
	@Override
	public List<BillDetail> findByBillDetailEligibleForReturn(Bill bill) {
		return billDetailRepository.findByBillDetailEligibleForReturn(bill);
	}

	/**
	 * Lấy BillDetail theo Id
	 */
	@Override
	public BillDetail findById(String detailBillId) {
		return billDetailRepository.findById(detailBillId).get();
	}

	/**
	 * Trả hàng, cập nhật lại trạng thái hóa đơn và sản phẩm
	 */
	@Override
	@Transactional
	public List<BillDetail> returnProductByCustomer(List<BillDetail> billDetails) {
		billDetails.forEach(billDetailReturn -> {
			// nếu note != blank thì thực thi câu lệnh trong block
			if (!billDetailReturn.getNote().isBlank()) {
				// lấy bill detail cũ
				BillDetail oldBillDetail = billDetailRepository.findById(billDetailReturn.getDetailBillId()).get();
				if (oldBillDetail.getQuantity() == billDetailReturn.getQuantity()) {
					// nếu số lượng cũ và số lượng trả bằng nhau thì update status
					oldBillDetail.setNote(billDetailReturn.getNote());
					oldBillDetail.setStatus(Constant.STATUS_BILL_DETAIL_CONFIRM_RETURN);
					billDetailRepository.save(oldBillDetail);
				} else if (billDetailReturn.getQuantity() < oldBillDetail.getQuantity()) {

					// Nếu quantity trả < quantity cũ thì tạo detail mới với số lượng còn lại
					// và update lại số lượng detail cũ
					// Tạo billDetail sản phẩm mà khách không hoàn trả
					BillDetail detailNew = new BillDetail();
					detailNew.setBills(oldBillDetail.getBills());
					detailNew.setPrice(oldBillDetail.getPrice());
					detailNew.setTax(oldBillDetail.getTax());
					detailNew.setProductVariants(oldBillDetail.getProductVariants());
					detailNew.setQuantity(oldBillDetail.getQuantity() - billDetailReturn.getQuantity());
					BigDecimal money = detailNew.getPrice().multiply(BigDecimal.valueOf(detailNew.getQuantity()));
					money = money.add(money.multiply(detailNew.getTax()).divide(BigDecimal.valueOf(100)));
					detailNew.setTotalMoney(money);
					detailNew.setStatus(Constant.STATUS_BILL_DETAIL_OK);

					// Update lại billDetail cũ với số lượng khách hoàn trả trạng thái chờ xác nhận
					// hoàn trả
					oldBillDetail.setQuantity(billDetailReturn.getQuantity());
					BigDecimal totalMoney = oldBillDetail.getPrice()
							.multiply(BigDecimal.valueOf(billDetailReturn.getQuantity()));
					totalMoney = totalMoney
							.add(totalMoney.multiply(oldBillDetail.getTax()).divide(BigDecimal.valueOf(100)));
					oldBillDetail.setTotalMoney(totalMoney);
					oldBillDetail.setStatus(Constant.STATUS_BILL_DETAIL_CONFIRM_RETURN);
					oldBillDetail.setNote(billDetailReturn.getNote());

					// Update billDetail
					billDetailRepository.save(detailNew);
					billDetailRepository.save(oldBillDetail);

//					int quantity = billDetailReturn.getQuantity();
//					billDetailReturn.setQuantity(oldBillDetail.getQuantity() - billDetailReturn.getQuantity());
//					// tạo billdetail mới
//					BillDetail newBillDetail = new BillDetail();
//					try {
//						// coppy dữ liệu các trường từ request qua billdetail mới
//						BeanUtils.copyProperties(newBillDetail, billDetailReturn);
//						newBillDetail.setQuantity(quantity);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					// update quantity
//					newBillDetail.setDetailBillId("");
//					newBillDetail.setStatus(Constant.STATUS_BILL_DETAIL_CONFIRM_RETURN);
//					billDetailRepository.save(newBillDetail);
//					billDetailRepository.save(billDetailReturn);
				}
			} else {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
			}
		});
		return billDetails;
	}

	/**
	 * Xác nhận trả hàng
	 */
	@Override
	@Transactional
	public List<BillDetail> verifyReturn(List<BillDetailReturnBean> billDetailReturnBeans) {
		BigDecimal refund = new BigDecimal(0);
		List<BillDetail> listReturn = new ArrayList<>();
		for (BillDetailReturnBean bean : billDetailReturnBeans) {
			BillDetail billDetail = bean.getBillDetail();
			if (billDetail.getStatus() == Constant.STATUS_BILL_DETAIL_CONFIRM_RETURN) {
				BillDetail billDetail2 = billDetailRepository.findById(billDetail.getDetailBillId()).get();
				billDetail2.setStatus(Constant.STATUS_BILL_DETAIL_RETURN_OK);
				billDetail2.setUserConfirm(bean.getBillDetails().get(0).getUserConfirm());
				billDetailRepository.save(billDetail2);
				listReturn.add(billDetail2);

				// cập nhật số lượng pv
				// Nếu type == success => + quantity
				// Nếu type == error => + quantityError
				bean.getReturnTypes().forEach(type -> {
					ProductVariant pv = productVariantRepository
							.findById(billDetail.getProductVariants().getVariantId()).get();
					if (type == Constant.RETURN_TYPE_SUCCESS) {
						pv.setQuantity(pv.getQuantity() + billDetail.getQuantity());
					} else if (type == Constant.RETURN_TYPE_ERROR) {
						pv.setQuantityError(pv.getQuantityError() + billDetail.getQuantity());
					}
					productVariantRepository.save(pv);
				});

				refund.add(billDetail.getTotalMoney());

				// Tạo bill detail cửa hàng đổi hàng or hoàn tiền cho khách hàng
				bean.getBillDetails().forEach(bd -> {
					totalMoney = new BigDecimal("0.0");
					bd.setBillDetail(billDetail);
					bd.setStatus(Constant.STATUS_BILL_DETAIL_RETURN_CUSTOMER);
					totalMoney = bd.getPrice().multiply(BigDecimal.valueOf(bd.getQuantity()));
					totalMoney = totalMoney.add(totalMoney.multiply(bd.getTax().divide(BigDecimal.valueOf(100))));
					bd.setTotalMoney(totalMoney);
					billDetailRepository.save(bd);
					if (bd.getProductVariants() != null) {
						ProductVariant pvReturnCustomer = productVariantRepository
								.findById(bd.getProductVariants().getVariantId()).get();
						pvReturnCustomer.setQuantity(pvReturnCustomer.getQuantity() - bd.getQuantity());
						productVariantRepository.save(pvReturnCustomer);
					}
				});

				// Update totalMoney Bill
				Bill bill = billRepository.findById(billDetail.getBills().getBillId()).get();
				List<BillDetail> details = billDetailRepository.findByBillsLike(bill);
				totalMoney = new BigDecimal("0.0");
				details.forEach(d -> {
					if (d.getStatus() == 0 || d.getStatus() == 4) {
						totalMoney = totalMoney.add(d.getTotalMoney());
					}
				});
				totalMoney = totalMoney.subtract(
						(totalMoney.multiply(BigDecimal.valueOf(bill.getDiscount()))).divide(BigDecimal.valueOf(100)));
				totalMoney = totalMoney.add(bill.getShippingFee());
				bill.setTotalMoney(totalMoney);
				billRepository.save(bill);

			} else {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
			}
		}
		return listReturn;
	}

	/**
	 * Từ chối trả hàng
	 */
	@Override
	public List<BillDetail> prohibitReturn(List<BillDetail> billDetails) {
		for (BillDetail billDetail : billDetails) {
			if (billDetail.getStatus() == Constant.STATUS_BILL_DETAIL_CONFIRM_RETURN) {
				BillDetail billDetail2 = billDetailRepository.findById(billDetail.getDetailBillId()).get();
				billDetail2.setStatus(Constant.STATUS_BILL_DETAIL_RETURN_FAIL);
				billDetailRepository.save(billDetail2);
			} else {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
			}
		}
		return billDetails;
	}

	/**
	 * Lấy list BillDetail có trạng thái chưa trả hàng theo Bill
	 */
	@Override
	public List<BillDetail> getSttOkByBill(Bill bill) {
		return billDetailRepository.findByBillDetailReturnInvoices(bill);
	}

	/**
	 * Lấy list BillDetailViewBean của người mua theo Bill
	 */
	@Override
	public List<BillDetailViewBean> findByBillDetailCustomerBuy(Bill bill)
			throws NumberFormatException, ReflectiveOperationException {
		List<Object[]> listResult = billDetailRepository.findByBillDetailCustomerOrder(bill);
		List<BillDetailViewBean> listReturn = new ArrayList<>();
		for (Object[] obj : listResult) {
			String pvId = "";
			if (obj[0] != null) {
				pvId = obj[0].toString();
			}
			String quantity = obj[1].toString();
			String price = obj[2].toString();
			String tax = obj[3].toString();
			String totalMoney = obj[4].toString();
			String user = "";
			if (obj[5] != null) {
				user = obj[5].toString();
			}
			String note = "";
			if (obj[6] != null) {
				note = obj[6].toString();
			}
			BillDetailViewBean bean = new BillDetailViewBean();
			if (pvId != "") {
				bean.setProductVariant(customNameProductByOneProductVariant(
						productVariantRepository.findById(Long.parseLong(obj[0].toString())).get()));
			}
			bean.setQuantity(Long.parseLong(quantity));
			bean.setPrice(new BigDecimal(price));
			bean.setTax(new BigDecimal(tax));
			bean.setTotalMoney(new BigDecimal(totalMoney));
			if (user != "") {
				bean.setUserConfirm(userRepository.findById(Integer.parseInt(user)).get());
			}
			bean.setNote(note);
			listReturn.add(bean);

		}

		return listReturn;
	}

	/**
	 * Lấy list BillDetailViewBean người mua trả hàng theo Bill
	 */
	@Override
	public List<BillDetailViewBean> findByBillDetailCustomerReturn(Bill bill)
			throws NumberFormatException, ReflectiveOperationException {
		List<Object[]> listResult = billDetailRepository.findByBillDetailCustomerReturn(bill);
		List<BillDetailViewBean> listReturn = new ArrayList<>();
		for (Object[] obj : listResult) {
			String pvId = "";
			if (obj[0] != null) {
				pvId = obj[0].toString();
			}
			String quantity = obj[1].toString();
			String price = obj[2].toString();
			String tax = obj[3].toString();
			String totalMoney = obj[4].toString();
			String user = obj[5].toString();
			String note = "";
			if (obj[6] != null) {
				note = obj[6].toString();
			}
			BillDetailViewBean bean = new BillDetailViewBean();
			if (pvId != "") {
				bean.setProductVariant(customNameProductByOneProductVariant(
						productVariantRepository.findById(Long.parseLong(obj[0].toString())).get()));
			}
			bean.setQuantity(Long.parseLong(quantity));
			bean.setPrice(new BigDecimal(price));
			bean.setTax(new BigDecimal(tax));
			bean.setTotalMoney(new BigDecimal(totalMoney));
			bean.setUserConfirm(userRepository.findById(Integer.parseInt(user)).get());
			bean.setNote(note);
			listReturn.add(bean);
		}
		return listReturn;
	}

	/**
	 * Lấy list BillDetailReturnCustomerViewBean cửa hàng trả cho khách theo Bill
	 */
	@Override
	public List<BillDetailReturnCustomerViewBean> findByBillDetailStoreReturn(Bill bill)
			throws ReflectiveOperationException {
		List<Object[]> listResult = billDetailRepository.findByBillDetailStoreReturn(bill);
		List<BillDetailReturnCustomerViewBean> listReturn = new ArrayList<>();

		for (Object[] obj : listResult) {
			String pvId = "";
			if (obj[0] != null) {
				pvId = obj[0].toString();
			}
			String quantity = obj[1].toString();
			String price = obj[2].toString();
			String tax = obj[3].toString();
			String totalMoney = obj[4].toString();
			String user = obj[5].toString();
			String note = "";
			if (obj[6] != null) {
				note = obj[6].toString();
			}
			String billDetailId = obj[7].toString();
			BillDetailReturnCustomerViewBean bean = new BillDetailReturnCustomerViewBean();
			if (pvId != "") {
				bean.setProductVariant(customNameProductByOneProductVariant(
						productVariantRepository.findById(Long.parseLong(obj[0].toString())).get()));
			}
			bean.setQuantity(Long.parseLong(quantity));
			bean.setPrice(new BigDecimal(price));
			bean.setTax(new BigDecimal(tax));
			bean.setTotalMoney(new BigDecimal(totalMoney));
			bean.setUserConfirm(userRepository.findById(Integer.parseInt(user)).get());
			bean.setNote(note);
			bean.setBillDetailParent(billDetailRepository.findById(billDetailId).get());
			bean.getBillDetailParent().setProductVariants(
					customNameProductByOneProductVariant(bean.getBillDetailParent().getProductVariants()));
			listReturn.add(bean);
		}
		return listReturn;
	}

	/**
	 * Custome ProductName theo ProductVariant
	 * 
	 * @param productVariant
	 * @return
	 * @throws ReflectiveOperationException
	 */
	public ProductVariant customNameProductByOneProductVariant(ProductVariant productVariant)
			throws ReflectiveOperationException {
		Product productOld = productRepository.findById(productVariant.getProducts().getProductId()).get();
		List<VariantValue> list = variantValueRepository.findByProductVariantsLike(productVariant);
		Comparator<VariantValue> comparator = Comparator.comparing(h -> h.getOptionValues().getValueName());
		list.sort(comparator.reversed());
		StringJoiner name = new StringJoiner("-");
		for (int i = 0; i < list.size(); i++) {
			VariantValue value = list.get(i);
			if (value.getOptionValues().getIsShow() == 1) {
				name.add(value.getOptionValues().getValueName());
			}
		}
		String productName = " [" + name.toString() + "]";
		Product productNew = new Product();
		BeanUtils.copyProperties(productOld, productNew);
		productNew.setProductName(productOld.getProductName() + productName);
		productVariant.setProducts(productNew);

		return productVariant;
	}

}
