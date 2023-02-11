package com.pro2111.service;

import java.util.List;

import com.pro2111.entities.Cart;
import com.pro2111.entities.CartDetail;
import com.pro2111.entities.ProductVariant;

public interface CartDetailService {
	/**
	 * Thêm mới CartDetail
	 * 
	 * @param cartDetails
	 * @return
	 */
	public CartDetail create(CartDetail cartDetails);

	/**
	 * Cập nhất CartDetail
	 * 
	 * @param cartDetails
	 * @return cartDetails
	 */
	public CartDetail update(CartDetail cartDetails);

	/**
	 * Xoá CartDetail
	 * 
	 * @param cartDetails
	 * @return
	 */

	public CartDetail delete(CartDetail cartDetails);

	/**
	 * Lấy tất cả CartDetail
	 * 
	 * @return
	 */
	public List<CartDetail> findAll();

	/**
	 * Lấy ra 1 CartDetail theo id
	 * 
	 * @param id
	 * @return cartDetails
	 */

	public CartDetail findById(String id);

	/**
	 * Lấy ra 1 CartDetail theo Cart
	 * 
	 * @param cart
	 * @return CartDetails
	 */
	public List<CartDetail> findByCart(Cart cart);

	/**
	 * Lấy ra 1 CartDetail theo ProductVariant
	 * 
	 * @param productVariant
	 * @return
	 */
	public List<CartDetail> findByProduct(ProductVariant productVariant);

}
