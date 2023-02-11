/**
 * Luvina Software JSC, 2022
 * UniqueProductName.java, Bui Quang Hieu
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
 */

@Documented
@Constraint(validatedBy = UniqueUserPhoneValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUserPhone {
	String message() default "Số điện thoại đã tồn tại";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
