/**
 * DATN_FALL2022, 2022
 * RegexUserPhoneValidator.java, BUI_QUANG_HIEU
 */
package com.pro2111.validations.users;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public class RegexUserPhoneValidator implements ConstraintValidator<RegexUserPhone, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		Pattern pattern = Pattern.compile("[0-9]{10,20}$");
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

}
