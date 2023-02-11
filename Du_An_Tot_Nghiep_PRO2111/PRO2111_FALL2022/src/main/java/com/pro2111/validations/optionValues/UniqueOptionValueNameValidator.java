/**
 * Luvina Software JSC, 2022
 * UniqueProductNameValidator.java, Bui Quang Hieu
 */
package com.pro2111.validations.optionValues;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.pro2111.restcontrollers.admin.OptionValueRestControllerAdmin;
import com.pro2111.service.OptionValueService;

/**
 * @author BUI_QUANG_HIEU
 */
public class UniqueOptionValueNameValidator implements ConstraintValidator<UniqueOptionValueName, String>{
	@Autowired
	HttpServletRequest request;

	@Autowired
	OptionValueService service;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
//		System.out.println(request.getMethod());

//		
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
				String name = OptionValueRestControllerAdmin.getOptionValueStatic().getValueName();
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
