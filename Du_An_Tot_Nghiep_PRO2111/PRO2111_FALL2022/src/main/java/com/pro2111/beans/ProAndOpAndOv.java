/**
 * DATN_FALL2022, 2022
 * ProAndOpAndOv.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import java.util.List;

import com.pro2111.entities.Option;
import com.pro2111.entities.OptionValue;
import com.pro2111.entities.Product;

import lombok.Data;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Data
public class ProAndOpAndOv {

	private Product product;
	private List<Option> options;
	private List<OptionValue> optionValues;
}
