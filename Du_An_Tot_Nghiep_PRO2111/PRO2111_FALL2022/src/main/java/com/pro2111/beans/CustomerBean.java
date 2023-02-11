/**
 * DATN_FALL2022, 2022
 * CustomerBean.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.pro2111.validations.users.UniqueUserEmail;

import lombok.Data;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Data
public class CustomerBean {
	@NotBlank(message = "{User.fullName.NotBlank}")
	@Length(max = 100, message = "{User.fullName.Length}")
	private String fullname;

	@NotBlank(message = "{User.email.NotBlank}")
	@Length(max = 255, message = "{User.email.Length}")
	@Email(message = "{User.email.Email}")
	@UniqueUserEmail(message = "{User.email.UniqueUserEmail}")
	private String email;

	@NotBlank(message = "{User.phone.NotBlank}")
	@Length(min = 10, max = 20, message = "{User.phone.Length}")
	private String phone;

	@NotNull(message = "{User.sex.NotNull}")
	@Min(value = 0)
	@Max(value = 1)
	private int sex;

}
