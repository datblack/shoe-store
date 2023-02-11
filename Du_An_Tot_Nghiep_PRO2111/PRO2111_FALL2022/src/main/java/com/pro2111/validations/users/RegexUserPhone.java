/**
 * DATN_FALL2022, 2022
 * RegexUserPhone.java, BUI_QUANG_HIEU
 */
package com.pro2111.validations.users;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Documented
@Constraint(validatedBy = RegexUserPhoneValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RegexUserPhone {
	String message() default "Số điện thoại không hợp lệ";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
