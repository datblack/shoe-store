/**
 * Luvina Software JSC, 2022
 * OptionValueRestControllerAdmin.java, Bui Quang Hieu
 */
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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pro2111.beans.OptionValuesBean;
import com.pro2111.entities.Option;
import com.pro2111.entities.OptionValue;
import com.pro2111.entities.Product;
import com.pro2111.service.OptionValueService;
import com.pro2111.utils.FormateString;

/**
 * @author BUI_QUANG_HIEU
 */
@RestController
@CrossOrigin("*")
@RequestMapping("admin/rest/option-values")

public class OptionValueRestControllerAdmin {

	@Autowired
	private OptionValueService optionValueService;

	static OptionValue optionValueStatic;

	/**
	 * Lấy tất cả OptionValue
	 * 
	 * @return
	 */
	@GetMapping()
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<OptionValue>> findAll() {
		return ResponseEntity.ok(optionValueService.findAll());
	}

	/**
	 * Lấy OptionValue theo valueId
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("{id}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<OptionValue> findById(@PathVariable("id") Integer id) {
		try {
			optionValueStatic = optionValueService.findById(id);
			return ResponseEntity.ok(optionValueService.findById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy list OptionValue theo optionId
	 * 
	 * @param option
	 * @return
	 */
	@GetMapping("find-by-option/{idOption}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<OptionValue>> findByOption(@PathVariable("idOption") Option option) {
		try {
			return ResponseEntity.ok(optionValueService.findByOption(option));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/*
	 * Lấy list OptionValue có trạng thái true và optionId
	 */
	@GetMapping("find-option-value-true-by-option/{idOption}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<OptionValue>> findOptionValueTrueByOption(@PathVariable("idOption") Option option) {
		try {
			return ResponseEntity.ok(optionValueService.findOptionValueTrueByOption(option));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấu list OptionValue chưa tồn tại trong variantValue theo productId, optionId
	 * 
	 * @param product
	 * @param option
	 * @return
	 */
	@GetMapping("find-not-exists-variant-value/{idProduct}/{idOption}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<OptionValue>> findNotExistsVariantValue(@PathVariable("idProduct") Product product,
			@PathVariable("idOption") Option option) {
		try {
			return ResponseEntity.ok(optionValueService.findNotExistsVariantValue(product, option));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Tạo list OptionValue
	 * 
	 * @param optionValues
	 * @return
	 */
	@PostMapping("create-list-option-value")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<OptionValue>> createList(@Valid @RequestBody OptionValuesBean optionValues) {
		try {
			return ResponseEntity.ok(optionValueService.createListOptionValue(optionValues.getListOptionValues()));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Tạo OptionValue
	 * 
	 * @param optionValue
	 * @return
	 */
	@PostMapping()
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<OptionValue> create(@Valid @RequestBody OptionValue optionValue) {
		try {
			return ResponseEntity.ok(optionValueService.create(optionValue));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Cập nhật OptionValue theo valueId
	 * 
	 * @param id
	 * @param optionValue
	 * @return
	 */
	@PutMapping("{id}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<OptionValue> update(@PathVariable("id") Integer id,
			@Valid @RequestBody OptionValue optionValue) {
		try {
			optionValue.setValueId(id);
			return ResponseEntity.ok(optionValueService.create(optionValue));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Kiểm tra OptionValue được phép xóa không theo valueId
	 * 
	 * @param optionValue
	 * @return
	 */
	@GetMapping("check-delete-option-value/{id}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<JsonNode> checkDeleteOption(@PathVariable("id") OptionValue optionValue) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode node = mapper.createObjectNode();
			Boolean checkDelete = optionValueService.checkDeleteOptionValue(optionValue);
			node.put("value", checkDelete);
			return ResponseEntity.ok(node);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Xóa OptionValue theo valueId
	 * 
	 * @param optionValue
	 * @return
	 */
	@DeleteMapping("{id}")
	public ResponseEntity<OptionValue> deleteOptionValue(@PathVariable("id") OptionValue optionValue) {
		try {
			return ResponseEntity.ok(optionValueService.deleteOptionValue(optionValue));
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

	public static OptionValue getOptionValueStatic() {
		return optionValueStatic;
	}

}