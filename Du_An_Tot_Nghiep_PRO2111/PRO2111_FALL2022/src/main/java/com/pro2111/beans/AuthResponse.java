/**
 * DATN_FALL2022, 2022
 * AuthResponse.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import com.pro2111.entities.User;

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
public class AuthResponse {
	
	private User user;
	private String accessToken;

}
