/**
 * DATN_FALL2022, 2022
 * BillDetailViewBean.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import java.math.BigDecimal;

import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BUI_QUANG_HIEU
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDetailViewBean {
	private ProductVariant productVariant;
	private long quantity;
	private BigDecimal price;
	private BigDecimal tax;
	private BigDecimal totalMoney;
	private User userConfirm;
	private String note;
}
