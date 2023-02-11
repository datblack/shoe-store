/**
 * DATN_FALL2022, 2022
 * SettingRestControllerAdmin.java, BUI_QUANG_HIEU
 */
package com.pro2111.restcontrollers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pro2111.beans.AddressBean;
import com.pro2111.beans.BankBean;
import com.pro2111.beans.EmailBean;
import com.pro2111.beans.PhoneSettingBean;
import com.pro2111.beans.SendMail;
import com.pro2111.entities.History;
import com.pro2111.entities.Setting;
import com.pro2111.entities.User;
import com.pro2111.service.SettingService;
import com.pro2111.service.SmtpMailSender;
import com.pro2111.utils.Constant;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("admin/rest/settings")
public class SettingRestControllerAdmin {

	@Autowired
	private SettingService settingService;

	@Autowired
	private SmtpMailSender smtpMailSender;

	@PostMapping("test-email")
	@PreAuthorize("@adminAuthorizer.authorize(authentication)")
	public ResponseEntity<?> testEmail(@Valid @RequestBody SendMail sendMail) {
		try {
			smtpMailSender.sendMail(sendMail.getTo(), sendMail.getSubject(), sendMail.getContent());
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy ra tất cả thông tin chung của cửa hàng
	 * 
	 * @return
	 */
	@GetMapping("get-all-infor-setting")
	@PreAuthorize("@adminAuthorizer.authorize(authentication)")
	public ResponseEntity<Setting> getAllInforSetting() {
		try {
			return ResponseEntity.ok(settingService.getAllInforSetting());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("get-district-id-store")
	public ResponseEntity<JsonNode> getDistrictIdStore() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode node = mapper.createObjectNode();
			node.put("value", settingService.getDistrictIdStore());
			return ResponseEntity.ok(node);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy ra tất cả lịch sửa thay đổi thông tin
	 * 
	 * @return
	 */
	@GetMapping("get-all-history-setting")
	@PreAuthorize("@adminAuthorizer.authorize(authentication)")
	public ResponseEntity<List<History>> getAllHistorySetting() {
		try {
			Setting setting = settingService.findSettingById(Constant.SETTING_ID_DEFAULT);
			return ResponseEntity.ok(settingService.getAllHistoryBySetting(setting));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

//	@PutMapping("update-infor-setting/{userId}")
//	@PreAuthorize("@adminAuthorizer.authorize(authentication)")
//	public ResponseEntity<Setting> updateInforSetting(@Valid @RequestBody Setting setting,
//			@PathVariable("userId") User user) {
//		try {
//			setting.setSettingId(Constant.SETTING_ID_DEFAULT);
//			setting.setUpdateDay(LocalDateTime.now());
//			setting.setUserEdit(user);
//			return ResponseEntity.ok(settingService.updateInforSetting(setting));
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//		}
//	}

	/**
	 * Thay đổi email cửa hàng
	 * 
	 * @param bean
	 * @param user
	 * @return
	 */
	@PostMapping("update-email/{userId}")
	@PreAuthorize("@adminAuthorizer.authorize(authentication)")
	public ResponseEntity<Setting> updateEmail(@Valid @RequestBody EmailBean bean, @PathVariable("userId") User user) {
		try {
			return ResponseEntity.ok(settingService.updateEmail(bean, user));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Thay đổi số điện thoại cửa hàng
	 * 
	 * @param bean
	 * @return
	 */
	@PostMapping("update-phone")
	@PreAuthorize("@adminAuthorizer.authorize(authentication)")
	public ResponseEntity<Setting> updatePhone(@Valid @RequestBody PhoneSettingBean bean) {
		try {
			return ResponseEntity.ok(settingService.updatePhone(bean.getPhone(), bean.getUser()));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Thay đổi thông tin tài khoản ngân hàng của cửa hàng
	 * 
	 * @param bankBean
	 * @param user
	 * @return
	 */
	@PostMapping("update-bank/{userId}")
	@PreAuthorize("@adminAuthorizer.authorize(authentication)")
	public ResponseEntity<Setting> updateBank(@Valid @RequestBody BankBean bankBean,
			@PathVariable("userId") User user) {
		try {
			return ResponseEntity.ok(settingService.updateBank(bankBean, user));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Thay đổi địa chỉ của cửa hàng
	 * 
	 * @param addressBean
	 * @param user
	 * @return
	 */
	@PostMapping("update-address/{userId}")
	@PreAuthorize("@adminAuthorizer.authorize(authentication)")
	public ResponseEntity<Setting> updateAddress(@Valid @RequestBody AddressBean addressBean,
			@PathVariable("userId") User user) {
		try {
			return ResponseEntity.ok(settingService.updateAddress(addressBean, user));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}
