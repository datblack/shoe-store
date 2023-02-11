/**
 * DATN_FALL2022, 2022
 * OpAndOvRestControllerUser.java, BUI_QUANG_HIEU
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

import com.pro2111.beans.OptionAndOptionValue;
import com.pro2111.entities.Product;
import com.pro2111.service.OpAndOvService;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("user/rest/option-optionvalue")
public class OpAndOvRestControllerUser {

	@Autowired
	private OpAndOvService opAndOvService;

	/**
	 * Lấy list OptionAndOptionValue theo ProductId
	 * 
	 * @param product
	 * @return
	 */
	@GetMapping("find-by-product/{idProduct}")
	public ResponseEntity<List<OptionAndOptionValue>> findByProduct(@PathVariable("idProduct") Product product) {
		try {
			return ResponseEntity.ok(opAndOvService.findByProduct(product));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
