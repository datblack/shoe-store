/**
 * DATN_FALL2022, 2022
 * PvAndVv.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.pro2111.entities.ProductVariant;
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
public class PvAndVv {

	@NotNull
	@Valid
	private ProductVariant productVariant;

	@NotEmpty
	private List<VariantValue> variantValues;

}
