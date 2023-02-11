/**
 * Luvina Software JSC, 2022
 * UniqueProductNameValidator.java, Bui Quang Hieu
 */
package com.pro2111.validations.options;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.pro2111.restcontrollers.admin.OptionRestControllerAdmin;
import com.pro2111.service.OptionService;

/**
 * @author BUI_QUANG_HIEU
 */
public class UniqueOptionNameValidator implements ConstraintValidator<UniqueOptionName, String>{
	@Autowired
	HttpServletRequest request;

	@Autowired
	OptionService service;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
	
		if (this.request == null) {
			return true;
		} else {
			String method = request.getMethod();
			if (method.equalsIgnoreCase("POST")) {
				if (service.findByName(value) != null) {
					return false;
				} else {
					return true;
				}
			} else {
				String name = OptionRestControllerAdmin.getOptionStatic().getOptionName();
				if (name.equals(value)) {
					return true;
				} else {
					if (service.findByName(value)!= null) {
						return false;
					} else {
						return true;
					}
				}
			}
		}

	}
}
