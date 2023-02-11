package com.pro2111.beans;

import java.util.List;

import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.Sale;

import lombok.Data;
@Data
public class SaleAndSaleChild {
	Sale saleParent;
	List<Sale> lstSaleChild;
	List<ProductVariant> listProductVariants;
}
