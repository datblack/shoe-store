/**
 * DATN_FALL2022, 2022
 * FormateNumber.java, BUI_QUANG_HIEU
 */
package com.pro2111.utils;

import java.math.BigDecimal;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public class FormateNumber {

	/**
	 * Formate số
	 * 
	 * @param decimal
	 * @return
	 */
	public static String formateBigDecimal(BigDecimal decimal) {
		return String.format("%,.0f", decimal);
	}

}
