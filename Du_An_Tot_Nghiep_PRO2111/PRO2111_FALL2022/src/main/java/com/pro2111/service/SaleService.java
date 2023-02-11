package com.pro2111.service;

import java.math.BigDecimal;
import java.util.List;

import com.pro2111.beans.SaleAndProductVariants;
import com.pro2111.beans.SaleAndSaleChild;
import com.pro2111.beans.VariantValueViewSaleBean;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.Sale;
import com.pro2111.entities.User;

public interface SaleService {

	/**
	 * Lấy ra tất cả sale
	 * 
	 * @param
	 * @return List<Sale>
	 */
	List<Sale> findAll();

	/**
	 * Lấy ra SaleAndProductVariants theo idSale
	 * 
	 * @param id
	 * @return Sale + List<ProductVariant>
	 */
	SaleAndProductVariants findSaleAndProductById(String id);

	/**
	 * Lấy ra Sale theo id
	 * 
	 * @param id
	 * @return Sale
	 */
	Sale findSaleById(String id);

	/**
	 * Thêm mới saleAndProduct
	 * 
	 * @param saleAndProduct
	 * @return Sale
	 */
	Sale create(SaleAndProductVariants saleAndProduct);

	/**
	 * Cập nhật saleAndProduct
	 * 
	 * @param saleAndProduct, userEdit
	 * @return SaleAndProductVariants
	 */
	SaleAndProductVariants update(SaleAndProductVariants saleAndProduct, User userEdit);

	/**
	 * Xóa sale
	 * 
	 * @param id
	 * @return
	 */
	void delete(String id);

	/**
	 * 
	 * @param lisProduct
	 * @return List<ProductVariant>
	 */
	List<ProductVariant> findProductVariantsPromotionIsAllowed(List<Product> lisProduct);

	/**
	 * check trùng saleName
	 * 
	 * @param saleNew, saleOld, method
	 * @return Boolean
	 */
	Boolean checkSaleName(Sale saleNew, Sale saleOld, String method);

	/**
	 * Thêm mới saleAndSaleChild
	 * 
	 * @param saleAndSaleChild, userCreate
	 * @return Sale
	 */
	Sale createAndSaleChild(SaleAndSaleChild saleAndSaleChild, User userCreate);

	/**
	 * Cập nhật saleAndSaleChild
	 * 
	 * @param saleAndSaleChild
	 * @return SaleAndSaleChild
	 */
	SaleAndSaleChild updateAndSaleChild(SaleAndSaleChild saleAndSaleChild);

	/**
	 * Lấy ra list saleParent
	 * 
	 * @param
	 * @return List<Sale>
	 */
	List<Sale> findSaleParent();

	/**
	 * Lấy ra SaleAndSaleChild theo id
	 * 
	 * @param id
	 * @return SaleAndSaleChild
	 */
	SaleAndSaleChild findSaleAndSaleChildBySaleParent(String id);

	/**
	 * Xóa saleChild
	 * 
	 * @param saleChild, userEdit
	 * @return Sale
	 */
	Sale deleteSaleChild(Sale saleChild, User userEdit);

	/**
	 * Cập nhật saleChild
	 * 
	 * @param saleChild, userEdit
	 * @return Sale
	 */
	Sale editSaleChild(Sale saleChild, User userEdit);

	/**
	 * Lấy ra list saleParent theo saleParent
	 * 
	 * @param
	 * @return List<Sale>
	 */
	List<Sale> findSaleChileBySaleParent(Sale saleParent);

	/**
	 * Xóa saleParent
	 * 
	 * @param
	 * @return List<Sale>
	 */
	Sale deleteSaleParent(Sale saleParent);

	/**
	 * Thêm mới saleChid
	 * 
	 * @param saleChlid, userEdit
	 * @return Sale
	 */
	Sale createSaleChild(Sale saleChlid, User userEdit);

	/**
	 * Lấy list Sale theo trạng thái
	 * 
	 * @param i
	 * @return List<Sale>
	 */
	List<Sale> findByStatus(int i);

	/**
	 * Cập nhật listSaleUpdate
	 * 
	 * @param listSaleUpdate List<Sale>
	 */
	List<Sale> updateSale(List<Sale> listSaleUpdate);

	/**
	 * Lấy discount Sale theo productVariant
	 * 
	 * @param productVariant
	 * @return BigDecimal
	 */
	BigDecimal findDiscountSaleByProductVariant(ProductVariant productVariant);

	/**
	 * Tìm VariantValueViewSaleBean theo productVariant
	 * 
	 * @param productVariant
	 * @return VariantValueViewSaleBean
	 */
	VariantValueViewSaleBean findVariantValueByProductVariant(ProductVariant productVariant);

	/**
	 * Lấy ra listSaleChild theo trạng thái
	 * 
	 * @param i
	 * @return List<Sale>
	 */
	List<Sale> findSaleChildByStatus(int i);
}
