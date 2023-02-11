/**
 * DATN_FALL2022, 2022
 * SettingService.java, BUI_QUANG_HIEU
 */
package com.pro2111.service;

import java.util.List;

import com.pro2111.beans.AddressBean;
import com.pro2111.beans.BankBean;
import com.pro2111.beans.EmailBean;
import com.pro2111.entities.History;
import com.pro2111.entities.Setting;
import com.pro2111.entities.User;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public interface SettingService {

	/**
	 * Lấy tất cả thông tin Setting
	 * 
	 * @return
	 */
	Setting getAllInforSetting();

	/**
	 * Cập nhật thông tin Setting
	 * 
	 * @param setting
	 * @return
	 */
	Setting updateInforSetting(Setting setting);

	/**
	 * Cập nhật SĐT
	 * 
	 * @param phone
	 * @return
	 */
	Setting updatePhone(String phone, User user);

	/**
	 * Lấy tất cả lịch sử thay đổi Setting
	 * 
	 * @return
	 */
	List<History> getAllHistoryBySetting(Setting setting);

	/**
	 * Lấy Setting theo id
	 * 
	 * @param settingIdDefault
	 * @return
	 */
	Setting findSettingById(int settingIdDefault);

	/**
	 * Cập nhật ngân hàng
	 * 
	 * @param bankBean
	 * @param user
	 * @return
	 */
	Setting updateBank(BankBean bankBean, User user);

	/**
	 * Cập nhật Email
	 * 
	 * @param bean
	 * @param user
	 * @return
	 */
	Setting updateEmail(EmailBean bean, User user);

	/**
	 * Cập nhật địa chỉ
	 * 
	 * @param addressBean
	 * @param user
	 * @return
	 */
	Setting updateAddress(AddressBean addressBean, User user);

	/**
	 * Lấy mã quận/huyện của cửa hàng
	 * 
	 * @return
	 */
	int getDistrictIdStore();

}
