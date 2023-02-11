/**
 * DATN_FALL2022, 2022
 * ProductVariantViewSaleBean.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import java.util.List;

import com.pro2111.entities.VariantValue;

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
public class VariantValueViewSaleBean {

	private List<VariantValue> variantValues;
	private Integer countSale;
	private List<String> saleName;
}
