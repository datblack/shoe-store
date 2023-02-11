/**
 * DATN_FALL2022, 2022
 * ProductVariansBean.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import java.util.List;

import javax.validation.Valid;

import com.pro2111.entities.ProductVariant;

import lombok.Data;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Data
public class ProductVariansBean {
	@Valid
	private List<ProductVariant> productVariants;
	private List<ProductVariant> productVariantsOld;
	private int userId;
}
