/**
 * DATN_FALL2022, 2022
 * PhoneSettingBean.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.pro2111.entities.User;

import lombok.Data;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Data
public class PhoneSettingBean {
	@NotBlank(message = "{Setting.phoneShop.NotBlank}")
	@Length(max = 100, message = "{Setting.phoneShop.Length}")
	private String phone;

	@NotNull
	private User user;
}
