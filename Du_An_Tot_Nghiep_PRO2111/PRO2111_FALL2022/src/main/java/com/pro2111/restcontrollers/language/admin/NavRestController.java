/**
 * DATN_FALL2022, 2022
 * NavRestController.java, BUI_QUANG_HIEU
 */
package com.pro2111.restcontrollers.language.admin;

import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro2111.i18n.ExposedResourceBundleMessageSource;
import com.pro2111.utils.Constant;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("rest/language/nav/admin")
public class NavRestController {
	@Autowired
	private ExposedResourceBundleMessageSource bundleMessageSource;

	@GetMapping
	public Map<String, String> getLanguageNavAdmin(Locale locale) {
		Map<String, String> map = bundleMessageSource.getValueContaisKey(Constant.PATH_PROPERTIES_I18N, locale,
				Constant.ADMIN_NAV);
		return map;
	}
}
