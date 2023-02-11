/**
 * Luvina Software JSC, 2022
 * OptionValueService.java, Bui Quang Hieu
 */
package com.pro2111.service;

import java.util.List;

import com.pro2111.entities.Option;
import com.pro2111.entities.OptionValue;
import com.pro2111.entities.Product;

/**
 * @author BUI_QUANG_HIEU
 */
public interface OptionValueService {

	/**
	 * Lấy tất cả OptionValue
	 * 
	 * @param
	 * @return list optionValue
	 */
	public List<OptionValue> findAll();

	/**
	 * Lấy OptionValue theo valueId
	 * 
	 * @param id
	 * @return optionValue
	 */
	public OptionValue findById(Integer id);

	/**
	 * Tạo mới OptionValue
	 * 
	 * @param optionValue
	 * @return optionValue
	 */
	public OptionValue create(OptionValue optionValue);

	/**
	 * Cập nhật OptionValue
	 * 
	 * @param optionValue
	 * @return optionValue
	 */
	public OptionValue update(OptionValue optionValue);

	/**
	 * Lấy OptionValue theo valueName
	 * 
	 * @param value
	 * @return
	 */
	public OptionValue findByName(String valueName);

	/**
	 * Lấy list OptionValue theo Option
	 * 
	 * @param option
	 * @return
	 */
	public List<OptionValue> findByOption(Option option);

	/**
	 * Lấy list OptionValue có trạng thái true và theo Option
	 * 
	 * @param option
	 * @return
	 */
	public List<OptionValue> findOptionValueTrueByOption(Option option);

	/**
	 * Lấy OptionValue chưa tồn tại trong VariantValue theo Product và Option
	 * 
	 * @param product
	 * @param option
	 * @return
	 */
	public List<OptionValue> findNotExistsVariantValue(Product product, Option option);

	/**
	 * Lấy list OptionValue theo valueName gần đúng
	 * 
	 * @param name
	 * @return
	 */
	public List<OptionValue> findByApproximateName(String name);

	/**
	 * Search OptionValue theo key
	 * 
	 * @param input
	 * @return
	 */
	public List<OptionValue> fullTextSearch(String input);

	/**
	 * Kiểm tra OptionValue có được phép xóa hay không
	 * 
	 * @param optionValue
	 * @return
	 */
	public Boolean checkDeleteOptionValue(OptionValue optionValue);

	/**
	 * Xóa OptionValue
	 * 
	 * @param optionValue
	 * @return
	 */
	public OptionValue deleteOptionValue(OptionValue optionValue);

	/**
	 * Tạo list OptionValue
	 * 
	 * @param optionValues
	 * @return
	 */
	public List<OptionValue> createListOptionValue(List<OptionValue> optionValues);

	/**
	 * Lấy list OptionValue theo Option, status, isShow
	 * 
	 * @param option
	 * @param i
	 * @param j
	 * @return
	 */
	public List<OptionValue> findByOptionsLikeAndStatusLikeAndIsShowLike(Option option, int status, int isShow);
}
