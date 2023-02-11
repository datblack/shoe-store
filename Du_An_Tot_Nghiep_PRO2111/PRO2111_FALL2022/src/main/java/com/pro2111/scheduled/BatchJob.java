/**
 * DATN_FALL2022, 2022
 * BatchJob.java, BUI_QUANG_HIEU
 */
package com.pro2111.scheduled;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pro2111.entities.Sale;
import com.pro2111.service.ConfigIndexService;
import com.pro2111.service.SaleService;
import com.pro2111.utils.Common;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Component
public class BatchJob {
	@Autowired
	private ConfigIndexService configIndexes;

	@Autowired
	private SaleService saleService;

	private Integer count = 0;

	String[] strDays = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

	/**
	 * Mỗi phút chạy 1 lần từ thứ 2 đến chủ nhật
	 */
	@Scheduled(cron = "0 * * * * MON-SUN")
	public void perform() throws Exception {
		System.out.println("BatchJob: " + new Date());
		if (count == 0) {
			configIndexes.perform();
		}
		count++;
	}

	@Scheduled(cron = "0 * * * * MON-SUN")
	public void updateStatusSale() throws Exception {
		LocalDateTime dateTimeNow = LocalDateTime.now();
		long secondNow = dateTimeNow.toEpochSecond(ZoneOffset.UTC);
		List<Sale> listSaleUpdate = new ArrayList<Sale>();

		// SaleType = 0
		List<Sale> listSaleType0Status0 = saleService.findByStatus(0);
		List<Sale> listSaleType0Status1 = saleService.findByStatus(1);

		// SaleType = 1
		List<Sale> listSaleType1Status0 = saleService.findSaleChildByStatus(0);
		List<Sale> listSaleType1Status1 = saleService.findSaleChildByStatus(1);

		// update hoạt động saleType =0
		if (listSaleType0Status0.size() > 0) {
			listSaleType0Status0.forEach(sale -> {
				long secondInput = sale.getStartDate().toEpochSecond(ZoneOffset.UTC);
				long minute = (secondNow - secondInput) / 60;
				if (minute >= 0) {
					sale.setStatus(1);
					listSaleUpdate.add(sale);
				}
			});
		}

		// update ngưng hoạt động saleType =0
		if (listSaleType0Status1.size() > 0) {
			listSaleType0Status1.forEach(sale -> {
				long secondInput = sale.getEndDate().toEpochSecond(ZoneOffset.UTC);
				long minute = (secondInput - secondNow) / 60;
				if (minute <= 0) {
					sale.setStatus(2);
					listSaleUpdate.add(sale);
				}
			});
		}
		// update hoạt động saleType = 1
		if (listSaleType1Status0.size() > 0 && !listSaleType1Status0.isEmpty()) {
			for (int i = 0; i < listSaleType1Status0.size(); i++) {
				long secondInput = listSaleType1Status0.get(i).getStartDate().toEpochSecond(ZoneOffset.UTC);
				long minute = (secondInput - secondNow) / 60;
				if (listSaleType1Status0.get(i).getStartDate() == null) {
					if (listSaleType1Status0.get(i).getStartAt() == null) {
						if (DayOfWeek.of(listSaleType1Status1.get(i).getWeekday())
								.equals(LocalDate.now().getDayOfWeek())) {
							listSaleType1Status0.get(i).setStatus(1);
							listSaleType1Status0.get(i).getSaleParent().setStatus(1);
							listSaleUpdate.add(listSaleType1Status0.get(i));
							listSaleUpdate.add(listSaleType1Status0.get(i).getSaleParent());
						}
					} else {
						if (DayOfWeek.of(listSaleType1Status1.get(i).getWeekday())
								.equals(LocalDate.now().getDayOfWeek())
								&& Common.checkTimeToLocal(listSaleType1Status0.get(i).getStartAt())) {
							listSaleType1Status0.get(i).setStatus(1);
							listSaleType1Status0.get(i).getSaleParent().setStatus(1);
							listSaleUpdate.add(listSaleType1Status0.get(i));
							listSaleUpdate.add(listSaleType1Status0.get(i).getSaleParent());
						}
					}
				} else {
					if (listSaleType1Status0.get(i).getStartAt() == null) {
						if (minute <= 0 && Common.checkTimeToLocal(listSaleType1Status0.get(i).getStartAt())) {
							listSaleType1Status0.get(i).setStatus(1);
							listSaleType1Status0.get(i).getSaleParent().setStatus(1);
							listSaleUpdate.add(listSaleType1Status0.get(i));
							listSaleUpdate.add(listSaleType1Status0.get(i).getSaleParent());
						}
					} else {
						if (minute <= 0 && Common.checkTimeToLocal(listSaleType1Status0.get(i).getStartAt())
								&& DayOfWeek.of(listSaleType1Status0.get(i).getWeekday())
										.equals(LocalDate.now().getDayOfWeek())) {
							System.out.println("130 roi");
							listSaleType1Status0.get(i).setStatus(1);
							listSaleType1Status0.get(i).getSaleParent().setStatus(1);
							listSaleUpdate.add(listSaleType1Status0.get(i));
							listSaleUpdate.add(listSaleType1Status0.get(i).getSaleParent());
						}
					}
				}

			}
		}

		// update ngưng hoạt động saleType = 1
		if (listSaleType1Status1.size() > 0 && !listSaleType1Status1.isEmpty()) {
			for (int i = 0; i < listSaleType1Status1.size(); i++) {
				long secondInput = listSaleType1Status1.get(i).getEndDate().toEpochSecond(ZoneOffset.UTC);
				long minute = (secondInput - secondNow) / 60;
				if (listSaleType1Status1.get(i).getEndDate() == null) {
					if (listSaleType1Status1.get(i).getEndAt() == null) {
						if (DayOfWeek.of(listSaleType1Status1.get(i).getWeekday()).equals(
								LocalDate.now().getDayOfWeek()) && LocalTime.now().equals(LocalTime.of(23, 59))) {
							listSaleType1Status1.get(i).setStatus(2);
							listSaleType1Status1.get(i).getSaleParent().setStatus(2);
							listSaleUpdate.add(listSaleType1Status1.get(i));
							listSaleUpdate.add(listSaleType1Status1.get(i).getSaleParent());
						}
					} else {
						if (Common.checkTimeToLocal(listSaleType1Status1.get(i).getEndAt()) && DayOfWeek
								.of(listSaleType1Status1.get(i).getWeekday()).equals(LocalDate.now().getDayOfWeek())) {
							listSaleType1Status1.get(i).setStatus(2);
							listSaleType1Status1.get(i).getSaleParent().setStatus(2);
							listSaleUpdate.add(listSaleType1Status1.get(i));
							listSaleUpdate.add(listSaleType1Status1.get(i).getSaleParent());
						}
					}
				} else {
					if (listSaleType1Status1.get(i).getEndAt() == null) {
						if (minute <= 0 && Common.checkTimeToLocal(listSaleType1Status1.get(i).getEndAt())
								&& LocalTime.now().equals(LocalTime.of(23, 59))) {
							listSaleType1Status1.get(i).setStatus(2);
							listSaleType1Status1.get(i).getSaleParent().setStatus(2);
							listSaleUpdate.add(listSaleType1Status1.get(i));
							listSaleUpdate.add(listSaleType1Status1.get(i).getSaleParent());
						}
					} else {
						if ((minute <= 0 || Common.checkTimeToLocal(listSaleType1Status1.get(i).getEndAt()))
								&& DayOfWeek.of(listSaleType1Status1.get(i).getWeekday())
										.equals(LocalDate.now().getDayOfWeek())) {
							listSaleType1Status1.get(i).setStatus(2);
							listSaleType1Status1.get(i).getSaleParent().setStatus(2);
							listSaleUpdate.add(listSaleType1Status1.get(i));
							listSaleUpdate.add(listSaleType1Status1.get(i).getSaleParent());
						}

					}
				}

			}
		}

		if (!listSaleUpdate.isEmpty()) {
			saleService.updateSale(listSaleUpdate);
		}
	}
}
