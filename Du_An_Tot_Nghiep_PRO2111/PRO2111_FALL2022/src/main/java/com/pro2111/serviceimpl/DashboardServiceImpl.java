/**
 * DATN_FALL2022, 2022
 * DashboardServiceImpl.java, BUI_QUANG_HIEU
 */
package com.pro2111.serviceimpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;

import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro2111.beans.RevenueMonthByYearBean;
import com.pro2111.beans.RevenueOnlineOfflineAllBean;
import com.pro2111.beans.TopCustomerBean;
import com.pro2111.beans.TopProductVariantSelling;
import com.pro2111.beans.TurnoverRateBean;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.VariantValue;
import com.pro2111.repositories.BillDetailRepository;
import com.pro2111.repositories.BillRepository;
import com.pro2111.repositories.ProductRepository;
import com.pro2111.repositories.ProductVariantRepository;
import com.pro2111.repositories.VariantValueRepository;
import com.pro2111.service.DashboardService;
import com.pro2111.utils.Common;
import com.pro2111.utils.Constant;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Service
public class DashboardServiceImpl implements DashboardService {
	@Autowired
	private BillRepository billRepository;

	@Autowired
	private ProductVariantRepository productVariantRepository;

	@Autowired
	private BillDetailRepository billDetailRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private VariantValueRepository variantValueRepository;

	@Override
	public synchronized List<RevenueMonthByYearBean> getElementMonthByYear(int year) {
		return billRepository.getMonthByYearRevenue(year);
	}

	@Override
	public synchronized BigDecimal getMonthlyRevenue(LocalDate date) {
		return billRepository.getMonthlyRevenue(date);
	}

	@Override
	public synchronized BigDecimal getRevenueToday(LocalDate date) {
		return billRepository.getRevenueToday(date);
	}

	@Override
	public synchronized RevenueOnlineOfflineAllBean getRevenueYear(int year) {
		RevenueOnlineOfflineAllBean bean = new RevenueOnlineOfflineAllBean();
		bean.setAll(billRepository.getRevenueYear(year));
		bean.setOffline(billRepository.getRevenueYearByBillType(year, Constant.BILL_TYPE_OFFLINE));
		bean.setOnline(billRepository.getRevenueYearByBillType(year, Constant.BILL_TYPE_ONLINE));
		return bean;
	}

	@Override
	public synchronized BigDecimal getRevenueByAboutDays(LocalDate startDate, LocalDate endDate) {
		if (!endDate.isAfter(startDate)) {
			LocalDate temp = startDate;
			startDate = endDate;
			endDate = temp;
		}
		LocalDateTime sDateTime = LocalDateTime.of(startDate.getYear(), startDate.getMonthValue(),
				startDate.getDayOfMonth(), 0, 0);
		LocalDateTime eDateTime = LocalDateTime.of(endDate.getYear(), endDate.getMonthValue(), endDate.getDayOfMonth(),
				0, 0);
		return billRepository.getRevenueByAboutDays(sDateTime, eDateTime);
	}

	/**
	 * Lấy ra list năm có hóa đơn
	 */
	@Override
	public synchronized List<Integer> getAllYearInBill() {
		List<Integer> years = billRepository.getAllYearInBill();
//		years.remove(years.size() - 1);
		return years;
	}

	/**
	 * Lấy ra tỉ lệ doanh thu online và offline
	 */
	@Override
	public synchronized TurnoverRateBean getTurnoverRate() {
		TurnoverRateBean bean = new TurnoverRateBean();
		double countAll = billRepository.countAllBillByStatus(Constant.STATUS_BILL_SUCCESS);
		double countOnline = billRepository.countAllBillSuccessByOnline();
		countOnline = (countOnline / countAll) * 100;
		bean.setOnline((int) countOnline);
		bean.setOffline(Constant.RATE - (int) countOnline);
		return bean;
	}

	/**
	 * Lấy doanh thu ngày hôm nay theo kiểu bill
	 */
	@Override
	public synchronized BigDecimal getRevenueTodayByBillType(LocalDate date, int billType) {
		return billRepository.getRevenueTodayByBillType(date, billType);
	}

	/**
	 * Lấy doanh thu khoảng ngày theo kiểu bill
	 */
	@Override
	public synchronized BigDecimal getRevenueByAboutDaysByBillType(LocalDate startDate, LocalDate endDate,
			int billType) {
		if (!endDate.isAfter(startDate)) {
			LocalDate temp = startDate;
			startDate = endDate;
			endDate = temp;
		}
		LocalDateTime sDateTime = LocalDateTime.of(startDate.getYear(), startDate.getMonthValue(),
				startDate.getDayOfMonth(), 0, 0);
		LocalDateTime eDateTime = LocalDateTime.of(endDate.getYear(), endDate.getMonthValue(), endDate.getDayOfMonth(),
				0, 0);
		return billRepository.getRevenueByAboutDaysByBillType(sDateTime, eDateTime, billType);
	}

	/**
	 * Lấy doanh thu thàng theo kiểu bill
	 */
	@Override
	public synchronized BigDecimal getMonthlyRevenueByBillType(LocalDate date, int billType) {
		return billRepository.getMonthlyRevenueByBillType(date, billType);
	}

	/**
	 * Lấy list ProductVariant có quantity <= ?
	 */
	@Override
	public synchronized List<ProductVariant> getProductVariantByLessQuantity(int quantity) {
		List<ProductVariant> productVariantsReturn = productVariantRepository.getProductVariantByLessQuantity(quantity);
		productVariantsReturn = Common.customNameProductByProductVariant(productVariantsReturn);
		return productVariantsReturn;
	}

	/**
	 * Lấy list ProductVariant bán chạy tháng hiện tại
	 */
	@Override
	public List<TopProductVariantSelling> findTopProductVariantSellingIsMonth(int top, LocalDate date)
			throws ReflectiveOperationException {
		try {
			List<TopProductVariantSelling> listReturn = new ArrayList<>();
			List<TopProductVariantSelling> list = billDetailRepository.findTopProductVariantSellingIsMonth(date);
			if (top > list.size()) {
				listReturn = list;
			} else {
				for (int i = 0; i < top; i++) {
					list.get(i)
							.setProductVariant(customNameProductByOneProductVariant(list.get(i).getProductVariant()));
					listReturn.add(list.get(i));
				}
			}
			return listReturn;
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<TopProductVariantSelling> findTopProductVariantNoSellingIsMonth(int top, LocalDate date)
			throws ReflectiveOperationException {

		try {
			List<TopProductVariantSelling> listReturn = new ArrayList<>();
			List<TopProductVariantSelling> list = billDetailRepository.findTopProductVariantNoSellingIsMonth(date);
			if (top > list.size()) {
				listReturn = list;
			} else {
				for (int i = 0; i < top; i++) {
					list.get(i)
							.setProductVariant(customNameProductByOneProductVariant(list.get(i).getProductVariant()));
					listReturn.add(list.get(i));
				}
			}
			return listReturn;
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public List<TopProductVariantSelling> findTopProductVariantSellingIsYear(int top, LocalDate date)
			throws ReflectiveOperationException {
		try {
			List<TopProductVariantSelling> listReturn = new ArrayList<>();
			List<TopProductVariantSelling> list = billDetailRepository.findTopProductVariantSellingIsYear(date);
			if (top > list.size()) {
				listReturn = list;
			} else {
				for (int i = 0; i < top; i++) {
					list.get(i)
							.setProductVariant(customNameProductByOneProductVariant(list.get(i).getProductVariant()));
					listReturn.add(list.get(i));
				}
			}
			return listReturn;
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<TopProductVariantSelling> findTopProductVariantNoSellingIsYear(int top, LocalDate date)
			throws ReflectiveOperationException {
		try {
			List<TopProductVariantSelling> listReturn = new ArrayList<>();
			List<TopProductVariantSelling> list = billDetailRepository.findTopProductVariantNoSellingIsYear(date);
			if (top > list.size()) {
				listReturn = list;
			} else {
				for (int i = 0; i < top; i++) {
					list.get(i)
							.setProductVariant(customNameProductByOneProductVariant(list.get(i).getProductVariant()));
					listReturn.add(list.get(i));
				}
			}
			return listReturn;
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<TopProductVariantSelling> findTopProductVariantSellingIsSevenDay(int top, LocalDateTime start,
			LocalDateTime end) throws ReflectiveOperationException {
		try {
			List<TopProductVariantSelling> listReturn = new ArrayList<>();
			List<TopProductVariantSelling> list = billDetailRepository.findTopProductVariantSellingIsSevenDay(start,
					end);
			if (top > list.size()) {
				listReturn = list;
			} else {
				for (int i = 0; i < top; i++) {
					list.get(i)
							.setProductVariant(customNameProductByOneProductVariant(list.get(i).getProductVariant()));
					listReturn.add(list.get(i));
				}
			}
			return listReturn;
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<TopProductVariantSelling> findTopProductVariantNoSellingIsSevenDay(@NotNull int top, LocalDate start,
			LocalDate end) throws ReflectiveOperationException {
		try {
			List<TopProductVariantSelling> listReturn = new ArrayList<>();
			List<TopProductVariantSelling> list = billDetailRepository.findTopProductVariantNoSellingIsSevenDay(start,
					end);
			if (top > list.size()) {
				listReturn = list;
			} else {
				for (int i = 0; i < top; i++) {
					list.get(i)
							.setProductVariant(customNameProductByOneProductVariant(list.get(i).getProductVariant()));
					listReturn.add(list.get(i));
				}
			}
			return listReturn;
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<TopCustomerBean> findTopCustomer(int top, int year) {
		List<TopCustomerBean> results = billRepository.findByTopCustomerSellingByYear(year);
		List<TopCustomerBean> listReturn = new ArrayList<>();
		if (top > results.size()) {
			listReturn = results;
		} else {
			for (int i = 0; i < top; i++) {
				listReturn.add(results.get(i));
			}
		}
		return listReturn;
	}

	@Override
	public long countBillByStatusInDay(int status, LocalDate date) {
		long resultReturn = 0;
		if (Constant.STATUS_BILL_SUCCESS == status) {
			resultReturn = billRepository.countBillBySuccessInDay(date);
		} else if (Constant.STATUS_BILL_CANCEL == status) {
			resultReturn = billRepository.countBillByCancelInDay(date);
		}
		return resultReturn;
	}

	@Override
	public long countBillByStatusInMonth(int status, LocalDate date) {
		long resultReturn = 0;
		if (Constant.STATUS_BILL_SUCCESS == status) {
			resultReturn = billRepository.countBillBySuccessInMonth(date);
		} else if (Constant.STATUS_BILL_CANCEL == status) {
			resultReturn = billRepository.countBillByCancelInMonth(date);
		}
		return resultReturn;
	}

	@Override
	public long countBillByStatusInSevenDay(int status, LocalDate start, LocalDate end) {
		long resultReturn = 0;
		if (Constant.STATUS_BILL_SUCCESS == status) {
			resultReturn = billRepository.countBillBySuccessInSevenDay(start, end);
		} else if (Constant.STATUS_BILL_CANCEL == status) {
			resultReturn = billRepository.countBillByCancelInSevenDay(start, end);
		}
		return resultReturn;
	}

	/**
	 * CustomeName
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

	/**
	 * Lấy tổng số hóa đơn chờ xác nhận
	 * 
	 * @return
	 */
	@Override
	public long countBillWaitConfirm() {
		return billRepository.countBillWaitConfirm();
	}

	/**
	 * Lấy tổng số hóa đơn chờ xác trả hàng
	 * 
	 * @return
	 */
	@Override
	public long countBillWaitConfirmReturn() {
		return billRepository.countBillWaitConfirmReturn();
	}

}
