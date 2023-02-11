/**
 * DATN_FALL2022, 2022
 * Setting.java, BUI_QUANG_HIEU
 */
package com.pro2111.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Entity(name = "Setting")
@Table(name = "settings")
@Data
public class Setting implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "setting_id", unique = true, nullable = false, precision = 10)
	@JsonIgnore
	private int settingId;

	@Column(name = "phone_shop")
	private String phoneShop;

	@Column(name = "email")
	private String email;

	@JsonIgnore
	@Column(name = "password")
	private String password;

	@Column(name = "bank")
	private String bank;

	@Column(name = "division_id")
	private int divisionId;

	@Column(name = "division_name")
	private String divisionName;

	@Column(name = "district_id")
	private int districtId;

	@Column(name = "district_name")
	private String districtName;

	@Column(name = "ward_code")
	private String wardCode;

	@Column(name = "ward_name")
	private String wardName;

	@Column(name = "address_detail")
	private String addressDetail;

	@JsonIgnore
	@OneToMany(mappedBy = "setting")
	private Set<History> historySettings;

	@Column(name = "update_day")
	private LocalDateTime updateDay;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_edit", nullable = false)
	private User userEdit;
}
