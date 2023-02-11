/**
 * Luvina Software JSC, 2022
 * UniqueProductNameValidator.java, Bui Quang Hieu
 */
package com.pro2111.validations.products;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.pro2111.restcontrollers.admin.ProductRestControllerAdmin;
import com.pro2111.service.ProductService;

/**
 * @author BUI_QUANG_HIEU
 */
public class UniqueProductNameValidator implements ConstraintValidator<UniqueProductName, String>{
	@Autowired
	HttpServletRequest request;

	@Autowired
	ProductService service;

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
				String name = ProductRestControllerAdmin.getProductStatic().getProductName();
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
