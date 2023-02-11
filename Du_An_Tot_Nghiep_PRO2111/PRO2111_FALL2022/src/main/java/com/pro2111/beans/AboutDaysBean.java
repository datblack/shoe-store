/**
 * DATN_FALL2022, 2022
 * AboutDaysBean.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Data
public class AboutDaysBean {
	@NotNull(message = "{AboutDaysBean.startDate.NotNull}")
	private LocalDate startDate;
	@NotNull(message = "{AboutDaysBean.endDate.NotNull}")
	private LocalDate endDate;
	private BigDecimal revenue;
}
