/**
 * DATN_FALL2022, 2022
 * ProductAndProductOptionBean.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.pro2111.entities.Product;
import com.pro2111.entities.ProductOption;

import lombok.Data;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Data
public class ProductAndProductOptionBean {
	@Valid
	@NotNull
	private Product product;

	private List<ProductOption> productOptions;
}
