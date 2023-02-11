/**
 * DATN_FALL2022, 2022
 * RevenueOnlineOfflineAllBean.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import java.math.BigDecimal;

import lombok.Data;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Data
public class RevenueOnlineOfflineAllBean {
	private BigDecimal all;
	private BigDecimal online;
	private BigDecimal offline;
}
