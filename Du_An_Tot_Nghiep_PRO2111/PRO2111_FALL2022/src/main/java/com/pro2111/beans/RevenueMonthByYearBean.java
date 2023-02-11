/**
 * DATN_FALL2022, 2022
 * RevenueMonthByYearBean.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueMonthByYearBean {
	@Id
	private int month;
	private BigDecimal money;
}
