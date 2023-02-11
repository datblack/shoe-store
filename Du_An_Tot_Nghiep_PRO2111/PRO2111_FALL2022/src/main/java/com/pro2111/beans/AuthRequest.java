/**
 * DATN_FALL2022, 2022
 * Login.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import javax.validation.constraints.Email;
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
public class AuthRequest {

	@NotBlank(message = "{AuthRequest.username.NotBlank}")
	@Email(message = "{AuthRequest.username.Email}")
	private String username;
	
	@NotBlank(message = "{AuthRequest.password.NotBlank}")
	private String password;
}
