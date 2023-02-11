/**
 * DATN_FALL2022, 2022
 * OptionRestControllerUser.java, BUI_QUANG_HIEU
 */
package com.pro2111.restcontrollers.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro2111.entities.Option;
import com.pro2111.service.OptionService;
import com.pro2111.utils.Constant;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("user/rest/options")
public class OptionRestControllerUser {
	@Autowired
	private OptionService optionService;

	/**
	 * Lấy list Option đang hoạt động và được phép hiển thị
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<Option>> getAllOption() {
		try {
			return ResponseEntity.ok(
					optionService.findByStatusLikeAndIsShowLike(Constant.STATUS_TRUE_OPTION, Constant.IS_SHOW_OPTION));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

}
