/**
 * DATN_FALL2022, 2022
 * StatusBillBean.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import com.pro2111.entities.Bill;

import lombok.Data;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Data
public class StatusBillBean {

	private Bill bill;
	private int status;
}
