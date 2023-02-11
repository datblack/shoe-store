/**
 * DATN_FALL2022, 2022
 * SettingServiceImpl.java, BUI_QUANG_HIEU
 */
package com.pro2111.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.pro2111.beans.AddressBean;
import com.pro2111.beans.BankBean;
import com.pro2111.beans.EmailBean;
import com.pro2111.entities.History;
import com.pro2111.entities.Setting;
import com.pro2111.entities.User;
import com.pro2111.repositories.HistoryRepository;
import com.pro2111.repositories.SettingRepository;
import com.pro2111.service.SettingService;
import com.pro2111.utils.Constant;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Service
public class SettingServiceImpl implements SettingService {
	@Autowired
	private SettingRepository settingRepository;

	@Autowired
	private HistoryRepository historyRepository;

	@Override
	public Setting getAllInforSetting() {
		return settingRepository.findById(Constant.SETTING_ID_DEFAULT).get();
	}

	@Override
	public Setting updateInforSetting(Setting setting) {
		return settingRepository.save(setting);
	}

	@Override
	@Transactional
	public Setting updatePhone(String phone, User user) {
		Setting setting = settingRepository.findById(Constant.SETTING_ID_DEFAULT).get();
		String phoneOld = setting.getPhoneShop();
		setting.setPhoneShop(phone);
		setting.setUserEdit(user);
		setting.setUpdateDay(LocalDateTime.now());
		Setting settingNew = settingRepository.save(setting);

		if (!phoneOld.equals(phone)) {
			StringBuffer note = new StringBuffer();
			note.append("Cập nhật số điện thoại từ ");
			note.append(phoneOld);
			note.append(" -> ");
			note.append(phone);
			History history = new History();
			history.setDescription(note.toString());
			history.setSetting(settingNew);
			history.setCreatedDate(LocalDateTime.now());
			historyRepository.save(history);
		}

		return settingNew;
	}

	@Override
	public List<History> getAllHistoryBySetting(Setting setting) {
		return historyRepository.getAllHistoryBySetting(setting);
	}

	@Override
	public Setting findSettingById(int settingIdDefault) {
		return settingRepository.findById(settingIdDefault).get();
	}

	@Override
	@Transactional
	public Setting updateBank(BankBean bankBean, User user) {
		Gson gson = new Gson();
		Setting setting = settingRepository.findById(Constant.SETTING_ID_DEFAULT).get();
		BankBean bankOld = gson.fromJson(setting.getBank(), BankBean.class);
		setting.setBank(gson.toJson(bankBean));
		setting.setUserEdit(user);
		setting.setUpdateDay(LocalDateTime.now());
		Setting settingNew = settingRepository.save(setting);

		if (!bankOld.getAccountHolder().equals(bankBean.getAccountHolder())
				|| !bankOld.getAccountNumber().equals(bankBean.getAccountNumber())
				|| !bankOld.getBankName().equals(bankBean.getBankName())) {
			StringBuffer note = new StringBuffer();
			if (!bankOld.getAccountHolder().equals(bankBean.getAccountHolder())) {
				note.append("Cập nhật chủ tài khoản từ ");
				note.append(bankOld.getAccountHolder());
				note.append(" -> ");
				note.append(bankBean.getAccountHolder());
				note.append(" ");
			}
			if (!bankOld.getBankName().equals(bankBean.getBankName())) {
				note.append("Cập nhật ngân hàng từ ");
				note.append(bankOld.getBankName());
				note.append(" -> ");
				note.append(bankBean.getBankName());
				note.append(" ");
			}
			if (!bankOld.getAccountNumber().equals(bankBean.getAccountNumber())) {
				note.append("Cập nhật số tài khoản từ ");
				note.append(bankOld.getAccountNumber());
				note.append(" -> ");
				note.append(bankBean.getAccountNumber());
				note.append(" ");
			}
			History history = new History();
			history.setDescription(note.toString());
			history.setSetting(settingNew);
			history.setCreatedDate(LocalDateTime.now());
			historyRepository.save(history);
		}
		return settingNew;
	}

	@Override
	public Setting updateEmail(EmailBean bean, User user) {
		Setting setting = settingRepository.findById(Constant.SETTING_ID_DEFAULT).get();
		String emailOld = setting.getEmail();
		setting.setEmail(bean.getEmail());
		setting.setPassword(bean.getPassword());
		setting.setUserEdit(user);
		setting.setUpdateDay(LocalDateTime.now());
		settingRepository.save(setting);
		if (!emailOld.equals(bean.getEmail())) {
			StringBuffer note = new StringBuffer();
			note.append("Cập nhật email từ ");
			note.append(emailOld);
			note.append(" -> ");
			note.append(setting.getEmail());

			History history = new History();
			history.setDescription(note.toString());
			history.setSetting(setting);
			history.setCreatedDate(LocalDateTime.now());
			historyRepository.save(history);
		}
		return setting;
	}

	@Override
	public Setting updateAddress(AddressBean addressBean, User user) {
		Setting setting = settingRepository.findById(Constant.SETTING_ID_DEFAULT).get();
		AddressBean addressOld = new AddressBean();
		BeanUtils.copyProperties(setting, addressOld);

		BeanUtils.copyProperties(addressBean, setting);
		setting.setUserEdit(user);
		setting.setUpdateDay(LocalDateTime.now());
		settingRepository.save(setting);

		if (!addressOld.equals(addressBean)) {
			StringBuffer note = new StringBuffer();
			if (!addressOld.getDivisionName().equals(addressBean.getDivisionName())) {
				note.append("Cập nhật Thành phố/Tỉnh từ: ");
				note.append(addressOld.getDivisionName());
				note.append(" -> ");
				note.append(addressBean.getDivisionName());
				note.append(" ");
			}

			if (!addressOld.getDistrictName().equals(addressBean.getDistrictName())) {
				note.append("Cập nhật Quận/Huyện từ: ");
				note.append(addressOld.getDistrictName());
				note.append(" -> ");
				note.append(addressBean.getDistrictName());
				note.append(" ");
			}

			if (!addressOld.getWardName().equals(addressBean.getWardName())) {
				note.append("Cập nhật Phường/Xã từ: ");
				note.append(addressOld.getWardName());
				note.append(" -> ");
				note.append(addressBean.getWardName());
				note.append(" ");
			}

			if (!addressOld.getAddressDetail().equals(addressBean.getAddressDetail())) {
				note.append("Cập nhật Địa chỉ chi tiết từ: ");
				note.append(addressOld.getAddressDetail());
				note.append(" -> ");
				note.append(addressBean.getAddressDetail());
			}

			History history = new History();
			history.setDescription(note.toString());
			history.setSetting(setting);
			history.setCreatedDate(LocalDateTime.now());
			historyRepository.save(history);
		}
		return setting;
	}

	@Override
	public int getDistrictIdStore() {
		Setting setting = settingRepository.findById(Constant.SETTING_ID_DEFAULT).get();
		return setting.getDistrictId();
	}

}
