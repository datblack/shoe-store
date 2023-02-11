/**
 * Luvina Software JSC, 2022
 * VariantValueRestControllerAdmin.java, Bui Quang Hieu
 */
package com.pro2111.restcontrollers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pro2111.entities.Option;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductOption;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.VariantValue;
import com.pro2111.service.ProductVariantService;
import com.pro2111.service.VariantValueService;

/**
 * @author BUI_QUANG_HIEU
 */
@RestController
@CrossOrigin("*")
@RequestMapping("admin/rest/variant-values")
public class VariantValueRestControllerAdmin {
	@Autowired
	private VariantValueService variantValueService;

	@Autowired
	private ProductVariantService productVariantService;

	@GetMapping()
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<VariantValue>> findAll() {
		try {
			return ResponseEntity.ok(variantValueService.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-by-product-variant/{idProductVariant}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<VariantValue>> findByProductVariant(
			@PathVariable("idProductVariant") ProductVariant productVariant) {
		try {
			return ResponseEntity.ok(variantValueService.findByProductVariant(productVariant));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-by-product-variant-origin/{idProductVariant}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<VariantValue>> findByProductVariantOrigin(
			@PathVariable("idProductVariant") ProductVariant productVariant) {
		try {
			return ResponseEntity.ok(variantValueService.findByProductVariantOrigin(productVariant));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-by-product-variant-status-true/{idProductVariant}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<VariantValue>> findByProductVariantStatusTrue(
			@PathVariable("idProductVariant") ProductVariant productVariant) {
		try {
			return ResponseEntity.ok(variantValueService.findByProductVariantsLikeAndStatusLike(productVariant, 1));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-by-product-option/{productId}/{optionId}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<VariantValue>> findByProductOption(@PathVariable("productId") Product product,
			@PathVariable("optionId") Option option) {
		try {
			ProductOption productOption = variantValueService.findByProductsLikeAndOptionsLike(product, option);
			return ResponseEntity.ok(variantValueService.findByProductOption(productOption));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}

//	@PutMapping("update-status")
//	@PreAuthorize("@appAuthorizer.authorize(authentication)")
//	public ResponseEntity<VariantValue> updateStatus(@Valid @RequestBody VariantValue variantValue) {
//		try {
//			variantValueService.updateStatus(variantValue.getProductVariants(),
//					variantValue.getProductOptions().getProducts(), variantValue.getProductOptions().getOptions(),
//					variantValue.getStatus());
//
//			return ResponseEntity.ok(variantValue);
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//		}
//	}

	@PostMapping()
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<VariantValue>> createVariantValue(@RequestBody List<VariantValue> variantValues) {
		try {
			return ResponseEntity.ok(variantValueService.create(variantValues));
		} catch (Exception e) {
			e.printStackTrace();
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

}
