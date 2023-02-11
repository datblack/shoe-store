/**
 * DATN_FALL2022, 2022
 * ChangePassword.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Data
@NoArgsConstructor
public class PasswordBean {

	@NotBlank(message = "{PasswordBean.passwordOld.NotBlank}")
	private String passwordOld;
	@NotBlank(message = "{PasswordBean.passwordNew.NotBlank}")
	@Length(min = 8,message = "{PasswordBean.passwordNew.Length}")
	private String passwordNew;
	@NotBlank(message = "{PasswordBean.confirmPassword.NotBlank}")
//	@Length(min = 8,message = "{PasswordBean.confirmPassword.Length}")
	private String confirmPassword;
}
