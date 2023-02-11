/**
 * DATN_FALL2022, 2022
 * HistorySetting.java, BUI_QUANG_HIEU
 */
package com.pro2111.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Entity
@Table(name = "history")
@Data
public class History implements Serializable {

	private static final long serialVersionUID = 1L;
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "history_id", unique = true, nullable = false, precision = 19)
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "history_id", unique = true)
	private String historyId;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "description", nullable = false, length = 1000)
	private String description;

	@ManyToOne(optional = false)
	@JoinColumn(name = "setting_id", nullable = false)
	private Setting setting;

}
