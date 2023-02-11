package com.pro2111.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro2111.entities.Cart;
import com.pro2111.entities.User;
import com.pro2111.repositories.CartRepository;
import com.pro2111.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	CartRepository cartRepository;

	/**
	 * lấy ra toàn bộ list Cart
	 */
	@Override
	public synchronized List<Cart> findAll() {
		return cartRepository.findAll();
	}

	/**
	 * lấy ra một đối tượng Cart theo id
	 */
	@Override
	public synchronized Cart findById(Integer id) {
		return cartRepository.findById(id).get();
	}

	/**
	 * Thêm mới một cart
	 */
	@Override
	public synchronized Cart create(Cart cart) {
		return cartRepository.save(cart);
	}

	/**
	 * Cập nhật cart
	 */
	@Override
	public synchronized Cart update(Cart cart) {
		return cartRepository.save(cart);
	}

	/**
	 * Xoá cart
	 */
	@Override
	public synchronized Cart delete(Cart cart) {
		cartRepository.delete(cart);
		return cart;
	}

	/**
	 * Lấy ra một list cart theo user
	 * 
	 */
	@Override
	public synchronized List<Cart> findByUser(User user) {
		return cartRepository.findByUser(user);
	}

}
