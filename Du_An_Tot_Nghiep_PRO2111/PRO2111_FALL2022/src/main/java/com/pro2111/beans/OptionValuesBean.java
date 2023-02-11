package com.pro2111.beans;

import java.util.List;

import javax.validation.Valid;

import com.pro2111.entities.OptionValue;

import lombok.Data;

@Data
public class OptionValuesBean {
	@Valid
	private List<OptionValue> listOptionValues;

}
