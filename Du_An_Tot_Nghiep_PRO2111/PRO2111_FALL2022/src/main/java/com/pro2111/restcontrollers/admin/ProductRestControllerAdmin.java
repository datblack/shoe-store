package com.pro2111.restcontrollers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pro2111.beans.ProductAndProductOptionBean;
import com.pro2111.entities.Product;
import com.pro2111.service.ProductService;

@RestController
@CrossOrigin("*")
@RequestMapping("admin/rest/products")
public class ProductRestControllerAdmin {
	@Autowired
	private ProductService productService;

	static Product productStatic;

	/**
	 * Lấy tất cả Product
	 * 
	 * @return
	 */
	@GetMapping()
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<Product>> getAll() {
		try {
			return ResponseEntity.ok(productService.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy Product theo id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("{id}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<Product> findById(@PathVariable("id") Integer id) {
		try {
			productStatic = productService.findById(id);
			return ResponseEntity.ok(productService.findById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy list Product theo status
	 * 
	 * @return
	 */
	@GetMapping("by-status-true")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<Product>> findByStatusTrue() {
		try {
			return ResponseEntity.ok(productService.findByStatusLike(1));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy list Product theo name gần đúng
	 * 
	 * @param name
	 * @return
	 */
	@GetMapping("find-by-approximate-name/{name}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<Product>> findByApproximateName(@PathVariable("name") String name) {
		try {
			return ResponseEntity.ok(productService.findByApproximateName(name));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Tạo mới Product
	 * 
	 * @param product
	 * @return
	 */
	@PostMapping()
	@PreAuthorize("@adminAuthorizer.authorize(authentication)")
	public ResponseEntity<Product> create(@Valid @RequestBody ProductAndProductOptionBean product) {
		try {
			return ResponseEntity.ok(productService.create(product));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Cập nhật Product theo productId
	 * 
	 * @param id
	 * @param product
	 * @return
	 */
	@PutMapping("{id}")
	@PreAuthorize("@adminAuthorizer.authorize(authentication)")
	public ResponseEntity<Product> update(@PathVariable("id") Integer id, @Valid @RequestBody Product product) {
		try {
			product.setProductId(id);
			return ResponseEntity.ok(productService.update(product));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Xóa Product theo ProductId
	 * 
	 * @param product
	 * @return
	 */
	@DeleteMapping("{id}")
	@PreAuthorize("@adminAuthorizer.authorize(authentication)")
	public ResponseEntity<Product> delete(@PathVariable("id") Product product) {
		try {
			return ResponseEntity.ok(productService.delete(product));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	public static Product getProductStatic() {
		return productStatic;
	}

}
