package com.pro2111.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro2111.entities.Cart;
import com.pro2111.entities.CartDetail;
import com.pro2111.entities.ProductVariant;
import com.pro2111.repositories.CartDetailRepository;
import com.pro2111.service.CartDetailService;

@Service
public class CartDetailServiceImpl implements CartDetailService {
	@Autowired
	CartDetailRepository cartDetailRepository;

	/**
	 * Thêm mới một cartDetails
	 */
	@Override
	public CartDetail create(CartDetail cartDetails) {
		// set LocalDateTime cho cartDetails
		cartDetails.setCreatedDate(LocalDateTime.now());
		return cartDetailRepository.save(cartDetails);
	}

	/**
	 * Cập nhật CartDetail
	 */
	@Override
	public synchronized CartDetail update(CartDetail cartDetails) {
		return cartDetailRepository.save(cartDetails);
	}

	/**
	 * Xoá CartDetail
	 */
	@Override
	public synchronized CartDetail delete(CartDetail cartDetails) {
		cartDetailRepository.delete(cartDetails);
		return cartDetails;
	}

	/**
	 * Lấy ra toàn bộ list cartDetail
	 */
	@Override
	public synchronized List<CartDetail> findAll() {
		return cartDetailRepository.findAll();
	}

	/**
	 * Lấy ra CartDetail theo id
	 */
	@Override
	public synchronized CartDetail findById(String id) {
		return cartDetailRepository.findById(id).get();
	}

	/**
	 * Lấy ra một list CartDetail theo cart
	 */
	@Override
	public synchronized List<CartDetail> findByCart(Cart cart) {
		return cartDetailRepository.findByCart(cart);
	}

	/**
	 * Lấy ra một list CartDetail theo productVariant
	 */
	@Override
	public synchronized List<CartDetail> findByProduct(ProductVariant productVariant) {
		return cartDetailRepository.findByProduct(productVariant);
	}

}
