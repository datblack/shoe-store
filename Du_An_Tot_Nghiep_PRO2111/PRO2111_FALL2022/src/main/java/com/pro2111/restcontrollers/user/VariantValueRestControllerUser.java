/**
 * DATN_FALL2022, 2022
 * VariantValueRestControllerUser.java, BUI_QUANG_HIEU
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

import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.VariantValue;
import com.pro2111.service.VariantValueService;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("user/rest/variant-values")
public class VariantValueRestControllerUser {

	@Autowired
	private VariantValueService valueService;

	/**
	 * Lấy list VariantValue theo variantId
	 * 
	 * @param productVariant
	 * @return
	 */
	@GetMapping("find-by-product-variant/{idProductVariant}")
	public ResponseEntity<List<VariantValue>> findByProductVariant(
			@PathVariable("idProductVariant") ProductVariant productVariant) {
		try {
			return ResponseEntity.ok(valueService.findByProductVariant(productVariant));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	
	/**
	 * Lấy list VariantValue theo variantId
	 * 
	 * @param productVariant
	 * @return
	 */
	@GetMapping("find-by-product-variant-origin/{idProductVariant}")
	public ResponseEntity<List<VariantValue>> findByProductVariantOrigin(
			@PathVariable("idProductVariant") ProductVariant productVariant) {
		try {
			return ResponseEntity.ok(valueService.findByProductVariantOrigin(productVariant));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

}
