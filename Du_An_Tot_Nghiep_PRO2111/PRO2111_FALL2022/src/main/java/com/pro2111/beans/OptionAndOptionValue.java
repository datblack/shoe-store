/**
 * DATN_FALL2022, 2022
 * OptionAndOptionValue.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.pro2111.entities.Option;
import com.pro2111.entities.OptionValue;

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
public class OptionAndOptionValue {
	@Valid
	@NotNull
	private Option option;
	@Valid
	private List<OptionValue> optionValues;
}
