/**
 * Luvina Software JSC, 2022
 * ProductServiceIml.java, Bui Quang Hieu
 */
package com.pro2111.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro2111.beans.FilterProductBean;
import com.pro2111.beans.ProductAndProductOptionBean;
import com.pro2111.entities.Product;
import com.pro2111.repositories.ProductOptionRepository;
import com.pro2111.repositories.ProductRepository;
import com.pro2111.service.ProductService;
import com.pro2111.utils.Constant;

/**
 * @author BUI_QUANG_HIEU
 */

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductOptionRepository productOptionRepository;

	/**
	 * Lấy tất cả Product
	 */
	@Override
	public synchronized List<Product> findAll() {
		return productRepository.findAll();
	}

	/**
	 * Tạo mới Product
	 */
	@Override
	@Transactional
	public synchronized Product create(ProductAndProductOptionBean productAndProductOptionBean) {
		productAndProductOptionBean.getProduct().setStatus(Constant.STATUS_TRUE_PRODUCT);
		Product product = productRepository.save(productAndProductOptionBean.getProduct());
		productAndProductOptionBean.getProductOptions().forEach(po -> {
			productOptionRepository.insertPO(product, po.getOptions(), Constant.STATUS_TRUE_PRODUCT_OPTION);
		});
		return product;
	}

	/**
	 * Lấy Product theo productId
	 */
	@Override
	public synchronized Product findById(Integer id) {
		return productRepository.findById(id).get();
	}

	/**
	 * Cập nhật Product
	 */
	@Override
	public synchronized Product update(Product product) {
		return productRepository.save(product);
	}

	/**
	 * Lấy Product theo productName
	 */
	@Override
	public synchronized Product findByName(String name) {
		// TODO Auto-generated method stub
		return productRepository.findByProductNameLike(name);
	}

	/**
	 * Lấy list Product theo trạng thái
	 */
	@Override
	public synchronized List<Product> findByStatusLike(int status) {
		return productRepository.findByStatusLike(status);
	}

	/**
	 * Lấy list Product theo productName gần đúng
	 */
	@Override
	public synchronized List<Product> findByApproximateName(String name) {
		return productRepository.findByApproximateName(name);
	}

	/*
	 * Xóa Product
	 */
	@Override
	public synchronized Product delete(Product product) {
		productOptionRepository.deletePOByProduct(product);
		productRepository.delete(product);
		return product;
	}

	/**
	 * Tìm kiếm theo khoảng giá và option
	 */
	@Override
	public List<Product> findByRangePrice(FilterProductBean filterProductBean) {
		if (filterProductBean.getOptionValues().size() == 0) {
			return productRepository.findByRangePrice(filterProductBean.getMin(), filterProductBean.getMax());
		}
		return productRepository.findByRangePriceAndOptionValues(filterProductBean.getOptionValues(),
				filterProductBean.getMin(), filterProductBean.getMax());
	}

}
