/**
 * DATN_FALL2022, 2022
 * TopCustomerBean.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

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
@Entity
public class TopCustomerBean {
	@Id
	private User user;
	private BigDecimal totalMoney;
}
