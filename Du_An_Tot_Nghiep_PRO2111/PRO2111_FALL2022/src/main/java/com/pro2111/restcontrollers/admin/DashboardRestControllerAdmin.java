/**
 * DATN_FALL2022, 2022
 * DashboardRestControllerAdmin.java, BUI_QUANG_HIEU
 */
package com.pro2111.restcontrollers.admin;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pro2111.beans.AboutDaysBean;
import com.pro2111.beans.RevenueMonthByYearBean;
import com.pro2111.beans.RevenueOnlineOfflineAllBean;
import com.pro2111.beans.TopProductVariantSelling;
import com.pro2111.beans.TopSellingBean;
import com.pro2111.beans.TurnoverRateBean;
import com.pro2111.service.DashboardService;
import com.pro2111.utils.Common;
import com.pro2111.utils.Constant;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("admin/rest/dashboards")
public class DashboardRestControllerAdmin {
	@Autowired
	private DashboardService dashboardService;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Lấy doanh thu tất cả các năm có trong bill
	 * 
	 * @return
	 */
	@GetMapping("get-all-year-in-bill")
	@PreAuthorize("@adminAuthorizer.authorize(authentication)")
	public ResponseEntity<List<Integer>> getAllYearInBill() {
		try {
			return ResponseEntity.ok(dashboardService.getAllYearInBill());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy tỉ lệ doanh thu online và offline
	 * 
	 * @return
	 */
	@GetMapping("get-turnover-rate")
	@PreAuthorize("@adminAuthorizer.authorize(authentication)")
	public ResponseEntity<TurnoverRateBean> getTurnoverRate() {
		try {
			return ResponseEntity.ok(dashboardService.getTurnoverRate());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy doanh thu các tháng theo năm
	 * 
	 * @param year
	 * @return
	 */
	@GetMapping("get-element-month-by-year/{year}")
	@PreAuthorize("@adminAuthorizer.authorize(authentication)")
	public ResponseEntity<List<RevenueMonthByYearBean>> getElementMonthByYear(@PathVariable("year") int year) {
		try {
			return ResponseEntity.ok(dashboardService.getElementMonthByYear(year));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy doanh thu theo năm
	 * 
	 * @param year
	 * @return
	 */
	@GetMapping("get-revenue-year/{year}")
	@PreAuthorize("@adminAuthorizer.authorize(authentication)")
	public ResponseEntity<RevenueOnlineOfflineAllBean> getRevenueYear(@PathVariable("year") int year) {
		try {
			return ResponseEntity.ok(dashboardService.getRevenueYear(year));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy doanh thu theo các kiểu khác nhau Hôm nay Hôm qua 7 ngày qua Tháng hiện
	 * tại Tháng trước
	 * 
	 * @param type
	 * @return
	 */
	@GetMapping("get-revenue/{type}")
	@PreAuthorize("@adminAuthorizer.authorize(authentication)")
	public ResponseEntity<JsonNode> getRevenue(@PathVariable("type") String type) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode node = mapper.createObjectNode();
			if (Constant.YESTERDAY.equals(type)) {
				LocalDate date = LocalDate.now();
				date = date.minusDays(1);
				node.put("value", dashboardService.getRevenueToday(date));
			} else if (Constant.SEVEN_DAYS.equals(type)) {
				LocalDate end = LocalDate.now();
				LocalDate start = end.minusDays(7);
				node.put("value", dashboardService.getRevenueByAboutDays(start, end));
			} else if (Constant.THIS_MONTH.equals(type)) {
				LocalDate date = LocalDate.now();
				node.put("value", dashboardService.getMonthlyRevenue(date));
			} else if (Constant.LAST_MONTH.equals(type)) {
				LocalDate date = LocalDate.now();
				date = date.minusMonths(1);
				node.put("value", dashboardService.getMonthlyRevenue(date));
			} else {
				LocalDate date = LocalDate.now();
				node.put("value", dashboardService.getRevenueToday(date));
			}
			return ResponseEntity.ok(node);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy doanh thu theo các kiểu khác nhau + kiểu hóa đơn (online/offline)
	 * 
	 * @param type
	 * @param billType
	 * @return
	 */
	@GetMapping("get-revenue/{type}/{billType}")
	@PreAuthorize("@adminAuthorizer.authorize(authentication)")
	public ResponseEntity<JsonNode> getRevenueByBillType(@PathVariable("type") String type,
			@PathVariable("billType") int billType) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode node = mapper.createObjectNode();
			if (Constant.YESTERDAY.equals(type)) {
				LocalDate date = LocalDate.now();
				date = date.minusDays(1);
				node.put("value", dashboardService.getRevenueTodayByBillType(date, billType));
			} else if (Constant.SEVEN_DAYS.equals(type)) {
				LocalDate end = LocalDate.now();
				LocalDate start = end.minusDays(7);
				node.put("value", dashboardService.getRevenueByAboutDaysByBillType(start, end, billType));
			} else if (Constant.THIS_MONTH.equals(type)) {
				LocalDate date = LocalDate.now();
				node.put("value", dashboardService.getMonthlyRevenueByBillType(date, billType));
			} else if (Constant.LAST_MONTH.equals(type)) {
				LocalDate date = LocalDate.now();
				date = date.minusMonths(1);
				node.put("value", dashboardService.getMonthlyRevenueByBillType(date, billType));
			} else {
				LocalDate date = LocalDate.now();
				node.put("value", dashboardService.getRevenueTodayByBillType(date, billType));
			}
			return ResponseEntity.ok(node);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/*
	 * Lấy ngày ngày hiện tại và 7 ngày trước
	 */
	@GetMapping("get-about-days")
	@PreAuthorize("@adminAuthorizer.authorize(authentication)")
	public ResponseEntity<AboutDaysBean> getAboutDays() {
		try {
			AboutDaysBean aboutDaysBean = new AboutDaysBean();
			LocalDate end = LocalDate.now();
			LocalDate start = end.minusDays(7);
			aboutDaysBean.setStartDate(start);
			aboutDaysBean.setEndDate(end);
			aboutDaysBean.setRevenue(dashboardService.getRevenueByAboutDays(start, end));
			return ResponseEntity.ok(aboutDaysBean);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy doanh thu theo khoảng ngày
	 * 
	 * @param aboutDaysBean
	 * @param locale
	 * @return
	 */
	@PostMapping("get-revenue-by-about-day")
	@PreAuthorize("@adminAuthorizer.authorize(authentication)")
	public ResponseEntity<?> getRevenueByAboutDays(@Valid @RequestBody AboutDaysBean aboutDaysBean, Locale locale) {
		try {
			Map<String, String> errorMap = new HashedMap<String, String>();
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode node = mapper.createObjectNode();
			if (aboutDaysBean.getEndDate().isAfter(LocalDate.now())) {
				errorMap.put("endDate", messageSource.getMessage("AboutDaysBean.endDate.isAfterNow", null, locale));
			}
			if (errorMap.size() > 0) {
				return ResponseEntity.badRequest().body(errorMap);
			} else {
				return ResponseEntity.ok(node.put("value", dashboardService
						.getRevenueByAboutDays(aboutDaysBean.getStartDate(), aboutDaysBean.getEndDate())));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy doanh thu theo khoảng ngày và loại hóa đơn (online/offline)
	 * 
	 * @param aboutDaysBean
	 * @param locale
	 * @param billType
	 * @return
	 */
	@PostMapping("get-revenue-by-about-day/{billType}")
	@PreAuthorize("@adminAuthorizer.authorize(authentication)")
	public ResponseEntity<?> getRevenueByAboutDays(@Valid @RequestBody AboutDaysBean aboutDaysBean, Locale locale,
			@PathVariable("billType") int billType) {
		try {
			Map<String, String> errorMap = new HashedMap<String, String>();
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode node = mapper.createObjectNode();
			if (aboutDaysBean.getEndDate().isAfter(LocalDate.now())) {
				errorMap.put("endDate", messageSource.getMessage("AboutDaysBean.endDate.isAfterNow", null, locale));
			}
			if (errorMap.size() > 0) {
				return ResponseEntity.badRequest().body(errorMap);
			} else {
				return ResponseEntity.ok(node.put("value", dashboardService.getRevenueByAboutDaysByBillType(
						aboutDaysBean.getStartDate(), aboutDaysBean.getEndDate(), billType)));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/*
	 * Lấy ra những product variant có quantity <=?
	 */
	@GetMapping("get-product-variant-by-less-quantity/{quantity}")
	@PreAuthorize("@adminAuthorizer.authorize(authentication)")
	public ResponseEntity<?> getProductVariantByQuantity(@PathVariable("quantity") String quantityString,
			Locale locale) {
		try {
			int quantity = Common.convertStringToInt(quantityString, Constant.QUANTITY_DEFAULT);
			if (quantity == Constant.QUANTITY_DEFAULT) {
				Map<String, String> error = new HashMap<>();
				error.put("quantity", messageSource.getMessage("Dashboard.quantity", null, locale));
				return ResponseEntity.badRequest().body(error);
			} else {
				return ResponseEntity.ok(dashboardService.getProductVariantByLessQuantity(quantity));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy ra tất cả sản phẩm bán chạy, bán ế
	 * 
	 * @param topSellingBean
	 * @return
	 */
	@PostMapping("find-top-product-selling")
	public ResponseEntity<List<TopProductVariantSelling>> findTopProductVariantSelling(
			@Valid @RequestBody TopSellingBean topSellingBean) {
		try {
			if (Constant.TYPE_SELLING.equals(topSellingBean.getType())) {
				if (Constant.LAST_MONTH.equals(topSellingBean.getTypeDate())) {
					LocalDate date = LocalDate.now();
					date = date.minusMonths(1);
					return ResponseEntity
							.ok(dashboardService.findTopProductVariantSellingIsMonth(topSellingBean.getTop(), date));
				} else if (Constant.THIS_YEAR.equals(topSellingBean.getTypeDate())) {
					return ResponseEntity.ok(dashboardService
							.findTopProductVariantSellingIsYear(topSellingBean.getTop(), LocalDate.now()));
				} else if (Constant.SEVEN_DAYS.equals(topSellingBean.getTypeDate())) {
					LocalDateTime end = LocalDateTime.now();
					LocalDateTime start = end.minusDays(7);
					return ResponseEntity.ok(dashboardService
							.findTopProductVariantSellingIsSevenDay(topSellingBean.getTop(), start, end));
				} else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
				}
			} else if (Constant.TYPE_NO_SELLING.equals(topSellingBean.getType())) {
				if (Constant.LAST_MONTH.equals(topSellingBean.getTypeDate())) {
					LocalDate date = LocalDate.now();
					date = date.minusMonths(1);
					return ResponseEntity
							.ok(dashboardService.findTopProductVariantNoSellingIsMonth(topSellingBean.getTop(), date));
				} else if (Constant.THIS_YEAR.equals(topSellingBean.getTypeDate())) {
					return ResponseEntity.ok(dashboardService
							.findTopProductVariantNoSellingIsYear(topSellingBean.getTop(), LocalDate.now()));
				} else if (Constant.SEVEN_DAYS.equals(topSellingBean.getTypeDate())) {
					LocalDate end = LocalDate.now();
					LocalDate start = end.minusDays(7);
					return ResponseEntity.ok(dashboardService
							.findTopProductVariantNoSellingIsSevenDay(topSellingBean.getTop(), start, end));
				} else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
				}
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy ra top khách hàng mua nhiều tiền nhất trong năm x
	 * 
	 * @return
	 */
	@GetMapping("find-top-customer/{top}/{year}")
	public ResponseEntity<?> findTopCustomer(@PathVariable("top") String topString,
			@PathVariable("year") String yearString, Locale locale) {
		try {
			int top = Common.convertStringToInt(topString, Constant.TOP_DEFAULT);
			int year = Common.convertStringToInt(yearString, Constant.YEAR_DEFAULT);
			Map<String, String> error = new HashMap<>();
			if (top == Constant.TOP_DEFAULT) {
				error.put("quantity", messageSource.getMessage("Dashboard.top", null, locale));

			} else if (top < Constant.TOP_MIN) {
				error.put("quantity", messageSource.getMessage("Dashboard.topMin", null, locale));
			}

			if (year == Constant.YEAR_DEFAULT) {
				error.put("year", messageSource.getMessage("Dashboard.year", null, locale));

			}

			if (error.size() > 0) {
				return ResponseEntity.badRequest().body(error);
			} else {
				return ResponseEntity.ok(dashboardService.findTopCustomer(top, year));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy tổng số hóa đơn theo trạng thái và dạng ngày lấy
	 * 
	 * @param ex
	 * @return
	 */
	@GetMapping("count-bill-by-status-and-type-date/{status}/{typeDate}")
	public ResponseEntity<?> countBillByStatusAndTypeDate(@PathVariable("status") String statusString,
			@PathVariable("typeDate") String typeDateString, Locale locale) {
		try {
			Map<String, String> error = new HashMap<>();
			int status = Common.convertStringToInt(statusString, Constant.STATUS_DEFAULT);
			if (status == Constant.STATUS_DEFAULT || !(status == Constant.STATUS_BILL_CANCEL
					|| status == Constant.STATUS_BILL_SUCCESS || status == Constant.STATUS_BILL_WAIT_FOR_CONFIRMATION
					|| status == Constant.STATUS_BILL_WAIT_FOR_DELIVERING
					|| status == Constant.STATUS_BILL_WAIT_FOR_DELIVERY
					|| status == Constant.STATUS_BILL_WAIT_FOR_PAY)) {
				error.put("status", messageSource.getMessage("Dashboard.status", null, locale));
			}

			if (error.size() > 0) {
				return ResponseEntity.badRequest().body(error);
			} else {
				LocalDate date = LocalDate.now();
				if (Constant.TODAY.equals(typeDateString)) {
					return ResponseEntity.ok(dashboardService.countBillByStatusInDay(status, date));
				} else if (Constant.YESTERDAY.equals(typeDateString)) {
					date = date.minusDays(1);
					return ResponseEntity.ok(dashboardService.countBillByStatusInDay(status, date));
				} else if (Constant.SEVEN_DAYS.equals(typeDateString)) {
					LocalDate end = LocalDate.now();
					LocalDate start = end.minusDays(7);
					return ResponseEntity.ok(dashboardService.countBillByStatusInSevenDay(status, start, end));
				} else if (Constant.THIS_MONTH.equals(typeDateString)) {
					return ResponseEntity.ok(dashboardService.countBillByStatusInMonth(status, date));
				} else if (Constant.LAST_MONTH.equals(typeDateString)) {
					date = date.minusMonths(1);
					return ResponseEntity.ok(dashboardService.countBillByStatusInMonth(status, date));
				} else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy tổng số hóa đơn chờ xác nhận
	 * 
	 * @return
	 */
	@GetMapping("count-bill-wait-confirm")
	public ResponseEntity<Long> countBillWaitConfirm() {
		try {
			return ResponseEntity.ok(dashboardService.countBillWaitConfirm());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy tổng số hóa đơn chờ xác nhận trả hàng
	 * 
	 * @return
	 */
	@GetMapping("count-bill-wait-confirm-return")
	public ResponseEntity<Long> countBillWaitConfirmReturn() {
		try {
			return ResponseEntity.ok(dashboardService.countBillWaitConfirmReturn());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}
