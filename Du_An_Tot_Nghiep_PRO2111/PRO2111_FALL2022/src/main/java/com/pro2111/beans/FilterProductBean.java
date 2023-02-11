package com.pro2111.beans;

import java.math.BigDecimal;
import java.util.List;

import com.pro2111.entities.OptionValue;

import lombok.Data;

@Data
public class FilterProductBean {
	List<OptionValue> optionValues;
	BigDecimal min;
	BigDecimal max;
}
