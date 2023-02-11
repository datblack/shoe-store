/**
 * DATN_FALL2022, 2022
 * ProductRestControllerUser.java, BUI_QUANG_HIEU
 */
package com.pro2111.restcontrollers.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro2111.beans.FilterProductBean;
import com.pro2111.entities.Product;
import com.pro2111.service.ProductService;
import com.pro2111.utils.Constant;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("user/rest/products")
@Validated
public class ProductRestControllerUser {
	@Autowired
	private ProductService productService;

	/**
	 * Lấy tất cả Product có trạng thái true
	 * 
	 * @return
	 */
	@GetMapping("find-by-status-true")
	public ResponseEntity<List<Product>> findByStatusTrue() {
		try {
			return ResponseEntity.ok(productService.findByStatusLike(Constant.STATUS_TRUE_PRODUCT));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-by-id/{idProduct}")
	public ResponseEntity<Product> findById(@PathVariable("idProduct") Integer id) {
		try {
			return ResponseEntity.ok(productService.findById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping
	public ResponseEntity<List<Product>> findByRangePrice(@RequestBody FilterProductBean searchRequest) {
		try {
			return ResponseEntity.ok(productService.findByRangePrice(searchRequest));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
