/**
 * DATN_FALL2022, 2022
 * AddressBean.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.pro2111.entities.User;

import lombok.Data;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Data
public class AddressBean {
	@NotNull
	private int divisionId;

	@NotBlank(message = "{AddressBean.divisionName.NotBlank}")
	private String divisionName;

	@NotNull
	private int districtId;

	@NotBlank(message = "{AddressBean.districtName.NotBlank}")
	private String districtName;

	@NotBlank
	private String wardCode;

	@NotBlank(message = "{AddressBean.wardName.NotBlank}")
	private String wardName;

	private String addressDetail;
	
	public AddressBean(User u) {
		this.divisionId = u.getDivisionId();
		this.divisionName = u.getDivisionName();
		this.districtId = u.getDistrictId();
		this.districtName = u.getDistrictName();
		this.wardCode = u.getWardCode();
		this.wardName = u.getWardName();
		this.addressDetail = u.getAddress();		
	}

	public AddressBean() {
		super();
	}
	

}
