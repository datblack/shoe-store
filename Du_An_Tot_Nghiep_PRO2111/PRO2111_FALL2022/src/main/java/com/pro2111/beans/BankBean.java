/**
 * DATN_FALL2022, 2022
 * BankBean.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import javax.validation.constraints.NotBlank;

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
public class BankBean {

	@NotBlank(message = "{BankBean.accountNumber.NotBlank}")
	private String accountNumber;
	
	@NotBlank(message = "{BankBean.bankName.NotBlank}")
	private String bankName;
	
	@NotBlank(message = "{BankBean.accountHolder.NotBlank}")
	private String accountHolder;
	
}
