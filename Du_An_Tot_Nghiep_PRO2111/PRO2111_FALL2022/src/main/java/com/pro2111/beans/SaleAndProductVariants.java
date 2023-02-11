package com.pro2111.beans;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.Sale;

import lombok.Data;

@Data
public class SaleAndProductVariants {
	@NotNull
	private Sale sale;
	
//	@NotEmpty
	private List<ProductVariant> listProductVariants;
}
