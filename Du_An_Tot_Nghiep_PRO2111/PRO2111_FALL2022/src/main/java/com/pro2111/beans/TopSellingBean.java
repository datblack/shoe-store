/**
 * DATN_FALL2022, 2022
 * TopSellingBean.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Data
public class TopSellingBean {
	@NotBlank
	private String type;
	@NotNull
	private int top;
	@NotBlank
	private String typeDate;
}
