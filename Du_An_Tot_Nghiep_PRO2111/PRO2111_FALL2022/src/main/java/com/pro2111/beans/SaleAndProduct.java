package com.pro2111.beans;





import java.util.List;

import com.pro2111.entities.Product;

import com.pro2111.entities.Sale;

import lombok.Data;

@Data
public class SaleAndProduct {
	private Sale sale;
	private List<Product> listProduct;
}
