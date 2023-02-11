/**
 * Luvina Software JSC, 2022
 * ProductServiceIml.java, Bui Quang Hieu
 */
package com.pro2111.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro2111.entities.Option;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductOption;
import com.pro2111.repositories.ProductOptionRepository;
import com.pro2111.service.ProductOptionService;

/**
 * @author BUI_QUANG_HIEU
 */

@Service
public class ProductOptionServiceImpl implements ProductOptionService {

	@Autowired
	private ProductOptionRepository productOptionRepository;

	/**
	 * Lấy tất cả ProductOption
	 */
	@Override
	public synchronized List<ProductOption> findAll() {
		return productOptionRepository.findAll();
	}

	/**
	 * Lấy list ProductOption theo Product
	 */
	@Override
	public synchronized List<ProductOption> findByProduct(Product product) {
		return productOptionRepository.findByProductsLike(product);
	}

	/**
	 * Tạo mới ProductOption
	 */
	@Override
	public synchronized void create(Product product, Option option, Integer status) {
		productOptionRepository.insertPO(product, option, status);
	}

	/**
	 * Cập nhật ProductOption
	 */
	@Override
	public synchronized void update(Product product, Option option, Option optionOld, Integer status) {
		productOptionRepository.updatePO(product, option, optionOld, status);
	}

	/**
	 * Cập nhật status ProductOption
	 */
	@Override
	public synchronized void updateStatus(Product products, Option options, Integer status) {
		productOptionRepository.updateStatusPO(products, options, status);
	}

	/**
	 * Lấy list ProductOption theo Product, có trạng thái true
	 */
	@Override
	public synchronized List<ProductOption> findByProductAndStatusTrue(Product product) {
		return productOptionRepository.findByProductsLikeAndStatusLike(product, 1);
	}

	/**
	 * Xóa ProductOption
	 */
	@Override
	public ProductOption delete(ProductOption productOption) {
		productOptionRepository.deletePO(productOption.getProducts(), productOption.getOptions());
		return productOption;
	}

}
