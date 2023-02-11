/**
 * DATN_FALL2022, 2022
 * ExposedResourceBundleMessageSource.java, BUI_QUANG_HIEU
 */
package com.pro2111.i18n;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Configuration
public class ExposedResourceBundleMessageSource{
	@Autowired
	private MessageSource messageSource;

	public Map<String, String> getValueContaisKey(String basename, Locale locale, String containKey) {
		Map<String, String> mapValues = new HashedMap<String, String>();
		// create a new ResourceBundle with specified locale
		ResourceBundle bundle = ResourceBundle.getBundle(basename, locale);
		// get the keys
		Enumeration<?> enumeration = bundle.getKeys();

		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			if (key.contains(containKey)) {
				mapValues.put(key, messageSource.getMessage(key, null, locale));
			}
		}
		return mapValues;
	}
}
