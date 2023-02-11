/**
 * Luvina Software JSC, 2022
 * OptionService.java, Bui Quang Hieu
 */
package com.pro2111.service;

import java.util.List;

import com.pro2111.beans.OptionAndOptionValue;
import com.pro2111.entities.Option;
import com.pro2111.entities.Product;

/**
 * Khai báo các method của tầng service
 * 
 * @author BUI_QUANG_HIEU
 */
public interface OptionService {

	/**
	 * Lấy tất cả Option
	 * 
	 * @param
	 * @return list option
	 */
	public List<Option> findAll();

	/**
	 * Lấy Option theo optionId
	 * 
	 * @param id
	 * @return option
	 */
	public Option findById(Integer id);

	/**
	 * Tạo Option từ OptionAndOptionValue
	 * 
	 * @param option
	 * @return option
	 */
	public Option create(OptionAndOptionValue option);

	/**
	 * Cập nhật Option
	 * 
	 * @param option
	 * @return option
	 */
	public Option update(Option option);

	/**
	 * Lấy list Option chưa tồn tại trong Product
	 * 
	 * @param product
	 * @return list option
	 */
	public List<Option> findOptionNotExistsProduct(Product product);

	/**
	 * Lấy list Option theo status
	 * 
	 * @param i
	 * @return
	 */
	public List<Option> findByStatusLike(int status);

	/**
	 * Lấy Option theo optionName
	 * 
	 * @param value
	 * @return
	 */
	public Option findByName(String name);

	/**
	 * Lấy list Option theo optionName gần đúng
	 * 
	 * @param name
	 * @return
	 */
	public List<Option> findByApproximateName(String name);

	/**
	 * Kiểm tra Option có được phép xóa hay không
	 * 
	 * @param option
	 * @return
	 */
	public Boolean checkDeleteOption(Option option);

	/**
	 * Xóa Option
	 * 
	 * @param option
	 * @return
	 */
	public Option deleteOption(Option option);

	/**
	 * Lấy list Option theo status và isShow
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public List<Option> findByStatusLikeAndIsShowLike(int status, int isShow);

}
