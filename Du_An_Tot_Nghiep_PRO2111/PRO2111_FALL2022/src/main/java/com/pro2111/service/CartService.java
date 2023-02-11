package com.pro2111.service;

import java.util.List;

import com.pro2111.entities.Cart;
import com.pro2111.entities.User;

public interface CartService {
	/**
	 * Lấy ra Cart theo User
	 * 
	 * @param user
	 * @return
	 */
	public List<Cart> findByUser(User user);

	/**
	 * Lấy ra toàn bộ Cart
	 * 
	 * @return
	 */
	public List<Cart> findAll();

	/**
	 * Thêm mới Cart
	 * 
	 * @param cart
	 * @return
	 */
	public Cart create(Cart cart);

	/**
	 * Lấy ra Cart theo id
	 * 
	 * @param id
	 * @return Cart
	 */
	public Cart findById(Integer id);

	/**
	 * Cập nhât Cart
	 * 
	 * @param cart
	 * @return cart
	 */
	public Cart update(Cart cart);

	/**
	 * Xoá Cart
	 * 
	 * @param cart
	 * @return
	 */
	public Cart delete(Cart cart);

}
