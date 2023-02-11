/**
 * DATN_FALL2022, 2022
 * EmailBean.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Data
public class EmailBean {
	@NotBlank(message = "{EmailBean.email.NotBlank}")
	@Email(message = "{EmailBean.email.Email}")
	private String email;

	@NotBlank(message = "{EmailBean.password.NotBlank}")
	private String password;
}
