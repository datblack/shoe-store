/**
 * Luvina Software JSC, 2022
 * UniqueProductNameValidator.java, Bui Quang Hieu
 */
package com.pro2111.validations.users;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.pro2111.restcontrollers.admin.UserRestControllerAdmin;
import com.pro2111.service.UserService;

/**
 * @author BUI_QUANG_HIEU
 */
public class UniqueUserPhoneValidator implements ConstraintValidator<UniqueUserPhone, String> {
	@Autowired
	HttpServletRequest request;

	@Autowired
	UserService service;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (this.request == null) {
			return true;
		} else {
			String method = request.getMethod();
			if (method.equalsIgnoreCase("POST")) {
				if (service.findByPhone(value) != null) {
					return false;
				} else {
					return true;
				}
			} else {
				String name = UserRestControllerAdmin.getUserStatic().getPhone();
				if (name.equals(value)) {
					return true;
				} else {
					if (service.findByPhone(value) != null) {
						return false;
					} else {
						return true;
					}
				}
			}
		}

	}
}
