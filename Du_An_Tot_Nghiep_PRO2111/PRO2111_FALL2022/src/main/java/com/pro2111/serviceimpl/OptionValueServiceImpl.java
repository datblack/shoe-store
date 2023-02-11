/**
 * Luvina Software JSC, 2022
 * OptionValueServiceIml.java, Bui Quang Hieu
 */
package com.pro2111.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro2111.entities.Option;
import com.pro2111.entities.OptionValue;
import com.pro2111.entities.Product;
import com.pro2111.repositories.OptionValueRepository;
import com.pro2111.service.OptionValueService;

/**
 * @author BUI_QUANG_HIEU
 */
@Service
public class OptionValueServiceImpl implements OptionValueService {
	@Autowired
	private OptionValueRepository optionValueRepository;

	/**
	 * Lấy list OptionValue
	 */
	@Override
	public synchronized List<OptionValue> findAll() {
		return optionValueRepository.findAll();
	}

	/**
	 * Lấy OptionValue theo valueId
	 */
	@Override
	public synchronized OptionValue findById(Integer id) {
		return optionValueRepository.findById(id).get();
	}

	/**
	 * Tạo mới OptionValue
	 */
	@Override
	public synchronized OptionValue create(OptionValue optionValue) {
		return optionValueRepository.save(optionValue);
	}

	/**
	 * Cập nhật OptionValue
	 */
	@Override
	public synchronized OptionValue update(OptionValue optionValue) {
		return optionValueRepository.save(optionValue);
	}

	/**
	 * Lấy OptionValue theo valueName
	 */
	@Override
	public synchronized OptionValue findByName(String valueName) {
		return optionValueRepository.findByValueNameLike(valueName);
	}

	/**
	 * Lấy list OptionValue theo Option
	 */
	@Override
	public synchronized List<OptionValue> findByOption(Option option) {
		return optionValueRepository.findByOptionsLike(option);
	}

	/**
	 * Lấy list OptionValue có trạng thái true và theo Option
	 */
	@Override
	public synchronized List<OptionValue> findOptionValueTrueByOption(Option option) {
		return optionValueRepository.findByStatusLikeAndOptionsLike(1, option);
	}

	/**
	 * Lấy list OptionValue không tồn tại trong VariantValue theo Product, Option
	 */
	@Override
	public synchronized List<OptionValue> findNotExistsVariantValue(Product product, Option option) {
		return optionValueRepository.findNotExistsVariantValue(product, option);
	}

	/**
	 * Lấy list OptionValue theo valueName gần đúng
	 */
	@Override
	public synchronized List<OptionValue> findByApproximateName(String name) {
		return optionValueRepository.findByApproximateName(name);
	}

	/**
	 * Search OptionValue
	 */
	@Override
	public synchronized List<OptionValue> fullTextSearch(String input) {
		return optionValueRepository.fullTextSearch(input);
	}

	/**
	 * Kiểm tra OptionValue có được phéo xóa hay không
	 */
	@Override
	public Boolean checkDeleteOptionValue(OptionValue optionValue) {
		if (optionValue.getVariantValues().size() > 0) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * Xóa OptionValue
	 */
	@Override
	public OptionValue deleteOptionValue(OptionValue optionValue) {
		optionValueRepository.delete(optionValue);
		return optionValue;
	}

	/**
	 * Tạo list OptionValue
	 */
	@Override
	public List<OptionValue> createListOptionValue(List<OptionValue> optionValues) {
		return optionValueRepository.saveAll(optionValues);
	}

	/**
	 * Lấy list OptionValue theo Option, status, isShow
	 */
	@Override
	public List<OptionValue> findByOptionsLikeAndStatusLikeAndIsShowLike(Option option, int status, int isShow) {
		return optionValueRepository.findByOptionsLikeAndStatusLikeAndIsShowLike(option, status, isShow);
	}

}
