/**
 * Luvina Software JSC, 2022
 * OptionServiceIml.java, Bui Quang Hieu
 */
package com.pro2111.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro2111.beans.OptionAndOptionValue;
import com.pro2111.entities.Option;
import com.pro2111.entities.OptionValue;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductOption;
import com.pro2111.repositories.OptionRepository;
import com.pro2111.repositories.OptionValueRepository;
import com.pro2111.repositories.ProductOptionRepository;
import com.pro2111.service.OptionService;

/**
 * Thực thi và xử lý logic các method của OptionService
 * 
 * @author BUI_QUANG_HIEU
 */
@Service
public class OptionServiceImpl implements OptionService {

	@Autowired
	private OptionRepository optionRepository;

	@Autowired
	private ProductOptionRepository productOptionRepository;

	@Autowired
	private OptionValueRepository optionValueRepository;

	/**
	 * Lấy tất cả Option
	 */
	@Override
	public synchronized List<Option> findAll() {
		return optionRepository.findAll();
	}

	/**
	 * Lấy Option theo optionId
	 */
	@Override
	public synchronized Option findById(Integer id) {
		return optionRepository.findById(id).get();
	}

	/**
	 * Tạo mới Option từ OptionAndOptionValue
	 */
	@Override
	@Transactional
	public synchronized Option create(OptionAndOptionValue optionAndOptionValue) {
		Option option = optionRepository.save(optionAndOptionValue.getOption());
		optionAndOptionValue.getOptionValues().forEach(ov -> {
			ov.setOptions(option);
			optionValueRepository.save(ov);
		});
		return option;
	}

	/**
	 * Cập nhật Option
	 */
	@Override
	public synchronized Option update(Option option) {
		return optionRepository.save(option);
	}

	/*
	 * Lấy list Option chưa tồn tại trong Product
	 */
	@Override
	public synchronized List<Option> findOptionNotExistsProduct(Product product) {
		return optionRepository.findOptionNotExistsProductOption(product);
	}

	/**
	 * Lấy list Option theo status
	 */
	@Override
	public synchronized List<Option> findByStatusLike(int status) {
		return optionRepository.findByStatusLike(status);
	}

	/**
	 * Lấy Option theo optionName
	 */
	@Override
	public synchronized Option findByName(String name) {
		return optionRepository.findByOptionNameLike(name);
	}

	/**
	 * Lấy list Option theo optionName gần đúng
	 */
	@Override
	public synchronized List<Option> findByApproximateName(String name) {
		return optionRepository.findByApproximateName(name);
	}

	/**
	 * Kiểm tra Option có được phép xóa hay không
	 */
	@Override
	public Boolean checkDeleteOption(Option option) {
		List<ProductOption> values = productOptionRepository.findByOptionsLike(option);
		if (values.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Xóa Option
	 */
	@Override
	@Transactional
	public Option deleteOption(Option option) {
		List<OptionValue> optionValues = optionValueRepository.findByOptionsLike(option);
		optionValues.forEach(ov -> {
			optionValueRepository.delete(ov);
		});

		List<ProductOption> productOptions = productOptionRepository.findByOptionsLike(option);
		productOptions.forEach(po -> {
			productOptionRepository.delete(po);
		});
		optionRepository.delete(option);
		return option;
	}

	/**
	 * Lấy list Option theo status, isShow
	 */
	@Override
	public List<Option> findByStatusLikeAndIsShowLike(int status, int isShow) {
		return optionRepository.findByStatusLikeAndIsShowLike(status, isShow);
	}

}
