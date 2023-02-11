/**
 * DATN_FALL2022, 2022
 * OpAndOvService.java, BUI_QUANG_HIEU
 */
package com.pro2111.service;

import java.util.List;

import com.pro2111.beans.OptionAndOptionValue;
import com.pro2111.entities.Product;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public interface OpAndOvService {

	/**
	 * Lấy list OptionAndOptionValue theo Product
	 * 
	 * @param product
	 * @return
	 */
	List<OptionAndOptionValue> findByProduct(Product product);
}
