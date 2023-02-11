/**
 * Luvina Software JSC, 2022
 * VariantValueServiceIml.java, Bui Quang Hieu
 */
package com.pro2111.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pro2111.entities.Option;
import com.pro2111.entities.OptionValue;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductOption;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.VariantValue;
import com.pro2111.repositories.ProductOptionRepository;
import com.pro2111.repositories.ProductVariantRepository;
import com.pro2111.repositories.VariantValueRepository;
import com.pro2111.service.VariantValueService;
import com.pro2111.utils.Common;

/**
 * @author BUI_QUANG_HIEU
 */

@Service
public class VariantValueServiceImpl implements VariantValueService {
	@Autowired
	VariantValueRepository variantValueRepository;

	@Autowired
	ProductOptionRepository productOptionRepository;

	@Autowired
	ProductVariantRepository productVariantRepository;

	Integer _count = 0;
	Integer _countExsits = 0;
	Boolean _checkCondition = true;

	private List<String> optionNames;
	private List<String> optionValueNames;

	/**
	 * Lấy tất cả VariantValue
	 */
	@Override
	public synchronized List<VariantValue> findAll() {
		return variantValueRepository.findAll();
	}

	/**
	 * Lấy list VariantValue theo ProductOption
	 */
	@Override
	public synchronized List<VariantValue> findByProductOption(ProductOption productOption) {
		return variantValueRepository.findByProductOption(productOption.getProducts(), productOption.getOptions());
	}

	/**
	 * Lấy VariantValue theo Product, Option
	 */
	@Override
	public synchronized ProductOption findByProductsLikeAndOptionsLike(Product product, Option option) {
		return productOptionRepository.findByProductsLikeAndOptionsLike(product, option);
	}

	/**
	 * Lấy list VariantValue theo ProductVariant
	 */
	@Override
	public synchronized List<VariantValue> findByProductVariant(ProductVariant productVariant) {
		List<VariantValue> list = variantValueRepository.findByProductVariantsLike(productVariant);
		for (int i = list.size() - 1; i >= 0; i--) {
			VariantValue value = list.get(i);
			if (value.getOptionValues().getIsShow() == 0 && value.getProductOptions().getOptions().getIsShow() == 0) {
				list.remove(i);
			}
		}
		return list;
	}

	/**
	 * Lấy list VariantValue theo ProductVariant, status
	 */
	@Override
	public synchronized List<VariantValue> findByProductVariantsLikeAndStatusLike(ProductVariant productVariant,
			Integer status) {
		return variantValueRepository.findByProductVariantsLikeAndStatusLike(productVariant, status);
	}

	/**
	 * Tạo mới VariantValue
	 */
	@Override
	public synchronized void createVariantValue(ProductVariant productVariants, Product products, Option options,
			OptionValue optionValues) {
		variantValueRepository.createVariantValue(productVariants, products, options, optionValues);
	}

	/**
	 * Lấy list VariantValue theo Product
	 */
	@Override
	public synchronized List<VariantValue> findByProduct(Product product) {
		return variantValueRepository.findByProduct(product);
	}

	/**
	 * Lấy list VariantValue theo ProductVariant
	 */
	@Override
	public List<VariantValue> findByProductVariantOrigin(ProductVariant productVariant) {
		return variantValueRepository.findByProductVariantsLike(productVariant);
	}

	/**
	 * Tạo mới VariantValue
	 */
	@Override
	@Transactional
	public List<VariantValue> create(List<VariantValue> variantValues) {
		ProductVariant pv = productVariantRepository.findById(variantValues.get(0).getProductVariants().getVariantId())
				.get();
		optionNames = new ArrayList<>();
		optionValueNames = new ArrayList<>();
		variantValues.forEach(value -> {
			optionNames.add(value.getProductOptions().getOptions().getOptionName());
			optionValueNames.add(value.getOptionValues().getValueName());
		});
		pv.setSkuId(Common.genarateSkuID(optionNames, optionValueNames));
		productVariantRepository.save(pv);
		variantValueRepository.deleteAll(pv);
		for (int i = 0; i < variantValues.size(); i++) {
			VariantValue value = variantValues.get(i);
			variantValueRepository.createVariantValue(pv, value.getProductOptions().getProducts(),
					value.getProductOptions().getOptions(), value.getOptionValues());
		}
		return variantValues;
	}

}
