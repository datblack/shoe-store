/**
 * DATN_FALL2022, 2022
 * BillDetailReturnBean.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.pro2111.entities.BillDetail;

import lombok.Data;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Data
public class BillDetailReturnBean {

	@NotNull
	private BillDetail billDetail;
	@NotNull
	private List<Integer> returnTypes;
	@NotBlank
	private List<BillDetail> billDetails;
}
