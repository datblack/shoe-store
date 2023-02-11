/**
 * DATN_FALL2022, 2022
 * OptionValueRestControllerUser.java, BUI_QUANG_HIEU
 */
package com.pro2111.restcontrollers.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro2111.entities.Option;
import com.pro2111.entities.OptionValue;
import com.pro2111.service.OptionValueService;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("user/rest/option-values")
public class OptionValueRestControllerUser {
	@Autowired
	private OptionValueService optionValueService;

	/**
	 * Lấy list OptionValue theo optionId
	 * 
	 * @param option
	 * @return
	 */
	@GetMapping("find-by-option/{optionId}")
	public ResponseEntity<List<OptionValue>> findOptionValueByOption(@PathVariable("optionId") Option option) {
		try {
			return ResponseEntity.ok(optionValueService.findByOptionsLikeAndStatusLikeAndIsShowLike(option, 1, 1));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
