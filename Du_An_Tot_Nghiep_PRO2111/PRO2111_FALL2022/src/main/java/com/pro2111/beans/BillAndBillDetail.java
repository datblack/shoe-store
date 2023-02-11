/**
 * DATN_FALL2022, 2022
 * BillAndBillDetail.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.pro2111.entities.Bill;
import com.pro2111.entities.BillDetail;

import lombok.Data;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Data
public class BillAndBillDetail {

	@NotNull
	private Bill bill;
	
	@NotEmpty
	private List<BillDetail> billDetails;
}
