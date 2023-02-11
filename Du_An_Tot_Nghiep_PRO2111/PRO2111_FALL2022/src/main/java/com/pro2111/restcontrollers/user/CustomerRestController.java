package com.pro2111.restcontrollers.user;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pro2111.beans.AddressBean;
import com.pro2111.beans.OTP;
import com.pro2111.beans.ResponseObject;
import com.pro2111.entities.User;
import com.pro2111.i18n.ExposedResourceBundleMessageSource;
import com.pro2111.service.SmtpMailSender;
import com.pro2111.service.UserService;
import com.pro2111.utils.Common;
import com.pro2111.utils.Constant;
import com.pro2111.utils.EncryptUtil;

@CrossOrigin("*")
@RestController
@RequestMapping("user/rest/customer")
public class CustomerRestController {
	@Autowired
	private UserService userService;
	@Autowired
	private SmtpMailSender smtpMailSender;
	@Autowired
	ExposedResourceBundleMessageSource bundleMessageSource;
	static User userStatic;
	static AddressBean addressBeanStatic;

	public Map<String, String> getListError(Locale locale) {
		Map<String, String> map = bundleMessageSource.getValueContaisKey(Constant.PATH_PROPERTIES_I18N, locale, "User");
		return map;
	}

	/**
	 * Thêm mới khách hàng
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping
	public ResponseEntity<?> createCustomer(@Valid @RequestBody User user) {
		try {
			String pwd = EncryptUtil.encrypt(user.getPassword());
			Random random = new Random();
			int otp = random.nextInt(900000) + 100000;
			user.setOtp(otp);
			user.setStatus(2);
			user.setCreatedDate(LocalDateTime.now());
			user.setPassword(pwd);
			user.setRole(1);
			smtpMailSender.sendMail(user.getEmail(), "[XÁC THỰC TÀI KHOẢN ĐĂNG KÝ]", "Mã OTP của bạn là:" + otp);
			return ResponseEntity.ok(userService.create(user));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Kiểm tra Email
	 * 
	 * @param email
	 * @return
	 */
	@GetMapping("check-email/{email}")
	public ResponseEntity<?> chekcEmail(@PathVariable("email") String email) {
		try {
			Optional<User> checkMail = userService.checkEmail(email);
			if (checkMail.isPresent()) {
				if (checkMail.get().getStatus() == 1) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(new ResponseObject("200", "Tài khoản đã tồn tại", checkMail));
				} else if (checkMail.get().getStatus() == 2) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(new ResponseObject("200", "Tài khoản chưa kích hoạt", checkMail));
				}

			}
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", "Email hợp lệ", null));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Xác nhận OTP
	 * 
	 * @param id
	 * @param userOTP
	 * @return
	 */
	@PutMapping("{id}")
	public ResponseEntity<?> confirmOtp(@PathVariable("id") Integer id, @RequestBody OTP userOTP) {
		try {
			userStatic = userService.findById(id);
			User user = userService.findById(id);
			int otp = userOTP.getOtp();
			Map<String, String> errorsMap = new HashMap<>();
			if (!Common.checkCreatedDateUser(user.getCreatedDate())) {
				errorsMap.put("createdDate", "Mã OTP của bạn đã hết hạn !");
			} else {
				if (otp == 0) {
					errorsMap.put("OTP", "Bạn chưa nhập mã OTP!");
				} else if (otp != user.getOtp()) {
					errorsMap.put("OTP", "Mã OTP của không đúng !");
				}
			}
			if (errorsMap != null && !errorsMap.isEmpty()) {
				return ResponseEntity.badRequest().body(errorsMap);
			} else {
				user.setStatus(1);
				return ResponseEntity.ok().body(userService.update(user));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy lại mã OTP
	 * 
	 * @param id
	 * @return
	 */
	@PutMapping("reset-otp/{id}")
	public ResponseEntity<?> resetOtp(@PathVariable("id") Integer id) {
		try {
			userStatic = userService.findById(id);
			User user = userService.findById(id);
			Random random = new Random();
			int otp = random.nextInt(900000) + 100000;
			user.setOtp(otp);
			user.setCreatedDate(LocalDateTime.now());
			smtpMailSender.sendMail(user.getEmail(), "Xác thực tài khoản", "Mã OTP mới của bạn là:" + otp);
			return ResponseEntity.ok(userService.update(user));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Cập nhật người dùng
	 * 
	 * @param id
	 * @param user
	 * @return
	 */
	@PutMapping("update-address/{id}")
	public ResponseEntity<User> updateCustomer(@PathVariable("id") Integer id, @RequestBody User user) {
		try {
			userStatic = userService.findById(id);
			user.setUserId(id);
			return ResponseEntity.ok(userService.update(user));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy người dùng theo mã
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("{id}")
	public ResponseEntity<User> findById(@PathVariable("id") Integer id) {
		try {
			User user = userService.findById(id);
			userStatic = user;
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * 
	 * @return
	 */
	public static User getUserStatic() {
		return userStatic;
	}

	/**
	 * Cập nhật thông tin khách hàng
	 * 
	 * @param id
	 * @param user
	 * @param locale
	 * @return
	 */
	@PutMapping("update-profile/{id}")
	public ResponseEntity<?> updateProfileCustomer(@PathVariable("id") Integer id, @RequestBody User user,
			Locale locale) {
		try {
			Map<String, String> listErrorMap = getListError(locale);
			Map<String, String> errorsMap = new HashMap<>();
			Optional<User> userByPhone = userService.checkPhoneUpdate(user.getPhone(), id);
			Optional<User> userByEmail = userService.checkEmailUpdate(user.getEmail(), id);

			// Validate fullname
			if (user.getFullName() == null || user.getFullName().isBlank()) {
				errorsMap.put("fullName", listErrorMap.get("User.fullName.NotBlank"));
			} else if (user.getFullName().length() > 100) {
				errorsMap.put("fullName", listErrorMap.get("User.fullName.Length"));
			} else if (!isValidName(user.getFullName())) {
				errorsMap.put("fullName", listErrorMap.get("User.fullname.RegexUserName"));
			}
			// Validate phone
			if (user.getPhone() == null || user.getPhone().isBlank()) {
				errorsMap.put("phone", listErrorMap.get("User.phone.NotBlank"));
			} else if (user.getPhone().length() < 10 || user.getPhone().length() > 20) {
				errorsMap.put("phone", listErrorMap.get("User.phone.Length"));
			} else if (!isValidPhone(user.getPhone())) {
				errorsMap.put("phone", listErrorMap.get("User.phone.RegexUserPhone"));
			} else if (userByPhone.isPresent()) {
				errorsMap.put("phone", listErrorMap.get("User.phone.UniqueUserPhone"));
			}
			// Validate email
			if (user.getEmail() == null || user.getEmail().isBlank()) {
				errorsMap.put("email", listErrorMap.get("User.email.NotBlank"));
			} else if (user.getEmail().length() > 100) {
				errorsMap.put("email", listErrorMap.get("User.email.Length"));
			} else if (!isValidEmail(user.getEmail())) {
				errorsMap.put("email", listErrorMap.get("User.email.RegexUserEmail"));
			} else if (userByEmail.isPresent()) {
				errorsMap.put("email", listErrorMap.get("User.email.UniqueUserEmail"));
			}
			// Validate DivisionName
			if (user.getDivisionName() == null || user.getDivisionName().isBlank()) {
				errorsMap.put("divisionName", listErrorMap.get("User.divisionName.NotNull"));
			} else if (user.getDivisionName().length() > 100) {
				errorsMap.put("divisionName", listErrorMap.get("User.divisionNameLength"));
			}
			// Validate DistrictName
			if (user.getDistrictName() == null || user.getDistrictName().isBlank()) {
				errorsMap.put("districtName", listErrorMap.get("User.districtName.NotNull"));
			} else if (user.getDistrictName().length() > 100) {
				errorsMap.put("districtName", listErrorMap.get("User.districtName.Length"));
			}
			// Validate WardName
			if (user.getWardName() == null || user.getWardName().isBlank()) {
				errorsMap.put("wardName", listErrorMap.get("User.wardName.NotNull"));
			} else if (user.getWardName().length() > 100) {
				errorsMap.put("wardName", listErrorMap.get("User.wardName.Length"));
			}
			// Validate Address
			if (user.getAddress().length() > 255) {
				errorsMap.put("address", listErrorMap.get("User.address.Length"));
			}

			if (errorsMap != null && !errorsMap.isEmpty()) {
				return ResponseEntity.badRequest().body(errorsMap);
			} else {
				return ResponseEntity.ok().body(userService.update(user));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	@GetMapping("address-by-UserID/{id}")
	public ResponseEntity<AddressBean> addressByUserID(@PathVariable("id") User user) {
		try {
			AddressBean ab = new AddressBean(user);
			return ResponseEntity.ok(ab);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * 
	 * @param ex
	 * @return
	 */
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

	/**
	 * regex số điện thoại
	 * 
	 * @param value
	 * @return boolean
	 */
	public boolean isValidPhone(String value) {
		Pattern pattern = Pattern.compile("[0-9]{10,20}$");
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

	/**
	 * regex email
	 * 
	 * @param value
	 * @return boolean
	 */
	public boolean isValidEmail(String value) {
		Pattern pattern = Pattern.compile("^\\w*?[a-zA-Z]\\w+@[a-z\\d\\-]+(\\.[a-z\\d\\-]+)*\\.[a-z]+\\z");
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

	/**
	 * regex name
	 * 
	 * @param value
	 * @return boolean
	 */
	public boolean isValidName(String value) {
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

}
